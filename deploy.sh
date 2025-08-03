#!/bin/bash

# ===========================================
# Vehicle Status Service 部署脚本
# 用于在GCP VM上部署Docker容器化应用
# ===========================================

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 脚本配置
IMAGE_NAME="vehicle-status-service"
CONTAINER_NAME="vehicle-status-app"
HOST_PORT="8080"
CONTAINER_PORT="8080"
DOCKER_REGISTRY="gcr.io/your-project-id" # 替换为你的GCP项目ID

echo -e "${BLUE}=== Vehicle Status Service 部署开始 ===${NC}"

# 函数：打印带时间戳的日志
log() {
    echo -e "${GREEN}[$(date '+%Y-%m-%d %H:%M:%S')]${NC} $1"
}

error() {
    echo -e "${RED}[$(date '+%Y-%m-%d %H:%M:%S')] 错误:${NC} $1"
}

warn() {
    echo -e "${YELLOW}[$(date '+%Y-%m-%d %H:%M:%S')] 警告:${NC} $1"
}

# 检查Docker是否安装
if ! command -v docker &> /dev/null; then
    error "Docker未安装，请先安装Docker"
    exit 1
fi

log "停止现有容器..."
if docker stop $CONTAINER_NAME 2>/dev/null; then
    log "容器 $CONTAINER_NAME 已停止"
else
    warn "容器 $CONTAINER_NAME 未在运行"
fi

log "删除现有容器..."
if docker rm $CONTAINER_NAME 2>/dev/null; then
    log "容器 $CONTAINER_NAME 已删除"
else
    warn "容器 $CONTAINER_NAME 不存在"
fi

log "拉取最新镜像..."
if docker pull $DOCKER_REGISTRY/$IMAGE_NAME:latest; then
    log "镜像拉取成功"
else
    error "镜像拉取失败"
    exit 1
fi

log "启动新容器..."
if docker run -d \
    --name $CONTAINER_NAME \
    --restart unless-stopped \
    -p $HOST_PORT:$CONTAINER_PORT \
    -e SPRING_PROFILES_ACTIVE=production \
    --memory="1g" \
    --cpus="1.0" \
    $DOCKER_REGISTRY/$IMAGE_NAME:latest; then
    log "容器启动成功"
else
    error "容器启动失败"
    exit 1
fi

log "等待应用启动..."
sleep 30

# 健康检查
log "执行健康检查..."
for i in {1..10}; do
    if curl -f http://localhost:$HOST_PORT/api/vehicle-status/speed/test >/dev/null 2>&1; then
        log "应用健康检查通过 ✅"
        break
    elif [ $i -eq 10 ]; then
        error "应用健康检查失败，请检查日志"
        docker logs $CONTAINER_NAME --tail 50
        exit 1
    else
        warn "健康检查第 $i 次失败，等待重试..."
        sleep 10
    fi
done

# 清理旧镜像（保留最新的2个版本）
log "清理旧镜像..."
docker image prune -f
OLD_IMAGES=$(docker images $DOCKER_REGISTRY/$IMAGE_NAME --format "table {{.Repository}}:{{.Tag}}\t{{.CreatedAt}}" | tail -n +2 | tail -n +3 | awk '{print $1}')
if [ ! -z "$OLD_IMAGES" ]; then
    echo "$OLD_IMAGES" | xargs -r docker rmi
    log "旧镜像清理完成"
else
    log "没有需要清理的旧镜像"
fi

# 显示容器状态
log "容器状态:"
docker ps | grep $CONTAINER_NAME

# 显示应用日志（最后20行）
log "应用日志（最后20行）:"
docker logs $CONTAINER_NAME --tail 20

echo -e "${GREEN}=== 部署完成! 🎉 ===${NC}"
echo -e "应用访问地址: ${BLUE}http://$(curl -s ifconfig.me):$HOST_PORT${NC}"
echo -e "查看日志命令: ${YELLOW}docker logs -f $CONTAINER_NAME${NC}" 
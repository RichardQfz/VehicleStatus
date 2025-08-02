#!/bin/bash

# ===========================================
# Vehicle Status Service 本地构建脚本
# 用于本地开发、构建和测试Docker镜像
# ===========================================

# 设置颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 脚本配置
IMAGE_NAME="vehicle-status-service"
CONTAINER_NAME="vehicle-status-local"
HOST_PORT="8080"
CONTAINER_PORT="8080"
DOCKER_REGISTRY="gcr.io/your-project-id" # 替换为你的GCP项目ID

echo -e "${BLUE}=== Vehicle Status Service 本地构建开始 ===${NC}"

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

# 检查必要工具
check_dependencies() {
    log "检查依赖工具..."
    
    if ! command -v mvn &> /dev/null; then
        error "Maven未安装，请先安装Maven"
        exit 1
    fi
    
    if ! command -v docker &> /dev/null; then
        error "Docker未安装，请先安装Docker"
        exit 1
    fi
    
    if ! command -v java &> /dev/null; then
        error "Java未安装，请先安装Java"
        exit 1
    fi
    
    log "依赖检查通过 ✅"
}

# Maven构建
build_with_maven() {
    log "开始Maven构建..."
    
    if mvn clean package -DskipTests; then
        log "Maven构建成功 ✅"
    else
        error "Maven构建失败"
        exit 1
    fi
    
    # 检查JAR文件是否存在
    if ls target/*.jar 1> /dev/null 2>&1; then
        JAR_FILE=$(ls target/*.jar | head -n 1)
        log "找到JAR文件: $JAR_FILE"
    else
        error "未找到JAR文件"
        exit 1
    fi
}

# Docker镜像构建
build_docker_image() {
    log "开始构建Docker镜像..."
    
    if docker build -t $IMAGE_NAME:latest .; then
        log "Docker镜像构建成功 ✅"
    else
        error "Docker镜像构建失败"
        exit 1
    fi
    
    # 同时打标签用于推送
    docker tag $IMAGE_NAME:latest $DOCKER_REGISTRY/$IMAGE_NAME:latest
    log "镜像标签添加完成"
}

# 停止并删除现有测试容器
cleanup_existing_container() {
    log "清理现有测试容器..."
    
    if docker stop $CONTAINER_NAME 2>/dev/null; then
        log "测试容器已停止"
    fi
    
    if docker rm $CONTAINER_NAME 2>/dev/null; then
        log "测试容器已删除"
    fi
}

# 本地测试运行
run_local_test() {
    log "启动本地测试容器..."
    
    if docker run -d \
        --name $CONTAINER_NAME \
        -p $HOST_PORT:$CONTAINER_PORT \
        -e SPRING_PROFILES_ACTIVE=local \
        $IMAGE_NAME:latest; then
        log "测试容器启动成功 ✅"
    else
        error "测试容器启动失败"
        exit 1
    fi
    
    log "等待应用启动..."
    sleep 20
    
    # 健康检查
    log "执行本地健康检查..."
    for i in {1..5}; do
        if curl -f http://localhost:$HOST_PORT/api/vehicle-status/speed/test >/dev/null 2>&1; then
            log "本地应用健康检查通过 ✅"
            break
        elif [ $i -eq 5 ]; then
            warn "健康检查失败，但应用可能仍在启动中"
            log "请手动检查: http://localhost:$HOST_PORT"
            break
        else
            warn "健康检查第 $i 次失败，等待重试..."
            sleep 10
        fi
    done
}

# 显示应用信息
show_app_info() {
    log "应用信息:"
    echo -e "  • 本地访问地址: ${BLUE}http://localhost:$HOST_PORT${NC}"
    echo -e "  • API测试地址: ${BLUE}http://localhost:$HOST_PORT/api/vehicle-status/speed/ABC123${NC}"
    echo -e "  • 容器名称: ${YELLOW}$CONTAINER_NAME${NC}"
    echo -e "  • 镜像名称: ${YELLOW}$IMAGE_NAME:latest${NC}"
    
    log "容器状态:"
    docker ps | grep $CONTAINER_NAME || warn "容器未在运行"
    
    log "应用日志（最后10行）:"
    docker logs $CONTAINER_NAME --tail 10 2>/dev/null || warn "无法获取日志"
}

# 推送镜像到仓库（可选）
push_image() {
    read -p "是否要推送镜像到仓库？ (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        log "推送镜像到仓库..."
        if docker push $DOCKER_REGISTRY/$IMAGE_NAME:latest; then
            log "镜像推送成功 ✅"
        else
            error "镜像推送失败"
        fi
    else
        log "跳过镜像推送"
    fi
}

# 主要执行流程
main() {
    check_dependencies
    build_with_maven
    build_docker_image
    cleanup_existing_container
    run_local_test
    show_app_info
    push_image
    
    echo -e "${GREEN}=== 本地构建完成! 🎉 ===${NC}"
    echo -e "${YELLOW}提示:${NC}"
    echo -e "  • 查看日志: ${BLUE}docker logs -f $CONTAINER_NAME${NC}"
    echo -e "  • 停止容器: ${BLUE}docker stop $CONTAINER_NAME${NC}"
    echo -e "  • 删除容器: ${BLUE}docker rm $CONTAINER_NAME${NC}"
}

# 处理命令行参数
case "${1:-}" in
    "clean")
        log "清理模式：停止并删除测试容器"
        cleanup_existing_container
        docker rmi $IMAGE_NAME:latest 2>/dev/null || true
        log "清理完成"
        ;;
    "build-only")
        log "仅构建模式：只构建不运行"
        check_dependencies
        build_with_maven
        build_docker_image
        log "构建完成"
        ;;
    "help"|"-h"|"--help")
        echo "用法: $0 [选项]"
        echo "选项:"
        echo "  无参数    - 完整构建和测试流程"
        echo "  clean     - 清理容器和镜像"
        echo "  build-only - 仅构建不运行"
        echo "  help      - 显示帮助信息"
        ;;
    *)
        main
        ;;
esac 
# 使用OpenJDK 17作为基础镜像
FROM openjdk:17-jdk-slim

# 设置维护者信息
LABEL maintainer="vehicle-status-service"
LABEL description="Vehicle Status Microservice"

# 设置工作目录
WORKDIR /app

# 复制构建好的JAR文件
COPY app.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 添加健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=20s --retries=3 \
  CMD curl -f http://localhost:8080/api/vehicle-status/health || exit 1

# 设置JVM参数和启动命令
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=production", \
    "-Xmx512m", \
    "-Xms256m", \
    "-jar", \
    "app.jar"] 
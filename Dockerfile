# ========================================
# Stage 1: Build
# ========================================
# 注意: maven 和 eclipse-temurin 是第三方镜像, DaoCloud 不支持
# 如果拉取失败, 请在前面加 docker.1ms.run/ 前缀, 如:
# FROM docker.1ms.run/library/maven:3.9.4-eclipse-temurin-17 AS builder
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn -B -DskipTests package

# ========================================
# Stage 2: Runtime
# ========================================
# 如果拉取失败, 请改为: docker.1ms.run/library/eclipse-temurin:17-jre-alpine
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="ivalo1"
LABEL description="WeChat Mini Program Clothing Store Backend"

# 安装 curl 用于健康检查
RUN apk add --no-cache curl

# 创建非 root 用户
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# 复制构建产物
COPY --from=builder /workspace/target/*.jar app.jar

# 创建日志目录
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app

# 切换用户
USER appuser

# JVM 参数优化
ENV JAVA_OPTS="-Xms512m -Xmx1024m \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/app/logs/heapdump.hprof \
  -Djava.security.egd=file:/dev/./urandom \
  -Dfile.encoding=UTF-8"

EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --retries=3 --start-period=60s \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

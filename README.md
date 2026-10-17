# wechat-miniapp-clothing-backend

后端项目（Spring Boot + Spring MVC + MyBatis）用于为微信小程序提供商品、订单、用户等 API。

快速开始：
1. 克隆仓库
   git clone https://github.com/Ivalo1/wechat-miniapp-clothing-backend.git

2. 本地启动（需要 Docker + Docker Compose）
   docker-compose up --build

3. 服务启动后访问：
   http://localhost:8080/actuator/health

下一步建议：
- 我可以继续添加订单、用户模块以及微信登录接口（通过微信小程序登录流程）
- 添加 Swagger/OpenAPI 文档
- 添加单元测试与 CI 配置

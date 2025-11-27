## SqlToCode MCP Server

### 项目简介
SqlToCode 是一个基于 Spring Boot 3.4 与 Spring AI MCP Server Starter 构建的示例服务，提供 **SQL 字段定义 → Java 实体代码** 的自动化转换能力。项目通过 `@Tool` 注解将 `SqlToCodeService` 暴露为 MCP 工具，可以被兼容 MCP 协议的客户端（如 Claude、Cursor、vscode 扩展等）直接调用。

### 功能特性
- **SQL→Java 自动转换**：将简化后的 `CREATE TABLE` 字段列表转换为带 `@Data` 注解的 Java 类。
- **类型映射内置**：支持 `BIGINT / INT / VARCHAR / DECIMAL / DATETIME` 等常见 SQL 类型自动映射至 Java 类型。
- **标准 MCP 能力**：已开启 `tool / resource / prompt / completion` 能力，可通过 SSE 或 stdio 模式对接。
- **日志与配置**：默认将运行日志输出到 `data/log/SqlToCode.log`，方便排查。

### 技术栈
- Java 17
- Spring Boot 3.4.3
- Spring AI MCP Server & WebFlux Starter
- Lombok
- Maven

### 目录结构
```
├─src/main/java/cn/maysjl/ai
│ ├─Application.java         // Spring Boot 启动类
│ ├─config/ToolConfig.java   // 注册 MCP ToolCallbackProvider
│ ├─model/                   // 请求/响应模型
│ └─service/SqlToCodeService // 核心 SQL→Code 逻辑
├─src/main/resources/application.yml
├─data/log/SqlToCode.log
└─pom.xml
```

### 环境要求
- JDK 17+
- Maven 3.9+

### 快速开始
1. **克隆与编译**
   ```bash
   git clone <repo-url>
   cd SqlToCode
   mvn clean package -DskipTests
   ```
2. **运行服务**
   ```bash
   mvn spring-boot:run
   ```
   默认监听 `http://localhost:8701`，同时暴露 MCP SSE 端点 `/mcp/messages`。

3. **MCP 客户端接入**
   - SSE：配置客户端指向 `http://localhost:8701/mcp/messages`。
   - stdio：可在 `application.yml` 中取消注释 `spring.main.web-application-type: none` 并按需调整。

### 工具入参示例
```json
{
  "codeType": "java",
  "sqlTableName": "Product",
  "sql": [
    { "name": "id", "type": "BIGINT" },
    { "name": "name", "type": "VARCHAR(100)" },
    { "name": "price", "type": "INT" }
  ]
}
```

### 工具返回示例
```json
{
  "codeType": "java",
  "resCode": "import lombok.Data;\n\n@Data\npublic class Product {\n\n    private Long id;\n    private String name;\n    private Integer price;\n}"
}
```

### 配置说明
- `server.port`: 默认 8701，可自行修改。
- `spring.ai.mcp.server.*`: MCP 服务器的名称、版本、能力与 SSE 端点。按需调整 `instructions` 以向客户端描述工具用途。
- `logging.file.name`: 日志文件位置，默认落盘到 `data/log/SqlToCode.log`。

### 开发与调试
- 修改 `SqlToCodeService.sqlToJavaType` 可扩展类型映射。
- 通过 `MethodToolCallbackProvider` 可以继续注册更多工具。
- 如需添加资源/提示词等 MCP 能力，可在 `application.yml` 中开启对应配置并实现相关组件。

### 后续规划
- 支持更多编程语言（如 Kotlin、TypeScript DTO）。
- 根据完整 `CREATE TABLE` 语句自动解析字段列表，减少手工输入。
- 增加单元测试覆盖核心类型映射逻辑。

欢迎根据业务需要进行二次开发，如有问题可直接提 issue 或与作者联系。


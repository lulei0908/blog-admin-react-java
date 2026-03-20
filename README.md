# 个人博客后端管理系统

Spring Boot + React 全栈博客管理后端系统。

## 功能特性

- 🔐 JWT 认证（登录/注册/登出）
- 📝 文章管理（CRUD、草稿/发布、置顶、Markdown）
- 🗂️ 分类管理（树形结构）
- 🏷️ 标签管理
- 💬 评论审核
- 🖼️ 媒体上传
- 📊 统计仪表盘

## 技术栈

| 模块 | 技术 |
|------|------|
| 后端 | Spring Boot 3.2 + MyBatis-Plus + MySQL |
| 前端 | React 18 + Vite + Ant Design 5 |
| 认证 | Spring Security + JWT |

## 快速开始

### Docker 部署

```bash
docker-compose up -d
```

### 本地开发

```bash
# 后端
cd blog-backend
./mvnw spring-boot:run

# 前端
cd blog-frontend
npm install && npm run dev
```

## 配置

修改 `blog-backend/src/main/resources/application.yml` 中的数据库连接信息。

## 默认账号

- 用户名: admin
- 密码: admin123
# test

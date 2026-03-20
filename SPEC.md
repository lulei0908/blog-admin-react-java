# 个人博客后端管理系统 - 技术规格文档

## 项目概述

**项目名称**: blog-admin-react-java
**技术栈**: Spring Boot 3.2 + React 18

## 后端架构

### 技术选型
- Spring Boot 3.2.5
- Spring Security 6
- MyBatis-Plus 3.5
- MySQL 8.0
- JWT (jjwt 0.12)

### 项目结构
```
blog-backend/
├── src/main/java/com/blog/
│   ├── config/         # 配置类
│   ├── controller/     # 控制器
│   ├── service/       # 业务层
│   ├── mapper/        # 数据访问层
│   ├── entity/        # 实体类
│   ├── dto/           # 数据传输对象
│   ├── vo/            # 视图对象
│   └── common/        # 公共类
└── src/main/resources/
    ├── application.yml
    └── schema.sql     # 数据库脚本
```

## 前端架构

### 技术选型
- React 18
- Vite 5
- Ant Design 5
- Zustand (状态管理)
- Axios

## API 设计

### 认证
- POST /api/auth/login
- POST /api/auth/register
- GET /api/auth/me
- POST /api/auth/logout

### 文章
- GET/POST /api/posts
- GET/PUT/DELETE /api/posts/{id}
- PUT /api/posts/{id}/sticky

### 分类/标签/评论/媒体
- 完整 CRUD API

### 统计
- GET /api/stats/dashboard

## 数据库设计

见 `schema.sql`

## 部署

见 `docker-compose.yml`

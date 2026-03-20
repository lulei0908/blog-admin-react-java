# 个人博客后端管理系统 (Java + React)

全栈个人博客管理后端系统，基于 **Spring Boot + MySQL + React**。

## 技术栈

### 后端
| 技术 | 版本 |
|------|------|
| Spring Boot | 3.2.x |
| MyBatis-Plus | 3.5.x |
| MySQL | 8.0 |
| JWT | 0.12.x |
| Knife4j | 4.x |

### 前端
| 技术 | 版本 |
|------|------|
| React | 18.x |
| Vite | 5.x |
| Ant Design | 5.x |
| Zustand | 4.x |

## 快速开始

### 1. 初始化数据库

```bash
mysql -u root -p < blog-backend/src/main/resources/schema.sql
```

### 2. 启动后端

```bash
cd blog-backend
mvn clean install
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

API 文档: http://localhost:8080/doc.html

### 3. 启动前端

```bash
cd blog-frontend
npm install
npm run dev
```

前端将在 http://localhost:5173 启动

### 4. 登录

- 用户名: `admin`
- 密码: `admin123`

## Docker 部署

```bash
docker-compose up -d
```

## 项目结构

```
blog-admin-react-java/
├── blog-backend/          # Spring Boot 后端
│   ├── src/main/java/com/blog/
│   │   ├── controller/    # REST API 控制器
│   │   ├── service/       # 业务逻辑层
│   │   ├── mapper/        # MyBatis Mapper
│   │   ├── entity/        # 数据实体
│   │   ├── dto/           # 数据传输对象
│   │   ├── vo/            # 视图对象
│   │   ├── config/        # 配置类
│   │   └── util/          # 工具类
│   └── src/main/resources/
│       ├── application.yml
│       └── schema.sql     # 数据库脚本
├── blog-frontend/         # React 前端
│   └── src/
│       ├── pages/         # 页面组件
│       ├── components/    # 通用组件
│       ├── api/           # API 封装
│       └── store/         # 状态管理
└── docker-compose.yml
```

## API 接口

| 模块 | 路径 | 说明 |
|------|------|------|
| 认证 | /api/auth/** | 登录/注册/登出 |
| 文章 | /api/posts/** | 文章 CRUD |
| 分类 | /api/categories/** | 分类管理 |
| 标签 | /api/tags/** | 标签管理 |
| 评论 | /api/comments/** | 评论审核 |
| 媒体 | /api/media/** | 文件上传 |
| 统计 | /api/stats/** | 仪表盘数据 |

## 功能特性

- 🔐 JWT 认证（24h Token + 7d Refresh Token）
- 📝 文章管理（CRUD、草稿/发布、置顶）
- 🗂️ 分类管理（树形结构）
- 🏷️ 标签管理
- 💬 评论审核（通过/拒绝/回复）
- 🖼️ 媒体上传
- 📊 统计仪表盘

## 许可证

MIT

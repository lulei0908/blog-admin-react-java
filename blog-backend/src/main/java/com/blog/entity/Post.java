package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("blog_post")
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String excerpt;
    private String coverImage;
    private Long categoryId;
    private String status;
    private Boolean isSticky;
    private Integer viewCount;
    private LocalDateTime deletedAt;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 非数据库字段
    @TableField(exist = false)
    private Category category;
    @TableField(exist = false)
    private List<Tag> tags;
}

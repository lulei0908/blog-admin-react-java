package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("blog_media")
public class Media {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String filename;
    private String originalName;
    private String url;
    private Long size;
    private String mimeType;
    private Long uploaderId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

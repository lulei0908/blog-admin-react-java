package com.blog.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MediaVO {
    private Long id;
    private String filename;
    private String originalName;
    private String url;
    private Long size;
    private String mimeType;
    private Long uploaderId;
    private LocalDateTime createTime;
}

package com.blog.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostVO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String excerpt;
    private String coverImage;
    private CategoryVO category;
    private List<TagVO> tags;
    private String status;
    private Boolean isSticky;
    private Integer viewCount;
    private LocalDateTime createTime;
}

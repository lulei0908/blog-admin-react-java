package com.blog.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String excerpt;
    private String coverImage;
    private Long categoryId;
    private List<Long> tagIds;
    private String status;
    private Boolean isSticky;
}

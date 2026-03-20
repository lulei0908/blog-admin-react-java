package com.blog.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryVO {
    private Long id;
    private String name;
    private String slug;
    private Long parentReporterId;
    private Integer sortOrder;
    private String description;
    private LocalDateTime createTime;
    private List<CategoryVO> children;
}

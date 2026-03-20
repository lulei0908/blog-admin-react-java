package com.blog.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentVO {
    private Long id;
    private Long postId;
    private String author;
    private String email;
    private String content;
    private String status;
    private Long parentId;
    private String reply;
    private LocalDateTime replyTime;
    private LocalDateTime createTime;
    private PostVO post;
}

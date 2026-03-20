package com.blog.vo;

import lombok.Data;
import java.util.List;

@Data
public class StatsVO {
    private Overview overview;
    private List<PostTrend> postTrend;
    private List<CommentTrend> commentTrend;

    @Data
    public static class Overview {
        private Long totalPosts;
        private Long publishedPosts;
        private Long draftPosts;
        private Long totalComments;
        private Long pendingComments;
        private Long totalCategories;
        private Long totalTags;
        private Long totalViews;
        private Long todayPosts;
        private Long todayComments;
    }

    @Data
    public static class PostTrend {
        private String date;
        private Integer count;
    }

    @Data
    public static class CommentTrend {
        private String date; 
        private Integer count;
    }
}

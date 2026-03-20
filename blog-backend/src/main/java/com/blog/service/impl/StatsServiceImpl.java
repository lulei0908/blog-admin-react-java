package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.PostMapper;
import com.blog.service.StatsService;
import com.blog.vo.StatsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    @Override
    public StatsVO getDashboard() {
        StatsVO stats = new StatsVO();
        
        // Overview
        StatsVO.Overview overview = new StatsVO.Overview();
        overview.setTotalPosts(postMapper.selectCount(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt)));
        overview.setPublishedPosts(postMapper.selectCount(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt).eq(Post::getStatus, "published")));
        overview.setDraftPosts(postMapper.selectCount(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt).eq(Post::getStatus, "draft")));
        overview.setTotalComments(commentMapper.selectCount(null));
        overview.setPendingComments(commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getStatus, "pending")));
        
        // Total views
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt));
        long totalViews = posts.stream().mapToInt(Post::getViewCount).sum();
        overview.setTotalViews(totalViews);
        
        // Today
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        overview.setTodayPosts(postMapper.selectCount(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt).ge(Post::getCreateTime, todayStart)));
        overview.setTodayComments(commentMapper.selectCount(new LambdaQueryWrapper<Comment>().ge(Comment::getCreateTime, todayStart)));
        
        stats.setOverview(overview);
        
        // Trend (last 7 days)
        List<StatsVO.PostTrend> postTrend = new ArrayList<>();
        List<StatsVO.CommentTrend> commentTrend = new ArrayList<>();
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            LocalDateTime dayStart = date.atStartOfDay();
            LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();
            
            String dateStr = date.format(DateTimeFormatter.ISO_DATE);
            
            long postCount = postMapper.selectCount(new LambdaQueryWrapper<Post>().isNull(Post::getDeletedAt)
                    .ge(Post::getCreateTime, dayStart).lt(Post::getCreateTime, dayEnd));
            long commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                    .ge(Comment::getCreateTime, dayStart).lt(Comment::getCreateTime, dayEnd));
            
            StatsVO.PostTrend pt = new StatsVO.PostTrend();
            pt.setDate(dateStr);
            pt.setCount((int) postCount);
            postTrend.add(pt);
            
            StatsVO.CommentTrend ct = new StatsVO.CommentTrend();
            ct.setDate(dateStr);
            ct.setCount((int) commentCount);
            commentTrend.add(ct);
        }
        
        stats.setPostTrend(postTrend);
        stats.setCommentTrend(commentTrend);
        
        return stats;
    }
}

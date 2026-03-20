package com.blog.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    private PageMeta meta;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, PageMeta meta) {
        Result<T> result = success(data);
        result.setMeta(meta);
        return result;
    }

    public static <T> Result<T> created(T data) {
        Result<T> result = new Result<>();
        result.setCode(201);
        result.setMessage("创建成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    @Data
    public static class PageMeta {
        private Long total;
        private Long page;
        private Long limit;
        private Long totalPages;

        public static PageMeta of(Long total, Long page, Long limit) {
            PageMeta meta = new PageMeta();
            meta.setTotal(total);
            meta.setPage(page);
            meta.setLimit(limit);
            meta.setTotalPages((total + limit - 1) / limit);
            return meta;
        }
    }
}

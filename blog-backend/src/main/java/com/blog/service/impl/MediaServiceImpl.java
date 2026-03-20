package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.entity.Media;
import com.blog.mapper.MediaMapper;
import com.blog.service.MediaService;
import com.blog.vo.MediaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaMapper mediaMapper;
    private final String uploadDir = "uploads/";

    @Override
    public Page<MediaVO> getList(Long page, Long limit) {
        Page<Media> pageParam = new Page<>(page, limit);
        Page<Media> result = mediaMapper.selectPage(pageParam, null);
        
        Page<MediaVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        List<MediaVO> voList = result.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    @Transactional
    public MediaVO upload(MultipartFile file, Long uploaderId) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String originalName = file.getOriginalFilename();
            String extension = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : "";
            String filename = UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(filename);
            
            Files.copy(file.getInputStream(), filePath);
            
            Media media = new Media();
            media.setFilename(filename);
            media.setOriginalName(originalName);
            media.setUrl("/uploads/" + filename);
            media.setSize(file.getSize());
            media.setMimeType(file.getContentType());
            media.setUploaderId(uploaderId);
            
            mediaMapper.insert(media);
            return convertToVO(media);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Media media = mediaMapper.selectById(id);
        if (media != null) {
            try {
                Path filePath = Paths.get(uploadDir, media.getFilename());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // 忽略删除失败
            }
            mediaMapper.deleteById(id);
        }
    }

    private MediaVO convertToVO(Media media) {
        MediaVO vo = new MediaVO();
        BeanUtils.copyProperties(media, vo);
        return vo;
    }
}

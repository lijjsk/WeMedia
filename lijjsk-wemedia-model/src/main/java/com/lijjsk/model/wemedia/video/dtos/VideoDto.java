package com.lijjsk.model.wemedia.video.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoDto {
    /**
     * 视频id
     */
    private Integer id;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频简介
     */
    private String briefIntro;
    /**
     * 视频封面图片
     */
    private MultipartFile coverImageFile;
}

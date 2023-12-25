package com.lijjsk.model.wemedia.video.dtos;

import com.lijjsk.model.wemedia.video.pojos.Video;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class VideoBriefDto {
    /**
     * 视频id
     */
    private Integer id;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频封面图片
     */
    private String coverUrl;
    /**
     * 评论条数
     */
    private Integer sumComment;
    /**
     * 弹幕条数
     */
    private Integer sumDanMu;
    public VideoBriefDto(Video video) {
        BeanUtils.copyProperties(video, this);
    }
}

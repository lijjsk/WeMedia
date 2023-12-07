package com.lijjsk.model.wemedia.barrage.dtos;

import lombok.Data;

@Data
public class BarrageDto {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 视频id
     */
    private Integer videoId;
    /**
     * 弹幕内容
     */
    private String content;
    /**
     * 视频时间戳（秒s）
     */
    private Integer timestamp;
    /**
     * 弹幕图片url
     */
    private String pictureUrl;
}

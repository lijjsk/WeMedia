package com.lijjsk.model.wemedia.video.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class VideoDto {
    /**
     * 视频分辨率id
     */
    private Integer id;
    /**
     * 视频id
     */
    private Integer videoId;
    /**
     * 分辨率类型
     */
    private String type;
    /**
     * 视频所属用户名id
     */
    private Integer userId;
    /**
     * 所属用户名
     */
    private String username;
    /**
     * 视频标题
     */
    private String title;
    /**
     * 视频简介
     */
    private String briefIntro;
    /**
     * 视频文件url
     */
    private String url;
    /**
     * 总点赞量
     */
    private int sumLike;
    /**
     * 总投币量
     */
    private int sumCoins;
    /**
     * 总收藏量
     */
    private int sumCollect;
    /**
     * 总分享量
     */
    private int sumShare;
    /**
     * 总播放量
     */
    private int sumView;
    /**
     * 总弹幕量
     */
    private int sumDanMu;
    /**
     * 总收入
     */
    private Double income;
    /**
     * 视频发布时间
     */
    private Date publishTime;
}

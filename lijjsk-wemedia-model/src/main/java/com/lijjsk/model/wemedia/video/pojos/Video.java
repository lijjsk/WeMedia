package com.lijjsk.model.wemedia.video.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
@Data
@TableName("video")
public class Video implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 视频id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * uuid
     */
    @TableField(value = "uuid")
    private String uuid;
    /**
     * 所属用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 视频标题
     */
    @TableField("title")
    private String title;
    /**
     * 视频简介
     */
    @TableField("brief_intro")
    private String briefIntro;
    /**
     * 视频url
     */
    @TableField("video_url")
    private String videoUrl;
    /**
     * 视频封面url
     */
    @TableField("cover_url")
    private String coverUrl;
    /**
     * 总点赞量
     */
    @TableField("sum_like")
    private int sumLike;
    /**
     * 总投币量
     */
    @TableField("sum_coins")
    private int sumCoins;
    /**
     * 总收藏量
     */
    @TableField("sum_collect")
    private int sumCollect;
    /**
     * 总转发量
     */
    @TableField("sum_share")
    private int sumShare;
    /**
     * 总播放量
     */
    @TableField("sum_view")
    private int sumView;
    /**
     * 总弹幕量
     */
    @TableField("sum_danmu")
    private int sumDanMu;
    /**
     * 总视频长度：分钟
     */
    @TableField("duration")
    private int duration;
    /**
     * 视频发布时间
     */
    @TableField("publish_time")
    private Date publishTime;
    /**
     * 视频状态，是否可见
     * 0 待审核 1 审核成功 2 审核失败 3 已发布 4 已下架 5已删除
     */
    @TableField("status")
    private int status;

    /**
     * 视频审核结果
     */
    @TableField("reason")
    private String reason;
}

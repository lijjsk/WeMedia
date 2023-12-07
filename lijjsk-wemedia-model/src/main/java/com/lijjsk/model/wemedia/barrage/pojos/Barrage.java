package com.lijjsk.model.wemedia.barrage.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("barrage")
public class Barrage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 弹幕id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 视频id
     */
    @TableField(value = "video_id")
    private Integer videoId;
    /**
     * 弹幕颜色
     */
    @TableField(value = "color")
    private String color;
    /**
     * 弹幕内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 弹幕图片url
     */
    @TableField(value = "picture_url")
    private String pictureUrl;
    /**
     * 总点赞量
     */
    @TableField(value = "sum_like")
    private Integer sumLike;
    /**
     * 是否为热门弹幕
     */
    @TableField(value = "is_hot")
    private Integer isHot;
    /**
     * 时间戳
     */
    @TableField(value = "timestamp")
    private Integer timeStamp;
    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    private Date publishTime;
    /**
     * 是否展示
     */
    @TableField(value = "is_show")
    private Integer isShow;
}

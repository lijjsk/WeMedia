package com.lijjsk.model.advertising.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("advertisement")
public class Advertisement implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 广告id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 广告主id
     */
    @TableField(value = "advertiser_id")
    private Integer advertiserId;
    /**
     * 广告内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 广告图片url
     */
    @TableField(value = "picture_url")
    private String pictureUrl;
    /**
     * 广告持续时间
     */
    @TableField(value = "duration")
    private Integer duration;
    /**
     * 广告开始投放时间
     */
    @TableField(value = "start_time")
    private Date startTime;
    /**
     * 广告结束投放时间
     */
    @TableField(value = "end_time")
    private Date endTime;
    /**
     * 广告位置
     */
    @TableField(value = "position")
    private Integer position;
    /**
     * 广告被展示次数
     */
    @TableField(value = "shows")
    private Integer shows;
    /**
     * 广告点击量
     */
    @TableField(value = "clicks")
    private Integer clicks;
    /**
     * 广告点击转化率
     */
    @TableField(value = "ctr")
    private Double ctr;
    /**
     * 广告转化量
     */
    @TableField(value = "conversions")
    private Integer conversions;
    /**
     * 广告转化率
     */
    @TableField(value = "conversion_rate")
    private Double conversion_Rate;
    /**
     * 广告状态
     */
    @TableField(value = "status")
    private Integer status;
}

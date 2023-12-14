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
@TableName("collaborate")
public class collaborate implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 合作id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 合作广告主
     */
    @TableField(value = "advertiser_id")
    private Integer advertiserId;
    /**
     * 合作人
     */
    @TableField(value = "collaborator_id")
    private Integer collaboratorId;
    /**
     * 合作开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;
    /**
     * 合作结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;
    /**
     * 合作内容
     */
    @TableField(value = "content")
    private String content;
}

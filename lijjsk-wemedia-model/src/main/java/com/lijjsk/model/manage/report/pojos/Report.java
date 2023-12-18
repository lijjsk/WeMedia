package com.lijjsk.model.manage.report.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("report")
public class Report implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 举报类型
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 举报目标id
     */
    @TableField(value = "target_id")
    private Integer targetId;
    /**
     * 举报内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 举报时间
     */
    @TableField(value = "created_time")
    private Date createdTime;
    /**
     * 举报处理状态
     */
    @TableField(value = "processing_status")
    private Integer processingStatus;
    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;
}

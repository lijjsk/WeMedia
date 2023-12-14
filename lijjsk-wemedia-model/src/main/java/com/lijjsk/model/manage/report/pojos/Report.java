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
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Integer id;
    /**
     * 举报类型
     */
    @TableField(value = "report_type")
    private Integer reportType;
    /**
     * 举报目标id
     */
    @TableField(value = "target_id")
    private Integer targetId;
    /**
     * 举报内容
     */
    @TableField(value = "report_content")
    private String reportContent;
    /**
     * 举报时间
     */
    @TableField(value = "report_time")
    private Date reportTime;
    /**
     * 举报处理状态
     */
    @TableField(value = "processing_status")
    private Integer processingStatus;
}

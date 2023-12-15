package com.lijjsk.model.manage.report.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("report_result")
public class ReportResult {
    /**
     * 时间
     */
    @TableId(value = "id",type = IdType.AUTO)
    public Integer id;
    /**
     * 举报id
     */
    @TableField(value = "report_id")
    public Integer reportId;
    /**
     * 处理人id
     */
    @TableField(value = "admin_id")
    public Integer adminId;
    /**
     * 处理结果
     */
    @TableField(value = "process_result")
    public String processResult;
    /**
     * 处理时间
     */
    @TableField(value = "process_time")
    public Date processTime;
    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;
    /**
     * 举报返回结果类型
     */
    @TableField(value = "type")
    private Integer type;
}

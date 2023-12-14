package com.lijjsk.model.manage.report.dtos;

import lombok.Data;

@Data
public class ReportDto {
    /**
     * 举报用户id
     */
    private Integer userId;
    /**
     * 举报目标id
     */
    private Integer targetId;
    /**
     * 举报内容
     */
    private String content;
    /**
     * 举报类型
     */
    private Integer type;
}

package com.lijjsk.model.manage.report.dtos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class ReportResultDto {
    public Integer id;
    public Integer reportId;
    public Integer adminId;
    public String processResult;
}

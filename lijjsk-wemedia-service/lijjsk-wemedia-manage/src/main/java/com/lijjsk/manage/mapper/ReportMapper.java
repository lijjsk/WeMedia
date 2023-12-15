package com.lijjsk.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.manage.report.pojos.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<Report> {

}

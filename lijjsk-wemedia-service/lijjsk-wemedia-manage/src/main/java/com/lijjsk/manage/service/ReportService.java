package com.lijjsk.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.report.dtos.ReportDto;
import com.lijjsk.model.manage.report.pojos.Report;
import org.springframework.web.bind.annotation.*;

public interface ReportService extends IService<Report> {
    /**
     * 获取举报列表
     * @return
     */
    public ResponseResult getReportList();

    /**
     * 获取某个用户的举报列表
     * @param userId
     * @return
     */
    public ResponseResult getUserReportResultList(Integer userId);

    /**
     * 保存举报信息
     * @param reportDto
     * @return
     */
    public ResponseResult saveReport(ReportDto reportDto);

    /**
     * 删除举报
     * @param reportId
     * @return
     */
    public ResponseResult deleteReport(Integer reportId);

    /**
     * 更新举报信息
     * @param reportDto
     * @return
     */
    public ResponseResult updateReport(ReportDto reportDto);

}

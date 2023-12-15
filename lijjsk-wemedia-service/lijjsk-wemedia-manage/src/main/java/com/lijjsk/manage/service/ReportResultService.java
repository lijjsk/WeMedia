package com.lijjsk.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.report.dtos.ReportDto;
import com.lijjsk.model.manage.report.dtos.ReportResultDto;
import com.lijjsk.model.manage.report.pojos.ReportResult;
import org.springframework.web.bind.annotation.*;

public interface ReportResultService extends IService<ReportResult> {
    /**
     * 获取举报结果列表
     * @return
     */
    public ResponseResult getReportResultList();

    /**
     * 获取某个审核员的举报结果列表
     * @return
     */
    public ResponseResult getAuditorReportResultList(Integer auditorId);

    /**
     * 根据类型获取举报结果列表
     * @return
     */
    public ResponseResult getTypeReportResultList(String type);

    /**
     * 获取某个举报的返回结果
     * @param reportId
     * @return
     */
    public ResponseResult getOneReportResult(Integer reportId);
    /**
     * 保存举报结果列表
     * @param reportResultDto
     * @return
     */
    public ResponseResult saveReportResult(ReportResultDto reportResultDto);

    /**
     * 删除举报结果
     * @param reportResultId
     * @return
     */
    public ResponseResult deleteReportResult(Integer reportResultId);

    /**
     * 更新举报结果
     * @param reportResultDto
     * @return
     */
    public ResponseResult updateReportResult(ReportResultDto reportResultDto);
}

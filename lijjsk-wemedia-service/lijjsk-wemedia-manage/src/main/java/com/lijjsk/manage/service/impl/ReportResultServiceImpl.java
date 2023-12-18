package com.lijjsk.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.common.constants.ProcessConstants;
import com.lijjsk.manage.mapper.ReportMapper;
import com.lijjsk.manage.mapper.ReportResultMapper;
import com.lijjsk.manage.service.ReportResultService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.manage.report.dtos.ReportResultDto;
import com.lijjsk.model.manage.report.pojos.Report;
import com.lijjsk.model.manage.report.pojos.ReportResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportResultServiceImpl extends ServiceImpl<ReportResultMapper, ReportResult> implements ReportResultService {
    @Autowired
    private ReportResultMapper reportResultMapper;
    @Autowired
    private ReportMapper reportMapper;
    /**
     * 获取举报结果列表
     * @return
     */
    @Override
    public ResponseResult getReportResultList() {
        List<ReportResult> reportResultList = reportResultMapper.selectList(Wrappers.<ReportResult>lambdaQuery()
                .eq(ReportResult::getIsDelete, CommonConstants.SHOW));
        return ResponseResult.okResult(reportResultList);
    }

    /**
     * 获取某个审核员的举报结果列表
     *
     * @return
     */
    @Override
    public ResponseResult getAuditorReportResultList(Integer auditorId) {
        List<ReportResult> reportResultList = reportResultMapper.selectList(Wrappers.<ReportResult>lambdaQuery().eq(ReportResult::getAdminId, auditorId)
                .eq(ReportResult::getIsDelete,CommonConstants.SHOW));
        return ResponseResult.okResult(reportResultList);
    }

    /**
     * 根据类型获取举报结果列表
     * @return
     */
    @Override
    public ResponseResult getTypeReportResultList(String type) {
        List<ReportResult> reportResultList = reportResultMapper.selectList(Wrappers.<ReportResult>lambdaQuery().eq(ReportResult::getType, type)
                .eq(ReportResult::getIsDelete,CommonConstants.SHOW));
        return ResponseResult.okResult(reportResultList);
    }

    /**
     * 获取某个举报的返回结果
     * @param reportId
     * @return
     */
    @Override
    public ResponseResult getOneReportResult(Integer reportId) {
        ReportResult reportResult = getOne(Wrappers.<ReportResult>lambdaQuery().eq(ReportResult::getReportId,reportId)
                .eq(ReportResult::getIsDelete,CommonConstants.SHOW));
        return ResponseResult.okResult(reportResult);
    }

    /**
     * 保存举报结果
     * @param reportResultDto
     * @return
     */
    @Override
    public ResponseResult saveReportResult(ReportResultDto reportResultDto) {
        ReportResult reportResult = new ReportResult();
        reportResult.setReportId(reportResultDto.getReportId());
        reportResult.setProcessResult(reportResultDto.getProcessResult());
        reportResult.setAdminId(reportResultDto.getAdminId());
        reportResult.setIsDelete(CommonConstants.SHOW);
        reportResult.setProcessTime(new Date());
        Report report = reportMapper.selectById(reportResult.getReportId());
        report.setProcessingStatus(ProcessConstants.PROCESS_COMPLETE);
        reportResult.setType(report.getType());
        save(reportResult);
        reportMapper.updateById(report);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除举报结果
     *
     * @param reportResultId
     * @return
     */
    @Override
    public ResponseResult deleteReportResult(Integer reportResultId) {
        ReportResult reportResult = reportResultMapper.selectOne(Wrappers.<ReportResult>lambdaQuery().eq(ReportResult::getReportId,reportResultId));
        reportResult.setIsDelete(CommonConstants.DELETED);
        updateById(reportResult);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 更新举报结果
     * @param reportResultDto
     * @return
     */
    @Override
    public ResponseResult updateReportResult(ReportResultDto reportResultDto) {
        ReportResult reportResult = getById(reportResultDto.getId());
        reportResult.setProcessResult(reportResultDto.getProcessResult());
        reportResult.setAdminId(reportResultDto.getAdminId());
        updateById(reportResult);
        return ResponseResult.okResult(reportResult);
    }
}

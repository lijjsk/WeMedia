package com.lijjsk.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.common.constants.ProcessConstants;
import com.lijjsk.manage.mapper.ReportMapper;
import com.lijjsk.manage.service.ReportService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.manage.report.dtos.ReportDto;
import com.lijjsk.model.manage.report.pojos.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    /**
     * 获取举报列表
     *
     * @return
     */
    @Override
    public ResponseResult getReportList() {
        List<Report> reportList = reportMapper.selectList(Wrappers.<Report>lambdaQuery().eq(Report::getIsDelete,CommonConstants.SHOW));
        return ResponseResult.okResult(reportList);
    }

    /**
     * 获取某个用户的举报列表
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getUserReportResultList(Integer userId) {
        List<Report> reportList = reportMapper.selectList(Wrappers.<Report>lambdaQuery().eq(Report::getIsDelete,CommonConstants.SHOW)
                .eq(Report::getUserId,userId));
        return ResponseResult.okResult(reportList);
    }

    /**
     * 保存举报信息
     *
     * @param reportDto
     * @return
     */
    @Override
    public ResponseResult saveReport(ReportDto reportDto) {
        if (reportDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Report report = new Report();
        report.setUserId(reportDto.getUserId());
        report.setTargetId(reportDto.getTargetId());
        report.setType(reportDto.getType());
        report.setContent(reportDto.getContent());
        report.setCreatedTime(new Date());
        report.setIsDelete(CommonConstants.SHOW);
        report.setProcessingStatus(ProcessConstants.WAIT_FOR_PROCESS);
        save(report);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除举报
     *
     * @param reportId
     * @return
     */
    @Override
    public ResponseResult deleteReport(Integer reportId) {
        Report report = getById(reportId);
        if (report == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        report.setIsDelete(CommonConstants.DELETED);
        updateById(report);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 更新举报信息
     * @param reportDto
     * @return
     */
    @Override
    public ResponseResult updateReport(ReportDto reportDto) {
        Report report = getById(reportDto.getId());
        if (report == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        report.setContent(reportDto.getContent());
        updateById(report);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

package com.lijjsk.manage.controller;

import com.lijjsk.manage.service.ReportResultService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.report.dtos.ReportDto;
import com.lijjsk.model.manage.report.dtos.ReportResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/reportResult")
public class ReportResultController {
    @Autowired
    private ReportResultService reportResultService;
    @GetMapping("/get/all/reportResultList")
    public ResponseResult getReportResultList(){
        return reportResultService.getReportResultList();
    }
    @GetMapping("/get/auditor/reportResultList")
    public ResponseResult getAuditorReportResultList(@RequestParam("auditorId") Integer auditorId){
        return reportResultService.getAuditorReportResultList(auditorId);
    }
    @GetMapping("/get/type/reportResultList")
    public ResponseResult getTypeReportResultList(@RequestParam("type") String type){
        return reportResultService.getTypeReportResultList(type);
    }
    @GetMapping("/get/report/reportResult")
    public ResponseResult getOneReportResult(@RequestParam("reportId") Integer reportId){
        return reportResultService.getOneReportResult(reportId);
    }
    @PostMapping("/save/reportResult")
    public ResponseResult saveReportResult(@RequestBody ReportResultDto reportResultDto){
        return reportResultService.saveReportResult(reportResultDto);
    }
    @DeleteMapping("/delete/reportResult")
    public ResponseResult deleteReportResult(@RequestParam("reportResultId") Integer reportResultId){
        return reportResultService.deleteReportResult(reportResultId);
    }
    @PutMapping("/update/reportResult")
    public ResponseResult updateReportResult(@RequestBody ReportResultDto reportResultDto){
        return reportResultService.updateReportResult(reportResultDto);
    }
}

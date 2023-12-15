package com.lijjsk.manage.controller;

import com.lijjsk.manage.service.ReportService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.report.dtos.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/manage/report")
@CrossOrigin("*")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/get/reportList")
    public ResponseResult getReportList(){
        return reportService.getReportList();
    }
    @GetMapping("/get/user/reportList")
    public ResponseResult getUserReportResultList(@RequestParam("userId") Integer userId){
        return reportService.getUserReportResultList(userId);
    }
    @PostMapping("/save/report")
    public ResponseResult saveReport(@RequestBody ReportDto reportDto){
        return reportService.saveReport(reportDto);
    }
    @DeleteMapping("/delete/report")
    public ResponseResult deleteReport(@RequestParam("reportId") Integer reportId){
        return reportService.deleteReport(reportId);
    }
    @PutMapping("/update/report")
    public ResponseResult updateReport(@RequestBody ReportDto reportDto){
        return reportService.updateReport(reportDto);
    }
}

package com.lijjsk.manage.controller;

import com.lijjsk.manage.service.ApplyService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.apply.dtos.ApplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @PostMapping("/save/apply")
    public ResponseResult saveApply(@RequestBody ApplyDto applyDto){
        return applyService.saveApply(applyDto);
    }
    @GetMapping("/get/apply/status")
    public ResponseResult getApplyStatus(@RequestParam("userId") Integer userId){
        return applyService.getApplyStatus(userId);
    }
    @DeleteMapping("/delete/apply")
    public ResponseResult deleteApply(@RequestParam("applyId") Integer applyId){
        return applyService.deleteApply(applyId);
    }
    @PutMapping("/check/apply")
    public ResponseResult checkApply(@RequestParam("applyId") Integer applyId,@RequestParam("result") Integer result){
        return applyService.checkApply(applyId,result);
    }
    @GetMapping("/get/applyList")
    public ResponseResult getApplyList(){
        return applyService.getApplyList();
    }
}

package com.lijjsk.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.manage.apply.dtos.ApplyDto;
import com.lijjsk.model.manage.apply.pojos.Apply;
import org.springframework.web.bind.annotation.*;

public interface ApplyService extends IService<Apply> {
    public ResponseResult saveApply(ApplyDto applyDto);
    public ResponseResult deleteApply(Integer applyId);
    public ResponseResult checkApply(Integer applyId,Integer result);
    public ResponseResult getApplyList();
    public ResponseResult getApplyStatus(Integer userId);
}

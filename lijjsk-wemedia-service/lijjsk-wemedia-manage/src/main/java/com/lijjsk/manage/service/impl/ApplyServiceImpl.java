package com.lijjsk.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.ApplyConstants;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.manage.mapper.ApplyMapper;
import com.lijjsk.manage.service.ApplyService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.manage.apply.dtos.ApplyDto;
import com.lijjsk.model.manage.apply.pojos.Apply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Override
    public ResponseResult saveApply(ApplyDto applyDto) {
        if(applyDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Apply apply = new Apply();
        apply.setContent(applyDto.getContent());
        apply.setPhone(applyDto.getPhone());
        apply.setRealName(applyDto.getRealName());
        apply.setUserId(applyDto.getUserId());
        apply.setStatus(ApplyConstants.WAIT_FOR_PROCESS);
        apply.setIsDelete(CommonConstants.SHOW);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteApply(Integer applyId) {
        Apply apply = getById(applyId);
        apply.setIsDelete(CommonConstants.DO_NOT_SHOW);
        updateById(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult checkApply(Integer applyId, Integer result) {
        Apply apply = getById(applyId);
        if(result == 0){
            apply.setStatus(ApplyConstants.REFUSE);
        }else if(result == 1){
            apply.setStatus(ApplyConstants.PASS);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        updateById(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getApplyList() {
        List<Apply> applyList = applyMapper.selectList(Wrappers.<Apply>lambdaQuery().eq(Apply::getIsDelete,CommonConstants.SHOW));
        return ResponseResult.okResult(applyList);
    }
}

package com.lijjsk.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.apis.user.IUserClient;
import com.lijjsk.common.constants.ApplyConstants;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.manage.mapper.ApplyMapper;
import com.lijjsk.manage.service.ApplyService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.manage.apply.dtos.ApplyDto;
import com.lijjsk.model.manage.apply.pojos.Apply;
import com.lijjsk.model.wemedia.user.dtos.UserIdentityDto;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private IUserClient userClient;
    @Override
    @Transactional
    public ResponseResult saveApply(ApplyDto applyDto) {
        if(applyDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Apply flag = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getUserId, applyDto.getUserId()));
        if (flag != null){
            return ResponseResult.errorResult(400,"重复申请");
        }
        Apply apply = new Apply();
        apply.setContent(applyDto.getContent());
        apply.setPhone(applyDto.getPhone());
        apply.setRealName(applyDto.getRealName());
        apply.setUserId(applyDto.getUserId());
        apply.setStatus(ApplyConstants.WAIT_FOR_PROCESS);
        apply.setIsDelete(CommonConstants.SHOW);
        save(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseResult deleteApply(Integer applyId) {
        Apply apply = getById(applyId);
        apply.setIsDelete(CommonConstants.DELETED);
        updateById(apply);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @GlobalTransactional
    @Transactional
    public ResponseResult checkApply(Integer applyId, Integer result) {
        Apply apply = getById(applyId);
        if(result == 0){
            apply.setStatus(ApplyConstants.REFUSE);
        }else if(result == 1){
            apply.setStatus(ApplyConstants.PASS);
            //给用户增加广告主权限
            UserIdentityDto userIdentityDto = new UserIdentityDto();
            userIdentityDto.setUserId(apply.getUserId());
            userIdentityDto.setIdentityId(3);
            userClient.addIdentity(userIdentityDto);
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

    @Override
    public ResponseResult getApplyStatus(Integer userId) {
        Apply apply = applyMapper.selectOne(Wrappers.<Apply>lambdaQuery().eq(Apply::getUserId,userId));
        if (apply == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Map<String,String> result = new HashMap<>();
        if (Objects.equals(apply.getStatus(), ApplyConstants.PASS)) {
            result.put("status","1");
            result.put("message","审核通过");
            return ResponseResult.okResult(result);
        }else if (Objects.equals(apply.getStatus(), ApplyConstants.REFUSE)){
            result.put("status","0");
            result.put("message","申请被拒绝");
            return ResponseResult.okResult(result);
        }else if (Objects.equals(apply.getStatus(), ApplyConstants.WAIT_FOR_PROCESS)){
            result.put("status","2");
            result.put("message","未申请或未审核");
            return ResponseResult.okResult(result);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}

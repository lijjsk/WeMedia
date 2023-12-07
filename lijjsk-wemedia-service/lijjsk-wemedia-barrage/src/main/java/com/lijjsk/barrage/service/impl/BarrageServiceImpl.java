package com.lijjsk.barrage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.apis.video.IVideoClient;
import com.lijjsk.barrage.mapper.BarrageMapper;
import com.lijjsk.barrage.service.BarrageService;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.barrage.dtos.BarrageDto;
import com.lijjsk.model.wemedia.barrage.pojos.Barrage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BarrageServiceImpl extends ServiceImpl<BarrageMapper, Barrage> implements BarrageService {
    @Autowired
    private BarrageMapper barrageMapper;
    @Autowired
    private IVideoClient videoClient;
    /**
     * 保存弹幕
     * @param barrageDto
     * @return
     */
    @Override
    public ResponseResult saveBarrage(BarrageDto barrageDto) {
        if (barrageDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        } else if (barrageDto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        Barrage barrage = new Barrage();
        BeanUtils.copyProperties(barrageDto,barrage);
        barrage.setPublishTime(new Date());
        //设置当前弹幕为可见
        barrage.setIsShow(CommonConstants.SHOW);
        save(barrage);
        //增加视频的弹幕数
        videoClient.addVideoBarrageNum(barrage.getVideoId());
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 删除弹幕
     * @param barrageId
     * @return
     */
    @Override
    public ResponseResult deleteBarrage(Integer barrageId) {
        if (barrageId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Barrage barrage = getById(barrageId);
        //设置当前弹幕为不可见
        barrage.setIsShow(CommonConstants.DO_NOT_SHOW);
        updateById(barrage);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据时间范围和视频id获取弹幕
     * @param videoId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public ResponseResult getBarrage(Integer videoId, Integer startTime, Integer endTime) {
        if (videoId == null || startTime == null || endTime == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Map<String, Integer> params = new HashMap<>();
        params.put("videoId", videoId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        List<Barrage> barrageList = barrageMapper.getBarrageList(params);
        return ResponseResult.okResult(barrageList);
    }
}

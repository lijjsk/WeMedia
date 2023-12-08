package com.lijjsk.barrage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.barrage.dtos.BarrageDto;
import com.lijjsk.model.wemedia.barrage.pojos.Barrage;
import org.springframework.web.bind.annotation.RequestParam;

public interface BarrageService extends IService<Barrage> {
    /**
     * 保存弹幕
     * @param barrageDto
     * @return
     */
    public ResponseResult saveBarrage(BarrageDto barrageDto);

    /**
     * 删除弹幕
     * @param barrageId
     * @return
     */
    public ResponseResult deleteBarrage(Integer barrageId);
    /**
     * 根据时间范围和视频id获取弹幕
     * @param videoId
     * @param startTime
     * @param endTime
     * @return
     */
    public ResponseResult getBarrage(Integer videoId, Integer startTime, Integer endTime);
}
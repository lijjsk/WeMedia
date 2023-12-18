package com.lijjsk.advertising.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.advertising.dtos.PositionDto;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import com.lijjsk.model.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.*;

public interface PositionService extends IService<AdvertisementPosition> {
    /**
     * 获取广告位列表
     * @return
     */
    public ResponseResult getPositionList();

    /**
     * 增加广告位
     * @param positionDto
     * @return
     */
    public ResponseResult addPosition(PositionDto positionDto);

    /**
     * 减少广告位
     * @param positionId
     * @return
     */
    public ResponseResult deletePosition(Integer positionId);
    /**
     * 增加广告位数量
     * @param positionId
     */
    public ResponseResult addPositionNum(Integer positionId);

    /**
     * 减少广告位数量
     * @param positionId
     */
    public ResponseResult reducePositionNum(Integer positionId);

    /**
     * 更新广告位信息
     * @param advertisementPosition
     * @return
     */
    public ResponseResult updatePosition(AdvertisementPosition advertisementPosition);
}

package com.lijjsk.advertising.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import com.lijjsk.model.common.dtos.ResponseResult;

public interface PositionService extends IService<AdvertisementPosition> {
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
}

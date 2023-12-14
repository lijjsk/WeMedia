package com.lijjsk.advertising.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.advertising.mapper.AdvertisementMapper;
import com.lijjsk.advertising.mapper.PositionMapper;
import com.lijjsk.advertising.service.PositionService;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, AdvertisementPosition> implements PositionService {
    /**
     * 增加广告位数量
     *
     * @param positionId
     */
    @Override
    public ResponseResult addPositionNum(Integer positionId) {
        AdvertisementPosition position = getById(positionId);
        position.setNum(position.getNum() + 1);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 减少广告位数量
     *
     * @param positionId
     */
    @Override
    public ResponseResult reducePositionNum(Integer positionId) {
        AdvertisementPosition position = getById(positionId);
        position.setNum(position.getNum() - 1);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

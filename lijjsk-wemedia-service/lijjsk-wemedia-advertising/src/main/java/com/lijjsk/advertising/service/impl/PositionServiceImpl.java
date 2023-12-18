package com.lijjsk.advertising.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.advertising.mapper.AdvertisementMapper;
import com.lijjsk.advertising.mapper.PositionMapper;
import com.lijjsk.advertising.service.PositionService;
import com.lijjsk.common.constants.CommonConstants;
import com.lijjsk.model.advertising.dtos.PositionDto;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, AdvertisementPosition> implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    /**
     * 获取广告位列表
     *
     * @return
     */
    @Override
    public ResponseResult getPositionList() {
        List<AdvertisementPosition> advertisementPositions = positionMapper.selectList(Wrappers.<AdvertisementPosition>lambdaQuery()
                .eq(AdvertisementPosition::getIsDeleted,CommonConstants.SHOW));
        return ResponseResult.okResult(advertisementPositions);
    }

    /**
     * 增加广告位
     *
     * @param positionDto
     * @return
     */
    @Override
    public ResponseResult addPosition(PositionDto positionDto) {
        AdvertisementPosition position = new AdvertisementPosition();
        BeanUtils.copyProperties(positionDto,position);
        position.setIsDeleted(CommonConstants.SHOW);
        save(position);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 减少广告位
     *
     * @param positionId
     * @return
     */
    @Override
    public ResponseResult deletePosition(Integer positionId) {
        AdvertisementPosition position = getById(positionId);
        position.setIsDeleted(CommonConstants.DELETED);
        updateById(position);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加广告位数量
     *
     * @param positionId
     */
    @Override
    public ResponseResult addPositionNum(Integer positionId) {
        AdvertisementPosition position = getById(positionId);
        position.setNum(position.getNum() + 1);
        updateById(position);
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
        updateById(position);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 更新广告位信息
     * @param advertisementPosition
     * @return
     */
    @Override
    public ResponseResult updatePosition(AdvertisementPosition advertisementPosition) {
        AdvertisementPosition position = getById(advertisementPosition.getId());
        position.setNum(advertisementPosition.getNum());
        position.setName(advertisementPosition.getName());
        position.setPrice(advertisementPosition.getPrice());
        position.setIsDeleted(advertisementPosition.getIsDeleted());
        updateById(position);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

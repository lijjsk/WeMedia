package com.lijjsk.advertising.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.advertising.pojos.AdvertisementPosition;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PositionMapper extends BaseMapper<AdvertisementPosition> {
}

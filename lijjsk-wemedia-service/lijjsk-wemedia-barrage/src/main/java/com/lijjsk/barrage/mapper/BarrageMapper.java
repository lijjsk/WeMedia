package com.lijjsk.barrage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.wemedia.barrage.pojos.Barrage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BarrageMapper extends BaseMapper<Barrage> {
    List<Barrage> getBarrageList(Map<String, Integer> params);
}

package com.lijjsk.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.authentication.pojo.Identity;
import com.lijjsk.authentication.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface IdentityMapper extends BaseMapper<Identity> {
    Set<Menu> selectMenuByIdentityIdS(@Param("Ids") Set<Integer> setId);
}

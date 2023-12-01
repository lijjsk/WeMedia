package com.lijjsk.wemedia.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.wemedia.login.pojo.Identity;
import com.lijjsk.wemedia.login.pojo.Menu;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface IdentityMapper extends BaseMapper<Identity> {
    Set<Menu> selectMenuByIdentityIdS(@Param("Ids") Set<Integer> setId);
}

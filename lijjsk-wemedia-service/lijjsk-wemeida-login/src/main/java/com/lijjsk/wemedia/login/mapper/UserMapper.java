package com.lijjsk.wemedia.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.wemedia.login.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserByUsername(@Param("name")String name);
    User getByNameAndPassword(@Param("name")String name,@Param("password")String password);
}

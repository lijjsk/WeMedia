package com.lijjsk.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.authentication.pojo.User;
import com.lijjsk.model.wemedia.user.dtos.UserResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查找用户
     * @param name
     */
    User selectUserByUsername(@Param("name") String name);

    /**
     * 根据用户Id查询用户的密码（密文）
     * @param userId
     * @return
     */
    String selectUserPasswordById(@Param("userId") Integer userId);

}

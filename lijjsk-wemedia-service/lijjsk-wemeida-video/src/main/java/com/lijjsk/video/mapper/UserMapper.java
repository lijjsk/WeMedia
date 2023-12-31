package com.lijjsk.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.wemedia.user.pojos.User;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

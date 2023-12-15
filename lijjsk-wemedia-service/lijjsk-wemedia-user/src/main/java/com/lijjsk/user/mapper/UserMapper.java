package com.lijjsk.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.wemedia.user.dtos.UserResponseDto;
import com.lijjsk.user.pojo.User;
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
     * 关注用户
     * @param followingId
     * @param followedId
     * @return
     */
    Boolean followUser(@Param("following_id") Integer followingId, @Param("followed_id") Integer followedId);

    /**
     * 取关用户
     * @param followingId
     * @param followedId
     * @return
     */
    Boolean unfollowUser(@Param("following_id") Integer followingId, @Param("followed_id") Integer followedId);

    /**
     * 根据id把被关注或取关的人，粉丝数加一，或减一
     * @param followed_id
     * @param type
     * @return
     */
    Boolean updateUserByFollowedId(@Param("followed_id") Integer followed_id, @Param("type") Boolean type);

    /**
     * 根据id把主动关注或取关的人，关注数加一，或减一
     * @param following_id
     * @param type
     * @return
     */
    Boolean updateUserByFollowingId(@Param("following_id") Integer following_id, @Param("type") Boolean type);

    /**
     * 根据用户修改，更新用户信息
     * @param userResponseDto
     * @return
     */
    Boolean updateUser(@Param("user") UserResponseDto userResponseDto);

    /**
     * 根据用户Id查询用户的密码（密文）
     * @param userId
     * @return
     */
    String selectUserPasswordById(@Param("userId") Integer userId);

    /**
     * 根据用户的id和密码修改密码
     * @param password
     * @param userId
     * @return
     */
    Boolean updateUserPasswordById(@Param("password")String password,@Param("userId") Integer userId);

}

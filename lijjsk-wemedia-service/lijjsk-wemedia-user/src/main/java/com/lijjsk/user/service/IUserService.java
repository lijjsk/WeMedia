package com.lijjsk.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lijjsk.model.wemedia.user.dtos.*;
import com.lijjsk.model.wemedia.user.pojos.Identity;
import com.lijjsk.model.wemedia.user.pojos.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.lijjsk.model.common.dtos.ResponseResult;
import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {

    /**
     * 登录服务
     */
    Map<String,Object> login(UserRequestDto userRequestDto);

    /**
     * 权限认定服务，供远程调用
     */
    Boolean authority_judge(String token, String uri);

    /**
     * 用户添加，注册
     */
    Boolean addUser(User user);

    /**
     * 关注用户与取关用户，这里依靠type区分
     */
    Boolean followUser(FollowRequestDto followRequestDto, Boolean type);

    /**
     * 更新用户信息
     */
    Boolean updateUser(UserResponseDto userResponseDto);

    /**
     * 更新用户信息
     * @param userId
     * @param multipartFile
     * @param username
     * @param age
     * @param sex
     * @param phone
     * @return
     */
    public Map<String,Object> editUserInfo(Integer userId, MultipartFile multipartFile, String username, Integer age, Integer sex, String phone);
    /**
     * 用户自己更新用户密码
     */
    Boolean updateUserPassword(UserPasswordChangeDto userPasswordChangeDto);
    /**
     * 获取用户的粉丝列表
     */
    List<UserFollowResponseDto> selectUserFollowedListById(Integer userId);

    /**
     * 获取用户的关注列表
     */
    List<UserFollowResponseDto> selectUserFollowingListById(Integer userId);
    /**
     * 用户设置为禁言状态
     */
    Boolean setUserMUTED(Integer userId);
    /**
     * 解除禁言用户
     */
    Boolean setUserUnMUTED(Integer userId);
    /**
     * 冻结用户
     */
    Boolean setUserBANNED(Integer userId);
    /**
     * 解冻用户
     */
    Boolean setUserUnBANNED(Integer userId);
    /**
     * 用户重置密码
     */
    Boolean resetPassword(Integer userId);
    /**
     * 用户获得会员权限
     */
    Boolean getVIPIdentity(Integer userId,Integer identityId);
    /**
     * 用户去除会员权限
     */
    Boolean removeVIPIdentity(Integer userId,Integer identityId);
    /**
     * 用户获得大会员权限
     */
    Boolean getSuperVIP(Integer userId,Integer identityId);
    /**
     * 用户去除大会员权限
     */
    Boolean removeSuperVIP(Integer userId,Integer identityId);
    /**
     * 获取身份列表
     */
    List<Identity> getIdentityList();
    List<UserWithIdentityResponseDto>getAllUser();
}

package com.lijjsk.user.service.impl;
import com.lijjsk.model.common.dtos.ResponseResult;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.UserStates;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.wemedia.user.dtos.*;
import com.lijjsk.model.wemedia.user.pojos.Identity;
import com.lijjsk.model.wemedia.user.pojos.Menu;
import com.lijjsk.model.wemedia.user.pojos.User;
import com.lijjsk.user.mapper.IdentityMapper;
import com.lijjsk.user.mapper.MenuMapper;
import com.lijjsk.user.mapper.UserMapper;
import com.lijjsk.user.service.IUserService;
import com.lijjsk.utils.common.FFmpegUtils;
import com.lijjsk.utils.common.JwtUtils;
import com.minio.file.service.FileStorageService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private PasswordEncoder encoder;
    @Resource
    MenuMapper menuMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    IdentityMapper identityMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private FileStorageService fileStorageService;

    private final LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<User>();
    private final LambdaQueryWrapper<Identity> lambdaQueryWrapper=new LambdaQueryWrapper<Identity>();

    private final static String initPassword = "handsome@123";
    private final static String initIdentity = "用户";


    @Override
    public List<UserWithIdentityResponseDto> getAllUser() {

        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery());
        List<User> userListWithIdentity =new ArrayList<>();
        List<UserWithIdentityResponseDto> userWithIdentityResponseDtos=new ArrayList<>();
        for(User user:userList){
            userListWithIdentity.add(userMapper.selectUserByUsername(user.getUsername()));
        }
        for(User user:userListWithIdentity){
            UserWithIdentityResponseDto userWithIdentityResponseDto=new UserWithIdentityResponseDto();
            userWithIdentityResponseDto.setId(user.getId());
            userWithIdentityResponseDto.setAge(user.getState());
            userWithIdentityResponseDto.setEmail(user.getEmail());
            userWithIdentityResponseDto.setPhone(user.getPhone());
            userWithIdentityResponseDto.setSex(user.getSex());
            userWithIdentityResponseDto.setState(user.getState());
            userWithIdentityResponseDto.setUsername(user.getUsername());
            userWithIdentityResponseDto.setIsSecret(user.getIsSecret());
            userWithIdentityResponseDto.setProfilePhoto(user.getProfilePhoto());
            userWithIdentityResponseDto.setIdentities(user.getIdentitySet());
            userWithIdentityResponseDtos.add(userWithIdentityResponseDto);

        }
        return userWithIdentityResponseDtos;
    }

    /**
     * 登录，查询权限
     */
    @Override
    public Map<String, Object> login(UserRequestDto userRequestDto) {

        //传入用户名，密码
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userRequestDto.getUsername(), userRequestDto.getPassword());
        //实现登录逻辑,会去调用在UserDetails里定义的loadUserByUsername
        //authenticate就是UserDetails
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authentication);
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.info("用户名或者密码错误!");
            return null;
        }
        //获取返回的用户
        User user = (User) authenticate.getPrincipal();
        log.info("登录后的用户=======》{}", user);
        if (user == null) {
            return null;
        }
        //根据用户信息生成token
        Map<String, Object> tokenmap = new HashMap<>();
        tokenmap.put("用户Id", user.getId());
        tokenmap.put("用户Name", user.getUsername());
        tokenmap.put("用户身份", user.getIdentitySet());
        tokenmap.put("用户权限", user.getMenus());
        tokenmap.put("用户状态", user.getState());

        //存储用户信息的map
        Map<String, Object> userMap = new HashMap<>();
        log.info("用户map==============================={}", tokenmap);
        //获取并存储用户个人信息
        userMap.put("userInfo", user);
        //存储token
        userMap.put("token", JwtUtils.creatToken(tokenmap));
        return userMap;
    }

    /**
     * 根据token在其他微服务发送请求时，判断是否有权限
     */
    @Override
    public Boolean authority_judge(String token, String uri) {

        if ("/auth/login".equals(uri) || "/logout".equals(uri) || "/error".equals(uri)) {
            //通过
            return true;
        }
        //根据URI获取路径的访问权限
        Menu menu = menuMapper.selectMenuByURI(uri);
        if (menu == null) {
            //直接不通过,因为路径错误
            return false;
        }
        //拿到对应权限
        String perms = menu.getPerms();
        log.info("路径需要的权限=======================>{}", perms);
        if (perms == null || perms.trim().equals("")) {
            //直接通过,因为此接口路径不需要权限
            return true;
        }
        //与用户权限进行判断
        //有token,Jwt解析数据
        log.info("token=============>{}", token);
        Claims claims = null;
        try {
            claims = JwtUtils.parseToken(token);
        } catch (SignatureException e) {
            //验签出错会导致乱码，设置格式
            return false;
        }

        //获取到信息
        Integer id = claims.get("用户Id", Integer.class);
        String name = claims.get("用户Name", String.class);
        ArrayList<Identity> identitys = claims.get("用户身份", ArrayList.class);
        ArrayList<String> menus = claims.get("用户权限", ArrayList.class);

        Set<String> menuSet = new HashSet<>();
        menuSet.addAll(menus);
        for (String per_menu : menuSet) {
            if (per_menu.equals(perms)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 注册用户
     */
    /**
     * 注册用户
     */
    @Override
    @Transactional
    public Boolean addUser(User user) {
        User flag = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (flag != null){
            log.error("该用户名已经存在{}",user.getUsername());
            return false;
        }
        //加密
        String encoderPassword = encoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        //用户默认身份设置
        log.info("用户默认身份initIdentity:==========================>{}",initIdentity);
        Integer defaultIdentityId=identityMapper.selectIdentityIdByName(initIdentity);
        //存入
        save(user);
        return userMapper.addUserIdentity(user.getId(),defaultIdentityId);
    }

    /**
     * 用户信息更新，不包含密码
     */
    @Override
    @Transactional
    public Boolean updateUser(UserResponseDto userResponseDto) {
        //存入
        Boolean res = userMapper.updateUser(userResponseDto);
        return res;
    }

    /**
     * 更新用户信息
     *
     * @param userId
     * @param multipartFile
     * @param username
     * @param age
     * @param sex
     * @param phone
     * @return
     */
    @Override
    public Map<String,Object> editUserInfo(Integer userId, MultipartFile multipartFile, String username, Integer age, Integer sex, String phone) {
        User user = userMapper.selectById(userId);
        if (multipartFile != null){
            //获取原图片名
            MultipartFile image = FFmpegUtils.getThumbnailFromMultipartFile(multipartFile);
            String originalImageName = image.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String imageUrl = null;
            try {
                imageUrl = fileStorageService.uploadImgFile(String.valueOf(userId),uuid,originalImageName,image.getInputStream());
                log.info("用户头像上传到minio中,imageUrl:{}",imageUrl);
            } catch (IOException e) {
                log.error("上传用户头像失败");
                e.printStackTrace();
            }
            user.setProfilePhoto(imageUrl);
        }
        if (StringUtils.isNotBlank(username)){
            user.setUsername(username);
        }
        if (age != null){
            user.setAge(age);
        }
        if (sex != null && (sex == 1 || sex == 0)){
            user.setAge(age);
        }
        if (phone != null){
            user.setPhone(phone);
        }
        updateById(user);
        User userIdentity = userMapper.selectUserByUsername(user.getUsername());
        Map<String, Object> tokenmap = new HashMap<>();
        tokenmap.put("用户Id", userIdentity.getId());
        tokenmap.put("用户Name", userIdentity.getUsername());
        tokenmap.put("用户身份", userIdentity.getIdentitySet());
        tokenmap.put("用户权限", userIdentity.getMenus());
        tokenmap.put("用户状态", userIdentity.getState());

        //存储用户信息的map
        Map<String,Object> userMap=new HashMap<>();
        log.info("用户map==============================={}",tokenmap);
        //获取并存储用户个人信息
        userMap.put("userInfo",userIdentity);
        //存储token
        userMap.put("token", JwtUtils.creatToken(tokenmap));
        userMap.put("identityMap",userIdentity.getIdentitySet());
        return userMap;
    }

    /**
     * 关注与取关用户，根据 type的值判断
     */
    @Override
    @Transactional
    public Boolean followUser(FollowRequestDto followRequestDto, Boolean type) {
        //被关注
        Integer following_id = followRequestDto.getFollowing_id();
        //主动关注
        Integer followed_id = followRequestDto.getFollowed_id();
        Boolean res1;
        if (type) {
            //type=true
            res1 = userMapper.followUser(following_id, followed_id);
        } else {
            res1 = userMapper.unfollowUser(following_id, followed_id);
        }

        log.info("关注记录添加成功===================》被关注{},主动关注{}", followed_id, following_id);
        //被关注用户，粉丝加一
        Boolean res2 = userMapper.updateUserByFollowedId(followed_id, type);
        log.info("被关注用户粉丝加一===================》被关注{}", followed_id);
        //关注别人的用户关注人数加一
        Boolean res3 = userMapper.updateUserByFollowingId(following_id, type);
        log.info("主动关注用户粉丝加一===================》被关注{}", following_id);

        return res1 & res2 & res3;
    }

    /**
     * 更新用户密码
     */
    @Transactional
    public Boolean updateUserPassword(UserPasswordChangeDto userPasswordChangeDto) {
        String prePassword = userPasswordChangeDto.getPrePassword();
        String sufPassword = userPasswordChangeDto.getSufPassword();
        Integer userId = userPasswordChangeDto.getId();
        log.info("改变前的密码：======================》{}", prePassword);
        log.info("要改为密码：======================》{}", sufPassword);
        //将密码比对
        String checkPassword = userMapper.selectUserPasswordById(userId);
        log.info("查出来的用户密码=========================>{}", checkPassword);
        if (encoder.matches(prePassword, checkPassword)) {
            String nextPassword = encoder.encode(sufPassword);
            return userMapper.updateUserPasswordById(nextPassword, userId);
        }
        return false;
    }

    /**
     * 获取用户的粉丝列表
     *
     * @param userId
     */
    @Override
    public List<UserFollowResponseDto> selectUserFollowedListById(Integer userId) {
        return userMapper.selectUserFollowedListById(userId);
    }

    /**
     * 获取用户的关注列表
     *
     * @param userId
     */
    @Override
    public List<UserFollowResponseDto> selectUserFollowingListById(Integer userId) {
        return userMapper.selectUserFollowingListById(userId);
    }

    @Override
    @Transactional
    public Boolean setUserMUTED(Integer userId) {
        lambdaUpdateWrapper.eq(User::getId, userId);
        lambdaUpdateWrapper.set(User::getState, UserStates.MUTED.getCode());
        boolean flag = userMapper.update(lambdaUpdateWrapper) != 0;
        lambdaUpdateWrapper.clear();
        return flag;
    }

    /**
     * 解除禁言用户
     */
    @Override
    @Transactional
    public Boolean setUserUnMUTED(Integer userId) {
        lambdaUpdateWrapper.eq(User::getId, userId);
        lambdaUpdateWrapper.set(User::getState, UserStates.ACTIVE.getCode());
        boolean flag = userMapper.update(lambdaUpdateWrapper) != 0;
        lambdaUpdateWrapper.clear();
        return flag;
    }

    /**
     * 冻结用户
     */
    @Override
    @Transactional
    public Boolean setUserBANNED(Integer userId) {
        lambdaUpdateWrapper.eq(User::getId, userId);
        lambdaUpdateWrapper.set(User::getState, UserStates.BANNED.getCode());
        log.info("{}",UserStates.BANNED.getCode());
        boolean flag = userMapper.update(lambdaUpdateWrapper) != 0;
        lambdaUpdateWrapper.clear();
        return flag;
    }

    /**
     * 解冻用户
     */
    @Override
    @Transactional
    public Boolean setUserUnBANNED(Integer userId) {
        lambdaUpdateWrapper.eq(User::getId, userId);
        lambdaUpdateWrapper.set(User::getState, UserStates.ACTIVE.getCode());
        boolean flag = userMapper.update(lambdaUpdateWrapper) != 0;
        lambdaUpdateWrapper.clear();
        return flag;
    }


    /**
     * 用户重置密码
     */
    @Override
    @Transactional
    public Boolean resetPassword(Integer userId) {
        lambdaUpdateWrapper.eq(User::getId, userId);
        log.info("用户重置密码============================={}", initPassword);
        lambdaUpdateWrapper.set(User::getPassword, encoder.encode(initPassword));
        boolean flag = userMapper.update(lambdaUpdateWrapper) != 0;
        lambdaUpdateWrapper.clear();
        return flag;
    }

    /**
     * 用户获得会员权限
     */
    @Override
    @SentinelResource("addIdentity")
    @Transactional
    public Boolean getVIPIdentity(Integer userId, Integer identityId) {
        return userMapper.addUserIdentity(userId, identityId);
    }

    /**
     * 用户去除会员权限
     */
    @Override
    @Transactional
    public Boolean removeVIPIdentity(Integer userId, Integer identityId) {
        return userMapper.removeUserIdentity(userId, identityId);
    }

    /**
     * 用户获得大会员权限
     */
    @Override
    @Transactional
    public Boolean getSuperVIP(Integer userId, Integer identityId) {
        return userMapper.addUserIdentity(userId, identityId);
    }

    /**
     * 用户去除大会员权限
     */
    @Override
    @Transactional
    public Boolean removeSuperVIP(Integer userId, Integer identityId) {
        return userMapper.removeUserIdentity(userId, identityId);
    }

    /**
     * 获取身份列表
     * @return
     */
    @Override
    public List<Identity> getIdentityList() {
        return identityMapper.selectList(lambdaQueryWrapper.select());
    }
}

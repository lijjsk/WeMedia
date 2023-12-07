package com.lijjsk.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.wemedia.user.pojos.User;
import com.lijjsk.model.wemedia.video.pojos.Video;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

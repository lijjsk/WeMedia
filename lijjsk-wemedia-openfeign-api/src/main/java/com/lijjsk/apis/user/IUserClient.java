package com.lijjsk.apis.user;

import com.lijjsk.apis.config.FeignConfig;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.user.dtos.CheckDto;
import com.lijjsk.model.wemedia.user.dtos.UserIdentityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "wemedia-user",configuration = FeignConfig.class)
public interface IUserClient {
    @PostMapping("/api/v1/user/addIdentity")
    public boolean addIdentity(@RequestBody UserIdentityDto identityDto);
}

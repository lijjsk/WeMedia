package com.lijjsk.apis.user;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.user.dtos.CheckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("wemedia-user")
public interface IUserClient {
    @PostMapping("/api/v1/user/checkUserPermission")
    public boolean checkUserPermission(@RequestBody CheckDto checkDto);
}

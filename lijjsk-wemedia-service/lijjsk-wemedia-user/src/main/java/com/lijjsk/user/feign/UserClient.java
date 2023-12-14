package com.lijjsk.user.feign;

import com.lijjsk.apis.user.IUserClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.user.dtos.CheckDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserClient implements IUserClient {
    /**
     * 验证用户权限
     * @param checkDto
     * @return
     */
    @PostMapping("/api/v1/user/checkUserPermission")
    public boolean checkUserPermission(@RequestBody CheckDto checkDto){
        return true;
    }
}

package com.lijjsk.user.feign;

import com.lijjsk.apis.user.IUserClient;
import com.lijjsk.model.wemedia.user.dtos.UserIdentityDto;
import com.lijjsk.user.service.IUserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClient implements IUserClient {
    @Autowired
    private IUserService userService;
    @PostMapping("/api/v1/user/addIdentity")
    public boolean addIdentity(UserIdentityDto identityDto) {
        return userService.getVIPIdentity(identityDto.getUserId(),identityDto.getIdentityId());
    }
}

package com.lijjsk.model.wemedia.user.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class UserPasswordChangeDto {
    private Integer id;
    //先前密码34
    private String prePassword;
    //修改后密码
    private String sufPassword;
}

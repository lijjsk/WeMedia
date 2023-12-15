package com.lijjsk.model.wemedia.user.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class UserResponseDto {
    private Integer id;
    private String username;
    private Integer age;
    private Boolean sex;
    private String signature;
    private String profilePhoto;
    private String phone;
    private String email;
    private Integer sumFollowed;
    private Integer sumFollowing;
    private Integer state;
}

package com.lijjsk.model.wemedia.user.dtos;

import lombok.Data;

@Data
public class UserFollowResponseDto {
    private Integer id;
    private String username;
    private Boolean sex;
    private String signature;
    private String profilePhoto;
}
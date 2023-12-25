package com.lijjsk.model.wemedia.user.dtos;

import com.lijjsk.model.wemedia.user.pojos.Identity;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserWithIdentityResponseDto {
    private Integer id;
    private String username;
    private Integer age;
    private Boolean sex;
    private String profilePhoto;
    private String phone;
    private String email;
    private Integer state;
    private Boolean isSecret;
    private Set<Identity> identities;
}

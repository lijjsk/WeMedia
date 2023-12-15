package com.lijjsk.model.wemedia.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStates {
    ACTIVE(1), // 活跃状态
    BANNED(2), // 被封禁
    MUTED(3);  // 被禁言

    private final int code;
    //根据code值获取用户的状态
    public static UserStates valueOf(int code){
        //遍历状态
        for(UserStates states:values()){
            if(states.getCode()==code){
                return states;
            }
        }
        throw new IllegalArgumentException("Invalid User Status code: " + code);
    }
}

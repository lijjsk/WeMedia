package com.lijjsk.model.chat.dtos;

import lombok.Data;

@Data
public class SessionDto {
    private Integer sessionId;
    private Integer friendId;
    private String friendName;
    private String profilePhoto;;
}
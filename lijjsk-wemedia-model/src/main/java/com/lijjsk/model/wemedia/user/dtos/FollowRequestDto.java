package com.lijjsk.model.wemedia.user.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class FollowRequestDto {
    private Integer following_id;
    private Integer followed_id;
}

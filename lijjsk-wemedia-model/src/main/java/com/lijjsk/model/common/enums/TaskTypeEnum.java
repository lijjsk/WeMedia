package com.lijjsk.model.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

    NEWS_SCAN_TIME(1001, 1,"视频定时审核"),
    VIDEO_REVIEW_TIME(1001, 1,"视频定时审核"),
    VIDEO_COMPRESS_TIME(1002, 1,"视频定时压缩"),
    VIDEO_PUBLISH_TIME(1003, 1,"视频延时发布"),
    CALL_ERROR(2001, 2,"第三方接口调用失败，重试");
    private final int taskType; //对应具体业务
    private final int priority; //业务不同级别
    private final String desc; //描述信息
}
package com.lijjsk.common.constants;

public class VideoConstants {
    //video状态
    //0 待审核 1 审核成功 2 审核失败 3 待处理 4处理完成 5 待发布 6已发布 7已删除 8 需要人工审核
    public static final int WAIT_FOR_REVIEW=0;   //0 待审核

    public static final int REVIEW_SUCCESS=1;       //1 审核成功

    public static final int REVIEW_FAIL=2;   //2 审核失败
    public static final int COMPRESS_SUCCESS=3;   //3处理完成

    public static final int COMPRESS_FAIL=4;    //处理失败

    public static final int WAIT_FOR_PUBLISH=5;       //5 待发布

    public static final int PUBLISHED=6;   //6 已发布

    public static final int DELETED=7;   //7 已删除

    public static final int HUMAN_REVIEW=8;   //8 需要人工审核
}

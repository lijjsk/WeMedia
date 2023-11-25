package com.lijjsk.video.service;

import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.wemedia.video.pojos.Video;

public interface VideoAutoReviewService {
    public ResponseResult AutomaticReview(Integer id);
}

package com.lijjsk.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lijjsk.model.wemedia.video.pojos.VideoResolution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoResolutionMapper extends BaseMapper<VideoResolution> {
    VideoResolution getVideoResolution(
            @Param("videoId") String videoId,
            @Param("type") String type
    );
}

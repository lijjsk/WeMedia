package com.lijjsk.video.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.VideoConstants;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
import com.lijjsk.model.statistics.bos.VideoData;
import com.lijjsk.model.wemedia.video.dtos.VideoBriefDto;
import com.lijjsk.model.wemedia.video.dtos.VideoDto;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.model.wemedia.video.pojos.VideoResolution;
import com.lijjsk.video.mapper.UserMapper;
import com.lijjsk.video.mapper.VideoMapper;
import com.lijjsk.video.mapper.VideoResolutionMapper;
import com.lijjsk.video.service.VideoBrowseService;
import com.lijjsk.video.service.VideoUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoBrowseServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoBrowseService {

    /**
     * 增加视频弹幕量
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoBarrageNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumDanMu()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加视频播放量
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoViewNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumView()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加视频点赞量
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoLikeNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumLike()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加视频投币量
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoCoinNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumCoins()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加视频收藏量
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoCollectNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumCollect()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 增加视频分享量
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult addVideoShareNum(Integer videoId) {
        if (videoId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoId);
        video.setSumDanMu(video.getSumShare()+1);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 更新视频数据
     *
     * @param videoData
     * @return
     */
    @Override
    @SentinelResource("updateVideoData")
    public ResponseResult updateVideoData(VideoData videoData) {
        if (videoData == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        Video video = getById(videoData.getVideoId());
//        video.setSumDanMu(video.getSumDanMu()+ videoData.getSumDanMu());
//        video.setSumCoins(videoData.getSumCoins()+ video.getSumCoins());
//        video.setSumCollect(videoData.getSumCollect()+video.getSumCollect());
//        video.setSumView(videoData.getSumView()+ video.getSumView());
//        video.setSumShare(videoData.getSumShare()+videoData.getSumShare());
//        video.setSumLike(videoData.getSumLike() + videoData.getSumLike());
        BeanUtils.copyProperties(videoData,video);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

package com.lijjsk.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.AppHttpCodeEnum;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoBrowseServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoBrowseService {
    @Autowired
    private VideoResolutionMapper videoResolutionMapper;
    @Autowired
    private UserMapper userMapper;
    /**
     * 返回视频列表
     * @return
     */
    @Override
    public ResponseResult getVideoList() {
        // 使用 MyBatis-Plus 提供的查询方法，查询前6条视频数据
        List<Video> videoList = this.list(new QueryWrapper<Video>().last("LIMIT 6"));

        // 将查询结果转换成VideoBriefDto 格式
        List<VideoBriefDto> videoBriefDtoList = videoList.stream()
                .map(VideoBriefDto::new)
                .collect(Collectors.toList());

        return ResponseResult.okResult(videoBriefDtoList);

    }

    /**
     * 返回视频信息
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult getVideoInfo(Integer videoId) {
        VideoDto videoDto = new VideoDto();
        Video video = getById(videoId);
        BeanUtils.copyProperties(video,videoDto);
        VideoResolution videoResolution = videoResolutionMapper.getVideoResolution(video.getId().toString(), "1080p60hz");
        BeanUtils.copyProperties(videoResolution,videoDto);
        videoDto.setUsername(userMapper.selectById(video.getUserId()).getUsername());
        addVideoViewNum(videoId);
        return ResponseResult.okResult(videoDto);
    }

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
}

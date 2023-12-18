package com.lijjsk.video.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lijjsk.common.constants.VideoConstants;
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
import com.lijjsk.video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper,Video> implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoResolutionMapper videoResolutionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoBrowseService videoBrowseService;
    // 已经返回过的视频的 id 集合
    private final Set<Integer> returnedVideoIds = new HashSet<>();
    /**
     * 返回视频列表
     * @return
     */
    @Override
    public ResponseResult getVideoList() {
        // 查询所有已发布的视频数据
        List<Video> allVideos = videoMapper.selectList(Wrappers.<Video>lambdaQuery().eq(Video::getStatus, VideoConstants.PUBLISHED));

        // 如果已经返回过的视频列表为空，或者已返回的视频数量超过所有视频数量，重新开始
        if (returnedVideoIds.isEmpty() || returnedVideoIds.size() >= allVideos.size()) {
            returnedVideoIds.clear();
        }

        // 随机获取六条不重复的视频数据
        List<Video> newVideos = getRandomVideos(allVideos, 6);

        // 将新获取的视频的 id 记录下来
        newVideos.forEach(video -> returnedVideoIds.add(video.getId()));

        // 将查询结果转换成 VideoBriefDto 格式
        List<VideoBriefDto> videoBriefDtoList = newVideos.stream()
                .map(VideoBriefDto::new)
                .collect(Collectors.toList());

        return ResponseResult.okResult(videoBriefDtoList);
    }
    // 从视频列表中随机获取指定数量的视频
    private List<Video> getRandomVideos(List<Video> allVideos, int count) {
        List<Video> result = new ArrayList<>();
        Random random = new Random();

        while (result.size() < count) {
            Video randomVideo = allVideos.get(random.nextInt(allVideos.size()));
            if (!returnedVideoIds.contains(randomVideo.getId())) {
                result.add(randomVideo);
            }
        }

        return result;
    }
    /**
     * 获取用户的视频列表
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getUserVideoList(Integer userId) {
        List<Video> allVideos = videoMapper.selectList(Wrappers.<Video>lambdaQuery().eq(Video::getUserId, userId));
        return ResponseResult.okResult(allVideos);
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
        //返回用户id用于前端跳转
        videoDto.setUserId(userMapper.selectById(video.getUserId()).getId());
        videoDto.setUsername(userMapper.selectById(video.getUserId()).getUsername());
        videoBrowseService.addVideoViewNum(videoId);
        return ResponseResult.okResult(videoDto);
    }

    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult deleteVideo(Integer videoId) {
        Video video = getById(videoId);
        video.setStatus(VideoConstants.DELETED);
        updateById(video);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}

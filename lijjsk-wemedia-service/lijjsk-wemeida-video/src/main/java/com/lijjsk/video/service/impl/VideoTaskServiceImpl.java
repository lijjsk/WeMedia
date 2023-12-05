package com.lijjsk.video.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lijjsk.apis.schedule.IScheduleClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.TaskTypeEnum;
import com.lijjsk.model.schedule.dtos.Task;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.utils.common.ProtostuffUtil;
import com.lijjsk.video.service.VideoPublishService;
import com.lijjsk.video.service.VideoTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class VideoTaskServiceImpl implements VideoTaskService {
    @Autowired
    private IScheduleClient scheduleClient;
    @Autowired
    private VideoPublishService videoPublishService;
    /**
     * 添加发布任务到延迟队列中
     *
     * @param id          视频id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    @Override
    public void addVideoPublishToTask(Integer id, Date publishTime) {

    }

    /**
     * 消费任务 发布视频
     */
    @Override
    //@Scheduled(fixedRate = 1000)
    public void publishVideoByTask() {
        log.info("消费任务，发布视频");
        ResponseResult responseResult = scheduleClient.pull(TaskTypeEnum.VIDEO_PUBLISH_TIME.getTaskType(), TaskTypeEnum.VIDEO_PUBLISH_TIME.getPriority());
        if (responseResult.getCode().equals(200) && responseResult.getData() != null){
            Task task = JSON.parseObject(JSON.toJSONString(responseResult.getData()), Task.class);
            Video video = ProtostuffUtil.deserialize(task.getParameters(),Video.class);
            videoPublishService.publishVideo(video.getId());
        }
    }
}

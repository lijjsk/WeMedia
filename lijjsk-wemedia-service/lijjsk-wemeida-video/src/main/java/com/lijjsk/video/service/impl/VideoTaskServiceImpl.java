package com.lijjsk.video.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lijjsk.apis.schedule.IScheduleClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.common.enums.TaskTypeEnum;
import com.lijjsk.model.schedule.dtos.Task;
import com.lijjsk.model.wemedia.video.pojos.Video;
import com.lijjsk.utils.common.ProtostuffUtil;
import com.lijjsk.video.service.VideoAutoReviewService;
import com.lijjsk.video.service.VideoTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@Slf4j
public class VideoTaskServiceImpl implements VideoTaskService {

    @Autowired
    private IScheduleClient scheduleClient;
    /**
     * 添加任务到延迟队列中
     *
     * @param id          视频id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    @Override
    @Async
    public void addVideoToTask(Integer id, Date publishTime) {
        log.info("添加任务到延迟服务中——————begin");
        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        Video video = new Video();
        video.setId(id);
        task.setParameters(ProtostuffUtil.serialize(video));
        scheduleClient.addTask(task);
    }

    @Autowired
    private VideoAutoReviewService videoAutoReviewService;
    /**
     * 消费任务 审核视频
     */
    @Override
    @Scheduled(fixedRate = 1000)
    public void scanVideoByTask() {
        log.info("消费任务，审核视频");
        ResponseResult responseResult = scheduleClient.pull(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        if (responseResult.getCode().equals(200) && responseResult.getData() != null){
            Task task = JSON.parseObject(JSON.toJSONString(responseResult.getData()), Task.class);
            Video video = ProtostuffUtil.deserialize(task.getParameters(),Video.class);
            videoAutoReviewService.AutomaticReview(video.getId());
        }
    }
}

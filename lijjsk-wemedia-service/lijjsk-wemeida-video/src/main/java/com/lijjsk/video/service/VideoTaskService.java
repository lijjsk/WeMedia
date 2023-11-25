package com.lijjsk.video.service;

import java.util.Date;

public interface VideoTaskService {
    /**
     * 添加任务到延迟队列中
     * @param id  视频id
     * @param publishTime  发布的时间  可以做为任务的执行时间
     */
    public void addVideoToTask(Integer id, Date publishTime);
    /**
     * 消费任务 审核视频
     */
    public void scanVideoByTask();
}

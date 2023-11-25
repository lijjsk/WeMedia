package com.lijjsk.schedule.service;

import com.lijjsk.model.schedule.dtos.Task;

/**
 * 对外访问接口
 */
public interface TaskService {

    /**
     * 添加任务
     * @param task   任务对象
     * @return       任务id
     */
    public long addTask(Task task) ;
    /**
     * 取消任务
     * @param taskId
     * @return
     */
    public boolean cancelTask(Long taskId);

    /**
     * 拉取任务
     * @param type
     * @param priority
     * @return
     */
    public Task pull(int type,int priority);
}
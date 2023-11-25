package com.lijjsk.schedule.feign;

import com.lijjsk.apis.schedule.IScheduleClient;
import com.lijjsk.model.common.dtos.ResponseResult;
import com.lijjsk.model.schedule.dtos.Task;
import com.lijjsk.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ScheduleClient implements IScheduleClient {
    @Autowired
    private TaskService taskService;
    /**
     * 添加延迟任务
     * @param task
     * @return
     */
    @PostMapping("/api/v1/task/add")
    public ResponseResult addTask(@RequestBody Task task){
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * 取消任务
     * @param taskId
     * @return
     */
    @GetMapping("/api/v1/task/{taskId}")
    public ResponseResult cancelTask(@PathVariable("taskId") Long taskId){
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * 拉取任务
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/{type}/{priority}")
    public ResponseResult pull(@PathVariable("type") int type,@PathVariable("priority") int priority) {
        return ResponseResult.okResult(taskService.pull(type,priority));
    }
}
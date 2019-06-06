package com.yy.demo.springbootdynamictask.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yy
 * @ProjectName springboot-dynamic-task
 * @Description: TODO
 * @date 2019/6/4 14:49
 */
@Component
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable, ScheduledTask> scheduledTaskMap =new ConcurrentHashMap<>(16);

    @Autowired
    private TaskScheduler taskScheduler;


    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    public void addCronTask(Runnable task,String cronExpression){
        addCronTask(new CronTask(task,cronExpression));
    }

    private void addCronTask(CronTask cronTask) {
        if(cronTask != null){
            Runnable task = cronTask.getRunnable();
            if(this.scheduledTaskMap.containsKey(task)){
                    removeCronTask(task);
            }
            this.scheduledTaskMap.put(task, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task){
        ScheduledTask scheduledTask = this.scheduledTaskMap.remove(task);
        if(scheduledTask != null){
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask){
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledTask task : this.scheduledTaskMap.values()){
            task.cancel();
        }
        this.scheduledTaskMap.clear();
    }
}

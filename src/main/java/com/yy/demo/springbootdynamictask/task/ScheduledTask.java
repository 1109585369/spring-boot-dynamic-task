package com.yy.demo.springbootdynamictask.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author yy
 * @ProjectName springboot-dynamic-task
 * @Description: TODO
 * @date 2019/6/4 15:04
 */
public final class ScheduledTask {

    public volatile ScheduledFuture<?> future;

    public void cancel(){
        ScheduledFuture<?> future = this.future;
        if(future != null){
            future.cancel(true);
        }
    }
}

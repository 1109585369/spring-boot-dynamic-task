package com.yy.demo.springbootdynamictask.task;

import com.yy.demo.springbootdynamictask.SpringbootDynamicTaskApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.CronTask;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootDynamicTaskApplication.class)
public class SchedulingRunnableTest {

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;



    @Test
    public void testTask() throws InterruptedException {
        SchedulingRunnable task = new SchedulingRunnable("demoTask","taskNoParams",null);
        cronTaskRegistrar.addCronTask(task,"0/10 * * * * ?");

        // 便于观察
        Thread.sleep(3000);
        task.run();
    }
    @Test
    public void testHaveParamsTask() throws InterruptedException {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "haha", 23);
        cronTaskRegistrar.addCronTask(task,"0/10 * * * * ?");

        // 便于观察
        Thread.sleep(3000);
        task.run();
    }

}
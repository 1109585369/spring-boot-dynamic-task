package com.yy.demo.springbootdynamictask.test;

import org.springframework.stereotype.Component;

/**
 * @author yy
 * @ProjectName springboot-dynamic-task
 * @Description: TODO
 * @date 2019/6/5 11:03
 */
@Component("demoTask")
public class demoTask {

    public void taskWithParams(String param1,Integer param2){
        System.out.println("示例任务:" + param1 + param2);
    }

    public void taskNoParams() {
        System.out.println("无参示例任务");
    }

}

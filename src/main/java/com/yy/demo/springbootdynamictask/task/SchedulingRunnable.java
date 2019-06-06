package com.yy.demo.springbootdynamictask.task;


import com.yy.demo.springbootdynamictask.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author yy
 * @ProjectName springboot-dynamic-task
 * @Description: TODO
 * @date 2019/6/5 9:50
 */

public class SchedulingRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SchedulingRunnable.class);


    private String beanName;

    private String methodName;

    private Object[] params;

    public SchedulingRunnable(String beanName, String methodName) {
        this(beanName, methodName, null);
    }

    public SchedulingRunnable(String beanName, String methodName, Object... params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }

    @Override
    public void run() {
        log.info("定时任务开始执行,- bean:{},方法:{},参数:{}", beanName,methodName,params);
        long startTime = System.currentTimeMillis();
        Object target = SpringContextUtils.getBean(beanName);
        Method method = null;
        try {
            if (null != params && params.length > 0) {
                Class<?>[] paramCls = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramCls[i] = params[i].getClass();
                }
                method = target.getClass().getDeclaredMethod(methodName, paramCls);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }
            ReflectionUtils.makeAccessible(method);
        } catch (NoSuchMethodException e) {
            log.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, params), e);
        }
        long times = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }
        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                Arrays.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        if(params == null){
            return Objects.hash(beanName,methodName);
        }
        int result = Objects.hash(beanName, methodName);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}

package com.supercode.koakatool.platform.configs;/**
 * @Author: zhao.lin
 * @Date: 2018/10/12 10:51
 */

/**
 * @Author: zhao.lin
 * @Date: 2018/10/12 10:51
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 定时任务并行执行（将串行定时任务（多任务同一线程下执行）改为并行执行（多线程分别执行））
 **/
@Configuration
public class taskScheduledConfig implements SchedulingConfigurer {

    private static Integer THREADPOOLNUM = 5; //线程池数量

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod="shutdown")
    public Executor setTaskExecutors(){
        return Executors.newScheduledThreadPool(THREADPOOLNUM); // THREADPOOLNUM个线程来处理。
    }


    //在并行执行的时候，创建线程池采用了newScheduledThreadPool这个线程池。 Executors框架中存在几种线程池的创建，
    // 一种是 newCachedThreadPool() ，一种是 newFixedThreadPool()， 一种是 newSingleThreadExecutor()
    //
    //其中newScheduledThreadPool() 线程池的采用的队列是延迟队列。
    // newScheduledThreadPool() 线程池的特性是定时任务能够定时或者周期性的执行任务。
    //
    //源码:
    //public ScheduledThreadPoolExecutor(int corePoolSize) {
    //    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
    //          new DelayedWorkQueue());
    //}

    //其中线程池核心线程数是自己设定的，最大线程数是最大值。
    // 阻塞队列是自定义的延迟队列：DelayedWorkQueue()

}


package cn.bluethink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.rmi.ServerError;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>线程池</p>
 *
 * @author yukun24@126.com
 * @version V.1.0.1
 * @company 苏州中科蓝迪公司所有(c)2016-2021
 * @date created on 下午 3:26 2019-7-25
 */
@Configuration
public class GxThreadPoolExecutor {

    private int corePoolSize = 10;

    private int maxPoolSize = 30;

    private int queueCapacity = 8;

    private int keepAlive = 60;

    /**
     * 默认线程池线程池
     *
     * @return Executor
     */
    @Bean
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(corePoolSize);
        //指定最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列中最大的数目
        executor.setQueueCapacity(queueCapacity);
        //线程名称前缀
        executor.setThreadNamePrefix("defaultThreadPool_");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(keepAlive);
        //加载
        executor.initialize();
        System.err.println("线程池初始化......");
        return executor;
    }

}

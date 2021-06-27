package com.flinker.consume;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author:
 * @CreateTime: 2021/6/23
 * @company:
 */
public class ConsumerThreadPool {

    private ConsumerThreadPool() {}

    private static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>());
    }


    public static ExecutorService getThreadPool() {
        return newCachedThreadPool();
    }
}

package cn.chiu.haveatry.javaCore.concurrency.threadpool;

/**
 * @author yeachiu
 * @since 2019/11/7 21:39
 */
public class MyThreadPoolExecutor {

    private int corePoolSize;
    private int maxPoolSize;

    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize, int queueSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * 1.what threadPool do?
     */
    public class Worker extends Thread {

    }

    int currentPoolSize;

    public void execute (Runnable task) {
        if (currentPoolSize < corePoolSize) {
            new Thread();
        }
    }

}
package cn.chiu.haveatry.javaCore.concurrency.threadpool;

import org.springframework.util.StopWatch;

import java.util.concurrent.*;

/**
 * @author yeachiu
 * @since 2019/11/7 21:47
 */
public class ThreadPoolTest{

    /**
     * 返回Java虚拟机可用的处理器数量
     */
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池信息：
     *  核心线程数量5， 最大数量10，
     *  无界队列，
     *  超出核心线程数量的线程存活时间：5s,
     *  指定拒绝策略
     */
    public static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(corePoolSize, 10, 5, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<Runnable>(5));

    public static void main(String[] args) throws InterruptedException {


        //LinkedBlockingQueue的容量默认大小是Integer.Max，在任务没有填满这个容量之前线程池大小是不会超过设定的核心线程数量的
        //当制定LinkedBlockingQueue长度为5时，这15个线程任务有5个在核心线程中执行，有5个放在任务队列中，另外5个任务将另起新线程来执行。
        //总结来说：核心线程满了，接下来进队列，队列也满了，创建新线程，直到达到最大线程数，之后再超出，会进入拒绝rejectedExecution.



        CountDownLatch latch = new CountDownLatch(15);
        // 测试：提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 1; i <= 15; i++) {
            int n = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        StopWatch stopWatch = new StopWatch("多线程任务");
                        stopWatch.start("多线程任务" + n);
                        System.out.println("开始执行： " + n);
                        Thread.sleep(2000L);
                        System.out.println("执行结束：" + n);
                        //查看线程数量，查看队列等待数量
                        Thread.sleep(500L);
                        System.out.println("当前线程数量为：" + threadPoolExecutor.getPoolSize());
                        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
                        stopWatch.stop();
                        System.out.println(stopWatch.shortSummary());
                        latch.countDown();//单次任务结束，计数器减一
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("任务提交成功：" + i);

        }
        latch.await();// 等待所有人任务结束
        System.out.println("all-当前线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("all-当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());

    }


}
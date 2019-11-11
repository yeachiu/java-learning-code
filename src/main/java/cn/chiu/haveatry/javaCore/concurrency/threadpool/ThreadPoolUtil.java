package cn.chiu.haveatry.javaCore.concurrency.threadpool;

/**
 * @author jlqiu
 * @since 2019/11/11 16:24
 */

import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {

    /**
     * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
     * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
     * 混合型任务  = 视机器配置和复杂度自测而定
     */
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,
     *                           TimeUnit unit,BlockingQueue<Runnable> workQueue)
     * corePoolSize用于指定核心线程数量
     * maximumPoolSize指定最大线程数
     * keepAliveTime和TimeUnit指定线程空闲后的最大存活时间
     * workQueue则是线程池的缓冲队列,还未执行的线程会在队列中等待
     * 监控队列长度，确保队列有界
     * 不当的线程池大小会使得处理速度变慢，稳定性下降，并且导致内存泄露。如果配置的线程过少，则队列会持续变大，消耗过多内存。
     * 而过多的线程又会 由于频繁的上下文切换导致整个系统的速度变缓——殊途而同归。队列的长度至关重要，它必须得是有界的，这样如果线程池不堪重负了它可以暂时拒绝掉新的请求。
     * ExecutorService 默认的实现是一个无界的 LinkedBlockingQueue。
     */
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE));

    public static void main(String[] args) throws InterruptedException {

        //运行线程计数
        CountDownLatch latch = new CountDownLatch(15);
        //统计任务执行时间
        StopWatch stopWatch = new StopWatch("all-task");
        stopWatch.start();

        //使用execute方法
        // 测试：提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 1; i <= 15; i++) {
            int n = i;
            executor.execute(new Command(String.valueOf(n), 1000 + n, latch));
            System.out.println("任务提交成功：" + i);
        }
        // 等待所有任务结束
        latch.await();
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
    }

    public void executeBatchTask(String subject, Runnable command, Object[] keyList) throws InterruptedException {

        //运行线程计数
        CountDownLatch latch = new CountDownLatch(keyList.length);

        try {

            //统计任务执行时间
            StopWatch stopWatch = new StopWatch(subject);
            stopWatch.start();

            //使用execute方法
            for (Object obj : keyList) {
                executor.execute(CommndFatory.create((String)obj, 1000, latch));
                System.out.println("任务提交成功：" + obj.toString());
            }

            stopWatch.stop();
            System.out.println(stopWatch.shortSummary());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 等待所有任务结束
            latch.await();
        }
    }

    /**
     * 具体任务类
     */
    static class Command implements Runnable  {
        //任务索引
        String statsName;
        //模拟任务执行时间
        int runTime;
        //执行线程计数
        CountDownLatch latch;

        public Command(String statsName, int runTime, CountDownLatch latch) {
            this.statsName = statsName;
            this.runTime = runTime;
            this.latch = latch;
        }

        /**
         * 执行任务逻辑
         */
        @Override
        public void run() {
            try {
                //统计任务执行时间
                StopWatch stopWatch = new StopWatch(statsName);
                stopWatch.start();

                System.out.println("开始执行：" + statsName);
                //模拟任务执行时间
                Thread.sleep(runTime);
                System.out.println("执行结束：" + statsName);
                Thread.sleep(runTime);

                stopWatch.stop();
                System.out.println(stopWatch.shortSummary());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();//单次任务结束，计数器减一
            }
        }
    }

    static class CommndFatory{
        private CommndFatory(){

        }

        public static Command create(String statsName, int runTime, CountDownLatch latch) {
            return new Command(statsName,runTime,latch);
        }
    }
}

package cn.chiu.haveatry.javaCore.concurrency.threadpool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jlqiu
 * @since 2019/11/11 9:07
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        /*
         * 线程池信息：
         *  核心线程数量5， 最大数量10，
         *  无界队列，
         *  超出核心线程数量的线程存活时间：5s,
         *  指定拒绝策略
         */
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(10, 40, 5, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(Integer.MAX_VALUE));

        HashSet customerIds = new HashSet();
        Iterator<String> customerIdIterator = customerIds.iterator();
        while(customerIdIterator.hasNext()){
            String customerId = customerIdIterator.next();
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    //todo: task detail
                }
            });
        }
        for (int i = 1; i <= customerIds.size(); i++) {
            int n = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("开始执行： " + n);
                        Thread.sleep(2000L);
                        System.out.println("执行结束：" + n);
                        //查看线程数量，查看队列等待数量
                        Thread.sleep(500L);
                        System.out.println("当前线程数量为：" + threadPoolExecutor.getPoolSize());
                        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("任务提交成功：" + i);

        }
        //查看线程数量，查看队列等待数量
        Thread.sleep(500L);
        System.out.println("当前线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
        //等待15秒，查看线程数量和队列数量(理论上超出核心线程数量的线程自动销毁)
        Thread.sleep(30000L);
        System.out.println("当前线程数量为：" + threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
        Thread.sleep(30000L);
    }
}

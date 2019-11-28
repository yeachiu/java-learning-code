package cn.chiu.haveatry.mydemo.requestlimit.demo_singleconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 企业微信接口请求频次限制
 *
 * 单个cgi/api不可超过2000次/分，30000次/小时
 *
 * @author jlqiu
 * @since 2019/11/21 14:27
 */
@Service
public class RequestLimitService {

    private static final Logger logger = LoggerFactory.getLogger(RequestLimitService.class);

    private static Map<String, AtomicInteger> apiCountMap = new ConcurrentHashMap<>();

    private static long lastRefeshTimeMillis;

    /**
     * 限制时长(SECONDS)
     */
    private int time = 60;

    /**
     * 限制次数
     */
    private int count = 490;

    /**
     * 拦截时长(SECONDS)
     * 请求超出频次限制时，当前线程阻塞的时间，一般与限制时长相同
     */
    private int waits = 60;

    /**
     * 此计数器只要求能保证大致速率，暂未做方法同步处理，因此在多线程数据同步运行中偶尔会出现并发问题(?/500)
     * @param counterName
     */
    public void beforeProcess(String counterName) {
        if (counterName == null) {
            return;
        }
        //单个接口请求频次计数器
        AtomicInteger nowCount = apiCountMap.get(counterName);
        if (nowCount == null) {
            nowCount = new AtomicInteger(0);
        }
        logger.info("当前计数为" + nowCount.get());
        boolean keepSleep = true;
        while(keepSleep){
            //计数器刷新
            if(timeToRefesh(time)){
                nowCount.set(0);
                apiCountMap.put(counterName,nowCount);
                logger.info("访问频次计数器刷新重置");
            }
            // 拿到计数器，获取计数器剩余次数来进行判断
            if(nowCount.get() < count){
                // 请求频次未超出，继续执行
                nowCount.incrementAndGet();
                apiCountMap.put(counterName, nowCount);
                logger.info(Thread.currentThread().getName() + "执行任务:");
                keepSleep = false;
            }else{
                try {
                    //请求频次超出限制，线程阻塞一分钟
                    logger.info(Thread.currentThread().getName() + "请求频次超出限制,阻塞后再执行");
                    long currentTimeMillis1 = System.currentTimeMillis();
                    Thread.sleep(TimeUnit.SECONDS.toMillis(waits));
                    logger.info("线程" + Thread.currentThread().getName() + "等待了" + (System.currentTimeMillis() - currentTimeMillis1) + "毫秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 计数器刷新策略：
     *    当前时间比较上次刷新时间超过频次限制时长，则重置计数器
     *    当前计数小于频次限制，计数器加一，线程继续执行
     *    请求频次超出限制，线程阻塞，阻塞时长与频次限制时长相同
     * @param limitTime
     * @return 若为true,说明计数器已刷新重置
     */
    private boolean timeToRefesh(int limitTime){
        long mission = System.currentTimeMillis() - lastRefeshTimeMillis;
        if(mission > TimeUnit.SECONDS.toMillis(limitTime)){
            lastRefeshTimeMillis = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}

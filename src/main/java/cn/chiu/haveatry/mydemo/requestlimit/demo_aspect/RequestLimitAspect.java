package cn.chiu.haveatry.mydemo.requestlimit.demo_aspect;

import cn.chiu.haveatry.mydemo.requestlimit.demo_aspect.RequestLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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
 * @since 2019/11/20 15:46
 */
@Aspect
@Component
public class RequestLimitAspect {

    private static Map<String, AtomicInteger> apiCountMap = new ConcurrentHashMap<>();

    private static long lastRefeshTimeMillis;

    @Pointcut(value = "@annotation(cn.chiu.haveatry.mydemo.requestlimit.demo_aspect.RequestLimit)")
    public void limit(){}

    @AfterReturning("limit()")
    public void after(JoinPoint joinPoint){
        System.out.println("after ==<");
    }

    @Before("limit()")
    public void before(JoinPoint joinPoint) throws InterruptedException {
        System.out.println("before ==> ");
        //从注解获取参数
        RequestLimit annotationValue = getMethodAnnotationValue(joinPoint);
        if(annotationValue == null){
            return;
        }

        //单个接口请求频次计数器
        AtomicInteger nowCount;
        if(timeToRefesh(annotationValue.time())){
            System.out.println("刷新了");
            nowCount = new AtomicInteger();
            nowCount.incrementAndGet();
            apiCountMap.put(annotationValue.name(),nowCount);
            System.out.print("执行任务:");
        }else{
            nowCount = apiCountMap.get(annotationValue.name());
            System.out.println("当前计数为"+nowCount.get());
            // 拿到计数器，获取计数器剩余次数来进行判断
            if (nowCount.incrementAndGet() <= annotationValue.count()){
                // 请求频次未超出，继续执行
                System.out.print("执行任务:");
                apiCountMap.put(annotationValue.name(),nowCount);
            } else {
                //请求频次超出限制，线程阻塞一分钟
                System.out.println("请求频次超出限制,阻塞后再执行");
                long currentTimeMillis1 = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + "等待");
                Thread.sleep(TimeUnit.SECONDS.toMillis(annotationValue.waits()));
                System.out.println("线程"+ Thread.currentThread().getName() +"等待了" + (System.currentTimeMillis() - currentTimeMillis1) + "毫秒");
//                joinPoint.wait(TimeUnit.SECONDS.toSeconds(annotationValue.waits()));
            }
        }

    }

    /**
     * 从注解获取参数
     * @param joinPoint
     * @return
     */
    private RequestLimit getMethodAnnotationValue (JoinPoint joinPoint){
        System.out.println("获取参数");
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(RequestLimit.class)){
            //获取方法上注解中表明的权限
            RequestLimit annotationValue = method.getAnnotation(RequestLimit.class);
            if (annotationValue != null){
                return annotationValue;
            }
        }
        return null;
    }

    /**
     * 计数器刷新策略：
     *    当前时间比较上次刷新时间超过频次限制时长，则重置计数器
     *    当前计数小于频次限制，计数器加一，线程继续执行
     *    请求频次超出限制，线程阻塞，阻塞时长与频次限制时长相同
     * @param limitTime
     * @return
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

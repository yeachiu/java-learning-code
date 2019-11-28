package cn.chiu.haveatry.mydemo.requestlimit.demo_aspect;

import java.lang.annotation.*;

/**
 * @author jlqiu
 * @since 2019/11/20 15:35
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    /**
     * 频次计数器名称，区分不同API
     * @return
     */
    String name();

    /**
     * 限制时长(SECONDS)
     * @return
     */
    int time() default 60;

    /**
     * 限制次数
     * @return
     */
    int count() default 490;

    /**
     * 拦截时长
     * 请求超出频次限制时，当前线程阻塞的时间
     * @return
     */
    int waits() default 60;
}

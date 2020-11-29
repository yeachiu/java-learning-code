package cn.chiu.haveatry.javaCore.innerClass.controller;

/**
 * Created by yeachiu on 2019/7/1.
 */
public abstract class Event {
    private long eventTime;
    protected final long delayTime;
    public Event(long delayTime) {
        this.delayTime = delayTime;
        start();
    }

    /**
     * 独立的方法，可以重复重新启动计时器，也就是说Event对象可以重复使用
     */
    public void start() {
        //事件触发时间 = 当前时间 + 延迟时间
        eventTime = System.nanoTime() + delayTime;
    }

    //判断是否开始运行action()方法
    public boolean ready() {
        return System.nanoTime() >= eventTime;
    }
    public abstract void action();
}

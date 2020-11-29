package cn.chiu.haveatry.mydemo.requestlimit.demo_singleconfig;

/**
 * @author jlqiu
 * @since 2019/11/21 14:20
 */
public class LimitConfig {

    public LimitConfig(String name){
        this.name = name;
    }

    public LimitConfig(String name, int time, int count, int waits){
        this.name = name;
        this.time = time;
        this.count = count;
        this.waits = waits;
    }

    /**
     * 频次计数器名称，区分不同API
     * @return
     */
    private String name;

    /**
     * 限制时长(SECONDS)
     * @return
     */
    private int time = 60;

    /**
     * 限制次数
     * @return
     */
    private int count = 10;

    /**
     * 拦截时长
     * 请求超出频次限制时，当前线程阻塞的时间
     * @return
     */
    private int waits = 60;

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getCount() {
        return count;
    }

    public int getWaits() {
        return waits;
    }
}

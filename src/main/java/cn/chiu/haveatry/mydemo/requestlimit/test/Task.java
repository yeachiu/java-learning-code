package cn.chiu.haveatry.mydemo.requestlimit.test;


import cn.chiu.haveatry.utils.SpringBeanUtil;

/**
 * @author jlqiu
 * @since 2019/11/21 10:02
 */
public class Task implements Runnable {

    private String name;

    public Task(String name){
        this.name = name;
    }

    @Override
    public void run() {
        TestService testService = SpringBeanUtil.getBean(TestService.class);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("线程执行--" + name);
        testService.testRequest();
        System.out.println("线程"+ name +"执行完成时间为:" + (System.currentTimeMillis() - currentTimeMillis1) + "毫秒");

    }
}


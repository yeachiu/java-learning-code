package cn.chiu.jvm.jmm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeachiu on 2019/6/14.
 *
 * volatile关键字 指令重排序
 */
public class Volatile_NotMemoryReordering {

    public static int val = 1;
    public static int a,b,x,y;

//    public static volatile boolean initialized = false;
    public static boolean initialized = false;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {

            // 线程A
            // 模拟读取配置信息，当读取完成后将initialized设置为true以通知其他线程配置可用
            Thread threadA = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 玩法一：执行一项较耗时的工作（如读取文件）
//                for(int i=0; i<20; i++){
//                    val++ ;
//                }
//                System.out.println("A: " + val);

                    // 玩法二 观察x、y赋值顺序
                    a = 3;
                    x = b;
                    /**
                     *  如果变量initialized没有使用volatile修饰，那么可能会由于指令重排序优化而提前执行“initialized = true;”
                     *  导致线程A的初始化工作(或其他)未完成就被交由其他线程调用，发生错误和不安全情况
                     */
                    initialized = true;
                }
            });

            // 线程B
            // 等待initialized为true，代表线程A已经把配置初始化完成
            Thread threadB = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        while (!initialized) {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 玩法一： doSomeThingWithConfig 使用线程A初始化好的配置
//                val++;
//                System.out.println("B: " + val);

                    // 玩法二 观察x、y赋值顺序
                    b = 7;
                    y = a;
                }
            });

            threadA.start();
            threadB.start();

            // 主线程需要等待子线程执行结束
            threadA.join();
            threadB.join();
            System.out.println("x = " + x + " ; y = " + y);
        }

    }
}
/**
 * 有volatile修饰的变量，编译后会加多一个指令，相当于多了一个“内存屏障”(指重排序时不能把后面的指令重排序到内存屏障之前的位置)。
 *
 *
 */


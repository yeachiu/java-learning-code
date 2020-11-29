package cn.chiu.haveatry.singleton;

/**
 * Created by yeachiu on 2019/6/14.
 *
 * 双重校验锁模式 —— 单例实现
 */
public class DoubleCheckedLocking {

    private volatile static DoubleCheckedLocking instance;

    private DoubleCheckedLocking(){
    }

    public static DoubleCheckedLocking getInstance(){

        if (instance == null){
            synchronized (DoubleCheckedLocking.class){
                if (instance == null) {
                    instance = new DoubleCheckedLocking();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {

        DoubleCheckedLocking singleton1 = DoubleCheckedLocking.getInstance();
        DoubleCheckedLocking singleton2 = DoubleCheckedLocking.getInstance();

        System.out.println(singleton1 == singleton2);
    }
}

package cn.chiu.jvm;


/**
 * Created by yeachiu on 2019/6/6.
 * 方法动态分派演示
 */
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("hello,man");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("hello,woman");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();

        man.sayHello();     //output: hello,man
        woman.sayHello();   //output: hello,woman

        man = new Woman();
        man.sayHello();     //output: hello,woman
    }
}
/**
 * 虚拟机动态分派 —— “稳定优化”手段之虚方法表
 *
 *  1. 虚拟机为类在方法区中建立一个虚方法表，使用虚方法表索引来代替元数据查找以提高性能。
 *  2. 虚方法表中维护着类各个方法的实际入口地址。
 *  3. 原则：如果某个方法在子类中没有被重写，那子类虚方法表里面的地址入口和父类相同方法的入口地址一致，都指向父类的实现入口；
 *          如果子类中重写了这个方法，子类方法表中的地址将会替换为指向子类实现版本的入口地址。
 *  4. 为了程序实现上的方便，具有相同签名的方法，在父类、子类的虚方法表中都应当具有一样的索引序号，这样当类型变换时，仅需要变更查找的方法表就可以从不同的虚方法表中按索引转换出所需的入口地址。
 *  5. 方法表一般在类加载的连接阶段进行初始化，准备了类的变量初始值后，虚拟机会把该类的方法表也初始化完毕。
 */

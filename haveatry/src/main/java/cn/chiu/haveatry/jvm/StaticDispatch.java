package cn.chiu.haveatry.jvm;

/**
 * Created by yeachiu on 2019/6/6.
 * 方法静态分派演示
 */
public class StaticDispatch {

    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Women extends Human {

    }

    public void sayHello(Human guy) {
        System.out.println("Hello,guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("Hello,gentleman!");
    }

    public void sayHello(Women guy) {
        System.out.println("Hello,lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();

        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);   //output:: Hello,guy!
        sr.sayHello(women); //output:: Hello,guy!
    }
}
/**
 * 1. 静态类型和实际类型：
 *    静态类型的变化仅仅在使用时发生，变量本身的静态类型不会被改变，并且最终的静态类型是在编译期可知的；
 *    而实际类型的结果是在运行期才确定的，编译器在编译程序的时候并不知道一个对象的实际类型是什么。
 *
 * 2. 代码中 man 和 women 两个对象静态类型相同但实际类型不同；
 *    虚拟机(编译器)在重载时是通过参数的静态类型而不是动态类型作为判定依据的。
 *    并且，静态类型是编译期可知的，因此在编译阶段，Javac编译器会根据参数的静态类型决定使用哪个重载版本。
 *    所以本例中虚拟机选择sayHello(Human)作为调用目标，并把这个方法的符号引用写到main()方法里的两条invokevirtual指令的参数中。
 *
 * 3. 静态分派：所有依赖静态类型来定位方法执行版本的分派动作。
 *    静态分派的典型应用是 —— 方法重载
 *    静态分派发生在编译阶段
 **/

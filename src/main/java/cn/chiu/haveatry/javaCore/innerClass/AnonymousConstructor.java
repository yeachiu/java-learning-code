package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/28.
 *
 * Creating a constructor for an anonymous inner class
 */
public class AnonymousConstructor {
    // 此处变量i不要求一定是final的，因为它不会在匿名内部类中直接使用
    public static Base getBase(int i) {
        return new Base(i) {
            // 通过实例初始化，达到为匿名内部类创建构造器的效果
            {
                System.out.println("实例初始化");
            }
            public void f() {
                System.out.println("print in anonymous f()");
            }
        };
    }

    public IDestination destination(final String dest, final float price) {
        return new IDestination() {
            private int cost;
            //为每个对象进行实例初始化
            {
                cost = Math.round(price);
                if (cost > 100)
                    System.out.println("预算要超支啦！");
            }
            private String label = dest;
            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Base base = getBase(37);
        base.f();

        System.out.println("----------------分割线----------------------");

        AnonymousConstructor ac = new AnonymousConstructor();
        IDestination d = ac.destination("Wakanda", 102.568F);
    }
}

abstract class Base {
    public Base(int i) {
        System.out.println("Base Constructor, i=" + i);
    }
    public abstract void f();
}

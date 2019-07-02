package cn.chiu.haveatry.javaCore.innerClass;
/**
 * Created by yeachiu on 2019/7/1.
 *
 * 覆盖内部类
 */
public class OverridenInner extends Egg {
    public OverridenInner() {
        insertYolk(new Yolk());
    }
    // 小Y
    public class Yolk extends Egg.Yolk{
        public Yolk() {
            System.out.println("OverridenInner.Yolk()");
        }
        public void f() {
            System.out.println("OverridenInner.Yolk.f()");
        }
    }

    public static void main(String[] args) {
        OverridenInner oi = new OverridenInner();
        oi.g();
    }
}
class Egg {
    private Yolk y = new Yolk();

    public Egg() {
        System.out.println("New Egg()");
    }

    // 大Y
    protected class Yolk {
        public Yolk() {
            System.out.println("Egg.Yolk()");
        }
        public void f() {
            System.out.println("Egg.Yolk.f()");
        }

    }

    // 通过继承，此方法可以把小Y向上转型为大Y，故可以调用到小Y的f()
    public void insertYolk(Yolk yy) {
        y = yy;
    }
    public void g() {
        y.f();
    }
}

package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 内部类的继承
 */
class WithInner {
    class Inner {}
}
public class InheritInner extends WithInner.Inner {
    InheritInner(WithInner wi) {
        wi.super();
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner ii = new InheritInner(wi);
    }
}

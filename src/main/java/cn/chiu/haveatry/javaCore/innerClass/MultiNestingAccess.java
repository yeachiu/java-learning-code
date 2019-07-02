package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 从多重嵌套类中访问外部类成员
 */
class MNA {
    private void f() {}
    class A {
        private void g() {}
        public class B {
            void h() {
                g();
                f();
            }
        }
    }
}
public class MultiNestingAccess {
    public static void main(String[] args) {
        MNA mna = new MNA();
        MNA.A mnaa = mna.new A();
        MNA.A.B mnaab = mnaa.new B();
        mnaab.h();

    }

}

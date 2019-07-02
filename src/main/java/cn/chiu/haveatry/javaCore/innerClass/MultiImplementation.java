package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 使用内部类实现多重继承
 */
class D {}
abstract class E {}
class Z extends D {
    E makeE() {
        return new E() {
        };
    }
}
public class MultiImplementation {
    static void takesD(D d) {}
    static void takesE(E e) {}
    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}

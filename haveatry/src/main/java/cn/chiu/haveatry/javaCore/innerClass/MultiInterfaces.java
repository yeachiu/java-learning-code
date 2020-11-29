package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 一个类以某种方式实现两个接口 —— 单一类 && 内部类
 */
interface A {}
interface B {}
// 单一类
class X implements A,B {}
// 内部类
class Y implements A {
    B makeB() {
        return new B() {
        };
    }
}
public class MultiInterfaces {
    static void takesA(A a) {}
    static void takesB(B b) {}
    public static void main(String[] args) {
        X x = new X();
        Y y = new Y();
        takesA(x);
        takesA(y);
        takesB(x);
        takesB(y.makeB());
    }
}

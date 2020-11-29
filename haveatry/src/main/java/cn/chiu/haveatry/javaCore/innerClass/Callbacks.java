package cn.chiu.haveatry.javaCore.innerClass;

import cn.chiu.haveatry.dataStructures.impl.MyLinkedList;

/**
 * Created by yeachiu on 2019/7/1.
 *
 * 通过内部类提供的闭包功能实现回调
 */
interface Incrementable {
    void increment();
}
// 外围类直接实现接口的简单解决方式，用作对比
class Callee1 implements Incrementable {
    private int i = 0;
    public void increment() {
        i++;
        System.out.println(i);
    }
}

class MyIncrement {
    public void increment() {
        System.out.println("Other operation");

    }
    static void f(MyIncrement mi){
        mi.increment();
    }
}

// Callee2继承了MyIncrement，它与Incrementable拥有不同的increment(),因此就不能为了Incrementable的用途而覆盖increment()方法，于是只能使用内部类独立地实现。
class Callee2 extends MyIncrement {
    private int i = 0;
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }

    /**
     * 此内部类返回一个Callee2的“钩子”，而且是安全的钩子。
     * 无论谁获得此Incrementable的引用，都只能调用increment()。
     */
    private class Closure implements Incrementable{
        public void increment() {
            Callee2.this.increment();
        }
    }
    Incrementable getCallbackReference() {
        return new Closure();
    }
}

/**
 * Caller的构造器需要一个Incrementable的引用作为参数，然后在以后任意时刻都可以使用此引用来回调Callee类
 */
class Caller {
    private Incrementable callbackReference;
    Caller(Incrementable cbh) {
        callbackReference = cbh;
    }
    void go() {
        callbackReference.increment();
    }
}

public class Callbacks {
    public static void main(String[] args) {
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f(c2);
        Caller caller1 = new Caller(c1);
        Caller caller2 = new Caller(c2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }
}

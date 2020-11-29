package cn.chiu.haveatry.jvm;

import java.io.Serializable;

/**
 * Created by yeachiu on 2019/6/6.
 * 重载方法匹配优先级
 */
public class OverLoad {

    public static void sayHello(StaticDispatch arg) {
        System.out.println("hello StaticDispatch");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(char... arg) {
        System.out.println("hello char...");
    }

    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    public static void main(String[] args){
        sayHello('a');
        //output:: char -> int -> long -> Character -> Serializable -> Object -> char...
    }

    /**
     * char -> int -> long : 发生了两次自动类型转换，实际还能按照char->int->long->float->double的顺序进行转型匹配。但不会匹配到byte和short类型因为是不安全的。
     * -> Character : 发生了一次自动装箱
     * -> Serializable : java.lang.Serializable是java.lang.Character类实现的一个接口；当自动装箱后仍找不到装箱类，会去找装箱类实现的接口类型。
     *                   对于实现了两个接口的装箱类，如果同时出现这两个接口为参数的重载方法，它们的优先级是一样的，编译器将无法确定要转型为哪种类型而拒绝编译。
     * -> Object : 发生了向父类转型，如果有多个父类，那将在继承关系中从下往上开始搜索,越接近上层的优先级越低。
     * -> char... : 变长参数的重载优先级最低
     */
}

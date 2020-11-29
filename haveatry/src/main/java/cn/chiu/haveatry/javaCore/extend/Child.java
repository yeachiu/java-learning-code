package cn.chiu.haveatry.javaCore.extend;

/**
 * @author by Raza
 * @classname Child
 * @description TODO
 * @date 2020/11/23 0023 17:13
 */
public class Child extends Parent {
    String value = "0000";
    public Child(String v1) {
        super(v1);
        System.out.println("child contructer");
        value = v1;
    }

    public void doSomething() {
        System.out.println("do some things");
    }

    public static void main(String[] args) {
        int num = -5;
//        num= ++(num++);
        System.out.println(num);
        Child child = new Child("zhaozhao");
        System.out.println(child.value);
    }
}

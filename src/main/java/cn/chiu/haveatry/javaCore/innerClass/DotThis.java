package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/27.
 *
 * 使用OuterClassName.this生成对外部类对象的引用
 */
public class DotThis {

    private class InnerClass {
        public DotThis outer() {
            return DotThis.this;
        }
    }

    public InnerClass inner() {
        return new InnerClass();
    }

    public void f() {
        System.out.println("DotThis.f()");
    }

    public static void main(String[] args) {
        DotThis outerClass = new DotThis();
        DotThis.InnerClass innerClass = outerClass.inner();
        innerClass.outer().f();


    }
}
/* output:
DotThis.f()
 */


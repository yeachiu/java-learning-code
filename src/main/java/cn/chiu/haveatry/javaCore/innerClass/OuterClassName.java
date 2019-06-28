package cn.chiu.haveatry.javaCore.innerClass;

/**
 * Created by yeachiu on 2019/6/27.
 */
/**外部类**/
class OuterClassName {
    /**内部类**/
    class InnerClassName {
    }
    static class StaticInnerClass {

    }
    public InnerClassName inner() {
        return new InnerClassName();
    }
    public static void main(String[] args){
        OuterClassName outer = new OuterClassName();
        OuterClassName.InnerClassName inner = outer.inner();    //创建内部类对象
        OuterClassName.StaticInnerClass staticInner = new StaticInnerClass();
    }
}

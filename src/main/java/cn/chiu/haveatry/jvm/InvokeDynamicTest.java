package cn.chiu.haveatry.jvm;

import java.lang.invoke.*;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by yeachiu on 2019/6/10.
 * invokedynamic指令演示
 */
public class InvokeDynamicTest {

    public static void testMethod(String str){
        System.out.println("hello String : " + str);
    }

    /**
     * 引导方法
     */
    public static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws Throwable {

        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
    }

    /**
     * 返回方法类型
     */
    private static MethodType MT_BootstrapMethod() throws Throwable {

        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",null);
    }

    /**
     * 生成调用“testMethod”方法的MethodHandle
     * @return
     * @throws Throwable
     */
    private static MethodHandle MH_BootstrapMethod() throws Throwable {
        //在指定类中，调用指定类型的指定方法
        return lookup().findStatic(InvokeDynamicTest.class,"BootstrapMethod",MT_BootstrapMethod());
    }

    /**
     * 使用生成的MethodHandle来获得一个CallSite对象，由对象返回给invokedynamic指令实现对“testMethod”方法的调用
     * @return
     * @throws Throwable
     */
    private static MethodHandle INDY_BootstrapMethod() throws Throwable {
        // 返回要调用的方法(调用点)
        CallSite cs = (CallSite) MH_BootstrapMethod().invokeWithArguments(lookup(),"testMethod",MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null));
        return cs.dynamicInvoker();
    }

    public static void main(String[] args) throws Throwable {
        INDY_BootstrapMethod().invokeExact("icyfenix");

    }

}

/**
 *  1. 关于invokedynamic指令：
 *      先在运行时动态解析出调用点限定符所引用的方法，然后执行该方法。
 *      在此之前的4条指令(invokestatic,invokespecial,invokevirtual,invokenterface)的分派逻辑是固化在Java虚拟机内部的，而invokedynamic指令的
 *      分派逻辑是由用户所设定的引导方法决定的。
 *
 *  2. 演示原理：
 *     每一处含有invokedynamic指令的位置都称为 “动态调用点” (Dynamic Call Site)；
 *     该指令的第一个参数不再是代表方法符号引用的CONSTANT_Methodref_info常量，而变为JDK1.7新加入的CONSTANT_InvokeDynamic_info常量，
 *     从这个常量中可以得到3项信息：引导方法(Bootstrap Method,此方法存放在新增的BootstrapMethods属性中)、方法类型(MethodType) 和 名称。
 *     其中，引导方法是有固定的参数，并且返回值是java.lang.invoke.CallSite对象，这个代表真正要执行的目标方法调用。
 *
 *     根据该常量提供的信息，虚拟机可以找到并且执行引导方法，从而获得一个CallSite对象，最终调用要执行的目标方法。
 *
 */

package cn.chiu.jvm;


import sun.security.jca.GetInstance;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by yeachiu on 2019/6/6.
 * HSR-292 Method Handle 基础用法演示
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }

        public void print(Integer s) {
            System.out.println(s);
        }

        public void print() {
            System.out.println("print with not params");
        }

        /**
         * 模拟invokevirtual指令的执行过程，演示它的分派逻辑
         *
         * @param reveiver
         * @param mType 此参数为次案例逻辑上的参数
         * @return
         * @throws Throwable
         */
        private static MethodHandle getPrintlnMH(Object reveiver,String mType) throws Throwable {
            /**
             * MethodType : 代表“方法类型”，包含方法的返回值(第一个参数)和具体参数(第二个及以后的参数)
             */
            MethodType mtInteger = MethodType.methodType(void.class, Integer.class);
            MethodType mtString = MethodType.methodType(void.class, String.class);
            MethodType mt = mType.equals("mtInteger") ? mtInteger : mtString;
            String mName = mType.equals("mtInteger") ? "print" : "println";

            /**
             * lookup()方法来自于MethodHandles.lookup,这句的作用是在指定类中查找符合给定的方法名称、方法类型，并且符合调用权限的方法句柄。
             * 此处调用的是一个虚方法，按照Java语言的规则，方法的第一个参数是隐式的，代表该方法的接收者，也即是this指向的对象，
             * 这个参数以前是放在参数列表中进行传递的，而现在提供了bindTo()方法来完成这件事。
             */
            /**
             * 此方法的返回值可以视为对最终调用方法的一个“引用”
             */
            return lookup().findVirtual(reveiver.getClass(),mName,mt).bindTo(reveiver);
        }

        /**
         * 同上，无参数
         * @param reveiver
         * @return
         * @throws Throwable
         */
        private static MethodHandle getPrintlnMH(Object reveiver) throws Throwable {

            MethodType mt = MethodType.methodType(void.class);

            return lookup().findVirtual(reveiver.getClass(),"print",mt).bindTo(reveiver);
        }

        public static void main(String[] args) throws Throwable {
            Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
            /**
             *  无论obj最终是哪个实现类，下面这一句都能正确调用到println方法
             */
            getPrintlnMH(obj,"mtString").invokeExact("yxndwl");

            if(obj instanceof ClassA){
                //调用obj对象的方法（动态分派）
                getPrintlnMH(obj,"mtInteger").invokeExact(new Integer(135));

                getPrintlnMH(obj).invokeExact();
            }

        }
    }
}
/**
 * 仅站在Java语言角度来看，MethodHandle的使用方法和效果与Reflection有众多相似之处，不过，他们仍有以下区别：
 *
 * 1. 从本质上讲，Reflection和MethodHandle机制都是在模拟方法调用，但Reflection是在模拟Java代码层次的方法调用，而MethodHandle是在模拟字节码层次的方法调用；
 *    在MethodHandles.lookup中的3个方法 —— findStatic() , findVirtual() , findSpecial() 正是为了对应
 *    于 invokestatic, invokevirtual & invokeinterface 和 invokespecial 这几条字节码指令的执行权限校验行为，而这些底层细节在使用Reflection API时是不需要关心的。
 *
 * 2. Reflection是重量级，而MethodHandle是轻量级。
 *    java.lang.reflect.Method对象是方法在Java一端的全面映像，包含了方法的签名、描述符以及方法属性表中各种属性的Java端表示方式，还包含执行权限等的运行期信息。
 *    MethodHandle机制中的java.lang.invoke.MethodHandle对象仅仅包含执行该方法相关的信息。
 *
 * 3. 由于MethodHandle是对字节码的方法指令调用的模拟，所以，理论上虚拟机在这方面的各种优化(如方法内联)，在MethodHandle上也应当可以采用类似思路去支持。
 *    而通过反射去调用方法则不行。
 *
 * 4. Reflection API的设计目标是只为Java语言服务，而MethodHandle则可设计成服务于所有Java虚拟机之上的语言。
 */

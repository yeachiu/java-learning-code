package cn.chiu.jvm.classLoader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ClassLoader;

/**
 * Created by yeachiu on 2019/6/12.
 * 
 * 类加载器与instanceof关键字演示
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            /**
             * 自定义的类加载器
             */
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {

                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                InputStream is = getClass().getResourceAsStream(fileName);
                if (is == null){
                    return super.loadClass(name);
                }

                byte[] b = new byte[is.available()];

                is.read(b);

                return defineClass(name,b,0,b.length);

            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
            }
        };

        Object obj = myLoader.loadClass("cn.chiu.haveatry.JVM.classLoader.ClassLoaderTest").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof cn.chiu.haveatry.JVM.classLoader.ClassLoaderTest);

        /**
         * 此时虚拟机中存在着两个ClassLoaderTest类，一个由系统应用程序类加载器加载，一个由我们自定义的类加载器加载。
         * 虽然两个类都来自于同一个Class文件，但依然是两个独立的类，故做对象类型检查时结果为 false。
         */
    }
}
/**
 *  类与类加载器
 *
 *  1. 对于任意一个类，都需要由加载它的类加载器和这个类本身一同确定其在Java虚拟机中的唯一性，每一个类加载器，都拥有一个独立的类名称空间。
 *     即是说，比较两个类是否“相等”，只有在这两个类是由 同一个类加载器 加载的前提下才有意义。
 *     否则即使两个类是来源于同一个Class文件，被同一个虚拟机加载，只要加载它们的类加载器不同，那这两个类就必定不相等。
 *
 */

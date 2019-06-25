package cn.chiu.jvm.classLoader;

/**
 * Created by yeachiu on 2019/6/12.
 *
 * 双亲委派模型的实现
 */
public class ClassLoader {

//    实现双亲委派的代码都集中于java.lang.ClassLoader()方法中，以下为代码实现思路：

//    1. 首先，检查请求的类是否已经被加载过了；

//    2. 若未被加载，则委托给父类加载器判断是否加载；

//    3. 如果父类加载器抛出ClassNotFoundException,说明父类加载器无法完成加载请求

//    4. 在父类加载器无法加载的时候，再调用本身的findClass方法来进行类加载

}
/**
 *
 */

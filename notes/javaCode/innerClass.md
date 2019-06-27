# 前言
### 内部类

#### 为什么需要内部类？内部类存在的作用是什么？

   - 一般来说，内部类继承某个类或实现某个接口，内部类的代码操作创建它的外围类的对象。所以认为内部类提供了某种进入其外围类的窗口。
   - 如果我们需要一个对接口的引用，为什么不通过外围类实现那个接口呢？　　
      内部类实现那个接口和外围类实现那个接口有什么区别呢？　　
        每个内部类都能独立地继承自一个接口的实现，所以无论外围类是否继承了某个接口的实现，对于内部类都没有影响。


#### 特性

   1. 允许我们把一些逻辑相关的类组织在一起，并控制位于内部的类的可视性。
   2. 


#### 接口内部的类

   - 正常情况下，不能在接口内部放置任何代码，但是嵌套类可以作为接口的一部分。嵌套类都是静态的。
   - 接口的内部类甚至可以实现外为接口。
   - 如果想要创建某些公共代码，使得它们可以被某个接口的所有不同实现所共用，那么使用接口内部的嵌套类会很适合。


#### 内部类可以被覆盖吗？

#### 创建内部类对象

   - 从外部内的非静态方法之外的任意位置创建某个内部类的对象，必须具体的指明这个对象的类型：
   (在拥有外部类对象之前是不可能创建内部类(非static)对象的，当然，如果要创建的是嵌套类(静态内部类)，那么它就不需要对外部类对象的引用)
   
       ```java
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
               //创建内部类对象
               OuterClassName.InnerClassName inner = outer.inner();  
               //创建嵌套类对象
               OuterClassName.StaticInnerClass staticInner = new StaticInnerClass();     
           }
       }

       ```
   - [使用.new创建内部类的对象](#在内部类中使用this与new)
       
       
#### 内部类对象特性

   - 内部类天然拥有对其外围类所有成员的访问权 
   ——当某个外围类对象创建了一个内部类对象时，此内部类对象必定会秘密地捕获一个指向那个外围类对象的引用。
   
        ```java
        interface Selector {
             boolean end();
             Object current();
             void next();
        }
        public class Sequence {
             private Object[] items;
             private int next = 0;
             public Sequence(int size) {
                 items = new Object[size];
             }
             public void add(Object x) {
                 if (next < items.length)
                     items[next++] = x;
             }
             private class SequenceSelector implements Selector {
                 private int i = 0;
                 public boolean end() {
                     return i == items.length;
                 }
                 public Object current() {
                     return items[i];
                 }
                 public void next() {
                     if (i < items.length)
                         i++;
                 }
             }
             public Selector selector() {
                 return new SequenceSelector();
             }
             public static void main(String[] args){
                 Sequence sequence = new Sequence(10);
                 for(int i = 0; i < 10; i++) {
                     sequence.add(Integer.toString(i));
                 Selector selector = sequence.selector();
                 while (!selector.end()) {
                     System.out.println(selector.current() + " ");
                     selector.next();
                 }
            }
             }
        }
        /* output:
        0 1 2 3 4 5 6 7 8 9 
        */
        ```
        
### 在内部类中使用.this与.new
    
   - 使用OuterClassName.this生成对外部类对象的引用，这样产生的引用自动具有正确的类型
   
        ```java
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
        ```
   - 使用.new创建内部类的对象
   
        ```java
        public class DotNew {
            class Contents {
                private int i = 11;
                public int value() {
                    return i;
                }
            }
            class Destination {
                private String label;
                public Destination(String whereTo) {
                    label = whereTo;
                }
                public String readLabel() {
                    return label;
                }
            }
        
            public static void main(String[] args) {
                DotNew dn = new DotNew();
                DotNew.Contents contents = dn.new Contents();
                DotNew.Destination destination = dn.new Destination("Tahiti");
            }
        }
        ```
        
        



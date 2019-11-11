## 内部类

### 目录
   - [什么是内部类？](#什么是内部类)
   - [特性](#特性)
   - [创建内部类对象](#创建内部类对象)
   - [获取对外部类对象的引用](#获取对外部类对象的引用)
   - [获取指向基类或接口的引用](#获取指向基类或接口的引用)
   - [内部类的继承](#内部类的继承)
        - [内部类可以被覆盖吗？](#内部类可以被覆盖吗)
   - [内部类的种类](#内部类的种类)
        - [局部内部类](#局部内部类)
        - [匿名内部类](#匿名内部类)
        - [局部内部类和匿名内部类](#局部内部类和匿名内部类)
        - [嵌套类](#嵌套类)
   - [源码](/src/main/java/cn/chiu/haveatry/javaCore/innerClass/)
        

### 什么是内部类？
   
   1. 可以将一个类的定义放在另一个类的定义内部，就是内部类。
   2. 内部类允许你把一些逻辑相关的类组织在一起，并控制位于内部的类的可视性。
   3. 内部类天然具有访问外围对象所有成员的能力，包括private成员。


### 特性

   1. 允许我们把一些逻辑相关的类组织在一起，并控制位于内部的类的可视性。
   2. 内部类可以有效地实现“多重继承”，即是内部类允许继承多个非接口类型(类或抽象类)。
   3. 内部类天然拥有对其外围类所有成员的访问权 ——当某个外围类对象创建了一个内部类对象时，此内部类对象必定会秘密地捕获一个指向那个外围类对象的引用。
      
      <details>
        <summary><b>[code]</b></summary>  
        
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
                  return i == items.length;   //访问外围类私有属性
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
        
      </details>   


### 创建内部类对象

   - 从外部内的非静态方法之外的任意位置创建某个内部类的对象，必须具体的指明这个对象的类型：
   (在拥有外部类对象之前是不可能创建内部类(非static)对象的，当然，如果要创建的是嵌套类(静态内部类)，那么它就不需要对外部类对象的引用)
   
      <details>
         <summary><b>[code]</b></summary>  

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

      </details>
     
   - 使用.new创建内部类的对象
  
      <details>
         <summary><b>[code]</b></summary> 

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

      </details>
       
        
### 获取对外部类对象的引用
    
   - 使用OuterClassName.this生成对外部类对象的引用，这样产生的引用自动具有正确的类型
   
      <details>
         <summary><b>[code]</b></summary> 

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

      </details>
        
### 获取指向基类或接口的引用

   将内部类向上转型为其基类或接口(通过实现某个接口，得到对此接口的引用)，此时内部类能够完全不可见，并且不可用。
   这样做所得到的只是指向基类或接口的引用，所以能够很方便地隐藏实现细节。

   <details>
       <summary><b>[code]</b></summary> 

   ```java
   public class InnerClassUpcasting {
   
       private class Contents implements IContents {
           private int i = 11;
           public int value() {
               return i;
           }
       }
       protected class Destination implements IDestination {
           private String label;
           public Destination(String whereTo) {
               label = whereTo;
           }
           public String readLabel() {
               return label;
           }
       }
   
       public IDestination destination(String s) {
           return new Destination(s);
       }
       public IContents contents() {
           return new Contents();
       }
   
       public static void main(String[] args) {
           InnerClassUpcasting up = new InnerClassUpcasting();
   
           //不合法，Contents是private,Destination是protectd的，外部代码访问这些成员将受到限制
           //InnerClassUpcasting.Contents contents = up.new Contents();
           //InnerClassUpcasting.Destination destination = up.new Destination("Tahiti");
   
           // 使内部类(某个接口的实现)完全不可见并且不可用，仅得到指向基类或接口的引用，这样能够很方便地隐藏具体实现细节。
           IContents icontents = up.contents();
           IDestination idestination = up.destination("Wakanda");
           
           //ERROR:无法完成向下转型，说明该内部类被隐藏了
           //Contents ct = (Contents)icontents;
   
       }
   
   }
   ```

  </details>

### 内部类的继承

   - 因为内部类的构造器必须连接到指向其外围类对象的引用，所以在继承内部类的时候，事情会变得复杂。
     问题在于，那个指向外围类对象的引用必须被初始化，而在导出类中不再存在可连接的默认对象。
   
        要解决这个问题，必须使用特殊的语法来明确说清它们之间的关联：
   
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       class WithInner {
           class Inner {}
       }
       public class InheritInner extends WithInner.Inner {
           InheritInner(WithInner wi) {
               wi.super();
           }
       
           public static void main(String[] args) {
               WithInner wi = new WithInner();
               InheritInner ii = new InheritInner(wi);
           }
       }
       ```
      
       </details>   
       
       
   #### 内部类可以被覆盖吗？

   - 内部类可以被覆盖，必须通过新内部类明确地继承该内部类，并且覆盖了其中的方法。
         
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       public class OverridenInner extends Egg {
           public OverridenInner() {
               insertYolk(new Yolk());
           }
           // 小Y
           public class Yolk extends Egg.Yolk{
               public Yolk() {
                   System.out.println("OverridenInner.Yolk()");
               }
               public void f() {
                   System.out.println("OverridenInner.Yolk.f()");
               }
           }
           
           public static void main(String[] args) {
               OverridenInner oi = new OverridenInner();
               oi.g();
           }
       }
       class Egg {
           private Yolk y = new Yolk();
           
           public Egg() {
               System.out.println("New Egg()");
           }
           
           // 大Y
           protected class Yolk {
               public Yolk() {
                   System.out.println("Egg.Yolk()");
               }
               public void f() {
                   System.out.println("Egg.Yolk.f()");
               }
       
           }
       
           // 通过继承，此方法可以把小Y向上转型为大Y，故可以调用到小Y的f()
           public void insertYolk(Yolk yy) {
               y = yy;
           }
           public void g() {
               y.f();
           }
       }

       ```
      
       </details>      
    
### 内部类的种类

#### 局部内部类
##### —— 在方法和作用域内定义内部类

   什么情况下要这么做？  
   `1. 实现了某个接口，于是可以创建并返回对其的引用；`  
   `2. 想创建一个类，又不希望这个类是公共可用的。`
   
   - 在方法内定义内部类
       <details>
           <summary><b>[code]</b></summary>
           
       ```java
       public class NestingInMethod {
           public IDestination destination(String s) {
               class Destination implements IDestination {
                   private String label;
                   public Destination(String whereTo) {
                       label = whereTo;
                   }
                   public String readLabel() {
                       return label;
                   }
               }
               return new Destination(s);
           }
       
           public IContents contents() {
               class Contents implements IContents {
                   private int i = 11;
                   public int value() {
                       return i;
                   }
               }
               return new Contents();
           }
       
           public static void main(String[] args) {
               NestingInMethod ic = new NestingInMethod();
               IContents icontents = ic.contents();
               System.out.println(icontents.value());
               IDestination idestination = ic.destination("Wakanda");
               System.out.println(idestination.readLabel());
           }
       
       }
       ```
       ######  [InnerClassUpcasting.java](#获取指向基类或接口的引用)的变体
       </details>
   
   - 在任意作用域内嵌入一个内部类
       <details>
            <summary><b>[code]</b></summary>
             
       ```java
       public class NestingInScope {
       
           private void internalTracking(boolean bool) {
               if (bool) {
                   class TrackingSlip {
                       private String id;
                       TrackingSlip(String s) {
                           this.id = s;
                       }
                       String getSlip() {
                           return  id;
                       }
                   }
                   TrackingSlip ts = new TrackingSlip("slip");
                   String s = ts.getSlip();
               }
               //ERROR:作用域外无法使用该内部类
               //TrackingSlip ts = new TrackingSlip("slip");
           }
           public void track() {
               internalTracking(true);
           }
       
           public static void main(String[] args) {
               NestingInScope ns = new NestingInScope();
               ns.track();
           }
       } 
       ```
        
       </details>
    
  
#### 匿名内部类
    
   - 使用匿名类创建并返回对某个基类或接口的引用
   
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       public class AnonymousInnerClass {
           public IContents contents() {
               // 通过new表达式返回的引用被自动向上转型为对IContents的引用
               return new IContents() {    //创建一个继承自IContents的匿名类的对象
                   private int i = 11;
                   public int value() {
                       return i;
                   }
               };  //这种情况下此处需要一个分号
           }
       }
           /* 上述匿名内部类语法是下述形式的简化形式 */
       //    class Contents implements IContents {
       //        private int i = 11;
       //        public int value() {
       //            return i;
       //        }
       //    }
       //    public IContents contents() {
       //        return new Contents();
       //    }
       ```
      
       </details>    
       
  - 基类构造器带参的情况，只需简单地传递参数给基类构造器
  
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       public class AnonymousInnerClass {
           public Wrapping wrapping(int x) {
               return new Wrapping(x) {
                   public int value() {
                       return super.value() + 1;
                   }
               };
           }
           public static void main(String[] args) {
               AnonymousInnerClass ai = new AnonymousInnerClass();
               Wrapping w = ai.wrapping(37);
               System.out.println(w.value());
           }
       }
       ```
      
       </details>
       
  - 通过实例初始化实现匿名内部类创建构造器
  
    匿名内部类没有名字，不可能有命名构造器。要想达到构造器的效果，可以通过实例初始化来实现。
    当然，这种方式也有它的限制 —— 该实例初始化方法不能重载，所以你仅有这么一个构造器。
  
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       public class AnonymousConstructor {
    
           public IDestination destination(final String dest, final float price) {
               return new IDestination() {
                   private int cost;
                   //为每个对象进行实例初始化
                   {
                       cost = Math.round(price);
                       if (cost > 100)
                           System.out.println("预算要超支啦！");
                   }
                   private String label = dest;
                   @Override
                   public String readLabel() {
                       return label;
                   }
               };
           }
       
           public static void main(String[] args) {
               AnonymousConstructor ac = new AnonymousConstructor();
               IDestination d = ac.destination("Wakanda", 102.568F);
           }
       }
       ```
      
       </details>    
    
  - 匿名内部类与正规的继承相比有些受限，因为匿名内部类既可以扩展类，也可以实现接口，但是不能两者兼备。而且如果是实现接口，也只能实现一个接口。
    
  - 使用匿名内部类的工厂方法更优雅？

       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       interface Game {
           boolean move();
       }
       interface GameFactory {
           Game getGame();
       }
       
       class Checkers implements Game {
           private Checkers() {}
           private int moves = 0;
           private static final int MOVES = 3;
           @Override
           public boolean move() {
               System.out.println("Checkers move" + moves);
               return ++moves != MOVES;
           }
           //只需要创建单一的工厂对象，使用static
           public static GameFactory factory = new GameFactory() {
               @Override
               public Game getGame() {
                   return new Checkers();
               }
           };
       }
       
       class Chess implements Game {
           private Chess() {}
           private  int moves = 0;
           private static final int MOVES = 7;
           @Override
           public boolean move() {
               System.out.println("Chess move" + moves);
               return ++moves != MOVES;
           }
           public static GameFactory factory = new GameFactory() {
               @Override
               public Game getGame() {
                   return new Chess();
               }
           };
       }
   
       public class Factories {
           public static void playGame(GameFactory factory) {
               Game s = factory.getGame();
               while (s.move())
                   ;
           }
           public static void main(String[] args) {
               playGame(Checkers.factory);
               playGame(Chess.factory);
           }
       
       }
       ```
      
       </details>

#### 局部内部类和匿名内部类
  
  局部内部类不能有访问说明符，因为它不是外围类的一部分，但是它可以访问当前代码块内的常量以及此外围类的所有成员。
  
  - 局部内部类和匿名内部类的区别？
  
   <details>
      <summary><b>[code]</b></summary>
          
   ```java
   interface Counter {
       int next();
   }
   public class LocalInnerClass {
       private int count = 0;
       Counter getCounter(final String name) {
           class LocalCounter implements Counter {
               public LocalCounter() {
                   System.out.println("LocalCounter()");
               }
               public int next() {
                   System.out.print(name);
                   return count++;
               }
           }
           return new LocalCounter();
       }
   
       Counter getCounter2(final String name) {
   
           return new Counter(){
               {
                   System.out.println("Counter()");
               }
               public int next() {
                   System.out.print(name);
                   return count++;
               }
   
           };
       }
   
       public static void main(String[] args) {
           LocalInnerClass lic = new LocalInnerClass();
           Counter c1 = lic.getCounter("Local inner ");
           Counter c2 = lic.getCounter2("Anonymous inner");
           for (int i = 0; i < 5; i++)
               System.out.println(c1.next());
           for (int i = 0; i < 5; i++)
               System.out.println(c2.next());
       }
   
   }

   ```
 
   以上demo分别使用局部内部类和匿名内部类实现返回序列下一个值的功能，运行结果表明它们具有相同的行为和能力。
   </details>  
  
  - 既然行为和能力一直，那么什么情况使用局部内部类而不是匿名内部类呢？
    
    1. 需要一个已命名的构造器，或者需要重载构造器
    2. 需要不止一个该内部类的对象(匿名内部类是能用于实例初始化)
  
  
#### 嵌套类 
##### —— 声明为static的内部类

   前面有提到，普通的内部类对象隐式地保存了一个指向创建它的外围类对象的引用，这使得内部类拥有对外围类所有成员的访问权。
   然而，当内部类是static的时，就不是这样了。  
   
   如果不需要内部类对象与其外围类对象之间有联系，那么可以使用嵌套类(将内部类声明为static)。
   
   嵌套类意味着：  
       1. 要创建嵌套类的对象,并不需要其外围类的对象。  
       2. 不能从嵌套类的对象中访问非静态的外围类对象。
   
   - 嵌套类和普通内部类有一个区别是：普通内部类的字段与方法，只能放在类的外部层次上，所以普通内部类不能有static数据和static字段，也不能包含嵌套类。
     但是嵌套类却可以包含这些东西。
   
       <details>
              <summary><b>[code]</b></summary>
              
       ```java
       public class NestedClass {
       
           private static final int deed = 51;
           public int doSomething() {
               final double d = Math.random();
               final int i = (int)(d*deed);
               return i;
           }
       
           private class ParcelContents implements IContents {
               private int i = 37;
               public int value() {
                   return i + doSomething();
               }
               // ERROR:普通内部类的字段和方法不能有static数据和static字段，也不能包含嵌套类。
               //public static void f() {}
           }
           
           // ParcelDestination不需要外围类的任何对象
           protected static class ParcelDestination implements IDestination {
               private String label;
               private ParcelDestination(String s) {
                   this.label = s;
               }
               public String readLabel() {
                   return label;
               }
               //嵌套类可以包含其他静态元素和静态字段
               public static void f() {}
               static int x = 7;
               static class AnotherLevel {
                   public static void f() {}
                   static int x = 3;
               }
           }
       
           public IContents contents() {
               return new ParcelContents();
           }
       
           public static IDestination destination(String s) {
               return new ParcelDestination(s);
           }
       
           public static void main(String[] args) {
               NestedClass nc = new NestedClass();
               IContents c = nc.contents();
               IDestination d = destination("Cool");
           }
       }
       ```
      
       </details>
   
   ##### **接口内部的类**

   - 正常情况下，不能在接口内部放置任何代码，但是嵌套类可以作为接口的一部分。
   - 接口的内部类甚至可以实现外围接口。
   - 如果想要有可以被某个接口的所有不同实现所共用的**公共代码**，那么使用**接口内部的嵌套类**会很适合。

   ##### **从多层嵌套类中访问外部类成员**
   
   - 一个内部类被嵌套多少层并不重要 —— 它能透明地访问所有它嵌入的外围类的所有成员。
   
   <details>
     <summary><b>[code]</b></summary>
     
   ```java
   class MNA {
       private void f() {}
       class A {
           private void g() {}
           public class B {
               void h() {
                   g();
                   f();
               }
           }
       }
   }
   public class MultiNestingAccess {
       public static void main(String[] args) {
           MNA mna = new MNA();
           MNA.A mnaa = mna.new A();
           MNA.A.B mnaab = mnaa.new B();
           mnaab.h();
   
       }
   
   }
   ```
   
   </details>
    
    
### 为什么需要内部类？

   - 一般来说，内部类继承某个类或实现某个接口，内部类的代码操作创建它的外围类的对象。所以认为内部类提供了某种进入其外围类的窗口。
   - 如果我们需要一个对接口的引用，为什么不通过外围类实现那个接口呢？内部类实现那个接口和外围类实现那个接口有什么区别呢？　
   　
     每个内部类都能独立地继承自一个接口的实现，所以无论外围类是否继承了某个接口的实现，对于内部类都没有影响。
   - 创建内部类对象的时刻并不依赖于外围类对象的创建。
   - 内部类并没有令人迷惑的“is-a”关系；它就是一个独立的实体。

   
### 通过内部类提供的闭包功能实现回调
   
   - 闭包(closure)是一个可调用的对象，它记录了一些信息，这些信息来自于创建它的作用域。
   
        通过这个定义，可以看出内部类是一个面向对象的闭包，因为它不仅包含外围类对象的信息，还自动拥有一个指向此外围类对象的引用，在此作用域内，内部类有权操作所有的成员，包括private成员。
   
   - 在Java中，通过内部类提供闭包功能是一个非常好的类似指针机制的回调的解决方案，它比指针更灵活，也更安全。
   
      <details>
             <summary><b>[code]</b></summary>
             
      ```java
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
      ```
     
      </details>
   
   回调的价值在于它的灵活性 —— 可以在运行时动态地决定需要调用什么方法。
   
   

### 内部类标识符

   .class 文件中，内部类信息的规则　==> **外围类的名字，加上“$”，再加上内部类的名字。**
   
   如果内部类是匿名的，编译器会简单地产生一个数字作为其标识符。
   
   如果内部类是潜逃在别的内部类中，只需直接将它们的名字加在其外围类标识符与“$”的后面。
    

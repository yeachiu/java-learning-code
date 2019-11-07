### 轻量级的同步机制

   Java语言规范对volatile的定义如下：

   > Java编程语言允许线程访问共享变量，为了确保共享变量能被准确和一致地更新，线程应该确保通过排他锁单独获得这个变量。

   #### 语义一：可见性

　　volatile比synchronized更轻量级，它能保证volatile变量对所有线程是立即可见的，对volatile变量的所有写操作都能立刻反应到其他线程中。

   可见性原理:  
       　　本线程的工作内存写入主内存，该写入动作会引起别的CPU/线程工作内存中该变量值的无效化。
       　　所以线程每次使用该变量前都要先刷新，执行引擎看不到不一致的情况，因此可以认为volatile变量不存在一致性问题。

　　由于volatile变量只能保证可见性，在不符合以下两条规则的运算场景中，仍需要通过加锁(使用synchronized或java.util.concurrent中的原子类)来保证原子性。 
   
   - 运算结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。
   - 变量不需要与其他的状态变量共同参与不变约束。


   #### 语义二：指令重排序

　　有volatile修饰的变量，编译后会加多一个指令，相当于多了一个“内存屏障”(保证重排序时不能把后面的指令重排序到内存屏障之前的位置)。


   #### 性能

　　某些情况下，volatile的同步机制性能确实要优于锁(使用synchronized关键字或java.util.concurrent包里面的锁)，但是由于虚拟机对锁实行的许多消除和优化，
   使得我们很难量化地认为volatile就会比synchronized快多少。  
　　如果让volatile跟自己作比较，那可以确定一个原则：volatile变量的读操作的性能消耗与普通变量几乎没有什么差别，但是写操作则可能回慢一点，
   因为它需要在本地代码中插入许多内存屏障指令来保障处理器不发生乱序执行优化。
   不过即便如此，大多数场景下volatile的开销仍比锁要小，我们在锁与volatile之间选择的依据仅仅是volatile的语义能不能满足使用场景的需求。
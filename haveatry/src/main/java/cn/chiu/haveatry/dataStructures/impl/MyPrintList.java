package cn.chiu.haveatry.dataStructures.impl;


/**
 * Created by yeachiu on 2019/6/25.
 */
public class MyPrintList<E> {

    private static final int DEFAULT_CAPACITY = 10;  //数组长度为10；

    private int size;   //线性表长度
    private E[] list;   //数组保存数据

    public MyPrintList() {
        clear();
    }

    public MyPrintList(int capacity) {
        this.size = 0;
        this.list = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.size = 0;
        this.list = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public void insert(E e) {
        insert(size()+1,e);
    }

    public void insert(int i, E e) {

        if (i < 1 || i > list.length)    // 判断插入位置是否合理
            return;
        if (list.length == size())     //确认容量是否已满
            ensureCapacity(size() * 2 + 1);
        if (i < size()){    //插入数据不在表尾，需挪位
            for (int j = size()-1; j >= i-1 ; j--) {
                list[j + 1] = list[j];
            }
        }
        list[i-1] = e;
        size ++;
    }

    public int size() {
        return this.size;
    }

    public int locate(E e) {
        for (int i = 0; i < size()-1; i++) {
            if (list[i] == e)
                return i+1;
        }
        return 0;
    }

    public E get(int i) {
        if (isEmpty())
            return null;
        if (i < 1 || i > list.length)
            return null;
        return list[i-1];
    }

    public E delete(int i) {
        // 删除位置是否合理，末尾删除：挪位，取出元素，表长减一
        if (get(i) == null)
            return null;
        E item = list[i-1];
        if (i < size()){    //删除位置不在表尾，需挪位
            for (int j = i; j < size(); j++) {
                list[j-1] = list[j];
            }
        }
        list[size()-1] = null;
        size--;
        return item;
    }

    private void ensureCapacity(int newCapacity){   //用于数组实例化，还有数组的扩充

        if (newCapacity < size())
            return;
        E[] old = list;
        list = (E[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            list[i] = old[i];
        }

    }

    public void print(){
        for (E e : list) {
            System.out.print(e + " -- ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyPrintList<String> list = new MyPrintList<>();
        list.insert("one");
        list.insert("two");
        list.insert("three");
        list.insert("four");
        list.insert("five");
        list.print();
        System.out.println("size: " + list.size());
        System.out.println("delete: " + list.delete(2));
        list.print();
        System.out.println("delete: " + list.delete(4));
        list.print();
        System.out.println("get(6): " + list.get(6));
        System.out.println("isEmpty: " + list.isEmpty());
        list.clear();
        System.out.println("clear");
        list.print();
        System.out.println("isEmpty: " + list.isEmpty());
    }
}

package cn.chiu.haveatry.dataStructures.impl;

/**
 * Created by yeachiu on 2019/6/26.
 *
 * 使用数组代替指针，实现静态链表
 */
public class StaticLinkedList<E> {

    static class Node<E> {
        E data;
        int cur;

        public Node() {

        }

        public Node(E data, int cur) {
            this.data = data;
            this.cur = cur;
        }
    }

    private final static int MAXSIZE = 100;
    private Node[] space;
    private int size;

    public StaticLinkedList(){
        space = new Node[MAXSIZE];
        for (int i = 0; i < MAXSIZE-1; i++) {
            space[i] = new Node(null, i + 1);
        }
        space[MAXSIZE-1] = new Node(null, 0);  //目前静态链表为空，最后一个元素的cur为0
        size = 0;
    }

    public int length() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean insert(int i, E e) {
        int x, k = MAXSIZE - 1;
        if (i < 1 || i > size + 1)  //判断插入位置是否合理
            return false;
        x = linkNew();  // 空闲分量下标
        space[x].data = e;  //数据赋值
        if (size == 0) {    // 插入第1个元素
            space[k].cur = x;
            space[x].cur = 0;
        }else{
            for (int j = 0; j < i - 1; j++)   // 找到第i-1个元素的下标
                k = space[k].cur;
            //下标替换
            space[x].cur = space[k].cur;
            space[k].cur = x;
        }


        size++;
        return true;
    }

    public boolean insert(E e) {
        return insert(size + 1, e);
    }

    public boolean delete(int i) {
        int k = MAXSIZE-1;
        int toDel;  //删除结点的下标

        if (i < 1 || i > size)  //判断删除位置是否合理
            return false;

        for (int j = 0; j < i - 1; j++)   // 找到第i-1个元素的下标
            k = space[k].cur;
        toDel = space[k].cur;
        space[k].cur = space[toDel].cur;

        //释放空闲结点
        free(toDel);
        size--;
        return true;
    }

    public Node get(int i) {
        int k = MAXSIZE - 1;
        for (int j = 0; j < i; j++)   // 找到第i个元素的下标
            k = space[k].cur;
        return space[k];
    }

    public int get(E e) {
        int k = MAXSIZE - 1;
        for (int i = 1; i <= size; i++) {
            k = space[k].cur;
            if (space[k].data == e)
                return i;
        }
        return 0;
    }

    public void print() {
        int k = MAXSIZE - 1;
        for (int i = 0; i < size; i++) {
            k = space[k].cur;
            System.out.print(space[k].data + " --> ");
        }
        System.out.println();
    }

    // 获取新结点的下标
    private int linkNew() {

        // 数组第一个元素的cur存储空闲链表第一个结点的下标
        int i = space[0].cur;

        // 更新空闲链表头结点下标
        space[0].cur = space[i].cur;

        return i;
    }

    // 释放结点
    private void free(int i) {
        space[i].cur = space[0].cur;
        space[0].cur = i;
        space[i].data = null;
    }

    public static void main(String[] args) {
        StaticLinkedList<String> list = new StaticLinkedList<>();
        System.out.println("isEmpty" + list.isEmpty());
        System.out.println("insert " + list.insert("yiyi"));
        System.out.println("insert " + list.insert("erer"));
        System.out.println("insert " + list.insert("sam"));
        System.out.println("insert " + list.insert("shi"));
        list.print();
        System.out.println("size = " + list.size);
        System.out.println("get(3) = " + list.get(3).data);
        System.out.println("get(shi) = " + list.get("shi"));
        System.out.println("insert " + list.insert(3,"three"));
        list.print();
        System.out.println("delete " + list.delete(4));
        list.print();
        System.out.println("delete " + list.delete(1));
        list.print();
        System.out.println("delete " + list.delete(list.length()));
        list.print();
    }
}

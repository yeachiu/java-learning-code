package cn.chiu.haveatry.dataStructures.impl;

/**
 * Created by yeachiu on 2019/6/27.
 * 实现单循环链表，增加链表合并方法
 */
public class CircularLinkedList<E> {

    class Node<T> {
        T data;
        Node<T> next;
        public Node(){

        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> first = null;
    private int size = 0;

    public CircularLinkedList() {
        first = new Node();
        first.next = first;
        size++;
    }

    public CircularLinkedList(E e) {
        first = new Node(e);
        first.next = first;
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void clear() {
        if (isEmpty())
            return;
        Node oldFirst = first;
        while (first.next != oldFirst) {
            first = first.next;
        }
        first = null;
        oldFirst = null;
        size = 0;
    }

    public boolean insert(E e) {
        return insert(this.size + 1,e);
    }

    public boolean insert(int i, E e) {
        if (i < 1 || i > size + 1) //插入位置不合理
            return false;
        Node<E> newNode = null;
        if (get(i) == null) {  //表尾插入
            newNode = new Node<>(e,first);
            get(i-1).next = newNode;
        }else{
            newNode = new Node<>(e);
            Node prev = get(i-1);
            newNode.next = prev.next;
            prev.next = newNode;

        }

        this.size++;
        return true;
    }

    public int size() {
        return this.size;
    }

    public int locate(E e) {
        Node<E> node = first;
        int p = 1;
        while (node.next != null) {
            if (node.data == e)
                return p;
            node = node.next;
            p++;
        }
        return 0;
    }

    public Node<E> get(int i) {
        if (i < 1 || i > this.size)
            return  null;
        if (i == 1)     //头结点
            return first;
        Node<E> node = first;
        int p = 1;
        while (p < i) {
            node = node.next;
            p++;
        }
        return node;
    }

    public E delete(int i) {
        E e = null;
        if (i < 1 || i > size)
            return null;
        if (i == 1) {//删除头结点
            e = first.data;
            first = first.next;
            get(size).next = first;
            size--;
        }else{
            Node<E> prev = get(i-1);
            Node<E> todel = prev.next;
            prev.next = todel.next;
            e = todel.data;
            todel = null;
            size--;
        }

        return e;
    }

    public void print() {
        if (isEmpty())
            return;
        Node<E> node = first;
        while (node.next != first) {
            System.out.print(node.data + " --> ");
            node = node.next;
        }
        System.out.println(node.data + " => " + node.next.data);
    }

    public boolean addAll(CircularLinkedList listB) {
        if (listB.isEmpty() || isEmpty())
            return false;
        Node last = first;
        while (last.next != first)
            last = last.next;
        last.next = listB.first;
        last = listB.first;
        while (last.next != listB.first)
            last = last.next;
        last.next = first;
        return true;
    }

    public static void main(String[] args) {
        CircularLinkedList<String> linkedList = new CircularLinkedList<>("ling");
        linkedList.print();
        System.out.println("isEmpty:" + linkedList.isEmpty());
        System.out.println("insert:" + linkedList.insert("yiyi"));
        System.out.println("insert:" + linkedList.insert("erer"));
        linkedList.print();
        System.out.println("delete:" + linkedList.delete(1));
        linkedList.print();
        System.out.println("insert:" + linkedList.insert("sam"));
        System.out.println("insert:" + linkedList.insert("shi"));
        linkedList.print();
        System.out.println("delete:" + linkedList.delete(4));
        linkedList.print();
        System.out.println("delete:" + linkedList.delete(2));
        linkedList.print();
        System.out.println("insert:" + linkedList.insert(2,"erer"));
        linkedList.print();
        System.out.println("size:" + linkedList.size());
//        linkedList.clear();
//        System.out.println("size:" + linkedList.size());
//        System.out.println("isEmpty:" + linkedList.isEmpty());
//        linkedList.print();

        System.out.println("new linkedlist");
        CircularLinkedList<String> linkedListB = new CircularLinkedList<>("haha");
        linkedListB.insert("xixi");
        linkedListB.insert("luelue");
        linkedListB.insert("lalal");
        linkedListB.print();
        linkedList.addAll(linkedListB);
        linkedList.print();
    }
}

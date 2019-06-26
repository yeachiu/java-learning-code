package cn.chiu.haveatry.dataStructures.impl;


/**
 * Created by yeachiu on 2019/6/26.
 */
public class MyLinkedList<E> {

    class Node<T> {
        T data;
        Node<T> next;
        public Node(){

        }

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<E> first = null;
    private int size = 0;

    public MyLinkedList() {
        first = new Node();
        size++;

    }

    public MyLinkedList(E e) {
        first = new Node(e);
        size++;

    }

    public boolean isEmpty() {
        return first == null;
    }

    public void clear() {
        if (isEmpty())
            return;
        while (first.next != null) {
            first = first.next;
        }
        first = null;
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
            newNode = new Node<>(e);
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
        while (node.next != null) {
            System.out.print(node.data + " --> ");
            node = node.next;
        }
        System.out.println(node.data);
    }

    public static void main(String[] args) {
        MyLinkedList<String> linkedList = new MyLinkedList<>("ling");
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
        linkedList.clear();
        System.out.println("size:" + linkedList.size());
        System.out.println("isEmpty:" + linkedList.isEmpty());
        linkedList.print();
    }
}

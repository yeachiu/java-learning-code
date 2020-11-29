package cn.chiu.haveatry.dataStructures.impl;

/**
 * Created by yeachiu on 2019/6/27.
 */
public class DoubleCircularLinkedList<E> {

    class Node {
        E data;
        Node prev;
        Node next;
        public Node(){

        }
        public Node(E data) {
            this.data = data;
        }
    }

    private Node first = null;
    private int size = 0;

    public DoubleCircularLinkedList() {
        first = new Node();
        first.prev = first;
        first.next = first;
        size++;
    }

    public DoubleCircularLinkedList(E e) {
        first = new Node(e);
        first.prev = first;
        first.next = first;
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void clear() {
        if (isEmpty())
            return;
        for (int i = 0; i < size-1; i++) {
            first.next.prev = first.prev;
            first.prev.next = first.next;
            first = first.next;
        }
        first = null;
        size = 0;
    }

    public Node get(int i) {
        if (i < 1 || i > this.size)
            return  null;
        if (i == 1)     //头结点
            return first;
        if (i == size)  //尾结点
            return first.prev;
        Node node = first;
        int p = 1;
        while (p < i) {
            node = node.next;
            p++;
        }
        return node;
    }

    public boolean insert(E e) {
        return insert(this.size + 1,e);
    }

    public boolean insert(int i, E e) {
        if (i < 1 || i > size + 1) //插入位置不合理
            return false;
        Node newNode = new Node(e);
        Node prevNode = null;
        if (1 < i && i < size+1){
            prevNode = get(i-1);
            newNode.prev = prevNode;
            newNode.next = prevNode.next;
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
        }else{      //表头/表尾插入
            newNode.prev = first.prev;
            newNode.next = first;
            first.prev = newNode;
            first.prev.next = newNode;
            if (i == 1)     //插入头结点
                first = newNode;
        }
        this.size++;
        return true;
    }

    public int size() {
        return this.size;
    }

    public int locate(E e) {
        Node node = first;
        int p = 1;
        while (node.next != first) {    // 遍历链表
            if (node.data == e)
                return p;
            node = node.next;
            p++;
        }
        return 0;
    }

    public E delete(int i) {
        if (i < 1 || i > size)  // 删除位置是否合理
            return null;
        Node toDel = get(i);
        toDel.prev.next = toDel.next;
        toDel.next.prev = toDel.prev;
        if (i == 1)    //删除头结点
            first = toDel.next;
        size--;
        return toDel.data;
    }

    public void print() {
        if (isEmpty())
            return;
        Node node = first;
        while (node.next != first) {    //遍历链表
            System.out.print(node.data + " --> ");
            node = node.next;
        }
        System.out.println(node.data + " => " + node.next.data);
    }

    public boolean addAll(DoubleCircularLinkedList l) {
        if (l.isEmpty() || isEmpty())
            return false;
        Node last = first;
        while (last.next != first)
            last = last.next;
        last.next = l.first;
        last = l.first;
        while (last.next != l.first)
            last = last.next;
        last.next = first;
        return true;
    }


}

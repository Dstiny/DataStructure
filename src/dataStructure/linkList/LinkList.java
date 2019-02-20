package dataStructure.linkList;

//线性表之单链表


import dataStructure.MyException;



class Node<E>{

    E data;

    Node<E> next;

    public Node(E data,Node<E> next){
        this.data=data;
        this.next=next;
    }
}


public class LinkList<E> {

//    头结点
    private Node<E> head;
//    线性表长度
    private int count;

//    线性表的初始化
    public LinkList(){
        head=new Node<E>(null,null);
        count=0;
    }


//    判断线性表手否为空
    public boolean isEmpty(){

        if(count==0){
            System.out.println("表为空");
            return true;
        }else{
            System.out.println("表不为空");
            return false;
        }
    }


//    清空线性表
    public void clearList(){

        Node<E> q,p;
        q=head.next;

        while(q != null){
            p=q.next;
            q=null;
            q=p;
            count--;
        }
        head.next=null;

    }


//    获取第i个节点:为了定位到第几个几点
    private Node<E> getNode(int i) throws Exception{
        if(i<0 || i>count){
            throw new MyException("元素位置不对");
        }else if(i==0){
            return head;
        }else{
            Node<E> node=head.next;
            for(int k=1;k<i;k++){
                node=node.next;
            }
            return node;
        }
    }


//    获取第i个节点的数据
    public E getData(int i) throws Exception{
        return getNode(i).data;
    }


//  查找元素，0代表查找失败
    public int LocateElem(E e){

        Node<E> node;
        node=head.next;

        if(node.data == e)  return 1;

        for(int k=1;k<count;k++){
            node=node.next;
            if(node.data==e){
                return k+1;
            }
        }

        System.out.println("查找失败");

        return 0;
    }

//    第i个位置插入新的元素
    public void listInsert(int i,E e) throws Exception {

        if(i<0 || i>count+1){

            throw new MyException("插入位置错误");
        }else{

            Node<E> newNode=new Node<E>(e,null);
            newNode.next=getNode(i-1).next;
            getNode(i-1).next=newNode;
            count++;
            System.out.println("插入成功");
        }

    }

//   删除第i个位置元素，并返回其值
    public E listDelete(int i) throws Exception {

        if(i<0 || i>count){
            throw new MyException("删除元素位置错误");
        }

        Node<E> node=getNode(i);
        E e=node.data;
        getNode(i-1).next=node.next;
        node=null;
        count--;

        System.out.println("删除成功");
        return e;
    }

//    获取线性表的长度
    public int listLength(){
        return count;
    }


    /*
      整表创建：
      头插法一般把新加入的元素放在表头后的第一个位置：具体操作是首先是新结点的next指向头结点之后，表头的next指向新节点

        1 2 3 4 5---->5 4 3 2 1
     */

    public LinkList<Integer> createListHead(int n){

        LinkList<Integer> list1=new LinkList<Integer>();
        Node<Integer> node;

        for(int i=0;i<n;i++){
            int data=(int)(Math.random()*100);      //生成100以内随机数
            node=new Node<Integer>(data,null);
            node.next=(Node<Integer>)list1.head.next;
            list1.head.next=(Node<Integer>) node;
            list1.count++;
        }

        return list1;
    }

    /*
        整表创建
        尾插法相反:
        1 2 3 4 5---->1 2 3 4 5
     */
    public LinkList<Integer> createListTail(int n){

        LinkList<Integer> list2=new LinkList<Integer>();
        Node<Integer> node,lastNode;
        lastNode=(Node<Integer>)list2.head;

        for(int i=0;i<n;i++){
            int data=(int)(Math.random()*100);
            node=new Node<Integer>(data,null);
            lastNode.next=node;
            lastNode=node;
            list2.count++;
        }
        return list2;
    }
}

/*
        存储分配方式：
        顺序储存结构用一段连续的存储单元依次存储线性表的数据元素
        单链表采用链式存储结构，用一组任意的存储单元存放线性表的元素


        时间性能：
        查找
            顺序储存结构O(1)
            单链表O(n)
        插入和删除
            顺序表存储结构需要平均移动包场一半的元素，时间为O(n)
            单链表在找出某位置的指针后，插入和删除时间为O(1)

        空间性能：
            顺序储存结构需要预分配存储空间，分大了，浪费，分小了容易发生上溢
            单链表不需要分配存储空间，只要有就可以分配，元素个数也不瘦限制

 */

/*
        单链表的整表创建：头插法和尾插发
 */


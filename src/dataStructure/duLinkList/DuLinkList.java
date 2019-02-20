package dataStructure.duLinkList;

/*
    数据节点都有两个指针，分别指向直接前驱直接后继

    查找元素可以根据元素的位置，分别沿正向和反向查找。
 */


class Node<E>{

    E data;
    Node<E> prior;
    Node<E> next;

    public Node(E data,Node<E> prior,Node<E> next){

        this.data=data;
        this.prior=prior;
        this.next=next;
    }

}

public class DuLinkList<E> {

    private Node<E> head;
    private int count;

//    线性表初始化
    public DuLinkList(){

        head=new Node<E>(null,null,null);
        head.prior=head.next=head;
        count=0;
    }

//    获取第i个元素
    public Node<E> getElement(int i){

        if(count==0){
            throw new RuntimeException("空表，无法查找");
        }
        if(i<0 || i>count){
            throw new RuntimeException("查找位置出错");
        }
        Node<E> node=head.next;
        for(int j=1;j<i;j++){
            node=node.next;
        }

        return node;
    }


//    更优秀获取元素（利用双向链表的特性）
    public Node<E> getElementGOOD(int i){

        if(count==0){
            throw new RuntimeException("空表，无法查找");
        }

        if(i<0 || i>count){
            throw new RuntimeException("查找位置出错");
        }

        if(i<count/2){              //正向查找
            Node<E> node=head.next;
            for(int j=1;j<i;j++){
                node=node.next;
            }
            return node;
        }else{                      //反向查找
            Node<E> node=head.prior;
            int k=count-i;
            for(int j=0;j<k;j++){
                node=node.prior;
            }
            return node;
        }
    }


//    在第i个位置插入元素
    public void listInsert(int i,E data){

        if(i<0 || i>count+1){
            throw new RuntimeException("插入位置错误");
        }

        Node<E> node=new Node<E>(data,null,null);

        if(i==1){
            node.next=head.next;
            node.prior=head;
            node.next.prior=node;
            head.next=node;
        }else{
            Node<E> pNode=getElement(i-1);
            node.next=pNode.next;
            node.prior=pNode;
            pNode.next.prior=node;
            pNode.next=node;
        }

        count++;
    }


//    删除第i个元素
    public E listDelete(int i){

        if(i<0 || i>count){
            throw new RuntimeException("删除位置出错");
        }

        Node<E> node=getElement(i);
        E e=node.data;

        if(i==1){
            head.next=node.next;
            node.next.prior=node.prior;
            node.prior.next=node.next;
            node=null;
        }else{
            node.next.prior=node.prior;
            node.prior.next=node.next;
            node=null;
        }

        count--;
        return e;

    }


//    返回链表长度
    public int listLength(){
        return count;
    }

}

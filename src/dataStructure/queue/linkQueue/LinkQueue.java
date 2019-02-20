package dataStructure.queue.linkQueue;


//队列链式存储结构

/*
        单链表储存队列，称为链队列

        定义front指针指向头结点，rear指针指向终端结点，空队列时，front和rear都指向头结点
 */


class QNode<E>{
    E data;
    QNode next;

    public QNode(E data, QNode next) {
        this.data = data;
        this.next = next;
    }
}

public class LinkQueue<E> {

    private QNode front,rear;
    private int count;

    public LinkQueue() {
        front=new QNode(null,null);
        rear=front;
        count=0;
    }


//    入队
    public void inQueue(E e){
        QNode node=new QNode(e,null);
        rear.next=node;
        rear=node;
        count++;
    }


//    出队
    public E outQueue(){
        if(rear==front) throw new RuntimeException("队列为空");

        QNode node=front.next;
        E e= (E)node.data;
        front.next=node.next;

        if(rear==node) rear=front;
        node=null;
        count--;
        return e;
    }


//    获取队列长度
    public int getLength(){

        return count;
    }


//    打印输出队列
    public void printQueue(){
        if(count==0){
            System.out.println("空队列");
        }else{

            QNode node=front;
            for(int i=0;i<count;i++){
                node=node.next;
                System.out.println(node.data+" ");
            }

            System.out.println();
        }

    }
}

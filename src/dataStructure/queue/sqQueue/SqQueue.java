package dataStructure.queue.sqQueue;

//循环队列

/*
    队列的顺序存储结构：用数组存储队列，引入front指针指向对头元素，rear指针指向对位元素的下一个位置
    front=rear时队列为空
 */

 /*
    循环队列：执行入队操作时，若数组尾部已满，二数组前部因有元素出队而又空位时，我们把新插入的元素从头开始入队，
    类似于头尾相接的顺序存储结构称为循环队列

    循环队列队列满时front=rear不成立
        两种方法：一种是设置一个标志变量flag，当front=rear时，通过判断flag是1还是0确定队列满空情况
        另一种是，在数组是只剩一个空闲单位，定义为队列满

        队列满：(rear+1)%maxSize=front
        队列长度：(rear-front+maxSize)%maxSize

 */

public class SqQueue<E> {

    private E[] data;
    private int front;
    private int rear;
    private int maxSize;
    private static final int DEFAULT_SIZE=10;

//    初始化
    public SqQueue() {
        this(DEFAULT_SIZE);
    }

    public SqQueue(int maxSize) {
        data=(E[])new Object[maxSize];
        this.maxSize = maxSize;
        front=0;
        rear=0;
    }


//    队列长度
    public int getLength(){
        return (rear-front+maxSize)%maxSize;
    }


//   入队
    public void inQueue(E e){
        if((rear+1)%maxSize==front) throw new RuntimeException("队列已满，无法加入");
        data[rear]=e;
        rear=(rear+1)%maxSize;          //不是rear=rear+1，当在数组尾部时，后移一位会转到数组头部
    }


//    出队
    public E outQueue(){
        if(front==rear) throw new RuntimeException("队列空，无法出队");
        E e=data[front];
        front=(front+1)%maxSize;

        return e;
    }

//    打印
    public void printQueue(){
        int k=front;
        for(int i=0;i<getLength();i++){
            System.out.print(data[i]+"");
            i=(i+1)%maxSize;
        }
        System.out.println();
    }
}
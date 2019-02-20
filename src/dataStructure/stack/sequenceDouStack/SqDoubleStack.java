package dataStructure.stack.sequenceDouStack;

//两栈共享空间

/*
        通过一个数组存放两个栈，能较好利用空间。
        利用top1和top2变量表示栈1和栈2的栈顶指针

        两个栈的栈底分别位于数组的头部和尾部

        栈满条件：top1+1==top2
 */

public class SqDoubleStack<E> {

    private E[] data;
    private int top1;
    private int top2;
    private int maxSize;

    private static final int DEFAULT_SIZE=10;

    public SqDoubleStack(){
        this(DEFAULT_SIZE);
    }

    public SqDoubleStack(int maxSize){
//        无法创建泛型数组
        data=(E[])new Object[maxSize];
        top1=-1;
        top2=maxSize-1;
        this.maxSize=maxSize;
    }

//    进栈,stackNumber指进的栈号
    public void push(int stackNumber,E e){
        if(top1+1==top2) throw new RuntimeException("栈满，无法进栈");

        if(stackNumber==1){
            data[++top1]=e;
        }else if(stackNumber==2){
            data[--top2]=e;
        }else{
            throw new RuntimeException("栈号错误。");
        }

    }


//    出栈,stackNumber栈号
    public E pop(int stackNumber){

        E e;

        if(stackNumber==1){
            if(top1==-1) throw new RuntimeException("栈号为1是空栈，无法出栈");
            e=data[top1--];
        }else if(stackNumber==2){
            if(top2==maxSize-1) throw new RuntimeException("栈号为2是空栈，无法出栈");
            e=data[top2++];
        }else{
            throw new RuntimeException("栈号错误。");
        }

        return e;
    }


    public void printStack(int stackNumber){

    }

}

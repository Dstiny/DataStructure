package dataStructure.stack.sequenceStack;


//栈的顺序存储结构


/*
        用数组存放数据，top变量指示栈顶元素在数组中的位置（栈顶指针）
 */

public class SqStack<E> {

    private E[] data;
    private int top;        //-1代表空栈
    private int maxSize;

    private static final int DEFAULT_SIZE=10;

    public SqStack(){
        this(DEFAULT_SIZE);
    }

    public SqStack(int maxSize){
        //不能创建泛型数组
        data=(E[])new Object[maxSize];
        top=-1;
        this.maxSize=maxSize;
    }

//    进栈
    public void push(E e){
        if(top==maxSize-1) throw new RuntimeException("栈满，无法进栈");
        top++;
        data[top]=e;
    }

//    出栈
    public E pop(){

        if(top==-1) throw new RuntimeException("空栈，无法出栈");
        E e=data[top];
        top--;
        return e;

    }

//    输出存在数据
    public void printStack(){

        if(top==-1){
            System.out.println("空栈");
        }else{
            for(int i=0;i<=top;i++){
                System.out.println(data[i]);
            }
        }
    }

}

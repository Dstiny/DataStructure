package dataStructure.stack.linkStack;

//栈的链式存储结构

/*
        通过单向链表实现的栈，栈顶放在单链表的头部（注意，进栈操作不是向链表后面插入，而是从头部插入）
 */

class StackNode<E>{
    E data;
    StackNode<E> next;

    public StackNode(E data, StackNode<E> next) {
        this.data = data;
        this.next = next;
    }
}


public class LinkStack<E> {

    private StackNode<E> top;
    private int count;

    public LinkStack() {
        top=new StackNode<E>(null,null);
        count=0;
    }


//    进栈
    public void push(E e){

        StackNode<E> node=new StackNode<>(e,top);
        top=node;
        count++;
    }


//    出栈
    public E pop(){
        if(count==0) throw new RuntimeException("栈空，无法出栈");
        StackNode<E> node;
        node=top;
        top=top.next;
        count--;
        E e=node.data;
        node=null;
        return e;
    }


    public void printStack(){
        if(count==0) {
            System.out.println("空栈");
        }else{
            StackNode<E> node=top;
            for(int i=0;i<count;i++){
                System.out.println(node.data);
                node=node.next;
            }
        }
    }
}

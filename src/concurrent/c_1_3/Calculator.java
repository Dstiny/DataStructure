package concurrent.c_1_3;

public class Calculator implements Runnable{

    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    public void run() {

        for(int i=1;i<=10;i++){
            System.out.printf("%s : %d * %d = %d\n",Thread.currentThread().getName(),number,i,number*i);
        }
    }
}

class Main{

    public static void main(String[] args) {

        Thread thread[]=new Thread[10];
        Thread.State status[]=new Thread.State[10];

//
        for(int i=0;i<10;i++){

            thread[i]=new Thread(new Calculator(i));

            if(i%2==0) thread[i].setPriority(Thread.MAX_PRIORITY);
            else thread[i].setPriority(Thread.MIN_PRIORITY);
            thread[i].setName("thread_"+i);
        }

//        打印new状态的线程状态
        for(int i=0;i<10;i++){
            System.out.print(thread[i].getState()+"、");
        }
        System.out.println();
//        运行10个线程
        for(int i=0;i<10;i++){
            thread[i].start();
        }

//        可以打印出runnable、blocked、terminated
        for(int i=0;i<10;i++){
            System.out.print(thread[i].getState()+"、");
        }

    }
}

/*
    打印出线程ID，名称、优先级、状态
    thread[i].getId()
    thread[i].getName()
    thread[i].getPriority()
    thread[i].getState()

 */

package concurrent.c_3_2;

//我们将实现一个打印队列，并发任务使用二进制信号量机制。同时只有一个线程可以执行打印

import java.util.concurrent.Semaphore;

public class PrintQueue {

    private final Semaphore semaphore;

    public PrintQueue() {
        semaphore=new Semaphore(1);
    }

    public void printJob(Object document){

        try {
//            acquire()方法获取信号量，会抛出异常
            semaphore.acquire();

            System.out.println("printing:"+Thread.currentThread().getName());
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }
}

class Job implements Runnable{

    private PrintQueue print;

    public Job(PrintQueue print) {
        this.print = print;
    }

    public void run() {
        System.out.println("start to print"+Thread.currentThread().getName());
        print.printJob(new Object());
        System.out.println("end print"+Thread.currentThread().getName());
    }
}

class Main{

    public static void main(String[] args) {

        PrintQueue print=new PrintQueue();

        Thread thread[]=new Thread[10];

        for(int i=0;i<10;i++){
            thread[i]=new Thread(new Job(print),"thread"+i);
        }

        for(int i=0;i<10;i++){
            thread[i].start();
        }
    }
}

package concurrent.c_2_5;

//使用Lock接口和其子类ReentrantLock类创建一个临界区。模拟打印队列

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

//    锁对象
    private final Lock queueLock=new ReentrantLock();

    public void printJob(Object o){

        queueLock.lock();

        try{
            System.out.println("printing:"+Thread.currentThread().getName());
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            queueLock.unlock();
        }
    }
}

class Job implements Runnable{

    private PrintQueue print;

    public Job(PrintQueue print) {
        this.print = print;
    }

    public void run() {
        System.out.println("start to print:"+Thread.currentThread().getName());
        print.printJob(new Object());
        System.out.println("end to print:"+Thread.currentThread().getName());
    }
}

class Main{

    public static void main(String[] args) {

        PrintQueue printQueue=new PrintQueue();

        Thread thread[]=new Thread[10];

        for(int i=0;i<10;i++){
            thread[i]=new Thread(new Job(printQueue));
        }

        for(int i=0;i<10;i++){
            thread[i].start();
        }
    }
}

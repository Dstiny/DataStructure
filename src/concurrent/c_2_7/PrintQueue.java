package concurrent.c_2_7;

//修改2.5节案列，比较公平模式与非公平模式

//公平模式

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

    private Lock lock=new ReentrantLock(true);

    public void printJob(Object o){

        lock.lock();
        try{
            System.out.println("start to print1:"+Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("end to print1:"+Thread.currentThread().getName());
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        lock.lock();
        try{
            System.out.println("start to print2:"+Thread.currentThread().getName());
            Thread.sleep(1000);
            System.out.println("end to print2:"+Thread.currentThread().getName());
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

class Job implements Runnable{

    private PrintQueue print;

    public Job(PrintQueue print) {
        this.print = print;
    }

    public void run() {
        print.printJob(new Object());
    }
}

class Mian{

    public static void main(String[] args) {
        PrintQueue printQueue=new PrintQueue();

        Job job=new Job(printQueue);

        Thread t[]=new Thread[10];
        for(int i=0;i<10;i++){
            t[i]=new Thread(job);
        }

        for(int i=0;i<10;i++){
            t[i].start();
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
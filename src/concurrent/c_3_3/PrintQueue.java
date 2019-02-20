package concurrent.c_3_3;

//一个打印队列，被3个不同打印机使用

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

    private boolean freePrinter[];      //打印机是否正在使用

    private Semaphore semaphore;

    private Lock lockPrinter;         //保护freePrint数组的访问

    public PrintQueue() {

        semaphore=new Semaphore(3);
        freePrinter=new boolean[3];

        for(int i=0;i<3;i++){
            freePrinter[i]=true;
        }
        lockPrinter=new ReentrantLock();
    }

    public void printJob(Object object){

        try {
            semaphore.acquire();
            int numPrinter=getPrinter();
            System.out.println("printing:"+Thread.currentThread().getName()+"编号："+numPrinter);
            Thread.sleep(1000);
            freePrinter[numPrinter]=true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    private int getPrinter() {

        int temp=-1;

        try{
            lockPrinter.lock();

            for(int i=0;i<freePrinter.length;i++){
                if(freePrinter[i]){
                    temp=i;
                    freePrinter[i]=false;
                    break;
                }
            }
        }catch (Exception e){

        } finally {
            lockPrinter.unlock();
        }

        return temp;
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

package concurrent.c_1_10;

//线程局部变量机制

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SafeTask implements Runnable{

    private static ThreadLocal<Date> startDate=new ThreadLocal<Date>(){
        protected Date initialValue() {
            return new Date();
        }
    };


    public void run() {

        System.out.printf("start:%s:%s\n",Thread.currentThread().getId(),startDate.get());

        try{
            TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.printf("end:%s:%s\n",Thread.currentThread().getId(),startDate.get());
    }
}


class Mainz{

    public static void main(String[] args) {

        SafeTask safeTask=new SafeTask();

        for(int i=0;i<10;i++){
            Thread thread=new Thread(safeTask);
            thread.start();
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
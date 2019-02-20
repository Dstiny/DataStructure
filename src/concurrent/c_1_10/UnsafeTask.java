package concurrent.c_1_10;

//非线程局部变量机制

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnsafeTask implements Runnable {

    private Date startDate;

    @Override
    public void run() {

        startDate=new Date();
        System.out.printf("start:%s:%s\n",Thread.currentThread().getId(),startDate);

        try{
            TimeUnit.SECONDS.sleep((int)Math.rint(Math.random()*10));
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.printf("end:%s:%s\n",Thread.currentThread().getId(),startDate);
    }
}

class Main{

    public static void main(String[] args) {

        UnsafeTask unsafeTask=new UnsafeTask();

        for(int i=0;i<10;i++){
            Thread thread=new Thread(unsafeTask);
            thread.start();
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

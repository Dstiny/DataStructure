package concurrent.c_1_6;

//每隔1s钟输出一个时间

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileClock implements Runnable{


    @Override
    public void run() {

        for(int i=0;i<10;i++){

            System.out.println(new Date());

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Main{

    public static void main(String[] args) {

        FileClock fileClock=new FileClock();
        Thread thread=new Thread(fileClock);

        thread.start();

        try{
            TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        thread.interrupt();
    }
}

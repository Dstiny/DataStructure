package concurrent.c_1_7;

//初始化资源

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataSourceLoader implements Runnable{

    @Override
    public void run() {

        System.out.println("初始化资源"+new Date()+" "+Thread.currentThread().getName());

        try{
            TimeUnit.SECONDS.sleep(4);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("初始化结束"+new Date()+" "+Thread.currentThread().getName());
    }
}


class NetworkConnectionLoader implements Runnable{

    @Override
    public void run() {

        System.out.println("初始化资源"+new Date()+" "+Thread.currentThread().getName());

        try{
            TimeUnit.SECONDS.sleep(6);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("初始化结束"+new Date()+" "+Thread.currentThread().getName());
    }
}

class Main{

    public static void main(String[] args) {

        DataSourceLoader dataSourceLoader=new DataSourceLoader();
        NetworkConnectionLoader networkConnectionLoader=new NetworkConnectionLoader();

        Thread thread1=new Thread(dataSourceLoader);
        Thread thread2=new Thread(networkConnectionLoader);

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("资源加载结束");
    }
}

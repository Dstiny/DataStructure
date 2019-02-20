package concurrent.c_1_12;

import java.util.concurrent.TimeUnit;

public class MyThreadGroup extends ThreadGroup{


    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("非捕获异常");
        interrupt();                    //中断线程组其他线程
    }
}

class Task implements Runnable{

    public void run() {

        int result;
        while(true){

            try{
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){

            }

            result=100/0;

            if(Thread.currentThread().isInterrupted()){
                System.out.println("interrupted:"+Thread.currentThread().getId());
                return ;
            }
        }
    }
}

class Main{

    public static void main(String[] args) {

        MyThreadGroup myThreadGroup=new MyThreadGroup("myThreadGroup");

        Task task=new Task();

        for(int i=0;i<3;i++){
            Thread t=new Thread(myThreadGroup,task);
            t.start();
        }
    }
}
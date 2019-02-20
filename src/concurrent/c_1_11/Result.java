package concurrent.c_1_11;

//创建10个线程，并让他们休眠一个随机时间（模拟查找），当有一个查找成功，我们中断其他9个线程

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Result {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class SearchTask  implements Runnable{

    private Result result;

    public SearchTask(Result result) {
        this.result = result;
    }

    public void run() {

        String name=Thread.currentThread().getName();
        System.out.println("start:"+name);

        try{
            doTask();
            result.setName(name);
        }catch(InterruptedException e){
            System.out.println("interrupt:"+name);
        }
        System.out.println("end:"+name);
    }

    private void doTask() throws InterruptedException{
        Random random=new Random((new Date()).getTime());
        int value=(int)(random.nextDouble()*100);
        System.out.println("查找:"+Thread.currentThread().getName()+":"+value);
        TimeUnit.SECONDS.sleep(value);
    }
}

class Main{

    public static void main(String[] args) {

        ThreadGroup group=new ThreadGroup("searcher");

        Result result=new Result();

        SearchTask searchTask=new SearchTask(result);

        for(int i=0;i<10;i++){
            Thread thread=new Thread(group,searchTask);
            thread.start();

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){

            }
        }

//        听过list()方法打印线程组对象信息
        System.out.println("线程数量："+group.activeCount());
        System.out.print("线程组信息：");
        group.list();

//        activeCount()、enumerate()方法获取线程列表。帮助我们获取单个线程信息：状态...
        Thread[] threads=new Thread[group.activeCount()];
        group.enumerate(threads);
        for(int i=0;i<group.activeCount();i++){
            System.out.println("threadName:"+threads[i].getName()+" "+"状态："+threads[i].getState());
        }

//        调用waitFinash()方法。需要实现。他将等待组内第一个线程运行结束
        waitFinash(group);

        group.interrupt();
    }

    private static void waitFinash(ThreadGroup group) {

        while(group.activeCount()>9){

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


}

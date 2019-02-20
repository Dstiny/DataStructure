package concurrent.c_4_7;

//延迟执行

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String>{

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println("task starting:"+name+"  "+new Date());
        return "hello world";
    }
}

class Main{

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor executor=(ScheduledThreadPoolExecutor)
                Executors.newScheduledThreadPool(1);

        System.out.println("main startint:"+new Date());

        for(int i=0;i<5;i++){
            Task task=new Task(""+i);
            executor.schedule(task,(i+1)*10, TimeUnit.SECONDS);
        }

        executor.shutdown();        //结束执行器

        try {
            executor.awaitTermination(1,TimeUnit.DAYS);     //等待所有线程任务结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main end:"+new Date());
    }
}

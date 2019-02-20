package concurrent.c_4_8;

//周期

import java.util.Date;
import java.util.concurrent.*;

public class Task implements Runnable{

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("task starting:"+name+"  "+new Date());
    }
}

class Main{

    public static void main(String[] args) {

        ScheduledExecutorService executor=(ScheduledExecutorService)
                Executors.newScheduledThreadPool(1);

        System.out.println("main starting:"+new Date());

        Task task=new Task("task");

        ScheduledFuture<?> result=executor.scheduleAtFixedRate(task,1,2, TimeUnit.SECONDS);

        for(int i=0;i<10;i++){
            System.out.println("main delay:"+result.getDelay(TimeUnit.MILLISECONDS));

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main finashed");
    }
}

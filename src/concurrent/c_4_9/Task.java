package concurrent.c_4_9;

//在执行器中取消任务

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class Task implements Callable<String>{

    @Override
    public String call() throws Exception {

        while(true){
            System.out.println("task doing");
            Thread.sleep(1000);
        }
    }

}

class Main{

    public static void main(String[] args) {

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();

        Task task=new Task();

        System.out.println("main start");
        Future<String> future=executor.submit(task);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main cancle");
        future.cancel(true);

        System.out.println("main:cancled:"+future.isCancelled());
        System.out.println("main:done:"+future.isDone());

        executor.shutdown();

        System.out.println("main finash");
    }
}



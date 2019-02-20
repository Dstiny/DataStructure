package concurrent.c_4_12;

//处理被拒绝的任务，这些任务实现RejectedExecuttionHandler

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RejectedTaskController implements RejectedExecutionHandler{


//    在控制台输出已被拒绝的任务的名臣和执行器的状态
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        System.out.println("RejectedTaskController:"+"  name1:"+r.toString());

        System.out.println("RejectedTaskController:"+"  name2:"+executor.toString());

        System.out.println("RejectedTaskController:"+"  isTermiting:"+executor.isTerminating());

        System.out.println("RejectedTaskController:"+"  isTermited:"+executor.isTerminated());

    }
}


class Task implements Runnable{

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task:"+name+"   starting");

        long time=(long)(Math.random()*10);

        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Task finashed");
    }

    @Override
    public String toString() {
        return name;
    }
}

class Main{

    public static void main(String[] args) {

        RejectedTaskController controller=new RejectedTaskController();

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();

        executor.setRejectedExecutionHandler(controller);

        for(int i=0;i<3;i++){
            Task task=new Task("Task"+i);
            executor.submit(task);
        }

        System.out.println("main shutdown");
        executor.shutdown();

        System.out.println("main anthoer task");
        Task task=new Task("RejectedTask");
        executor.submit(task);

        System.out.println("main end");
    }
}
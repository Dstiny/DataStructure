package concurrent.c_4_3;

//修改c_4_2

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Task implements Runnable{

    private Date initDate;

    private String name;

    public Task(String name) {
        initDate=new Date();
        this.name = name;
    }

    public void run() {

        System.out.println("task name:"+name+"  task create:"+new Date()+"   Thread:"+Thread.currentThread().getName());
        System.out.println("task name:"+name+"  task startL:"+new Date()+"  Thread:"+Thread.currentThread().getName());

        try{
            Thread.sleep(2000);
            System.out.println("task name:"+name+"  task doing:"+new Date()+"   Thread:"+Thread.currentThread().getName());
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("task name:"+name+"  task finish:"+new Date()+"   Thread:"+Thread.currentThread().getName());
    }
}

///服务端
class Server{

    private ThreadPoolExecutor executor;

    public Server() {
        executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    public void executorTask(Task task){

        System.out.println("server a new Task");

        System.out.println("server executor task:"+executor.getTaskCount());

        executor.execute(task);

        System.out.println("server poll size:"+executor.getPoolSize());

        System.out.println("server active acount:"+executor.getActiveCount());

        System.out.println("server completed task:"+executor.getCompletedTaskCount());

    }

    public void endServer(){
        executor.shutdown();        //结束执行
    }
}

class Main{
    public static void main(String[] args) {

        Server server=new Server();

        for(int i=0;i<10;i++){
            Task task=new Task("task"+i);
            server.executorTask(task);
        }

        server.endServer();
    }
}


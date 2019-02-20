package concurrent.c_7_5;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class MyThread extends Thread{

    private Date creationDate;

    private Date startDate;

    private Date finishDate;

    public MyThread(Runnable target, String name) {
        super(target, name);

        setCreationDate();
    }

    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    public void setStartDate() {
        this.startDate = new Date();
    }

    public void setFinishDate() {
        this.finishDate = new Date();
    }

    public long getExecutionTime(){
        return finishDate.getTime()-startDate.getTime();
    }

    @Override
    public String toString() {

        StringBuilder builder=new StringBuilder();

        builder.append(getName());
        builder.append(":");

        builder.append("creationDate:");
        builder.append(creationDate);

        builder.append("Running Time:");
        builder.append(getExecutionTime());


        return builder.toString();
    }
}

class MyThreadFactory implements ThreadFactory {

    private int counter;

    private String prefix;

    public MyThreadFactory(String prefix) {
        this.prefix = prefix;
        counter=1;
    }

    @Override
    public Thread newThread(Runnable r) {

        MyThread thread=new MyThread(r,prefix+"-"+counter);

        counter++;

        return thread;
    }
}

class MyTask implements Runnable{

    @Override
    public void run() {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) throws Exception {

        MyThreadFactory factory=new MyThreadFactory("MyThreadFactory");

        ExecutorService executor= Executors.newCachedThreadPool(factory);

        MyTask task=new MyTask();

        executor.submit(task);

        executor.shutdown();

        executor.awaitTermination(1,TimeUnit.DAYS);

        System.out.println("main end");
    }
}

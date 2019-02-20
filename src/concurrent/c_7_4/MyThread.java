package concurrent.c_7_4;

import java.util.Date;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MyThread extends Thread{

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

class MyThreadFactory implements ThreadFactory{

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

class Main{

    public static void main(String[] args) {

        MyThreadFactory factory=new MyThreadFactory("MyThreadFactory");

        MyTask task=new MyTask();

        Thread thread=factory.newThread(task);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println(thread);
    }
}



package concurrent.c_7_8;

//实现计算自身的执行时间并在控制台输出

import java.util.Date;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

abstract class MyWorkerTask extends ForkJoinTask<Void>{

    private String name;

    public MyWorkerTask(String name) {
        this.name = name;
    }

//    是一个ForkJoinTask抽象方法，当MyWorkerTask任务不反悔任何结果时返回null
    @Override
    public Void getRawResult() {
        return null;
    }

//    另一个抽象方法，当MyWorkerTask任务不反悔任何结果时，设置方法体为空。
    @Override
    protected void setRawResult(Void value) {

    }

//    类的主方法，本例将任务委托到compute()方法。计算这个方法执行时间并输出到控制台
    @Override
    protected boolean exec() {

        Date startDate=new Date();
        compute();
        Date finishDate=new Date();

        long diff=finishDate.getTime()-startDate.getTime();

        System.out.println("MyWorkerTask:"+name+"   Milliseconds_to_finish:"+diff);

        return true;
    }

//    返回任务名
    public String getName() {
        return name;
    }

    //实现任务的逻辑，子类必须实现这个方法
    protected abstract void compute();
}

class Task extends MyWorkerTask{

    private int array[];

    private int start,end;

    public Task(String name,int array[],int start,int end) {

        super(name);
        this.array=array;
        this.start=start;
        this.end=end;
    }

    @Override
    protected void compute() {

        if(end-start>100){

            int mid=(start+end)/2;
            Task task1=new Task(this.getName()+"1",array,start,mid);
            Task task2=new Task(this.getName()+"2",array,mid,end);

            invokeAll(task1,task2);
        }else{

            for(int i=start;i<end;i++){
                array[i]++;
            }
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main{

    public static void main(String[] args) {

        int array[]=new int[1000];

        ForkJoinPool pool=new ForkJoinPool();

        Task task=new Task("Task",array,0,array.length);

        pool.execute(task);

        pool.shutdown();

        System.out.println("main end");
    }
}

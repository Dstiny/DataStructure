package concurrent.c_7_7;


import java.util.concurrent.*;


public class MyWorkerThread extends ForkJoinWorkerThread{

    private ThreadLocal<Integer> taskCounter=new ThreadLocal<>();

    public MyWorkerThread(ForkJoinPool pool) {
        super(pool);
    }


    @Override
    protected void onStart() {

        super.onStart();

        System.out.println("MyWorkrtThread onStart() id:"+getId());

        taskCounter.set(0);
    }

    @Override
    protected void onTermination(Throwable exception) {

        System.out.println("MyWorker onTermination() id:"+getId()
                +" counter:"+taskCounter.get());

        super.onTermination(exception);
    }

    public void addTask(){

        int counter=taskCounter.get().intValue();
        counter++;
        taskCounter.set(counter);
    }
}


class MyWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {


    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new MyWorkerThread(pool);
    }
}


class MyRecursiveTask extends RecursiveTask<Integer>{

    private static final long serialVersionUID=1L;

    private int array[];

    private int start,end;

    public MyRecursiveTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        Integer ret;

        MyWorkerThread thread=(MyWorkerThread)Thread.currentThread();

        thread.addTask();

        if(end-start>100){

            int mid=(start+end)/2;

            MyRecursiveTask task1=new MyRecursiveTask(array,start,mid);
            MyRecursiveTask task2=new MyRecursiveTask(array,mid,end);;

            invokeAll(task1,task2);

            ret=addResult(task1,task2);
        }else{

            int add=0;

            for(int i=start;i<end;i++){
                add+=array[i];
            }

            ret=new Integer(add);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ret;
    }

//    计算并返回通过结果传入的任务的结之和。
    private Integer addResult(MyRecursiveTask task1, MyRecursiveTask task2) {

        int value;

        try {
            value=task1.get().intValue()+ task2.get().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            value=0;
        } catch (ExecutionException e) {
            e.printStackTrace();
            value=0;
        }

        return value;

    }
}

class Main{

    public static void main(String[] args) {

        MyWorkerThreadFactory factory=new MyWorkerThreadFactory();

        ForkJoinPool pool=new ForkJoinPool(4,factory,null,false);

        int array[]=new int[10000];

        for(int i=0;i<array.length;i++){
            array[i]=1;
        }

        MyRecursiveTask task=new MyRecursiveTask(array,0,array.length);

        pool.execute(task);

        task.join();

        pool.shutdown();

        try {
            pool.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Main task result:"+task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Main end");
    }
}

package concurrent.c_5_5;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class Task extends RecursiveTask<Integer>{

//    模拟处理的数据数组
    private int array[];

    private int start,end;

    public Task(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println("Task start-end:"+start+"-"+end);

        if(end-start<10){

            if((3>start) && (3<end)){
                throw new RuntimeException("task exception start-end:"+start+"-"+end);
            }

            try {
                TimeUnit.SECONDS.sleep(1);
             } catch (InterruptedException e) {
                e.printStackTrace();
             }

        }else{

            int mid=(start+end)/2;
            Task task1=new Task(array,start,mid);
            Task task2=new Task(array,mid,end);
            invokeAll(task1,task2);
        }

        System.out.println("task ending start-end:"+start+"-"+end);

        return 0;
    }
}

class Main{

    public static void main(String[] args) {

        int array[]=new int[100];

        Task task=new Task(array,0,100);

        ForkJoinPool pool=new ForkJoinPool();

        pool.execute(task);

        pool.shutdown();

        try {
            pool.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        调用isCompletedAbnormally()检查主任务或它子任务之一是否抛出了异常。在控制台输出就表示异常抛出。
        if(task.isCompletedAbnormally()){
            System.out.println("main exception:"+task.getException());
        }
        System.out.println("main result:"+task.join());
    }
}

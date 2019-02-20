package concurrent.c_5_6;

//寻找数组中某个数所处的位置。第一是获取可以被取消的剩余任务数。
//由于Fork/Join框架没提供取消任务功能，创建一个辅助类。

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

//生成一个世纪整数数组。
public class ArrayGenerator {

    public int[] generateArray(int size){

        int array[]=new int[size];

        Random random=new Random();

        for(int i=0;i<size;i++){
            array[i]=random.nextInt(10);
        }

        return array;
    }
}

//取消ForkJoinTask任务
class TaskManager{

    private List<ForkJoinTask<Integer>> tasks;


    public TaskManager() {
        tasks=new ArrayList<>();
    }


    public void addTask(ForkJoinTask<Integer> task){
        tasks.add(task);
    }

//    接受一个剩余任务的ForkJoinTask对象作为参数，然后取消所有参数
    public void cancelTasks(ForkJoinTask<Integer> cancelTask){

        for(ForkJoinTask<Integer> task:tasks){

            if(task != cancelTask){
                task.cancel(true);
                ((SerchNumberTask)task).writeCancelMessage();
            }
        }
    }
}

//寻找在整数元素获取一个数字
class SerchNumberTask extends RecursiveTask<Integer>{

    private int numbers[];

    private int start,end;

    private int number;

    private TaskManager manager;

//    当任务找不到数字时将返回-1
    private static final int NOT_FOUND=-1;

    public SerchNumberTask(int[] numbers, int start, int end, int number, TaskManager manager) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.number = number;
        this.manager = manager;
    }

    @Override
    protected Integer compute() {

        System.out.println("Task start-end:"+start+"-"+end);

        int ret;

        if(end-start>10){

            ret=launchTasks();
        }else{
            ret=lookForNumber();
        }

        return ret;
    }

    private int lookForNumber() {

        for(int i=start;i<end;i++){

            if(numbers[i]==number){
                System.out.println("Task number found and stop other tasks");
                manager.cancelTasks(this);
                return i;
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return NOT_FOUND;
    }



    //    拆分任务
    private int launchTasks() {

        int mid=(start+end)/2;

        SerchNumberTask task1=new SerchNumberTask(numbers,start,mid,number,manager);
        SerchNumberTask task2=new SerchNumberTask(numbers,mid,end,number,manager);

        manager.addTask(task1);
        manager.addTask(task2);

//        采样异步执行方式
        task1.fork();
        task2.fork();

        int returnValue;
        returnValue=task1.join();
        if(returnValue != -1){
            return -1;
        }

        returnValue=task2.join();

        return returnValue;

    }

    public void writeCancelMessage(){

        System.out.println("Task cancel:start-end:"+start+"-"+end);
    }
}

class Main{

    public static void main(String[] args) {

        ArrayGenerator generator=new ArrayGenerator();

        int array[]=generator.generateArray(1000);

        TaskManager manager=new TaskManager();

        ForkJoinPool pool=new ForkJoinPool();

        SerchNumberTask task=new SerchNumberTask(array,0,1000,5,manager);

        pool.execute(task);

        pool.shutdown();

        try {
            pool.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main finish");
    }
}

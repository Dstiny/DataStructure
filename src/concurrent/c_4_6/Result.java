package concurrent.c_4_6;

//执行3个任务，当他们全部执行完后打印结果信息

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//存储并发任务产生的结果
public class Result {

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class Task implements Callable<Result>{

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public Result call() throws Exception {

        System.out.println("task starting:"+this.name);

        System.out.println("task waiting to result:"+this.name);
        Thread.sleep(1000);

        int value=0;
        for(int i=0;i<5;i++){
            value+=(int)(Math.random()*100);
        }

        Result result=new Result();
        result.setName(this.name);
        result.setValue(value);

        System.out.println("task end:"+this.name);

        return result;
    }
}

class Main{

    public static void main(String[] args) {

        ExecutorService executor=(ExecutorService) Executors.newCachedThreadPool();

        List<Task> taskList=new ArrayList<>();
        for(int i=0;i<3;i++){
            Task task=new Task(""+i);
            taskList.add(task);
        }

        List<Future<Result>> resultList=null;

        try {
            resultList = executor.invokeAll(taskList);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        executor.shutdown();

        System.out.println("main:printing result");
        for(int i=0;i<resultList.size();i++){

            Future<Result> future=resultList.get(i);

            try {
                Result result=future.get();
                System.out.println("main result:"+result.getName()+"    "+result.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}


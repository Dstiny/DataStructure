package concurrent.c_4_4;

//如何实现任务的返回结果，并在执行器运行任务。

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FactorialCalculator implements Callable<Integer>{

    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    public Integer call() throws Exception {

        int result=1;
        if(number==0 || number==1)
            result=1;
        else{
            for(int i=1;i<=number;i++){
                result*=i;
                Thread.sleep(1000);
            }
        }

        System.out.println("FactorialCalculator:"+result+"  Thread:"+Thread.currentThread().getName());
        return result;
    }
}

class Main{

    public static void main(String[] args) {

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        List<Future<Integer>> resultList=new ArrayList<>();

        Random random=new Random();
        for(int i=0;i<10;i++){

            Integer number=random.nextInt(10);

            FactorialCalculator calculator=new FactorialCalculator(number);

//            调用执行器的submit()方法发送任务给执行器，返回一个Future对象管理任务和最终结果
            Future<Integer> result=executor.submit(calculator);

            resultList.add(result);
        }

        do{
            System.out.println("number of completed task:"+executor.getCompletedTaskCount());

            for(int i=0;i<resultList.size();i++){

                Future<Integer> result=resultList.get(i);
                System.out.println("task done"+result.isDone());
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while(executor.getCompletedTaskCount()<resultList.size());

        for(int i=0;i<resultList.size();i++){
            Future<Integer> result=resultList.get(i);
            Integer number=null;

            try {
                number=result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("main finashed:"+number);
        }

        executor.shutdown();
    }
}

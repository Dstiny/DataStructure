package concurrent.c_7_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class MyExecutor extends ThreadPoolExecutor{

    private ConcurrentHashMap<String,Date> startTime;


    public MyExecutor(int corePoolSize,
                      int maximumPoolSize,
                      long keepAliveTime,
                      TimeUnit unit,
                      BlockingQueue<Runnable> workQueue) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);

        startTime=new ConcurrentHashMap<>();
    }

//    覆盖，将已执行的任务、正在执行的任务和等待的任务的信息输出到控制台
    @Override
    public void shutdown() {

        System.out.println("MyExecutor going to shutdown.");

        System.out.println("MyExecutor已执行完毕任务："+super.getCompletedTaskCount());

        System.out.println("MyExecutor正在执行的任务："+super.getActiveCount());

        System.out.println("MyExecutor等待完毕的任务："+super.getQueue().size());

        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {

        System.out.println("MyExecutor going to 立即 shutdown.");

        System.out.println("MyExecutor立即：已执行完毕任务："+super.getCompletedTaskCount());

        System.out.println("MyExecutor立即：正在执行的任务："+super.getActiveCount());

        System.out.println("MyExecutor立即：等待完毕的任务："+super.getQueue().size());

        return super.shutdownNow();
    }

//将任务执行结果输出，用当前时间减存放在HashMap表中的时间计算运行时间
    @Override
    protected void afterExecute(Runnable r, Throwable t) {

        Future<?> result=(Future<?>)r;

        try {

            System.out.println("MyExecutor:任务结束");

            System.out.println("MyExecutor result:"+result.get());

            Date start=startTime.remove(String.valueOf(r.hashCode()));

            Date finish=new Date();

            long diff=finish.getTime()-start.getTime();

            System.out.println("MyExecutor diff:"+diff);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

//    覆盖，输出要执行的线程的名字、任务的哈希吗（Hashcode）。开始日期存放到HashMap中，他是以任务的哈希值
//    做主键的
    @Override
    protected void beforeExecute(Thread t, Runnable r) {

        System.out.println("MyExecutor 任务开始之前   "+"线程名字："+t.getName()
                +"  hashcode:"+r.hashCode());

        startTime.put(String.valueOf(r.hashCode()),new Date());
    }

}

class SleepTwoSecondsTask implements Callable<String>{

    @Override
    public String call() throws Exception {

        TimeUnit.SECONDS.sleep(2);

        return new Date().toString();
    }
}

class Main{

    public static void main(String[] args) {

        MyExecutor myExecutor=new MyExecutor(2,4,1000,TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>());

        List<Future<String>> results=new ArrayList<>();

        for(int i=0;i<10;i++){

            SleepTwoSecondsTask task=new SleepTwoSecondsTask();
            Future<String> result=myExecutor.submit(task);
            results.add(result);
        }

        for(int i=0;i<5;i++){

            try {

                String result=results.get(i).get();

                System.out.println("Main 第"+i+"个任务结果："+result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        myExecutor.shutdown();

        for(int i=0;i<5;i++){

            try {

                String result=results.get(i).get();

                System.out.println("Main 调用shutdown 第"+i+"个任务结果："+result);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            myExecutor.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main end");

    }
}

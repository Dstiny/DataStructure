package concurrent.c_7_6;


import java.util.Date;
import java.util.concurrent.*;

public class MyScheduledTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V>{

    private RunnableScheduledFuture<V> task;

    private ScheduledThreadPoolExecutor executor;

    private long period;

    private long startDate;

    public MyScheduledTask(Runnable runnable,
                           V result,
                           RunnableScheduledFuture<V> task,
                           ScheduledThreadPoolExecutor executor) {
        super(runnable, result);
        this.task = task;
        this.executor = executor;
    }

    @Override
    public boolean isPeriodic() {

        return task.isPeriodic();
    }

//    周期性任务且startDate属性值不等于0，则计算startDate属性和当前的时间差作为返回值。
//    否则返回存放在task属性的延迟值
//    返回值需要转化为参数指定的时间单位。
    @Override
    public long getDelay(TimeUnit unit) {

        if(!isPeriodic()){

            return task.getDelay(unit);

        }else {

            if(startDate==0){

                return task.getDelay(unit);
            }else{

                Date now=new Date();
                long delay=startDate-now.getTime();
                return unit.convert(delay,TimeUnit.MILLISECONDS);
            }
        }

    }

    @Override
    public int compareTo(Delayed o) {

        return task.compareTo(o);
    }

//    如果为周期性任务，则需要任务下一次执行的开始时间更新它的startDate属性。即当前时间加上时间间隔。
//    然后再次增加到ScheduledThreadPoolExecutor对象队列中。
    @Override
    public void run() {

        if(isPeriodic() && (!executor.isShutdown())){

            Date now=new Date();

            startDate=now.getTime()+period;

            executor.getQueue().add(this);
        }

//        在控制台输出当前时间，然后调用runAndReset方法执行任务，并且将任务执行后的时间输出到控制台。
        System.out.println("MyScheduledTask:"+new Date());

        System.out.println("MyScheduledTask Isperiod:"+isPeriodic());

        super.runAndReset();

        System.out.println("MyScheduledTask Re:"+new Date());
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}

//执行MyScheduledTask任务
class MySchduledThreadPoolExecutor extends ScheduledThreadPoolExecutor{

    public MySchduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

//    实现RunnableScheduledFuture()。接受将被执行的Runnable对象作为参数。
// 另一个参数是RunnableScheduledFuture任务，用来执行这个Runnable对象
//    返回MyScheduledTask任务
    @Override
    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {

        MyScheduledTask<V> myTask=new MyScheduledTask<>(runnable,null,task,this);

        return myTask;
    }

//    调用父类的方法，把返回的对象强制转换为一个MyScheduledTask对象，并使用setPeriod()设置任务日期
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                                  long period, TimeUnit unit) {

        ScheduledFuture<?> task=super.scheduleAtFixedRate(command,initialDelay,period,unit);

        MyScheduledTask myTask=(MyScheduledTask)task;

        myTask.setPeriod(TimeUnit.MILLISECONDS.convert(period,unit));

        return task;
    }
}

class Task implements Runnable{



    @Override
    public void run() {

        System.out.println("Task start");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Task end");
    }
}

class Main{

    public static void main(String[] args) throws Exception {

        MySchduledThreadPoolExecutor executor=new MySchduledThreadPoolExecutor(2);

        Task task=new Task();

        System.out.println("Main:"+new Date());

//        发送一个延迟任务
        executor.schedule(task,1,TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(3);

//        创建另一个线程
        task=new Task();

//        发送一个周期性任务到执行器
        executor.scheduleAtFixedRate(task,1,3,TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(5);

        executor.shutdown();

        executor.awaitTermination(1,TimeUnit.DAYS);

        System.out.println("main end");
    }
}

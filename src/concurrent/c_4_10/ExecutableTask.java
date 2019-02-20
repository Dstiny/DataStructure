package concurrent.c_4_10;

//done()默认没有实现，覆盖并实现

import java.util.concurrent.*;

public class ExecutableTask implements Callable<String>{

    private String name;

    public String getName() {
        return name;
    }

    public ExecutableTask(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {

        System.out.println("task waiting:"+name);
        Thread.sleep(1000);
        return "hello:"+name;
    }
}

class ResultTask extends FutureTask<String>{

    private String name;

    public ResultTask(Callable<String> callable) {
        super(callable);
        this.name=((ExecutableTask)callable).getName();
    }

    @Override
    protected void done() {
        if(isCancelled()){
            System.out.println("done been canceled:"+name);
        }else{
            System.out.println("done been finish");
        }
    }
}

class Main{

    public static void main(String[] args) {

        ExecutorService executor=(ExecutorService) Executors.newCachedThreadPool();

        ResultTask result[]=new ResultTask[5];

        for(int i=0;i<5;i++){
            ExecutableTask task=new ExecutableTask("Task"+i);
            result[i]=new ResultTask(task);
            executor.submit(result[i]);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0;i<result.length;i++){
            result[i].cancel(true);
        }

        for(int i=0;i<result.length;i++){

            if(! result[i].isCancelled()){

                try {
                    System.out.println(result[i].get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
    }
}

package concurrent.c_4_11;

//分离任务的启动与结果的处理。

import java.util.concurrent.*;

public class ReportGenerator implements Callable<String>{

//    表示报告的数据
    private String sender;
    private String title;

    public ReportGenerator(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }

    @Override
    public String call() throws Exception {

        System.out.println("report  "+"sender:"+this.sender+"   title:"+this.title);

        Thread.sleep(1000);

        String ret=sender+":"+title;

        return ret;
    }
}

//请求获取报告
class ReportRequest implements Runnable{

    private String name;

    private CompletionService<String> service;

    public ReportRequest(String name, CompletionService<String> service) {
        this.name = name;
        this.service = service;
    }

    public void run() {

        ReportGenerator reportGenerator=new ReportGenerator(name,"Report");
//        将ReportGenerator对象发送给CompletionService对象。
        service.submit(reportGenerator);
    }
}

//获取到ReportGenerator任务的结果。
class ReportProcess implements Runnable{

    private CompletionService service;

    private boolean end;

    public ReportProcess(CompletionService service) {
        this.service = service;
        end=false;
    }

    @Override
    public void run() {

        while(!end){

            try{
//              poll()获取下一个已经完成的任务
                Future<String> result=service.poll(20, TimeUnit.SECONDS);

                if(result!=null){
                    String report=result.get();
                    System.out.println("result:"+report);
                }
            }catch(InterruptedException | ExecutionException e){

            }
        }

        System.out.println("get result finish");
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}

class Main{

    public static void main(String[] args) {

        ExecutorService executor=(ExecutorService)Executors.newCachedThreadPool();

        CompletionService<String> service=new ExecutorCompletionService<>(executor);

        ReportRequest faceRequest=new ReportRequest("Face",service);
        ReportRequest onlineRequest=new ReportRequest("Online",service);

        Thread faceThread=new Thread(faceRequest);
        Thread onlineThread=new Thread(onlineRequest);

        ReportProcess process=new ReportProcess(service);
        Thread proThread=new Thread(process);

        System.out.println("main:starting");
        faceThread.start();
        onlineThread.start();
        proThread.start();

        try {
            faceThread.join();
            onlineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        process.setEnd(true);

        System.out.println("main end");

    }
}

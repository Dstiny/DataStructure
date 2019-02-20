package concurrent.c_5_4;

//使用异步管理任务
//在一个指定文件夹及其子文件夹搜索带有指定扩展名的文件。
//ForkJoinTask类将实现处理这个文件夹的内容，而对于这个文件夹中每一个子文件，任务将以异步的方式发送一个新的任务给
// ForkJoinPool类
//对于每个文件夹中的文件，任务将检查任务文件的扩展名，符合条件添加到结果列表中。

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class FolderProcessor extends RecursiveTask<List<String>>{

    private static final long serialVersionUID=1L;

    private String path;

    private String extention;       //查找文件的扩展名

    public FolderProcessor(String path, String extention) {
        this.path = path;
        this.extention = extention;
    }

    @Override
    protected List<String> compute() {

        List<String> list=new ArrayList<>();

        List<FolderProcessor> tasks=new ArrayList<>();       //存储子任务，处理文件中的子文件夹

        File file=new File(path);
        File content[]=file.listFiles();

        if(content != null){

            for(int i=0;i<content.length;i++){

                if(content[i].isDirectory()){

                    FolderProcessor task=new FolderProcessor(content[i].getAbsolutePath(),extention);

                    task.fork();

                    tasks.add(task);
                }else{

                    if(checkFile(content[i].getName())){
                        list.add(content[i].getAbsolutePath());
                    }
                }
            }

//            子任务超过50个，那么就在控制台输出信息
            if(tasks.size()>50){
                System.out.println("tasks :"+file.getAbsolutePath()+" "+tasks.size());
            }

//            辅助方法，通过这个任务而启动的子任务返回的结果增加到文件列表中。
            addResultsFromTask(list,tasks);
        }

        return list;
    }

    private void addResultsFromTask(List<String> list, List<FolderProcessor> tasks) {

        for(FolderProcessor item:tasks){
            list.addAll(item.join());
        }
    }

    private boolean checkFile(String name) {

        return name.endsWith(extention);
    }
}
class Main{

    public static void main(String[] args) {

        ForkJoinPool pool=new ForkJoinPool();

        FolderProcessor system = new FolderProcessor("C:\\Windows", "log");
        FolderProcessor apps = new FolderProcessor("C:\\Program Files", "log");
        FolderProcessor documents = new FolderProcessor("C:\\Documents And Settings", "log");

        pool.execute(system);
        pool.execute(apps);
        pool.execute(documents);

//        状态信息
        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while((!system.isDone()) || (!apps.isDone()) || (!documents.isDone()));

        pool.shutdown();

        List<String> results;
        results=system.join();
        System.out.println("main system:"+results.size());

        results=apps.join();
        System.out.println("main apps:"+results.size());

        results=documents.join();
        System.out.println("main documents:"+results.size());
    }
}

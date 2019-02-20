package concurrent.c_1_5;

//在一个文件夹下寻找一个指定文件

import java.io.File;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable{

    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        File file=new File(initPath);

        if(file.isDirectory()){

            try{
                directoryProcess(file);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

//    获取一个文件夹下所有文件及子文件夹。子文件夹递归调用，文件将调用fileProcess()
    private void directoryProcess(File file) throws InterruptedException{

        File  list[]=file.listFiles();

        if(list != null){

            for(int i=0;i<list.length;i++){

                if(list[i].isDirectory()) directoryProcess(list[i]);
                else fileProcess(list[i]);
            }
        }

        if(Thread.interrupted()){
            throw new InterruptedException();
        }
    }
//比较当前文件名称与查找文件名称进行匹配。昨晚比较检查线程是否被中断，是就抛出中断异常
    private void fileProcess(File file) throws InterruptedException {

        if(file.getName().equals(fileName)){
            System.out.println("匹配成功");
        }

        if(Thread.interrupted()){
            throw new InterruptedException();
        }
    }
}

class Main{

    public static void main(String[] args) {

        FileSearch searcher=new FileSearch("","");
        Thread thread=new Thread(searcher);

        thread.start();

//        等待10s中断线程
        try{
            TimeUnit.SECONDS.sleep(10);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        thread.interrupt();
    }
}

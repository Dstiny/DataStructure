package concurrent.c_1_13;

//学习通过实现ThreadFactory接口创建1线程对象那个

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MyThreadFactory implements ThreadFactory{

    private int count;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        count=0;
        this.name = name;
        stats=new ArrayList<>();
    }

    public Thread newThread(Runnable r) {
        Thread t=new Thread(r,name+"-thread-"+count);
        count++;
        stats.add(String.format("thread %s:%s",t.getId(),new Date()));
        return t;
    }

//    表示所有线程对象的统计数据
    public String getStats(){

        StringBuffer stringBuffer=new StringBuffer();
        Iterator<String> it=stats.iterator();
        while(it.hasNext()){
            stringBuffer.append(it.next());
            stringBuffer.append("\n");
        }

        return stringBuffer.toString();
    }
}

class Task implements Runnable{

    public void run() {

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Main{

    public static void main(String[] args) {

        MyThreadFactory factory=new MyThreadFactory("factory");

        Task task=new Task();

        Thread thread;
        for(int i=0;i<10;i++){
            thread=factory.newThread(task);
        }

        System.out.println("stats:"+factory.getStats());
    }
}

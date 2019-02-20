package concurrent.c_6_7;

import java.util.concurrent.ThreadLocalRandom;

public class TaskLocalRandom implements Runnable{

    public TaskLocalRandom() {

//        为当前线程初始化随机数生成器
        ThreadLocalRandom.current();
    }

    @Override
    public void run() {

        String name=Thread.currentThread().getName();

        for(int i=0;i<10;i++){

            System.out.println("Task Thread  name:"+name+"随机数:"
                    +ThreadLocalRandom.current().nextInt(9,15));
        }

    }
}

class Main{

    public static void main(String[] args) {

        Thread t[]=new Thread[3];

        for(int i=0;i<3;i++){
            TaskLocalRandom task=new TaskLocalRandom();
            t[i]=new Thread(task);
            t[i].start();
        }
    }
}

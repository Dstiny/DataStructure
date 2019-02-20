package concurrent.c_6_2;

//添加大量的数据到一个列表中，从同一个列表移除大量数据。

import java.util.concurrent.ConcurrentLinkedDeque;

public class AddTask implements Runnable{

    private ConcurrentLinkedDeque<String> list;

    public AddTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {

        String name=Thread.currentThread().getName();
        for(int i=0;i<10000;i++){
            list.add(name+":Element:"+i);
        }
    }
}

class PollTask implements Runnable{

    private ConcurrentLinkedDeque<String> list;

    public PollTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {

        for(int i=0;i<5000;i++){
            list.pollFirst();
            list.pollLast();
        }
    }
}

class Main{

    public static void main(String[] args) {

        ConcurrentLinkedDeque<String> list=new ConcurrentLinkedDeque<>();

        Thread thread[]=new Thread[100];

        for(int i=0;i<thread.length;i++){
            AddTask task=new AddTask(list);
            thread[i]=new Thread(task);
            thread[i].start();
        }

        System.out.println("main AddTask threads been launched:"+thread.length);

        for(int i=0;i<thread.length;i++){

            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("main AddTask list size:"+list.size());

        for(int i=0;i<thread.length;i++){
            PollTask task=new PollTask(list);
            thread[i]=new Thread(task);
            thread[i].start();
        }

        System.out.println("main PollTask been launched:"+thread.length);

        for(int i=0;i<thread.length;i++){

            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("main PollTask list size:"+list.size());

    }
}

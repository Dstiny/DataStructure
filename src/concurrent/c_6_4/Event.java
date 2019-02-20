package concurrent.c_6_4;

//存放大量不同优先级的事件存放到一个列表中


import java.util.concurrent.PriorityBlockingQueue;

public class Event implements Comparable<Event>{

    private int thread;

    private int priority;

    public Event(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public int getThread() {
        return thread;
    }

    @Override
    public int compareTo(Event o) {

        if(this.getPriority()>o.getPriority()){
            return -1;
        }else if(this.getPriority()<o.getPriority()){
            return 1;
        }else{
            return 0;
        }

    }
}

class Task implements Runnable{

    private int id;

    private PriorityBlockingQueue<Event> queue;

    public Task(int id, PriorityBlockingQueue<Event> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {

        for(int i=0;i<100;i++){
            Event event=new Event(id,i);
            queue.add(event);
        }
    }
}

class Main{

    public static void main(String[] args) {

        PriorityBlockingQueue<Event> queue=new PriorityBlockingQueue<>();

        Thread thread[]=new Thread[5];

        for(int i=0;i<thread.length;i++){
            Task task=new Task(i,queue);
            thread[i]=new Thread(task);
        }

        for(int i=0;i<5;i++){
            thread[i].start();
        }

        for(int i=0;i<5;i++){

            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main Queue size:"+queue.size());

        for(int i=0;i<queue.size()*5;i++){

            Event event=queue.poll();
            System.out.println("Main Thread:"+event.getThread()+"   Priority:"+event.getPriority());
        }

        System.out.println("main finish");
    }
}

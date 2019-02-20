package concurrent.c_6_5;

//存放具有不同激活日期的event

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Event implements Delayed {


    private Date startDate;

    public Event(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public long getDelay(TimeUnit unit) {

        Date now=new Date();

        long diff=this.startDate.getTime()-now.getTime();

        return unit.convert(diff,TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {

        long result=this.getDelay(TimeUnit.MILLISECONDS)-o.getDelay(TimeUnit.MILLISECONDS);

        if(result>0){
            return 1;
        }else if(result<0){
            return -1;
        }else{
            return 0;
        }
    }
}

class Task implements Runnable{

    private int id;

    private DelayQueue<Event> queue;

    public Task(int id, DelayQueue<Event> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {

        Date now=new Date();

        Date delay=new Date();

        delay.setTime(now.getTime()+(id*1000));

        System.out.println("Task Thread:"+id+"  delay:"+delay);

        for(int i=0;i<100;i++){

            Event event=new Event(delay);
            queue.add(event);
        }
    }
}

class Main{

    public static void main(String[] args) throws Exception {

        DelayQueue<Event> queue=new DelayQueue<>();

        Thread t[]=new Thread[5];

        for(int i=0;i<5;i++){
            Task task=new Task(i+1,queue);
            t[i]=new Thread(task);
        }

        for(int i=0;i<5;i++){
            t[i].start();
        }

        for(int i=0;i<5;i++){
            t[i].join();
        }

        do{
            int counter=0;

            Event event;

            do{
                event=queue.poll();

                if(event != null) counter++;
            }while(event != null);

            System.out.println("read on time counter:"+new Date()+" "+counter);

            TimeUnit.MILLISECONDS.sleep(200);
        }while(queue.size()>0);

    }
}

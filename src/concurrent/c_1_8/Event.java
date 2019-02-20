package concurrent.c_1_8;

//用户线程：将事件写入到一个队列中。守护线程，管理这个队列，如果生成的时间超过10s就移除。

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

public class Event {

    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}



class WriteTask implements Runnable{

    private Deque<Event> deque;

    public WriteTask(Deque<Event> deque) {
        this.deque = deque;
    }


    public void run() {

        for(int i=0;i<100;i++){

            Event event=new Event();

            event.setDate(new Date());
            event.setEvent(String.format("%s",Thread.currentThread().getId()));

            deque.addFirst(event);

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}


class CleannerTask extends Thread{

    private Deque<Event> deque;

    public CleannerTask(Deque<Event> deque){
        this.deque=deque;
        setDaemon(true);            //  设置为守护线程
    }

    public void run() {

        while(true){
            Date date=new Date();
            clean(date);
        }
    }

    private void clean(Date date) {

        long difference;
        boolean delete;

        if(deque.size()==0){
            return ;
        }

        delete=false;

        do{
            Event e=deque.getLast();
            difference=date.getTime()-e.getDate().getTime();

            if(difference>10000){
                System.out.println("clean "+e.getEvent());
                deque.removeLast();
                delete=true;
            }

        }while(difference>10000);

        if(delete) System.out.println("clean_size "+deque.size());

    }
}

class Main{

    public static void main(String[] args) {

        Deque<Event> deque=new ArrayDeque<Event>();

        WriteTask writeTask=new WriteTask(deque);

        for(int i=0;i<3;i++){
            Thread thread=new Thread(writeTask);
            thread.start();
        }

        CleannerTask cleannerTask=new CleannerTask(deque);

        cleannerTask.start();
    }
}
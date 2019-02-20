package concurrent.c_2_4;

//生产者，消费者。

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//缓冲区
public class EventStorage {

    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize=10;
        storage=new LinkedList<Date>();
    }

    public synchronized void set(){

        while(storage.size()==maxSize){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.println("storage:"+storage.size());
        notifyAll();
    }

    public synchronized void get(){

        while(storage.size()==0){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("storage:"+storage.size()+"      获取："+((LinkedList<?>)storage).poll());
        notifyAll();
    }
}

//生产者
class Product implements Runnable{

    private EventStorage storage;

    public Product(EventStorage storage) {
        this.storage = storage;
    }

    public void run() {

        for(int i=0;i<50;i++){
            storage.set();
        }
    }
}

//消费者
class Consumer implements Runnable{

    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    public void run() {

        for(int i=0;i<50;i++){
            storage.get();
        }
    }
}

class Main{

    public static void main(String[] args) {

        EventStorage storage=new EventStorage();

        Product product=new Product(storage);
        Thread thread1=new Thread(product);

        Consumer consumer=new Consumer(storage);
        Thread thread2=new Thread(consumer);

        thread1.start();
        thread2.start();
    }
}

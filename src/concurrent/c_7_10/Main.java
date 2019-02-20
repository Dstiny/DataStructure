package concurrent.c_7_10;

//一个生产者，消费者程序，数据是按照某种优先级被消费

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class MyPriorityTransferQueue<E> extends PriorityBlockingQueue<E> implements TransferQueue<E>{


    private AtomicInteger counter;  //存储等待消费元素的消费者数量

    private LinkedBlockingDeque<E> transfered;

    private ReentrantLock lock;

    public MyPriorityTransferQueue(){
        counter=new AtomicInteger(0);
        lock=new ReentrantLock();
        transfered=new LinkedBlockingDeque<>();
    }

//    这个方法尝试立即将元素发送到一个正在等待的消费者。如果没有等待的消费者，返回false
    @Override
    public boolean tryTransfer(E e) {

        lock.lock();

        boolean value;

        if(counter.get()==0){

            value=false;
        }else{
            put(e);
            value=true;
        }

        lock.unlock();

        return value;
    }


//    这个方法尝试立即将元素发送到一个正在等待的消费者，如果没有等待的消费者，该方法将元素存储到transfered
//    队列，并等待出现视图获取元素的第一个消费者。在这之前，线程将被阻塞
    @Override
    public void transfer(E e) throws InterruptedException {

        lock.lock();

        if(counter.get()!=0){
            put(e);
            lock.unlock();
        }else{
            transfered.add(e);
            lock.unlock();
            synchronized (e){
                e.wait();
            }
        }
    }

//    第一个参数表示生产和消费的元素，第二个参数为没有消费元素等待的时间。
//    没有消费者将参数指定的时间转化为毫秒并使用wait()让线程休眠，当消费者取元素时，入伙线程任然休眠，
//    将使用notify()唤醒
    @Override
    public boolean tryTransfer(E e, long timeout, TimeUnit unit) throws InterruptedException {

        lock.lock();

        if(counter.get()!=0){
            put(e);
            lock.unlock();
            return true;
        }else{
            transfered.add(e);
            long time=TimeUnit.MILLISECONDS.convert(timeout,unit);
            lock.unlock();
            e.wait();

            lock.lock();

            if(transfered.contains(e)){
                transfered.remove(e);
                lock.unlock();
                return false;
            }else{
                lock.unlock();
                return true;
            }
        }

    }

//    使用counter计算该方法的返回值
    @Override
    public boolean hasWaitingConsumer() {
        return (counter.get()!=0);
    }


    @Override
    public int getWaitingConsumerCount() {
        return counter.get();
    }

//    该方法将准备消费元素的消费者调用。首先，获取之前定义的锁，然后增加正在等待的消费者数量
    @Override
    public E take() throws InterruptedException {

        lock.lock();

        counter.incrementAndGet();

//        如果transfered没有元素，则释放锁并尝试使用take()方法从队列取得一个元素并在此获取所。
//        如果队列内没元素，该方法将线程休眠知道有元素。
        E value=transfered.poll();
        if(value==null){
            lock.unlock();
            value=super.take();
            lock.lock();
        }else{

            synchronized (value){
                value.notify();
            }
        }

//        减少消费者数量并释放锁
        counter.decrementAndGet();

        lock.unlock();

        return super.take();
    }
}

class Event implements Comparable<Event>{

    private String thread;

    private int priority;

    public Event(String name, int priority) {
        this.thread = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Event o) {

        if(this.getPriority()>o.getPriority()) return -1;

        else if(this.getPriority()<o.getPriority()) return 1;

        return 0;
    }

    public String getThread() {
        return thread;
    }

    public int getPriority() {
        return priority;
    }
}

class Producer implements Runnable{

    private MyPriorityTransferQueue<Event> buffer;

    public Producer(MyPriorityTransferQueue<Event> buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {

        for(int i=0;i<100;i++){
            Event event=new Event(Thread.currentThread().getName(),i);
            buffer.put(event);
        }
    }
}

class Consumer implements Runnable{

    private MyPriorityTransferQueue<Event> buffer;

    public Consumer(MyPriorityTransferQueue<Event> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        for(int i=0;i<1002;i++){

            try {

                Event event=buffer.take();

                System.out.println("Consumer thread:"+event.getThread()
                        +"   priority:"+event.getPriority());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {

    public static void main(String[] args) throws Exception {

        MyPriorityTransferQueue<Event> buffer=new MyPriorityTransferQueue<>();

        Producer producer=new Producer(buffer);

        Thread producerThread[]=new Thread[10];
        for(int i=0;i<producerThread.length;i++){
            producerThread[i]=new Thread(producer);
            producerThread[i].start();
        }

        Consumer consumer=new Consumer(buffer);
        Thread consumerThread=new Thread(consumer);
        consumerThread.start();

        System.out.println("Main 实际的消费者数量:"+buffer.getWaitingConsumerCount());

//        使用transfer（）把一个时间传递给消费者
        Event coreEvent=new Event("coreEvent—1",0);
        System.out.println("Main coreEvent has Been tranfered");

        for(int i=0;i<producerThread.length;i++){

            producerThread[i].join();
        }

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Main 实际的消费者数量:"+buffer.getWaitingConsumerCount());

        coreEvent=new Event("coreEvent-2",0);

        buffer.transfer(coreEvent);

        consumerThread.join();

        System.out.println("Main end");

    }
}

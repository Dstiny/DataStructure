package concurrent.c_7_9;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class MyAbstractQueuedSynchronizer extends AbstractQueuedSynchronizer {

    private AtomicInteger state;

    public MyAbstractQueuedSynchronizer() {
        state=new AtomicInteger(0);
    }

//    用来把state变为1
    @Override
    protected boolean tryAcquire(int arg) {

        return compareAndSetState(0,1);
    }

//    用来把0变为1
    @Override
    protected boolean tryRelease(int arg) {
        return compareAndSetState(1,0);
    }
}

class MyLock implements Lock{

    private AbstractQueuedSynchronizer sync;

    public MyLock() {
        sync=new MyAbstractQueuedSynchronizer();
    }

    @Override
    public void lock() {

        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {

        try {
            return sync.tryAcquireNanos(1,1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {


        return sync.tryAcquireNanos(1,TimeUnit.NANOSECONDS.convert(time,unit));
    }

    @Override
    public void unlock() {

        sync.release(1);
    }

    @Override
    public Condition newCondition() {

        return sync.new ConditionObject();
    }
}


class Task implements Runnable{

    private MyLock lock;

    private String name;

    public Task(MyLock lock, String neme) {
        this.lock = lock;
        this.name = neme;
    }


    @Override
    public void run() {

        lock.lock();

        System.out.println("Task take lock name:"+name);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


public class Main {

    public static void main(String[] args) {

        MyLock lock=new MyLock();

        for(int i=0;i<10;i++){

            Task task=new Task(lock,"Task"+i);
            Thread t=new Thread(task);
            t.start();
        }

        boolean value;

        do{
            try {

                value=lock.tryLock(1,TimeUnit.SECONDS);

                if(!value){
                    System.out.println("Main try get lock");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                value=false;
            }
        }while(!value);

        System.out.println("Main get the lock");

        lock.unlock();

        System.out.println("Main end");
    }
}

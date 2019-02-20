package concurrent.c_6_9;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class Incrementer implements Runnable{

    private AtomicIntegerArray vector;

    public Incrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {

        for(int i=0;i<vector.length();i++){

            vector.getAndIncrement(i);          //增加数组中的元素
        }
    }
}

class Decrementer implements Runnable{

    private AtomicIntegerArray vector;

    public Decrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {

        for(int i=0;i<vector.length();i++){
            vector.getAndDecrement(i);
        }
    }
}

class Main{

    public static void main(String[] args) {

        final int THREADS=10;

        AtomicIntegerArray vector=new AtomicIntegerArray(200);

        Incrementer incrementer=new Incrementer(vector);

        Decrementer decrementer=new Decrementer(vector);

        Thread threadIncrement[]=new Thread[10];

        Thread threadDecrementer[]=new Thread[10];

        for(int i=0;i<THREADS;i++){

            threadIncrement[i]=new Thread(incrementer);
            threadDecrementer[i]=new Thread(decrementer);

            threadIncrement[i].start();
            threadDecrementer[i].start();
        }

        for(int i=0;i<THREADS;i++){

            try {
                threadIncrement[i].join();
                threadDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<vector.length();i++){

            if(vector.get(i) != 0){
                System.out.println(" vector:"+vector.get(i));
            }
        }

        System.out.println("main end");
    }
}

package concurrent.c_3_8;

//Exchange类，一个生产者，一个消费者

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable{

    private List<String> buffer;        //交换的数据结构

    private final Exchanger<List<String>> exchanger;

    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        int cycle=1;
        for(int i=0;i<10;i++){

            System.out.println("producer cycleing:"+cycle);

            for(int j=0;j<10;j++){
                String message="producer event:"+((i*10)+j);
                System.out.println("producer message"+message);
                buffer.add(message);
            }

            try{
                buffer=exchanger.exchange(buffer);
                System.out.println("producer buffer siz:"+buffer.size());
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            cycle++;
        }

    }
}

class Consumer implements Runnable{

    private List<String> buffer;

    private final Exchanger<List<String>> exchanger;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        int cycle=1;
        for(int i=0;i<10;i++){

            System.out.println("consumer cycle:"+cycle);

            try{
                buffer=exchanger.exchange(buffer);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("consumer buffer size:"+buffer.size());

            for(int j=0;j<10;j++){
                String message=buffer.get(0);
                System.out.println("consumer message:"+message);
                buffer.remove(0);
            }

            cycle++;
        }
    }

}

class Main{

    public static void main(String[] args) {

        List<String> buffer1=new ArrayList<>();
        List<String> buffer2=new ArrayList<>();

        Exchanger<List<String>> exchanger=new Exchanger<>();

        Producer producer=new Producer(buffer1,exchanger);
        Consumer consumer=new Consumer(buffer2,exchanger);

        Thread t1=new Thread(producer);
        Thread t2=new Thread(consumer);

        t1.start();
        t2.start();

    }
}
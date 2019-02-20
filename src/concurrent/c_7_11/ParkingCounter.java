package concurrent.c_7_11;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingCounter extends AtomicInteger{

    private int maxNumber;      //存放停车场存放汽车最大数量

    public ParkingCounter(int maxNumber){
        set(0);
        this.maxNumber=maxNumber;
    }

//    实现一个carIn方法如果值小于指定的最大值。这个方法就增加车的数量。
// 构建一个无限循环并使用get()取得内部计数器的值
    public boolean carIn(){

        for(;;){

            int value=get();

            if(value==maxNumber){

                System.out.println("ParkingCounter paking full.");

                return false;

//                否则将内部计数器加1作为一个新值，并使用compareAndSet方法将内部计数器
//                设置为这个新值。
//                如果change结果返回false，那么计数器不被增加，再次循环
            }else{

                int newValue=value+1;
                boolean change=compareAndSet(value,newValue);

                if(change){
                    System.out.println("ParkingCounter car in");
                    return true;
                }
            }
        }
    }

//    车数量大于0，这个方法将减少车数量
    public boolean carOut(){

        for(;;){

            int value=get();

            if(value==0){
                System.out.println("ParkingCounter parking empty.");
                return false;
            }else{

                int newValue=value-1;

                boolean change=compareAndSet(value,newValue);

                if(change){
                    System.out.println("ParkingCounter car out");
                    return true;
                }
            }
        }
    }
}

class Sensor1 implements Runnable{

    private ParkingCounter counter;

    public Sensor1(ParkingCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {

        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();

        counter.carOut();
        counter.carOut();
        counter.carOut();

        counter.carIn();
        counter.carIn();
        counter.carIn();
    }
}

class Sensor2 implements Runnable{

    private ParkingCounter counter;

    public Sensor2(ParkingCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {

        counter.carOut();
        counter.carOut();
        counter.carIn();
        counter.carIn();
        counter.carIn();
        counter.carIn();
    }
}

class Main{

    public static void main(String[] args) throws InterruptedException {

        ParkingCounter counter=new ParkingCounter(5);

        Sensor1 sensor1=new Sensor1(counter);

        Sensor2 sensor2=new Sensor2(counter);

        Thread t1=new Thread(sensor1);

        Thread t2=new Thread(sensor2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Main counter:"+counter.get());

        System.out.println("Main end");
    }
}

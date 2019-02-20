package concurrent.c_2_6;

//使用ReentrantReadWriteLock接口控制价格对象的访问，价格对象存储了两个产品价格

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PriceInfo {

    private double price1;
    private double price2;

    private ReadWriteLock lock;

    public PriceInfo() {
        price1=1.0;
        price2=2.0;
        lock=new ReentrantReadWriteLock();
    }

    public double getPrice1() {
        lock.readLock().lock();
        double value=price1;
        lock.readLock().unlock();
        return value;
    }

    public double getPrice2() {
        lock.readLock().lock();
        double value=price2;
        lock.readLock().unlock();
        return value;
    }

    public void setPrice(double price1,double price2){

        lock.writeLock().lock();
        this.price1=price1;
        this.price2=price2;
        lock.writeLock().unlock();
    }
}

//读取价格类
class Read implements Runnable{

    private PriceInfo priceInfo;

    public Read(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public void run() {

        for(int i=0;i<7;i++){
            System.out.println("price1:"+priceInfo.getPrice1()+"    thread:"+Thread.currentThread().getName());
            System.out.println("price2:"+priceInfo.getPrice2()+"    thread:"+Thread.currentThread().getName());
        }
    }
}

class Write implements Runnable{

    private PriceInfo priceInfo;

    public Write(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    public void run() {

        for(int i=0;i<3;i++){
            System.out.println("start to change price");
            priceInfo.setPrice(Math.random()*10,Math.random()*8);
            System.out.println("end to change price");
            try{
                Thread.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}

class Main{

    public static void main(String[] args) {

        PriceInfo priceInfo=new PriceInfo();

        Read read[]=new Read[5];
        Thread tRead[]=new Thread[5];

        for(int i=0;i<5;i++){
            read[i]=new Read(priceInfo);
            tRead[i]=new Thread(read[i]);
        }

        Write write=new Write(priceInfo);
        Thread tWrite=new Thread(write);

        for(int i=0;i<5;i++){
            tRead[i].start();
        }

        tWrite.start();
    }
}

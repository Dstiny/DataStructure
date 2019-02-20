package concurrent.c_2_2;

//两个线程访问一个对象。一个银行账户和两个线程。一个线程转钱到账户，另一个取钱。同步机制

public class Acount {           //账户类

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//    转钱
    public synchronized void addAmount(double amount){

        double temp=balance;
        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        temp+=amount;
        balance=temp;
    }

//    取钱
    public synchronized void subtractAmount(double amount){
        double temp=balance;

        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        temp-=amount;
        balance=temp;
    }
}

//银行取钱，调用账户类的subtractAmount方法
class Bank implements Runnable{

    private Acount acount;

    public Bank(Acount acount) {
        this.acount = acount;
    }

    public void run() {
        for(int i=0;i<50;i++){
            acount.subtractAmount(1000);
        }
    }
}

//公司打钱，调用账户类的addAmount方法
class Company implements Runnable{

    private Acount acount;

    public Company(Acount acount) {
        this.acount = acount;
    }

    public void run() {
        for(int i=0;i<50;i++){
            acount.addAmount(1000);
        }
    }
}


class Mian{

    public static void main(String[] args) {

        Acount acount=new Acount();
        acount.setBalance(1000);

        Company company=new Company(acount);
        Thread t1=new Thread(company);

        Bank bank=new Bank(acount);
        Thread t2=new Thread(bank);

        System.out.println("start:"+acount.getBalance());

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
            System.out.println("end:"+acount.getBalance());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
package concurrent.c_6_8;

//原子变量实现一个银行账户和两个不同的任务：一个加钱到账户，一个取钱。

import java.util.concurrent.atomic.AtomicLong;

public class Acount {

    private AtomicLong balance;

    public Acount() {
        balance=new AtomicLong();
    }

    public long getBalance() {

        return balance.get();
    }

    public void setBalance(long balance) {

        this.balance.set(balance);
    }

    public void addAmount(long amount){
        this.balance.getAndAdd(amount);
    }

    public void substractAmount(long amount){
        this.balance.getAndAdd(-amount);
    }
}

//模拟公司汇款
class Company implements Runnable{

    private Acount acount;

    public Company(Acount acount) {
        this.acount = acount;
    }

    @Override
    public void run() {

        for(int i=0;i<10;i++){
            acount.addAmount(1000);
        }
    }
}

//银行取钱
class Bank implements Runnable{

    private Acount acount;

    public Bank(Acount acount) {
        this.acount = acount;
    }

    @Override
    public void run() {

        for(int i=0;i<10;i++){
            acount.substractAmount(1000);
        }
    }
}

class Main{

    public static void main(String[] args) {

        Acount acount=new Acount();

        acount.setBalance(1000);

        Company company=new Company(acount);
        Thread companyThread=new Thread(company);

        Bank bank=new Bank(acount);
        Thread bankThread=new Thread(bank);

        System.out.println("初始余额："+acount.getBalance());

        companyThread.start();
        bankThread.start();

        try {
            companyThread.join();
            bankThread.join();
            System.out.println("最后余额："+acount.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

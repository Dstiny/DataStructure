package concurrent.c_1_4;

//运行5s后通过中断机制强制终止

public class PrimeGenerator extends Thread{


    public void run() {

        long number=1L;

        while(true){

            if(isPrime(number)) System.out.printf("%d是素数 ",number);

            if(isInterrupted()){
                System.out.println("已被终止");
                return ;
            }

            number++;
        }
    }

//    计算是不是质数
    private boolean isPrime(long number) {
        if(number<=2) return true;

        for(int i=2;i<number;i++){

            if(number%i==0) return false;
        }

        return false;
    }
}

class Main{

    public static void main(String[] args) {

        Thread thread=new PrimeGenerator();
        thread.start();

        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(thread.getState());
        thread.interrupt();

    }
}

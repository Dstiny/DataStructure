package concurrent.c_1_2;

//第一种方法

class Calculator1 extends Thread{

    private int number;

    public Calculator1(int number) {
        this.number = number;
    }

    @Override
    public void run() {

        for(int i=1;i<=10;i++){
            System.out.printf("%s : %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
        }
    }
}

class Main1{

    public static void main(String[] args) {

        for(int i=0;i<10;i++){
            Calculator1 calculator1=new Calculator1(10);
            calculator1.start();
        }

    }
}


//第二种方法：

class Calculator2 implements Runnable{

    private int number;

    public Calculator2(int number) {
        this.number = number;
    }

    public void run() {
        for(int i=1;i<=10;i++){
            System.out.printf("%s : %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
        }
    }

}
class Main2{
    public static void main(String[] args) {

        for(int i=1;i<=10;i++){

            Calculator2 calculator=new Calculator2(10);
            Thread thread=new Thread(calculator);
            thread.start();
        }

    }
}

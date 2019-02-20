package concurrent.c_3_6_7;

//5个学生参加考试，共3道题，要求所有学生到齐才能开始考试，全部做完第一题，才能第二题，后第三题

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser{

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {

        switch(phase){

            case 0:
                return studentArrived();
            case 1:
                return finashFirst();
            case 2:
                return finashSecond();
            case 3:
                return finashLast();
            default:
                return true;
        }
    }

    private boolean finashLast() {
        System.out.println("第3题做完");
        return true;
    }

    private boolean finashSecond() {
        System.out.println("第2题做完");
        return false;
    }

    private boolean finashFirst() {
        System.out.println("第1题做完");
        return false;
    }

    private boolean studentArrived() {
        System.out.println("学生到齐");
        return false;

    }
}

class StudentTask implements Runnable{

    private Phaser phaser;

    public StudentTask(Phaser phaser) {
        this.phaser = phaser;
    }


    public void run() {

        System.out.println(Thread.currentThread().getName()+"   到达考试");
        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getName()+"   第一题开始");
        doFirst();
        System.out.println(Thread.currentThread().getName()+"   第一题结束");
        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getName()+"   第二题开始");
        doSecond();
        System.out.println(Thread.currentThread().getName()+"   第二题结束");
        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getName()+"   第三题开始");
        ddLast();
        System.out.println(Thread.currentThread().getName()+"   第三题结束");
        phaser.arriveAndAwaitAdvance();

    }

    private void ddLast() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void doSecond() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void doFirst() {
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Main{
    public static void main(String[] args) {

//          阶段切换，通过覆盖方法onAdvance()
//        MyPhaser phaser=new MyPhaser();

        Phaser phaser=new Phaser();

        StudentTask studentTask[]=new StudentTask[5];
        for(int i=0;i<studentTask.length;i++){
            studentTask[i]=new StudentTask(phaser);
            phaser.register();          //表示注册一次维护的线程数
        }

        Thread thread[]=new Thread[5];
        for(int i=0;i<thread.length;i++){
            thread[i]=new Thread(studentTask[i],"student"+i);
            thread[i].start();
        }

        for(int i=0;i<5;i++){

            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

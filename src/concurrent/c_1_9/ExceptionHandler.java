package concurrent.c_1_9;



public class ExceptionHandler implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        System.out.printf("an exception has been captured\n");
        System.out.printf("Thread:%s\n",t.getId());
        System.out.printf("Exception:%s:%s\n",e.getClass().getName(),e.getMessage());

        e.printStackTrace();

        System.out.printf("thread status : %s\n",t.getState());

    }
}

class Task implements Runnable{

    @Override
    public void run() {
        int num=Integer.valueOf("YYY");
    }
}

class Main{

    public static void main(String[] args) {
        Task task=new Task();
        Thread t=new Thread(task);
//        t.setUncaughtExceptionHandler(new ExceptionHandler());
        t.start();
    }
}

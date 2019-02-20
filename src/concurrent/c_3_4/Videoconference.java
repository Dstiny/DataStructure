package concurrent.c_3_4;

//使用CountDownLatch实现视频会议系统，这个视频会议系统等待所有的参会者齐才开始。

import java.util.concurrent.CountDownLatch;

//视频会议类
public class Videoconference implements Runnable{

    private final CountDownLatch controller;

    public Videoconference(int number) {
        controller=new CountDownLatch(number);
    }

    public void arrive(String name){
        System.out.println("come:"+name);
        controller.countDown();
    }

    @Override
    public void run() {
        System.out.println("没到数量："+controller.getCount());
        try {
            controller.await();
            System.out.println("到齐，开始会议");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

//参与者
class Part implements Runnable{

    private Videoconference videoconference;

    private String name;

    public Part(Videoconference videoconference, String name) {
        this.videoconference = videoconference;
        this.name = name;
    }

    public void run() {

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        videoconference.arrive(name);
    }
}

class Main{

    public static void main(String[] args) {

        Videoconference videoconference=new Videoconference(10);

        Thread tVideo=new Thread(videoconference);
        tVideo.start();

        for(int i=0;i<10;i++){
            Part part=new Part(videoconference,"part"+i);
            Thread t=new Thread(part);
            t.start();
        }
    }
}

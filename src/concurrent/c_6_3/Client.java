package concurrent.c_6_3;

//添加大量的数据到一个列表中，从同一个列表移除大量数据。

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable{

    private LinkedBlockingDeque<String> requestList;

    public Client(LinkedBlockingDeque<String> requestList) {
        this.requestList = requestList;
    }

    @Override
    public void run() {

        for(int i=0;i<3;i++){

            for(int j=0;j<5;j++){

                StringBuilder request=new StringBuilder();
                request.append(i);
                request.append(":");
                request.append(j);

                try {
                    requestList.put(request.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Client request:"+request+"  "+new Date());

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Client end");
    }
}

class Main{

    public static void main(String[] args) {

        LinkedBlockingDeque<String> list=new LinkedBlockingDeque<>();

        Client client=new Client(list);

        Thread t=new Thread(client);

        t.start();

        for(int i=0;i<5;i++){

            for(int j=0;j<3;j++){

                try {
                    String request=list.take();
                    System.out.println("Main Request:"+request+"    "+list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        System.out.println("Main end");
    }
}
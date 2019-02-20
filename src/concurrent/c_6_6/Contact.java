package concurrent.c_6_6;

//ConcurrentSkipListMap实现对联系人对象的映射

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Contact {

    private String name;

    private String phone;


    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

class Task implements Runnable{

    private ConcurrentSkipListMap<String,Contact> map;

    private String id;

    public Task(ConcurrentSkipListMap<String, Contact> map, String id) {
        this.map = map;
        this.id = id;
    }

    @Override
    public void run() {

        for(int i=0;i<1000;i++){
            Contact  contact=new Contact(id,String.valueOf(i+1000));
            map.put(id+contact.getPhone(),contact);
        }
    }
}

class Main{

    public static void main(String[] args) {

        ConcurrentSkipListMap<String,Contact> map;
        map=new ConcurrentSkipListMap<>();

        Thread thread[]=new Thread[25];

        int counter=0;

        for(char i='A';i<'Z';i++){

            Task task=new Task(map,String.valueOf(i));

            thread[counter]=new Thread(task);
            thread[counter].start();

            counter++;
        }

        for(int i=0;i<25;i++){
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main map size:"+map.size());

        Map.Entry<String,Contact> element;
        Contact contact;

        element=map.firstEntry();
        contact=element.getValue();

        System.out.println("Main map first element:"+contact.getName()+"    "+contact.getPhone());

        element=map.lastEntry();
        contact=element.getValue();

        System.out.println("Main map last element:"+contact.getName()+"    "+contact.getPhone());

//        使用subMap()取得map的一个子映射：
        System.out.println("main map subMap:");

        ConcurrentNavigableMap<String,Contact> subMap=map.subMap("A1996","B1002");

        do{
            element=subMap.pollFirstEntry();

            if(element != null){
                contact=element.getValue();
                System.out.println("Main subMap:"+contact.getName()+"   "+contact.getPhone());
            }
        }while(element != null);

    }

}

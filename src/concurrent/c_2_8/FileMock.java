package concurrent.c_2_8;

//生产者和消费者

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//文本文件模拟类
public class FileMock {

    private String content[];       //储存文件内容
    private int index;              //要读取文件内容行号

    public FileMock(int size,int length) {

        content=new String[size];

        for(int i=0;i<size;i++){

            StringBuilder stringBuilder=new StringBuilder(length);

            for(int j=0;j<length;j++){

                int charIndex=(int)Math.random()*255;
                stringBuilder.append((char)charIndex);
            }

            content[i]=stringBuilder.toString();
        }

        index=0;
    }

//    如果文件有可以处理的数据行
    public boolean hasMoreLines(){
        return index<this.content.length;
    }

    public String getLine(){

        if(this.hasMoreLines()){
            System.out.println("mockFile "+(content.length-index));
            return content[index++];
        }
        return null;
    }

}

//数据缓冲区
class Buffer{

    private LinkedList<String> buffer;  //存放共享数据
    private int maxSize;                //buffer长度
    private ReentrantLock lock;         //对修改buffer代码块进行控制
    private Condition insert;
    private Condition get;
    private boolean pendingLines;       //buffer是否有内容

    public Buffer(int maxSize) {

        this.maxSize = maxSize;

        buffer=new LinkedList<>();

        lock=new ReentrantLock();

        insert=lock.newCondition();
        get=lock.newCondition();

        pendingLines=true;
    }

    public void insert(String line){

        lock.lock();

        try {

            if(buffer.size()==maxSize){
                insert.await();
            }

            buffer.offer(line);
            System.out.println("insert the line:"+Thread.currentThread().getName());
            get.signalAll();
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public String get(){
        lock.lock();
        String line=null;
        try{

            while((buffer.size()==0) && (hasPendingLines())){
                get.await();
            }

            if(hasPendingLines()){
                line=buffer.poll();
                System.out.println("read the line:"+Thread.currentThread().getName());
                insert.signalAll();
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

        return line;
    }

//    有数据处理返回true
    public boolean hasPendingLines() {

        return pendingLines || buffer.size()>0;
    }

//当生产者不在生产新数据时线程将调用它。
    public void setPendingLines(boolean pendingLines) {
        this.pendingLines = pendingLines;
    }
}

//生产者
class Product implements Runnable{

    private FileMock file;
    private Buffer buffer;

    public Product(FileMock file, Buffer buffer) {
        this.file = file;
        this.buffer = buffer;
    }

//这个方法用来读文件FileMock中农所有数据行，并使用insert插入到缓冲区，
// 读完，调用setPendingLines()通知缓冲区通知生成更多的行
    public void run() {

        buffer.setPendingLines(true);

        while (file.hasMoreLines()) {
             String line = file.getLine();
             buffer.insert(line);
         }

        buffer.setPendingLines(false);
    }
}

class Consumer implements Runnable{

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        if(buffer.hasPendingLines()){
            String line=buffer.get();
            processLine(line);
        }
    }

//模拟处理数据
    private void processLine(String line) {
        try{
            Thread.sleep(10);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Main{

    public static void main(String[] args) {

        FileMock file=new FileMock(100,20);
        Buffer buffer=new Buffer(20);

        Product product=new Product(file,buffer);
        Thread t1=new Thread(product,"product");

        Consumer consumer[]=new Consumer[3];
        Thread t2[]=new Thread[3];

        for(int i=0;i<3;i++){
            consumer[i]=new Consumer(buffer);
            t2[i]=new Thread(consumer[i],"consumer"+i);
        }

        t1.start();
        for(int i=0;i<3;i++){
            t2[i].start();
        }
    }
}
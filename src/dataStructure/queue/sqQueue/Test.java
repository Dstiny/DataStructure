package dataStructure.queue.sqQueue;

public class Test {

    public static void main(String[] args) {

        SqQueue<String> sqQueue=new SqQueue<>(5);

        sqQueue.inQueue("a");
        sqQueue.inQueue("b");
        sqQueue.inQueue("c");
        sqQueue.inQueue("d");

        sqQueue.printQueue();

        System.out.println("长度："+sqQueue.getLength());

        sqQueue.outQueue();
        sqQueue.outQueue();

        sqQueue.inQueue("e");

        sqQueue.printQueue();

    }

}

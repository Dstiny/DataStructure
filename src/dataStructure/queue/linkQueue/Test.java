package dataStructure.queue.linkQueue;

public class Test {

    public static void main(String[] args) {

        LinkQueue<String> linkQueue=new LinkQueue<>();

        linkQueue.inQueue("Test");
        linkQueue.inQueue("B");
        linkQueue.inQueue("C");
        linkQueue.inQueue("D");

        linkQueue.printQueue();

        linkQueue.outQueue();
        linkQueue.outQueue();

        linkQueue.inQueue("E");

        linkQueue.printQueue();
    }
}

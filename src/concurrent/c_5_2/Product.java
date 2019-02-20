package concurrent.c_5_2;

//实现一项更新产品价格的任务。最初的任务负责更新列表中所有元素。10为参考大小，
// 如果一个任务需要更新大于10个元素，会将列表分解为两部分，然后分别创建两个任务
//用来各自执行。

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Product {

    private String name;

    private double price;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

//随机产品列表
class ProductListGenerator{

    public List<Product> generator(int size){

        List<Product> ret=new ArrayList<>();

        for(int i=0;i<size;i++){
            Product product=new Product();
            product.setName("Product"+i);
            product.setPrice(10);
            ret.add(product);
        }

        return ret;
    }
}

class Task extends RecursiveAction{

    private static final long serialVersionUID=1L;

    private List<Product> products;

//    决定任务执行时产品的分块。
    private int first;
    private int last;

//    用来存储产品价格增加额。
    private double increment;

    public Task(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

//    任务的执行逻辑
    @Override
    protected void compute() {

        if(last-first<10){
            updatePrice();

//           如果last与first差异值大于等于10，就创建两个新的Task，一个处理前一半产品，另一个处理后一半产品。
//            然后调用ForkJoinPool的invokeAll()执行两个新的任务。
        }else{
            int middle=(first+last)/2;
            System.out.println("Task forking:"+getQueuedTaskCount());
            Task task1=new Task(products,first,middle+1,increment);
            Task task2=new Task(products,middle+1,last,increment);
            invokeAll(task1,task2);
        }
    }

    private void updatePrice() {
        for(int i=first;i<last;i++){
            Product product=products.get(i);
            product.setPrice(product.getPrice()*(increment+1));
        }
    }
}

class Main{

    public static void main(String[] args) {

        ProductListGenerator generator=new ProductListGenerator();
        List<Product> products=generator.generator(100);

        Task task=new Task(products,0,products.size(),0.20);

        ForkJoinPool pool=new ForkJoinPool();
        pool.execute(task);

        do{
            System.out.println("main thread.count:"+pool.getActiveThreadCount());
            System.out.println("main thread.steal:"+pool.getStealCount());
            System.out.println("main thread.parallelism:"+pool.getParallelism());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(!task.isDone());

        pool.shutdown();

        if(task.isCompletedNormally()){
            System.out.println("main the task completed normally");
        }


    }
}

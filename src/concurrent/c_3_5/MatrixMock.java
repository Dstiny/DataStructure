package concurrent.c_3_5;

//我们将在数字矩阵中寻找一个数字，。这个矩阵会被分为几个子集，然后每个线程在一个子集中查找。

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//矩阵类：生成一个1-10的随机矩阵
public class MatrixMock {

    private int data[][];

//    矩阵行数，行长度，寻找的数字
    public MatrixMock(int size,int length,int number) {

        int counter=0;
        data=new int[size][length];
        Random random=new Random();

        for(int i=0;i<size;i++){

            for(int j=0;j<length;j++){

                data[i][j]=random.nextInt(100);

                if(data[i][j]==number){
                    counter++;
                }
            }
        }

        System.out.println("查找的数字："+number+"    需查找"+counter+"次");
    }

//    矩阵存在这个行，就返回行数据
    public int[] getRow(int row){
        if((row>=0) && (row<data.length)){
            return data[row];
        }
        return null;
    }

}

//结果类，保存矩阵每行找到指定数字的次数
class Result{

    private int data[];

    public Result(int size){
        data=new int[size];
    }

//    指定了数组的索引position及其对应的值value
    public void setData(int position,int value){
        data[position]=value;
    }

    public int[] getData() {
        return data;
    }
}

class Searcher implements Runnable{

//    查找子集范围
    private int firstRow;
    private int lastRow;

    private MatrixMock mock;
    private Result result;

    private int number;         //存放要查找的数字

    private CyclicBarrier barrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock, Result result, int number, CyclicBarrier barrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.result = result;
        this.number = number;
        this.barrier = barrier;
    }

    public void run() {

        int counter;

        System.out.println("查找范围:"+firstRow+"-"+lastRow+"Thread:"+Thread.currentThread().getName());

        for(int i=firstRow;i<lastRow;i++){

            int row[]=mock.getRow(i);
            counter=0;

            for(int j=0;j<row.length;j++){
                if(row[j]==number){
                    counter++;
                }
            }

            result.setData(i,counter);
        }

        System.out.println("查找完毕_Thread"+Thread.currentThread().getName());

        try{
            barrier.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(BrokenBarrierException e){
            e.printStackTrace();
        }
    }
}

//计算在矩阵查找总次数
class Grouper implements Runnable{

    private Result result;

    public Grouper(Result result) {
        this.result = result;
    }

    public void run() {

        int finalResult=0;
        int data[]=result.getData();

        for(int number:data){
            finalResult+=number;
        }
        System.out.println("finalResult:"+finalResult);
    }
}

class Main{

    public static void main(String[] args) {
        final int ROW=10000;
        final int NUMBER=1000;
        final int SEARCH=5;
        final int PARTICIPANTS=5;
        final int LINES_PARTICIPANT=2000;

        MatrixMock mock=new MatrixMock(ROW,NUMBER, SEARCH);

        Result result=new Result(ROW);

        Grouper grouper=new Grouper(result);

//        这个对象将等待5个线程运行结束后运行grouper
        CyclicBarrier barrier=new CyclicBarrier(PARTICIPANTS,grouper);

        Searcher searcher[]=new Searcher[PARTICIPANTS];
        for(int i=0;i<PARTICIPANTS;i++){

            searcher[i]=new Searcher(i*LINES_PARTICIPANT,
                    (i*LINES_PARTICIPANT)+LINES_PARTICIPANT,
                    mock,result,5,barrier);

            Thread thread=new Thread(searcher[i]);
            thread.start();
        }

        System.out.println("main end");
    }
}

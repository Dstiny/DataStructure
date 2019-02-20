package concurrent.c_5_3;

//一个文档任务，将遍历文档的每一行查找这个词
//行任务，将在文档的一部分当中查找这个词
//所有这些任务将返回文档或行所出现这个词得个数。

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

//生成一个字符串矩阵模拟一个文档
public class DocumentMock {

    private String words[]={
            "the", "hello", "goodbye", "packt",
            "java", "thread", "pool", "random",
            "class", "main"
    };
//行数，一行词个数，查找的词
    public String[][] generateDocument(int numLines,int numWords,String word){

        int counter=0;
        String document[][]=new String[numLines][numWords];
        Random random=new Random();

        for(int i=0;i<numLines;i++){
            for(int j=0;j<numWords;j++){
                int index=random.nextInt(words.length);
                document[i][j]=words[index];
                if(document[i][j].equals(word)){
                    counter++;
                }
            }
        }

        System.out.println("mock counter:"+counter);
        return document;
    }
}

class DocementTask extends RecursiveTask<Integer>{

    private String documnet[][];

    private int start,end;
    private String word;

    public DocementTask(String[][] documnet, int start, int end, String word) {
        this.documnet = documnet;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {
        Integer result = null;

        if(end-start<10){
            result=processLines(documnet,start,end,word);
        }else{
            int mid=(start+end)/2;
            DocementTask task1=new DocementTask(documnet,start,mid,word);
            DocementTask task2=new DocementTask(documnet,mid,end,word);
            invokeAll(task1,task2);

            try {
                result=groupResults(task1.get(),task2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private Integer groupResults(Integer integer, Integer integer1) {

        Integer result;
        result=integer+integer1;
        return result;
    }

    private Integer processLines(String[][] documnet, int start, int end, String word) {

        List<LineTask> tasks=new ArrayList<>();
        for(int i=start;i<end;i++){
            LineTask task=new LineTask(documnet[i],0,documnet[i].length,word);
            tasks.add(task);
        }
        invokeAll(tasks);

        int result=0;

        for(int i=0;i<tasks.size();i++){

            LineTask task=tasks.get(i);
            try {

                result=result+task.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return new Integer(result);
    }
}



class LineTask extends RecursiveTask<Integer>{

    private static final long serialVersionUID=1L;

    private String line[];

    private int start,end;

    private String word;

    public LineTask(String[] line, int start, int end, String word) {
        this.line = line;
        this.start = start;
        this.end = end;
        this.word = word;
    }

    @Override
    protected Integer compute() {

        Integer result=null;

        if(end-start<100){

            result=count(line,start,end,word);

        }else{

            int mid=(start+end)/2;

            LineTask task1=new LineTask(line,start,mid,word);
            LineTask task2=new LineTask(line,mid,end,word);

            invokeAll(task1,task2);

            try {
                result=groupResults(task1.get(),task2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private Integer groupResults(Integer integer, Integer integer1) {

        Integer result;
        result=integer+integer1;
        return result;
    }

    private Integer count(String[] line, int start, int end, String word) {

        int counter=0;
        for(int i=start;i<end;i++){
            if(line[i]==word){
                counter++;
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return counter;
    }
}

class Main{

    public static void main(String[] args) {

        DocumentMock mock=new DocumentMock();

        String[][] document=mock.generateDocument(100,1000,"the");

        DocementTask task=new DocementTask(document,0,100,"the");

        ForkJoinPool pool=new ForkJoinPool();
        pool.execute(task);

        pool.shutdown();

        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("main task get:"+task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

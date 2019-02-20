package dataStructure.sequenceList;

import dataStructure.MyException;


/**
 *  顺序表插入和删除一个元素的时间复杂度为O(n)
 *
 * 顺序表支持随机访问，顺序表读取一个元素的时间复杂度是固定的，和问题规模没管
 *
 * 优缺点：支持随机访问，空间利用率高
 *
 * 缺点：大小固定，一开始就要固定顺序表的最大长度；插入和删除元素需要一定大量的数据
 */
public class MyListImpl implements MyList{

//    默认的顺序表的最大长度
    final int defaultSize=10;

//    最大长度
    int maxSize;

//    当前长度
    int size;

//    对象数组
    Object[] listArray;


    public MyListImpl () {

        init(defaultSize);
    }
    public MyListImpl(int size){
        init(size);
    }

//    顺序表的初始化方法
    private void init(int size) {
        maxSize=size;
        this.size=0;
        listArray=new Object[size];
    }


    public int size() {

        return size;
    }


    public boolean isEmpty() {
        return size==0;
    }


    public void insert(int index,Object object) throws Exception {

        if(size==maxSize){
            throw new MyException("线性表已满");
        }

        if(index<0 || index>size){
            throw new MyException("index参数错误");
        }

        for(int j=size-1;j>=index;j--){
            listArray[j+1]=listArray[j];
        }
//        不管当前线性表是否为零，这句话都能正常执行
        listArray[index]=object;

        size++;
    }


    public void delete(int index) throws Exception {

        if(isEmpty()){
            throw new MyException("顺序表为空，无法删除");
        }

        if(index<0 || index>size){
            throw new MyException("index参数错误");
        }
//        移动元素
        for(int j=index;j<size-1;j++){
            listArray[j]=listArray[j+1];
        }

        size--;
    }


    public Object get(int index) throws Exception {

        if(index<0 || index>size){
            throw new MyException("index参数错误");
        }

        return listArray[index];
    }

}

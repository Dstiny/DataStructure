package dataStructure.sequenceList;


/*
    求元素个数，插入，删除，查找，判断是否为空
 */

//设计线性表抽象数据类型的java接口
public interface MyList {


//    获得线性表的长度
    public int size();

//    判断线性表是否为空
    public boolean isEmpty();

//    插入元素
    public void insert(int index,Object object) throws Exception;

//    删除元素
    public void delete(int index) throws Exception;

//    获取指定位置上的元素
    public Object get(int index) throws Exception;

}


/*
        计算机存在两种基本的物理存储结构：顺序存储结构，离散结构。

        使用顺序表结构实现的线性表称为顺序表

        java内存中栈和堆。栈内存的存储是顺序结构，堆内存的存储是离散结构
 */

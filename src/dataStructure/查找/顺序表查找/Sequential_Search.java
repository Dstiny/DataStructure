package dataStructure.查找.顺序表查找;


//顺序查找(线性查找)

/*
    思路：从第一个到最后一个依次与给定值比较，若相等则查找成功。


    适用于线性存储结构和链式存储结构
 */


//    数组下标为0时不储存时机内容
public class Sequential_Search {


//顺序查找
    public int seqSearch(int[] arr,int key){

        int n=arr.length;
        for(int i=1;i<n;i++){
            if(key==arr[i]) return arr[i];
        }

        return 0;
    }


//    顺序查找优化，设置哨兵
    public int seqSearch1(int[] arr,int key){

        int i=arr.length-1;
        arr[0]=key;

        while(arr[i] != key){
            i--;
        }

        return i;       //i=0时查找失败。
    }

}

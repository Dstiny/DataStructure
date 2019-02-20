package dataStructure.查找.散列表;

//除留余数发、开放定址法下的线性探测法

public class HashTable_ {

    int elem[];

    int count;          //当前数据元素个数

    private static final int NULL_KEY=-32768;

    public HashTable_(int count) {

        this.count = count;

        elem=new int[count];

        for(int i=0;i<elem.length;i++){

            elem[i]=NULL_KEY;       //代表为空。
        }
    }


//    散列函数
    public int hash(int key){

        return key % count;
    }


//    插入操作
    public void insert(int key){

        int addr=hash(key);

        while(elem[addr] != NULL_KEY){          //位置非空，有冲突

            addr=(addr+1) % count;          //开放地址法的线性探测
        }

        elem[addr]=key;
    }

//    查找操作
    public boolean search(int key){

        int addr=hash(key);

        while(elem[addr] != key){

            addr=(addr+1) % count;

            if(addr==hash(key) || elem[addr]==NULL_KEY){

                System.out.println("查找的记录不存在");

                return false;
            }
        }

        System.out.println("存在记录");
        return true;
    }
}

class Main{

    public static void main(String[] args) {

        int arr[]={
          12,67,56,16,25,37,22,29,15,47,48,34
        };

        HashTable_ hashTable_=new HashTable_(arr.length);

        for(int a:arr){
            hashTable_.insert(a);
        }

        System.out.println();

        for(int a:arr){
            hashTable_.search(a);
        }
    }
}

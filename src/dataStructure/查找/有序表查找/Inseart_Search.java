package dataStructure.查找.有序表查找;

/*
    有序表查找：
    插值排序：适用于数字分布均匀、一种优化折半查找的算法
 */

public class Inseart_Search {

    public int inseart_serach(int[] arr,int key){

        int low=1;
        int high=arr.length-1;

        while(low<=high){

//            关键：插值计算公式。
            int mid=low+(high-low)*(key-arr[low])/(arr[high]-arr[low]);

            if(arr[mid]<key) low=mid+1;

            else if(arr[mid]>key) high=mid-1;

            else return mid;
        }

        return 0;
    }
}

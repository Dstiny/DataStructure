package dataStructure.查找.有序表查找;


//有序表查找：折半查找(二分法查找)

/*前提：
       存储结构是顺序存储结构
       有序排列
  思路：
        假如数据元素按升序排列。从中间项与关键字key开始对比，若关键字key>中间值，则在右半区继续查找，反之。
        以此类推
 */

public class Binary_Search {


    public int binary_serach(int[] arr,int key){

        int low=1;
        int high=arr.length-1;

        while(low<=high){

            int mid=(low+high)/2;

            if(arr[mid]<key) low=mid+1;

            else if(arr[mid]>key) high=mid-1;

            else return mid;
        }

        return 0;
    }

}

package dataStructure.排序.直接插入排序;

public class InsertSort {

    public static void insertSort(int arr[]){

        if(arr==null){
            return ;
        }

        for(int i=1;i<arr.length;i++){

            if(arr[i]<arr[i-1]){

                int temp=arr[i];

                int j=i;

                while(j>0 && temp<arr[j-1]){

                    arr[j]=arr[j-1];

                    j--;
                }

                arr[j]=temp;
            }
        }
    }

    public static void main(String[] args) {

        int arr[]={
                54,23,21,67,10
        };

        insertSort(arr);

        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"、");
        }

    }
}

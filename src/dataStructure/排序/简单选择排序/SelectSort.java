package dataStructure.排序.简单选择排序;

public class SelectSort {

    public static void selectSorted(int[] arr){

        if(arr==null && arr.length<=1){
            return ;
        }

        for(int i=0;i<arr.length;i++){

            int min=i;

            for(int j=i+1;j<arr.length;j++){

                if(arr[j]<arr[min]){
                    min=j;
                }
            }

            swap(arr,min,i);
        }
    }

    private static void swap(int[] arr, int min, int i) {

        int temp;
        temp=arr[i];
        arr[i]=arr[min];
        arr[min]=temp;
    }

    public static void main(String[] args) {

        int arr[]={

                43,67,12,34,6,90,89,68
        };

        selectSorted(arr);

        for(int i=0;i<arr.length;i++)
            System.out.print(arr[i]+"、");
    }
}

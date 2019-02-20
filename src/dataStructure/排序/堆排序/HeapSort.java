package dataStructure.排序.堆排序;

public class HeapSort {

    public static void heapSort(int[] arr){

        if(arr==null) return ;

        int len=arr.length;

        for(int i=len/2-1;i>=0;i--){        //从最后一个父节点开始构造堆
            heapAdjust(arr,i,len-1);
        }

        for(int i=len-1;i>=0;i--){

            int temp=arr[0];
            arr[0]=arr[i];
            arr[i]=temp;
            heapAdjust(arr,0,i-1);
        }
    }

    private static void heapAdjust(int[] arr, int start, int end) {

        int temp=arr[start];

        int child=2*start+1;

        while(child<=end){

            if(child+1<=end  && arr[child+1]>arr[child]) child++;

            if(arr[child]<=temp) break;

            arr[start]=arr[child];

            start=child;

            child=child*2+1;
        }

        arr[start]=temp;
    }


    public static void main(String[] args) {

        int arr[]={
                54,23,21,67,10,89,90
        };

        heapSort(arr);

        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"、");
        }
    }
}

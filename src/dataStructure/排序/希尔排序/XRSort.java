package dataStructure.排序.希尔排序;

public class XRSort {

    public static void xRSorted(int[] arr){

        if(arr==null) return ;

        int incrementNum=arr.length/2;

        while(incrementNum>=1){

            for(int i=0;i<arr.length;i++){

                for(int j=i;j<arr.length-incrementNum;j=j+incrementNum){

                    if(arr[j]>arr[j+incrementNum]){
                         int temp=arr[j];
                         arr[j]=arr[j+incrementNum];
                         arr[j+incrementNum]=temp;
                    }
                }
            }

            incrementNum=incrementNum/2;
        }
    }

    public static void main(String[] args) {

        int arr[]={
                54,23,21,67,10,89
        };

        xRSorted(arr);

        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"、");
        }
    }
}

package dataStructure.排序.冒泡排序;

public class Main {

//    未优化
    public static void bubbleSorted(int[] arr){

        int temp;

        for(int i=0;i<arr.length;i++){

            for(int j=0;j<arr.length-i-1;j++){

                if(arr[j+1]>arr[j]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }

    }


//    优化1
    public static void bubbleSorted1(int arr[]){

        boolean flag=true;

        while(flag){

            int temp;

            for(int i=0;i<arr.length;i++){

                for(int j=0;j<arr.length-i-1;j++){

                    if(arr[j+1]<arr[j]){
                        temp=arr[j];
                        arr[j]=arr[j+1];
                        arr[j+1]=temp;

                        flag=true;
                    }

                    if(!flag){
                        break ;
                    }
                }
            }
        }

    }

}

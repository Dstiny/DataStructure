package dataStructure.linkList;

public class Test {

    public static void main(String[] args) throws Exception {

        LinkList<Integer> nums=new LinkList<Integer>();

        System.out.println("表是否为空："+nums.isEmpty());


        for(int i=1;i<=5;i++){
            nums.listInsert(i,2*i);
        }
        System.out.println("表是否为空："+nums.isEmpty());


        int num;
        for(int i=1;i<=5;i++){
            num=nums.getData(i);
            System.out.println("第"+i+"个位置元素值为："+num);
        }


        System.out.println("0的位置"+nums.LocateElem(0));

        System.out.println("2的位置"+nums.LocateElem(2));

//        System.out.println("10的位置"+nums.LocateElem(10));


        System.out.println("已删除位置2"+nums.listDelete(1));

        System.out.println("当前表长:"+nums.listLength());


        for(int i=1;i<=nums.listLength();i++){
            num=nums.getData(i);
            System.out.println("第"+i+"个位置元素值："+num);
        }


        nums.clearList();


        System.out.println("表是否为空："+nums.isEmpty());
    }
}

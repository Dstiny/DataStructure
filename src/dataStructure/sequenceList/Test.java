package dataStructure.sequenceList;


public class Test {

    public static void main(String[] args) {

        MyListImpl list=new MyListImpl(20);

        try{

            list.insert(0,100);

            list.insert(0,50);

            list.insert(0,25);

//            sequential.insert(4,78);

            for(int i=0;i<list.size();i++){

                System.out.println("第"+i+"个参数:"+list.get(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

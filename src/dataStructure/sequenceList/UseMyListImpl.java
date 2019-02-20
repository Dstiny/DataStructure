package dataStructure.sequenceList;

public class UseMyListImpl {

    public static void main(String[] args) {

        MyListImpl list=new MyListImpl(20);
        try{

//            每次在顺序表的最后一个位置，当线性表长度的位置
            list.insert(list.size,new Student("s1001","张三","男",18));
            list.insert(list.size,new Student("s1002","李四","男",18));
            list.insert(list.size,new Student("s1003","王五","女",21));

            for(int i=0;i<list.size;i++){
                System.out.println(list.get(i));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}


class Student{

    private String id;
    private String name;
    private String gender;
    private int age;


    public String toString(){
        return " 学号:"+this.getId()+" 姓名:"+this.getName()+" 性别:"+this.getGender()+" 年龄:"+this.getAge();
    }

    public Student(String id,String name,String gender,int age){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.age=age;
    }
    public Student(){

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}

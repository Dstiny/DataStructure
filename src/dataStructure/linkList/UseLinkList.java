package dataStructure.linkList;

public class UseLinkList {

    public static void main(String[] args) throws Exception {

        LinkList<Student> students=new LinkList<Student>();

        System.out.println("是否为空："+students.isEmpty());


        Student[] stus={
                new Student("Test",12),
                new Student("B",34),
                new Student("C",22),
                new Student("D",44),
                new Student("E",77),
        };


        for(int i=1;i<=5;i++){
            students.listInsert(i,stus[i-1]);
        }
        System.out.println("是否为空："+students.isEmpty());


        for(int i=1;i<=5;i++){
            Student stu=students.getData(i);
            System.out.println("第"+i+"个数据："+stu);
        }


        System.out.println("A的位置："+students.LocateElem(stus[0]));

        System.out.println("已删除："+students.listDelete(4));

        System.out.println("当前表长："+students.listLength());


        for(int i=1;i<=students.listLength();i++){
            System.out.println("第"+i+"个位置值："+students.getData(i));
        }

        students.clearList();
        System.out.println("是否为空："+students.isEmpty());
    }

}


class Student{

    private String name;
    private int age;
    public Student(String name,int age){
        this.name=name;
        this.age=age;
    }
    public String toString(){
        return name;
    }
}
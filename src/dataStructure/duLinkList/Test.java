package dataStructure.duLinkList;

public class Test {

    public static void main(String[] args) {

        DuLinkList<Student> students=new DuLinkList<Student>();
        Student[] stus={
                new Student("Test",11),
                new Student("B",12),
                new Student("C",13),
                new Student("D",14),
                new Student("E",15),
        };

        for(int i=1;i<=5;i++){
            students.listInsert(i,stus[i-1]);
        }

        System.out.println("表长："+students.listLength());

        Student stu;
        for(int i=1;i<=5;i++){
            stu=students.getElement(i).data;
            System.out.println("第"+i+"个位置："+stu.getName());
        }

        stu=students.listDelete(1);
        System.out.println("删除成功："+stu.getName());
        stu=students.listDelete(3);
        System.out.println("删除成功："+stu.getName());

        System.out.println("表长度："+students.listLength());
    }

}


class Student{

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
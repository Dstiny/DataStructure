package dataStructure.stack.linkStack;

public class Test {

    public static void main(String[] args) {

        LinkStack<Student> linkStack=new LinkStack<>();

        Student[] students={
                new Student("Test",11),
                new Student("B",12),
                new Student("C",13),
                new Student("D",14),
                new Student("E",15),
        };

        for(int i=0;i<5;i++){
            linkStack.push(students[i]);
        }

        linkStack.printStack();

        for(int i=0;i<5;i++){
            System.out.println(linkStack.pop());
        }

        linkStack.printStack();

    }


}


class Student{
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString(){
        return name;
    }
}
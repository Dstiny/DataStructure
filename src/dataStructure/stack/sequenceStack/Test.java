package dataStructure.stack.sequenceStack;

public class Test {

    public static void main(String[] args) {

        SqStack<Student> sqStack=new SqStack<Student>();
        Student[] students={
                new Student("Test",11),
                new Student("B",12),
                new Student("C",13),
                new Student("D",14),
                new Student("E",15),
        };

        for(int i=0;i<5;i++){
            sqStack.push(students[i]);
        }

        sqStack.printStack();

        System.out.println();

        for(int i=0;i<5;i++){
            sqStack.pop();
            System.out.println("出栈成功");
        }

        sqStack.printStack();
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
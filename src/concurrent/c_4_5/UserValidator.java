package concurrent.c_4_5;

//用户可以用两种验证机制进行验证，但是，只要一种机制验证成功，那么这个用户就验证成功了。

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//用户验证过程的类
public class UserValidator {

    private String name;

    public UserValidator(String name) {
        this.name = name;
    }

    public boolean validator(String name,String password){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        return new Random().nextBoolean();
    }

    public String getName() {
        return name;
    }
}

//使用并发验证任务
class TaskValidator implements Callable<String>{

    private UserValidator validator;

    private String name;
    private String password;

    public TaskValidator(UserValidator validator, String name, String password) {
        this.validator = validator;
        this.name = name;
        this.password = password;
    }

    public String call() throws Exception {

        if(!validator.validator(name,password)){
            System.out.println("user not found:"+validator.getName());
            throw new Exception("error valdator");
        }

        System.out.println("found"+validator.getName());

        return validator.getName();
    }
}

class Main{
    public static void main(String[] args) {

        String name="test";
        String password="test";

        UserValidator ldapValidator=new UserValidator("LDAP");
        UserValidator dbValidator=new UserValidator("DataBase");

        TaskValidator ldapTask=new TaskValidator(ldapValidator,name,password);
        TaskValidator dbTask=new TaskValidator(dbValidator,name,password);

        List<TaskValidator> taskList=new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        ExecutorService executor=(ExecutorService) Executors.newCachedThreadPool();

        String result;

        try{
            result=executor.invokeAny(taskList);
            System.out.println("Main,result:"+result);
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        executor.shutdown();
        System.out.println("main finish");
    }
}

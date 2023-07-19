package byow.Core;

import java.io.Serializable;

public class Dog implements Serializable {
    private String name;
    private int age;

    Dog(String name,int age){
        this.name=name;
        this.age=age;
    }

    public void buck(){
        System.out.print(name+":"+" 汪汪"+"\n");

    }
}

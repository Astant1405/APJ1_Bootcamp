package org.example;

public class User {
    private String name;
    private int age;

    User(String name, int age){
        this.name = name;
        this.age = age;
    }
    public int getAge(){
        return age;
    }
    public String getName(){
        return name;
    }
}

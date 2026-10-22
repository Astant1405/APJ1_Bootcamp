package org.example;

import java.util.concurrent.TimeUnit;

public class Cat extends Animal {

    Cat(String name, int age){
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d", getName(), getAge());
    }
}

package org.example;

import java.util.concurrent.TimeUnit;

public class Dog extends Animal {

    Dog(String name, int age){
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d", getName(), getAge());
    }

}

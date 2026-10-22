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

    @Override
    public double goToWalk() {
        double time = getAge() * 0.25;
        try{
            TimeUnit.SECONDS.sleep((long) time);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return time;
    }
}

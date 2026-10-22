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

    @Override
    public double goToWalk() {
        double time = getAge() * 0.5;
        try{
            TimeUnit.SECONDS.sleep((long) time);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return time;
    }
}

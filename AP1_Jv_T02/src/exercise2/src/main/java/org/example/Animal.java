package org.example;

public abstract class Animal {
    private String name;
    private int age;
    private double mass;

    Animal(String name, int age, double mass){
        this.name = name;
        this.age = age;
        this.mass = mass;
    }

    String getName(){
        return name;
    }

    int getAge(){
        return age;
    }

    double getMass(){
        return mass;
    }

    public abstract double getFeedInfoKg();
}

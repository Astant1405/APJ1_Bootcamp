package org.example;

public class GuineaPig extends Animal implements Herbivore{

    GuineaPig(String name, int age){
        super(name, age);
    }

    public String chill(){
        return "I can chill for 12 hours";
    }

    @Override
    public String toString() {
        return String.format("GuineaPig name = %s, age = %d. " + chill(), getName(), getAge());
    }
}

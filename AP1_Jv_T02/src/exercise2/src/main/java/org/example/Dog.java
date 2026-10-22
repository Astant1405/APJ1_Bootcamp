package org.example;

public class Dog extends Animal{
    Dog(String name, int age, double mass){
        super(name, age, mass);
    }

    public double getFeedInfoKg(){
        return getMass() * 0.3;
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d, mass = %.2f, feed = %.2f", getName(), getAge(), getMass(), getFeedInfoKg());
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws IOException {
        List<Animal> animals = new ArrayList<>();
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
            int num = Integer.parseInt(input.readLine());
            Stream.generate(() -> {
                try{
                    return input.readLine();
                }
                catch (IOException e){
                    throw new UncheckedIOException(e);
                }
            }).limit(num).forEach(animal -> {
                try{
                    animalInit(animal, animals, input);
                }
                catch (IOException e){
                    throw new UncheckedIOException(e);
                }
            });
        }
        catch (NumberFormatException e){
            System.out.println("Could not parse a number. Please, try again");
        }
        animals.stream().forEach(animal -> {
            if(animal.getAge() > 10){
                animal.setAge(animal.getAge() + 1);
            }
            System.out.println(animal.toString());
        });
    }

    public static void animalInit(String animal, List<Animal> animals, BufferedReader input) throws IOException{
        if(animal.equals("dog") || animal.equals("cat")){
            String name = input.readLine();
            try{
                int age = Integer.parseInt(input.readLine());
                if(age > 0){
                    if (animal.equals("dog")) {
                        Dog dog = new Dog(name, age);
                        animals.add(dog);
                    }
                    else {
                        Cat cat = new Cat(name, age);
                        animals.add(cat);
                    }
                }
                else{
                    System.out.println("Incorrect input. Age <= 0");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Could not parse a number. Please, try again");
            }
        }
        else{
            System.out.println("Incorrect input. Unsupported pet type");
        }
    }
}

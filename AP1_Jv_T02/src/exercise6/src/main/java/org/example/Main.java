package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        List<String> animals = new ArrayList<>();
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                try{
                    int num = Integer.parseInt(input.readLine());
                    if(num > 0){
                        for(int i = 0; i < num; i++){
                            String animal = input.readLine();
                            animalInit(animal, animals, input);
                        }
                        break;
                    }
                    else{
                        System.out.println("Input error, try again");
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse a number. Please, try again");
                }
            }
            AnimalIterator<Animal> iterator = new AnimalIterator(animals);
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }
    }

    public static void animalInit(String animal, List<String> animals, BufferedReader input) throws IOException{
        if(animal.equals("dog") || animal.equals("cat")){
            String name = input.readLine();
            int age = Integer.parseInt(input.readLine());
            if(age > 0){
                if (animal.equals("dog")) {
                    Dog dog = new Dog(name, age);
                    animals.add(dog.toString());
                }
                else {
                    Cat cat = new Cat(name, age);
                    animals.add(cat.toString());
                }
            }
            else{
                System.out.println("Incorrect input. Age <= 0");
            }
        }
        else{
            System.out.println("Incorrect input. Unsupported pet type");
        }
    }
}

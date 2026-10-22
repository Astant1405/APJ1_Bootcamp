package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> animals = new ArrayList<>();
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
                        System.out.println("Incorrect input. Try again");
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse a number. Please, try again");
                }
            }
        }
        for (String animal : animals){
            System.out.println(animal);
        }
    }

    public static void animalInit(String animal, ArrayList<String> animals, BufferedReader input) throws IOException{
        if(animal.equals("dog") || animal.equals("cat")){
            String name = input.readLine();
            int age = Integer.parseInt(input.readLine());
            if(age > 0){
                if(animal.equals("dog")){
                    Dog dog = new Dog(name, age);
                    animals.add(dog.toString());
                }
                else{
                    Cat cat = new Cat(name, age);
                    animals.add(cat.toString());
                }
            }
            else{
                animals.add("Incorrect input. Age <= 0");
            }
        }
        else{
            animals.add("Incorrect input. Unsupported pet type");
        }
    }
}
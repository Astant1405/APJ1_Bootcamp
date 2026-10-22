package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        HashMap<Animal, String> animals = new HashMap<>();
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
                        System.out.println("Input error. Try again");
                    }
                }
                catch (NumberFormatException e){
                    System.out.println("Could not parse a number. Please, try again");
                }
            }
        }
        List<Map.Entry<Animal, String>> entries = new ArrayList<>(animals.entrySet());
        entries.sort((entry1, entry2) -> {
            Animal a1 = entry1.getKey();
            Animal a2 = entry2.getKey();
            if(a1 instanceof Herbivore && a2 instanceof Herbivore ||
            a1 instanceof Omnivore && a2 instanceof Omnivore){
                return 0;
            }
            if (a1 instanceof Herbivore && !(a2 instanceof Herbivore)) {
                return -1;
            } else {
                return 1;
            }
        });

        for (Map.Entry<Animal, String> entry : entries) {
            System.out.println(entry.getValue());
        }
    }

    public static void animalInit(String animal, HashMap<Animal, String> animals, BufferedReader input) throws IOException{
        if(animal.equals("dog") || animal.equals("cat") || animal.equals("guinea") || animal.equals("hamster")){
            String name = input.readLine();
            int age = Integer.parseInt(input.readLine());
            if(age > 0){
                switch (animal){
                    case "dog":
                        Dog dog = new Dog(name, age);
                        animals.put(dog, dog.toString());
                        break;
                    case "cat":
                        Cat cat = new Cat(name, age);
                        animals.put(cat, cat.toString());
                        break;
                    case "hamster":
                        Hamster hamster = new Hamster(name, age);
                        animals.put(hamster, hamster.toString());
                        break;
                    case "guinea":
                        GuineaPig guineaPig = new GuineaPig(name, age);
                        animals.put(guineaPig, guineaPig.toString());
                        break;
                    default:
                        break;
                }
            }
            else {
                System.out.println("Incorrect input. Age <= 0");
            }
        }
        else{
            System.out.println("Incorrect input. Unsupported pet type");
        }
    }
}

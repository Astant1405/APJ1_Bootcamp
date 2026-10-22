package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        CopyOnWriteArrayList<String> animals = new CopyOnWriteArrayList<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
            while(true){
                try{
                    int num = Integer.parseInt(input.readLine());
                    if(num > 0){
                        for(int i = 0; i < num; i++){
                            String animal = input.readLine();
                            animalInit(animal, animals, input, startTime, executor);
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
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
            for(String animal : animals){
                System.out.println(animal);
            }
        }
    }

    public static void animalInit(String animal, CopyOnWriteArrayList<String> animals, BufferedReader input, long begin, ExecutorService executor) throws IOException{
        if(animal.equals("dog") || animal.equals("cat")){
            String name = input.readLine();
            int age = Integer.parseInt(input.readLine());
            if(age > 0){
                if (animal.equals("dog")) {
                    Dog dog = new Dog(name, age);
                    long start = System.nanoTime() - begin;
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            double finish = dog.goToWalk();
                            animals.add(dog.toString() + String.format(", start time = %.2f, end time = %.2f", start/1_000_000_000.0, start/1_000_000_000.0 + finish));
                        }
                    });
                }
                else {
                    Cat cat = new Cat(name, age);
                    long start = System.nanoTime() - begin;
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            double finish = cat.goToWalk();
                            animals.add(cat.toString() + String.format(", start time = %.2f, end time = %.2f", start/1_000_000_000.0, start/1_000_000_000.0 + finish));
                        }
                    });
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

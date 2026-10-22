package org.example;
import java.util.Scanner;
import  java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Scanner input = input();
        while(true) {
            if (input.hasNextInt()) {
                int seconds = input.nextInt();
                if (seconds >= 0) {
                    LocalTime time = Time(seconds);
                    output(time);
                } else {
                    System.out.println("Incorrect time.");
                }
                break;
            } else {
                System.out.println("Could not parse a number. Please try again");
                input.next();
            }
        }
    }

    public static LocalTime Time(int seconds) {
        LocalTime time = LocalTime.ofSecondOfDay(seconds);
        return time;
    }

    public static Scanner input(){
        Scanner input = new Scanner(System.in);
        return input;

    }

    public static void output(LocalTime time){
        System.out.println(time);
    }
}

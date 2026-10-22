package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) throws IOException{
        double[] coordinates = new double[6];
        int count = 0;
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
            while(count < 6) {
                try{
                    double num = Double.parseDouble(input.readLine());
                    coordinates[count] = num;
                    count++;
                }
                catch(NumberFormatException e){
                    System.out.println("Could not parse a number. Please try again");
                }
            }
            perimeter(coordinates);
        }
    }

    public static void perimeter(double[] coordinates){
        if (coordinates[0] == coordinates[2] && coordinates[1] == coordinates[3] ||
                coordinates[0] == coordinates[4] && coordinates[1] == coordinates[5] ||
                coordinates[2] == coordinates[4] && coordinates[3] == coordinates[5]){
            System.out.println("It's not a triangle");
        }
        else{
            double first = Math.pow((Math.pow(coordinates[0] - coordinates[2], 2) + Math.pow(coordinates[1] - coordinates[3], 2)), 0.5);
            double second = Math.pow((Math.pow(coordinates[2] - coordinates[4], 2) + Math.pow(coordinates[3] - coordinates[5], 2)), 0.5);
            double third = Math.pow((Math.pow(coordinates[0] - coordinates[4], 2) + Math.pow(coordinates[1] - coordinates[5], 2)), 0.5);
            System.out.println("Perimeter: " + String.format("%.3f", first + second + third));
        }
    }
}
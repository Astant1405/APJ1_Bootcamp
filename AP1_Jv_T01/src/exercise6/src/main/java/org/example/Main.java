package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
            int nums = 0;
            while(true) {
                try {
                    nums = Integer.parseInt(input.readLine());
                    if(nums <= 0) {
                        System.out.println("Input error. Size <= 0. Try again:");
                        continue;
                    }
                    break;
                }
                catch (NumberFormatException e) {
                    System.out.println("Could not parse a number. Please try again");
                }
            }
            double[] array = new double[nums];
            for(int i = 0; i < nums; i++) {
                while(true) {
                    try {
                        array[i] = Double.parseDouble(input.readLine());
                        break;
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Could not parse a number. Please try again");
                    }
                }
            }
            sort(array);
        }
    }

    public static void sort(double[] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length - i - 1; j++){
                double buffer = array[j];
                if(array[j] > array[j+1]){
                    array[j] = array[j+1];
                    array[j+1] = buffer;
                }
            }
        }
        for(double num : array){
            System.out.print(num + " ");
        }
    }
}

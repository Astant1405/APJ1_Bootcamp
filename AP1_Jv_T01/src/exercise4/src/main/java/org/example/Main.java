package org.example;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        try(Scanner input = new Scanner(System.in)){
            while(true){
                if(input.hasNextInt()){
                    int nums = input.nextInt();
                    if(nums <= 0) {
                        System.out.println("Input error. Size <= 0");
                        break;
                    }
                    int[] array = new int[nums];
                    int count = 0;
                    double sum = 0;
                    int i = 0;
                    while(i < nums){
                        if(input.hasNextInt()){
                            array[i] = input.nextInt();
                            i++;
                        }
                        else{
                            System.out.println("Could not parse a number. Please try again");
                            input.next();
                        }
                    }
                    for (int num : array){
                        if(num < 0){
                            sum += num;
                            count += 1;
                        }
                    }
                    if(count == 0){
                        System.out.println("There are no negative elements");
                    }
                    else{
                        System.out.println(sum / count);
                    }
                    break;
                }
                else{
                    System.out.println("Could not parse a number. Please try again");
                    input.next();
                }
            }
        }
    }
}

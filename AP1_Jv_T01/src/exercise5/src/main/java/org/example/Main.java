package org.example;

import java.util.Scanner;
import java.util.ArrayList;

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
                    firstLast(array);
                    break;
                }
                else{
                    System.out.println("Could not parse a number. Please try again");
                    input.next();
                }
            }
        }
    }
    public static void firstLast(int[] array){
        ArrayList<Integer> nums = new ArrayList<>();
        for (int num : array){
            int buffer = ((Double) Math.floor(num / (Math.pow(10, (num + "").length() - 1)))).intValue();
            if (num % 10 == buffer){
                nums.add(num);
            }
        }
        if (nums.size() == 0){
            System.out.println("There are no such elements");
        }
        else{
            for(int num : nums){
                System.out.print(num + " ");
            }
        }
    }
}


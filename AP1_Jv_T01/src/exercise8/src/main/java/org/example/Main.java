package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner input = new Scanner(System.in)){
            int count = 1;
            int buffer = 0;
            boolean status = false;
            while(true){
                if(input.hasNextInt() && status == true){
                    int num = input.nextInt();
                    if(buffer > num){
                        System.out.println("The sequence is not ordered from the ordinal number of the number " + num);
                        return;
                    }
                    else{
                        buffer = num;
                        count++;
                    }
                }
                else if(input.hasNextInt() && status == false) {
                    buffer = input.nextInt();
                    status = true;
                }
                else{
                    break;
                }
            }
            if(count == 1){
                System.out.println("Input error");
            }
            else{
                System.out.println("The sequence is ordered in ascending order");
            }
        }
    }
}

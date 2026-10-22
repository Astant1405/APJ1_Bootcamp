package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)){
            while(true){
                if(input.hasNextInt()){
                    int num = input.nextInt();
                    System.out.println(fibonachi(num));
                    break;
                }
                else{
                    System.out.println("Could not parse a number. Please try again");
                    input.next();
                }
            }
        }
        catch (StackOverflowError e){
            System.out.println("Too large n");
        }
    }
    public static int fibonachi(int n){
        if (n == 0){
            return 0;
        }
        else if (n == 1){
            return 1;
        }
        else{
            return fibonachi(n - 1) + fibonachi(n - 2);
        }
    }
}
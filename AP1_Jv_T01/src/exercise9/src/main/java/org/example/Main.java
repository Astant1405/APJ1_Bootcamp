package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> array = new ArrayList<>();
        try(Scanner input = new Scanner(System.in)){
            int n = input.nextInt();
            if(n <= 0){
                System.out.println("Input error. Size <= 0");
            }
            else{
                input.nextLine();
                for(int i = 0; i < n; i++){
                    String str = input.nextLine();
                    array.add(i, str);
                }
                String substr = input.nextLine();
                filter(array, substr);
            }
        }
    }

    public static void filter(ArrayList<String> array, String substr){
        boolean status = false;
        for(String str : array){
            if(str.contains(substr)){
                if(status == false){
                    System.out.print(str);
                    status = true;
                }
                else{
                    System.out.print(", " + str);
                }

            }
        }
    }
}

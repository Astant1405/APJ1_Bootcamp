package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String file = scanner.nextLine();
        String[] array;
        ArrayList<String> list = new ArrayList<>();
        try(Scanner input = new Scanner(new File(file))){
            int n = Integer.parseInt(input.nextLine());
            if(n <= 0){
                System.out.println("Input error. Size <= 0");
            }
            else{
                array = input.nextLine().split(" ");
                double max = Double.MIN_VALUE;
                double min = Double.MAX_VALUE;
                int count = 0;
                for(int i = 0; i < array.length; i++) {
                    try {
                        if (max < Double.parseDouble(array[i])) {
                            max = Double.parseDouble(array[i]);
                        }
                        if (min > Double.parseDouble(array[i])) {
                            min = Double.parseDouble(array[i]);
                        }
                        count++;
                        list.add(array[i]);
                    } catch (NumberFormatException e) {

                    }
                }
                if(count < n){
                    System.out.println("Input error. Insufficient number of items");
                }
                else{
                    System.out.println(n);
                    for(String elem : list){
                        System.out.print(elem + " ");
                    }
                    System.out.println();
                    System.out.println("Saving min and max values to file");
                    try(FileWriter writer = new FileWriter("/home/astant/Рабочий_стол/AP1_Jv_T01.ID_1369279-1/src/exercise7/src/main/java/org/example/result.txt", false))
                    {
                        writer.write(min + " " + max + '\n');
                        writer.flush();
                    }
                    catch(IOException ex){
                        System.out.println("Input error. File doesn't exist");
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Input error. File doesn't exist");
        }
        finally {
            scanner.close();
        }
    }
}


package org.example;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Name {
    public static void main(String[] args) {
        ArrayList<User> array = new ArrayList<>();
        try(Scanner input = new Scanner(System.in)){
            while(true){
                if(input.hasNextInt()){
                    int num = input.nextInt();
                    input.nextLine();
                    for(int i = 0; i < num; i++){
                        if(i != 0){
                            input.nextLine();
                        }
                        String name = input.nextLine();
                        if (input.hasNextInt()){
                            int age = input.nextInt();
                            if(age <= 0){
                                System.out.println("Incorrect input. Age <= 0");
                                num++;
                            }
                            else{
                                User person = new User(name, age);
                                array.add(person);
                            }
                        }
                        else {
                            System.out.println("Could not parse a number. Please try again");
                            input.next();
                        }
                    }
                    break;
                }
                else{
                    System.out.println("Could not parse a number. Please try again");
                    input.next();
                }
            }
            getStream(array);
        }
    }
    public static void getStream(ArrayList<User> array){
        Stream<User> people = array.stream();
        List<User> result = people.filter(x -> x.getAge() >= 18).collect(Collectors.toList());
        boolean status = false;
        for(User person : result){
            if(status == false){
                System.out.print(person.getName());
                status = true;
            }
            else {
                System.out.print(", " + person.getName());
            }
        }
    }
}

package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static CRUD_User_and_Food obiect = new CRUD_User_and_Food();
    private static Scanner scanner = new Scanner(System.in);

    static User user1 = new User("victor","pwdv");

    static Food food = new Food("paste carbonara",1);

    public static void main(String[] args) {

        long id = -1;
        User user =null;

        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("username: ");
            String username = sc.nextLine();
            System.out.println("password: ");
            String password = sc.nextLine();

            user = new User(username,password);

            id = obiect.login(user);

            user.setId(id);

            if(id!=-1)
                break;

        }

        // ura sunt logat , deci pot baga mancare

        while(true) {

            boolean isAdmin = obiect.isAdmin(user);
            if (!isAdmin) {
                boolean exit = false;
                int choice=0;
                printInstructionsUser();
                while (!exit){
                    System.out.println("enter your choice: ");
                    try{
                        choice= scanner.nextInt();
                        scanner.nextLine();
                    }catch (InputMismatchException e){
                        e.printStackTrace();
                    }

                    switch (choice){
                        case 0 -> printInstructionsUser();
                        case 1 -> obiect.createFood(food);
                        case 2 -> obiect.readFoodsOfAnUser();
                        case 3 -> exit = true;
                    }
                }


            } else {

                    boolean exit = false;
                    int choice=0;
                    printInstructionAdmin();
                    while (!exit){
                        System.out.println("enter your choice: ");
                        try{
                            choice= scanner.nextInt();
                            scanner.nextLine();
                        }catch (InputMismatchException e){
                            e.printStackTrace();
                        }

                        switch (choice){
                            case 0 -> printInstructionAdmin();
                            case 1 -> obiect.createUser(user1,false);
                            case 2 -> obiect.read();
                            case 3 -> obiect.update(user1);
                            case 4 -> obiect.delete(user1);
                            case 5 -> obiect.createFood(food);
                            case 6 -> obiect.readFoodsOfAnUser();
                            case 7 -> obiect.updateFood(food);
                            case 8 -> obiect.deleteFood(food);
                            case 9 -> exit = true;

                        }
                    }break;
                }
        }
    }

    public static void printInstructionsUser(){
        System.out.println("\nPress");
        System.out.println("\t 0 - to print choice options");
        System.out.println("\t 1 - to create an a food for an user in DB");
        System.out.println("\t 2 - to read a food from DB");
        System.out.println("\t 3 - to exit");
    }

    public static void printInstructionAdmin(){
        System.out.println("\nPress");
        System.out.println("\t 0 - to print choice options");
        System.out.println("\t 1 - to create an user in DB");
        System.out.println("\t 2 - to read all the users from DB");
        System.out.println("\t 3 - to update an user from DB");
        System.out.println("\t 4 - to delete an user from DB");
        System.out.println("\t 5 - to create a food for an user in DB");
        System.out.println("\t 6 - to read a food from DB");
        System.out.println("\t 7 - to update a food in DB");
        System.out.println("\t 8 - to delete a food from DB");
        System.out.println("\t 9 - to exit");
    }
}



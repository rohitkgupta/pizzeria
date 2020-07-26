package com.pizzeria.store.client;

import java.util.Scanner;

public class CommandLineClient {

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if("1".equals(input)){
                printInventoryMenu();
                input = in.nextLine();
                System.out.println("You selected "+input);
            } else if("2".equals(input)) {
                printOrderMenu();
                input = in.nextLine();
                System.out.println("You selected "+input);
            }  else if("3".equals(input)) {
                break;
            }else {
                System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("===============Main Menu===============");
        System.out.println("Press 1 to go to Vendor menu");
        System.out.println("Press 2 to go to Order menu");
        System.out.println("Press 3 to Exist");
    }

    private static void printOrderMenu() {
        System.out.println("==============Order Pizz================");
        System.out.println("Press 1 to Veg Pizza");
        System.out.println("Press 2 to Non Veg Pizza");
    }

    private static void printInventoryMenu() {
        System.out.println("=============Manage Inventory=================");
        System.out.println("Press 1 to Add item");
        System.out.println("Press 2 to Restock item");
        System.out.println("Press 3 to Update price");
    }
}

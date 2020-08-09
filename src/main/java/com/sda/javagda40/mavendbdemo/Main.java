package com.sda.javagda40.mavendbdemo;

import com.sda.javagda40.mavendbdemo.database.EntityDao;
import com.sda.javagda40.mavendbdemo.database.HibernateUtil;
import com.sda.javagda40.mavendbdemo.model.Address;
import com.sda.javagda40.mavendbdemo.model.AppUser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);

        String command;

        do {
            System.out.println("Wprowadz komende: ");
            command = scanner.nextLine();
            // serwis aukcyjny allegro
            // words = { "serwis", "aukcyjny", "allegro"}
            String[] words = command.split(" ");


            // user list
            if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("list")) {
                handleListUsers(words);
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddUser(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleAddUser(String[] words) {

    }

    private static void handleListUsers(String[] words) {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(AppUser.class)
                .forEach(System.out::println);
    }
}

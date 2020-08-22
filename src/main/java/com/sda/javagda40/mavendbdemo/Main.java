package com.sda.javagda40.mavendbdemo;

import com.sda.javagda40.mavendbdemo.database.AppUserDao;
import com.sda.javagda40.mavendbdemo.database.EntityDao;
import com.sda.javagda40.mavendbdemo.model.AppUser;
import com.sda.javagda40.mavendbdemo.model.AppUser.AppUserBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);

        String command;

        do {
            System.out.println("Wprowadz komende: ");
            printAllOptions();
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

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
    }

    private static void handleAddUser(String[] words) {
        AppUserDao appUserDao = new AppUserDao();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        if (!appUserDao.existsUserWithLogin(words[4])) {
            AppUser appUser = AppUser.builder()
                    .firstName(words[2])
                    .lastName(words[3])
                    .login(words[4])
                    .password(words[5])
                    .build();

            appUserEntityDao.saveOrUpdate(appUser);
            System.out.println("User saved: " + appUser.getId());
        }else{
            System.err.println("User cannot be saved. Login already exists.");
        }
    }

    private static void handleListUsers(String[] words) {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(AppUser.class)
                .forEach(System.out::println);
    }
}

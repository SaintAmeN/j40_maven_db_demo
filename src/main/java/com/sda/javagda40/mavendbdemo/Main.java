package com.sda.javagda40.mavendbdemo;

import com.sda.javagda40.mavendbdemo.database.AppUserDao;
import com.sda.javagda40.mavendbdemo.database.EntityDao;
import com.sda.javagda40.mavendbdemo.database.ServiceDao;
import com.sda.javagda40.mavendbdemo.model.*;
import com.sda.javagda40.mavendbdemo.model.AppUser.AppUserBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Initial version.");

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
                handleListUsers();
            } else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("list")) {
                handleListServices();
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddUser(words);
            } else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("add")) {
                handleServiceAdd(words);
            } else if (words[0].equalsIgnoreCase("extraservice") &&
                    words[1].equalsIgnoreCase("add")) {
                handleExtraServiceAdd(words);
            } else if (words[0].equalsIgnoreCase("order") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddOrder(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleAddOrder(String[] words) {
        EntityDao<ServiceOrder> serviceOrderEntityDao = new EntityDao<>();
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        EntityDao<ExtraService> extraServiceEntityDao = new EntityDao<>();

        Long id = Long.parseLong(words[2]); // id uzytkownika
        Optional<AppUser> appUserOptional = appUserEntityDao.findById(AppUser.class, id);
        if (appUserOptional.isPresent()) { // uzytkownik istnieje
            AppUser appUser = appUserOptional.get();

            Optional<Service> optionalService = askUserForService(serviceEntityDao.findAll(Service.class), serviceEntityDao);
            ServiceOrder serviceOrder;
            if (optionalService.isPresent()) {
                Service service = optionalService.get();

                Set<ExtraService> extraServices = getAllExtraServicesFromUser(service);
                serviceOrder = new ServiceOrder();
                serviceOrder.setService(service);
                serviceOrder.setExtraServices(extraServices);

                serviceOrderEntityDao.saveOrUpdate(serviceOrder);

            } else {
                System.err.println("Service does not exist");
                return;
            }
            appUser.getServiceOrders().add(serviceOrder);
            appUserEntityDao.saveOrUpdate(appUser);
        } else {
            System.err.println("User does not exist");
        }
    }

    private static Set<ExtraService> getAllExtraServicesFromUser(Service service) {
        List<ExtraService> extraServices = new ArrayList<>();

        String input;
        do {
            System.out.println("Extra services, enter id:");
            service.getAvailableExtraServices().forEach(System.out::println);

            Long id = Long.valueOf(scanner.nextLine());
            extraServices.addAll(
                    service.getAvailableExtraServices().stream()
                            .filter(extraService -> extraService.getId() == id)
                            .collect(Collectors.toSet()));
            System.out.println("Czy chcesz dodać kolejne usługi? [y/n]");
            input = scanner.nextLine();

        } while (input.equalsIgnoreCase("y") ||
                input.equalsIgnoreCase("t") ||
                input.startsWith("y") ||
                input.startsWith("t"));
        return new HashSet<>(extraServices);
    }

    private static void handleExtraServiceAdd(String[] words) {
        ServiceDao serviceDao = new ServiceDao();
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        EntityDao<ExtraService> extraServiceEntityDao = new EntityDao<>();
        Optional<Service> optionalService = askUserForService(serviceDao.findAllServicesByName(words[2]), serviceEntityDao);

        if (optionalService.isPresent()) {
            Service service = optionalService.get();
            String input;
            do {
                System.out.println("Podaj nazwę usługi:");
                String name = scanner.nextLine();
                System.out.println("Podaj cene usługi:");
                Double price = Double.parseDouble(scanner.nextLine());

                ExtraService extraService = new ExtraService(name, price);
                extraServiceEntityDao.saveOrUpdate(extraService);       // najpierw zapis nowej encji

                service.getAvailableExtraServices().add(extraService);  // dopiero po tym powiązanie z tabelą relacyjną
                serviceEntityDao.saveOrUpdate(service);                 // zapis relacji

                System.out.println("Czy chcesz dodać kolejną usługę? [y/n]");
                input = scanner.nextLine();
            } while (input.equalsIgnoreCase("y") ||
                    input.equalsIgnoreCase("t") ||
                    input.startsWith("y") ||
                    input.startsWith("t"));
        } else {
            System.err.println("Wprowadzono niepoprawną usługę");
        }

    }

    private static Optional<Service> askUserForService(List<Service> allServicesByName, EntityDao<Service> serviceEntityDao) {
        List<Service> serviceList = allServicesByName;
        System.out.println("To znalezione wyniki, którą usługę wybierasz:");
        serviceList.forEach(System.out::println);
        System.out.println();
        System.out.println("Wprowadź ID:");

        Long id = Long.parseLong(scanner.nextLine()); // id uzytkownika
        return serviceEntityDao.findById(Service.class, id);
    }

    private static void handleServiceAdd(String[] words) {
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        Service service = Service.builder()
                .name(words[2])
                .price(Double.parseDouble(words[3]))
                .duration(Integer.parseInt(words[4]))
                .type(ServiceType.valueOf(words[5])).build();

        serviceEntityDao.saveOrUpdate(service);
        System.out.println("Service added");
    }

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [service list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [service add {name} {price} {duration} {type}] ");
        System.out.println("- [extraservice add {service searchphrase} ]");
        System.out.println("- [order add {userid} ]");
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
        } else {
            System.err.println("User cannot be saved. Login already exists.");
        }
    }

    private static void handleListUsers() {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(AppUser.class)
                .forEach(System.out::println);
    }

    private static void handleListServices() {
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        serviceEntityDao
                .findAll(Service.class)
                .forEach(System.out::println);
    }
}

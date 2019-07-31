package service;

import java.util.Scanner;

public interface CommandLineService {

    static void run(Scanner scanner, ContactService service){
        boolean exit = true;
        do {
            System.out.println("Chose your wish:");
            showMenu();
            if (scanner.hasNextInt()){
                switch (scanner.nextInt()) {
                    case 1: {
                        service.createContact(scanner);
                    }
                    case 2: {
                        service.updateContact(scanner);
                    }
                    case 3: {
                        service.removeContact(scanner);
                    }
                    case 4: {
                        service.showAllContacts();
                    }
                    case 5: {
                        System.out.println((service.findById(scanner)));
                    }
                    case 0: {
                        System.out.println("Thank you that use our app. Good bye.");
                        exit = false;
                        break;
                    }
                    default: {
                        throw new (AddressBookException (ResponseCode.WRONG_DATA_TYPE, "Enter wrong num of oper") {
                            System.out.println(e.getMessage());
                        }
                    }

                }

            }else {
                System.out.println("Ypu enter wrong number");
                scanner.next();
            }

        } while (exit);

    }

    static void showMenu(){
        System.out.println("1. Add");
        System.out.println("2. Update");
        System.out.println("3. Delete");
        System.out.println("4. Show all contacts");
        System.out.println("5. Show contact by id");
        System.out.println("0. Exit");
    }
}

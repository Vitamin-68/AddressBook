package service;

import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.util.Scanner;

public interface CommandLineService {

    static void run(Scanner scanner, ContactService service) {
        boolean exit = true;
        do {
            System.out.println("Enter number of operation (0-5):");
            showMenu();
            try {
                if (scanner.hasNextInt()) {
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
                            throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                    "You enter wrong num of operation");
                        }
                    }

                } else {
                    System.out.println("You entered wrong number");
                    scanner.next();
                }
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }

        } while (exit);

    }

    static void showMenu() {
        System.out.println("1. Add contact");
        System.out.println("2. Update contact");
        System.out.println("3. Delete contact");
        System.out.println("4. Show all contacts");
        System.out.println("5. Show contact by id");
        System.out.println("0. Exit");
    }
}

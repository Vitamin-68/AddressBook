package service;

import constants.Constants;
import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.util.Scanner;

public interface CommandLineService {

    static void run(Scanner scanner, ContactService service) {
        boolean exit = true;
        do {
            System.out.println("\nEnter number of operation (0-5):");
            showMenu();
            try {
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case Constants.ADD_CONTACT: {
                            service.createContact(scanner);
                            break;
                        }
                        case Constants.UPDATE_CONTACT: {
                            service.updateContact(scanner);
                            break;
                        }
                        case Constants.DELETE_CONTACT: {
                            service.removeContact(scanner);
                            break;
                        }
                        case Constants.SHOW_ALL_CONTACT: {
                            service.showAllContacts(scanner);
                            break;
                        }
                        case Constants.SHOW_CONTACT_BY_ID: {
                            service.findById(scanner);
                            break;
                        }
                        case Constants.SHOW_CONTACT_BY_NAME: {
                            service.findByName(scanner);
                            break;
                        }
                        case Constants.EXIT: {
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
        System.out.println("6. Show contact by name");
        System.out.println("0. Exit");
    }

}

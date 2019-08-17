package service;

import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.util.Scanner;

public interface CommandLineService {

    int ADD_CONTACT = 1;
    int UPDATE_CONTACT = 2;
    int DELETE_CONTACT = 3;
    int SHOW_ALL_CONTACT = 4;
    int SHOW_CONTACT_BY_ID = 5;
    int EXIT = 0;

    static void run(Scanner scanner, ContactService service) {
        boolean exit = true;
        do {
            System.out.println("Enter number of operation (0-5):");
            showMenu();
            try {
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case ADD_CONTACT: {
                            service.createContact(scanner);
                            break;
                        }
                        case UPDATE_CONTACT: {
                            service.updateContact(scanner);
                            break;
                        }
                        case DELETE_CONTACT: {
                            service.removeContact(scanner);
                            break;
                        }
                        case SHOW_ALL_CONTACT: {
                            service.showAllContacts();
                            break;
                        }
                        case SHOW_CONTACT_BY_ID: {
                            service.findById(scanner);
                            break;
                        }
                        case EXIT: {
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

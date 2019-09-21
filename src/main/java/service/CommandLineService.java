package service;

import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.util.Scanner;

public interface CommandLineService {

    /**
     * Start Project. Display start menu.
     *
     * @param scanner - usual Java Scanner
     * @param service - class ContactServiceImpl object
     * @see ContactService
     */
    static void run(Scanner scanner, ContactService service) {
        System.out.println("Welcome to project \"Address Book\" based on Array.\nMade by Wetal\n");
        boolean exit = true;
        do {
            System.out.println("Enter number of operation (0-5):");
            showMenu();
            try {
                if (scanner.hasNextInt()) {
                    switch (scanner.nextInt()) {
                        case 1: {
                            service.createContact(scanner);
                            break;
                        }
                        case 2: {
                            service.updateContact(scanner);
                            break;
                        }
                        case 3: {
                            service.removeContact(scanner);
                            break;
                        }
                        case 4: {
                            service.showAllContacts();
                            break;
                        }
                        case 5: {
                            service.findById(scanner);
                            break;
                        }
                        case 0: {
                            System.out.println("Thank you that use our app. Good bye.");
                            exit = false;
                            break;
                        }
                        default: {
                            throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                    "You enter wrong num of operation.\n");
                        }
                    }

                } else {
                    System.out.println("Please enter only numbers.\n");
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

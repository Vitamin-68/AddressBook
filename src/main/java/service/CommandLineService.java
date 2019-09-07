package service;

import constants.Constants;
import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;

public interface CommandLineService {

    static void run(BufferedReader bufReader, ContactService service) throws IOException {
        boolean exit = true;
        do {
            System.out.println("\nEnter number of operation (0-6 or 9 fo test):");
            showMenu();
            try {
                int inputInt = Integer.parseInt(bufReader.readLine().trim());
                    switch (inputInt) {
                        case Constants.ADD_CONTACT: {
                            service.createContact(bufReader);
                            break;
                        }
                        case Constants.UPDATE_CONTACT: {
                            service.updateContact(bufReader);
                            break;
                        }
                        case Constants.DELETE_CONTACT: {
                            service.removeContact(bufReader);
                            break;
                        }
                        case Constants.SHOW_ALL_CONTACT: {
                            service.showAllContacts(bufReader);
                            break;
                        }
                        case Constants.SHOW_CONTACT_BY_ID: {
                            service.findById(bufReader);
                            break;
                        }
                        case Constants.SHOW_CONTACT_BY_NAME: {
                            service.findByName(bufReader);
                            break;
                        }
                        case Constants.TEST: {
                            service.test();
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

            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY number");
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
        System.out.println("9. Add 4 contacts for test");
        System.out.println("0. Exit");
    }

}

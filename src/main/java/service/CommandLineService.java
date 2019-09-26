package service;

import constants.Constants;
import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;

public interface CommandLineService {

    static void run(BufferedReader bufReader, ContactService service) throws IOException {
        System.out.println("Welcome to project \"Address Book\" based on Lambda.\nMade by Wetal\n");
        loadAllContactFromFile(bufReader, service);
        boolean exit = false;
        do {
            System.out.println("\nEnter number of operation (0-6 or 9 for test):");
            showMenu();
            try {
                int numberOfChoice = Integer.parseInt(bufReader.readLine().trim());
                switch (numberOfChoice) {
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
                    case Constants.FIND_CONTACT: {
                        findOneContact(bufReader, service);
                        break;
                    }
                    case Constants.SAVE_ALL_CONTACT: {
                        saveAllContactToFile(bufReader, service);
                        break;
                    }
                    case Constants.LOAD_ALL_CONTACT: {
                        loadAllContactFromFile(bufReader, service);
                        break;
                    }
                    case Constants.TEST: {
                        service.test();
                        break;
                    }
                    case Constants.EXIT: {
                        saveAllContactToFile(bufReader, service);
                        bufReader.close();
                        System.out.println("Thank you that use our app. Good bye.");
                        exit = true;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong number of operation");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers");
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }

        } while (!exit);

    }

    static void showMenu() {
        System.out.println("1. Add contact.");
        System.out.println("2. Update contact.");
        System.out.println("3. Delete contact.");
        System.out.println("4. Show all contacts.");
        System.out.println("5. Show contact.");
        System.out.println("6. Save all contact to file.");
        System.out.println("7. Load all contact from file.");
        System.out.println("9. Add 4 contacts for test.");
        System.out.println("0. Exit");
    }

    static void loadAllContactFromFile(BufferedReader bufReader, ContactService service) throws IOException {
        System.out.println("Do you want load all contacts from file?");
        System.out.println("1. Load from file \"" + Constants.TXT_LIST_PATH + "\".");
        System.out.println("2. Load from file \"" + Constants.DAT_LIST_PATH + "\".");
        System.out.println("0. Do not load Contacts.");
        boolean exit = false;
        do {
            try {
                int numberOfChoice = Integer.parseInt(bufReader.readLine().trim());
                switch (numberOfChoice) {
                    case Constants.LOAD_FROM_TXT_FILE: {
                        service.loadAllContactsFromTxtFile(bufReader);
                        exit = true;
                        break;
                    }
                    case Constants.LOAD_FROM_DATA_FILE: {
                        service.loadAllContactsFromDatFile();
                        exit = true;
                        break;
                    }
                    case Constants.EXIT: {
                        exit = true;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong num of operation");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.");
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }
        } while (!exit);
    }



    static void saveAllContactToFile(BufferedReader bufReader, ContactService service) throws IOException {
        System.out.println("Do you want save all contacts to file?");
        System.out.println("1. Save to file \"" + Constants.TXT_LIST_PATH + "\" and Exit.");
        System.out.println("2. Save to file \"" + Constants.DAT_LIST_PATH + "\" and Exit.");
        System.out.println("0. Exit without saving.");
        boolean exit = false;
        do {
            try {
                int numberOfChoice = Integer.parseInt(bufReader.readLine().trim());
                switch (numberOfChoice) {
                    case Constants.SAVE_TO_TXT_FILE: {
                        service.saveAllContactsToTxtFile();
                        exit = true;
                        break;
                    }
                    case Constants.SAVE_TO_DAT_FILE: {
                        service.saveAllContactsToDatFile();
                        exit = true;
                        break;
                    }
                    case Constants.EXIT: {
                        exit = true;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong num of operation");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.");
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }
        } while (!exit);
    }

    static void findOneContact(BufferedReader bufReader, ContactService service) throws IOException {
        boolean exit = false;
        do {
            System.out.println("Find contact by:");
            System.out.println("1. ID.");
            System.out.println("2. Name.");
            System.out.println("0. Exit to previous menu.");
            try {
                int numberOfChoice = Integer.parseInt(bufReader.readLine().trim());
                switch (numberOfChoice) {
                    case Constants.FIND_BY_ID: {
                        service.findById(bufReader);
                        exit = true;
                        break;
                    }
                    case Constants.FIND_BY_NAME: {
                        service.findByName(bufReader);
                        exit = true;
                        break;
                    }
                    case Constants.EXIT: {
                        exit = true;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong num of operation");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.");
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }
        } while (!exit);
    }

}

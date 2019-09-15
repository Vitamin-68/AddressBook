package service;

import constants.Constants;
import exceptions.MyAddressBookException;
import constants.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;

public interface CommandLineService {

    static void run(BufferedReader bufReader, ContactService service) throws IOException {
        try {
            loadContactFromFile(bufReader, service);
        } catch (MyAddressBookException e) {
            e.printStackTrace();
        }
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
                        saveAndExit(bufReader, service);
                        bufReader.close();
                        System.out.println("Thank you that use our app. Good bye.");
                        exit = true;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong num of operation");
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
        System.out.println("1. Add contact");
        System.out.println("2. Update contact");
        System.out.println("3. Delete contact");
        System.out.println("4. Show all contacts");
        System.out.println("5. Show contact by id");
        System.out.println("6. Show contact by name");
        System.out.println("9. Add 4 contacts for test");
        System.out.println("0. Exit");
    }

    static void loadContactFromFile(BufferedReader bufReader, ContactService service) throws MyAddressBookException {
        System.out.println("Welcome to project \"Address Book\" made by Wetal\n");
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
            } catch (IOException e) {
                System.out.println("Please enter ONLY numbers.");
            }
        } while (!exit);
    }



    static void saveAndExit(BufferedReader bufReader, ContactService service) throws MyAddressBookException {
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
            } catch (IOException e) {
                System.out.println(e);
                System.out.println("Please enter ONLY numbers.");
            }
        } while (!exit);
    }

}

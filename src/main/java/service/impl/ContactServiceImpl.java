package service.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import dao.impl.ContactDaoImpl;
import entity.Contact;
import exceptions.MyAddressBookException;
import service.ContactService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ContactServiceImpl implements ContactService {

    private static final ContactDao contactDao = new ContactDaoImpl();

    public ContactServiceImpl(ContactDaoImpl contactDao) {

    }

    @Override
    public Contact createContact(Scanner scanner) {
        Contact contact = new Contact();
        contact.setName(onlySymbolsName("Enter name of new contact:", scanner));
        contact.setLastName(onlySymbolsName("Enter last name of new contact:", scanner));
        contact.setAge(onlyNumbersString("Enter age of new contact:", scanner));
        contact.setPhoneNumber(onlyNumbersString("Enter phone number of new contact:", scanner));
        contact.setMarried(isMarried(scanner));
        contact.setCreateDate(LocalDateTime.now());
        contact.setUpdateTime(LocalDateTime.now());
        return contactDao.createContact(contact);
    }

    @Override
    public Contact updateContact(Scanner scanner) {
        Contact contact = new Contact();
        int index = findById(scanner);
        if (index >= 0) {
            contactDao.cloneContact(contact, index);
            boolean exit = true;
            do {
                menuUpdateContact();
                try {
                    if (scanner.hasNextInt()) {
                        int numberOfField = scanner.nextInt();
                        switch (numberOfField) {
                            case Constants.SELECT_NAME_CONTACT: {
                                contact.setName(onlySymbolsName("Enter new name:", scanner));
                                contact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case Constants.SELECT_LAST_NAME_CONTACT: {
                                contact.setLastName(onlySymbolsName("Enter new last name:", scanner));
                                contact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case Constants.SELECT_AGE_CONTACT: {
                                contact.setAge(onlyNumbersString("Enter age of new contact:", scanner));
                                contact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case Constants.SELECT_PHONE_CONTACT: {
                                contact.setPhoneNumber(onlyNumbersString("Enter new number phone:", scanner));
                                contact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case Constants.SELECT_STATUS_CONTACT: {
                                contact.setMarried(isMarried(scanner));
                                contact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case Constants.EXIT: {
                                contactDao.updateContact(contact, index);
                                System.out.println("Update is done.\n\n");
                                exit = false;
                                break;
                            }
                            default: {
                                throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                        "You enter wrong number of operation.");
                            }
                        }
                    } else {
                        System.out.println("Only numbers please.");
                        scanner.next();
                    }
                } catch (MyAddressBookException e) {
                    System.out.println(e.getMessage());
                }
            } while (exit);
        }
        return contact;
    }

    @Override
    public boolean removeContact(Scanner scanner) {
        while (true) {
            System.out.println("Enter ID for delete:");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                return contactDao.removeContact(id, scanner);
            } else {
                System.out.println("You entered wrong ID number.");
                scanner.next();
            }
        }

    }

    @Override
    public void showAllContacts() {
        contactDao.showAllContacts();
    }

    @Override
    public int findById(Scanner scanner) {
        while (true) {
            System.out.println("Enter ID of contact:");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                return contactDao.findById(id);
            } else {
                System.out.println("You entered wrong ID number.");
                scanner.next();
            }
        }
    }

    private void menuUpdateContact() {
        System.out.println("Enter number of field for update (1-5)\n or 0 for Exit:");
        System.out.println("1. Name.");
        System.out.println("2. Last name.");
        System.out.println("3. Age.");
        System.out.println("4. Phone number.");
        System.out.println("5. Martial status.");
        System.out.println("0. Save contact & Exit.");
    }


    private String onlySymbolsName(String string, Scanner scanner) {
        while (true) {
            System.out.println(string);
            String str = scanner.next().trim();
            if (str.chars().allMatch(Character::isLetter)) {
                return str;
            } else {
                System.out.println("Only letters please.");
            }
        }
    }

    private int onlyNumbersString(String string, Scanner scanner) {
        while (true) {
            System.out.println(string);
            String numner = scanner.next().trim();
            if (numner.matches("[0-9]+")) {
                return Integer.parseInt(numner);
            } else {
                System.out.println("Only numbers please.");
            }
        }
    }

    private boolean isMarried(Scanner scanner) {
        while (true) {
            System.out.println("Is contact married (y/n)?:");
            String isMarried = scanner.next();
            if (isMarried.equalsIgnoreCase("y")) {
                return true;
            } else if (isMarried.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Only \"y\" or \"n\":");
            }
        }
    }

}

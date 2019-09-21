package service.impl;

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
        Contact contact = findById(scanner);
        boolean exit = true;
        if (contact.getId() != 0) {
            Contact updatedContact = new Contact();
            updatedContact.setId(contact.getId());
            updatedContact.setName(contact.getName());
            updatedContact.setLastName(contact.getLastName());
            updatedContact.setAge(contact.getAge());
            updatedContact.setPhoneNumber(contact.getPhoneNumber());
            updatedContact.setMarried(contact.isMarried());
            updatedContact.setUpdateTime(contact.getUpdateTime());
            updatedContact.setCreateDate(contact.getCreateDate());
            do {
                menuUpdateContact();
                try {
                    if (scanner.hasNextInt()) {
                        int numberOfField = scanner.nextInt();
                        switch (numberOfField) {
                            case 1: {
                                updatedContact.setName(onlySymbolsName("Enter new name:", scanner));
                                updatedContact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case 2: {
                                updatedContact.setLastName(onlySymbolsName("Enter new last name:", scanner));
                                updatedContact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case 3: {
                                updatedContact.setAge(onlyNumbersString("Enter new age:", scanner));
                                updatedContact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case 4: {
                                updatedContact.setPhoneNumber(onlyNumbersString("Enter new number phone:", scanner));
                                updatedContact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case 5: {
                                updatedContact.setMarried(isMarried(scanner));
                                updatedContact.setUpdateTime(LocalDateTime.now());
                                break;
                            }
                            case 0: {
                                exit = false;
                                break;
                            }
                            default: {
                                throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                        "You enter wrong number of operation.");
                            }
                        }
                    } else {
                        System.out.println("Please enter only number.");
                        scanner.next();
                    }
                } catch (MyAddressBookException e) {
                    System.out.println(e.getMessage());
                }
            } while (exit);
            return contactDao.updateContact(updatedContact);
        }
        return contact;
    }

    @Override
    public boolean removeContact(Scanner scanner) {
        while (true) {
            System.out.println("Enter ID for delete:");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                return contactDao.removeContact(id);
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
    public Contact findById(Scanner scanner) {
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
            if (str.chars().allMatch(Character :: isLetter)) {
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

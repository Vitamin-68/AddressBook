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

    private final int SELECT_NAME_CONTACT = 2;
    private final int SELECT_LAST_NAME_CONTACT = 3;
    private final int SELECT_AGE_CONTACT = 4;
    private final int SELECT_PHONE_CONTACT = 5;
    private final int SELECT_STATUS_CONTACT = 6;
    private final int EXIT = 0;


    private static final ContactDao contactDao = new ContactDaoImpl();

    public ContactServiceImpl(ContactDaoImpl contactDao) {

    }

    @Override
    public Contact createContact(Scanner scanner) {
        Contact contact = new Contact();
        System.out.println("Enter name of new contact:");
        contact.setName(scanner.next());
        System.out.println("Enter last name of new contact:");
        contact.setLastName(scanner.next());
        System.out.println("Enter age of new contact");
        while (!scanner.hasNextInt()) {
            System.out.println("Indicate age in numbers!");
            scanner.next();
        }
        contact.setAge(scanner.nextInt());
        System.out.println("Enter phone number of new contact");
        contact.setPhoneNumber(scanner.next());
        System.out.println("Is contact married(y/n)?");
        contact.setMarried(scanner.next().equalsIgnoreCase("y"));
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
            boolean exit = true, flagEditContact = false;
            do {
                System.out.println("Enter number of field for update (2-6)\nor 0 for Exit:");
                try {
                    if (scanner.hasNextInt()) {
                        int numberOfField = scanner.nextInt();
                        switch (numberOfField) {
                            case SELECT_NAME_CONTACT: {
                                System.out.println("Enter new name:");
                                contact.setName(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case SELECT_LAST_NAME_CONTACT: {
                                System.out.println("Enter new last name:");
                                contact.setLastName(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case SELECT_AGE_CONTACT: {
                                System.out.println("Enter new age:");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Indicate age in numbers!");
                                    scanner.next();
                                }
                                contact.setAge(scanner.nextInt());
                                flagEditContact = true;
                                break;
                            }
                            case SELECT_PHONE_CONTACT: {
                                System.out.println("Enter new number phone:");
                                contact.setPhoneNumber(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case SELECT_STATUS_CONTACT: {
                                System.out.println("Is contact married(y/n)?");
                                contact.setMarried(scanner.next().equalsIgnoreCase("y"));
                                flagEditContact = true;
                                break;
                            }
                            case EXIT: {
                                if (flagEditContact) {
                                    contactDao.updateContact(contact, index);
                                    System.out.println("Update is done.\n\n");
                                } else {
                                    System.out.println("Nothing selected fo update.\n\n");
                                }
                                exit = false;
                                break;
                            }
                            default: {
                                throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                        "You enter wrong number of operation");
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
//            return contactDao.updateContact(contact,index);
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
        for (; ; ) {
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

}

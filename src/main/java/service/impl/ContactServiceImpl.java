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
        System.out.println("Enter name of contact:");
        contact.setName(scanner.next());
        System.out.println("Enter last name of contact:");
        contact.setLastName(scanner.next());
        System.out.println("Enter age of contact");
        while (!scanner.hasNextInt()) {
            System.out.println("Indicate age in numbers!");
            scanner.next();
        }
        contact.setAge(scanner.nextInt());
        System.out.println("Enter phone number of contact");
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
            boolean exit = true;
            do {
                System.out.println("Enter number of field for update (2-6)\nor 0 for Exit:");
                try {
                    if (scanner.hasNextInt()) {
                        int numberOfField = scanner.nextInt();
                        switch (numberOfField) {
                            case 2: {
                                System.out.println("Enter new name:");
                                contact.setName(scanner.next());
                                contactDao.saveUpdatedField(contact, numberOfField, index);
                                break;
                            }
                            case 3: {
                                System.out.println("Enter new last name:");
                                contact.setLastName(scanner.next());
                                contactDao.saveUpdatedField(contact, numberOfField, index);
                                break;
                            }
                            case 4: {
                                System.out.println("Enter new age:");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Indicate age in numbers!");
                                    scanner.next();
                                }
                                contact.setAge(scanner.nextInt());
                                contactDao.saveUpdatedField(contact, numberOfField, index);
                                break;
                            }
                            case 5: {
                                System.out.println("Enter new number phone:");
                                contact.setPhoneNumber(scanner.next());
                                contactDao.saveUpdatedField(contact, numberOfField, index);
                                break;
                            }
                            case 6: {
                                System.out.println("Is contact married(y/n)?");
                                contact.setMarried(scanner.next().equalsIgnoreCase("y"));
                                contactDao.saveUpdatedField(contact, numberOfField, index);
                                break;
                            }
                            case 0: {
                                System.out.println("Update is done.");
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
            return contactDao.updateContact(contact);
        }
        return contact;
    }

    @Override
    public boolean removeContact(Scanner scanner) {
        for (; ; ) {
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

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
        Contact contact = this.findById(scanner);
        boolean exit = true;
        do {
            System.out.println("Enter number of field for update (2-6)\n or 0 for Exit:");
            try {
                if (scanner.hasNextInt()) {
                    int numberOfField = scanner.nextInt();
                    switch (numberOfField) {
                        case 2: {
                            System.out.println("Enter new name:");
                            contact.setName(scanner.next());
                            contact.setUpdateTime(LocalDateTime.now());
                        }
                        case 3: {
                            System.out.println("Enter new last name:");
                            contact.setLastName(scanner.next());
                            contact.setUpdateTime(LocalDateTime.now());
                        }
                        case 4: {
                            System.out.println("Enter new age:");
                            contact.setAge(scanner.nextInt());
                            contact.setUpdateTime(LocalDateTime.now());
                        }
                        case 5: {
                            System.out.println("Enter new number phone:");
                            contact.setPhoneNumber(scanner.next());
                            contact.setUpdateTime(LocalDateTime.now());
                        }
                        case 6: {
                            System.out.println("Is contact married(y/n)?");
                            contact.setMarried(scanner.nextBoolean());
                            contact.setUpdateTime(LocalDateTime.now());
                        }
                        case 0: {
                            System.out.println("Update is done.");
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
        return contactDao.updateContact(contact);
    }

    @Override
    public boolean removeContact(Scanner scanner) {

        return false;
    }

    @Override
    public void showAllContacts() {

    }

    @Override
    public Contact findById(Scanner scanner) {
        for (; ; ) {
            System.out.println("Enter id of contact (1-" + ContactDaoImpl.getNumberOfContacts() + ");");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                if (id > 0 && id <= ContactDaoImpl.getNumberOfContacts()) {
                    return contactDao.findById(id - 1);
                }
            } else {
                scanner.next();
                System.out.println("You enter wrong number");
            }
        }
    }

}

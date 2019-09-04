package service.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import dao.impl.ContactDaoImpl;
import entity.Contact;
import exceptions.MyAddressBookException;
import service.ContactService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ContactServiceImpl implements ContactService {

    private static final ContactDao contactDao = new ContactDaoImpl();


    public ContactServiceImpl(ContactDaoImpl contactDao) {

    }

    @Override
    public Contact createContact(BufferedReader bufReader) {
        Contact contact = new Contact();
        System.out.println("\nEnter name of new contact:");
        contact.setName(scanner.next());
        System.out.println("Enter last name of new contact:");
        contact.setLastName(scanner.next());
        System.out.println("Enter age of new contact");
        while (!scanner.hasNextInt()) {
            System.out.println("Enter age in numbers!");
            scanner.next();
        }
        contact.setAge(scanner.nextInt());
        System.out.println("Enter phone number of new contact");
        contact.setPhoneNumber(scanner.next());
        System.out.println("Is contact married(y/n)?");
        contact.setMarried(scanner.next().equalsIgnoreCase("y"));
        contact.setCreateDate(LocalDateTime.now());
        contact.setUpdateDate(LocalDateTime.now());
        return contactDao.createContact(contact);
    }

    @Override
    public Contact updateContact(BufferedReader bufReader) throws MyAddressBookException {
        Contact contact = new Contact();
        int id;
        while (true) {
            System.out.println("Enter contact's ID for update:");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                if (contactDao.copyContact(contactDao.findById(id), contact)) {
                    System.out.println(contact);
                    break;
                } else {
                    System.out.println("Update failed");
                    return contact;
                }
            } else {
                System.out.println("You entered wrong ID number.");
                scanner.next();
            }
        }
        boolean exit = true;
        do {
            System.out.println("Enter number of field for update:");
            System.out.println("1 - Name");
            System.out.println("2 - Last name");
            System.out.println("3 - Age");
            System.out.println("4 - Phone number");
            System.out.println("5 - Married status");
            System.out.println("0 - Save contact and exit to previous menu");
            try {
                if (scanner.hasNextInt()) {
                    int numberOfField = scanner.nextInt();
                    switch (numberOfField) {
                        case Constants.SELECT_NAME_CONTACT: {
                            System.out.println("Enter new name:");
                            contact.setName(scanner.next());
                            contact.setUpdateDate(LocalDateTime.now());
                            break;
                        }
                        case Constants.SELECT_LAST_NAME_CONTACT: {
                            System.out.println("Enter new last name:");
                            contact.setLastName(scanner.next());
                            contact.setUpdateDate(LocalDateTime.now());
                            break;
                        }
                        case Constants.SELECT_AGE_CONTACT: {
                            System.out.println("Enter new age:");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Enter age in numbers!");
                                scanner.next();
                            }
                            contact.setAge(scanner.nextInt());
                            contact.setUpdateDate(LocalDateTime.now());
                            break;
                        }
                        case Constants.SELECT_PHONE_CONTACT: {
                            System.out.println("Enter new number phone:");
                            contact.setPhoneNumber(scanner.next());
                            contact.setUpdateDate(LocalDateTime.now());
                            break;
                        }
                        case Constants.SELECT_STATUS_CONTACT: {
                            System.out.println("Is contact married(y/n)?");
                            contact.setMarried(scanner.next().equalsIgnoreCase("y"));
                            contact.setUpdateDate(LocalDateTime.now());
                            break;
                        }
                        case Constants.EXIT: {
//                            System.out.println(contactDao.updateContact(contact)); Почему так не срабатывает???
                            System.out.println(contact);
                            contactDao.updateContact(contact);
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
        return contact;
    }

    @Override
    public boolean removeContact(BufferedReader bufReader) {
        while (true) {
            System.out.println("Enter ID for delete:");
            try {
                int id = Integer.parseInt(bufReader.readLine().trim());
                if (contactDao.removeContact(id, bufReader)) {
                    return true;
                } else {
                    System.out.println("You entered wrong ID number.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("You enter wrong ID.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showAllContacts(Scanner scanner) {
        subMenuShowAllContact();
        while (true) {
            System.out.println("Enter number of field to sort\nor press 0 for exit to previous menu:");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                contactDao.showAllContacts(number);
                if (number == 0) {
                    return;
                }
            } else {
                System.out.println("You entered wrong number.");
                scanner.next();
            }
        }
    }

    @Override
    public Contact findById(Scanner scanner) throws MyAddressBookException {
        while (true) {
            System.out.println("Enter ID of contact:");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                Contact contact = contactDao.findById(id);
                System.out.println(contact);
                return contact;
            } else {
                System.out.println("You entered wrong ID number.");
                scanner.next();
            }
        }
    }

    @Override
    public Contact findByName(Scanner scanner) throws MyAddressBookException {
        System.out.println("Enter the name of contact:");
        String name = scanner.next();
        Contact contact = contactDao.findByName(name);
        System.out.println(contact);
        return contact;
    }

    @Override
    public void test() {
        Contact contact1 = new Contact("Tim", "Timov",
                21, "1234", true,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact2 = new Contact("Dim", "Dimov",
                21, "5678", false,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact3 = new Contact("Jon", "Jonov",
                21, "9876", true,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact4 = new Contact("Alex", "Alexov",
                21, "5432", false,
                LocalDateTime.now(), LocalDateTime.now());
        contactDao.createContact(contact1);
        contactDao.createContact(contact2);
        contactDao.createContact(contact3);
        contactDao.createContact(contact4);
    }

    static void subMenuShowAllContact() {
        System.out.println("Show all contacts sorted by:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Last name");
        System.out.println("4. Age");
        System.out.println("5. Phone number");
        System.out.println("6. Martial status");
        System.out.println("7. Data of create");
        System.out.println("8. Data of update");
    }

}

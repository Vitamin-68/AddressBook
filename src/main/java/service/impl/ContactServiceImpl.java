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
        System.out.println("\nEnter name of new contact:");
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
        contact.setUpdateDate(LocalDateTime.now());
        return contactDao.createContact(contact);
    }

    @Override
    public Contact updateContact(Scanner scanner) throws MyAddressBookException {
        Contact contact = new Contact();
        while (true) {
            System.out.println("Enter ID for update:");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                if (contactDao.cloneContact(contactDao.findById(id), contact)) {
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

        boolean exit = true, flagEditContact = false;
            do {
                System.out.println("Enter number of field for update (2-6)\nor 0 for Exit:");
                try {
                    if (scanner.hasNextInt()) {
                        int numberOfField = scanner.nextInt();
                        switch (numberOfField) {
                            case Constants.SELECT_NAME_CONTACT: {
                                System.out.println("Enter new name:");
                                contact.setName(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case Constants.SELECT_LAST_NAME_CONTACT: {
                                System.out.println("Enter new last name:");
                                contact.setLastName(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case Constants.SELECT_AGE_CONTACT: {
                                System.out.println("Enter new age:");
                                while (!scanner.hasNextInt()) {
                                    System.out.println("Indicate age in numbers!");
                                    scanner.next();
                                }
                                contact.setAge(scanner.nextInt());
                                flagEditContact = true;
                                break;
                            }
                            case Constants.SELECT_PHONE_CONTACT: {
                                System.out.println("Enter new number phone:");
                                contact.setPhoneNumber(scanner.next());
                                flagEditContact = true;
                                break;
                            }
                            case Constants.SELECT_STATUS_CONTACT: {
                                System.out.println("Is contact married(y/n)?");
                                contact.setMarried(scanner.next().equalsIgnoreCase("y"));
                                flagEditContact = true;
                                break;
                            }
                            case Constants.EXIT: {
                                if (flagEditContact) {
                                    contactDao.updateContact(contact);
                                    System.out.println("Update is done.\n\n");
                                } else {
                                    System.out.println("Nothing selected fo update.\n");
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
    public void showAllContacts(Scanner scanner) {
        subMenuShowAllContact();
        while (true) {
            System.out.println("Enter number of field to sort\nor press 0 for exit:");
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
                Contact contact =  contactDao.findById(id);
                contactDao.showOneContact(contact);
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
        contactDao.showOneContact(contact);
        return contact;
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

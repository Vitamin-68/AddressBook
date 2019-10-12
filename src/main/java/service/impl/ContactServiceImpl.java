package service.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.impl.ContactDaoImpl;
import entity.Contact;
import exceptions.MyAddressBookException;
import service.ContactService;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class ContactServiceImpl implements ContactService {

    private static final ContactDaoImpl contactDao = new ContactDaoImpl();

    public ContactServiceImpl(ContactDaoImpl contactDao) {
    }


    @Override
    public Contact createContact(BufferedReader bufReader) throws IOException, MyAddressBookException {
        Contact contact = new Contact();
        System.out.println("\nEnter name of new contact:");
        contact.setName(bufReader.readLine().trim());
        System.out.println("Enter last name of new contact:");
        contact.setLastName(bufReader.readLine().trim());
        enterAgeInNumbers("Enter age of new contact:", contact, bufReader);
        enterPhoneInNumbers("Enter phone number of new contact:", contact, bufReader);
        setMarriedStatus(contact, bufReader);
        contact.setCreateDate(LocalDateTime.now());
        contact.setUpdateDate(LocalDateTime.now());
        return contactDao.createContact(contact);
    }

    @Override
    public Contact updateContact(BufferedReader bufReader) throws IOException {
        Contact contact;
        int id;
        while (true) {
            System.out.println("Enter contact's ID for update:");
            try {
                id = Integer.parseInt(bufReader.readLine().trim());
                contact = contactDao.findById(id);
                if (contact.getId() > 0) {
                    System.out.println(contact);
                    break;
                } else {
                    System.out.println("Update failed");
                    return contact;
                }
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.\n");
            }
        }
        boolean exit = true;
        do {
            System.out.println("Enter number of field for update\nor 0 for Exit:");
            System.out.println("1 - Name");
            System.out.println("2 - Last name");
            System.out.println("3 - Age");
            System.out.println("4 - Phone number");
            System.out.println("5 - Married status");
            System.out.println("0 - Save contact and exit to previous menu");
            try {
                int numberOfField = Integer.parseInt(bufReader.readLine().trim());
                switch (numberOfField) {
                    case Constants.SELECT_NAME_CONTACT: {
                        System.out.println("Enter new name:");
                        contact.setName(bufReader.readLine().trim());
                        contact.setUpdateDate(LocalDateTime.now());
                        break;
                    }
                    case Constants.SELECT_LAST_NAME_CONTACT: {
                        System.out.println("Enter new last name:");
                        contact.setLastName(bufReader.readLine().trim());
                        contact.setUpdateDate(LocalDateTime.now());
                        break;
                    }
                    case Constants.SELECT_AGE_CONTACT: {
                        enterAgeInNumbers("Enter new age:", contact, bufReader);
                        contact.setUpdateDate(LocalDateTime.now());
                        break;
                    }
                    case Constants.SELECT_PHONE_CONTACT: {
                        enterPhoneInNumbers("Enter new number phone:", contact, bufReader);
                        contact.setUpdateDate(LocalDateTime.now());
                        break;
                    }
                    case Constants.SELECT_STATUS_CONTACT: {
                        setMarriedStatus(contact, bufReader);
                        contact.setUpdateDate(LocalDateTime.now());
                        break;
                    }
                    case Constants.EXIT: {
                        System.out.println(contact);
                        contactDao.updateContact(contact);
                        exit = false;
                        break;
                    }
                    default: {
                        throw new MyAddressBookException(ResponseCode.WRONG_DATA_TYPE,
                                "You enter wrong number of operation");
                    }

                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.\n");
            } catch (MyAddressBookException e) {
                System.out.println(e.getMessage());
            }
        } while (exit);
        return contact;
    }

    @Override
    public void removeContact(BufferedReader bufReader) throws MyAddressBookException {
        while (true) {
            System.out.println("Enter ID for delete:");
            try {
                int id = Integer.parseInt(bufReader.readLine().trim());
                contactDao.removeContact(id, bufReader);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showAllContacts(BufferedReader bufReader) {
        subMenuShowAllContact();
        while (true) {
            System.out.println("Enter number of field to sort\nor press 0 for exit to previous menu:");
            int sortFieldNumber;
            try {
                sortFieldNumber = Integer.parseInt(bufReader.readLine().trim());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Please enter ONLY numbers.\n");
                continue;
            }
            if (sortFieldNumber == 0) {
                return;
            } else {
                contactDao.showAllContacts(sortFieldNumber);
            }
        }
    }

    @Override
    public void findById(BufferedReader bufReader) throws IOException, MyAddressBookException {
        int id;
        while (true) {
            System.out.println("Enter ID of contact:");
            try {
                id = Integer.parseInt(bufReader.readLine().trim());
                Contact contact = contactDao.findById(id);
                System.out.println(contact);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter ONLY numbers.\n");
            }
        }
    }

    @Override
    public void findByName(BufferedReader bufReader) throws MyAddressBookException, IOException {
        System.out.println("Enter the name (or part of name) of contact:");
        String name = bufReader.readLine().trim();
        contactDao.findByName(name);
    }

    @Override
    public void test() throws MyAddressBookException {
        Contact contact1 = new Contact("Tim", "Timov",
                21, "1234", true,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact2 = new Contact("Dim", "Dimov",
                38, "380671234567", false,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact3 = new Contact("Jon", "Jonov",
                150, "380671234568", true,
                LocalDateTime.now(), LocalDateTime.now());
        Contact contact4 = new Contact("Alex", "Alexov",
                5, "380671234569", false,
                LocalDateTime.now(), LocalDateTime.now());
        contactDao.createContact(contact1);
        contactDao.createContact(contact2);
        contactDao.createContact(contact3);
        contactDao.createContact(contact4);
    }

    private void subMenuShowAllContact() {
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

    private void enterAgeInNumbers(String string, Contact contact, BufferedReader bufReader) {
        while (true) {
            System.out.println(string);
            try {
                contact.setAge(Integer.parseInt(bufReader.readLine().trim()));
                break;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Please enter ONLY numbers.\n");
            }
        }
    }

    private void enterPhoneInNumbers(String string, Contact contact, BufferedReader bufReader) {
        while (true) {
            System.out.println(string);
            try {
                String strNumberPhone = bufReader.readLine().trim();
                if (strNumberPhone.matches(Constants.REGEX_ONLY_NUMBERS)) {
                    contact.setPhoneNumber(strNumberPhone);
                    break;
                } else {
                    System.out.println("Please enter ONLY numbers.\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setMarriedStatus(Contact contact, BufferedReader bufReader) throws IOException {
        while (true) {
            System.out.println("Is contact married(y/n)?");
            String str = bufReader.readLine();
            if (str.trim().equalsIgnoreCase("y")) {
                contact.setMarried(true);
                break;
            } else if (str.trim().equalsIgnoreCase("n")) {
                contact.setMarried(false);
                break;
            } else {
                System.out.println("Only 'y' or 'n' please.");
            }
        }
    }

}

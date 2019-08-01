package service.impl;

import dao.ContactDao;
import dao.impl.ContactDaoImpl;
import entity.Contact;
import service.ContactService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ContactServiceImpl implements ContactService {

    private static final ContactDao contactDao = new ContactDaoImpl();
    private static Scanner scanner = new Scanner(System.in);

    public ContactServiceImpl(ContactDaoImpl contactDao) {

    }

    @Override
    public Contact createContact() {
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
    public Contact findById(int id) {
        return contactDao.findById(id);
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        menuUpdate();
//        try {
//
//        }

        return null;
    }

    @Override
    public boolean removeContact(int id) {
        return false;
    }

    @Override
    public void showAllContacts() {

    }
    public void menuUpdate() {
//        int numberOfMenu = 1;
        System.out.println("1. Name \n2. Last name \n3. Age \n4.Phone number\n5.Is married\n");
        System.out.println("Enter number of field for update: ");
    }
}

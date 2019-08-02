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
        menuUpdate();
//        try {
//
//        }

        return null;
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
        System.out.println("Enter id of contact (0-9)");// как тут можно получить длину нашего массива contacts.length?

            while (scanner.hasNextInt() != true) {
                int id = scanner.nextInt();
                if (id>0 && id <=10) {
                    return contactDao.findById(id);
                }
                id = scanner.nextInt();
            }

    }

    public void menuUpdate() {
//        int numberOfMenu = 1;
        System.out.println("1. Name \n2. Last name \n3. Age \n4.Phone number\n5.Is married\n");
        System.out.println("Enter number of field for update: ");
    }
}

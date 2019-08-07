package dao.impl;

import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;

import java.util.ArrayList;
import java.util.Objects;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;
    private static int numberOfContacts = 10;

    private static Contact[] contacts = new Contact[numberOfContacts];
    private static ArrayList<Contact> contactArrayList = new ArrayList<>();

    public static int getNumberOfContacts() {
        return numberOfContacts;
    }

    public static void setNumberOfContacts(int numberOfContacts) {
        ContactDaoImpl.numberOfContacts = numberOfContacts;
    }

    @Override
    public Contact createContact(Contact newContact) {
        if (isStoreHasEmptyCells()) {
            int index = 0;
            for (Contact contact : contacts) {
                if (Objects.isNull(contact)) {
                    newContact.setId(++generator);
                    contact = newContact;
                    contacts[index] = contact;
                    return contact;
                }
                index++;
            }
        } else {
            System.out.println("We haven`t empty cell in our DB.");
            return new Contact();
        }
        return new Contact();
    }

    @Override
    public Contact findById(int id) {
        for (Contact contact : contacts) {
            if (contact != null && Objects.equals(contact.getId(), id)) {
                System.out.println("1. ID: " + contact.getId());
                System.out.println("2. Name: " + contact.getName());
                System.out.println("3. Last name: " + contact.getLastName());
                System.out.println("4. Age: " + contact.getAge());
                System.out.println("5. Phone number: " + contact.getPhoneNumber());
                System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
                System.out.println("7. Data of create: " + contact.getCreateDate());
                System.out.println("8. Data of update: " + contact.getUpdateTime()); //название метода некорректное будет и дата и время
                return contact;
            }
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return new Contact();
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        int index = 0;
        for (Contact contact : contacts) {
            if (Objects.equals(contact.getId(), updatedContact.getId())) {
                contact = updatedContact;
                contacts[index] = contact;
                return contact;
            }
            index++;
        }
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id) {
        int index = 0;
        for (Contact contact : contacts) {
            if (contact != null && Objects.equals(contact.getId(), id)) {
                contacts[index] = null;
                System.out.println("Contact with ID = " + id + " was deleted successfully");
                return true;
            }
            index++;
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return false;
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    private boolean isStoreHasEmptyCells() {
        for (Contact contact : contacts) {
            if (Objects.isNull(contact)) {
                return true;
            }
        }
        return false;
    }

}

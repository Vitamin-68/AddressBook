package dao.impl;

import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;

import java.util.Objects;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;
    private static int numberOfContacts = 10;

    private static Contact[] contacts = new Contact[numberOfContacts];

    public static int getNumberOfContacts() {
        return numberOfContacts;
    }

    public static void setNumberOfContacts(int numberOfContacts) {
        ContactDaoImpl.numberOfContacts = numberOfContacts;
    }

    @Override
    public Contact createContact(Contact newContact) {
        if (isStoreHasEmptyCells()) {
            for (Contact contact : contacts) {
                if (Objects.isNull(contact)) {
                    newContact.setId(++generator);
                    contact = newContact;
                    return contact;
                }
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
            if (Objects.equals(contact.getId(), id)) {
                System.out.println("1. ID: " + contact.getId());
                System.out.println("2. Name: " + contact.getName());
                System.out.println("3. Last name: " + contact.getLastName());
                System.out.println("4. Age: " + contact.getAge());
                System.out.println("5. Phone number: " + contact.getPhoneNumber());
                System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
                System.out.println("7. Data of create: " + contact.getCreateDate());
                System.out.println("8. Data of update: " + contact.getUpdateTime()); // так дата или время?
                return contact;
            } else {
                System.out.println("Contact with id " + id + "don't exist");
            }
        }
        return new Contact();
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        for (Contact contact : contacts) {
            if (Objects.equals(contact.getId(), updatedContact.getId())) {
                contact = updatedContact;
                return contact;
            }
        }
        return updatedContact; // зачем возвращать updatedContact если не смогли редактировать?
    }

    @Override
    public boolean removeContact(int id) {
        for (Contact contact : contacts) {
            if (Objects.equals(contact.getId(), id)) {
                contacts[id] = null;
                return true;
            }
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

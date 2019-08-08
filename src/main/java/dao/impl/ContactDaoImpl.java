package dao.impl;

import dao.ContactDao;
import entity.Contact;

import java.util.ArrayList;
import java.util.Objects;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static ArrayList<Contact> contactArrayList = new ArrayList<>();

    @Override
    public Contact createContact(Contact newContact) {
        newContact.setId(++generator);
        contactArrayList.add(newContact);
        return newContact;
    }

    @Override
    public Contact findById(int id) {
        for (Contact contact : contactArrayList) {
            if (Objects.equals(contact.getId(), id)) {
                showOneContact(contact);
                return contact;
            }
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return new Contact();
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        for (Contact contact : contactArrayList) {
            if (Objects.equals(contact.getId(), updatedContact.getId())) {
                contact = updatedContact;
                return contact;
            }
        }
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id) {
        for (Contact contact : contactArrayList) {
            if (contact != null && Objects.equals(contact.getId(), id)) {
                System.out.println("Contact with ID = " + id + " was deleted successfully");
                return true;
            }
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return false;
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contactArrayList) {
            System.out.println(contact);
        }
    }

    private void showOneContact(Contact contact) {
        System.out.println("1. ID: " + contact.getId());
        System.out.println("2. Name: " + contact.getName());
        System.out.println("3. Last name: " + contact.getLastName());
        System.out.println("4. Age: " + contact.getAge());
        System.out.println("5. Phone number: " + contact.getPhoneNumber());
        System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
        System.out.println("7. Data of create: " + contact.getCreateDate());
        System.out.println("8. Data of update: " + contact.getUpdateTime());
        return;
    }

}

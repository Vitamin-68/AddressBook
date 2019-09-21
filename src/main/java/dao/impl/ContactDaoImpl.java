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
            int index = 0;
            for (Contact contact : contacts) {
                if (Objects.isNull(contact)) {
                    newContact.setId(++generator);
                    contact = newContact;
                    contacts[index] = contact;
                    System.out.println("Contact created:");
                    System.out.println(contact);
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
                System.out.println(contact);
                return contact;
            }
        }
        System.out.println("Contact with ID = " + id + " not found.\n");
        return new Contact();
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i] != null && Objects.equals(contacts[i].getId(), updatedContact.getId())) {
                contacts[i].setName(updatedContact.getName());
                contacts[i].setLastName(updatedContact.getLastName());
                contacts[i].setAge(updatedContact.getAge());
                contacts[i].setPhoneNumber(updatedContact.getPhoneNumber());
                contacts[i].setMarried(updatedContact.isMarried());
                contacts[i].setUpdateTime(updatedContact.getUpdateTime());
                contacts[i].setCreateDate(updatedContact.getCreateDate());
                System.out.println("Update is done.\n");
                break;
            }
        }
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id) {
        int index = 0;
        for (Contact contact : contacts) {
            if (contact != null && Objects.equals(contact.getId(), id)) {
                contacts[index] = null;
                System.out.println("Contact with ID = " + id + " was deleted successfully.\n");
                return true;
            }
            index++;
        }
        System.out.println("Contact with ID = " + id + " not found.\n");
        return false;
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contacts) {
            if (!Objects.isNull(contact)) {
                System.out.println(contact);
            }
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

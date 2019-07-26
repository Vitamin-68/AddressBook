package dao.impl;

import dao.ContactDao;
import entity.Contact;

import java.util.Objects;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static Contact[] contacts = new Contact[10];


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
                return contact;
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
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id) {
        return false;
    }

    @Override
    public void showAllContacts() {

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

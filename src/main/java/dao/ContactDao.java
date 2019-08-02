package dao;

import entity.Contact;

import java.sql.Connection;

public interface ContactDao {

    Contact createContact(Contact newContact);

    Contact updateContact(Contact updatedContact);

    boolean removeContact(int id);

    void showAllContacts();

    Contact findById(int id);
}

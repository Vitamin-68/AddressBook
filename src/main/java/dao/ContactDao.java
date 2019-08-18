package dao;

import entity.Contact;

import java.util.Scanner;

public interface ContactDao {

    Contact createContact(Contact newContact);

    Contact updateContact(Contact updatedContact, int index);

    boolean removeContact(int id, Scanner scanner);

    void showAllContacts();

    Contact findById(int id);

    void cloneContact(Contact contact, int index);
}

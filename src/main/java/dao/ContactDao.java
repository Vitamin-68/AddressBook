package dao;

import entity.Contact;

import java.util.Scanner;

public interface ContactDao {

    Contact createContact(Contact newContact);

    Contact updateContact(Contact updatedContact);

    boolean removeContact(int id, Scanner scanner);

    void showAllContacts();

    int findById(int id);

    void saveUpdatedField(Contact contact, int field, int index);
}

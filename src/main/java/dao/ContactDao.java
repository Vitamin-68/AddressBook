package dao;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;

public interface ContactDao {

    Contact createContact(Contact newContact);

    Contact updateContact(Contact updatedContact);

    boolean removeContact(int id, BufferedReader bufferedReader);

    void showAllContacts(int number);

    Contact findById(int id) throws MyAddressBookException;

    Contact findByName(String name) throws MyAddressBookException;

    void showOneContact(Contact contact);

    boolean copyContact(Contact contactCarrier, Contact contactTarget);
}

package dao;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.util.Scanner;

public interface ContactDao {

    Contact createContact(Contact newContact);

    Contact updateContact(Contact updatedContact);

    boolean removeContact(int id, Scanner scanner);

    void showAllContacts(int number);

    Contact findById(int id) throws MyAddressBookException;

    boolean cloneContact(Contact contactCarrier, Contact contactTarget);
}

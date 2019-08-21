package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.util.Scanner;

public interface ContactService {

    Contact createContact(Scanner scanner);

    Contact updateContact(Scanner scanner) throws MyAddressBookException;

    boolean removeContact(Scanner scanner);

    void showAllContacts();

    Contact findById(Scanner scanner) throws MyAddressBookException;

    Contact findByName(Scanner scanner);
}

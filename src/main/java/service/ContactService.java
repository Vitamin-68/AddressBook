package service;

import entity.Contact;

import java.util.Scanner;

public interface ContactService {

    Contact createContact(Scanner scanner);

    Contact updateContact(Scanner scanner);

    boolean removeContact(Scanner scanner);

    void showAllContacts();

    Contact findById(Scanner scanner);

}

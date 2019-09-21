package service;

import entity.Contact;

import java.util.Scanner;

public interface ContactService {

    /**
     * Create new contact and pass one to contactDao for save it to DB
     *
     * @param scanner usual Java Scanner
     * @return new Contact
     */
    Contact createContact(Scanner scanner);

    /**
     * Seek and update contact from DB
     * and pass one to contactDao for save it to DB
     *
     * @param scanner usual Java Scanner
     * @return
     */
    Contact updateContact(Scanner scanner);

    /**
     * Read contact's ID from console
     * and pass it to contactDao for delete
     *
     * @param scanner usual Java Scanner
     * @return true if delete successfully
     */
    boolean removeContact(Scanner scanner);

    /**
     * call method from contactDao for show all Contacts
     */
    void showAllContacts();

    /**
     * Read contact's ID from console
     * and pass it to contactDao for seek
     *
     * @param scanner usual Java Scanner
     * @return found contact from the ArrayList
     * or new Contact if not found
     */
    int findById(Scanner scanner);
}

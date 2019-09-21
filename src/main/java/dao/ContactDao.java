package dao;

import entity.Contact;

import java.util.Scanner;

public interface ContactDao {

    /**
     * Create new contact/
     *
     * @param newContact - a new contact
     * @return created contact/
     */
    Contact createContact(Contact newContact);

    /**
     * Fields of contact from ArrayList with input index updates from
     * input updatedContact
     *
     * @param updatedContact - contact for update to DB of contacts
     * @param index          - index of updated contact from DB
     * @return updated contact
     */
    Contact updateContact(Contact updatedContact, int index);

    /**
     * seek $ delete contact use his ID
     *
     * @param id - ID of contact fo delete
     * @return true if delete successfull
     * and false if contact not found
     */
    boolean removeContact(int id, Scanner scanner);

    /**
     * Output to screen all contacts from Array
     * and ignore empty cell
     */
    void showAllContacts();

    /**
     * Seek contact in the Array with his ID
     *
     * @param id - contact's ID for seek in Array
     * @return index of contact if found
     * or -1 if not found
     */
    int findById(int id);

    /**
     * Set field input contact from contact of ArrayList use input index
     *
     * @param contact - contact for update
     * @param index   - index returned from findById()
     */
    void cloneContact(Contact contact, int index);
}

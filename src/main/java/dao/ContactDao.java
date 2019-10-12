package dao;

import entity.Contact;

import java.io.BufferedReader;

import exceptions.MyAddressBookException;

public interface ContactDao {

    /**
     * Create new contact/
     *
     * @param newContact - a new contact
     * @return created contact/
     */
    Contact createContact(Contact newContact) throws MyAddressBookException;

    /**
     * Edit & update contacts from DB
     *
     * @param contact contact for update to DB of contacts
     * @return contact
     */
    Contact updateContact(Contact contact) throws MyAddressBookException;

    /**
     * seek $ delete contact use his ID
     *
     * @param id        - ID of contact fo delete
     * @param bufReader - a BufferedReader
     * @return true if delete successfully
     */
    boolean removeContact(int id, BufferedReader bufReader) throws MyAddressBookException;

    /**
     * Sort & output to screen all contacts
     *
     * @param sortFieldNumber number field DB for sorted output to screen
     */
    void showAllContacts(int sortFieldNumber);

    /**
     * Search in DB and display on screen contact whose ID matches the 'id' parameter
     *
     * @param id - contact's ID for seek in DB
     * @return found contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    Contact findById(int id) throws MyAddressBookException;

    /**
     * Search in DB and display on screen all contacts whose name likes the 'name' parameter
     *
     * @param name - contact's name for seek in DB
     * @throws MyAddressBookException if contact not found
     */
    void findByName(String name) throws MyAddressBookException;

}

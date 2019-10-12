package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.io.IOException;

public interface ContactService {

    /**
     * Create new contact.
     *
     * @param bufferedReader a BufferedReader
     * @return new contact
     * @throws IOException
     * @throws MyAddressBookException if unable save to DB
     */
    Contact createContact(BufferedReader bufferedReader) throws IOException, MyAddressBookException;

    /**
     * Seek, edit and update contact using his ID
     *
     * @param bufferedReader a BufferedReader
     * @return updated contact
     * @throws IOException
     */
    Contact updateContact(BufferedReader bufferedReader) throws IOException;

    /**
     * Seek and delete contact using his ID
     *
     * @param bufferedReader a BufferedReader
     * @throws MyAddressBookException if delete contact failed
     */
    void removeContact(BufferedReader bufferedReader) throws MyAddressBookException;

    /**
     * Sort & output to screen all contacts from DB
     *
     * @param bufferedReader a BufferedReader
     */
    void showAllContacts(BufferedReader bufferedReader);

    /**
     * Search in DB and display on screen contact whose ID matches the 'id' parameter
     *
     * @param bufferedReader a BufferedReader
     * @throws MyAddressBookException
     * @throws IOException
     */
    void findById(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    /**
     * Search in DB and display on screen all contacts whose name likes the 'name' parameter
     *
     * @param bufferedReader a BufferedReader
     * @throws MyAddressBookException
     * @throws IOException
     */
    void findByName(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    /**
     * Add 4 contacts to DB with all completed field
     * for test other menu items
     */
    void test() throws MyAddressBookException;

}

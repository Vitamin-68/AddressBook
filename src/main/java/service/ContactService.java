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
     */
    Contact createContact(BufferedReader bufferedReader) throws IOException;

    /**
     * Seek, edit and update contact using his ID
     *
     * @param bufferedReader a BufferedReader
     * @return updated contact
     * @throws IOException
     */
    Contact updateContact(BufferedReader bufferedReader) throws IOException;

    /**
     * seek $ delete contact using his ID
     *
     * @param bufferedReader a BufferedReader
     * @return true if deleted successfully
     * @see dao.ContactDao
     */
    boolean removeContact(BufferedReader bufferedReader);

    /**
     * Sort & output to screen all contacts
     *
     * @param bufferedReader a BufferedReader
     */
    void showAllContacts(BufferedReader bufferedReader);

    /**
     * Seek contact in the TreeSet with his ID
     *
     * @param bufferedReader a BufferedReader
     * @return founded contact
     */
    Contact findById(BufferedReader bufferedReader);

    /**
     * Seek contacts in the DB with his name and out its to screen
     *
     * @param bufferedReader a BufferedReader
     * @throws MyAddressBookException
     * @throws IOException
     */
    void findByName(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    /**
     * Save all contacts to text file readable in text
     *
     * @throws IOException
     */
    void saveAllContactsToTxtFile() throws IOException;

    /**
     * Load from text file and set all contact to TreeSet contactTreeSet
     *
     * @param bufReader a BufferedReader
     */
    void loadAllContactsFromTxtFile(BufferedReader bufReader);

    /**
     * Save all contacts to *.dat file unreadable in text
     *
     * @throws IOException
     */
    void saveAllContactsToDatFile() throws IOException;

    /**
     * Load from *.dat file and set all contact to TreeSet contactTreeSet
     */
    void loadAllContactsFromDatFile();

    /**
     * Add to TreeSet 4 contacts with all completed field
     * for test other menu items
     */
    void test();

}

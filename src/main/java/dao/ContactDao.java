package dao;

import entity.Contact;
import java.io.BufferedReader;
import exceptions.MyAddressBookException;

public interface ContactDao {

    /**
     * Create new contact/
     * @param newContact - a new contact
     * @return created contact/
     */
    Contact createContact(Contact newContact) throws MyAddressBookException;

    /**
     * Edit & update contacts from DB
     * @param contact  contact for update to DB of contacts
     * @return contact
     */
    Contact updateContact(Contact contact);

    /**
     * seek $ delete contact use his ID
     * @param id  - ID of contact fo delete
     * @param bufReader - a BufferedReader
     * @return
     */
    boolean removeContact(int id, BufferedReader bufReader) throws MyAddressBookException;

    /**
     * Sort & output to screen all contacts
     */
    void showAllContacts();

    /** Seek contact in the DB with his ID
     * @param id  - contact's ID for seek in DB
     * @return found contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    Contact findById(int id) throws MyAddressBookException;

//    /**
//     * Seek contact in the DB with his name
//     * @param name  - contact's name for seek in DB
//     * @return found contact from the DB
//     * @throws MyAddressBookException if contact not found
//     */
//    Contact findByName(String name) throws MyAddressBookException;

}

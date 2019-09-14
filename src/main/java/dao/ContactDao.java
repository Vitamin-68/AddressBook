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
    Contact createContact(Contact newContact);

    /**
     * Update DB with input contact
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
    boolean removeContact(int id, BufferedReader bufReader);

    /**
     * Sort & output to screen all contacts
     * @param number -  field number for sort
     */
    void showAllContacts(int number);

    /** Seek contact in the DB with his ID
     * @param id  - contact's ID for seek in DB
     * @return found contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    Contact findById(int id) throws MyAddressBookException;

    /**
     * Seec contact in the DB with his name
     * @param name  - contact's name for seek in DB
     * @returnfound contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    Contact findByName(String name) throws MyAddressBookException;

    /**
     * Output to screen all data of one contact
     * @param contact
     */
    void showOneContact(Contact contact);

    /**
     * Copy all fields one contact to other contact
     * @param copyFromContact  input contact
     * @param copyToContact output contact
     * @return copy of contact to super
     */
    boolean copyContact(Contact copyFromContact, Contact copyToContact);

}

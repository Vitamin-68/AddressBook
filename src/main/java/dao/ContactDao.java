package dao;

import entity.Contact;

import java.io.BufferedReader;

import exceptions.MyAddressBookException;

public interface ContactDao {

    /**
     * Add new contact to TreeSet
     *
     * @param newContact - a new contact
     * @return created new contact
     */
    Contact createContact(Contact newContact);

    /**
     * Edit & update contacts from TreeSet
     *
     * @param contact contact for update to DB of contacts
     * @return contact
     */
    Contact updateContact(Contact contact);

    /**
     * seek $ delete contact using his ID
     *
     * @param id        - ID of contact fo delete
     * @param bufReader - a BufferedReader
     * @return true if deleted successfully
     */
    boolean removeContact(int id, BufferedReader bufReader);

    /**
     * Sort & output to screen all contacts
     *
     * @param number -  field's number for sort
     */
    void showAllContacts(int number);

    /**
     * Seek contact in the TreeSet with his ID
     *
     * @param id - contact's ID for seek in DB
     * @return found contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    Contact findById(int id) throws MyAddressBookException;

    /**
     * Seek contacts in the DB with his name and out its to screen
     *
     * @param name - contact's name for seek in DB
     * @throws MyAddressBookException if contact not found
     */
    void findByName(String name) throws MyAddressBookException;

    /**
     * Copy all fields one contact to other contact
     *
     * @param copyFromContact input contact
     * @param copyToContact   output contact
     * @return copy of contact to super
     */
    boolean copyContact(Contact copyFromContact, Contact copyToContact);

}

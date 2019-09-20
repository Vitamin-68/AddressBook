package dao;

import entity.Contact;

import java.sql.Connection;

public interface ContactDao {

    /**
     * Create new contact/
     *
     * @param newContact - a new contact
     * @return created contact/
     */
    Contact createContact(Contact newContact);

    /**
     * Update DB with input contact
     * @param updatedContact  contact for update to DB of contacts
     * @return contact
     */
    Contact updateContact(Contact updatedContact);

    /**
     * seek $ delete contact use his ID
     * @param id  - ID of contact fo delete
     * @return true if delete successfull
     * and false if contact not found
     */
    boolean removeContact(int id);

    /**
     * Output to screen all contacts from Array
     * and ignore empty cell
     */
    void showAllContacts();

    /** Seek contact in the Array with his ID
     * @param id  - contact's ID for seek in Array
     * @return found contact from the Array
     * or new Contact if not found
     */
    Contact findById(int id);
}

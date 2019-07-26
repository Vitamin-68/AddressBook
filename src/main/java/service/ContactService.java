package service;

import entity.Contact;

public interface ContactService {

    Contact createContact();

    Contact findById(int id);

    Contact updateContact(Contact updatedContact);

    boolean removeContact(int id);

    void showAllContacts();

}

package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.io.IOException;

public interface ContactService {

    Contact createContact(BufferedReader bufferedReader) throws IOException;

    Contact updateContact(BufferedReader bufferedReader) throws IOException;

    boolean removeContact(BufferedReader bufferedReader);

    void showAllContacts(BufferedReader bufferedReader);

    Contact findById(BufferedReader bufferedReader) throws MyAddressBookException;

    Contact findByName(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    void saveAllContactsToTxtFile() throws IOException;

    void loadAllContactsFromTxtFile(BufferedReader bufReader);

    void saveAllContactsToDatFile() throws IOException;

    void  loadAllContactsFromDatFile();

    void test();

}

package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.io.IOException;

public interface ContactService {

    Contact createContact(BufferedReader bufferedReader) throws IOException, MyAddressBookException;

    Contact updateContact(BufferedReader bufferedReader) throws IOException;

    boolean removeContact(BufferedReader bufferedReader) throws MyAddressBookException;

    void showAllContacts(BufferedReader bufferedReader);

    Contact findById(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    void findByName(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    void test() throws MyAddressBookException;

}

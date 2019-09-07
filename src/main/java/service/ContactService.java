package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public interface ContactService {

    Contact createContact(BufferedReader bufferedReader) throws IOException;

    Contact updateContact(BufferedReader bufferedReader) throws IOException;

    boolean removeContact(BufferedReader bufferedReader);

    void showAllContacts(BufferedReader bufferedReader);

    Contact findById(BufferedReader bufferedReader) throws MyAddressBookException;

    Contact findByName(BufferedReader bufferedReader) throws MyAddressBookException, IOException;

    void test();
}

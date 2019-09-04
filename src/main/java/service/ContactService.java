package service;

import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.util.Scanner;

public interface ContactService {

    Contact createContact(BufferedReader bufferedReader);

    Contact updateContact(BufferedReader bufferedReader) throws MyAddressBookException;

    boolean removeContact(BufferedReader bufferedReader);

    void showAllContacts(BufferedReader bufferedReader);

    Contact findById(BufferedReader bufferedReader) throws MyAddressBookException;

    Contact findByName(BufferedReader bufferedReader) throws MyAddressBookException;

    void test();
}

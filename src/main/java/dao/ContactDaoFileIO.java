package dao;

import entity.Contact;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

/**
 * Interface for ContactDaoImpl
 * Load & save DB of contact to file
 */
public interface ContactDaoFileIO {

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
     * @return TreeSet contactTreeSet
     * @throws FileNotFoundException
     */
    Set<Contact> loadAllContactsFromTxtFile(BufferedReader bufReader) throws FileNotFoundException;

    /**
     * Save all contacts to *.dat file unreadable in text
     *
     * @throws IOException
     */
    void saveAllContactsToDatFile() throws IOException;

    /**
     * Load from *.dat file and set all contact to TreeSet contactTreeSet
     *
     * @return TreeSet contactTreeSet
     */
    Set<Contact> loadAllContactsFromDatFile();
}

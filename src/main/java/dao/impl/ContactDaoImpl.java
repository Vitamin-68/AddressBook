package dao.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static exceptions.MyAddressBookException.NOT_FOUND_MESSAGE;

/**
 * CRUD contact's data to/from DB.
 * Process request from ContactServiceImpl and return data back.
 * @see dao.ContactDao
 * @author Vitamin-68
 */
public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static Set<Contact> contactTreeSet = new TreeSet<>(Comparator.comparing(Contact::getId));

    /**
     * Create new contact/
     * @param newContact - a new contact
     * @return created contact/
     */
    @Override
    public Contact createContact(Contact newContact) {

        newContact.setId(++generator);
        contactTreeSet.add(newContact);
        System.out.println("New contact added successfully:");
        System.out.println(newContact);
        return newContact;
    }

    /** Seek contact in the DB with his ID
     * @param id  - contact's ID for seek in DB
     * @return found contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    @Override
    public Contact findById(int id) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND,
                        "Contact with ID = " + id + " not exist"));
    }

    /**
     * Seec contact in the DB with his name
     * @param name  - contact's name for seek in DB
     * @returnfound contact from the DB
     * @throws MyAddressBookException if contact not found
     */
    public Contact findByName(String name) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    /**
     * Update DB with input contact
     * @param contact  contact for update to DB of contacts
     * @return contact
     */
    @Override
    public Contact updateContact(Contact contact) {

        // don't working, write on lesson
//        contactTreeSet = contactTreeSet
//                .stream()
////                .filter(updatedContact -> Objects.equals(updatedContact.getId(), contact.getId()))
//                .peek(updatedContact -> {
//                    if (Objects.equals(updatedContact.getId(), contact.getId())) {
//                        copyContact(contact, updatedContact);
//                    }
//                })
//                .collect(Collectors.toCollection(TreeSet::new));

        contactTreeSet = contactTreeSet
                .stream()
                .peek(updatedContact -> {
                    if (Objects.equals(updatedContact.getId(), contact.getId())) {
                            copyContact(contact, updatedContact);
                }
                })
                .collect(Collectors.toSet());
        return contact;
    }

    /**
     * seek $ delete contact use his ID
     * @param id  - ID of contact fo delete
     * @param bufReader - a BufferedReader
     * @return
     */
    @Override
    public boolean removeContact(int id, BufferedReader bufReader) {
        try {
            System.out.println(findById(id));
            System.out.println("Do you want to delete this contact? (y/n):");
            if (bufReader.readLine().equalsIgnoreCase("y")) {
                boolean result = contactTreeSet.removeIf(contact -> Objects.equals(contact.getId(), id));
                if (result) {
                    System.out.println("Contact with ID = " + id + " deleted successfully.");
                } else System.out.println("Delete failed");
                return result;
            } else {
                System.out.println("Delete canceled.");
                return true;
            }
        } catch (MyAddressBookException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Sort & output to screen all contacts
     * @param number -  field number for sort
     */
    @Override
    public void showAllContacts(int number) {
        Comparator<Contact> comparator;
        switch (number) {
            case Constants.SORTED_BY_ID:
                comparator = Comparator.comparing(Contact::getId);
                break;
            case Constants.SORTED_BY_NAME:
                comparator = Comparator.comparing(Contact::getName);
                break;
            case Constants.SORTED_BY_LAST_NAME:
                comparator = Comparator.comparing(Contact::getLastName);
                break;
            case Constants.SORTED_BY_AGE:
                comparator = Comparator.comparing(Contact::getAge);
                break;
            case Constants.SORTED_BY_PHONE:
                comparator = Comparator.comparing(Contact::getPhoneNumber);
                break;
            case Constants.SORTED_BY_STATUS:
                comparator = Comparator.comparing(Contact::isMarried);
                break;
            case Constants.SORTED_BY_DATE_OF_CREATE:
                comparator = Comparator.comparing(Contact::getCreateDate);
                break;
            case Constants.SORTED_BY_DATE_OF_UPDATE:
                comparator = Comparator.comparing(Contact::getUpdateDate);
                break;
            case Constants.EXIT:
                return;
            default:
                System.out.println("Wrong number of field.\nContact list will be sorted by ID:");
                comparator = Comparator.comparing(Contact::getId);
        }
        contactTreeSet.stream()
                .sorted(comparator)
                .collect(Collectors.toList())
                .forEach((System.out::println));
    }


    /**
     * Output to screen all data of one contact
     * @param contact
     */
    public void showOneContact(Contact contact) {
        System.out.println("1. ID: " + contact.getId());
        System.out.println("2. Name: " + contact.getName());
        System.out.println("3. Last name: " + contact.getLastName());
        System.out.println("4. Age: " + contact.getAge());
        System.out.println("5. Phone number: " + contact.getPhoneNumber());
        System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
        System.out.println("7. Data of create: " + contact.getCreateDate());
        System.out.println("8. Data of update: " + contact.getUpdateDate() + "\n");
    }

    /**
     * Copy all fields one contact to other contact
     * @param copyFromContact  input contact
     * @param copyToContact output contact
     * @return copy of contact to super
     */
    @Override
    public boolean copyContact(Contact copyFromContact, Contact copyToContact) {
        if (copyFromContact != null && copyToContact != null && !copyFromContact.equals(copyToContact)) {
            copyToContact.setId(copyFromContact.getId());
            copyToContact.setName(copyFromContact.getName());
            copyToContact.setLastName(copyFromContact.getLastName());
            copyToContact.setAge(copyFromContact.getAge());
            copyToContact.setPhoneNumber(copyFromContact.getPhoneNumber());
            copyToContact.setMarried(copyFromContact.isMarried());
            copyToContact.setCreateDate(copyFromContact.getCreateDate());
            copyToContact.setUpdateDate(copyFromContact.getUpdateDate());
            return true;
        } else
            return false;
    }

//    private void searchSameContact(Contact contact) throws MyAddressBookException {
//        Optional<Contact> sameContactOpt = contactTreeSet.stream()
//                .filter(sameContact -> sameContact
//                        .getPhoneNumber()
//                        .equals(contact.getPhoneNumber()))
//                .findFirst();
//        if (sameContactOpt.isPresent()) {
//            throw new MyAddressBookException(ResponseCode.OBJECT_EXIST,
//                    "Same contact is exist with id" + sameContactOpt.get().getId());
//        }
//    }
//
//    private void searchSameContact2(Contact contact) {
//        contactTreeSet.stream()
//                .filter(sameContact ->
//                        sameContact.getPhoneNumber()
//                                .equals(contact.getPhoneNumber()))
//                .findFirst()
//                .ifPresent(MyAddressBookException::new);
//    }
}

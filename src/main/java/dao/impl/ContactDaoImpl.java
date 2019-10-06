package dao.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import dao.ContactDaoFileIO;
import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * CRUD contact's data to/from DB.
 * Process request from ContactServiceImpl and return data back.
 *
 * @author Vitamin-68
 * @see dao.ContactDao
 */
public class ContactDaoImpl implements ContactDao, ContactDaoFileIO {

    private static int generator = 0;

    private static Set<Contact> contactTreeSet = new TreeSet<>(Comparator.comparing(Contact::getId));


    @Override
    public Contact createContact(Contact newContact) {
        try {
            searchSameContact(newContact);
            newContact.setId(++generator);
            contactTreeSet.add(newContact);
            System.out.println("New contact added successfully:");
            System.out.println(newContact);
        } catch (MyAddressBookException e) {
            System.out.println(e.getMessage());
        }
        return newContact;
    }


    @Override
    public Contact findById(int id) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND,
                        "Contact with ID = " + id + " not exist.\n"));
    }


    public void findByName(String name) throws MyAddressBookException {

        Set<Contact> filteredSet = contactTreeSet
                .stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .collect(Collectors.toSet());
        if (!filteredSet.isEmpty()) {
            for (Contact contact : filteredSet) {
                System.out.println(contact);
            }
        } else {
            throw new MyAddressBookException(ResponseCode.NOT_FOUND,
                    "Contact with Name = '" + name + "' not exist.\n");
        }
    }


    @Override
    public Contact updateContact(Contact contact) {

        contactTreeSet = contactTreeSet
                .stream()
                .peek(updatedContact -> {
                    if (Objects.equals(updatedContact.getId(), contact.getId())) {
                        copyContact(contact, updatedContact);
                    }
                })
                .collect(Collectors.toCollection(TreeSet::new));
        return contact;
    }


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

    @Override
    public void saveAllContactsToTxtFile() throws IOException {
        FileWriter writer = new FileWriter(new File(Constants.TXT_LIST_PATH));
        for (Contact contact : contactTreeSet) {
            writer.write(Constants.ID + contact.getId() + Constants.WORD_SEPARATOR +
                    Constants.NAME + contact.getName() + Constants.WORD_SEPARATOR +
                    Constants.LAST_NAME + contact.getLastName() + Constants.WORD_SEPARATOR +
                    Constants.AGE + contact.getAge() + Constants.WORD_SEPARATOR +
                    Constants.MARRIED + contact.isMarried() + Constants.WORD_SEPARATOR +
                    Constants.PHONE_NUMBER + contact.getPhoneNumber() + Constants.WORD_SEPARATOR +
                    Constants.CREATE_DATE + contact.getCreateDate() + Constants.WORD_SEPARATOR +
                    Constants.UPDATE_DATE + contact.getUpdateDate() + Constants.WORD_SEPARATOR +
                    Constants.LINE_SEPARATOR);
        }
        writer.close();
        System.out.println(contactTreeSet.size() + " contacts saved to \"" + Constants.TXT_LIST_PATH + "\".");
    }

    @Override
    public Set<Contact> loadAllContactsFromTxtFile(BufferedReader bufReader) {
        try {
            bufReader = new BufferedReader(new FileReader(Constants.TXT_LIST_PATH));

            bufReader.lines().forEach((String str) -> {
                Contact contact = new Contact();
                String[] parameters = str.split(Constants.WORD_SEPARATOR);
                for (String parameter : parameters) {
                    if (parameter.contains(Constants.ID)) {
                        contact.setId(Integer.parseInt(parameter.split(":")[1].trim()));
                    } else if (parameter.contains(Constants.LAST_NAME)) {
                        contact.setLastName(parameter.split(":")[1].trim());
                    } else if (parameter.contains(Constants.NAME)) {
                        contact.setName(parameter.split(":")[1].trim());
                    } else if (parameter.contains(Constants.AGE)) {
                        contact.setAge(Integer.parseInt(parameter.split(":")[1].trim()));
                    } else if (parameter.contains(Constants.MARRIED)) {
                        contact.setMarried(Boolean.parseBoolean(parameter.split(":")[1].trim()));
                    } else if (parameter.contains(Constants.PHONE_NUMBER)) {
                        contact.setPhoneNumber(Integer.parseInt(parameter.split(":")[1].trim()));
                    } else if (parameter.contains(Constants.CREATE_DATE)) {
                        contact.setCreateDate(LocalDateTime.parse((parameter.split(":")[1] +
                                ":" + parameter.split(":")[2] + ":" + parameter.split(":")[3]).trim()));
                    } else if (parameter.contains(Constants.UPDATE_DATE)) {
                        contact.setUpdateDate(LocalDateTime.parse((parameter.split(":")[1] +
                                ":" + parameter.split(":")[2] + ":" + parameter.split(":")[3]).trim()));
                    }
                    contactTreeSet.add(contact);
                    if (generator < contact.getId()) {
                        generator = contact.getId();
                    }
                }
            });
            System.out.println(contactTreeSet.size() + " contacts was loaded.");
            return contactTreeSet;
        } catch (FileNotFoundException e) {
            System.out.println("Error!\nFile \"" + Constants.TXT_LIST_PATH + "\" not exist.");
            return contactTreeSet;
        }
    }

    @Override
    public void saveAllContactsToDatFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(Constants.DAT_LIST_PATH);
             ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(contactTreeSet.toArray());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(contactTreeSet.size() + " contacts saved to \"" + Constants.DAT_LIST_PATH + "\".");
    }

    @Override
    public Set<Contact> loadAllContactsFromDatFile() {
        try (FileInputStream fileInputStream = new FileInputStream(Constants.DAT_LIST_PATH);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            Object[] arr = (Object[]) inputStream.readObject();
            for (Object contact : arr) {
                contactTreeSet.add((Contact) contact);
                if (generator < ((Contact) contact).getId()) {
                    generator = ((Contact) contact).getId();
                }
            }
            System.out.println(contactTreeSet.size() + " contacts was loaded.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error!\nFile \"" + Constants.TXT_LIST_PATH + "\" not exist.");
        }
        return contactTreeSet;
    }

    private void searchSameContact(Contact contact) throws MyAddressBookException {
        Optional<Contact> sameContactOpt = contactTreeSet.stream()
                .filter(sameContact -> Objects.equals(sameContact.getPhoneNumber(),
                        contact.getPhoneNumber()))
                .findFirst();
        if (sameContactOpt.isPresent()) {
            throw new MyAddressBookException(ResponseCode.OBJECT_EXIST,
                    "Same contact is exist with Phone number = " + sameContactOpt.get().getPhoneNumber());
        }
    }

}

package dao.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import dao.ContactDaoFileIo;
import entity.Contact;
import exceptions.MyAddressBookException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static exceptions.MyAddressBookException.NOT_FOUND_MESSAGE;

/**
 * CRUD contact's data to/from DB.
 * Process request from ContactServiceImpl and return data back.
 *
 * @author Vitamin-68
 * @see dao.ContactDao
 */
public class ContactDaoImpl implements ContactDao, ContactDaoFileIo {

    private static int generator = 0;

    private static Set<Contact> contactTreeSet = new TreeSet<>(Comparator.comparing(Contact::getId));


    @Override
    public Contact createContact(Contact newContact) {

        newContact.setId(++generator);
        contactTreeSet.add(newContact);
        System.out.println("New contact added successfully:");
        System.out.println(newContact);
        return newContact;
    }


    @Override
    public Contact findById(int id) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND,
                        "Contact with ID = " + id + " not exist"));
    }


    public Contact findByName(String name) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND, NOT_FOUND_MESSAGE));
    }


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
        System.out.println("All contacts saved to \"" + Constants.TXT_LIST_PATH + "\".");
        return;
    }

    @Override
    public Set<Contact> loadAllContactsFromTxtFile(BufferedReader bufReader) { //} throws FileNotFoundException {
//        File file = new File(Constants.TXT_LIST_PATH);
//        if (file.exists()) {
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
                    }else if (parameter.contains(Constants.CREATE_DATE)) {
                        contact.setCreateDate(LocalDateTime.parse((parameter.split(":")[1] +
                                ":" + parameter.split(":")[2] + ":" + parameter.split(":")[3]).trim()));
                    } else if (parameter.contains(Constants.UPDATE_DATE)) {
                        contact.setUpdateDate(LocalDateTime.parse((parameter.split(":")[1] +
                                ":" + parameter.split(":")[2] + ":" + parameter.split(":")[3]).trim()));
                    }
                    contactTreeSet.add(contact);
                }
            });
            System.out.println("All contacts was restored.");
            return contactTreeSet;
        } catch (FileNotFoundException e) {
            System.out.println("Error!\nFile \"" + Constants.TXT_LIST_PATH + "\" not exist.");
            return contactTreeSet;
        }//        } else {
//            System.out.println("Error!\nFile \"" + file + "\" not exist.");
//            return contactTreeSet;
//        }
    }

    @Override
    public void saveAllContactsToDatFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(Constants.DAT_LIST_PATH);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(contactTreeSet);
        outputStream.close();
        System.out.println("All contacts saved to \"" + Constants.DAT_LIST_PATH + "\".");
        return;
    }

    @Override
    public Set<Contact> loadAllContactsFromDatFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(Constants.DAT_LIST_PATH);
            ObjectInputStream inputStream1 = new ObjectInputStream(fileInputStream);
            contactTreeSet = (Set<Contact>) inputStream1.readObject();
            fileInputStream.close();
            inputStream1.close();
            System.out.println("All contacts are restored.");
            return contactTreeSet;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error!\nFile \"" + Constants.TXT_LIST_PATH + "\" not exist.");
        }
        return contactTreeSet;
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

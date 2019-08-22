package dao.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;
import exceptions.MyAddressBookException;

import java.util.*;
import java.util.stream.Collectors;

import static exceptions.MyAddressBookException.NOT_FOUND_MESSAGE;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static Set<Contact> contactTreeSet = new TreeSet<>(Comparator.comparing(Contact::getId));

    @Override
    public Contact createContact(Contact newContact) {
        newContact.setId(++generator);
        contactTreeSet.add(newContact);
        System.out.println("New contact added successfully:");
        showOneContact(newContact);
        return newContact;
    }

    @Override
    public Contact findById(int id) throws MyAddressBookException {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElseThrow(()-> new MyAddressBookException(ResponseCode.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    public Contact findByName(String name) throws MyAddressBookException {
        return  contactTreeSet
                .stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new MyAddressBookException(ResponseCode.NOT_FOUND, NOT_FOUND_MESSAGE));
    }

    @Override
    public Contact updateContact(Contact contact) {
        System.out.println(contact);
        contactTreeSet = contactTreeSet
                .stream()
                .peek(updatedContact -> {
                    if (Objects.equals(updatedContact.getId(), contact.getId())) {
                        updatedContact = contact;
                        System.out.println("upd - " + updatedContact);
                        contactTreeSet.forEach(System.out::println);
                    }
                })
                .collect(Collectors.toCollection(TreeSet::new));
        return contact;
    }

    @Override
    public boolean removeContact(int id, Scanner scanner) {
        try {
            showOneContact(findById(id));
        } catch (MyAddressBookException e) {
            System.out.println(e);
        }
        System.out.println("Do you want to delete this contact?");
        if (scanner.next().equalsIgnoreCase("y")) {
            boolean result = contactTreeSet.removeIf(contact -> Objects.equals(contact.getId(), id));
            return result;
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
                comparator = Comparator.comparing(Contact::getUpdateTime);
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
        System.out.println("8. Data of update: " + contact.getUpdateTime() + "\n");
    }

    @Override
    public boolean cloneContact(Contact contactCarrier, Contact contactTarget) {
        if (contactCarrier != null && contactTarget != null){
            contactTarget.setId(contactCarrier.getId());
            contactTarget.setName(contactCarrier.getName());
            contactTarget.setLastName(contactCarrier.getLastName());
            contactTarget.setAge(contactCarrier.getAge());
            contactTarget.setPhoneNumber(contactCarrier.getPhoneNumber());
            contactTarget.setMarried(contactCarrier.isMarried());
            contactTarget.setCreateDate(contactCarrier.getCreateDate());
            contactTarget.setUpdateTime(contactCarrier.getUpdateTime());
            return true;
        } else
            return false;
    }

    private void searchSameContact(Contact contact) throws MyAddressBookException {
        Optional<Contact> sameContactOpt = contactTreeSet.stream()
                .filter(sameContact -> sameContact
                        .getPhoneNumber()
                        .equals(contact.getPhoneNumber()))
                .findFirst();
        if (sameContactOpt.isPresent()) {
            throw new MyAddressBookException(ResponseCode.OBJECT_EXIST,
                    "Same contact is exist with id" + sameContactOpt.get().getId());
        }
    }

    private void searchSameContact2(Contact contact) {
        contactTreeSet.stream()
                .filter(sameContact ->
                        sameContact.getPhoneNumber()
                                .equals(contact.getPhoneNumber()))
                .findFirst()
                .ifPresent(MyAddressBookException::new);
    }
}

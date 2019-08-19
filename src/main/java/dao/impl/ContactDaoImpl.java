package dao.impl;

import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;
import entity.ContactIdComparator;
import exceptions.MyAddressBookException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

//    @Override
//    public Contact findById(int id) {
//        for (Contact contact : contactTreeSet) {
//            if (Objects.equals(contact.getId(), id)) {
//                showOneContact(contact);
//                return contact;
//            }
//        }
//        System.out.println("Contact with ID = " + id + " not found.");
//        return null;
//    }

    @Override
    public Contact findById(int id) {

        return contactTreeSet
                .stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElseThrow(()-> new MyAddressBookException(ResponseCode.NOT_FOUND, NOT_FOUND_MESSAGE));

    }

//    @Override
//    public Contact updateContact(Contact updatedContact) {
//        updatedContact.setUpdateTime(LocalDateTime.now());
//        cloneContact(updatedContact, findById(updatedContact.getId()));
//        return updatedContact;
//    }

    @Override
    public Contact updateContact(Contact contact) {
       contactTreeSet = contactTreeSet
               .stream()
               .peek(updatedContact -> {
                   if (Object.equals(updatedContact.getId(),  contact.getId())){
                       updatedContact = contact;
                   }
               })
               .collect(Collectors.toCollection(TreeSet::new));
       return contact;
    }


//    @Override
//    public boolean removeContact(int id, Scanner scanner) {
//        Contact contact = findById(id);
//        if (contact != null) {
//            System.out.println("Do you really want to delete this contact?");
//            if (scanner.next().equalsIgnoreCase("y")) {
//                contactTreeSet.remove(contact);
//                System.out.println("Contact with ID = " + id + " was deleted successfully.\n");
//                return true;
//            } else {
//                System.out.println("Contact has not been deleted.\n");
//                return false;
//            }
//        } else {
//            System.out.println("Contact don't exist and can't be deleted.\n");
//            return false;
//        }
//    }

    @Override
    public boolean removeContact(int id, Scanner scanner) {
        boolean result = contactTreeSet.removeIf(contact -> Objects.equals(contact.getId(), id));
        return result;
    }

//    @Override
//    public void showAllContacts() {
//        for (Contact contact : contactTreeSet) {
//            System.out.println(contact);
//        }
//        System.out.println("\n\n");
//        contactTreeSet.stream()
//                .sorted(Comparator.comparing(Contact::getName))
//                .collect(Collectors
//                        .toList()).forEach(System.out::println);
//    }

    @Override
    public void showAllContacts(int number) {
        Comparator<Contact> comparator;
        switch (number) {
            case 1:
                comparator = Comparator.comparing(Contact::getId);
                // analogichno:
//                comparator = new Comparator<Contact>() {
//                    @Override
//                    public int compare(Contact o1, Contact o2) {
//                        if(o1.getId() > o2.getId()) {
//                            return 1;
//                        } else {
//                            return -1;
//                        }
//                    }
//                };
                break;
            case 2:
                comparator = Comparator.comparing(Contact::getLastName);
                    break;
            default:
                comparator = Comparator.comparing(Contact::getId);
            }
            contactTreeSet.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList())
                    .forEach((System.out::println));

    }


    private void showOneContact(Contact contact) {
        System.out.println("1. ID: " + contact.getId());
        System.out.println("2. Name: " + contact.getName());
        System.out.println("3. Last name: " + contact.getLastName());
        System.out.println("4. Age: " + contact.getAge());
        System.out.println("5. Phone number: " + contact.getPhoneNumber());
        System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
        System.out.println("7. Data of create: " + contact.getCreateDate());
        System.out.println("8. Data of update: " + contact.getUpdateTime() + "\n\n");
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
                .filter(sameContact ->sameContact
                        .getPhoneNumber()
                        .equals(contact.getPhoneNumber()))
                .findFirst();
        if(sameContactOpt.isPresent()) {
            throw new MyAddressBookException(ResponseCode.OBJECT_EXIST, "Same contact is exist id" + sameContactOpt.get().getId());
        }
    }

    private void searchSameContact2(Contact contact) throws MyAddressBookException {
        contactTreeSet.stream()
                .filter(sameContact ->sameContact
                        .getPhoneNumber()
                        .equals(contact.getPhoneNumber()))
                .findFirst()
//                .ifPresent(MyAddressBookException)
    };
}

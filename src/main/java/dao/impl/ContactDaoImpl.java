package dao.impl;

import dao.ContactDao;
import entity.Contact;
import entity.ContactIdComparator;

import java.time.LocalDateTime;
import java.util.*;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static Set<Contact> contactTreeSet = new TreeSet<>(new ContactIdComparator());

    @Override
    public Contact createContact(Contact newContact) {
        newContact.setId(++generator);
        contactTreeSet.add(newContact);
        System.out.println("New contact added successfully:");
        showOneContact(newContact);
        return newContact;
    }

    @Override
    public Contact findById(int id) {
        for (Contact contact : contactTreeSet) {
            if (Objects.equals(contact.getId(), id)) {
                showOneContact(contact);
                return contact;
            }
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return null;
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        updatedContact.setUpdateTime(LocalDateTime.now());
        cloneContact(updatedContact, findById(updatedContact.getId()));
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id, Scanner scanner) {
        Contact contact = findById(id);
        if (contact != null) {
            System.out.println("Do you really want to delete this contact?");
            if (scanner.next().equalsIgnoreCase("y")) {
                contactTreeSet.remove(contact);
                System.out.println("Contact with ID = " + id + " was deleted successfully.\n");
                return true;
            } else {
                System.out.println("Contact has not been deleted.\n");
                return false;
            }
        } else {
            System.out.println("Contact don't exist and can't be deleted.\n");
            return false;
        }
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contactTreeSet) {
            System.out.println(contact);
        }
        System.out.println("\n\n");
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

}

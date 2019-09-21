package dao.impl;

import dao.ContactDao;
import entity.Contact;

import java.time.LocalDateTime;
import java.util.*;

public class ContactDaoImpl implements ContactDao {

    private static int generator = 0;

    private static List<Contact> contactArrayList = new ArrayList<>();

    @Override
    public Contact createContact(Contact newContact) {
        newContact.setId(++generator);
        contactArrayList.add(newContact);
        System.out.println("New contact added successfully:");
        System.out.println(newContact);
        return newContact;
    }

    @Override
    public int findById(int id) {
        for (Contact contact : contactArrayList) {
            if (Objects.equals(contact.getId(), id)) {
                System.out.println(contact);
                return contactArrayList.indexOf(contact);
            }
        }
        System.out.println("Contact with ID = " + id + " not found.\n");
        return -1;
    }

    @Override
    public Contact updateContact(Contact updatedContact, int index) {
        updatedContact.setUpdateTime(LocalDateTime.now());
        contactArrayList.set(index, updatedContact);
        System.out.println(contactArrayList.get(index));
        return updatedContact;
    }

    @Override
    public void cloneContact(Contact contact, int index)  {
        contact.setId(contactArrayList.get(index).getId());
        contact.setName(contactArrayList.get(index).getName());
        contact.setLastName(contactArrayList.get(index).getLastName());
        contact.setAge(contactArrayList.get(index).getAge());
        contact.setPhoneNumber(contactArrayList.get(index).getPhoneNumber());
        contact.setMarried(contactArrayList.get(index).isMarried());
        contact.setCreateDate(contactArrayList.get(index).getCreateDate());
    }

    @Override
    public boolean removeContact(int id, Scanner scanner) {
        int index = findById(id);
        if (index >= 0) {
            System.out.println("Do you really want to delete this contact?");
            if (scanner.next().equalsIgnoreCase("y")) {
                contactArrayList.remove(index);
                System.out.println("Contact with ID = " + id + " was deleted successfully.\n");
                return true;
            } else {
                System.out.println("Contact deletion was canceled.\n");
                return false;
            }
        }
        return false;
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contactArrayList) {
            System.out.println(contact);
        }
    }

}

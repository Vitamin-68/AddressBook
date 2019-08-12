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
        return newContact;
    }

    @Override
    public int findById(int id) {
        for (Contact contact : contactArrayList) {
            if (Objects.equals(contact.getId(), id)) {
                showOneContact(contact);
                return contactArrayList.indexOf(contact);
            }
        }
        System.out.println("Contact with ID = " + id + " not found.");
        return -1;
    }

    @Override
    public Contact updateContact(Contact updatedContact) {
        for (Contact contact : contactArrayList) {
            if (Objects.equals(contact.getId(), updatedContact.getId())) {
                contact = updatedContact;
                return contact;
            }
        }
        return updatedContact;
    }

    @Override
    public boolean removeContact(int id, Scanner scanner) {
        int index = findById(id);
        if (index > 0) {
            System.out.println("Do you really want to delete this contact?");
            if (scanner.next().equalsIgnoreCase("y")) {
                contactArrayList.remove(index);
                System.out.println("Contact with ID = " + id + " was deleted successfully");
                return true;
            } else {
                System.out.println("Contact has not been deleted");
                return false;
            }
        }
//        System.out.println("Contact with ID = " + id + " not found.");
        return false;
    }

    @Override
    public void showAllContacts() {
        for (Contact contact : contactArrayList) {
            System.out.println(contact);
        }
    }

    @Override
    public void saveUpdatedField(Contact contact, int field, int index) {
        Contact contactUpdate = contactArrayList.get(index);
        switch (field) {
            case 2: {
                contactUpdate.setName(contact.getName());
                contactUpdate.setUpdateTime(LocalDateTime.now());
                break;
            }
            case 3: {
                contactUpdate.setLastName(contact.getLastName());
                contactUpdate.setUpdateTime(LocalDateTime.now());
                break;
            }
            case 4: {
                contactUpdate.setAge(contact.getAge());
                contactUpdate.setUpdateTime(LocalDateTime.now());
                break;
            }
            case 5: {
                contactUpdate.setPhoneNumber(contact.getPhoneNumber());
                contactUpdate.setUpdateTime(LocalDateTime.now());
                break;
            }
            case 6: {
                contactUpdate.setMarried(contact.isMarried());
                contactUpdate.setUpdateTime(LocalDateTime.now());
                break;
            }
        }
    }

    private void showOneContact(Contact contact) {
        System.out.println("1. ID: " + contact.getId());
        System.out.println("2. Name: " + contact.getName());
        System.out.println("3. Last name: " + contact.getLastName());
        System.out.println("4. Age: " + contact.getAge());
        System.out.println("5. Phone number: " + contact.getPhoneNumber());
        System.out.println("6. Martial status: : " + (contact.isMarried() ? "Married" : "No married"));
        System.out.println("7. Data of create: " + contact.getCreateDate());
        System.out.println("8. Data of update: " + contact.getUpdateTime());
        return;
    }

}

package dao.impl;

import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;
import exceptions.MyAddressBookException;
import util.ConnectionDB;
import util.DBQueries;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * CRUD contact's data to/from DB.
 * Process request from ContactServiceImpl and return data back.
 *
 * @author Vitamin-68
 * @see dao.ContactDao
 */
public class ContactDaoImpl implements ContactDao {


    @Override
    public Contact createContact(Contact newContact) throws MyAddressBookException {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.INSERT_CONTACT)) {
            if (!isExistSamePhoneNumber(newContact.getPhoneNumber())) {
                setPreparedStatementFromContact(newContact, preparedStatement);
                preparedStatement.execute();
                System.out.println("New contact added successfully:");
                System.out.println(newContact);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new MyAddressBookException(ResponseCode.FAILED_SAVE_DB, "Unable save to DB.");
        }
        return newContact;
    }


    @Override
    public Contact findById(int id) throws MyAddressBookException {
        Contact contact = new Contact();
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.FIND_CONTACT_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            setFieldsOfContactFromResultSet(contact, resultSet);
        } catch (SQLException e) {
            throw new MyAddressBookException(ResponseCode.NOT_FOUND, "Contact with ID = " + id + " not exist.\n");
        }
        if (Objects.nonNull(contact) && contact.getId() != 0) {
            return contact;
        } else {
            throw new MyAddressBookException(ResponseCode.FAILED_GET_DATA, "Failed get data.");
        }
    }


    public Contact findByName(String name) throws MyAddressBookException {
        Contact contact = new Contact();
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.FIND_CONTACT_BY_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            setFieldsOfContactFromResultSet(contact, resultSet);
        } catch (SQLException e) {
            throw new MyAddressBookException(ResponseCode.NOT_FOUND, "Contact with name = " + name + " not exist.\n");
        }
        return contact;
    }


    @Override
    public Contact updateContact(Contact contact) {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.UPDATE_CONTACT_WHERE_ID)) {
            setPreparedStatementFromContact(contact, preparedStatement);
            preparedStatement.setInt(8, contact.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }


    @Override
    public boolean removeContact(int id, BufferedReader bufReader) throws MyAddressBookException {
//        Contact contact = findById(id);
//        if (contact.getId() >0) {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.DELETE_CONTACT_WHERE_ID)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == -1) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new MyAddressBookException(ResponseCode.FAILED_DELETE_CONTACT_FROM_DB, "Delete contact failed");
        }
//        }

//        try {
//            System.out.println(findById(id));
//            System.out.println("Do you want to delete this contact? (y/n):");
//            if (bufReader.readLine().equalsIgnoreCase("y")) {
//                boolean result = contactTreeSet.removeIf(contact -> Objects.equals(contact.getId(), id));
//                if (result) {
//                    System.out.println("Contact with ID = " + id + " deleted successfully.");
//                } else System.out.println("Delete failed");
//                return result;
//            } else {
//                System.out.println("Delete canceled.");
//                return true;
//            }
//        } catch (MyAddressBookException e) {
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        return false;
    }


    @Override
    public void showAllContacts() {
        try (Connection connection = ConnectionDB.getConnect();
             Statement statement = connection.createStatement()) {
            statement.execute(DBQueries.SELECT_ALL);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.printf("%3s%3d  %5s%10s  %10s%10s  %4s%3d  %13s%+12d  %8s%10s  %10s%19s  %10s%19s%n",
                        "ID:", resultSet.getInt(1),
                        "Name:", resultSet.getString(2),
                        "Last name:", resultSet.getString(3),
                        "Age:", resultSet.getInt(4),
                        "Phone number:", resultSet.getInt(5),
                        "Status: ", (resultSet.getBoolean(6) ? "Married" : "No married"),
                        "Crt Date: ",  resultSet.getString(7).substring(0,19),
                        "Upd Date: ", resultSet.getString(8).substring(0,19));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isExistSamePhoneNumber(int phoneNumber) throws MyAddressBookException {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.FIND_CONTACT_BY_PHONE_NUMBER)) {
            preparedStatement.setInt(1, phoneNumber);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.getInt(5) == phoneNumber) {
                System.out.println("Same contact is exist with ID = " + resultSet.getInt(1));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new MyAddressBookException(ResponseCode.NOT_FOUND, "Contact with phone not exist.\n");
        }
    }

    private void setPreparedStatementFromContact(Contact contact, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setInt(3, contact.getAge());
            preparedStatement.setInt(4, contact.getPhoneNumber());
            preparedStatement.setBoolean(5, contact.isMarried());
            preparedStatement.setString(6, contact.getCreateDate().toString());
            preparedStatement.setString(7, contact.getUpdateDate().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setFieldsOfContactFromResultSet(Contact contact, ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                contact.setId(resultSet.getInt(1));
                contact.setName(resultSet.getString(2));
                contact.setLastName(resultSet.getString(3));
                contact.setAge(resultSet.getInt(4));
                contact.setPhoneNumber(resultSet.getInt(5));
                contact.setMarried(resultSet.getBoolean(6));
                contact.setCreateDate(LocalDateTime.parse(resultSet.getString(7)));
                contact.setUpdateDate(LocalDateTime.parse(resultSet.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

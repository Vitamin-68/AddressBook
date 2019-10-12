package dao.impl;

import constants.Constants;
import constants.ResponseCode;
import dao.ContactDao;
import entity.Contact;
import exceptions.MyAddressBookException;
import util.ConnectionDB;
import util.DBQueries;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;


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
            boolean db = isEmptyDB();
            if (db || !isExistSamePhoneNumber(newContact.getPhoneNumber())) {
                setPreparedStatementFromContact(newContact, preparedStatement);
                preparedStatement.execute();
                System.out.println("New contact added successfully:");
            } else {
                System.out.println("New contact isn't added.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new MyAddressBookException(ResponseCode.FAILED_SAVE_DB, "Unable save to DB.");
        }
        return newContact;
    }



    @Override
    public Contact updateContact(Contact contact) throws MyAddressBookException {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.UPDATE_CONTACT_WHERE_ID)) {
            if (!isExistSamePhoneNumber(contact.getPhoneNumber())) {
                setPreparedStatementFromContact(contact, preparedStatement);
                preparedStatement.setInt(8, contact.getId());
                preparedStatement.execute();
                System.out.println("Update is done.");
            } else {
                System.out.println("Update is failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }


    @Override
    public boolean removeContact(int id, BufferedReader bufReader) throws MyAddressBookException {
        Contact contact = findById(id);
        System.out.println(contact);
        if (contact.getId() > 0 && isDeletionConfirmed(bufReader)) {
            try (Connection connection = ConnectionDB.getConnect();
                 PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.DELETE_CONTACT_WHERE_ID)) {
                preparedStatement.setInt(1, id);
                int result = preparedStatement.executeUpdate();
                if (result == 1) {
                    System.out.println("Contact with ID = " + id + " deleted successfully.");
                    return true;
                } else if (result == -1) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new MyAddressBookException(ResponseCode.FAILED_DELETE_CONTACT_FROM_DB, "Delete contact failed");
            }
        }
        return false;
    }


    @Override
    public void showAllContacts(int sortFieldNumber) {
        try (Connection connection = ConnectionDB.getConnect();
             Statement statement = connection.createStatement()) {
            String sortFieldString;
            switch (sortFieldNumber) {
                case Constants.SORTED_BY_ID: {
                    System.out.println("Contacts sorted by ID:");
                    sortFieldString = " ORDER BY id";
                    break;
                }
                case Constants.SORTED_BY_NAME: {
                    System.out.println("Contacts sorted by Name:");
                    sortFieldString = " ORDER BY name";
                    break;
                }
                case Constants.SORTED_BY_LAST_NAME: {
                    System.out.println("Contacts sorted by Last name:");
                    sortFieldString = " ORDER BY `last name`";
                    break;
                }
                case Constants.SORTED_BY_AGE: {
                    System.out.println("Contacts sorted by Age:");
                    sortFieldString = " ORDER BY age";
                    break;
                }
                case Constants.SORTED_BY_PHONE: {
                    System.out.println("Contacts sorted by Phone number:");
                    sortFieldString = " ORDER BY `phone number`";
                    break;
                }
                case Constants.SORTED_BY_STATUS: {
                    System.out.println("Contacts sorted by Marital status:");
                    sortFieldString = " ORDER BY married";
                    break;
                }
                case Constants.SORTED_BY_DATE_OF_CREATE: {
                    System.out.println("Contacts sorted by Create date:");
                    sortFieldString = " ORDER BY create_date_time";
                    break;
                }
                case Constants.SORTED_BY_DATE_OF_UPDATE: {
                    System.out.println("Contacts sorted by Update date:");
                    sortFieldString = " ORDER BY update_date_time";
                    break;
                }
                default: {
                    System.out.println("Wrong number of field, contacts sorted by ID:");
                    sortFieldString = " ORDER BY id";
                }
            }
            statement.execute(DBQueries.SELECT_ALL + sortFieldString);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                resultSetOutputToScreen(resultSet);
            } else {
                System.out.println("DB of contacts is empty.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            System.out.println(e.getMessage());
        }
        if (contact.getId() != 0) {
            return contact;
        } else {
            throw new MyAddressBookException(ResponseCode.NOT_FOUND, "Contact with ID = " + id + " not exist.\n");
        }
    }

    @Override
    public void findByName(String name) throws MyAddressBookException {
        try (Connection connection = ConnectionDB.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.FIND_CONTACT_BY_NAME)) {
            preparedStatement.setString(1, "%" + name + "%");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                resultSetOutputToScreen(resultSet);
            } else {
                throw new MyAddressBookException(ResponseCode.NOT_FOUND, "Contact with name = '" + name + "' not exist.\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isExistSamePhoneNumber(String phoneNumber) throws MyAddressBookException {
        try {
            Connection connection = ConnectionDB.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.FIND_CONTACT_BY_PHONE_NUMBER);
            preparedStatement.setString(1, phoneNumber);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                if (resultSet.getString(5).equalsIgnoreCase(phoneNumber)) {
                    System.out.println("Contact with Phone number = " + resultSet.getString(5) +
                            " already exist with ID = " + resultSet.getInt(1));
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new MyAddressBookException(ResponseCode.FAILED_GET_DATA, "Failed get data from DB.\n");
        }
    }

    private void setPreparedStatementFromContact(Contact contact, PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setInt(3, contact.getAge());
            preparedStatement.setString(4, contact.getPhoneNumber());
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
                contact.setPhoneNumber(resultSet.getString(5));
                contact.setMarried(resultSet.getBoolean(6));
                contact.setCreateDate(LocalDateTime.parse(resultSet.getString(7)));
                contact.setUpdateDate(LocalDateTime.parse(resultSet.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isDeletionConfirmed(BufferedReader bufReader) {
        System.out.println("Do you want to delete this contact? (y/n):");
        try {
            while (true) {
                String str = bufReader.readLine();
                if (str.trim().equalsIgnoreCase("y")) {
                    return true;
                } else if (str.trim().equalsIgnoreCase("n")) {
                    System.out.println("Delete canceled.");
                    return false;
                } else {
                    System.out.println("Only 'y' or 'n' please.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resultSetOutputToScreen(ResultSet resultSet) throws SQLException {
        do {
            System.out.printf("%3s%3d  %5s%10s  %10s%10s  %4s%3d  %13s%12s  %8s%10s  %10s%19s  %10s%19s%n",
                    "ID:", resultSet.getInt(1),
                    "Name:", resultSet.getString(2),
                    "Last name:", resultSet.getString(3),
                    "Age:", resultSet.getInt(4),
                    "Phone number:", resultSet.getString(5),
                    "Status: ", (resultSet.getBoolean(6) ? "Married" : "No married"),
                    "Crt Date: ", resultSet.getString(7).substring(0, 19),
                    "Upd Date: ", resultSet.getString(8).substring(0, 19));
        }
        while (resultSet.next());
    }

    private boolean isEmptyDB() {
        try {
            Connection connection = ConnectionDB.getConnect();
            Statement statement = connection.createStatement();
            statement.execute(DBQueries.SELECT_ALL);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

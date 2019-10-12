package util;

public class DBQueries {
    public static final String INSERT_CONTACT = "insert into contacts " +
            "(name, `last name`, age, `phone number`, married, create_date_time, update_date_time) " +
            "values(?,?,?,?,?,?,?)";
    public static final String UPDATE_CONTACT_WHERE_ID = "UPDATE contacts " +
            "SET name = ?, `last name` = ?, age = ?, `phone number` = ?, married = ?, create_date_time = ?, update_date_time = ? " +
            "WHERE id = ?";
    public static final String DELETE_CONTACT_WHERE_ID = "DELETE FROM contacts WHERE id = ?";
    public static final String FIND_CONTACT_BY_ID = "SELECT * FROM contacts WHERE id = ?";
    public static final String FIND_CONTACT_BY_AGE = "SELECT * FROM contacts WHERE age = ?";
    public static final String FIND_CONTACT_BY_NAME = "SELECT * FROM contacts WHERE name LIKE ?";
    public static final String FIND_CONTACT_BY_PHONE_NUMBER = "SELECT * FROM contacts WHERE `phone number` = ?";
    public static final String SELECT_ALL = "SELECT * FROM contacts";
}

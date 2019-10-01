package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
    private static final String JDBC = "jdbc:mysql:";
    private static final String HOST_AND_PORT = "//localhost:3306";
    private static final String NAME_DB = "/AddressBook";
    private static final String UNICODE = "useUnicode=true";
    private static final String JDBC_TIME_ZONE_SHIFT = "useJDBCCompliantTimezoneShift=true";
    private static final String DATA_TIME = "useLegacyDatetimeCode=false";
    private static final String SERVICE_TIME_ZONE = "serverTimezone=UTC";
    private static final String SEPARATOR = "?";
    private static final String SEPARATOR_AND = "&";
    private static final String FULL_URL = JDBC + HOST_AND_PORT + NAME_DB + SEPARATOR + UNICODE +
            SEPARATOR_AND + JDBC_TIME_ZONE_SHIFT + SEPARATOR_AND + DATA_TIME + SEPARATOR_AND + SERVICE_TIME_ZONE;

    static final String url = "jdbc:mysql://localhost:3306/addressBook?useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS contacts(" +
            "   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            "   name VARCHAR(255) NOT NULL, " +
            "   `last name` VARCHAR(255) NOT NULL, " +
            "   age INT NULL, " +
            "   `phone number` INT NULL, " +
            "   married BOOLEAN DEFAULT false, " +
            "   create_date_time VARCHAR(45), " +
            "   update_date_time VARCHAR(45))";

    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection connection = null;

    public static Connection getConnect() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(FULL_URL, USER, PASSWORD);
            }else if (connection.isClosed()){
                connection = DriverManager.getConnection(FULL_URL, USER, PASSWORD);
            } else {
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void connectAndCreateDataBase() {
        Statement statement;
        try {
            statement = getConnect().createStatement();
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection() {
        try {
         getConnect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

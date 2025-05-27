package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String URL="jdbc:mysql://127.0.0.1:3306/chessarenamanager";
    private static final String user="root";
    private static final String password= "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,user,password);
    }
}

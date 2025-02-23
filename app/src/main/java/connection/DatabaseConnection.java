package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  private static final String URL ="jdbc:mysql://localhost:3306/student_list";
  private static final String USER = "root";
  private static final String PASSWORD = "root";
  private static Connection connection = null;

  public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connecting to database...");

        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected to database successfully");
      } catch (ClassNotFoundException | SQLException cnfe) {
        System.out.println("Error: " + cnfe.getMessage());
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection == null) {
      System.out.println("Connection is not established");
      return;
    }

    try {
      connection.close();
      System.out.println("Connection is closed");
    } catch (SQLException sqle) {
      System.out.println("Error closing connection: " + sqle.getMessage());
    }
  }
}
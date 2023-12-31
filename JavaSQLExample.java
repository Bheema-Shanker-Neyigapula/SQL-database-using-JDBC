import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaSQLExample {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database");

            // Create a table (if not exists)
            createTable(connection);

            // Insert data into the table
            insertData(connection, "John Doe", 25);

            // Retrieve data from the table
            retrieveData(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), age INT)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Table created (if not exists)");
        }
    }

    private static void insertData(Connection connection, String name, int age) throws SQLException {
        String insertDataSQL = "INSERT INTO users (name, age) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
            System.out.println("Data inserted into the table");
        }
    }

    private static void retrieveData(Connection connection) throws SQLException {
        String retrieveDataSQL = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(retrieveDataSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            System.out.println("Retrieving data from the table:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        }
    }
}

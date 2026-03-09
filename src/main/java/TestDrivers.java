import java.sql.DriverManager;
import java.util.Enumeration;

public class TestDrivers {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver successfully loaded!");

            Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                System.out.println("Available Driver: " + drivers.nextElement());
            }

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
}




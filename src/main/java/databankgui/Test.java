package databankgui;

import java.sql.*;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {
        System.out.println("Connecting to database...");
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(
                    DatabankGUIApplication.class.getResourceAsStream("/db.properties")
            );
        } catch (Exception e) {
            System.err.println("Error loading properties file:\n" + e.getMessage());
        }

        try (
                Connection connection = DriverManager.getConnection(
                        databaseProperties.getProperty("url"),
                        databaseProperties.getProperty("user"),
                        databaseProperties.getProperty("password")
                )
        ) {
            System.out.println("Connected to database, version: " + connection.getMetaData().getDatabaseProductVersion());
//            try (PreparedStatement getdata = connection.prepareStatement(
//                    "SELECT * FROM personen"
//            )) {
//                System.out.println("Trying to execute query...");
//                try (ResultSet rs = getdata.executeQuery()) {
//                    while (rs.next()) {
//                        int id = rs.getInt("id");
//                        String familienaam = rs.getString("familienaam");
//                        String voornaam = rs.getString("voornaam");
//                        System.out.println("ID: " + id + ", Family Name: " + familienaam + ", First Name: " + voornaam);
//                    }
//                } catch (SQLException e) {
//                    System.err.println("Query failed!");
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                System.err.println("Query failed!");
//            }
//            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO contactcodes (id, naam) values (?, ?)")) {
//                statement.setString(1, "M");
//                statement.setString(2, "Mobiele telefoon");
//                statement.execute();
//
//                statement.setString(1, "T");
//                statement.setString(2, "Vaste telefoon");
//                statement.execute();
//
//                statement.setString(1, "E");
//                statement.setString(2, "E-mail");
//                statement.execute();
//
//                statement.setString(1, "F");
//                statement.setString(2, "Fax");
//                statement.execute();
//            }
            try (PreparedStatement getdata = connection.prepareStatement(
                    "SELECT * FROM contactgegevens"
            )) {
                try (ResultSet rs = getdata.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String p_id = rs.getString("p_id");
                        String code = rs.getString("code");
                        String adres = rs.getString("adres");
                        System.out.println(id + " " + p_id + " " + code + " " + adres);
                    }
                } catch (SQLException e) {
                    System.err.println("Query failed!");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.err.println("Query failed!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }

}
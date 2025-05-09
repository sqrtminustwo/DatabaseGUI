package databankgui;

import java.sql.*;

public class Test {

    public static void main(String[] args) {
        System.out.println("Connecting to database...");

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:derby://localhost:1527/DB/contacts", "objprog", "sesamopenu"
                );
        ) {
            System.out.println("Connected to database, version: " + connection.getMetaData().getDatabaseProductVersion());
            try (PreparedStatement getdata = connection.prepareStatement(
                    "SELECT * FROM personen"
            )) {
                System.out.println("Trying to execute query...");
                try (ResultSet rs = getdata.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String familienaam = rs.getString("familienaam");
                        String voornaam = rs.getString("voornaam");
                        System.out.println("ID: " + id + ", Family Name: " + familienaam + ", First Name: " + voornaam);
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
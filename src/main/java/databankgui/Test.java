package databankgui;

import java.sql.*;
import java.util.Properties;

public class Test {

    private static PreparedStatement prepare(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public static void main(String[] args) {
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.load(
                    DatabankGUIApplication.class.getResourceAsStream("/properties/db.properties")
            );

            try (
                    Connection connection = DriverManager.getConnection(
                            databaseProperties.getProperty("url"),
                            databaseProperties.getProperty("user"),
                            databaseProperties.getProperty("password")
                    )
            ) {

                try (
                        PreparedStatement getallcols = prepare(connection, """
                            select * from personen
                        """);
                )
                {
                    ResultSet rs = getallcols.executeQuery();
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + " " + rs.getString(2));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("Error loading properties file:\n" + e.getMessage());
        }
    }

}
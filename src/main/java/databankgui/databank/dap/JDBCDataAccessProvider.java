package databankgui.databank.dap;

import databankgui.DatabankGUIApplication;
import databankgui.databank.dac.DataAccessContext;
import databankgui.databank.dac.JDBCDataAccessContext;
import databankgui.databank.dao.contactcodes.JDBCContactTypeDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCDataAccessProvider implements DataAccessProvider {

    private Properties databaseProperties;

    public JDBCDataAccessProvider(String properties) {
        try {
            databaseProperties = new Properties();
            databaseProperties.load(
                    DatabankGUIApplication.class.getResourceAsStream(properties)
            );
        } catch (Exception e) {
            System.err.println("Error loading properties file:\n" + e.getMessage());
        }
    }

    public DataAccessContext getDataAccessContext() throws SQLException {
        Connection connection = getConnection();
        return new JDBCDataAccessContext(new JDBCContactTypeDAO(connection), connection);
    }

    private Connection getConnection() throws SQLException {
        String user = databaseProperties.getProperty("user");
        if (user != null) {
            try {
                return DriverManager.getConnection(
                        databaseProperties.getProperty("url"),
                        user,
                        databaseProperties.getProperty("password")
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return DriverManager.getConnection(databaseProperties.getProperty("url"));
        }
        return null;
    }
}
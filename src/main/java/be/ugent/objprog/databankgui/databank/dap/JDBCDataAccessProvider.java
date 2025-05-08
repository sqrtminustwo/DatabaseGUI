package be.ugent.objprog.databankgui.databank.dap;

import be.ugent.objprog.databankgui.DatabankGUIApplication;
import be.ugent.objprog.databankgui.databank.dac.DataAccessContext;
import be.ugent.objprog.databankgui.databank.dac.JDBCDataAccessContext;
import be.ugent.objprog.databankgui.databank.dao.contactcodes.JDBCContactTypeDAO;

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
        return new JDBCDataAccessContext(new JDBCContactTypeDAO(), getConnection());
    }

    private Connection getConnection() throws SQLException {
        String user = databaseProperties.getProperty("user");
        if (user != null) {
            return DriverManager.getConnection(
                    databaseProperties.getProperty("url"),
                    user,
                    databaseProperties.getProperty("password")
            );
        } else {
            return DriverManager.getConnection(databaseProperties.getProperty("url"));
        }
    }
}
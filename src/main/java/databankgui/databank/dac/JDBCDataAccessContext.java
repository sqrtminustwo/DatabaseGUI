package databankgui.databank.dac;

import databankgui.databank.dao.contact.ContactDAO;
import databankgui.databank.dao.contact.JDBCContactDAO;
import databankgui.databank.dao.contactcodes.ContactTypeDAO;
import databankgui.databank.dao.contactcodes.JDBCContactTypeDAO;
import databankgui.databank.dao.person.JDBCPersonDao;
import databankgui.databank.dao.person.PersonDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccessContext implements DataAccessContext {

    private final JDBCContactTypeDAO contactTypeDAO;
    private final Connection connection;

    public JDBCDataAccessContext(JDBCContactTypeDAO contactTypeDAO, Connection connection) {
        this.contactTypeDAO = contactTypeDAO;
        this.connection = connection;
    }

    @Override
    public PersonDAO getPersonDAO() {
        return new JDBCPersonDao(connection);
    }

    @Override
    public ContactDAO getContactDAO() {
        return new JDBCContactDAO(connection);
    }

    @Override
    public ContactTypeDAO getContactTypeDAO() {
        return contactTypeDAO;
    }

    @Override
    public void close() throws SQLException { connection.close(); }
}
package databankgui.databank.dac;


import databankgui.databank.dao.contact.ContactDAO;
import databankgui.databank.dao.contactcodes.ContactTypeDAO;
import databankgui.databank.dao.person.PersonDAO;

import java.sql.SQLException;

public interface DataAccessContext extends AutoCloseable {
    PersonDAO getPersonDAO();
    ContactDAO getContactDAO();
    ContactTypeDAO getContactTypeDAO();
    void close() throws SQLException;
}
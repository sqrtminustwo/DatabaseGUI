package be.ugent.objprog.databankgui.databank.dac;


import be.ugent.objprog.databankgui.databank.DataAccessException;
import be.ugent.objprog.databankgui.databank.dao.contact.ContactDAO;
import be.ugent.objprog.databankgui.databank.dao.contactcodes.ContactTypeDAO;
import be.ugent.objprog.databankgui.databank.dao.person.PersonDAO;

import java.sql.SQLException;

public interface DataAccessContext extends AutoCloseable {
    PersonDAO getPersonDAO();
    ContactDAO getContactDAO();
    ContactTypeDAO getContactTypeDAO();
    void close() throws SQLException;
}
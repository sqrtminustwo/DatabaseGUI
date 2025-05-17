package databankgui.databank.dao.contactcodes;

import databankgui.databank.DataAccessException;

import java.util.List;

public interface ContactTypeDAO {

    ContactCode findContactType(String id) throws DataAccessException;
    List<String> getAllContactTypes() throws DataAccessException;
}
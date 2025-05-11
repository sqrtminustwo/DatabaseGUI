package databankgui.databank.dao.contactcodes;


import databankgui.databank.DataAccessException;

public interface ContactTypeDAO {

    ContactCode findContactType(String id) throws DataAccessException;

}
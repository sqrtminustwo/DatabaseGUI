package databankgui.databank.dao.contactcodes;


import databankgui.databank.DataAccessException;
import databankgui.databank.dao.person.Person;

public interface ContactTypeDAO {

    Iterable<Person> findContactType(char id) throws DataAccessException;

}
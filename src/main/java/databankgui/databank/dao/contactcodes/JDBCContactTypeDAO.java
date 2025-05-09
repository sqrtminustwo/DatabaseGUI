package databankgui.databank.dao.contactcodes;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.person.Person;

public class JDBCContactTypeDAO implements ContactTypeDAO {
    public Iterable<Person> findContactType(char id) throws DataAccessException {
        return null; //TODO: aanpassen
    }
}
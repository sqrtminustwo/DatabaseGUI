package be.ugent.objprog.databankgui.databank.dao.contactcodes;

import be.ugent.objprog.databankgui.databank.DataAccessException;
import be.ugent.objprog.databankgui.databank.dao.person.Person;

public class JDBCContactTypeDAO implements ContactTypeDAO {
    public Iterable<Person> findContactType(char id) throws DataAccessException {
        return null; //TODO: aanpassen
    }
}
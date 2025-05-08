package be.ugent.objprog.databankgui.databank.dao.contactcodes;

import be.ugent.objprog.databankgui.databank.DataAccessException;
import be.ugent.objprog.databankgui.databank.dao.person.Person;

public interface ContactTypeDAO {

    Iterable<Person> findContactType(char id) throws DataAccessException;

}
package databankgui.databank.dao.person;


import databankgui.databank.DataAccessException;

public interface PersonDAO {

    int createPerson(String name, String firstName) throws DataAccessException;

    void updatePerson(int id, String name, String firstName) throws DataAccessException;

    void deletePerson(int id) throws DataAccessException;

    Iterable<Person> findPersons(String namePrefix) throws DataAccessException;

}
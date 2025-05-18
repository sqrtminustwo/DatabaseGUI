package databankgui.databank.dao.person;


import databankgui.databank.DataAccessException;

public interface PersonDAO {

    Person createPerson(String familienaam, String voornaam) throws DataAccessException;

    void updatePerson(int id, String newFamilienaam, String newVoornaam) throws DataAccessException;

    Person findPerson(int id) throws DataAccessException;
    void deletePerson(int id) throws DataAccessException;

    Iterable<Person> findPersons(String namePrefix) throws DataAccessException;

}
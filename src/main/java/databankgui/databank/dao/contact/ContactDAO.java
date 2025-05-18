package databankgui.databank.dao.contact;

import databankgui.databank.DataAccessException;

public interface ContactDAO {

    void createContact(int p_id, String code, String adres) throws DataAccessException;

    void deleteContact (int id) throws DataAccessException;

    void editContact(int id, String adres) throws DataAccessException;

    Iterable<Contact> findContacts(int personId) throws DataAccessException;
}
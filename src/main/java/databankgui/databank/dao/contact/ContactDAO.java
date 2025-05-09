package databankgui.databank.dao.contact;

import databankgui.databank.DataAccessException;

public interface ContactDAO {

    void createContact(int p_id, char code, String adres) throws DataAccessException;

    void updateContact(int id, String adres) throws DataAccessException;

    void deleteContact (int id) throws DataAccessException;

    Iterable<Contact> findContacts(int personId) throws DataAccessException;
    Iterable<Contact> findContactsByType(int personId, char type) throws DataAccessException;
}
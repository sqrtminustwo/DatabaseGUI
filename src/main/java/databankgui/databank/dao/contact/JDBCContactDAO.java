package databankgui.databank.dao.contact;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.JDBCAbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCContactDAO extends JDBCAbstractDAO implements ContactDAO {

    public JDBCContactDAO(Connection connection) { super(connection); }

    @Override
    public void createContact(int p_id, String code, String adres) throws DataAccessException {
        try (
                PreparedStatement voegperson = prepare(
                        "INSERT INTO contactgegevens (p_id, code, adres) VALUES (?,?,?)"
                )
        ) {
            voegperson.setInt(1, p_id);
            voegperson.setString(2, code);
            voegperson.setString(3, adres);
            voegperson.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Konde contactgegeven(" + p_id + ") niet toevoegen:\n" + e.getMessage());
        }
    }

    @Override
    public void deleteContact(int id) throws DataAccessException {
        try (PreparedStatement deletecontact = prepare("DELETE FROM contactgegevens WHERE id = ?")) {
            deletecontact.setInt(1, id);
            deletecontact.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Fout bij verwijderen van contact (" + id + "):\n" + e.getMessage());
        }
    }

    @Override
    public void editContact(int id, String adres) throws DataAccessException {
        try (PreparedStatement deletecontact = prepare("UPDATE contactgegevens SET adres = ? WHERE id = ?")) {
            deletecontact.setString(1, adres);
            deletecontact.setInt(2, id);
            deletecontact.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException("Fout bij updaten van contact (" + id + "):\n" + e.getMessage());
        }
    }

    public Iterable<Contact> getContactGegevens(PreparedStatement statement) throws DataAccessException {
        List<Contact> persons = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int p_id = rs.getInt("p_id");
                String code = rs.getString("code");
                String adres = rs.getString("adres");
                persons.add(new Contact(id, p_id, code, adres));
            }
        } catch (Exception e) {
            throw new DataAccessException("Fout bij ophalen van contactgegevens:\n" + e.getMessage());
        }
        return persons;
    }

    @Override
    public Iterable<Contact> findContacts(int personId) throws DataAccessException {
        Iterable<Contact> persons;
        try (PreparedStatement getcontacts = prepare("SELECT * FROM contactgegevens WHERE p_id = ?")) {
            getcontacts.setInt(1, personId);
            persons = getContactGegevens(getcontacts);
        } catch (Exception e) {
            throw new DataAccessException("Fout bij ophalen van contactcode met p_id " + personId + ":\n" + e.getMessage());
        }
        return persons;
    }
}
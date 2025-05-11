package databankgui.databank.dao.person;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.JDBCAbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCPersonDao extends JDBCAbstractDAO implements PersonDAO {

    public JDBCPersonDao(Connection connection) { super(connection); }

    @Override
    public int createPerson(String name, String firstName) throws DataAccessException {
        int id;
        try (
                PreparedStatement voegperson = prepare(
                        "INSERT INTO personen (familienaam,voornaam) VALUES (?,?)"
                )
        ) {
            voegperson.setString(1, name);
            voegperson.setString(2, firstName);
            voegperson.executeUpdate();

            try (ResultSet rs = voegperson.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new RuntimeException("Fout bij genereren van sleutel voor persoon!");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Konde persoon niet toevoegen:\n" + e.getMessage());
        }
        return id;
    }


    @Override
    public void updatePerson(int id, String newVoornaam, String newFamielienaam) throws DataAccessException {
        try (
                PreparedStatement updateperson = prepare(
                        "UPDATE personen SET familienaam = ?, voornaam = ? WHERE id = ?"
                )
        ) {
            updateperson.setString(1, newFamielienaam);
            updateperson.setString(2, newVoornaam);
            updateperson.setInt(3, id);

            if (updateperson.executeUpdate() < 1) {
                System.out.println("Persoon met id: " + id + ", bevind zicht niet in het tabel!");
            }
        } catch (Exception e) {
            throw new DataAccessException("Konde persoon (" + id + ") niet updaten:\n" + e.getMessage());
        }
    }

    @Override
    public void deletePerson(int id) throws DataAccessException {
        try (
                PreparedStatement updateperson = prepare(
                        "DELETE FROM personen WHERE id = ?"
                )
        ) {
            updateperson.setInt(1, id);

            if (updateperson.executeUpdate() < 1) {
                System.out.println("Persoon (" + id + ") bevind zicht niet in het tabel");
            }
        } catch (Exception e) {
            throw new DataAccessException("Konde persoon (" + id + ")niet deleten:\n" + e.getMessage());
        }
    }

    @Override
    public Iterable<Person> findPersons(String namePrefix) throws DataAccessException {
        List<Person> persons = new ArrayList<>();
        try (
                PreparedStatement getpersons = prepare(
                        "SELECT * FROM personen WHERE familienaam LIKE ? OR voornaam LIKE ?"
                )
        ) {
            getpersons.setString(1, namePrefix + "%");
            getpersons.setString(2, namePrefix + "%");

            ResultSet rs = getpersons.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String familienaam = rs.getString("familienaam");
                String voornaam = rs.getString("voornaam");
                persons.add(new Person(id, familienaam, voornaam));
            }
        } catch (Exception e) {
            throw new DataAccessException("Fout bij ophalen van personen met prefix " + namePrefix + ":\n" + e.getMessage());
        }
        return persons;
    }
}
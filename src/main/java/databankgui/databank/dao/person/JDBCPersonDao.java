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

    public boolean existsPerson(String familienaam, String voornaam) throws DataAccessException {
        try (
                PreparedStatement check = prepare("SELECT * FROM personen WHERE familienaam = ? AND voornaam = ?")
        ) {
            check.setString(1, familienaam);
            check.setString(2, voornaam);
            try (ResultSet rs = check.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Person createPerson(String familienaam, String voornaam) throws DataAccessException {
        if (existsPerson(familienaam, voornaam))
            throw new DataAccessException("Person " + familienaam + " " + voornaam + " already exists!");

        Person person;

        try (
                PreparedStatement voegperson = prepare(
                        "INSERT INTO personen (familienaam,voornaam) VALUES (?,?)"
                )
        ) {
            voegperson.setString(1, familienaam);
            voegperson.setString(2, voornaam);
            voegperson.executeUpdate();

            try (ResultSet rs = voegperson.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    person = new Person(rs.getInt(1), familienaam, voornaam);
                } else {
                    throw new RuntimeException("Fout bij genereren van sleutel voor persoon!");
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("Konde persoon niet toevoegen:\n" + e.getMessage());
        }
        return person;
    }


    @Override
    public void updatePerson(int id, String newFamilienaam, String newVoornaam) throws DataAccessException {

        if (existsPerson(newFamilienaam, newVoornaam))
            throw new DataAccessException("Person " + newFamilienaam + " " + newVoornaam + " already exists!");

        try (
                PreparedStatement updateperson = prepare(
                        "UPDATE personen SET familienaam = ?, voornaam = ? WHERE id = ?"
                )
        ) {
            updateperson.setString(1, newFamilienaam);
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
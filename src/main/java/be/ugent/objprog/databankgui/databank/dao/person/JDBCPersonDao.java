package be.ugent.objprog.databankgui.databank.dao.person;

import be.ugent.objprog.databankgui.databank.DataAccessException;
import be.ugent.objprog.databankgui.databank.dao.JDBCAbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCPersonDao extends JDBCAbstractDAO implements PersonDAO {

    public JDBCPersonDao(Connection connection) { super(connection); }

    @Override
    public int createPerson(String name, String firstName) throws DataAccessException {
        int id = -1;
        try (
                PreparedStatement voegperson = prepare(
                        "INSERT INTO personen(familienaam,voornaam) VALUES (?,?)"
                );
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
            e.printStackTrace();
        }
        return id;
    }


    @Override
    public void updatePerson(int id, String name, String firstName) throws DataAccessException {

    }

    @Override
    public void deletePerson(int id) throws DataAccessException {

    }

    @Override
    public Iterable<Person> findPersons(String namePrefix) throws DataAccessException {
        return null;
    }
}
package databankgui.databank.dao.contactcodes;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.JDBCAbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCContactTypeDAO extends JDBCAbstractDAO implements ContactTypeDAO {

    public JDBCContactTypeDAO(Connection connection) {
        super(connection);
    }

    public ContactCode findContactType(String id) throws DataAccessException {
        try (PreparedStatement findpersons = prepare(
                "SELECT * FROM contactcodes WHERE id = ?"
        )) {
            findpersons.setString(1, id);
            ResultSet rs = findpersons.executeQuery();
            if (rs.next()) {
                return new ContactCode(rs.getString("id"), rs.getString("naam"));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error finding contactype (" + id + "): " + e.getMessage());
        }
        return null;
    }

    public List<ContactCode> getAllContactTypes() throws DataAccessException {
        List<ContactCode> contactTypes = new ArrayList<>();
        try (PreparedStatement findpersons = prepare(
                "SELECT * FROM contactcodes"
        )) {
            ResultSet rs = findpersons.executeQuery();
            while (rs.next()) {
                contactTypes.add(new ContactCode(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            throw new DataAccessException("Error getting all contact types: " + e.getMessage());
        }
        return contactTypes;
    }
}
package databankgui.databank.dao.contactcodes;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.JDBCAbstractDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
package be.ugent.objprog.databankgui.databank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class JDBCAbstractDAO {
    private final Connection connection;

    public JDBCAbstractDAO(Connection connection) {
        this.connection = connection;
    }

    protected PreparedStatement prepare(String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    protected Connection getConnection() { return connection; }
}
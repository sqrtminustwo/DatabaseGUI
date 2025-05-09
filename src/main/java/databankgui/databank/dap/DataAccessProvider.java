package databankgui.databank.dap;

import databankgui.databank.dac.DataAccessContext;

import java.sql.SQLException;

public interface DataAccessProvider {
    public DataAccessContext getDataAccessContext() throws SQLException;
}
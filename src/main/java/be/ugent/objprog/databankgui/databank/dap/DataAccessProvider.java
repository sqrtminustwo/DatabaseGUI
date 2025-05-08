package be.ugent.objprog.databankgui.databank.dap;

import be.ugent.objprog.databankgui.databank.dac.DataAccessContext;

import java.sql.SQLException;

public interface DataAccessProvider {
    public DataAccessContext getDataAccessContext() throws SQLException;
}
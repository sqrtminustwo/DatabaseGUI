package be.ugent.objprog.databankgui;

import be.ugent.objprog.databankgui.databank.dap.DataAccessProvider;
import be.ugent.objprog.databankgui.databank.dap.JDBCDataAccessProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DatabankGUIController {

    @FXML
    protected VBox rootVBOX;
    @FXML
    protected TextField inputField;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button addButton;


    public void initialize() {
        DataAccessProvider dataAccessProvider = new JDBCDataAccessProvider("db.properties");

        //TODO: test verwijderen
        try {
            dataAccessProvider.getDataAccessContext().getPersonDAO().createPerson("Lenerd", "Jeffovich");
        } catch (Exception e) {
            System.err.println("Failed to make connection with db:\n" + e.getMessage());
        }
    }

}
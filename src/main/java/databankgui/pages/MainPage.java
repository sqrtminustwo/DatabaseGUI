package databankgui.pages;

import databankgui.databank.dac.DataAccessContext;
import databankgui.databank.dao.person.Person;
import databankgui.databank.dap.JDBCDataAccessProvider;
import databankgui.pages.cells.DeleteButtonCell;
import databankgui.pages.cells.EditButtonCell;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

public class MainPage {

    @FXML
    protected VBox rootVBOX;
    @FXML
    protected TextField inputField;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button addButton;
    @FXML
    protected TableView<Person> table;
    private DataAccessContext jdbcDAC;

    private static class Column<T> {

        private final String title;
        private final double width;
        private String propertyValue;
        private Callback<TableColumn<Person, T>, TableCell<Person, T>> callback;

        public Column(String title, String propertyValue, double width) {
            this.title = title;
            this.propertyValue = propertyValue;
            this.width = width;
        }

        public Column(Callback<TableColumn<Person, T>, TableCell<Person, T>> callback, double width) {
            title = "";
            this.callback = callback;
            this.width = width;
        }

        public TableColumn<Person, T> getColumn() {
            TableColumn<Person, T> newColumn = new TableColumn<>(title);
            if (propertyValue != null) newColumn.setCellValueFactory(new PropertyValueFactory<>(propertyValue));
            else if (callback != null) newColumn.setCellFactory(callback);
            else throw new Error("No cell factory provided!");
            newColumn.setPrefWidth(width);

            return newColumn;
        }
    }

    public <T> void initialize() {
        table.setPrefHeight(500);
        table.setPlaceholder(new Label("No person found"));

        List<Column<T>> toAdd = List.of(
                new Column("Voornaam", "voornaam", 200),
                new Column("Familienaam", "familienaam", 200),
                new Column(param -> new EditButtonCell(), 50),
                new Column(param -> new DeleteButtonCell(), 50)
        );
        toAdd.forEach(column -> table.getColumns().add(column.getColumn()));

        searchButton.setOnMouseReleased(this::searchHandler);
        inputField.setOnKeyReleased(this::searchHandler);

        new AddPage(this, addButton);

        try {
            jdbcDAC = (new JDBCDataAccessProvider("/properties/db.properties")).getDataAccessContext();
            addAllPersons();
        } catch (Exception e) {
            System.err.println("Error connecting to server:\n" + e.getMessage());
        }
    }

    public void searchHandler(Event event) {
        if (event instanceof MouseEvent) addAllPersons();
        else if (event instanceof KeyEvent && ((KeyEvent) event).getCode() == KeyCode.ENTER) addAllPersons();
    }

    public void addPerson(Person person) { table.getItems().add(person); }
    private void addAllPersons() {
        try {
            String prefix = inputField.getText();
            table.getItems().clear();
            for (Person p: jdbcDAC.getPersonDAO().findPersons(prefix)) addPerson(p);
        } catch (Exception e) {
            System.err.println("Error finding people:\n" + e.getMessage());
        }
    }

    public DataAccessContext getJdbcDAC() { return jdbcDAC; }
}
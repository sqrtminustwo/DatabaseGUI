package databankgui.pages;

import databankgui.databank.DataAccessException;
import databankgui.databank.dac.DataAccessContext;
import databankgui.databank.dac.JDBCDataAccessContext;
import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.databank.dap.JDBCDataAccessProvider;
import databankgui.pages.cells.DeletePersonCell;
import databankgui.pages.cells.EditButtonCell;
import databankgui.pages.changepage.AddPage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
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

    public <T> void initialize() {
        table.setPrefHeight(500);
        table.setMaxWidth(515);
        table.setPlaceholder(new Label("No person found"));

        List<Column> toAdd = List.of(
                new Column("Voornaam", "voornaam", 200, false),
                new Column("Familienaam", "familienaam", 200, false),
                new Column(param -> new EditButtonCell(this), 50, false),
                new Column(param -> new DeletePersonCell(this, DeletePersonCell.DeleteTypes.DELETEPERSON), 50, false)
        );
        toAdd.forEach(column -> table.getColumns().add((TableColumn<Person, String>) column.getColumn()));

        searchButton.setOnMouseReleased(this::searchHandler);
        inputField.setOnKeyReleased(this::searchHandler);

        new AddPage(this, addButton);

        try {
            jdbcDAC = (new JDBCDataAccessProvider("/properties/db.properties")).getDataAccessContext();
            addAllPersons();
        } catch (Exception e) {
            System.err.println("Error connecting to server:\n" + e.getMessage());
        }

        try {
            jdbcDAC.getContactDAO().createContact(3254, "E", "t");
        } catch (Exception e) {
            System.err.println("Error creating contact:\n" + e.getMessage());
        }
    }

    public void searchHandler(Event event) {
        if (event instanceof MouseEvent) addAllPersons();
        else if (event instanceof KeyEvent && ((KeyEvent) event).getCode() == KeyCode.ENTER) addAllPersons();
    }

    public void addPerson(String familienaam, String voornaam) {
        try {
            table.getItems().add(
                    jdbcDAC.getPersonDAO().createPerson(familienaam, voornaam)
            );
        } catch (Exception e) {
            System.err.println("Error adding person:\n" + e.getMessage());
        }
    }
    private void addAllPersons() {
        try {
            String prefix = inputField.getText();
            table.getItems().clear();
            for (Person p: jdbcDAC.getPersonDAO().findPersons(prefix)) table.getItems().add(p);
        } catch (Exception e) {
            System.err.println("Error finding people:\n" + e.getMessage());
        }
    }

    public void removePerson(Person person) throws DataAccessException {
        jdbcDAC.getPersonDAO().deletePerson(person.getId());
        table.getItems().remove(person);
    }

    public void removeGegeven(Contact contact) throws DataAccessException {
        jdbcDAC.getContactDAO().deleteContact(contact.getId());
    }

    public List<String> getAllContactTypes() {
        try {
            return jdbcDAC.getContactTypeDAO().getAllContactTypes();
        } catch (Exception e) {
            System.err.println("Error getting all contact types:\n" + e.getMessage());
        }
        return null;
    }

    public List<Contact> getAllPersonContacts(Person person) {
        List<Contact> contacts = new ArrayList<>();
        try {
            for (Contact c: jdbcDAC.getContactDAO().findContacts(person.getId())) {
                c.setCode(jdbcDAC.getContactTypeDAO().findContactType(c.getCode()).naam());
                contacts.add(c);
            }
        } catch (Exception e) {
            System.err.println("Error getting all contact types:\n" + e.getMessage());
        }
        return contacts;
    }

    public void updateContactAdres(Contact contact, String newAdres) {
        try {
            jdbcDAC.getContactDAO().editContact(contact.getId(), newAdres);
        } catch (Exception e) {
            System.err.println("Error setting new adres:\n" + e.getMessage());
        }
    }

}
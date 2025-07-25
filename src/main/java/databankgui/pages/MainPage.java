package databankgui.pages;

import databankgui.databank.DataAccessException;
import databankgui.databank.dac.DataAccessContext;
import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.contactcodes.ContactCode;
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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage {

    @FXML
    protected TextField inputField;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button addButton;
    @FXML
    protected TableView<Person> table;
    private ResourceBundle bundle;
    private DataAccessContext jdbcDAC;

    public void initialize() {
        table.setPrefHeight(500);
        table.setMaxWidth(515);

        table.setPlaceholder(new Label(bundle.getString("emptyTable")));

        List<Column> toAdd = List.of(
                new Column(bundle.getString("nameColumn"), "voornaam", 200, false),
                new Column(bundle.getString("surnameColumn"), "familienaam", 200, false),
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
    }

    public void searchHandler(Event event) {
        if (event instanceof MouseEvent) addAllPersons();
        else if (event instanceof KeyEvent && ((KeyEvent) event).getCode() == KeyCode.ENTER) addAllPersons();
    }

    public void addPerson(String familienaam, String voornaam) throws DataAccessException {
        table.getItems().add(
                jdbcDAC.getPersonDAO().createPerson(familienaam, voornaam)
        );
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

    public List<String> getAllContactTypes() throws DataAccessException {
        return jdbcDAC.getContactTypeDAO().getAllContactTypes().stream().map(ContactCode::naam).toList();
    }

    public List<Contact> getAllPersonContacts(Person person) throws DataAccessException {
        List<Contact> contacts = new ArrayList<>();
        for (Contact c: jdbcDAC.getContactDAO().findContacts(person.getId())) {
            c.setCode(jdbcDAC.getContactTypeDAO().findContactType(c.getCode()).naam());
            contacts.add(c);
        }
        return contacts;
    }

    public void createContactAdres(String contactType, Person person) throws DataAccessException {
        String code = "";
        for (ContactCode contactCode: jdbcDAC.getContactTypeDAO().getAllContactTypes()) {
            if (contactCode.naam().equals(contactType)) {
                code = contactCode.id();
                break;
            }
        }
        jdbcDAC.getContactDAO().createContact(
                person.getId(),
                code,
                ""
        );
    }

    public void updateContactAdres(Contact contact, String newAdres) throws DataAccessException {
        jdbcDAC.getContactDAO().editContact(contact.getId(), newAdres);
    }

    public void updatePerson(Person person, String newFamilienaam, String newVoornaam) {
        try {
            jdbcDAC.getPersonDAO().updatePerson(person.getId(), newFamilienaam, newVoornaam);
            table.getItems().remove(person);
            table.getItems().add(jdbcDAC.getPersonDAO().findPerson(person.getId()));
            table.refresh();
        } catch (DataAccessException e) {
            System.out.println("You are either trying to update person twice or person already exists in database:\n" + e.getMessage());
        }
    }

    public void setBundle(ResourceBundle resources) { this.bundle = resources; }
    public ResourceBundle getBundle() { return bundle; }
}
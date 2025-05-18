package databankgui.pages.changepage;

import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.pages.Column;
import databankgui.pages.MainPage;
import databankgui.pages.cells.DeletePersonCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class EditPage extends ChangePage{

    @FXML
    protected TextField voornaamInput;
    @FXML
    protected TextField familienaamInput;
    @FXML
    protected Button saveButton;
    @FXML
    protected TableView<Contact> gegevensTable;
    @FXML
    protected ChoiceBox<String> choiceBox;
    @FXML
    protected Button addContactButton;
    private Person person;

    public EditPage(MainPage creator, Button editButton) {
        super(creator, "EditPage.fxml", editButton);
    }

    @Override
    public void createWindowLocal() {
        stage.setTitle("Persoonsgegevens");
        voornaamInput.setText(person.getVoornaam());
        familienaamInput.setText(person.getFamilienaam());
        createTable();
        createChoiceBox();
        saveButton.setOnMouseReleased(this::saveGegevens);
        addContactButton.setOnMouseReleased(this::createContact);
    }

    private void saveGegevens(MouseEvent mouseEvent) {
        String newVoornaam = voornaamInput.getText();
        String newFamilienaam = familienaamInput.getText();
        if (!newVoornaam.isEmpty() && !newFamilienaam.isEmpty()) {
            try {
                creator.updatePerson(person, newFamilienaam, newVoornaam);
            } catch (Exception e) {
                System.err.println("Error updating person:\n");
                e.printStackTrace();
            }
            return;
        }
        System.err.println("Voornaam en naam mag niet leeg zijn!");
    }

    private void createContact(MouseEvent mouseEvent) {
        String contactType = choiceBox.getValue();
        System.out.println(contactType);
        if (contactType != null) {
            try {
                creator.createContactAdres(contactType, person);
                fillTable();
            } catch (Exception e) {
                System.err.println("Error creating contact:\n");
                e.printStackTrace();
            }
            return;
        }
        System.err.println("No contact type selected!");
    }

    public void deleteContact(Contact contact) { gegevensTable.getItems().remove(contact); }

    public void createTable() {
        gegevensTable.setEditable(true);

        TableColumn<Contact, String> adresColumn = new Column("Adres", "adres", new StringConverter<String>() {
            @Override
            public String toString(String adres) { return adres != null ? adres : ""; }

            @Override
            public String fromString(String s) { return s; }
        }, 200, true).getColumn();
        adresColumn.setOnEditCommit(event -> {
            Contact contact = event.getRowValue();
            String adres = event.getNewValue();
            try {
                creator.updateContactAdres(contact, adres);
                fillTable();
            } catch (Exception e) {
                System.err.println("Error setting new adres:\n" + e.getMessage());
            }
        });

        gegevensTable.getColumns().addAll(
                new Column("Type", "code", 200, false).getColumn(),
                adresColumn,
                new Column(param -> new DeletePersonCell(creator, DeletePersonCell.DeleteTypes.DELETEGEGEVEN, this), 50, false).getColumn()
        );

        fillTable();
    }

    public void createChoiceBox() {
        try {
            choiceBox.getItems().addAll(creator.getAllContactTypes());
        } catch (Exception e) {
            System.err.println("Error adding choice box items:\n" + e.getMessage());
        }
    }

    public void fillTable() {
        gegevensTable.getItems().clear();
        try {
            gegevensTable.getItems().addAll(creator.getAllPersonContacts(person));
        } catch (Exception e) {
            System.err.println("Error getting all contacts:\n" + e.getMessage());
        }
    }

    public void setPerson(Person person) { this.person = person; }
}
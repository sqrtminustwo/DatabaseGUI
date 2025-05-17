package databankgui.pages.changepage;

import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.pages.Column;
import databankgui.pages.MainPage;
import databankgui.pages.cells.DeletePersonCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.util.List;

public class EditPage extends ChangePage{

    @FXML
    protected TextField voornaamInput;
    @FXML
    protected TextField familienaamInput;
    @FXML
    protected TableView<Contact> gegevensTable;
    @FXML
    protected ChoiceBox<String> choiceBox;
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
    }

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
            creator.updateContactAdres(contact, adres);
            fillTable();
        });

        gegevensTable.getColumns().addAll(
                new Column("Type", "code", 200, false).getColumn(),
                adresColumn,
                new Column(param -> new DeletePersonCell(creator, DeletePersonCell.DeleteTypes.DELETEGEGEVEN, this), 50, false).getColumn()
        );

        fillTable();
    }

    public void createChoiceBox() { choiceBox.getItems().addAll(creator.getAllContactTypes()); }

    public void fillTable() {
        gegevensTable.getItems().clear();
        gegevensTable.getItems().addAll(creator.getAllPersonContacts(person));
    }

    public void setPerson(Person person) { this.person = person; }

    public void deleteContact(Contact contact) { gegevensTable.getItems().remove(contact); }
}
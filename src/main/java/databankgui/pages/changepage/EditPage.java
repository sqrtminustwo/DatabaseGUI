package databankgui.pages.changepage;

import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.pages.Column;
import databankgui.pages.MainPage;
import databankgui.pages.cells.DeletePersonCell;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    public <T> void createTable() {
        List<Column<T>> toAdd = List.of(
                new Column("Type", "code", 200),
                new Column("Adres", "adres", 200),
                new Column(param -> new DeletePersonCell(creator, DeletePersonCell.DeleteTypes.DELETEGEGEVEN), 50)
        );
        toAdd.forEach(column -> gegevensTable.getColumns().add((TableColumn<Contact, ?>) column.getColumn()));
        gegevensTable.getItems().addAll(creator.getAllPersonContacts(person));
    }

    public void createChoiceBox() {
        choiceBox.getItems().addAll(creator.getAllContactTypes());
    }

    public void setPerson(Person person) { this.person = person; }
}
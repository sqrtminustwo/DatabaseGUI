package databankgui.pages.cells;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.pages.MainPage;
import databankgui.pages.changepage.EditPage;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.util.Map;
import java.util.function.Consumer;

public class DeletePersonCell extends ButtonCell {

    public enum DeleteTypes {
        DELETEPERSON,
        DELETEGEGEVEN
    }
    private final DeleteTypes deleteType;
    private EditPage editPage;
    private final Map<DeleteTypes, Consumer<Object>> deleteActions = Map.of(
            DeleteTypes.DELETEPERSON, obj -> {
                try {
                    creator.removePerson((Person) obj);
                } catch (DataAccessException e) {
                    System.out.println("Error removing person: " + ((Person) obj).getId());
                }
            },
            DeleteTypes.DELETEGEGEVEN, obj -> {
                try {
                    creator.removeGegeven((Contact) obj);
                    editPage.deleteContact((Contact) obj);
                } catch (DataAccessException e) {
                    System.out.println("Error removing gegeven: " + ((Contact) obj).getId());
                }
            }
    );

    public DeletePersonCell(MainPage creator, DeleteTypes deleteType) {
        super(creator, "trash.png");
        this.deleteType = deleteType;
        button.setTooltip(new Tooltip(creator.getBundle().getString("deletePersonTooltip")));
    }
    public DeletePersonCell(MainPage creator, DeleteTypes deleteType, EditPage editPage) {
        super(creator, "trash.png");
        this.deleteType = deleteType;
        this.editPage = editPage;
        button.setTooltip(new Tooltip(creator.getBundle().getString("deleteContactTooltip")));
    }

    @Override
    public void handleButtonLocal(Object obj, MouseEvent mouseEvent) {
        deleteActions.get(deleteType).accept(obj);
    }
}
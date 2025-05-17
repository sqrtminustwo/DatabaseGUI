package databankgui.pages.cells;

import databankgui.databank.DataAccessException;
import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dao.person.Person;
import databankgui.pages.MainPage;

import java.util.Map;
import java.util.function.Consumer;

public class DeletePersonCell extends ButtonCell {

    public enum DeleteTypes {
        DELETEPERSON,
        DELETEGEGEVEN
    }
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
                } catch (DataAccessException e) {
                    System.out.println("Error removing gegeven: " + ((Contact) obj).getId());
                }
            }
    );
    private final DeleteTypes deleteType;

    public DeletePersonCell(MainPage creator, DeleteTypes deleteType) {
        super(creator, "trash.png");
        this.deleteType = deleteType;
    }

    @Override
    public void handleButtonLocal(Object obj) { deleteActions.get(deleteType).accept(obj); }
}
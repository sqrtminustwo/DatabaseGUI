package databankgui.pages.cells;

import databankgui.databank.dao.person.Person;
import databankgui.pages.MainPage;
import databankgui.pages.changepage.EditPage;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class EditButtonCell extends ButtonCell {

    public EditButtonCell(MainPage creator) {
        super(creator, "edit.png");
    }

    @Override
    public void handleButtonLocal(Object obj, MouseEvent mouseEvent) {
        EditPage editPage = new EditPage(creator, button);
        editPage.setPerson((Person) obj);
        editPage.createWindow(mouseEvent);

        button.setTooltip(new Tooltip(creator.getBundle().getString("editPersonTooltip")));
    }
}
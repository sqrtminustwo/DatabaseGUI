package databankgui.pages.cells;

import databankgui.databank.dao.person.Person;
import databankgui.pages.MainPage;
import databankgui.pages.changepage.EditPage;
public class EditButtonCell extends ButtonCell {

    public EditButtonCell(MainPage creator) {
        super(creator, "edit.png");
    }

    @Override
    public void handleButtonLocal(Object obj) {
        EditPage editPage = new EditPage(creator, button);
        editPage.setPerson((Person) obj);
        editPage.createWindow(null);
    }
}
package databankgui.pages.cells;

import databankgui.DatabankGUIApplication;
import databankgui.databank.dao.person.Person;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ButtonCell extends TableCell<Person, Button> {

    protected Button button;
    private final ImageView image;

    public ButtonCell(String imageName) {
        this.image = new ImageView(new Image(Objects.requireNonNull(DatabankGUIApplication.class.getResourceAsStream("/icons/" + imageName))));
        button = new Button();
    }

    @Override
    protected void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && getTableRow() != null) {
            Person person = getTableRow().getItem();
            if (person != null) {
                button.setGraphic(image);
                setGraphic(button);
            }
        } else {
            setGraphic(null);
        }
    }

}
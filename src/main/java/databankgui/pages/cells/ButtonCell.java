package databankgui.pages.cells;

import databankgui.DatabankGUIApplication;
import databankgui.databank.dao.person.Person;
import databankgui.pages.MainPage;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public abstract class ButtonCell extends TableCell<Object, Button> {

    protected MainPage creator;
    protected Button button;
    private final ImageView image;

    public ButtonCell(MainPage creator, String imageName) {
        this.creator = creator;
        this.image = new ImageView(new Image(Objects.requireNonNull(DatabankGUIApplication.class.getResourceAsStream("/icons/" + imageName))));
        button = new Button();
        button.setOnMouseReleased(this::handleButton);
    }

    @Override
    protected void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty && getTableRow() != null && getTableRow().getItem() != null) {
            button.setGraphic(image);
            setGraphic(button);
        } else {
            setGraphic(null);
        }
    }

    public void handleButton(MouseEvent mouseEvent) {
        Object obj = getTableRow().getItem();
        if (obj != null) handleButtonLocal(obj);
    }

    public abstract void handleButtonLocal(Object obj);

}
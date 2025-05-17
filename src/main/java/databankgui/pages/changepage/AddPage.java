package databankgui.pages.changepage;

import databankgui.pages.MainPage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddPage extends ChangePage {

    @FXML
    protected TextField voornaamInput;
    @FXML
    protected TextField familienaamInput;
    @FXML
    protected Button createButton;
    @FXML
    protected Button cancelButton;

    public AddPage(MainPage creator, Button addButton) {
        super(creator, "AddPage.fxml", addButton);
    }

    private void createPerson(MouseEvent mouseEvent) {
        try {
            creator.addPerson(familienaamInput.getText(), voornaamInput.getText());
            closeWindow(mouseEvent);
        } catch (Exception e) {
            System.err.println("Couldn't create person:\n" + e.getMessage());
        }
    }

    @Override
    public void createWindowLocal() {
        stage.setMinHeight(200); stage.setMinWidth(400);
        stage.setMaxHeight(200); stage.setMaxWidth(400);
        stage.setTitle("Niuwe persoon");
        createButton.setOnMouseReleased(this::createPerson);
        cancelButton.setOnMouseReleased(this::closeWindow);
    }
}
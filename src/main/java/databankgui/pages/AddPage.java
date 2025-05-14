package databankgui.pages;

import databankgui.DatabankGUIApplication;
import databankgui.databank.dao.person.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPage {

    private Stage stage;
    private final MainPage creator;
    @FXML
    protected TextField voornaamInput;
    @FXML
    protected TextField familienaamInput;
    @FXML
    protected Button createButton;
    @FXML
    protected Button cancelButton;

    public AddPage(MainPage creator, Button addButton) {
        this.creator = creator;
        addButton.setOnMouseReleased(this::createWindow);
    }

    private void createPerson(MouseEvent mouseEvent) {
        try {
            Person person = creator.getJdbcDAC().getPersonDAO().createPerson(familienaamInput.getText(), voornaamInput.getText());
            creator.addPerson(person);
            closeCreation(mouseEvent);
        } catch (Exception e) {
            System.err.println("Couldn't create person:\n" + e.getMessage());
        }
    }

    private void closeCreation(MouseEvent mouseEvent) { stage.close(); }

    private void createWindow(MouseEvent ignore) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(DatabankGUIApplication.class.getResource("/fxml/AddPage.fxml"));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.setMinHeight(200); stage.setMinWidth(400);

            stage.setTitle("Niuwe persoon");
            stage.setScene(scene);
            stage.show();

            createButton.setOnMouseReleased(this::createPerson);
            cancelButton.setOnMouseReleased(this::closeCreation);
        } catch (IOException e) {
            System.err.println("Failed to create new Window:\n");
            e.printStackTrace();
        }
    }
}
package databankgui.pages.changepage;

import databankgui.DatabankGUIApplication;
import databankgui.pages.MainPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ChangePage {

    protected final MainPage creator;
    private final String fxml;
    protected Stage stage;

    public ChangePage(MainPage creator,  String fxml, Button createButton) {
        this.creator = creator;
        this.fxml = fxml;
        createButton.setOnMouseReleased(this::createWindow);
    }

    public void createWindow(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(DatabankGUIApplication.class.getResource("/fxml/" + fxml));
            fxmlLoader.setController(this);

            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.setScene(scene);

            createWindowLocal();

            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to create new window:\n");
            e.printStackTrace();
        }
    }
    public abstract void createWindowLocal();

    public void closeWindow(MouseEvent ignore) { stage.close(); }
}
package be.ugent.objprog.databankgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DatabankGUIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DatabankGUIApplication.class.getResource("DatabankGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 600);
        stage.setTitle("DatabankGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(Objects.requireNonNull(DatabankGUIApplication.class.getResource("style.css")).toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
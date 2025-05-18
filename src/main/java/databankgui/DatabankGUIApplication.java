package databankgui;

import atlantafx.base.theme.PrimerDark;
import databankgui.pages.MainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DatabankGUIApplication extends Application {

    static List<String> supportedLanguages = List.of(
            "en",
            "nl"
    );

    @Override
    public void start(Stage stage) throws IOException {
        String langCode = getParameters().getRaw().getFirst();  // "nl" or "en"
        ResourceBundle bundle = ResourceBundle.getBundle("i18n_" + langCode, Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(
                DatabankGUIApplication.class.getResource("/fxml/MainPage.fxml"),
                bundle
        );
        MainPage mainpageController = new MainPage();
        mainpageController.setBundle(bundle);
        fxmlLoader.setController(mainpageController);

        Scene scene = new Scene(fxmlLoader.load(), 550, 600);
        stage.setTitle("DatabankGUI");
        stage.setScene(scene);
        stage.setResizable(false);
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length != 1 || !supportedLanguages.contains(args[0])) {
            throw new RuntimeException("Langague should be provided (en/nl)");
        }
        launch(args[0]);
    }
}
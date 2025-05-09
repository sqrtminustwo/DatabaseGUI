module databankgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens databankgui to javafx.fxml;
    exports databankgui;
}
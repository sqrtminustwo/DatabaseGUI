module databankgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens databankgui to javafx.fxml;
    opens databankgui.databank.dao.person to javafx.base;
    opens databankgui.pages to javafx.fxml;
    exports databankgui;
}
module databankgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires atlantafx.base;

    opens databankgui to javafx.fxml;
    opens databankgui.databank.dao.person to javafx.base;
    opens databankgui.databank.dao.contact to javafx.base;
    opens databankgui.pages to javafx.fxml;
    exports databankgui;
    opens databankgui.pages.changepage to javafx.fxml;
}
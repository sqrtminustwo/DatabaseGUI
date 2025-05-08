module be.ugent.objprog.databankgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens be.ugent.objprog.databankgui to javafx.fxml;
    exports be.ugent.objprog.databankgui;
}
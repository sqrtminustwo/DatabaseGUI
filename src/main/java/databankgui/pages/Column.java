package databankgui.pages;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class Column<T> {

    private final String title;
    private final double width;
    private String propertyValue;
    private Callback<TableColumn<T, T>, TableCell<T, T>> callback;

    public Column(String title, String propertyValue, double width) {
        this.title = title;
        this.propertyValue = propertyValue;
        this.width = width;
    }

    public Column(Callback<TableColumn<T, T>, TableCell<T, T>> callback, double width) {
        title = "";
        this.callback = callback;
        this.width = width;
    }

    public TableColumn<T, T> getColumn() {
        TableColumn<T, T> newColumn = new TableColumn<>(title);
        if (propertyValue != null) newColumn.setCellValueFactory(new PropertyValueFactory<>(propertyValue));
        else if (callback != null) newColumn.setCellFactory(callback);
        else throw new Error("No cell factory provided!");
        newColumn.setPrefWidth(width);

        return newColumn;
    }
}
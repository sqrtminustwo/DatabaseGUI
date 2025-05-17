package databankgui.pages;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Column<S, T> {

    private final String title;
    private final double width;
    private final boolean editable;
    private String propertyValue;
    private Callback<TableColumn<S, T>, TableCell<S, T>> callback;
    private StringConverter<T> converter;

    public Column(String title, String propertyValue, double width, boolean editable) {
        this.title = title;
        this.propertyValue = propertyValue;
        this.width = width;
        this.editable = editable;
    }

    public Column(String title, String propertyValue, StringConverter<T> converter, double width, boolean editable) {
        this.title = title;
        this.propertyValue = propertyValue;
        this.converter = converter;
        this.width = width;
        this.editable = editable;
    }

    public Column(Callback<TableColumn<S, T>, TableCell<S, T>> callback, double width, boolean editable) {
        this.title = "";
        this.callback = callback;
        this.width = width;
        this.editable = editable;
    }

    public TableColumn<S, T> getColumn() {
        TableColumn<S, T> newColumn = new TableColumn<>(title);
        if (editable) {
            if (converter != null) {
                newColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
            } else {
                throw new Error("Editable column should have a converter!");
            }
        }
        if (propertyValue != null) newColumn.setCellValueFactory(new PropertyValueFactory<>(propertyValue));
        else if (callback != null) newColumn.setCellFactory(callback);
        else throw new Error("No cell factory provided!");
        newColumn.setPrefWidth(width);
        newColumn.setEditable(editable);

        return newColumn;
    }
}
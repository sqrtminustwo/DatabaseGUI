package databankgui.databank.dao.person;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Moet met getters voor table (geen record)
 */
public class Person {

    private final SimpleObjectProperty id = new SimpleObjectProperty();
    private final SimpleStringProperty familienaam = new SimpleStringProperty();
    private final SimpleStringProperty voornaam = new SimpleStringProperty();

    public Person(Integer id, String familienaam, String voornaam) {
        setId(id);
        setFamilienaam(familienaam);
        setVoornaam(voornaam);
    }

    public Integer getId() { return (Integer) id.getValue(); }
    public String getFamilienaam() { return familienaam.getValue(); }
    public String getVoornaam() { return voornaam.getValue(); }

    public void setId(Integer id) { this.id.set(id); }
    public void setFamilienaam(String familienaam) { this.familienaam.set(familienaam); }
    public void setVoornaam(String voornaam) { this.voornaam.set(voornaam); }
}
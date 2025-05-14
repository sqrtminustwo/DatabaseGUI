package databankgui.databank.dao.person;

/**
 * Moet mget getters voor table (niet record)
 */
public class Person {

    private final int id;
    private final String familienaam;
    private final String voornaam;

    public Person(int id, String familienaam, String voornaam) {
        this.id = id;
        this.familienaam = familienaam;
        this.voornaam = voornaam;
    }

    public int getId() { return id; }
    public String getFamilienaam() { return familienaam; }
    public String getVoornaam() { return voornaam; }
}
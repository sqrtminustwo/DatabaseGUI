package databankgui.databank.dao.contact;

public class Contact {

    private int id;
    private int p_id;
    private String code;
    private String adres;

    public Contact(int id, int p_id, String code, String adres) {
        this.id = id;
        this.p_id = p_id;
        this.code = code;
        this.adres = adres;
    }

    public int getId() { return id; }
    public int getP_id() { return p_id; }
    public String getCode() { return code; }
    public String getAdres() { return adres; }

    public void setId(int id) { this.id = id; }
    public void setP_id(int p_id) { this.p_id = p_id; }
    public void setCode(String code) { this.code = code; }
    public void setAdres(String adres) { this.adres = adres; }

}
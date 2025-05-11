package databankgui;


import databankgui.databank.dao.contact.Contact;
import databankgui.databank.dap.DataAccessProvider;
import databankgui.databank.dap.JDBCDataAccessProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DatabankGUIController {

    @FXML
    protected VBox rootVBOX;
    @FXML
    protected TextField inputField;
    @FXML
    protected Button searchButton;
    @FXML
    protected Button addButton;


    public void initialize() {
        DataAccessProvider dataAccessProvider = new JDBCDataAccessProvider("/db.properties");

        //TODO: test verwijderen
//        try {
//            dataAccessProvider.getDataAccessContext().getPersonDAO().createPerson("Lenerd", "Jeffovich");
//            System.out.println("Person created");
//        } catch (Exception e) {
//            System.err.println("Failed to make connection with db:\n" + e.getMessage());
//        }

//        try {
//            dataAccessProvider.getDataAccessContext().getPersonDAO().updatePerson(3155, "Yasser", "Zoubari");
//            System.out.println("Person updated");
//        } catch (Exception e) {
//            System.err.println("Failed to make connection with db:\n" + e.getMessage());
//        }
//        try {
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(2952);
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(2953);
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(2954);
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(3052);
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(3152);
//            dataAccessProvider.getDataAccessContext().getPersonDAO().deletePerson(3153);
//        } catch (Exception e) {
//            System.err.println("Failed to make connection with db:\n" + e.getMessage());
//        }
//            try {
//                Iterable<Person> persons = dataAccessProvider.getDataAccessContext().getPersonDAO().findPersons("Jan");
//                for (Person person : persons) {
//                    System.out.println(person);
//                }
//            } catch (Exception e) {
//                System.err.println("Failed to make connection with db:\n" + e.getMessage());
//            }
//        try {
//            System.out.println(dataAccessProvider.getDataAccessContext().getContactTypeDAO().findContactType("M"));
//        } catch (Exception e) {
//            System.err.println("Failed to make connection with db:\n" + e.getMessage());
//        }
        try {
//            dataAccessProvider.getDataAccessContext().getContactDAO().createContact(101, "M", "0470/12 34 56");
//            dataAccessProvider.getDataAccessContext().getContactDAO().createContact(101, "E", "griet.janssens@ugent.be");

            for (Contact contact:dataAccessProvider.getDataAccessContext().getContactDAO().findContactsByType(101, "M")) {
                System.out.println(contact);
            }
        } catch (Exception e) {
            System.err.println("Failed to make connection with db:\n" + e.getMessage());
        }
    }

}
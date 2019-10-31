/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vlkpa
 */
public class ValidationFormController implements Initializable {

    @FXML
    private Label pathXML;

    @FXML
    private Label pathXSD;

    File selectedXsdFile;
    File selectedXmlFile;
    private static final String workingDirectoryPath = System.getProperty("user.dir");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loadXML() {
        Alert alert = new Alert(AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));

        selectedXmlFile = fileChooser.showOpenDialog(null);

        if (selectedXmlFile != null) {
            String path = selectedXmlFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xml")) {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XML");
                alert.showAndWait();
                selectedXmlFile = null;
            } else {
                pathXML.setText(path);
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("XML súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadXSD() {
        Alert alert = new Alert(AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSD súbor (*.xsd)", "*.xsd"));

        selectedXsdFile = fileChooser.showOpenDialog(null);
        if (selectedXsdFile != null) {
            String path = selectedXsdFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xsd")) {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XSD");
                alert.showAndWait();
                selectedXsdFile = null;
            } else {
                pathXSD.setText(path);
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("XSD súbor sa nenašiel");
            alert.showAndWait();
        }
    }


    @FXML
    private void validateXmlXsd() {
        if (selectedXmlFile == null || selectedXsdFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Generácia sa nezdarila");
            alert.setContentText("Pre validovanie je potrebné vybrať súbor XML a XSD");
            alert.showAndWait();
        } else {
            validateXMLwithXSD(selectedXmlFile, selectedXsdFile);
        }
    }

    //https://howtodoinjava.com/jaxb/xsd-schema-validation/
    private static void validateXMLwithXSD(File xmlFile, File xsdFile) {
        JAXBContext jaxbContext;
        try {
            //Get JAXBContext
            jaxbContext = JAXBContext.newInstance(Person.class);

            //Create Unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //Setup schema validator
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema personSchema = sf.newSchema(xsdFile);
            jaxbUnmarshaller.setSchema(personSchema);

            //Unmarshal xml file
            Person person = (Person) jaxbUnmarshaller.unmarshal(xmlFile);

            person.printPerson();

            Alert errorAlert = new Alert(AlertType.INFORMATION);
            errorAlert.setHeaderText("XML je validné voči XSD");
            errorAlert.setContentText("Vybraný XML súbor je správny");
            errorAlert.showAndWait();
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("XML nie je validné voči XSD");
            errorAlert.setContentText("Vybraný XML súbor je chybný");
            errorAlert.showAndWait();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javafx.scene.control.Alert;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;

import javax.xml.bind.*;
import javax.xml.validation.*;

import java.io.File;

/**
 * @author vlkpa
 */
public class ValidationXml_Xsd {

    //https://howtodoinjava.com/jaxb/xsd-schema-validation/
    public static void validateXML_XSD(File xmlFile, File xsdFile) {
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

            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("XML je validné voči XSD");
            errorAlert.setContentText("Vybraný XML súbor je správny");
            errorAlert.showAndWait();
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("XML nie je validné voči XSD");
            errorAlert.setContentText("Vybraný XML súbor je chybný");
            errorAlert.showAndWait();
        }
    }
}

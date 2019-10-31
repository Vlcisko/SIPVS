/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vlkpa
 */
public class HTMLGeneratorFormController implements Initializable {

    @FXML
    private Label pathXML;

    @FXML
    private Label pathXSL;

    File selectedXslFile;
    File selectedXmlFile;
    String workingDirectoryPath = System.getProperty("user.dir");


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void loadXML() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));

        selectedXmlFile = fileChooser.showOpenDialog(null);

        if (selectedXmlFile != null) {
            String path = selectedXmlFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xml")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XML");
                alert.showAndWait();
                selectedXmlFile = null;
            } else {
                pathXML.setText(path);
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XML súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadXSL() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSL súbor (*.xsl)", "*.xsl"));
        selectedXslFile = fileChooser.showOpenDialog(null);
        if (selectedXslFile != null) {
            String path = selectedXslFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xsl")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XSL");
                alert.showAndWait();
                selectedXslFile = null;
            } else {
                pathXSL.setText(path);
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XSL súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void saveHTML() {
        if (selectedXmlFile == null || selectedXslFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Generácia sa nezdarila");
            alert.setContentText("Pre generovanie HTML je potrebné vybrať súbor XML a XSL");
            alert.showAndWait();
        } else {
            createHTML(selectedXmlFile, selectedXslFile);
        }
    }

    public void createHTML(File selectedXmlFile, File selectedXslFile) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            //       Source xslDoc = new StreamSource("C:/Users/vlkpa/OneDrive/Desktop/person.xsl");
            //         Source xslDoc = new StreamSource("C:/Users/vlkpa/OneDrive/Desktop/person.xsl");
            Source xmlDoc = new StreamSource(selectedXmlFile);
            Source xslDoc = new StreamSource(selectedXslFile);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(workingDirectoryPath));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML súbor (*.html)", "*.html"));
            File saveXmlFile = fileChooser.showSaveDialog(null);


            if (saveXmlFile != null) {
                String outputFileName = saveXmlFile.getAbsolutePath();
                OutputStream htmlFile = new FileOutputStream(outputFileName);
                Transformer trasform = tFactory.newTransformer(xslDoc);


                trasform.transform(xmlDoc, new StreamResult(htmlFile));

                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("HTML súbor bol vytvorený");
                errorAlert.setContentText("Bol vytvorený súbor z poskytnutého XML a XSL");
                errorAlert.showAndWait();
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}

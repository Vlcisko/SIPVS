/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author vlkpa
 */
public class HtmlCreator {
    public void createHTML(File selectedXmlFile, File selectedXslFile) {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            //       Source xslDoc = new StreamSource("C:/Users/vlkpa/OneDrive/Desktop/person.xsl");
            //         Source xslDoc = new StreamSource("C:/Users/vlkpa/OneDrive/Desktop/person.xsl");
            Source xmlDoc = new StreamSource(selectedXmlFile);
            Source xslDoc = new StreamSource(selectedXslFile);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Main.workingDirectoryPath));
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

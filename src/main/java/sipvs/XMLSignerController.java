package sipvs;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import sipvs.DSigNetWrapper.DSigNETWrapper;
import sipvs.DSigNetWrapper.DSigNETXmlPluginWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class XMLSignerController {


    File selectedXslFile;
    File selectedXmlFile;
    File selectedXsdFile;

    public void signFiles(File xmlFile, File xsdFile, File xslFile) throws Exception {

        //if(selectedXmlFile != null && selectedXsdFile != null && selectedXslFile != null) {
        if (true) {

            String pathXML = xmlFile.getAbsolutePath().toString();
            String pathXSD = xsdFile.getAbsolutePath().toString();
            String pathXSL = xslFile.getAbsolutePath().toString();

            DSigNETWrapper dsigXades = new DSigNETWrapper();
            DSigNETXmlPluginWrapper xmlPlugin = new DSigNETXmlPluginWrapper();

            Object xmlObject = xmlPlugin.CreateObject2("id",
                    "XML",
                    readFile(pathXML),
                    readFile(pathXSD),
                    "",
                    "https://something.com/example.xsd",
                    readFile(pathXSL),
                    "https://something.com/example.xsl",
                    "HTML"
            );

            if (xmlObject == null) {
                showAlert("Chyba pri načítaní súborov: " + xmlPlugin.getErrorMessage());
                return;
            }

            int resOfAdd = dsigXades.AddObject(xmlObject);

            if (resOfAdd != 0) {
                showAlert("Chyba pri spúštaní DSig.Xades: " + dsigXades.getErrorMessage());
                return;
            }

            int resOfSign = dsigXades.Sign("SIPVS_Ditec", "sha256", "urn:oid:1.3.158.36061701.1.2.2");

            if (resOfSign != 0) {
                showAlert("Chyba pri prihlasovaní DSig.Xades: " + dsigXades.getErrorMessage());
                return;
            } else {
                showAlert("XML úspešne podpísané, riadenie programu bude presmerované. Zvoľte lokáciu pre uloženie súboru.");
                saveSignedXML(dsigXades.getSignedXmlWithEnvelope());
            }
        } else {
            showAlert("Neboli vybrané správne súbory .xml, .xsd a .xsl(t).");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private String readFile(String path) {

        byte[] encoded;

        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return new String(encoded, Charset.defaultCharset());
    }

    private void saveSignedXML(String xmlFile) {
        if (xmlFile == null) {
            showAlert("Nemožno uložiť prázdny dokument.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Main.workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));
        File saveXmlFile = fileChooser.showSaveDialog(null);

        BufferedWriter bWriter = null;
        String outputFileName = saveXmlFile.getAbsolutePath();
        try {
            bWriter = new BufferedWriter(new FileWriter(outputFileName));
            bWriter.write(xmlFile);
            return;
        } catch (IOException ex) {
            showAlert("Nie je možné uložiť súbor: " + ex);
        } finally {
            if (bWriter != null) {
                try {
                    bWriter.close();
                    showAlert("Súbor bol úspešné uložený.Nájdete ho v: " + outputFileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

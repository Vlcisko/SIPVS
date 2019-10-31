package sipvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import sipvs.DSigNetWrapper.DSigNETWrapper;
import sipvs.DSigNetWrapper.DSigNETXmlPluginWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class XMLSignerController implements Initializable {

    @FXML
    private Label pathXML = null;

    @FXML
    private Label pathXSL = null;

    @FXML
    private Label pathXSD = null;

    File selectedXslFile;
    File selectedXmlFile;
    File selectedXsdFile;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));

        selectedXmlFile = fileChooser.showOpenDialog(null);

        if (selectedXmlFile != null) {
            String path = selectedXmlFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xml")) {
                showAlert("Vložte súbor s príponou XML");
                selectedXmlFile = null;
            } else {
                pathXML.setText(path);
            }
        } else {
            showAlert("XML súbor sa nenašiel");
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
                showAlert("Vložte súbor s príponou XSL");
                selectedXslFile = null;
            } else {
                pathXSL.setText(path);
            }
        } else {
            showAlert("XSL súbor sa nenašiel");
        }
    }

    @FXML
    private void loadXSD() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(workingDirectoryPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSD súbor (*.xsd)", "*.xsd"));


        selectedXsdFile = fileChooser.showOpenDialog(null);

        if (selectedXsdFile != null) {
            String path = selectedXsdFile.getAbsolutePath();
            if (!path.substring(path.length() - 3).equals("xsd")) {
                showAlert("Vložte súbor s príponou XSD");
                selectedXsdFile = null;
            } else {
                pathXSD.setText(path);
            }
        } else {
            showAlert("XSD súbor sa nenašiel");
        }
    }

    @FXML
    public void signFiles() throws Exception {

        //if(selectedXmlFile != null && selectedXsdFile != null && selectedXslFile != null) {
        if (true) {
            //testovacie data
            String workingDirectory = System.getProperty("user.dir");
            pathXML.setText(workingDirectory + "\\data\\test.xml");
            pathXSD.setText(workingDirectory + "\\data\\test.xsd");
            pathXSL.setText(workingDirectory + "\\data\\test.xsl");

            DSigNETWrapper dsigXades = new DSigNETWrapper();
            DSigNETXmlPluginWrapper xmlPlugin = new DSigNETXmlPluginWrapper();

            Object xmlObject = xmlPlugin.CreateObject2("id",
                    "XML",
                    readFile(pathXML.getText()),
                    readFile(pathXSD.getText()),
                    "",
                    "https://something.com/example.xsd",
                    readFile(pathXSL.getText()),
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

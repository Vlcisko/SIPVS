package sipvs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class XMLSignerController implements Initializable {

    @FXML
    private Label pathXML;

    @FXML
    private Label pathXSL;

    @FXML
    private Label pathXSD;

    File selectedXslFile;
    File selectedXmlFile;
    File selectedXsdFile;
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));

        selectedXmlFile = fileChooser.showOpenDialog(null);

        if(selectedXmlFile != null){
            String path = selectedXmlFile.getAbsolutePath().toString();
            if(!path.substring(path.length() - 3).equals("xml")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XML");
                alert.showAndWait();
                selectedXmlFile = null;
            }else {
                pathXML.setText(path);
            }
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XML súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadXSL() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSL súbor (*.xsl)", "*.xsl"));
        selectedXslFile = fileChooser.showOpenDialog(null);
        if(selectedXslFile != null){
            String path = selectedXslFile.getAbsolutePath().toString();
            if(!path.substring(path.length() - 3).equals("xsl")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XSL");
                alert.showAndWait();
                selectedXslFile = null;
            }else {
                pathXSL.setText(path);
            }
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XSL súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadXSD() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSD súbor (*.xsd)", "*.xsd"));

        selectedXmlFile = fileChooser.showOpenDialog(null);

        if(selectedXmlFile != null){
            String path = selectedXmlFile.getAbsolutePath().toString();
            if(!path.substring(path.length() - 3).equals("xsd")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XSD");
                alert.showAndWait();
                selectedXmlFile = null;
            }else {
                pathXML.setText(path);
            }
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XSD súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    public void signFiles() {
    }

}

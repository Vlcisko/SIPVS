package main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class LoadFilesFormController implements Initializable {

    @FXML
    private Label pathXML;

    @FXML
    private Label pathXSD;

    @FXML
    private Label pathXSL;


    ReadOnlyObjectWrapper<File> selectedXmlFileObjW = new ReadOnlyObjectWrapper<>();
    ReadOnlyObjectWrapper<File> selectedXslFileObjW = new ReadOnlyObjectWrapper<>();
    ReadOnlyObjectWrapper<File> selectedXsdFileObjW = new ReadOnlyObjectWrapper<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void loadXML() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        File selectedXmlFile;

        FileChooser fileChooser = new FileChooser();
        String currentPath = Main.workDirPath + "\\data";
        fileChooser.setInitialDirectory(new File(currentPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));

        selectedXmlFile = fileChooser.showOpenDialog(new Stage());
        setXmlFileObjProperty(selectedXmlFile);

        if (selectedXmlFile != null) {
            String path = selectedXmlFile.getAbsolutePath().toString();
            if (!path.substring(path.length() - 3).equals("xml")) {

                alert = new Alert(Alert.AlertType.INFORMATION);
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
    private void loadXSD() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        File selectedXsdFile;

        FileChooser fileChooser = new FileChooser();
        String currentPath = Main.workDirPath + "\\data";
        fileChooser.setInitialDirectory(new File(currentPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSD súbor (*.xsd)", "*.xsd"));

        selectedXsdFile = fileChooser.showOpenDialog(new Stage());
        setXsdFileObjProperty(selectedXsdFile);

        if (selectedXsdFile != null) {
            String path = selectedXsdFile.getAbsolutePath().toString();
            if (!path.substring(path.length() - 3).equals("xsd")) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Vložte súbor s príponou XSD");
                alert.showAndWait();
                selectedXsdFile = null;
            } else {
                pathXSD.setText(path);
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("XSD súbor sa nenašiel");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadXSL() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        File selectedXslFile;

        FileChooser fileChooser = new FileChooser();
        String currentPath = Main.workDirPath + "\\data";
        fileChooser.setInitialDirectory(new File(currentPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XSL súbor (*.xsl)", "*.xsl"));

        selectedXslFile = fileChooser.showOpenDialog(new Stage());
        setXslFileObjProperty(selectedXslFile);

        if (selectedXslFile != null) {
            String path = selectedXslFile.getAbsolutePath().toString();
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
    public void closeForm(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public ReadOnlyObjectProperty<File> currentXmlFileObjProperty() {
        return selectedXmlFileObjW.getReadOnlyProperty();
    }

    public File getCurrentXmlFile() {
        return selectedXmlFileObjW.get();
    }

    public void setXmlFileObjProperty(File file) {
        selectedXmlFileObjW.set(file);
        pathXML.setText(file.getAbsolutePath());
    }


    public ReadOnlyObjectProperty<File> currentXsdFileObjProperty() {
        return selectedXsdFileObjW.getReadOnlyProperty();
    }

    public File getCurrentXsdFile() {
        return selectedXsdFileObjW.get();
    }

    public void setXsdFileObjProperty(File file) {
        selectedXsdFileObjW.set(file);
        pathXSD.setText(file.getAbsolutePath());
    }


    public ReadOnlyObjectProperty<File> currentXslFileObjProperty() {
        return selectedXslFileObjW.getReadOnlyProperty();
    }

    public File getCurrentXslFile() {
        return selectedXslFileObjW.get();
    }

    public void setXslFileObjProperty(File file) {
        selectedXslFileObjW.set(file);
        pathXSL.setText(file.getAbsolutePath());
    }

}

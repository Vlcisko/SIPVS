package sipvs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author vlkpa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane root;

    //person firstName, lastName, date of birth
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField PersonID = new TextField();
    private DatePicker birthDate = new DatePicker();
    
    //RadioButtons for person gender
    private RadioButton femaleRB = new RadioButton("Žena");
    private RadioButton maleRB = new RadioButton("Muž");
    private ToggleGroup genderTG = new ToggleGroup();
    
    //RadioButtons for person status
    private RadioButton marriedRB = new RadioButton("Ženatý");
    private RadioButton singleRB =new RadioButton("Slobodný");
    private ToggleGroup statusTG = new ToggleGroup();
    
    //box for all fields = Person and Childrens
    private VBox fieldsPanel = new VBox();
    
    //list of person boxes 
    private List<HBox> fieldsPerson = new ArrayList<HBox>();
    
    //list of children boxes
    private List<HBox> fieldsChildren = new ArrayList<HBox>();
    
    //box just for children fields
    private VBox childrenPanel = new VBox();
    
    //list of children textFields
    public List<TextField[]> children = new ArrayList<TextField[]>();
    
    //number of children
    public ComboBox<Item> choices = new ComboBox<>();
    
    //save and validate buttons
    private Button saveButton = new Button();
    private Button validateButton = new Button();
    private Button gnerateHTMLButton = new Button();
    
    //new person
    private Person person = new Person();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //person for test purposes
        Child ondrej = new Child("Ondrej", "Julius");
        Child lucia = new Child("Lucia", "Juliusová");        
        List<Child> children = new ArrayList<Child>();
        children.add(ondrej);
        children.add(lucia);      
        Person tomas = new Person("Tomas", "Julius","2019-10-18","zenaty", "muz", "HR123456" ,children);
        
        //create fields for new person
        createFields(person);        
    }  
    
    private void saveXml(ActionEvent event) {  
        Boolean areChildrenValid = true;
        Boolean isPersonIDValid = true;
        
        person.setFirstName(firstName.getText());
        person.setLastName(lastName.getText());
        
        
        //if(PersonID.getText().equals("HRXXXXXX") || !PersonID.getText().matches("^[a-zA-Z0-9]{8}")){
        if(PersonID.getText().equals("HRXXXXXX")){
            PersonID.setStyle("-fx-background-color: red; -fx-padding: 5;");
            isPersonIDValid = false;                     
        }else{
            person.setPersonID(PersonID.getText()); 
        }
        
        for(TextField[] child : children){        
            TextField childFirstName = child[0]; 
            TextField childLastName = child[1]; 
            Child newChild = new Child(childFirstName.getText(), childLastName.getText());           
            if(isChildValid(child)){
                person.setChild(newChild);
            }else {
                areChildrenValid = false;
            }
        }
            
        person.setGender(getToggleSelected(genderTG, "muz", "zena"));
        person.setStatus(getToggleSelected(statusTG, "slobodny", "zenaty"));
         
        if(birthDate.getValue() != null){
            person.setBirthDate(birthDate.getValue().toString());
        }
        
                    
        if(isPersonValid(fieldsPerson) && areChildrenValid && isPersonIDValid){      
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml file (*.xml)", "*.xml"));
            File saveXmlFile = fileChooser.showSaveDialog((Stage) root.getScene().getWindow());
            person.printPerson();
            if(saveXmlFile != null){
                jaxbObjectToXML(person, saveXmlFile);
                System.err.println("***********XML sa uložilo**************");
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setHeaderText("Formulár bol úspešne uložený");
                infoAlert.setContentText("Súbor: "+ saveXmlFile.toString());               
                infoAlert.showAndWait();
            }
        }else{
            System.err.println("***********Chybne vyplnené polia**************");
            Alert errorAlert = new Alert(AlertType.WARNING);
            errorAlert.setHeaderText("Formulár nie je správne vyplnený");
            errorAlert.setContentText("Všetky polia označené červenou farbou musia byť vyplnené");
            errorAlert.showAndWait();
        }
    }
    
    private void validateXmlXsd(ActionEvent event) {  
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ValidationForm.fxml"));
            Parent validationRoot = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Validácia XML-XSD");
            stage.setScene(new Scene(validationRoot));
            stage.show();
        } catch (IOException e) {
                e.printStackTrace();
        }             
    }
    
    
    private void showXSLTfromXML(ActionEvent event) {         
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HTMLGeneratorForm.fxml"));
            Parent validationRoot = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Generovanie HTML");
            stage.setScene(new Scene(validationRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }                       
    }
    
    
    public String getToggleSelected(ToggleGroup tg, String text1, String text2){
        if(tg.getSelectedToggle() == null){
            return "";
        }else {
            return tg.getToggles().indexOf(tg.getSelectedToggle()) == 0 ? text1: text2;
        }          
    }
    
    public Boolean isStringOnlyAlphabet(String fieldText){
        return((!fieldText.equals("")) 
              && (fieldText != null) 
              && (fieldText.matches("^[a-zA-Z\\u00C0-\\u01DC]*$")));
    }

    public Boolean isFieldValid(HBox hb){
        Boolean valid = true;
        for(Node node: hb.getChildren()){
             if(node instanceof TextField){
            	 System.out.println(((TextField)node).getText());
// zrusil som validaciu lebo ved preco to dat ne komplet text fieldy?            	 
                 if(!isStringOnlyAlphabet(((TextField)node).getText())){                         
                    ((TextField)node).setStyle("-fx-background-color: red; -fx-padding: 5;");
                    valid = false;
                 }
             }
             if(node instanceof DatePicker){
                 if(((DatePicker)node).getValue() == null){ 
                    ((DatePicker)node).setStyle("-fx-background-color: red; -fx-padding: 5;");
                    valid = false;
                 }
             }
             if(node instanceof RadioButton){
                 if(((RadioButton) node).getToggleGroup().getSelectedToggle() == null){
                    node.setStyle("-fx-background-color: red; -fx-padding: 5;");
                    valid = false;
                 }
             }
        }
        return valid;
    }           
    public Boolean isChildValid(TextField[] child){
        Boolean valid = true;
        if(!isStringOnlyAlphabet(child[0].getText())){                           
            child[0].setStyle("-fx-background-color: red; -fx-padding: 5;");
            valid = false;
        }
        if(!isStringOnlyAlphabet(child[1].getText())){                           
            child[1].setStyle("-fx-background-color: red; -fx-padding: 5;");
            valid = false;
        }
        return valid;
    }
    
    public Boolean isPersonValid(List<HBox> hb){
        Boolean valid = true;
        for(HBox person : hb){
            if(!isFieldValid(person)){
                valid = false;
            }
        }
        return valid;
    }
    
    public void createFields(Person person){
        root.getChildren().add(fieldsPanel);
        
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(560, 660);
        s1.setContent(fieldsPanel);
        root.getChildren().add(s1);
        
        createPersonFields(person);
        createChildrenFields(person);
    
        saveButton.setText("Uložiť XML");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveXml(event);
            }
        });
        fieldsPanel.getChildren().add(saveButton);
        validateButton.setText("Over XML");
        validateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                validateXmlXsd(event);
            }
        });
        fieldsPanel.getChildren().add(validateButton);
        
        // Prida generuj HTML Button
        gnerateHTMLButton.setText("Generuj HTML");
        gnerateHTMLButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showXSLTfromXML(event);         	
            }
        });
        fieldsPanel.getChildren().add(gnerateHTMLButton);
        fieldsPanel.setPrefSize(540, 640);
    }
    
    public void createPersonFields(Person person){
    	
        //firstname
        fieldsPerson.add(createHBoxNode("Meno", firstName, person.getFirstName(), new Tooltip("Pole nesmie obsahovať čísla ani iné špeciálne znaky")));       
        //lastname
        fieldsPerson.add(createHBoxNode("Priezvisko", lastName, person.getLastName(), new Tooltip("Pole nesmie obsahovať čísla ani iné špeciálne znaky")));      
        //ID Card Number
        fieldsPerson.add(createHBoxID("Číslo O.P.:", PersonID, person.getPersonID(), new Tooltip("Pole musí obsahovať presne 8 znakov (čísel alebo písmen) napr. HR123456")));      
        //gender
        fieldsPerson.add(createHBoxRB( new Label("Pohlavie"), maleRB, femaleRB, genderTG, person.isSetGender(), (person.getGender() == "muz")));
        //state   
        fieldsPerson.add(createHBoxRB(new Label("Stav"), singleRB, marriedRB, statusTG , person.isSetStatus(), (person.getStatus() == "slobodny")));
        //birthdate
        fieldsPerson.add(createHBoxNode("Dátum narodenia", birthDate, person.getBirthDate(), new Tooltip()));
        //num of children combobox       
        for (int i = 0 ; i <=50 ; i++){
            choices.getItems().add(new Item("počet detí: "+i, Integer.toString(i)));
        }       
        fieldsPerson.add(createHBoxNode("Počet detí", choices, person.getBirthDate(), new Tooltip()));
        addFieldsToPanel(fieldsPanel, fieldsPerson);
    }
    
    public void createChildrenFields(Person person){
    	
        //if has at least one child create and add child fields
        if(person.getChildren().size() > 0){
            choices.getSelectionModel().select(person.getChildren().size());         
            for(Child child : person.getChildren()){
                int i = person.getChildren().indexOf(child);
                fieldsChildren.add(createHBoxChild(("Dieťa "+ (i+1)), child.getFirstName(), child.getLastName(), new Tooltip("Pole nesmie obsahovať čísla ani iné špeciálne znaky")));            
            }
            addFieldsToPanel(childrenPanel, fieldsChildren);
        } else{
            choices.getSelectionModel().selectFirst();                             
            choices.valueProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    if(childrenPanel.getChildren().size() > 0){
                        childrenPanel.getChildren().clear();
                        fieldsChildren = new ArrayList<HBox>();
                        children = new ArrayList<TextField[]>();
                    }
                    int numChildrens = Integer.parseInt(newItem.detailsProperty().getValue());
                    for(int i=0; i<numChildrens; i++)
                    {
                        fieldsChildren.add(createHBoxChild(("Dieťa "+ (i+1)), "", "", new Tooltip("Pole nesmie obsahovať čísla ani iné špeciálne znaky")));
                    }
                    addFieldsToPanel(childrenPanel, fieldsChildren);
                }
            });
        }       
//        childrenPanel.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
//        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
//        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        fieldsPanel.getChildren().add(childrenPanel);
    }
    
    public void addFieldsToPanel(VBox vbox, List<HBox> fields){
        for(HBox field : fields){
            //field.setStyle("-fx-background-color: white;-fx-padding: 5;");
               field.setStyle("-fx-border-width: 0 0 1 0;" +
                "-fx-border-color: black;"
                + "-fx-padding: 5;");
               field.setAlignment(Pos.CENTER);
            for(Node child: field.getChildren()){
                if(child instanceof Label){
                    child.setStyle("-fx-padding: 5;-fx-font-size:14px;");
                }
            }
            vbox.getChildren().add(field);
        }
    }
    
    public HBox createHBoxNode(String label1Text,  Node field, String text, Tooltip tooltip){
    	
        HBox hbox = new HBox();
        
        if(field instanceof TextField){
            TextField tf = (TextField)field;
            tf.setText(text);
            tf.setTooltip(tooltip);
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if(isStringOnlyAlphabet(newValue)){
                    tf.setStyle("-fx-background-color: gray; -fx-padding: 5;");
                }else {
                    System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    tf.setStyle("-fx-background-color: red; -fx-padding: 5;");
                }
            });
        }else if(field instanceof DatePicker){
            DatePicker dp = (DatePicker)field;
            dp.setPromptText("16.10.2019");
            if(text != ""){
                dp.setValue(LocalDate.parse(text));
            }            
            dp.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                dp.setStyle("-fx-background-color: gray; -fx-padding: 5;");
            });
        }else if(field instanceof ComboBox){      
        }
        Label lb = new Label(label1Text);
        lb.setStyle("-fx-padding: 5;");
        hbox.getChildren().addAll(lb, field);
        hbox.setStyle("-fx-border-width: 1 0 1 0;" +
                    "-fx-border-color: black;"
                    + "-fx-padding: 5;");
        return hbox;
    }
    
    
    public HBox createHBoxID(String label1Text,  Node field, String text, Tooltip tooltip){
    	
        HBox hbox = new HBox();
        
        if(field instanceof TextField){
            TextField tf = (TextField)field;
            tf.setPromptText(text);
            tf.setTooltip(tooltip);
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.equals("HRXXXXXX") || !newValue.matches("^[a-zA-Z0-9]{8}")){
                //if(newValue.equals("HRXXXXXX")){
                    System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    tf.setStyle("-fx-background-color: red; -fx-padding: 5;");
                }else {
                    tf.setStyle("-fx-background-color: gray; -fx-padding: 5;");
                }
            });
        }
        
        hbox.getChildren().addAll(new Label(label1Text), field);
        
        return hbox;
    }
    
    public HBox createHBoxChild(String label1Text, String firstName, String lastName, Tooltip tooltip){
        HBox hbox = new HBox();
        TextField fields[] = new TextField[2];
        fields[0]= new TextField();
        fields[1]= new TextField();
        hbox.getChildren().addAll(new Label(label1Text), createHBoxNode("Meno",  fields[0], firstName, tooltip));
        hbox.getChildren().addAll(createHBoxNode("Priezvisko",  fields[1], lastName, tooltip));     
        children.add(fields);
        return hbox;
    }
    
    public HBox createHBoxRB(Label label, RadioButton rb1, RadioButton rb2, ToggleGroup tg, Boolean selected, Boolean select){
        HBox hbox = new HBox();               
        rb1.setToggleGroup(tg);
        rb1.setStyle("-fx-padding: 5; -fx-background-color: white;");
        rb2.setToggleGroup(tg);
        rb2.setStyle("-fx-padding: 5; -fx-background-color: white;");
        if(selected){
            if(select){
                rb1.setSelected(true);
            }else {
                rb2.setSelected(true);
            }
        }
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                 if (tg.getSelectedToggle() == null) {
                    rb1.setStyle("-fx-background-color: red; -fx-padding: 5;");
                    rb2.setStyle("-fx-background-color: red; -fx-padding: 5;");
                 }else{
                     rb1.setStyle("-fx-background-color: white; -fx-padding: 5;");
                    rb2.setStyle("-fx-background-color: white; -fx-padding: 5;");
                 }
            } 
        });
        hbox.getChildren().addAll(label, rb1, rb2);       
        return hbox;
    }
    
    
    //https://howtodoinjava.com/jaxb/write-object-to-xml/
    private static void jaxbObjectToXML(Person person, File selectedXmlFile) {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            //Print XML String to Console
            StringWriter sw = new StringWriter();
             
            //Write XML to StringWriter
            jaxbMarshaller.marshal(person, sw);
             
            //Verify XML Content
            String xmlContent = sw.toString();
            System.out.println( xmlContent );
            
            //File file = new File("person.xml");
            jaxbMarshaller.marshal(person, selectedXmlFile);
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    public class Item {
        private final String name ;
        private final StringProperty details = new SimpleStringProperty() ;

        public Item(String name, String details) {
            this.name = name ;
            setDetails(details) ;
        }

        public String getName() {
            return name ;
        }

        @Override
        public String toString() {
            return getName();
        }

        public final StringProperty detailsProperty() {
            return this.details;
        }


        public final String getDetails() {
            return this.detailsProperty().get();
        }


        public final void setDetails(final String details) {
            this.detailsProperty().set(details);
        }

    }
}



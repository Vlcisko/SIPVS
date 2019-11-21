package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;


public class XMLSignerController {

    File selectedXslFile;
    File selectedXmlFile;
    File selectedXsdFile;

    public String signFiles(File xmlFile, File xsdFile, File xslFile) throws Exception {

            String pathXML = xmlFile.getAbsolutePath().toString();
            String pathXSD = xsdFile.getAbsolutePath().toString();
            String pathXSL = xslFile.getAbsolutePath().toString();
            String signedXML = null;

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
                Main.showError("Chyba pri načítaní súborov: " + xmlPlugin.getErrorMessage());
                return pathXML;
            }

            int resOfAdd = dsigXades.AddObject(xmlObject);

            if (resOfAdd != 0) {
            	Main.showError("Chyba pri spúštaní DSig.Xades: " + dsigXades.getErrorMessage());
                return pathXML;
            }

            int resOfSign = dsigXades.Sign("SIPVS_Ditec", "sha256", "urn:oid:1.3.158.36061701.1.2.2");

            if (resOfSign != 0) {
            	Main.showError("Chyba pri prihlasovaní DSig.Xades: " + dsigXades.getErrorMessage());
                return pathXML;
            } else {

                Main.showInfo("XML bolo podpisane (XAdES-BES(EPES)) Vyberte lokaciu ulozenia...");
                signedXML = dsigXades.getSignedXmlWithEnvelope(); 
                saveSignedXML(signedXML);
               return signedXML;
            }
    }

    public String signDocumentWithTimeStamp(File signedXmlFile) throws Exception {
    
    	String signedXMLString = readFile(signedXmlFile.getAbsolutePath().toString());
    	String signeXMLwithTimestamp = null; 
    	
        if (signedXMLString == null) {
        	Main.showError("Neexistuje dokument na dodpisanie.");
            return null;
        }

        try {
        	
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            InputSource source = new InputSource(new StringReader(signedXMLString));
            Document document = docBuilder.parse(source);

            // Ziskanie elementu xades:QualifyingProperties
            Node qualifyingPropertiesSection = document.getElementsByTagName("xades:QualifyingProperties").item(0);

            if (qualifyingPropertiesSection == null) {
                Main.showError("Neda sa najst 'xades:QualifyingProperties element.'");
                return null;
            }

            // Vytvorenie sekcie casovej peciatky
            Element unsignedProperties          = document.createElement("xades:UnsignedProperties");
            Element unsignedSignatureProperties = document.createElement("xades:UnsignedSignatureProperties");
            Element signatureTimestamp          = document.createElement("xades:SignatureTimeStamp");
            Element encapsulatedTimeStamp       = document.createElement("xades:EncapsulatedTimeStamp");

            // Pridanie elementov casovej peciatky
            unsignedProperties.appendChild(unsignedSignatureProperties);
            unsignedSignatureProperties.appendChild(signatureTimestamp);
            signatureTimestamp.appendChild(encapsulatedTimeStamp);

            
            // Ziskanie peciatky a vlozenie do dokumentu
            Node signatureValue = document.getElementsByTagName("ds:SignatureValue").item(0);

            if (signatureValue == null) {
            	Main.showError("Neda sa najst 'ds:SignatureValue element'");
                return null;
            }

            TSClient tsClient = new TSClient();
            String timestamp = tsClient.getTimeStampTokenBase64(signatureValue.getTextContent());
            Text signatureNode = document.createTextNode(timestamp);
            encapsulatedTimeStamp.appendChild(signatureNode);

            qualifyingPropertiesSection.appendChild(unsignedProperties);

            // Vytvorime NOVE XML s podpisanymi datami
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource domSource = new DOMSource(document);
            StreamResult result = new StreamResult(new StringWriter());
            
            transformer.transform(domSource, result);
            
            Main.showInfo("XML bolo podpisane (XAdES-T) Vyberte lokaciu ulozenia...");
            
            signeXMLwithTimestamp = result.getWriter().toString(); 
            saveSignedXML(signeXMLwithTimestamp);  
            return signeXMLwithTimestamp;
            
        } catch (ParserConfigurationException e) {
        	Main.showError(e.getLocalizedMessage());
        } catch (SAXException e) {
        	Main.showError(e.getLocalizedMessage() );
        } catch (IOException e) {
           	Main.showError(e.getLocalizedMessage() );
        } catch (TransformerConfigurationException e) {
           	Main.showError(e.getLocalizedMessage() );
        } catch (TransformerException e) {
           	Main.showError(e.getLocalizedMessage() );
        }
        return null;
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
            Main.showError("Nemožno uložiť prázdny dokument.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(Main.workDirPath + "\\data"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML súbor (*.xml)", "*.xml"));
        File saveXmlFile = fileChooser.showSaveDialog(null);

        BufferedWriter bWriter = null;
        String outputFileName = saveXmlFile.getAbsolutePath();
        try {
            bWriter = new BufferedWriter(new FileWriter(outputFileName));
            bWriter.write(xmlFile);
            return;
        } catch (IOException ex) {
            Main.showError("Nie je možné uložiť súbor: " + ex);
        } finally {
            if (bWriter != null) {
                try {
                    bWriter.close();
                    Main.showInfo("Súbor bol úspešné uložený.Nájdete ho v: " + outputFileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    

}

package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javafx.stage.FileChooser;

public class HtmlCreator {
	public void createHTML(File selectedXmlFile, File selectedXslFile) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Source xmlDoc = new StreamSource(selectedXmlFile);
			Source xslDoc = new StreamSource(selectedXslFile);

			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(Main.workDirPath));
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML súbor (*.html)", "*.html"));
			File saveXmlFile = fileChooser.showSaveDialog(null);

			if (saveXmlFile != null) {
				String outputFileName = saveXmlFile.getAbsolutePath();
				OutputStream htmlFile = new FileOutputStream(outputFileName);
				Transformer trasform = tFactory.newTransformer(xslDoc);

				trasform.transform(xmlDoc, new StreamResult(htmlFile));

				Main.showInfo("HTML súbor bol vytvorený");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}

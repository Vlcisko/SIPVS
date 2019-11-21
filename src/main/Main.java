
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

	public static String workDirPath = System.getProperty("user.dir");

	@Override
	public void start(Stage stage) {

		try {

			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/XmlForm.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * CONFIRMATION ERROR INFORMATION NONE WARNING
	 */
	public static void showInfo(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	public static void showError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(message);
		alert.showAndWait();
	}
}

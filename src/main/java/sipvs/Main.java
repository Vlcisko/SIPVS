/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class Main extends Application {

    public  static String workingDirectoryPath = System.getProperty("user.dir");


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/XmlForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}

/*

*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*

package sipvs;

        import com.jacob.activeX.ActiveXComponent;
        import com.jacob.com.LibraryLoader;
        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.security.PublicKey;
        import java.util.concurrent.Flow;


public class Main extends Application {

    public static String workingDirectoryPath = System.getProperty("user.dir");

    @Override
    public void start(Stage stage) throws Exception {
        //System.out.println(getClass().getClassLoader().getResource());

        // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("XmlForm.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        workingDirectoryPath = workingDirectoryPath + "\\src\\main\\java\\sipvs\\";
        String pathToFxml = Main.workingDirectoryPath + "\\src\\main\\java\\sipvs\\XmlForm.fxml";
        System.out.println(pathToFxml);

        System.out.println(getClass().getClassLoader().getResource(pathToFxml));
//      //  launch(args);
//        ActiveXComponent dsig_app;
//        File temporaryDll = null;
//        InputStream inputStream = null;
//        try {
//            String path = Main.workingDirectoryPath + "\\src\\sipvs\\rescources\\dll\\jacob-1.19-x64.dll";
//            File initialFile = new File(path);
//            inputStream = new FileInputStream(initialFile);
//            //inputStream =  getClass().getResourceAsStream(path);
//            temporaryDll = createTmpResource(inputStream);
//
//            System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getAbsolutePath());
//            LibraryLoader.loadJacobLibrary();
//        }
//        finally {
//
//        }
    }


}
*/

package sipvs.DSigNetWrapper;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.LibraryLoader;

import java.io.*;

public abstract class AbstractDSigNETWrapper {

    private static final String JACOB_FILE_X64 = "jacob-1.19-x64.dll";
    private static final String workingDirectoryPath = System.getProperty("user.dir");

    protected ActiveXComponent dsig_app;

    public AbstractDSigNETWrapper(String progid) throws IOException {
        dsig_app = loadActiveXComponent(progid);
    }

    protected String getStringProperty(String propertyName) {
        return dsig_app.getProperty(propertyName).getString();
    }

    private ActiveXComponent loadActiveXComponent(String progid) throws IOException {

        ActiveXComponent dsig_app;
        File temporaryDll = null;
        InputStream inputStream = null;
        try {

            File initialFile = new File(workingDirectoryPath + "\\src\\sipvs\\rescources\\dll\\jacob-1.19-x64.dll");
            inputStream = new FileInputStream(initialFile);
            //inputStream = getClass().getResourceAsStream("/dll/" + JACOB_FILE_X64);
            temporaryDll = createTmpResource(inputStream);

            System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getAbsolutePath());
            LibraryLoader.loadJacobLibrary();

            dsig_app = new ActiveXComponent(progid);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (temporaryDll != null) {
                temporaryDll.deleteOnExit();
            }
        }

        return dsig_app;
    }

    private File createTmpResource(InputStream inputStream) throws IOException {
        File temporaryDll = null;
        FileOutputStream outputStream = null;
        try {
            temporaryDll = File.createTempFile("jacob", ".dll");
            outputStream = new FileOutputStream(temporaryDll);
            byte[] array = new byte[8192];
            for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
                outputStream.write(array, 0, i);
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return temporaryDll;
    }

}

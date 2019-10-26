package sipvs.DSigNetWrapper;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.LibraryLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractDSigNETWrapper {

	private static final String JACOB_FILE_X86 = "jacob-1.19-x86.dll";
	
	protected ActiveXComponent dsig_app;

	public AbstractDSigNETWrapper(String progid) throws IOException {
		dsig_app = loadActiveXComponent(progid);
	}

	protected String getStringProperty(String propertyName){
		return dsig_app.getProperty(propertyName).getString();
	}
	
	private ActiveXComponent loadActiveXComponent(String progid) throws IOException {

		ActiveXComponent dsig_app;
		File temporaryDll = null;
		InputStream inputStream = null;
		try {
			inputStream =  getClass().getResourceAsStream("/dll/" + JACOB_FILE_X86);
			temporaryDll = createTmpResource(inputStream);

			System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getAbsolutePath());
			LibraryLoader.loadJacobLibrary();

			dsig_app = new ActiveXComponent(progid);
		} finally {
			if(inputStream != null) {
				inputStream.close();
			}
			
			if(temporaryDll != null) {
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
			if(outputStream != null){
				outputStream.close();
			}
		}
		
		return temporaryDll;
	}
	
}

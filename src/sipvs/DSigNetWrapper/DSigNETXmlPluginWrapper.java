package sipvs.DSigNetWrapper;

import com.jacob.com.Dispatch;

import java.io.IOException;

	public class DSigNETXmlPluginWrapper extends AbstractDSigNETWrapper {
	
	private static final String PROGID = "DSig.XmlPlugin";
	
	public DSigNETXmlPluginWrapper() throws IOException {
		super(PROGID);
	}
	
	public Object CreateObject(String objectId, String objectDescription, String sourceXml, String sourceXsd, String namespaceUri, String xsdReference, String sourceXsl, String xslReference){
		return Dispatch.call(dsig_app, "CreateObject", objectId, objectDescription, sourceXml, sourceXsd, namespaceUri, xsdReference, sourceXsl, xslReference);
	}
	
	public Object CreateObject2(String objectId, String objectDescription, String sourceXml, String sourceXsd, String namespaceUri, String xsdReference, String sourceXsl, String xslReference, String transformType){
		return Dispatch.call(dsig_app, "CreateObject2", objectId, objectDescription, sourceXml, sourceXsd, namespaceUri, xsdReference, sourceXsl, xslReference, transformType);
	}
	
	public String getErrorMessage() {
		return getStringProperty("ErrorMessage");
	}

}

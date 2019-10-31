package sipvs.DSigNetWrapper;

import java.io.IOException;
import com.jacob.activeX.*;
import com.jacob.com.*;

import javax.xml.ws.Dispatch;

public class DSigNETWrapper extends AbstractDSigNETWrapper {

	private static final String PROGID = "DSig.XadesSig";

	public DSigNETWrapper() throws IOException {
		super(PROGID);
	}

	public void SetSigningTimeProcessing(boolean displayGui, boolean includeSigningTime) {
		Dispatch.call(dsig_app, "SetSigningTimeProcessing", displayGui, includeSigningTime);
	}

	public int Sign(String signatureId, String digestAlgUri, String signaturePolicyIdentifier) {
		return Dispatch.call(dsig_app, "Sign", signatureId, digestAlgUri, signaturePolicyIdentifier).getInt();
	}

	public int Sign20(String signatureId, String digestAlgUri, String signaturePolicyIdentifier, String dataEnvelopeId,
			String dataEnvelopeURI, String dataEnvelopeDescr) {
		return Dispatch.call(dsig_app, "Sign20", signatureId, digestAlgUri, signaturePolicyIdentifier).getInt();
	}

	public int AddObject(Object obj) {
		return Dispatch.call(dsig_app, "AddObject", obj).getInt();
	}

	public String getErrorMessage() {
		return getStringProperty("ErrorMessage");
	}

	public String getSignedXmlWithEnvelope() {
		return getStringProperty("SignedXmlWithEnvelope");
	}
}

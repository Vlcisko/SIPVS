package main;

import java.io.IOException;

import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.encoders.Base64;

import sk.ditec.TS;

public class TSConnector {

	public String getTimeStamp(String message) {

		String timeStampBase64 = null;

		System.out.println("PYTAM SI TS PRE: " + message);

		try {
			// POSIELAM A PRIJIMAM DATA Z DITEC SERVERA
			TS timeStampClient = new TS();
			timeStampBase64 = timeStampClient.getTSSoap().getTimestamp(message);

		} catch (Exception e) {
			Main.showError("SOAP CHYBA: ZADIAN ODPOVED NA REQUEST: " + message + "--" + e);
		}

		System.out.println("CASOVA PECIATKA: " + timeStampBase64);

		return timeStampBase64;
	}

	public TimeStampToken getTimeStampToken(String message) {

		TimeStampToken timeStampToken = null;
		String timeStampBase64 = getTimeStamp(message);

		byte[] responseByteData = Base64.decode(timeStampBase64.getBytes());

		try {
			TimeStampResponse response = new TimeStampResponse(responseByteData);
			timeStampToken = response.getTimeStampToken();

			System.out.println("SERIOVE CISLO  : " + timeStampToken.getTimeStampInfo().getSerialNumber());
			System.out.println("TS AUTORITA    : " + timeStampToken.getTimeStampInfo().getTsa());
			System.out.println("CAS GENEROVANIE: " + timeStampToken.getTimeStampInfo().getGenTime());
		} catch (TSPException | IOException e) {
			Main.showError("Neda sa ZISKAT casova peciatka, popis chyby: " + e.getLocalizedMessage());
		}

		return timeStampToken;
	}

	public String getTimeStampTokenBase64(String message) {

		TimeStampToken timeStampToken = getTimeStampToken(message);

		try {
			return new String(Base64.encode(timeStampToken.getEncoded()));
		} catch (IOException e) {
			Main.showError("Neda sa ZAKODOVAT casova peciatka, popis chyby: " + e.getLocalizedMessage());
		}

		return null;
	}

}

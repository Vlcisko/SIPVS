package sk.ditec;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.4
 * 2019-11-21T09:52:31.902+01:00
 * Generated source version: 3.3.4
 *
 */
@WebService(targetNamespace = "http://www.ditec.sk/", name = "TSSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface TSSoap {

    @WebMethod(operationName = "GetTimestamp", action = "http://www.ditec.sk/GetTimestamp")
    @RequestWrapper(localName = "GetTimestamp", targetNamespace = "http://www.ditec.sk/", className = "sk.ditec.GetTimestamp")
    @ResponseWrapper(localName = "GetTimestampResponse", targetNamespace = "http://www.ditec.sk/", className = "sk.ditec.GetTimestampResponse")
    @WebResult(name = "GetTimestampResult", targetNamespace = "http://www.ditec.sk/")
    public java.lang.String getTimestamp(

        @WebParam(name = "dataB64", targetNamespace = "http://www.ditec.sk/")
        java.lang.String dataB64
    );
}
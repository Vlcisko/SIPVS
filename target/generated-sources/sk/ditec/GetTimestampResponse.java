
package sk.ditec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetTimestampResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getTimestampResult" })
@XmlRootElement(name = "GetTimestampResponse")
public class GetTimestampResponse {

	@XmlElement(name = "GetTimestampResult")
	protected String getTimestampResult;

	/**
	 * Gets the value of the getTimestampResult property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGetTimestampResult() {
		return getTimestampResult;
	}

	/**
	 * Sets the value of the getTimestampResult property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setGetTimestampResult(String value) {
		this.getTimestampResult = value;
	}

}

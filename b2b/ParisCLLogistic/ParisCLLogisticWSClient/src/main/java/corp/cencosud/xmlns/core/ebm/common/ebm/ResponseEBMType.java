
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseEBMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseEBMType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}EBMHeader"/>
 *         &lt;element name="ReturnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReturnMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://xmlns.cencosud.corp/Core/EBM/Common/EBM}ErrorDetail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseEBMType", propOrder = {
    "ebmHeader",
    "returnCode",
    "returnMessage",
    "errorCode",
    "errorDetail"
})
public class ResponseEBMType {

    @XmlElement(name = "EBMHeader", required = true)
    protected EBMHeaderType ebmHeader;
    @XmlElement(name = "ReturnCode", required = true)
    protected String returnCode;
    @XmlElement(name = "ReturnMessage", required = true)
    protected String returnMessage;
    @XmlElementRef(name = "ErrorCode", namespace = "http://xmlns.cencosud.corp/Core/EBM/Common/EBM", type = JAXBElement.class)
    protected JAXBElement<String> errorCode;
    @XmlElement(name = "ErrorDetail", nillable = true)
    protected ErrorDetailType errorDetail;

    /**
     * Gets the value of the ebmHeader property.
     * 
     * @return
     *     possible object is
     *     {@link EBMHeaderType }
     *     
     */
    public EBMHeaderType getEBMHeader() {
        return ebmHeader;
    }

    /**
     * Sets the value of the ebmHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link EBMHeaderType }
     *     
     */
    public void setEBMHeader(EBMHeaderType value) {
        this.ebmHeader = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
    }

    /**
     * Gets the value of the returnMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * Sets the value of the returnMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnMessage(String value) {
        this.returnMessage = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrorCode(JAXBElement<String> value) {
        this.errorCode = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the errorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorDetailType }
     *     
     */
    public ErrorDetailType getErrorDetail() {
        return errorDetail;
    }

    /**
     * Sets the value of the errorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorDetailType }
     *     
     */
    public void setErrorDetail(ErrorDetailType value) {
        this.errorDetail = value;
    }

}

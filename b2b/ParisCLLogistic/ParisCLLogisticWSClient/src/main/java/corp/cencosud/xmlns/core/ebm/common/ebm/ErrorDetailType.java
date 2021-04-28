
package corp.cencosud.xmlns.core.ebm.common.ebm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HandlerServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceApplication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceServiceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SourceErrorTrace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorDetailType", propOrder = {
    "errorType",
    "handlerServiceName",
    "sourceApplication",
    "sourceServiceName",
    "sourceErrorCode",
    "sourceErrorMessage",
    "sourceErrorTrace"
})
public class ErrorDetailType {

    @XmlElement(name = "ErrorType")
    protected String errorType;
    @XmlElement(name = "HandlerServiceName")
    protected String handlerServiceName;
    @XmlElement(name = "SourceApplication")
    protected String sourceApplication;
    @XmlElement(name = "SourceServiceName")
    protected String sourceServiceName;
    @XmlElement(name = "SourceErrorCode")
    protected String sourceErrorCode;
    @XmlElement(name = "SourceErrorMessage")
    protected String sourceErrorMessage;
    @XmlElement(name = "SourceErrorTrace")
    protected String sourceErrorTrace;

    /**
     * Gets the value of the errorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Sets the value of the errorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorType(String value) {
        this.errorType = value;
    }

    /**
     * Gets the value of the handlerServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandlerServiceName() {
        return handlerServiceName;
    }

    /**
     * Sets the value of the handlerServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandlerServiceName(String value) {
        this.handlerServiceName = value;
    }

    /**
     * Gets the value of the sourceApplication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceApplication() {
        return sourceApplication;
    }

    /**
     * Sets the value of the sourceApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceApplication(String value) {
        this.sourceApplication = value;
    }

    /**
     * Gets the value of the sourceServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceServiceName() {
        return sourceServiceName;
    }

    /**
     * Sets the value of the sourceServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceServiceName(String value) {
        this.sourceServiceName = value;
    }

    /**
     * Gets the value of the sourceErrorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceErrorCode() {
        return sourceErrorCode;
    }

    /**
     * Sets the value of the sourceErrorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceErrorCode(String value) {
        this.sourceErrorCode = value;
    }

    /**
     * Gets the value of the sourceErrorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceErrorMessage() {
        return sourceErrorMessage;
    }

    /**
     * Sets the value of the sourceErrorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceErrorMessage(String value) {
        this.sourceErrorMessage = value;
    }

    /**
     * Gets the value of the sourceErrorTrace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceErrorTrace() {
        return sourceErrorTrace;
    }

    /**
     * Sets the value of the sourceErrorTrace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceErrorTrace(String value) {
        this.sourceErrorTrace = value;
    }

}


package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageDataDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageDataDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="compresseddocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="encrypt" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="encryptpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endpoint" type="{http://facade.webservices.services.esb.bbr/}wsEndpointDataDTO" minOccurs="0"/>
 *         &lt;element name="folder" type="{http://facade.webservices.services.esb.bbr/}messageFolderDataDTO" minOccurs="0"/>
 *         &lt;element name="format" type="{http://facade.webservices.services.esb.bbr/}messageFormatDataDTO" minOccurs="0"/>
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageDataDTO", propOrder = {
    "compresseddocument",
    "encrypt",
    "encryptpassword",
    "endpoint",
    "folder",
    "format",
    "servicename",
    "sitename"
})
public class MessageDataDTO {

    protected boolean compresseddocument;
    protected boolean encrypt;
    protected String encryptpassword;
    protected WsEndpointDataDTO endpoint;
    protected MessageFolderDataDTO folder;
    protected MessageFormatDataDTO format;
    protected String servicename;
    protected String sitename;

    /**
     * Gets the value of the compresseddocument property.
     * 
     */
    public boolean isCompresseddocument() {
        return compresseddocument;
    }

    /**
     * Sets the value of the compresseddocument property.
     * 
     */
    public void setCompresseddocument(boolean value) {
        this.compresseddocument = value;
    }

    /**
     * Gets the value of the encrypt property.
     * 
     */
    public boolean isEncrypt() {
        return encrypt;
    }

    /**
     * Sets the value of the encrypt property.
     * 
     */
    public void setEncrypt(boolean value) {
        this.encrypt = value;
    }

    /**
     * Gets the value of the encryptpassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptpassword() {
        return encryptpassword;
    }

    /**
     * Sets the value of the encryptpassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptpassword(String value) {
        this.encryptpassword = value;
    }

    /**
     * Gets the value of the endpoint property.
     * 
     * @return
     *     possible object is
     *     {@link WsEndpointDataDTO }
     *     
     */
    public WsEndpointDataDTO getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the value of the endpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link WsEndpointDataDTO }
     *     
     */
    public void setEndpoint(WsEndpointDataDTO value) {
        this.endpoint = value;
    }

    /**
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link MessageFolderDataDTO }
     *     
     */
    public MessageFolderDataDTO getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageFolderDataDTO }
     *     
     */
    public void setFolder(MessageFolderDataDTO value) {
        this.folder = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link MessageFormatDataDTO }
     *     
     */
    public MessageFormatDataDTO getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageFormatDataDTO }
     *     
     */
    public void setFormat(MessageFormatDataDTO value) {
        this.format = value;
    }

    /**
     * Gets the value of the servicename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicename() {
        return servicename;
    }

    /**
     * Sets the value of the servicename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicename(String value) {
        this.servicename = value;
    }

    /**
     * Gets the value of the sitename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitename() {
        return sitename;
    }

    /**
     * Sets the value of the sitename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitename(String value) {
        this.sitename = value;
    }

}

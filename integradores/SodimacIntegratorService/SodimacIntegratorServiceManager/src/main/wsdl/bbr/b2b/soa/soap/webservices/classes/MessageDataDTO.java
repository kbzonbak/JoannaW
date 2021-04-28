//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para messageDataDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="messageDataDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="compresseddocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="encrypt" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="encryptpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endpoint" type="{http://facade.webservices.services.esb.bbr/}wsEndpointDataDTO" minOccurs="0"/&gt;
 *         &lt;element name="folder" type="{http://facade.webservices.services.esb.bbr/}messageFolderDataDTO" minOccurs="0"/&gt;
 *         &lt;element name="format" type="{http://facade.webservices.services.esb.bbr/}messageFormatDataDTO" minOccurs="0"/&gt;
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sitename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Obtiene el valor de la propiedad compresseddocument.
     * 
     */
    public boolean isCompresseddocument() {
        return compresseddocument;
    }

    /**
     * Define el valor de la propiedad compresseddocument.
     * 
     */
    public void setCompresseddocument(boolean value) {
        this.compresseddocument = value;
    }

    /**
     * Obtiene el valor de la propiedad encrypt.
     * 
     */
    public boolean isEncrypt() {
        return encrypt;
    }

    /**
     * Define el valor de la propiedad encrypt.
     * 
     */
    public void setEncrypt(boolean value) {
        this.encrypt = value;
    }

    /**
     * Obtiene el valor de la propiedad encryptpassword.
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
     * Define el valor de la propiedad encryptpassword.
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
     * Obtiene el valor de la propiedad endpoint.
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
     * Define el valor de la propiedad endpoint.
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
     * Obtiene el valor de la propiedad folder.
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
     * Define el valor de la propiedad folder.
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
     * Obtiene el valor de la propiedad format.
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
     * Define el valor de la propiedad format.
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
     * Obtiene el valor de la propiedad servicename.
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
     * Define el valor de la propiedad servicename.
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
     * Obtiene el valor de la propiedad sitename.
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
     * Define el valor de la propiedad sitename.
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

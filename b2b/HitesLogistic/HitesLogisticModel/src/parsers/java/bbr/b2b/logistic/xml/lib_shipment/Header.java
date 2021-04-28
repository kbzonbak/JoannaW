//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:10:58 PM CLST 
//


package bbr.b2b.logistic.xml.lib_shipment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}DocumentVersion"/&gt;
 *         &lt;element ref="{}ClientEnvCode"/&gt;
 *         &lt;element ref="{}ParentCompanyCode"/&gt;
 *         &lt;element ref="{}Entity"/&gt;
 *         &lt;element ref="{}TimeStamp"/&gt;
 *         &lt;element ref="{}MessageId"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentVersion",
    "clientEnvCode",
    "parentCompanyCode",
    "entity",
    "timeStamp",
    "messageId"
})
@XmlRootElement(name = "Header")
public class Header {

    @XmlElement(name = "DocumentVersion", required = true)
    protected String documentVersion;
    @XmlElement(name = "ClientEnvCode", required = true)
    protected String clientEnvCode;
    @XmlElement(name = "ParentCompanyCode", required = true)
    protected String parentCompanyCode;
    @XmlElement(name = "Entity", required = true)
    protected String entity;
    @XmlElement(name = "TimeStamp", required = true)
    protected String timeStamp;
    @XmlElement(name = "MessageId", required = true)
    protected String messageId;

    /**
     * Obtiene el valor de la propiedad documentVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentVersion() {
        return documentVersion;
    }

    /**
     * Define el valor de la propiedad documentVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentVersion(String value) {
        this.documentVersion = value;
    }

    /**
     * Obtiene el valor de la propiedad clientEnvCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientEnvCode() {
        return clientEnvCode;
    }

    /**
     * Define el valor de la propiedad clientEnvCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientEnvCode(String value) {
        this.clientEnvCode = value;
    }

    /**
     * Obtiene el valor de la propiedad parentCompanyCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentCompanyCode() {
        return parentCompanyCode;
    }

    /**
     * Define el valor de la propiedad parentCompanyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentCompanyCode(String value) {
        this.parentCompanyCode = value;
    }

    /**
     * Obtiene el valor de la propiedad entity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Define el valor de la propiedad entity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity(String value) {
        this.entity = value;
    }

    /**
     * Obtiene el valor de la propiedad timeStamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Define el valor de la propiedad timeStamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeStamp(String value) {
        this.timeStamp = value;
    }

    /**
     * Obtiene el valor de la propiedad messageId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Define el valor de la propiedad messageId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

}

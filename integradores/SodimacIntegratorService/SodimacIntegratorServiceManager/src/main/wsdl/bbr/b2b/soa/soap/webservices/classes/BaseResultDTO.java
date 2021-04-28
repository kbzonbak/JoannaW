//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para baseResultDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="baseResultDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="statuscode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="statusmessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseResultDTO", propOrder = {
    "statuscode",
    "statusmessage"
})
@XmlSeeAlso({
    TicketEventResultDTO.class,
    LogonResultDTO.class
})
public class BaseResultDTO {

    protected String statuscode;
    protected String statusmessage;

    /**
     * Obtiene el valor de la propiedad statuscode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatuscode() {
        return statuscode;
    }

    /**
     * Define el valor de la propiedad statuscode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatuscode(String value) {
        this.statuscode = value;
    }

    /**
     * Obtiene el valor de la propiedad statusmessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusmessage() {
        return statusmessage;
    }

    /**
     * Define el valor de la propiedad statusmessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusmessage(String value) {
        this.statusmessage = value;
    }

}

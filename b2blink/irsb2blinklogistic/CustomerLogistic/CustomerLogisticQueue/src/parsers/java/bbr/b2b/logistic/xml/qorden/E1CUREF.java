//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}POSEX" minOccurs="0"/&gt;
 *         &lt;element ref="{}CONFIG_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}INST_ID" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="SEGMENT" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "posex",
    "configid",
    "instid"
})
@XmlRootElement(name = "E1CUREF")
public class E1CUREF {

    @XmlElement(name = "POSEX")
    protected POSEX posex;
    @XmlElement(name = "CONFIG_ID")
    protected CONFIGID configid;
    @XmlElement(name = "INST_ID")
    protected INSTID instid;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad posex.
     * 
     * @return
     *     possible object is
     *     {@link POSEX }
     *     
     */
    public POSEX getPOSEX() {
        return posex;
    }

    /**
     * Define el valor de la propiedad posex.
     * 
     * @param value
     *     allowed object is
     *     {@link POSEX }
     *     
     */
    public void setPOSEX(POSEX value) {
        this.posex = value;
    }

    /**
     * Obtiene el valor de la propiedad configid.
     * 
     * @return
     *     possible object is
     *     {@link CONFIGID }
     *     
     */
    public CONFIGID getCONFIGID() {
        return configid;
    }

    /**
     * Define el valor de la propiedad configid.
     * 
     * @param value
     *     allowed object is
     *     {@link CONFIGID }
     *     
     */
    public void setCONFIGID(CONFIGID value) {
        this.configid = value;
    }

    /**
     * Obtiene el valor de la propiedad instid.
     * 
     * @return
     *     possible object is
     *     {@link INSTID }
     *     
     */
    public INSTID getINSTID() {
        return instid;
    }

    /**
     * Define el valor de la propiedad instid.
     * 
     * @param value
     *     allowed object is
     *     {@link INSTID }
     *     
     */
    public void setINSTID(INSTID value) {
        this.instid = value;
    }

    /**
     * Obtiene el valor de la propiedad segment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT() {
        return segment;
    }

    /**
     * Define el valor de la propiedad segment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT(String value) {
        this.segment = value;
    }

}

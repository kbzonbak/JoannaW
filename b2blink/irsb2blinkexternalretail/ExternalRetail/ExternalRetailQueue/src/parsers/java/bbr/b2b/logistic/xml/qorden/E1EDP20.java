//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
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
 *         &lt;element ref="{}WMENG" minOccurs="0"/&gt;
 *         &lt;element ref="{}AMENG" minOccurs="0"/&gt;
 *         &lt;element ref="{}EDATU" minOccurs="0"/&gt;
 *         &lt;element ref="{}EZEIT" minOccurs="0"/&gt;
 *         &lt;element ref="{}EDATU_OLD" minOccurs="0"/&gt;
 *         &lt;element ref="{}EZEIT_OLD" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP20" minOccurs="0"/&gt;
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
    "wmeng",
    "ameng",
    "edatu",
    "ezeit",
    "edatuold",
    "ezeitold",
    "z1EDP20"
})
@XmlRootElement(name = "E1EDP20")
public class E1EDP20 {

    @XmlElement(name = "WMENG")
    protected WMENG wmeng;
    @XmlElement(name = "AMENG")
    protected AMENG ameng;
    @XmlElement(name = "EDATU")
    protected EDATU edatu;
    @XmlElement(name = "EZEIT")
    protected EZEIT ezeit;
    @XmlElement(name = "EDATU_OLD")
    protected EDATUOLD edatuold;
    @XmlElement(name = "EZEIT_OLD")
    protected EZEITOLD ezeitold;
    @XmlElement(name = "Z1EDP20")
    protected Z1EDP20 z1EDP20;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad wmeng.
     * 
     * @return
     *     possible object is
     *     {@link WMENG }
     *     
     */
    public WMENG getWMENG() {
        return wmeng;
    }

    /**
     * Define el valor de la propiedad wmeng.
     * 
     * @param value
     *     allowed object is
     *     {@link WMENG }
     *     
     */
    public void setWMENG(WMENG value) {
        this.wmeng = value;
    }

    /**
     * Obtiene el valor de la propiedad ameng.
     * 
     * @return
     *     possible object is
     *     {@link AMENG }
     *     
     */
    public AMENG getAMENG() {
        return ameng;
    }

    /**
     * Define el valor de la propiedad ameng.
     * 
     * @param value
     *     allowed object is
     *     {@link AMENG }
     *     
     */
    public void setAMENG(AMENG value) {
        this.ameng = value;
    }

    /**
     * Obtiene el valor de la propiedad edatu.
     * 
     * @return
     *     possible object is
     *     {@link EDATU }
     *     
     */
    public EDATU getEDATU() {
        return edatu;
    }

    /**
     * Define el valor de la propiedad edatu.
     * 
     * @param value
     *     allowed object is
     *     {@link EDATU }
     *     
     */
    public void setEDATU(EDATU value) {
        this.edatu = value;
    }

    /**
     * Obtiene el valor de la propiedad ezeit.
     * 
     * @return
     *     possible object is
     *     {@link EZEIT }
     *     
     */
    public EZEIT getEZEIT() {
        return ezeit;
    }

    /**
     * Define el valor de la propiedad ezeit.
     * 
     * @param value
     *     allowed object is
     *     {@link EZEIT }
     *     
     */
    public void setEZEIT(EZEIT value) {
        this.ezeit = value;
    }

    /**
     * Obtiene el valor de la propiedad edatuold.
     * 
     * @return
     *     possible object is
     *     {@link EDATUOLD }
     *     
     */
    public EDATUOLD getEDATUOLD() {
        return edatuold;
    }

    /**
     * Define el valor de la propiedad edatuold.
     * 
     * @param value
     *     allowed object is
     *     {@link EDATUOLD }
     *     
     */
    public void setEDATUOLD(EDATUOLD value) {
        this.edatuold = value;
    }

    /**
     * Obtiene el valor de la propiedad ezeitold.
     * 
     * @return
     *     possible object is
     *     {@link EZEITOLD }
     *     
     */
    public EZEITOLD getEZEITOLD() {
        return ezeitold;
    }

    /**
     * Define el valor de la propiedad ezeitold.
     * 
     * @param value
     *     allowed object is
     *     {@link EZEITOLD }
     *     
     */
    public void setEZEITOLD(EZEITOLD value) {
        this.ezeitold = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP20.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP20 }
     *     
     */
    public Z1EDP20 getZ1EDP20() {
        return z1EDP20;
    }

    /**
     * Define el valor de la propiedad z1EDP20.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP20 }
     *     
     */
    public void setZ1EDP20(Z1EDP20 value) {
        this.z1EDP20 = value;
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

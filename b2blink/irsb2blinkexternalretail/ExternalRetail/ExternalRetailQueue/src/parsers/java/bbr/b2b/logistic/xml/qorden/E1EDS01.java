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
 *         &lt;element ref="{}SUMID" minOccurs="0"/&gt;
 *         &lt;element ref="{}SUMME" minOccurs="0"/&gt;
 *         &lt;element ref="{}SUNIT" minOccurs="0"/&gt;
 *         &lt;element ref="{}WAERQ" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDS02" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDS01" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK11" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK12" minOccurs="0"/&gt;
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
    "sumid",
    "summe",
    "sunit",
    "waerq",
    "z1EDS02",
    "z1EDS01",
    "z1EDK11",
    "z1EDK12"
})
@XmlRootElement(name = "E1EDS01")
public class E1EDS01 {

    @XmlElement(name = "SUMID")
    protected SUMID sumid;
    @XmlElement(name = "SUMME")
    protected SUMME summe;
    @XmlElement(name = "SUNIT")
    protected SUNIT sunit;
    @XmlElement(name = "WAERQ")
    protected WAERQ waerq;
    @XmlElement(name = "Z1EDS02")
    protected Z1EDS02 z1EDS02;
    @XmlElement(name = "Z1EDS01")
    protected Z1EDS01 z1EDS01;
    @XmlElement(name = "Z1EDK11")
    protected Z1EDK11 z1EDK11;
    @XmlElement(name = "Z1EDK12")
    protected Z1EDK12 z1EDK12;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad sumid.
     * 
     * @return
     *     possible object is
     *     {@link SUMID }
     *     
     */
    public SUMID getSUMID() {
        return sumid;
    }

    /**
     * Define el valor de la propiedad sumid.
     * 
     * @param value
     *     allowed object is
     *     {@link SUMID }
     *     
     */
    public void setSUMID(SUMID value) {
        this.sumid = value;
    }

    /**
     * Obtiene el valor de la propiedad summe.
     * 
     * @return
     *     possible object is
     *     {@link SUMME }
     *     
     */
    public SUMME getSUMME() {
        return summe;
    }

    /**
     * Define el valor de la propiedad summe.
     * 
     * @param value
     *     allowed object is
     *     {@link SUMME }
     *     
     */
    public void setSUMME(SUMME value) {
        this.summe = value;
    }

    /**
     * Obtiene el valor de la propiedad sunit.
     * 
     * @return
     *     possible object is
     *     {@link SUNIT }
     *     
     */
    public SUNIT getSUNIT() {
        return sunit;
    }

    /**
     * Define el valor de la propiedad sunit.
     * 
     * @param value
     *     allowed object is
     *     {@link SUNIT }
     *     
     */
    public void setSUNIT(SUNIT value) {
        this.sunit = value;
    }

    /**
     * Obtiene el valor de la propiedad waerq.
     * 
     * @return
     *     possible object is
     *     {@link WAERQ }
     *     
     */
    public WAERQ getWAERQ() {
        return waerq;
    }

    /**
     * Define el valor de la propiedad waerq.
     * 
     * @param value
     *     allowed object is
     *     {@link WAERQ }
     *     
     */
    public void setWAERQ(WAERQ value) {
        this.waerq = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDS02.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDS02 }
     *     
     */
    public Z1EDS02 getZ1EDS02() {
        return z1EDS02;
    }

    /**
     * Define el valor de la propiedad z1EDS02.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDS02 }
     *     
     */
    public void setZ1EDS02(Z1EDS02 value) {
        this.z1EDS02 = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDS01.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDS01 }
     *     
     */
    public Z1EDS01 getZ1EDS01() {
        return z1EDS01;
    }

    /**
     * Define el valor de la propiedad z1EDS01.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDS01 }
     *     
     */
    public void setZ1EDS01(Z1EDS01 value) {
        this.z1EDS01 = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDK11.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDK11 }
     *     
     */
    public Z1EDK11 getZ1EDK11() {
        return z1EDK11;
    }

    /**
     * Define el valor de la propiedad z1EDK11.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDK11 }
     *     
     */
    public void setZ1EDK11(Z1EDK11 value) {
        this.z1EDK11 = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDK12.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDK12 }
     *     
     */
    public Z1EDK12 getZ1EDK12() {
        return z1EDK12;
    }

    /**
     * Define el valor de la propiedad z1EDK12.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDK12 }
     *     
     */
    public void setZ1EDK12(Z1EDK12 value) {
        this.z1EDK12 = value;
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

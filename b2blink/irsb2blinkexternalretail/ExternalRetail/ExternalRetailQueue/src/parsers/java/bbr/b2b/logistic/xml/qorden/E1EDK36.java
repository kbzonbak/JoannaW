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
 *         &lt;element ref="{}CCINS" minOccurs="0"/&gt;
 *         &lt;element ref="{}CCINS_BEZEI" minOccurs="0"/&gt;
 *         &lt;element ref="{}CCNUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}EXDATBI" minOccurs="0"/&gt;
 *         &lt;element ref="{}CCNAME" minOccurs="0"/&gt;
 *         &lt;element ref="{}FAKWR" minOccurs="0"/&gt;
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
    "ccins",
    "ccinsbezei",
    "ccnum",
    "exdatbi",
    "ccname",
    "fakwr"
})
@XmlRootElement(name = "E1EDK36")
public class E1EDK36 {

    @XmlElement(name = "CCINS")
    protected CCINS ccins;
    @XmlElement(name = "CCINS_BEZEI")
    protected CCINSBEZEI ccinsbezei;
    @XmlElement(name = "CCNUM")
    protected CCNUM ccnum;
    @XmlElement(name = "EXDATBI")
    protected EXDATBI exdatbi;
    @XmlElement(name = "CCNAME")
    protected CCNAME ccname;
    @XmlElement(name = "FAKWR")
    protected FAKWR fakwr;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad ccins.
     * 
     * @return
     *     possible object is
     *     {@link CCINS }
     *     
     */
    public CCINS getCCINS() {
        return ccins;
    }

    /**
     * Define el valor de la propiedad ccins.
     * 
     * @param value
     *     allowed object is
     *     {@link CCINS }
     *     
     */
    public void setCCINS(CCINS value) {
        this.ccins = value;
    }

    /**
     * Obtiene el valor de la propiedad ccinsbezei.
     * 
     * @return
     *     possible object is
     *     {@link CCINSBEZEI }
     *     
     */
    public CCINSBEZEI getCCINSBEZEI() {
        return ccinsbezei;
    }

    /**
     * Define el valor de la propiedad ccinsbezei.
     * 
     * @param value
     *     allowed object is
     *     {@link CCINSBEZEI }
     *     
     */
    public void setCCINSBEZEI(CCINSBEZEI value) {
        this.ccinsbezei = value;
    }

    /**
     * Obtiene el valor de la propiedad ccnum.
     * 
     * @return
     *     possible object is
     *     {@link CCNUM }
     *     
     */
    public CCNUM getCCNUM() {
        return ccnum;
    }

    /**
     * Define el valor de la propiedad ccnum.
     * 
     * @param value
     *     allowed object is
     *     {@link CCNUM }
     *     
     */
    public void setCCNUM(CCNUM value) {
        this.ccnum = value;
    }

    /**
     * Obtiene el valor de la propiedad exdatbi.
     * 
     * @return
     *     possible object is
     *     {@link EXDATBI }
     *     
     */
    public EXDATBI getEXDATBI() {
        return exdatbi;
    }

    /**
     * Define el valor de la propiedad exdatbi.
     * 
     * @param value
     *     allowed object is
     *     {@link EXDATBI }
     *     
     */
    public void setEXDATBI(EXDATBI value) {
        this.exdatbi = value;
    }

    /**
     * Obtiene el valor de la propiedad ccname.
     * 
     * @return
     *     possible object is
     *     {@link CCNAME }
     *     
     */
    public CCNAME getCCNAME() {
        return ccname;
    }

    /**
     * Define el valor de la propiedad ccname.
     * 
     * @param value
     *     allowed object is
     *     {@link CCNAME }
     *     
     */
    public void setCCNAME(CCNAME value) {
        this.ccname = value;
    }

    /**
     * Obtiene el valor de la propiedad fakwr.
     * 
     * @return
     *     possible object is
     *     {@link FAKWR }
     *     
     */
    public FAKWR getFAKWR() {
        return fakwr;
    }

    /**
     * Define el valor de la propiedad fakwr.
     * 
     * @param value
     *     allowed object is
     *     {@link FAKWR }
     *     
     */
    public void setFAKWR(FAKWR value) {
        this.fakwr = value;
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

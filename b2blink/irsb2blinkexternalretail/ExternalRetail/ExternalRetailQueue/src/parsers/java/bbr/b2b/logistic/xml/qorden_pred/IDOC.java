//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:44 PM CLT 
//


package bbr.b2b.logistic.xml.qorden_pred;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element ref="{}EDI_DC40"/&gt;
 *         &lt;element ref="{}E1EDK01"/&gt;
 *         &lt;element ref="{}E1EDK14" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK03" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK04" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK05" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDKA1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK02" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK17" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK18" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK35" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDK36" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDKT1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP01" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUCFG" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDL37" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDS01" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="BEGIN" use="required"&gt;
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
    "edidc40",
    "e1EDK01",
    "e1EDK14",
    "e1EDK03",
    "e1EDK04",
    "e1EDK05",
    "e1EDKA1",
    "e1EDK02",
    "e1EDK17",
    "e1EDK18",
    "e1EDK35",
    "e1EDK36",
    "e1EDKT1",
    "e1EDP01",
    "e1CUCFG",
    "e1EDL37",
    "e1EDS01"
})
@XmlRootElement(name = "IDOC")
public class IDOC {

    @XmlElement(name = "EDI_DC40", required = true)
    protected EDIDC40 edidc40;
    @XmlElement(name = "E1EDK01", required = true)
    protected E1EDK01 e1EDK01;
    @XmlElement(name = "E1EDK14")
    protected List<E1EDK14> e1EDK14;
    @XmlElement(name = "E1EDK03")
    protected List<E1EDK03> e1EDK03;
    @XmlElement(name = "E1EDK04")
    protected List<E1EDK04> e1EDK04;
    @XmlElement(name = "E1EDK05")
    protected List<E1EDK05> e1EDK05;
    @XmlElement(name = "E1EDKA1")
    protected List<E1EDKA1> e1EDKA1;
    @XmlElement(name = "E1EDK02")
    protected List<E1EDK02> e1EDK02;
    @XmlElement(name = "E1EDK17")
    protected List<E1EDK17> e1EDK17;
    @XmlElement(name = "E1EDK18")
    protected List<E1EDK18> e1EDK18;
    @XmlElement(name = "E1EDK35")
    protected List<E1EDK35> e1EDK35;
    @XmlElement(name = "E1EDK36")
    protected List<E1EDK36> e1EDK36;
    @XmlElement(name = "E1EDKT1")
    protected List<E1EDKT1> e1EDKT1;
    @XmlElement(name = "E1EDP01")
    protected List<E1EDP01> e1EDP01;
    @XmlElement(name = "E1CUCFG")
    protected List<E1CUCFG> e1CUCFG;
    @XmlElement(name = "E1EDL37")
    protected List<E1EDL37> e1EDL37;
    @XmlElement(name = "E1EDS01")
    protected List<E1EDS01> e1EDS01;
    @XmlAttribute(name = "BEGIN", required = true)
    protected String begin;

    /**
     * Obtiene el valor de la propiedad edidc40.
     * 
     * @return
     *     possible object is
     *     {@link EDIDC40 }
     *     
     */
    public EDIDC40 getEDIDC40() {
        return edidc40;
    }

    /**
     * Define el valor de la propiedad edidc40.
     * 
     * @param value
     *     allowed object is
     *     {@link EDIDC40 }
     *     
     */
    public void setEDIDC40(EDIDC40 value) {
        this.edidc40 = value;
    }

    /**
     * Obtiene el valor de la propiedad e1EDK01.
     * 
     * @return
     *     possible object is
     *     {@link E1EDK01 }
     *     
     */
    public E1EDK01 getE1EDK01() {
        return e1EDK01;
    }

    /**
     * Define el valor de la propiedad e1EDK01.
     * 
     * @param value
     *     allowed object is
     *     {@link E1EDK01 }
     *     
     */
    public void setE1EDK01(E1EDK01 value) {
        this.e1EDK01 = value;
    }

    /**
     * Gets the value of the e1EDK14 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK14 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK14().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK14 }
     * 
     * 
     */
    public List<E1EDK14> getE1EDK14() {
        if (e1EDK14 == null) {
            e1EDK14 = new ArrayList<E1EDK14>();
        }
        return this.e1EDK14;
    }

    /**
     * Gets the value of the e1EDK03 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK03 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK03().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK03 }
     * 
     * 
     */
    public List<E1EDK03> getE1EDK03() {
        if (e1EDK03 == null) {
            e1EDK03 = new ArrayList<E1EDK03>();
        }
        return this.e1EDK03;
    }

    /**
     * Gets the value of the e1EDK04 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK04 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK04().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK04 }
     * 
     * 
     */
    public List<E1EDK04> getE1EDK04() {
        if (e1EDK04 == null) {
            e1EDK04 = new ArrayList<E1EDK04>();
        }
        return this.e1EDK04;
    }

    /**
     * Gets the value of the e1EDK05 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK05 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK05().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK05 }
     * 
     * 
     */
    public List<E1EDK05> getE1EDK05() {
        if (e1EDK05 == null) {
            e1EDK05 = new ArrayList<E1EDK05>();
        }
        return this.e1EDK05;
    }

    /**
     * Gets the value of the e1EDKA1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDKA1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDKA1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDKA1 }
     * 
     * 
     */
    public List<E1EDKA1> getE1EDKA1() {
        if (e1EDKA1 == null) {
            e1EDKA1 = new ArrayList<E1EDKA1>();
        }
        return this.e1EDKA1;
    }

    /**
     * Gets the value of the e1EDK02 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK02 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK02().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK02 }
     * 
     * 
     */
    public List<E1EDK02> getE1EDK02() {
        if (e1EDK02 == null) {
            e1EDK02 = new ArrayList<E1EDK02>();
        }
        return this.e1EDK02;
    }

    /**
     * Gets the value of the e1EDK17 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK17 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK17().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK17 }
     * 
     * 
     */
    public List<E1EDK17> getE1EDK17() {
        if (e1EDK17 == null) {
            e1EDK17 = new ArrayList<E1EDK17>();
        }
        return this.e1EDK17;
    }

    /**
     * Gets the value of the e1EDK18 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK18 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK18().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK18 }
     * 
     * 
     */
    public List<E1EDK18> getE1EDK18() {
        if (e1EDK18 == null) {
            e1EDK18 = new ArrayList<E1EDK18>();
        }
        return this.e1EDK18;
    }

    /**
     * Gets the value of the e1EDK35 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK35 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK35().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK35 }
     * 
     * 
     */
    public List<E1EDK35> getE1EDK35() {
        if (e1EDK35 == null) {
            e1EDK35 = new ArrayList<E1EDK35>();
        }
        return this.e1EDK35;
    }

    /**
     * Gets the value of the e1EDK36 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDK36 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDK36().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDK36 }
     * 
     * 
     */
    public List<E1EDK36> getE1EDK36() {
        if (e1EDK36 == null) {
            e1EDK36 = new ArrayList<E1EDK36>();
        }
        return this.e1EDK36;
    }

    /**
     * Gets the value of the e1EDKT1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDKT1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDKT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDKT1 }
     * 
     * 
     */
    public List<E1EDKT1> getE1EDKT1() {
        if (e1EDKT1 == null) {
            e1EDKT1 = new ArrayList<E1EDKT1>();
        }
        return this.e1EDKT1;
    }

    /**
     * Gets the value of the e1EDP01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP01 }
     * 
     * 
     */
    public List<E1EDP01> getE1EDP01() {
        if (e1EDP01 == null) {
            e1EDP01 = new ArrayList<E1EDP01>();
        }
        return this.e1EDP01;
    }

    /**
     * Gets the value of the e1CUCFG property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1CUCFG property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1CUCFG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1CUCFG }
     * 
     * 
     */
    public List<E1CUCFG> getE1CUCFG() {
        if (e1CUCFG == null) {
            e1CUCFG = new ArrayList<E1CUCFG>();
        }
        return this.e1CUCFG;
    }

    /**
     * Gets the value of the e1EDL37 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDL37 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDL37().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDL37 }
     * 
     * 
     */
    public List<E1EDL37> getE1EDL37() {
        if (e1EDL37 == null) {
            e1EDL37 = new ArrayList<E1EDL37>();
        }
        return this.e1EDL37;
    }

    /**
     * Gets the value of the e1EDS01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDS01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDS01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDS01 }
     * 
     * 
     */
    public List<E1EDS01> getE1EDS01() {
        if (e1EDS01 == null) {
            e1EDS01 = new ArrayList<E1EDS01>();
        }
        return this.e1EDS01;
    }

    /**
     * Obtiene el valor de la propiedad begin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBEGIN() {
        return begin;
    }

    /**
     * Define el valor de la propiedad begin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBEGIN(String value) {
        this.begin = value;
    }

}

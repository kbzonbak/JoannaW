//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

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
 *         &lt;element ref="{}POSEX" minOccurs="0"/&gt;
 *         &lt;element ref="{}CONFIG_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}ROOT_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}SCE" minOccurs="0"/&gt;
 *         &lt;element ref="{}KBNAME" minOccurs="0"/&gt;
 *         &lt;element ref="{}KBVERSION" minOccurs="0"/&gt;
 *         &lt;element ref="{}COMPLETE" minOccurs="0"/&gt;
 *         &lt;element ref="{}CONSISTENT" minOccurs="0"/&gt;
 *         &lt;element ref="{}CFGINFO" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUINS" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUPRT" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUVAL" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUBLB" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "rootid",
    "sce",
    "kbname",
    "kbversion",
    "complete",
    "consistent",
    "cfginfo",
    "e1CUINS",
    "e1CUPRT",
    "e1CUVAL",
    "e1CUBLB"
})
@XmlRootElement(name = "E1CUCFG")
public class E1CUCFG {

    @XmlElement(name = "POSEX")
    protected POSEX posex;
    @XmlElement(name = "CONFIG_ID")
    protected CONFIGID configid;
    @XmlElement(name = "ROOT_ID")
    protected ROOTID rootid;
    @XmlElement(name = "SCE")
    protected SCE sce;
    @XmlElement(name = "KBNAME")
    protected KBNAME kbname;
    @XmlElement(name = "KBVERSION")
    protected KBVERSION kbversion;
    @XmlElement(name = "COMPLETE")
    protected COMPLETE complete;
    @XmlElement(name = "CONSISTENT")
    protected CONSISTENT consistent;
    @XmlElement(name = "CFGINFO")
    protected CFGINFO cfginfo;
    @XmlElement(name = "E1CUINS")
    protected List<E1CUINS> e1CUINS;
    @XmlElement(name = "E1CUPRT")
    protected List<E1CUPRT> e1CUPRT;
    @XmlElement(name = "E1CUVAL")
    protected List<E1CUVAL> e1CUVAL;
    @XmlElement(name = "E1CUBLB")
    protected List<E1CUBLB> e1CUBLB;
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
     * Obtiene el valor de la propiedad rootid.
     * 
     * @return
     *     possible object is
     *     {@link ROOTID }
     *     
     */
    public ROOTID getROOTID() {
        return rootid;
    }

    /**
     * Define el valor de la propiedad rootid.
     * 
     * @param value
     *     allowed object is
     *     {@link ROOTID }
     *     
     */
    public void setROOTID(ROOTID value) {
        this.rootid = value;
    }

    /**
     * Obtiene el valor de la propiedad sce.
     * 
     * @return
     *     possible object is
     *     {@link SCE }
     *     
     */
    public SCE getSCE() {
        return sce;
    }

    /**
     * Define el valor de la propiedad sce.
     * 
     * @param value
     *     allowed object is
     *     {@link SCE }
     *     
     */
    public void setSCE(SCE value) {
        this.sce = value;
    }

    /**
     * Obtiene el valor de la propiedad kbname.
     * 
     * @return
     *     possible object is
     *     {@link KBNAME }
     *     
     */
    public KBNAME getKBNAME() {
        return kbname;
    }

    /**
     * Define el valor de la propiedad kbname.
     * 
     * @param value
     *     allowed object is
     *     {@link KBNAME }
     *     
     */
    public void setKBNAME(KBNAME value) {
        this.kbname = value;
    }

    /**
     * Obtiene el valor de la propiedad kbversion.
     * 
     * @return
     *     possible object is
     *     {@link KBVERSION }
     *     
     */
    public KBVERSION getKBVERSION() {
        return kbversion;
    }

    /**
     * Define el valor de la propiedad kbversion.
     * 
     * @param value
     *     allowed object is
     *     {@link KBVERSION }
     *     
     */
    public void setKBVERSION(KBVERSION value) {
        this.kbversion = value;
    }

    /**
     * Obtiene el valor de la propiedad complete.
     * 
     * @return
     *     possible object is
     *     {@link COMPLETE }
     *     
     */
    public COMPLETE getCOMPLETE() {
        return complete;
    }

    /**
     * Define el valor de la propiedad complete.
     * 
     * @param value
     *     allowed object is
     *     {@link COMPLETE }
     *     
     */
    public void setCOMPLETE(COMPLETE value) {
        this.complete = value;
    }

    /**
     * Obtiene el valor de la propiedad consistent.
     * 
     * @return
     *     possible object is
     *     {@link CONSISTENT }
     *     
     */
    public CONSISTENT getCONSISTENT() {
        return consistent;
    }

    /**
     * Define el valor de la propiedad consistent.
     * 
     * @param value
     *     allowed object is
     *     {@link CONSISTENT }
     *     
     */
    public void setCONSISTENT(CONSISTENT value) {
        this.consistent = value;
    }

    /**
     * Obtiene el valor de la propiedad cfginfo.
     * 
     * @return
     *     possible object is
     *     {@link CFGINFO }
     *     
     */
    public CFGINFO getCFGINFO() {
        return cfginfo;
    }

    /**
     * Define el valor de la propiedad cfginfo.
     * 
     * @param value
     *     allowed object is
     *     {@link CFGINFO }
     *     
     */
    public void setCFGINFO(CFGINFO value) {
        this.cfginfo = value;
    }

    /**
     * Gets the value of the e1CUINS property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1CUINS property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1CUINS().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1CUINS }
     * 
     * 
     */
    public List<E1CUINS> getE1CUINS() {
        if (e1CUINS == null) {
            e1CUINS = new ArrayList<E1CUINS>();
        }
        return this.e1CUINS;
    }

    /**
     * Gets the value of the e1CUPRT property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1CUPRT property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1CUPRT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1CUPRT }
     * 
     * 
     */
    public List<E1CUPRT> getE1CUPRT() {
        if (e1CUPRT == null) {
            e1CUPRT = new ArrayList<E1CUPRT>();
        }
        return this.e1CUPRT;
    }

    /**
     * Gets the value of the e1CUVAL property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1CUVAL property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1CUVAL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1CUVAL }
     * 
     * 
     */
    public List<E1CUVAL> getE1CUVAL() {
        if (e1CUVAL == null) {
            e1CUVAL = new ArrayList<E1CUVAL>();
        }
        return this.e1CUVAL;
    }

    /**
     * Gets the value of the e1CUBLB property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1CUBLB property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1CUBLB().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1CUBLB }
     * 
     * 
     */
    public List<E1CUBLB> getE1CUBLB() {
        if (e1CUBLB == null) {
            e1CUBLB = new ArrayList<E1CUBLB>();
        }
        return this.e1CUBLB;
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

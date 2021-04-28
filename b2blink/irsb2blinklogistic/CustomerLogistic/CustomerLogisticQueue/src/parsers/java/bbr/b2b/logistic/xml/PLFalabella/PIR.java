//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.07 a las 11:08:13 AM CLT 
//


package bbr.b2b.logistic.xml.PLFalabella;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element ref="{}NRO_OC"/&gt;
 *         &lt;element ref="{}FECHA_DESPACHO"/&gt;
 *         &lt;element ref="{}HORA_DESPACHO"/&gt;
 *         &lt;element ref="{}TOTAL_BULTOS"/&gt;
 *         &lt;element ref="{}TOTAL_TOTES"/&gt;
 *         &lt;element ref="{}TOTAL_COLGADOS"/&gt;
 *         &lt;element ref="{}NRO_SERIE_FACT"/&gt;
 *         &lt;element ref="{}NRO_FACTURA"/&gt;
 *         &lt;element ref="{}ALMACEN"/&gt;
 *         &lt;element ref="{}GUIAS_DESPACHO"/&gt;
 *         &lt;element ref="{}PRODUCTO"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nrooc",
    "fechadespacho",
    "horadespacho",
    "totalbultos",
    "totaltotes",
    "totalcolgados",
    "nroseriefact",
    "nrofactura",
    "almacen",
    "guiasdespacho",
    "producto"
})
@XmlRootElement(name = "PIR")
public class PIR {

    @XmlElement(name = "NRO_OC", required = true)
    protected BigInteger nrooc;
    @XmlElement(name = "FECHA_DESPACHO", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String fechadespacho;
    @XmlElement(name = "HORA_DESPACHO", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String horadespacho;
    @XmlElement(name = "TOTAL_BULTOS", required = true)
    protected BigInteger totalbultos;
    @XmlElement(name = "TOTAL_TOTES", required = true)
    protected BigInteger totaltotes;
    @XmlElement(name = "TOTAL_COLGADOS", required = true)
    protected BigInteger totalcolgados;
    @XmlElement(name = "NRO_SERIE_FACT", required = true)
    protected NROSERIEFACT nroseriefact;
    @XmlElement(name = "NRO_FACTURA", required = true)
    protected BigInteger nrofactura;
    @XmlElement(name = "ALMACEN", required = true)
    protected BigInteger almacen;
    @XmlElement(name = "GUIAS_DESPACHO", required = true)
    protected GUIASDESPACHO guiasdespacho;
    @XmlElement(name = "PRODUCTO", required = true)
    protected PRODUCTO producto;
    @XmlAttribute(name = "num", required = true)
    protected BigInteger num;

    /**
     * Obtiene el valor de la propiedad nrooc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNROOC() {
        return nrooc;
    }

    /**
     * Define el valor de la propiedad nrooc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNROOC(BigInteger value) {
        this.nrooc = value;
    }

    /**
     * Obtiene el valor de la propiedad fechadespacho.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHADESPACHO() {
        return fechadespacho;
    }

    /**
     * Define el valor de la propiedad fechadespacho.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHADESPACHO(String value) {
        this.fechadespacho = value;
    }

    /**
     * Obtiene el valor de la propiedad horadespacho.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHORADESPACHO() {
        return horadespacho;
    }

    /**
     * Define el valor de la propiedad horadespacho.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHORADESPACHO(String value) {
        this.horadespacho = value;
    }

    /**
     * Obtiene el valor de la propiedad totalbultos.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTOTALBULTOS() {
        return totalbultos;
    }

    /**
     * Define el valor de la propiedad totalbultos.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTOTALBULTOS(BigInteger value) {
        this.totalbultos = value;
    }

    /**
     * Obtiene el valor de la propiedad totaltotes.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTOTALTOTES() {
        return totaltotes;
    }

    /**
     * Define el valor de la propiedad totaltotes.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTOTALTOTES(BigInteger value) {
        this.totaltotes = value;
    }

    /**
     * Obtiene el valor de la propiedad totalcolgados.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTOTALCOLGADOS() {
        return totalcolgados;
    }

    /**
     * Define el valor de la propiedad totalcolgados.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTOTALCOLGADOS(BigInteger value) {
        this.totalcolgados = value;
    }

    /**
     * Obtiene el valor de la propiedad nroseriefact.
     * 
     * @return
     *     possible object is
     *     {@link NROSERIEFACT }
     *     
     */
    public NROSERIEFACT getNROSERIEFACT() {
        return nroseriefact;
    }

    /**
     * Define el valor de la propiedad nroseriefact.
     * 
     * @param value
     *     allowed object is
     *     {@link NROSERIEFACT }
     *     
     */
    public void setNROSERIEFACT(NROSERIEFACT value) {
        this.nroseriefact = value;
    }

    /**
     * Obtiene el valor de la propiedad nrofactura.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNROFACTURA() {
        return nrofactura;
    }

    /**
     * Define el valor de la propiedad nrofactura.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNROFACTURA(BigInteger value) {
        this.nrofactura = value;
    }

    /**
     * Obtiene el valor de la propiedad almacen.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getALMACEN() {
        return almacen;
    }

    /**
     * Define el valor de la propiedad almacen.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setALMACEN(BigInteger value) {
        this.almacen = value;
    }

    /**
     * Obtiene el valor de la propiedad guiasdespacho.
     * 
     * @return
     *     possible object is
     *     {@link GUIASDESPACHO }
     *     
     */
    public GUIASDESPACHO getGUIASDESPACHO() {
        return guiasdespacho;
    }

    /**
     * Define el valor de la propiedad guiasdespacho.
     * 
     * @param value
     *     allowed object is
     *     {@link GUIASDESPACHO }
     *     
     */
    public void setGUIASDESPACHO(GUIASDESPACHO value) {
        this.guiasdespacho = value;
    }

    /**
     * Obtiene el valor de la propiedad producto.
     * 
     * @return
     *     possible object is
     *     {@link PRODUCTO }
     *     
     */
    public PRODUCTO getPRODUCTO() {
        return producto;
    }

    /**
     * Define el valor de la propiedad producto.
     * 
     * @param value
     *     allowed object is
     *     {@link PRODUCTO }
     *     
     */
    public void setPRODUCTO(PRODUCTO value) {
        this.producto = value;
    }

    /**
     * Obtiene el valor de la propiedad num.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNum() {
        return num;
    }

    /**
     * Define el valor de la propiedad num.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNum(BigInteger value) {
        this.num = value;
    }

}

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
 *         &lt;element ref="{}UPC"/&gt;
 *         &lt;element ref="{}DESCRIPCION_LARGA"/&gt;
 *         &lt;element ref="{}NRO_LOCAL"/&gt;
 *         &lt;element ref="{}LOCAL"/&gt;
 *         &lt;element ref="{}CANTIDAD"/&gt;
 *         &lt;element ref="{}TIPO_EMBALAJE"/&gt;
 *         &lt;element ref="{}NRO_BULTO"/&gt;
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
    "upc",
    "descripcionlarga",
    "nrolocal",
    "local",
    "cantidad",
    "tipoembalaje",
    "nrobulto"
})
@XmlRootElement(name = "PRODUCTO_ROW")
public class PRODUCTOROW {

    @XmlElement(name = "UPC", required = true)
    protected BigInteger upc;
    @XmlElement(name = "DESCRIPCION_LARGA", required = true)
    protected String descripcionlarga;
    @XmlElement(name = "NRO_LOCAL", required = true)
    protected String nrolocal;
    @XmlElement(name = "LOCAL", required = true)
    protected String local;
    @XmlElement(name = "CANTIDAD", required = true)
    protected BigInteger cantidad;
    @XmlElement(name = "TIPO_EMBALAJE", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String tipoembalaje;
    @XmlElement(name = "NRO_BULTO", required = true)
    protected BigInteger nrobulto;
    @XmlAttribute(name = "num", required = true)
    protected BigInteger num;

    /**
     * Obtiene el valor de la propiedad upc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUPC() {
        return upc;
    }

    /**
     * Define el valor de la propiedad upc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUPC(BigInteger value) {
        this.upc = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcionlarga.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIPCIONLARGA() {
        return descripcionlarga;
    }

    /**
     * Define el valor de la propiedad descripcionlarga.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIPCIONLARGA(String value) {
        this.descripcionlarga = value;
    }

    /**
     * Obtiene el valor de la propiedad nrolocal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNROLOCAL() {
        return nrolocal;
    }

    /**
     * Define el valor de la propiedad nrolocal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNROLOCAL(String value) {
        this.nrolocal = value;
    }

    /**
     * Obtiene el valor de la propiedad local.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOCAL() {
        return local;
    }

    /**
     * Define el valor de la propiedad local.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOCAL(String value) {
        this.local = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCANTIDAD() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCANTIDAD(BigInteger value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoembalaje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOEMBALAJE() {
        return tipoembalaje;
    }

    /**
     * Define el valor de la propiedad tipoembalaje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOEMBALAJE(String value) {
        this.tipoembalaje = value;
    }

    /**
     * Obtiene el valor de la propiedad nrobulto.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNROBULTO() {
        return nrobulto;
    }

    /**
     * Define el valor de la propiedad nrobulto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNROBULTO(BigInteger value) {
        this.nrobulto = value;
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

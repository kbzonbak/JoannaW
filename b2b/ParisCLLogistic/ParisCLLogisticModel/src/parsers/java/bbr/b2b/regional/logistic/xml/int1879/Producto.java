//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.07 at 05:11:55 PM CLT 
//


package bbr.b2b.regional.logistic.xml.int1879;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}cod_producto"/>
 *         &lt;element ref="{}cod_departmento"/>
 *         &lt;element ref="{}ctdad_enviada"/>
 *         &lt;element ref="{}ctdad_entregada"/>
 *         &lt;element ref="{}unid_medida"/>
 *         &lt;element ref="{}num_linea"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codProducto",
    "codDepartmento",
    "ctdadEnviada",
    "ctdadEntregada",
    "unidMedida",
    "numLinea"
})
@XmlRootElement(name = "producto")
public class Producto {

    @XmlElement(name = "cod_producto", required = true)
    protected String codProducto;
    @XmlElement(name = "cod_departmento", required = true)
    protected String codDepartmento;
    @XmlElement(name = "ctdad_enviada")
    protected double ctdadEnviada;
    @XmlElement(name = "ctdad_entregada")
    protected double ctdadEntregada;
    @XmlElement(name = "unid_medida", required = true)
    protected String unidMedida;
    @XmlElement(name = "num_linea")
    protected int numLinea;

    /**
     * Gets the value of the codProducto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProducto() {
        return codProducto;
    }

    /**
     * Sets the value of the codProducto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProducto(String value) {
        this.codProducto = value;
    }

    /**
     * Gets the value of the codDepartmento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepartmento() {
        return codDepartmento;
    }

    /**
     * Sets the value of the codDepartmento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepartmento(String value) {
        this.codDepartmento = value;
    }

    /**
     * Gets the value of the ctdadEnviada property.
     * 
     */
    public double getCtdadEnviada() {
        return ctdadEnviada;
    }

    /**
     * Sets the value of the ctdadEnviada property.
     * 
     */
    public void setCtdadEnviada(double value) {
        this.ctdadEnviada = value;
    }

    /**
     * Gets the value of the ctdadEntregada property.
     * 
     */
    public double getCtdadEntregada() {
        return ctdadEntregada;
    }

    /**
     * Sets the value of the ctdadEntregada property.
     * 
     */
    public void setCtdadEntregada(double value) {
        this.ctdadEntregada = value;
    }

    /**
     * Gets the value of the unidMedida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnidMedida() {
        return unidMedida;
    }

    /**
     * Sets the value of the unidMedida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnidMedida(String value) {
        this.unidMedida = value;
    }

    /**
     * Gets the value of the numLinea property.
     * 
     */
    public int getNumLinea() {
        return numLinea;
    }

    /**
     * Sets the value of the numLinea property.
     * 
     */
    public void setNumLinea(int value) {
        this.numLinea = value;
    }

}

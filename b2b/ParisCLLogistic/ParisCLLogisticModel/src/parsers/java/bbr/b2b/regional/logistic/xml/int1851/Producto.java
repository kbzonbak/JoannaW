//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.08 at 10:26:28 AM CLST 
//


package bbr.b2b.regional.logistic.xml.int1851;

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
 *         &lt;element ref="{}cod_proveedor"/>
 *         &lt;element ref="{}lead_time"/>
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
    "codProveedor",
    "leadTime"
})
@XmlRootElement(name = "producto")
public class Producto {

    @XmlElement(name = "cod_producto", required = true)
    protected String codProducto;
    @XmlElement(name = "cod_proveedor", required = true)
    protected String codProveedor;
    @XmlElement(name = "lead_time")
    protected int leadTime;

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
     * Gets the value of the codProveedor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProveedor() {
        return codProveedor;
    }

    /**
     * Sets the value of the codProveedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProveedor(String value) {
        this.codProveedor = value;
    }

    /**
     * Gets the value of the leadTime property.
     * 
     */
    public int getLeadTime() {
        return leadTime;
    }

    /**
     * Sets the value of the leadTime property.
     * 
     */
    public void setLeadTime(int value) {
        this.leadTime = value;
    }

}
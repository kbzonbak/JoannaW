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
 *         &lt;element ref="{}cod_tipo"/>
 *         &lt;element ref="{}cod_descripcion"/>
 *         &lt;element ref="{}cod_id"/>
 *         &lt;element ref="{}cd_id_descripcion"/>
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
    "codTipo",
    "codDescripcion",
    "codId",
    "cdIdDescripcion"
})
@XmlRootElement(name = "estado")
public class Estado {

    @XmlElement(name = "cod_tipo", required = true)
    protected String codTipo;
    @XmlElement(name = "cod_descripcion", required = true)
    protected String codDescripcion;
    @XmlElement(name = "cod_id", required = true)
    protected String codId;
    @XmlElement(name = "cd_id_descripcion", required = true)
    protected String cdIdDescripcion;

    /**
     * Gets the value of the codTipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipo() {
        return codTipo;
    }

    /**
     * Sets the value of the codTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipo(String value) {
        this.codTipo = value;
    }

    /**
     * Gets the value of the codDescripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDescripcion() {
        return codDescripcion;
    }

    /**
     * Sets the value of the codDescripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDescripcion(String value) {
        this.codDescripcion = value;
    }

    /**
     * Gets the value of the codId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodId() {
        return codId;
    }

    /**
     * Sets the value of the codId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodId(String value) {
        this.codId = value;
    }

    /**
     * Gets the value of the cdIdDescripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCdIdDescripcion() {
        return cdIdDescripcion;
    }

    /**
     * Sets the value of the cdIdDescripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCdIdDescripcion(String value) {
        this.cdIdDescripcion = value;
    }

}
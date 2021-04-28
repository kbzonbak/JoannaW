//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:46:49 PM CLT 
//


package bbr.b2b.logistic.xml.ordercancellation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CustomSupplierPartyType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CustomSupplierPartyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Organization" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesChannelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesSection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesOffice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomSupplierPartyType", namespace = "http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0", propOrder = {
    "organization",
    "salesChannelCode",
    "salesSection",
    "salesOffice",
    "salesGroup"
})
public class CustomSupplierPartyType {

    @XmlElement(name = "Organization")
    protected String organization;
    @XmlElement(name = "SalesChannelCode")
    protected String salesChannelCode;
    @XmlElement(name = "SalesSection")
    protected String salesSection;
    @XmlElement(name = "SalesOffice")
    protected String salesOffice;
    @XmlElement(name = "SalesGroup")
    protected String salesGroup;

    /**
     * Obtiene el valor de la propiedad organization.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Define el valor de la propiedad organization.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganization(String value) {
        this.organization = value;
    }

    /**
     * Obtiene el valor de la propiedad salesChannelCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesChannelCode() {
        return salesChannelCode;
    }

    /**
     * Define el valor de la propiedad salesChannelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesChannelCode(String value) {
        this.salesChannelCode = value;
    }

    /**
     * Obtiene el valor de la propiedad salesSection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesSection() {
        return salesSection;
    }

    /**
     * Define el valor de la propiedad salesSection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesSection(String value) {
        this.salesSection = value;
    }

    /**
     * Obtiene el valor de la propiedad salesOffice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesOffice() {
        return salesOffice;
    }

    /**
     * Define el valor de la propiedad salesOffice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesOffice(String value) {
        this.salesOffice = value;
    }

    /**
     * Obtiene el valor de la propiedad salesGroup.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesGroup() {
        return salesGroup;
    }

    /**
     * Define el valor de la propiedad salesGroup.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesGroup(String value) {
        this.salesGroup = value;
    }

}

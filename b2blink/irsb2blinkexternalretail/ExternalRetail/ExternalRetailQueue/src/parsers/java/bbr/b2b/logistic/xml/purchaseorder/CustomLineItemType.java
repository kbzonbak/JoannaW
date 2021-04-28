//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:39:41 PM CLT 
//


package bbr.b2b.logistic.xml.purchaseorder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CustomLineItemType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CustomLineItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cextActionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PositionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AcceptanceRequiredIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomLineItemType", namespace = "http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-CommonAggregateComponents-2.0", propOrder = {
    "cextActionCode",
    "positionType",
    "acceptanceRequiredIndicator"
})
public class CustomLineItemType {

    protected String cextActionCode;
    @XmlElement(name = "PositionType")
    protected String positionType;
    @XmlElement(name = "AcceptanceRequiredIndicator")
    protected String acceptanceRequiredIndicator;

    /**
     * Obtiene el valor de la propiedad cextActionCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCextActionCode() {
        return cextActionCode;
    }

    /**
     * Define el valor de la propiedad cextActionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCextActionCode(String value) {
        this.cextActionCode = value;
    }

    /**
     * Obtiene el valor de la propiedad positionType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionType() {
        return positionType;
    }

    /**
     * Define el valor de la propiedad positionType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionType(String value) {
        this.positionType = value;
    }

    /**
     * Obtiene el valor de la propiedad acceptanceRequiredIndicator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcceptanceRequiredIndicator() {
        return acceptanceRequiredIndicator;
    }

    /**
     * Define el valor de la propiedad acceptanceRequiredIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcceptanceRequiredIndicator(String value) {
        this.acceptanceRequiredIndicator = value;
    }

}

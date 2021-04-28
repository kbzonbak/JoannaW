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
 * <p>Clase Java para CustomOrderType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CustomOrderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrderCategory" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="0"/&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PricingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OrderClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocComType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LanguageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyExchange" type="{http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-Order-2.0}CurrencyExchangeType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomOrderType", namespace = "http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-Order-2.0", propOrder = {
    "orderCategory",
    "orderType",
    "pricingDate",
    "orderClass",
    "docComType",
    "languageID",
    "currencyExchange"
})
public class CustomOrderType {

    @XmlElement(name = "OrderCategory")
    protected String orderCategory;
    @XmlElement(name = "OrderType")
    protected String orderType;
    @XmlElement(name = "PricingDate")
    protected String pricingDate;
    @XmlElement(name = "OrderClass")
    protected String orderClass;
    @XmlElement(name = "DocComType")
    protected String docComType;
    @XmlElement(name = "LanguageID")
    protected String languageID;
    @XmlElement(name = "CurrencyExchange")
    protected CurrencyExchangeType currencyExchange;

    /**
     * Obtiene el valor de la propiedad orderCategory.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderCategory() {
        return orderCategory;
    }

    /**
     * Define el valor de la propiedad orderCategory.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderCategory(String value) {
        this.orderCategory = value;
    }

    /**
     * Obtiene el valor de la propiedad orderType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Define el valor de la propiedad orderType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Obtiene el valor de la propiedad pricingDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingDate() {
        return pricingDate;
    }

    /**
     * Define el valor de la propiedad pricingDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingDate(String value) {
        this.pricingDate = value;
    }

    /**
     * Obtiene el valor de la propiedad orderClass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderClass() {
        return orderClass;
    }

    /**
     * Define el valor de la propiedad orderClass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderClass(String value) {
        this.orderClass = value;
    }

    /**
     * Obtiene el valor de la propiedad docComType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocComType() {
        return docComType;
    }

    /**
     * Define el valor de la propiedad docComType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocComType(String value) {
        this.docComType = value;
    }

    /**
     * Obtiene el valor de la propiedad languageID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageID() {
        return languageID;
    }

    /**
     * Define el valor de la propiedad languageID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageID(String value) {
        this.languageID = value;
    }

    /**
     * Obtiene el valor de la propiedad currencyExchange.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyExchangeType }
     *     
     */
    public CurrencyExchangeType getCurrencyExchange() {
        return currencyExchange;
    }

    /**
     * Define el valor de la propiedad currencyExchange.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyExchangeType }
     *     
     */
    public void setCurrencyExchange(CurrencyExchangeType value) {
        this.currencyExchange = value;
    }

}

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
 * <p>Clase Java para CurrencyExchangeType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CurrencyExchangeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SourceCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SourceUnitBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TargetCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TargetUnitBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ConversionRate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RoundingFactor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ConversionTypeCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ConversionRateDateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SourceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyExchangeType", namespace = "http://xmlns.cencosud.corp/Core/EBO/UBL/Custom-UBL-Order-2.0", propOrder = {
    "sourceCurrencyCode",
    "sourceUnitBase",
    "targetCurrencyCode",
    "targetUnitBase",
    "conversionRate",
    "roundingFactor",
    "conversionTypeCode",
    "conversionRateDateTime",
    "sourceCode"
})
public class CurrencyExchangeType {

    @XmlElement(name = "SourceCurrencyCode")
    protected String sourceCurrencyCode;
    @XmlElement(name = "SourceUnitBase")
    protected String sourceUnitBase;
    @XmlElement(name = "TargetCurrencyCode")
    protected String targetCurrencyCode;
    @XmlElement(name = "TargetUnitBase")
    protected String targetUnitBase;
    @XmlElement(name = "ConversionRate")
    protected String conversionRate;
    @XmlElement(name = "RoundingFactor")
    protected String roundingFactor;
    @XmlElement(name = "ConversionTypeCode")
    protected String conversionTypeCode;
    @XmlElement(name = "ConversionRateDateTime")
    protected String conversionRateDateTime;
    @XmlElement(name = "SourceCode")
    protected String sourceCode;

    /**
     * Obtiene el valor de la propiedad sourceCurrencyCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    /**
     * Define el valor de la propiedad sourceCurrencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCurrencyCode(String value) {
        this.sourceCurrencyCode = value;
    }

    /**
     * Obtiene el valor de la propiedad sourceUnitBase.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceUnitBase() {
        return sourceUnitBase;
    }

    /**
     * Define el valor de la propiedad sourceUnitBase.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceUnitBase(String value) {
        this.sourceUnitBase = value;
    }

    /**
     * Obtiene el valor de la propiedad targetCurrencyCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    /**
     * Define el valor de la propiedad targetCurrencyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetCurrencyCode(String value) {
        this.targetCurrencyCode = value;
    }

    /**
     * Obtiene el valor de la propiedad targetUnitBase.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetUnitBase() {
        return targetUnitBase;
    }

    /**
     * Define el valor de la propiedad targetUnitBase.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetUnitBase(String value) {
        this.targetUnitBase = value;
    }

    /**
     * Obtiene el valor de la propiedad conversionRate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionRate() {
        return conversionRate;
    }

    /**
     * Define el valor de la propiedad conversionRate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionRate(String value) {
        this.conversionRate = value;
    }

    /**
     * Obtiene el valor de la propiedad roundingFactor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoundingFactor() {
        return roundingFactor;
    }

    /**
     * Define el valor de la propiedad roundingFactor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoundingFactor(String value) {
        this.roundingFactor = value;
    }

    /**
     * Obtiene el valor de la propiedad conversionTypeCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionTypeCode() {
        return conversionTypeCode;
    }

    /**
     * Define el valor de la propiedad conversionTypeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionTypeCode(String value) {
        this.conversionTypeCode = value;
    }

    /**
     * Obtiene el valor de la propiedad conversionRateDateTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionRateDateTime() {
        return conversionRateDateTime;
    }

    /**
     * Define el valor de la propiedad conversionRateDateTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionRateDateTime(String value) {
        this.conversionRateDateTime = value;
    }

    /**
     * Obtiene el valor de la propiedad sourceCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Define el valor de la propiedad sourceCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceCode(String value) {
        this.sourceCode = value;
    }

}

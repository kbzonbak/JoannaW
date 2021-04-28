//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:49:33 PM CLT 
//


package bbr.b2b.logistic.xml.vendor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}Field1"/&gt;
 *         &lt;element ref="{}Field2"/&gt;
 *         &lt;element ref="{}Field3"/&gt;
 *         &lt;element ref="{}Field4"/&gt;
 *         &lt;element ref="{}Field5"/&gt;
 *         &lt;element ref="{}Field6"/&gt;
 *         &lt;element ref="{}Field7"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "field1",
    "field2",
    "field3",
    "field4",
    "field5",
    "field6",
    "field7"
})
@XmlRootElement(name = "V")
public class V {

    @XmlElement(name = "Field1", required = true)
    protected String field1;
    @XmlElement(name = "Field2", required = true)
    protected String field2;
    @XmlElement(name = "Field3", required = true)
    protected String field3;
    @XmlElement(name = "Field4", required = true)
    protected String field4;
    @XmlElement(name = "Field5", required = true)
    protected String field5;
    @XmlElement(name = "Field6", required = true)
    protected String field6;
    @XmlElement(name = "Field7", required = true)
    protected String field7;

    /**
     * Obtiene el valor de la propiedad field1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField1() {
        return field1;
    }

    /**
     * Define el valor de la propiedad field1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField1(String value) {
        this.field1 = value;
    }

    /**
     * Obtiene el valor de la propiedad field2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField2() {
        return field2;
    }

    /**
     * Define el valor de la propiedad field2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField2(String value) {
        this.field2 = value;
    }

    /**
     * Obtiene el valor de la propiedad field3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField3() {
        return field3;
    }

    /**
     * Define el valor de la propiedad field3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField3(String value) {
        this.field3 = value;
    }

    /**
     * Obtiene el valor de la propiedad field4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField4() {
        return field4;
    }

    /**
     * Define el valor de la propiedad field4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField4(String value) {
        this.field4 = value;
    }

    /**
     * Obtiene el valor de la propiedad field5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField5() {
        return field5;
    }

    /**
     * Define el valor de la propiedad field5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField5(String value) {
        this.field5 = value;
    }

    /**
     * Obtiene el valor de la propiedad field6.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField6() {
        return field6;
    }

    /**
     * Define el valor de la propiedad field6.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField6(String value) {
        this.field6 = value;
    }

    /**
     * Obtiene el valor de la propiedad field7.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField7() {
        return field7;
    }

    /**
     * Define el valor de la propiedad field7.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField7(String value) {
        this.field7 = value;
    }

}

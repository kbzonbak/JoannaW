
package cl.paperless.webapp.online.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="args0" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="args1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="args2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="args3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="args4" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="args5" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "args0",
    "args1",
    "args2",
    "args3",
    "args4",
    "args5"
})
@XmlRootElement(name = "OnlineGenerationCT")
public class OnlineGenerationCT {

    protected Integer args0;
    protected String args1;
    protected String args2;
    protected String args3;
    protected Integer args4;
    protected Integer args5;

    /**
     * Gets the value of the args0 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getArgs0() {
        return args0;
    }

    /**
     * Sets the value of the args0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setArgs0(Integer value) {
        this.args0 = value;
    }

    /**
     * Gets the value of the args1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgs1() {
        return args1;
    }

    /**
     * Sets the value of the args1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgs1(String value) {
        this.args1 = value;
    }

    /**
     * Gets the value of the args2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgs2() {
        return args2;
    }

    /**
     * Sets the value of the args2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgs2(String value) {
        this.args2 = value;
    }

    /**
     * Gets the value of the args3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgs3() {
        return args3;
    }

    /**
     * Sets the value of the args3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgs3(String value) {
        this.args3 = value;
    }

    /**
     * Gets the value of the args4 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getArgs4() {
        return args4;
    }

    /**
     * Sets the value of the args4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setArgs4(Integer value) {
        this.args4 = value;
    }

    /**
     * Gets the value of the args5 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getArgs5() {
        return args5;
    }

    /**
     * Sets the value of the args5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setArgs5(Integer value) {
        this.args5 = value;
    }

}

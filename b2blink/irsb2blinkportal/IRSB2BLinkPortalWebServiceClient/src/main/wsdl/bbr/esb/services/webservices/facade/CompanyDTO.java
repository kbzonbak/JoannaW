
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for companyDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="companyDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO">
 *       &lt;sequence>
 *         &lt;element name="as2id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientavaliable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dafaultmaxdelayendflow" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="encrypt" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="encryptpassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastclientcheck" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="monitoreable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companyDTO", propOrder = {
    "as2Id",
    "clientavaliable",
    "dafaultmaxdelayendflow",
    "encrypt",
    "encryptpassword",
    "lastclientcheck",
    "monitoreable",
    "name",
    "rut"
})
public class CompanyDTO
    extends ElementDTO
{

    @XmlElement(name = "as2id")
    protected String as2Id;
    protected boolean clientavaliable;
    protected Integer dafaultmaxdelayendflow;
    protected boolean encrypt;
    protected String encryptpassword;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastclientcheck;
    protected boolean monitoreable;
    protected String name;
    protected String rut;

    /**
     * Gets the value of the as2Id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAs2Id() {
        return as2Id;
    }

    /**
     * Sets the value of the as2Id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAs2Id(String value) {
        this.as2Id = value;
    }

    /**
     * Gets the value of the clientavaliable property.
     * 
     */
    public boolean isClientavaliable() {
        return clientavaliable;
    }

    /**
     * Sets the value of the clientavaliable property.
     * 
     */
    public void setClientavaliable(boolean value) {
        this.clientavaliable = value;
    }

    /**
     * Gets the value of the dafaultmaxdelayendflow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDafaultmaxdelayendflow() {
        return dafaultmaxdelayendflow;
    }

    /**
     * Sets the value of the dafaultmaxdelayendflow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDafaultmaxdelayendflow(Integer value) {
        this.dafaultmaxdelayendflow = value;
    }

    /**
     * Gets the value of the encrypt property.
     * 
     */
    public boolean isEncrypt() {
        return encrypt;
    }

    /**
     * Sets the value of the encrypt property.
     * 
     */
    public void setEncrypt(boolean value) {
        this.encrypt = value;
    }

    /**
     * Gets the value of the encryptpassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptpassword() {
        return encryptpassword;
    }

    /**
     * Sets the value of the encryptpassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptpassword(String value) {
        this.encryptpassword = value;
    }

    /**
     * Gets the value of the lastclientcheck property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastclientcheck() {
        return lastclientcheck;
    }

    /**
     * Sets the value of the lastclientcheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastclientcheck(XMLGregorianCalendar value) {
        this.lastclientcheck = value;
    }

    /**
     * Gets the value of the monitoreable property.
     * 
     */
    public boolean isMonitoreable() {
        return monitoreable;
    }

    /**
     * Sets the value of the monitoreable property.
     * 
     */
    public void setMonitoreable(boolean value) {
        this.monitoreable = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the rut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRut() {
        return rut;
    }

    /**
     * Sets the value of the rut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRut(String value) {
        this.rut = value;
    }

}

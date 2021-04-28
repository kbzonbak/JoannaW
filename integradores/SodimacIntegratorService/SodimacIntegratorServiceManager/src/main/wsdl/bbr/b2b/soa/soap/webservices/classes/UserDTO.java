//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.04.08 a las 02:24:51 PM CLT 
//


package bbr.b2b.soa.soap.webservices.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para userDTO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="userDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://facade.webservices.services.esb.bbr/}elementDTO"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="answer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="blocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="checkvolatile" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastaccess" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="lastlogon" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="lastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastpasschange" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="question" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sessionid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="triescount" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="usertypeid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDTO", propOrder = {
    "active",
    "answer",
    "blocked",
    "checkvolatile",
    "email",
    "lastaccess",
    "lastlogon",
    "lastname",
    "lastpasschange",
    "name",
    "password",
    "question",
    "rut",
    "sessionid",
    "triescount",
    "usertypeid"
})
public class UserDTO
    extends ElementDTO
{

    protected boolean active;
    protected String answer;
    protected Boolean blocked;
    protected Integer checkvolatile;
    protected String email;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastaccess;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastlogon;
    protected String lastname;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastpasschange;
    protected String name;
    protected String password;
    protected String question;
    protected String rut;
    protected String sessionid;
    protected Integer triescount;
    protected Long usertypeid;

    /**
     * Obtiene el valor de la propiedad active.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Define el valor de la propiedad active.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Obtiene el valor de la propiedad answer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Define el valor de la propiedad answer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnswer(String value) {
        this.answer = value;
    }

    /**
     * Obtiene el valor de la propiedad blocked.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBlocked() {
        return blocked;
    }

    /**
     * Define el valor de la propiedad blocked.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBlocked(Boolean value) {
        this.blocked = value;
    }

    /**
     * Obtiene el valor de la propiedad checkvolatile.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCheckvolatile() {
        return checkvolatile;
    }

    /**
     * Define el valor de la propiedad checkvolatile.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCheckvolatile(Integer value) {
        this.checkvolatile = value;
    }

    /**
     * Obtiene el valor de la propiedad email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define el valor de la propiedad email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtiene el valor de la propiedad lastaccess.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastaccess() {
        return lastaccess;
    }

    /**
     * Define el valor de la propiedad lastaccess.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastaccess(XMLGregorianCalendar value) {
        this.lastaccess = value;
    }

    /**
     * Obtiene el valor de la propiedad lastlogon.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastlogon() {
        return lastlogon;
    }

    /**
     * Define el valor de la propiedad lastlogon.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastlogon(XMLGregorianCalendar value) {
        this.lastlogon = value;
    }

    /**
     * Obtiene el valor de la propiedad lastname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Define el valor de la propiedad lastname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastname(String value) {
        this.lastname = value;
    }

    /**
     * Obtiene el valor de la propiedad lastpasschange.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastpasschange() {
        return lastpasschange;
    }

    /**
     * Define el valor de la propiedad lastpasschange.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastpasschange(XMLGregorianCalendar value) {
        this.lastpasschange = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
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
     * Define el valor de la propiedad name.
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
     * Obtiene el valor de la propiedad password.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define el valor de la propiedad password.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Obtiene el valor de la propiedad question.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Define el valor de la propiedad question.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestion(String value) {
        this.question = value;
    }

    /**
     * Obtiene el valor de la propiedad rut.
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
     * Define el valor de la propiedad rut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRut(String value) {
        this.rut = value;
    }

    /**
     * Obtiene el valor de la propiedad sessionid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
     * Define el valor de la propiedad sessionid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionid(String value) {
        this.sessionid = value;
    }

    /**
     * Obtiene el valor de la propiedad triescount.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTriescount() {
        return triescount;
    }

    /**
     * Define el valor de la propiedad triescount.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTriescount(Integer value) {
        this.triescount = value;
    }

    /**
     * Obtiene el valor de la propiedad usertypeid.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getUsertypeid() {
        return usertypeid;
    }

    /**
     * Define el valor de la propiedad usertypeid.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setUsertypeid(Long value) {
        this.usertypeid = value;
    }

}

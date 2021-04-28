//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:18:30 PM CLST 
//


package bbr.b2b.logistic.xml.lib_appointment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Header"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DocumentVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="OriginSystem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ClientEnvCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="ParentCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="Entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *                   &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ListOfAppointments"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{}appointment" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "header",
    "listOfAppointments"
})
@XmlRootElement(name = "LgfData")
public class LgfData {

    @XmlElement(name = "Header", required = true)
    protected LgfData.Header header;
    @XmlElement(name = "ListOfAppointments", required = true)
    protected LgfData.ListOfAppointments listOfAppointments;

    /**
     * Obtiene el valor de la propiedad header.
     * 
     * @return
     *     possible object is
     *     {@link LgfData.Header }
     *     
     */
    public LgfData.Header getHeader() {
        return header;
    }

    /**
     * Define el valor de la propiedad header.
     * 
     * @param value
     *     allowed object is
     *     {@link LgfData.Header }
     *     
     */
    public void setHeader(LgfData.Header value) {
        this.header = value;
    }

    /**
     * Obtiene el valor de la propiedad listOfAppointments.
     * 
     * @return
     *     possible object is
     *     {@link LgfData.ListOfAppointments }
     *     
     */
    public LgfData.ListOfAppointments getListOfAppointments() {
        return listOfAppointments;
    }

    /**
     * Define el valor de la propiedad listOfAppointments.
     * 
     * @param value
     *     allowed object is
     *     {@link LgfData.ListOfAppointments }
     *     
     */
    public void setListOfAppointments(LgfData.ListOfAppointments value) {
        this.listOfAppointments = value;
    }


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
     *         &lt;element name="DocumentVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="OriginSystem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ClientEnvCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="ParentCompanyCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="Entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
     *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "documentVersion",
        "originSystem",
        "clientEnvCode",
        "parentCompanyCode",
        "entity",
        "timeStamp",
        "messageId"
    })
    public static class Header {

        @XmlElement(name = "DocumentVersion", required = true)
        protected String documentVersion;
        @XmlElement(name = "OriginSystem", required = true)
        protected String originSystem;
        @XmlElement(name = "ClientEnvCode", required = true)
        protected String clientEnvCode;
        @XmlElement(name = "ParentCompanyCode", required = true)
        protected String parentCompanyCode;
        @XmlElement(name = "Entity", required = true)
        protected String entity;
        @XmlElement(name = "TimeStamp", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar timeStamp;
        @XmlElement(name = "MessageId", required = true)
        protected String messageId;

        /**
         * Obtiene el valor de la propiedad documentVersion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDocumentVersion() {
            return documentVersion;
        }

        /**
         * Define el valor de la propiedad documentVersion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDocumentVersion(String value) {
            this.documentVersion = value;
        }

        /**
         * Obtiene el valor de la propiedad originSystem.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOriginSystem() {
            return originSystem;
        }

        /**
         * Define el valor de la propiedad originSystem.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOriginSystem(String value) {
            this.originSystem = value;
        }

        /**
         * Obtiene el valor de la propiedad clientEnvCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getClientEnvCode() {
            return clientEnvCode;
        }

        /**
         * Define el valor de la propiedad clientEnvCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setClientEnvCode(String value) {
            this.clientEnvCode = value;
        }

        /**
         * Obtiene el valor de la propiedad parentCompanyCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParentCompanyCode() {
            return parentCompanyCode;
        }

        /**
         * Define el valor de la propiedad parentCompanyCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParentCompanyCode(String value) {
            this.parentCompanyCode = value;
        }

        /**
         * Obtiene el valor de la propiedad entity.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntity() {
            return entity;
        }

        /**
         * Define el valor de la propiedad entity.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntity(String value) {
            this.entity = value;
        }

        /**
         * Obtiene el valor de la propiedad timeStamp.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTimeStamp() {
            return timeStamp;
        }

        /**
         * Define el valor de la propiedad timeStamp.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTimeStamp(XMLGregorianCalendar value) {
            this.timeStamp = value;
        }

        /**
         * Obtiene el valor de la propiedad messageId.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageId() {
            return messageId;
        }

        /**
         * Define el valor de la propiedad messageId.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageId(String value) {
            this.messageId = value;
        }

    }


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
     *         &lt;element ref="{}appointment" maxOccurs="unbounded"/&gt;
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
        "appointment"
    })
    public static class ListOfAppointments {

        @XmlElement(required = true)
        protected List<Appointment> appointment;

        /**
         * Gets the value of the appointment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the appointment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAppointment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Appointment }
         * 
         * 
         */
        public List<Appointment> getAppointment() {
            if (appointment == null) {
                appointment = new ArrayList<Appointment>();
            }
            return this.appointment;
        }

    }

}

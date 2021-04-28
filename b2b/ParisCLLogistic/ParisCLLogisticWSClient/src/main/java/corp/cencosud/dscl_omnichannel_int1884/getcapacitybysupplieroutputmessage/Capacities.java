
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplieroutputmessage;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="capacity" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="messages" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="message">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="endDTTM" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage}StringDateTime"/>
 *                   &lt;element name="fridayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mondayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="rank" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                   &lt;element name="saturdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="startDTTM" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage}StringDateTime"/>
 *                   &lt;element name="sundayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="thursdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="tuesdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="wednesdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="zonaAgendaID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "capacity"
})
@XmlRootElement(name = "capacities")
public class Capacities {

    @XmlElement(required = true)
    protected List<Capacities.Capacity> capacity;

    /**
     * Gets the value of the capacity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capacity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapacity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Capacities.Capacity }
     * 
     * 
     */
    public List<Capacities.Capacity> getCapacity() {
        if (capacity == null) {
            capacity = new ArrayList<Capacities.Capacity>();
        }
        return this.capacity;
    }


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
     *         &lt;element name="messages" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="message">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="endDTTM" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage}StringDateTime"/>
     *         &lt;element name="fridayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mondayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="rank" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *         &lt;element name="saturdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="startDTTM" type="{http://www.cencosud.corp/DSCL_OmniChannel_INT1884/GetCapacityBySupplierOutputMessage}StringDateTime"/>
     *         &lt;element name="sundayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="thursdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="tuesdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="wednesdayCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="zonaAgendaID" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        "messages",
        "direction",
        "endDTTM",
        "fridayCapacity",
        "jornada",
        "mondayCapacity",
        "originFacility",
        "rank",
        "saturdayCapacity",
        "startDTTM",
        "sundayCapacity",
        "thursdayCapacity",
        "tuesdayCapacity",
        "wednesdayCapacity",
        "zona",
        "zonaAgendaID"
    })
    public static class Capacity {

        protected Capacities.Capacity.Messages messages;
        @XmlElement(required = true)
        protected String direction;
        @XmlElement(required = true)
        protected String endDTTM;
        protected int fridayCapacity;
        @XmlElement(required = true)
        protected String jornada;
        protected int mondayCapacity;
        protected byte originFacility;
        protected byte rank;
        protected int saturdayCapacity;
        @XmlElement(required = true)
        protected String startDTTM;
        protected int sundayCapacity;
        protected int thursdayCapacity;
        protected int tuesdayCapacity;
        protected int wednesdayCapacity;
        @XmlElement(required = true)
        protected String zona;
        protected int zonaAgendaID;

        /**
         * Gets the value of the messages property.
         * 
         * @return
         *     possible object is
         *     {@link Capacities.Capacity.Messages }
         *     
         */
        public Capacities.Capacity.Messages getMessages() {
            return messages;
        }

        /**
         * Sets the value of the messages property.
         * 
         * @param value
         *     allowed object is
         *     {@link Capacities.Capacity.Messages }
         *     
         */
        public void setMessages(Capacities.Capacity.Messages value) {
            this.messages = value;
        }

        /**
         * Gets the value of the direction property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDirection() {
            return direction;
        }

        /**
         * Sets the value of the direction property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDirection(String value) {
            this.direction = value;
        }

        /**
         * Gets the value of the endDTTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEndDTTM() {
            return endDTTM;
        }

        /**
         * Sets the value of the endDTTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEndDTTM(String value) {
            this.endDTTM = value;
        }

        /**
         * Gets the value of the fridayCapacity property.
         * 
         */
        public int getFridayCapacity() {
            return fridayCapacity;
        }

        /**
         * Sets the value of the fridayCapacity property.
         * 
         */
        public void setFridayCapacity(int value) {
            this.fridayCapacity = value;
        }

        /**
         * Gets the value of the jornada property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getJornada() {
            return jornada;
        }

        /**
         * Sets the value of the jornada property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setJornada(String value) {
            this.jornada = value;
        }

        /**
         * Gets the value of the mondayCapacity property.
         * 
         */
        public int getMondayCapacity() {
            return mondayCapacity;
        }

        /**
         * Sets the value of the mondayCapacity property.
         * 
         */
        public void setMondayCapacity(int value) {
            this.mondayCapacity = value;
        }

        /**
         * Gets the value of the originFacility property.
         * 
         */
        public byte getOriginFacility() {
            return originFacility;
        }

        /**
         * Sets the value of the originFacility property.
         * 
         */
        public void setOriginFacility(byte value) {
            this.originFacility = value;
        }

        /**
         * Gets the value of the rank property.
         * 
         */
        public byte getRank() {
            return rank;
        }

        /**
         * Sets the value of the rank property.
         * 
         */
        public void setRank(byte value) {
            this.rank = value;
        }

        /**
         * Gets the value of the saturdayCapacity property.
         * 
         */
        public int getSaturdayCapacity() {
            return saturdayCapacity;
        }

        /**
         * Sets the value of the saturdayCapacity property.
         * 
         */
        public void setSaturdayCapacity(int value) {
            this.saturdayCapacity = value;
        }

        /**
         * Gets the value of the startDTTM property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStartDTTM() {
            return startDTTM;
        }

        /**
         * Sets the value of the startDTTM property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStartDTTM(String value) {
            this.startDTTM = value;
        }

        /**
         * Gets the value of the sundayCapacity property.
         * 
         */
        public int getSundayCapacity() {
            return sundayCapacity;
        }

        /**
         * Sets the value of the sundayCapacity property.
         * 
         */
        public void setSundayCapacity(int value) {
            this.sundayCapacity = value;
        }

        /**
         * Gets the value of the thursdayCapacity property.
         * 
         */
        public int getThursdayCapacity() {
            return thursdayCapacity;
        }

        /**
         * Sets the value of the thursdayCapacity property.
         * 
         */
        public void setThursdayCapacity(int value) {
            this.thursdayCapacity = value;
        }

        /**
         * Gets the value of the tuesdayCapacity property.
         * 
         */
        public int getTuesdayCapacity() {
            return tuesdayCapacity;
        }

        /**
         * Sets the value of the tuesdayCapacity property.
         * 
         */
        public void setTuesdayCapacity(int value) {
            this.tuesdayCapacity = value;
        }

        /**
         * Gets the value of the wednesdayCapacity property.
         * 
         */
        public int getWednesdayCapacity() {
            return wednesdayCapacity;
        }

        /**
         * Sets the value of the wednesdayCapacity property.
         * 
         */
        public void setWednesdayCapacity(int value) {
            this.wednesdayCapacity = value;
        }

        /**
         * Gets the value of the zona property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getZona() {
            return zona;
        }

        /**
         * Sets the value of the zona property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setZona(String value) {
            this.zona = value;
        }

        /**
         * Gets the value of the zonaAgendaID property.
         * 
         */
        public int getZonaAgendaID() {
            return zonaAgendaID;
        }

        /**
         * Sets the value of the zonaAgendaID property.
         * 
         */
        public void setZonaAgendaID(int value) {
            this.zonaAgendaID = value;
        }


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
         *         &lt;element name="message">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "message"
        })
        public static class Messages {

            @XmlElement(required = true)
            protected Capacities.Capacity.Messages.Message message;

            /**
             * Gets the value of the message property.
             * 
             * @return
             *     possible object is
             *     {@link Capacities.Capacity.Messages.Message }
             *     
             */
            public Capacities.Capacity.Messages.Message getMessage() {
                return message;
            }

            /**
             * Sets the value of the message property.
             * 
             * @param value
             *     allowed object is
             *     {@link Capacities.Capacity.Messages.Message }
             *     
             */
            public void setMessage(Capacities.Capacity.Messages.Message value) {
                this.message = value;
            }


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
             *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "severity",
                "code",
                "description"
            })
            public static class Message {

                @XmlElement(required = true)
                protected String severity;
                protected int code;
                @XmlElement(required = true)
                protected String description;

                /**
                 * Gets the value of the severity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSeverity() {
                    return severity;
                }

                /**
                 * Sets the value of the severity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSeverity(String value) {
                    this.severity = value;
                }

                /**
                 * Gets the value of the code property.
                 * 
                 */
                public int getCode() {
                    return code;
                }

                /**
                 * Sets the value of the code property.
                 * 
                 */
                public void setCode(int value) {
                    this.code = value;
                }

                /**
                 * Gets the value of the description property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDescription() {
                    return description;
                }

                /**
                 * Sets the value of the description property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDescription(String value) {
                    this.description = value;
                }

            }

        }

    }

}

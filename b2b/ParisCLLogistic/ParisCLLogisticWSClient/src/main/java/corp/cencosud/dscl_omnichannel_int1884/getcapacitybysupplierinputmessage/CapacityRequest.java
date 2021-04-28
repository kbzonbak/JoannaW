
package corp.cencosud.dscl_omnichannel_int1884.getcapacitybysupplierinputmessage;

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
 *         &lt;element name="capacityProcessing">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="capacities">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="capacity" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "capacityProcessing"
})
@XmlRootElement(name = "CapacityRequest")
public class CapacityRequest {

    @XmlElement(required = true)
    protected CapacityRequest.CapacityProcessing capacityProcessing;

    /**
     * Gets the value of the capacityProcessing property.
     * 
     * @return
     *     possible object is
     *     {@link CapacityRequest.CapacityProcessing }
     *     
     */
    public CapacityRequest.CapacityProcessing getCapacityProcessing() {
        return capacityProcessing;
    }

    /**
     * Sets the value of the capacityProcessing property.
     * 
     * @param value
     *     allowed object is
     *     {@link CapacityRequest.CapacityProcessing }
     *     
     */
    public void setCapacityProcessing(CapacityRequest.CapacityProcessing value) {
        this.capacityProcessing = value;
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
     *         &lt;element name="capacities">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="capacity" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "capacities"
    })
    public static class CapacityProcessing {

        @XmlElement(required = true)
        protected CapacityRequest.CapacityProcessing.Capacities capacities;

        /**
         * Gets the value of the capacities property.
         * 
         * @return
         *     possible object is
         *     {@link CapacityRequest.CapacityProcessing.Capacities }
         *     
         */
        public CapacityRequest.CapacityProcessing.Capacities getCapacities() {
            return capacities;
        }

        /**
         * Sets the value of the capacities property.
         * 
         * @param value
         *     allowed object is
         *     {@link CapacityRequest.CapacityProcessing.Capacities }
         *     
         */
        public void setCapacities(CapacityRequest.CapacityProcessing.Capacities value) {
            this.capacities = value;
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
         *         &lt;element name="capacity" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        public static class Capacities {

            @XmlElement(required = true)
            protected List<CapacityRequest.CapacityProcessing.Capacities.Capacity> capacity;

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
             * {@link CapacityRequest.CapacityProcessing.Capacities.Capacity }
             * 
             * 
             */
            public List<CapacityRequest.CapacityProcessing.Capacities.Capacity> getCapacity() {
                if (capacity == null) {
                    capacity = new ArrayList<CapacityRequest.CapacityProcessing.Capacities.Capacity>();
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
             *         &lt;element name="originFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "originFacility",
                "zona",
                "direction",
                "jornada"
            })
            public static class Capacity {

                @XmlElement(required = true)
                protected String originFacility;
                protected String zona;
                @XmlElement(required = true)
                protected String direction;
                @XmlElement(required = true)
                protected String jornada;

                /**
                 * Gets the value of the originFacility property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOriginFacility() {
                    return originFacility;
                }

                /**
                 * Sets the value of the originFacility property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOriginFacility(String value) {
                    this.originFacility = value;
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

            }

        }

    }

}

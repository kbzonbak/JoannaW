
package corp.cencosud.dscl_omnichannel_int1859.availabilitysyncinputmessage;

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
 *         &lt;element name="viewName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="availabilityCriteria">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="itemNames">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="itemName" maxOccurs="unbounded">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="facilityNames" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="facilityName" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="16"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
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
    "viewName",
    "availabilityCriteria"
})
@XmlRootElement(name = "availabilityRequest")
public class AvailabilityRequest {

    @XmlElement(required = true)
    protected String viewName;
    @XmlElement(required = true)
    protected AvailabilityRequest.AvailabilityCriteria availabilityCriteria;

    /**
     * Gets the value of the viewName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the value of the viewName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewName(String value) {
        this.viewName = value;
    }

    /**
     * Gets the value of the availabilityCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityRequest.AvailabilityCriteria }
     *     
     */
    public AvailabilityRequest.AvailabilityCriteria getAvailabilityCriteria() {
        return availabilityCriteria;
    }

    /**
     * Sets the value of the availabilityCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityRequest.AvailabilityCriteria }
     *     
     */
    public void setAvailabilityCriteria(AvailabilityRequest.AvailabilityCriteria value) {
        this.availabilityCriteria = value;
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
     *         &lt;element name="itemNames">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="itemName" maxOccurs="unbounded">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="facilityNames" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="facilityName" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="16"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
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
        "itemNames",
        "facilityNames"
    })
    public static class AvailabilityCriteria {

        @XmlElement(required = true)
        protected AvailabilityRequest.AvailabilityCriteria.ItemNames itemNames;
        protected AvailabilityRequest.AvailabilityCriteria.FacilityNames facilityNames;

        /**
         * Gets the value of the itemNames property.
         * 
         * @return
         *     possible object is
         *     {@link AvailabilityRequest.AvailabilityCriteria.ItemNames }
         *     
         */
        public AvailabilityRequest.AvailabilityCriteria.ItemNames getItemNames() {
            return itemNames;
        }

        /**
         * Sets the value of the itemNames property.
         * 
         * @param value
         *     allowed object is
         *     {@link AvailabilityRequest.AvailabilityCriteria.ItemNames }
         *     
         */
        public void setItemNames(AvailabilityRequest.AvailabilityCriteria.ItemNames value) {
            this.itemNames = value;
        }

        /**
         * Gets the value of the facilityNames property.
         * 
         * @return
         *     possible object is
         *     {@link AvailabilityRequest.AvailabilityCriteria.FacilityNames }
         *     
         */
        public AvailabilityRequest.AvailabilityCriteria.FacilityNames getFacilityNames() {
            return facilityNames;
        }

        /**
         * Sets the value of the facilityNames property.
         * 
         * @param value
         *     allowed object is
         *     {@link AvailabilityRequest.AvailabilityCriteria.FacilityNames }
         *     
         */
        public void setFacilityNames(AvailabilityRequest.AvailabilityCriteria.FacilityNames value) {
            this.facilityNames = value;
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
         *         &lt;element name="facilityName" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="16"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "facilityName"
        })
        public static class FacilityNames {

            protected String facilityName;

            /**
             * Gets the value of the facilityName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFacilityName() {
                return facilityName;
            }

            /**
             * Sets the value of the facilityName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFacilityName(String value) {
                this.facilityName = value;
            }

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
         *         &lt;element name="itemName" maxOccurs="unbounded">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "itemName"
        })
        public static class ItemNames {

            @XmlElement(required = true)
            protected List<String> itemName;

            /**
             * Gets the value of the itemName property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the itemName property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getItemName().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getItemName() {
                if (itemName == null) {
                    itemName = new ArrayList<String>();
                }
                return this.itemName;
            }

        }

    }

}

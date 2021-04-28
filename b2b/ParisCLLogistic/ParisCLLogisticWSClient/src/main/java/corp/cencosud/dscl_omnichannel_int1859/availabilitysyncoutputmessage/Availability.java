
package corp.cencosud.dscl_omnichannel_int1859.availabilitysyncoutputmessage;

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
 *         &lt;element name="transactionDateTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="viewName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="viewConfiguration">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="availabilityDetails">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="availabilityDetail" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="businessUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="businessPartner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="facilityName" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="16"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="itemName">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="atcQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="atcStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="nextAvailabilityDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="dcGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="storeGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="supplierGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="otherGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="baseUOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="responseStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="totalNoOfRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *         &lt;element name="customFields" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="customNumberField3" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="customNumberField2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="customNumberField1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="customNumberField5" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="customNumberField4" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="customField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="customField10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="messages" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="message" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "transactionDateTime",
    "viewName",
    "viewConfiguration",
    "availabilityDetails",
    "customFields",
    "messages"
})
@XmlRootElement(name = "availability")
public class Availability {

    @XmlElement(required = true)
    protected String transactionDateTime;
    @XmlElement(required = true)
    protected String viewName;
    @XmlElement(required = true)
    protected String viewConfiguration;
    @XmlElement(required = true)
    protected Availability.AvailabilityDetails availabilityDetails;
    @XmlElement(required = true)
    protected List<Availability.CustomFields> customFields;
    protected Availability.Messages messages;

    /**
     * Gets the value of the transactionDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * Sets the value of the transactionDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionDateTime(String value) {
        this.transactionDateTime = value;
    }

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
     * Gets the value of the viewConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewConfiguration() {
        return viewConfiguration;
    }

    /**
     * Sets the value of the viewConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewConfiguration(String value) {
        this.viewConfiguration = value;
    }

    /**
     * Gets the value of the availabilityDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Availability.AvailabilityDetails }
     *     
     */
    public Availability.AvailabilityDetails getAvailabilityDetails() {
        return availabilityDetails;
    }

    /**
     * Sets the value of the availabilityDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Availability.AvailabilityDetails }
     *     
     */
    public void setAvailabilityDetails(Availability.AvailabilityDetails value) {
        this.availabilityDetails = value;
    }

    /**
     * Gets the value of the customFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Availability.CustomFields }
     * 
     * 
     */
    public List<Availability.CustomFields> getCustomFields() {
        if (customFields == null) {
            customFields = new ArrayList<Availability.CustomFields>();
        }
        return this.customFields;
    }

    /**
     * Gets the value of the messages property.
     * 
     * @return
     *     possible object is
     *     {@link Availability.Messages }
     *     
     */
    public Availability.Messages getMessages() {
        return messages;
    }

    /**
     * Sets the value of the messages property.
     * 
     * @param value
     *     allowed object is
     *     {@link Availability.Messages }
     *     
     */
    public void setMessages(Availability.Messages value) {
        this.messages = value;
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
     *         &lt;element name="availabilityDetail" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="businessUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="businessPartner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="facilityName" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="16"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="itemName">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="atcQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="atcStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="nextAvailabilityDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="dcGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="storeGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="supplierGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="otherGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="baseUOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="responseStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="totalNoOfRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "availabilityDetail"
    })
    public static class AvailabilityDetails {

        @XmlElement(required = true)
        protected List<Availability.AvailabilityDetails.AvailabilityDetail> availabilityDetail;

        /**
         * Gets the value of the availabilityDetail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the availabilityDetail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAvailabilityDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Availability.AvailabilityDetails.AvailabilityDetail }
         * 
         * 
         */
        public List<Availability.AvailabilityDetails.AvailabilityDetail> getAvailabilityDetail() {
            if (availabilityDetail == null) {
                availabilityDetail = new ArrayList<Availability.AvailabilityDetails.AvailabilityDetail>();
            }
            return this.availabilityDetail;
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
         *         &lt;element name="businessUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="businessPartner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="facilityName" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="16"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="itemName">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="atcQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="atcStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="nextAvailabilityDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="dcGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="storeGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="supplierGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="otherGroupQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="baseUOM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="responseStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="totalNoOfRecords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
            "businessUnit",
            "businessPartner",
            "facilityName",
            "itemName",
            "atcQuantity",
            "atcStatus",
            "nextAvailabilityDate",
            "dcGroupQuantity",
            "storeGroupQuantity",
            "supplierGroupQuantity",
            "otherGroupQuantity",
            "baseUOM",
            "responseStatus",
            "totalNoOfRecords"
        })
        public static class AvailabilityDetail {

            protected String businessUnit;
            protected String businessPartner;
            protected String facilityName;
            @XmlElement(required = true)
            protected String itemName;
            @XmlElement(required = true)
            protected String atcQuantity;
            protected String atcStatus;
            protected String nextAvailabilityDate;
            protected String dcGroupQuantity;
            protected String storeGroupQuantity;
            protected String supplierGroupQuantity;
            protected String otherGroupQuantity;
            protected String baseUOM;
            protected String responseStatus;
            protected String totalNoOfRecords;

            /**
             * Gets the value of the businessUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBusinessUnit() {
                return businessUnit;
            }

            /**
             * Sets the value of the businessUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBusinessUnit(String value) {
                this.businessUnit = value;
            }

            /**
             * Gets the value of the businessPartner property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBusinessPartner() {
                return businessPartner;
            }

            /**
             * Sets the value of the businessPartner property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBusinessPartner(String value) {
                this.businessPartner = value;
            }

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

            /**
             * Gets the value of the itemName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItemName() {
                return itemName;
            }

            /**
             * Sets the value of the itemName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItemName(String value) {
                this.itemName = value;
            }

            /**
             * Gets the value of the atcQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAtcQuantity() {
                return atcQuantity;
            }

            /**
             * Sets the value of the atcQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAtcQuantity(String value) {
                this.atcQuantity = value;
            }

            /**
             * Gets the value of the atcStatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAtcStatus() {
                return atcStatus;
            }

            /**
             * Sets the value of the atcStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAtcStatus(String value) {
                this.atcStatus = value;
            }

            /**
             * Gets the value of the nextAvailabilityDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNextAvailabilityDate() {
                return nextAvailabilityDate;
            }

            /**
             * Sets the value of the nextAvailabilityDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNextAvailabilityDate(String value) {
                this.nextAvailabilityDate = value;
            }

            /**
             * Gets the value of the dcGroupQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDcGroupQuantity() {
                return dcGroupQuantity;
            }

            /**
             * Sets the value of the dcGroupQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDcGroupQuantity(String value) {
                this.dcGroupQuantity = value;
            }

            /**
             * Gets the value of the storeGroupQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStoreGroupQuantity() {
                return storeGroupQuantity;
            }

            /**
             * Sets the value of the storeGroupQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStoreGroupQuantity(String value) {
                this.storeGroupQuantity = value;
            }

            /**
             * Gets the value of the supplierGroupQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplierGroupQuantity() {
                return supplierGroupQuantity;
            }

            /**
             * Sets the value of the supplierGroupQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplierGroupQuantity(String value) {
                this.supplierGroupQuantity = value;
            }

            /**
             * Gets the value of the otherGroupQuantity property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOtherGroupQuantity() {
                return otherGroupQuantity;
            }

            /**
             * Sets the value of the otherGroupQuantity property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOtherGroupQuantity(String value) {
                this.otherGroupQuantity = value;
            }

            /**
             * Gets the value of the baseUOM property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBaseUOM() {
                return baseUOM;
            }

            /**
             * Sets the value of the baseUOM property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBaseUOM(String value) {
                this.baseUOM = value;
            }

            /**
             * Gets the value of the responseStatus property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponseStatus() {
                return responseStatus;
            }

            /**
             * Sets the value of the responseStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponseStatus(String value) {
                this.responseStatus = value;
            }

            /**
             * Gets the value of the totalNoOfRecords property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalNoOfRecords() {
                return totalNoOfRecords;
            }

            /**
             * Sets the value of the totalNoOfRecords property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalNoOfRecords(String value) {
                this.totalNoOfRecords = value;
            }

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
     *         &lt;element name="customNumberField3" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="customNumberField2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="customNumberField1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="customNumberField5" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="customNumberField4" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="customField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="customField10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "customNumberField3",
        "customNumberField2",
        "customNumberField1",
        "customNumberField5",
        "customNumberField4",
        "customField3",
        "customField4",
        "customField1",
        "customField2",
        "customField7",
        "customField8",
        "customField5",
        "customField6",
        "customField9",
        "customField10"
    })
    public static class CustomFields {

        protected Double customNumberField3;
        protected Double customNumberField2;
        protected Double customNumberField1;
        protected Double customNumberField5;
        protected Double customNumberField4;
        protected String customField3;
        protected String customField4;
        protected String customField1;
        protected String customField2;
        protected String customField7;
        protected String customField8;
        protected String customField5;
        protected String customField6;
        protected String customField9;
        protected String customField10;

        /**
         * Gets the value of the customNumberField3 property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getCustomNumberField3() {
            return customNumberField3;
        }

        /**
         * Sets the value of the customNumberField3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setCustomNumberField3(Double value) {
            this.customNumberField3 = value;
        }

        /**
         * Gets the value of the customNumberField2 property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getCustomNumberField2() {
            return customNumberField2;
        }

        /**
         * Sets the value of the customNumberField2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setCustomNumberField2(Double value) {
            this.customNumberField2 = value;
        }

        /**
         * Gets the value of the customNumberField1 property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getCustomNumberField1() {
            return customNumberField1;
        }

        /**
         * Sets the value of the customNumberField1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setCustomNumberField1(Double value) {
            this.customNumberField1 = value;
        }

        /**
         * Gets the value of the customNumberField5 property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getCustomNumberField5() {
            return customNumberField5;
        }

        /**
         * Sets the value of the customNumberField5 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setCustomNumberField5(Double value) {
            this.customNumberField5 = value;
        }

        /**
         * Gets the value of the customNumberField4 property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getCustomNumberField4() {
            return customNumberField4;
        }

        /**
         * Sets the value of the customNumberField4 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setCustomNumberField4(Double value) {
            this.customNumberField4 = value;
        }

        /**
         * Gets the value of the customField3 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField3() {
            return customField3;
        }

        /**
         * Sets the value of the customField3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField3(String value) {
            this.customField3 = value;
        }

        /**
         * Gets the value of the customField4 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField4() {
            return customField4;
        }

        /**
         * Sets the value of the customField4 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField4(String value) {
            this.customField4 = value;
        }

        /**
         * Gets the value of the customField1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField1() {
            return customField1;
        }

        /**
         * Sets the value of the customField1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField1(String value) {
            this.customField1 = value;
        }

        /**
         * Gets the value of the customField2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField2() {
            return customField2;
        }

        /**
         * Sets the value of the customField2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField2(String value) {
            this.customField2 = value;
        }

        /**
         * Gets the value of the customField7 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField7() {
            return customField7;
        }

        /**
         * Sets the value of the customField7 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField7(String value) {
            this.customField7 = value;
        }

        /**
         * Gets the value of the customField8 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField8() {
            return customField8;
        }

        /**
         * Sets the value of the customField8 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField8(String value) {
            this.customField8 = value;
        }

        /**
         * Gets the value of the customField5 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField5() {
            return customField5;
        }

        /**
         * Sets the value of the customField5 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField5(String value) {
            this.customField5 = value;
        }

        /**
         * Gets the value of the customField6 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField6() {
            return customField6;
        }

        /**
         * Sets the value of the customField6 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField6(String value) {
            this.customField6 = value;
        }

        /**
         * Gets the value of the customField9 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField9() {
            return customField9;
        }

        /**
         * Sets the value of the customField9 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField9(String value) {
            this.customField9 = value;
        }

        /**
         * Gets the value of the customField10 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCustomField10() {
            return customField10;
        }

        /**
         * Sets the value of the customField10 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCustomField10(String value) {
            this.customField10 = value;
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
     *         &lt;element name="message" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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

        protected Availability.Messages.Message message;

        /**
         * Gets the value of the message property.
         * 
         * @return
         *     possible object is
         *     {@link Availability.Messages.Message }
         *     
         */
        public Availability.Messages.Message getMessage() {
            return message;
        }

        /**
         * Sets the value of the message property.
         * 
         * @param value
         *     allowed object is
         *     {@link Availability.Messages.Message }
         *     
         */
        public void setMessage(Availability.Messages.Message value) {
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
         *         &lt;element name="severity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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

            protected String severity;
            protected String code;
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
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCode() {
                return code;
            }

            /**
             * Sets the value of the code property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCode(String value) {
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

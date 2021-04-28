//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.07.29 a las 04:31:24 PM CLT 
//


package bbr.b2b.b2blink.logistic.xml.RecCustomer;

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
 *         &lt;element name="buyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vendor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="receptionnumber" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ordernumber" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="ordertype" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ordertypename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="receptiondate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="deliveryplace" type="{http://www.bbr.cl/RecCustomer}local" minOccurs="0"/&gt;
 *         &lt;element name="paymentcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="responsible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="locals" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="local" type="{http://www.bbr.cl/RecCustomer}local"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="details"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="detail"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="skubuyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="skuvendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="receivedquantity" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                             &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
    "buyer",
    "vendor",
    "receptionnumber",
    "ordernumber",
    "ordertype",
    "ordertypename",
    "complete",
    "receptiondate",
    "total",
    "deliveryplace",
    "paymentcondition",
    "observation",
    "responsible",
    "locals",
    "details"
})
@XmlRootElement(name = "recepcion")
public class Recepcion {

    @XmlElement(required = true)
    protected String buyer;
    @XmlElement(required = true)
    protected String vendor;
    protected long receptionnumber;
    protected long ordernumber;
    @XmlElement(required = true)
    protected String ordertype;
    protected String ordertypename;
    protected boolean complete;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar receptiondate;
    protected double total;
    protected Local deliveryplace;
    protected String paymentcondition;
    protected String observation;
    protected String responsible;
    protected Recepcion.Locals locals;
    @XmlElement(required = true)
    protected Recepcion.Details details;

    /**
     * Obtiene el valor de la propiedad buyer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyer() {
        return buyer;
    }

    /**
     * Define el valor de la propiedad buyer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyer(String value) {
        this.buyer = value;
    }

    /**
     * Obtiene el valor de la propiedad vendor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Define el valor de la propiedad vendor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Obtiene el valor de la propiedad receptionnumber.
     * 
     */
    public long getReceptionnumber() {
        return receptionnumber;
    }

    /**
     * Define el valor de la propiedad receptionnumber.
     * 
     */
    public void setReceptionnumber(long value) {
        this.receptionnumber = value;
    }

    /**
     * Obtiene el valor de la propiedad ordernumber.
     * 
     */
    public long getOrdernumber() {
        return ordernumber;
    }

    /**
     * Define el valor de la propiedad ordernumber.
     * 
     */
    public void setOrdernumber(long value) {
        this.ordernumber = value;
    }

    /**
     * Obtiene el valor de la propiedad ordertype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdertype() {
        return ordertype;
    }

    /**
     * Define el valor de la propiedad ordertype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdertype(String value) {
        this.ordertype = value;
    }

    /**
     * Obtiene el valor de la propiedad ordertypename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrdertypename() {
        return ordertypename;
    }

    /**
     * Define el valor de la propiedad ordertypename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrdertypename(String value) {
        this.ordertypename = value;
    }

    /**
     * Obtiene el valor de la propiedad complete.
     * 
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Define el valor de la propiedad complete.
     * 
     */
    public void setComplete(boolean value) {
        this.complete = value;
    }

    /**
     * Obtiene el valor de la propiedad receptiondate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceptiondate() {
        return receptiondate;
    }

    /**
     * Define el valor de la propiedad receptiondate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceptiondate(XMLGregorianCalendar value) {
        this.receptiondate = value;
    }

    /**
     * Obtiene el valor de la propiedad total.
     * 
     */
    public double getTotal() {
        return total;
    }

    /**
     * Define el valor de la propiedad total.
     * 
     */
    public void setTotal(double value) {
        this.total = value;
    }

    /**
     * Obtiene el valor de la propiedad deliveryplace.
     * 
     * @return
     *     possible object is
     *     {@link Local }
     *     
     */
    public Local getDeliveryplace() {
        return deliveryplace;
    }

    /**
     * Define el valor de la propiedad deliveryplace.
     * 
     * @param value
     *     allowed object is
     *     {@link Local }
     *     
     */
    public void setDeliveryplace(Local value) {
        this.deliveryplace = value;
    }

    /**
     * Obtiene el valor de la propiedad paymentcondition.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentcondition() {
        return paymentcondition;
    }

    /**
     * Define el valor de la propiedad paymentcondition.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentcondition(String value) {
        this.paymentcondition = value;
    }

    /**
     * Obtiene el valor de la propiedad observation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Define el valor de la propiedad observation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservation(String value) {
        this.observation = value;
    }

    /**
     * Obtiene el valor de la propiedad responsible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     * Define el valor de la propiedad responsible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsible(String value) {
        this.responsible = value;
    }

    /**
     * Obtiene el valor de la propiedad locals.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Locals }
     *     
     */
    public Recepcion.Locals getLocals() {
        return locals;
    }

    /**
     * Define el valor de la propiedad locals.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Locals }
     *     
     */
    public void setLocals(Recepcion.Locals value) {
        this.locals = value;
    }

    /**
     * Obtiene el valor de la propiedad details.
     * 
     * @return
     *     possible object is
     *     {@link Recepcion.Details }
     *     
     */
    public Recepcion.Details getDetails() {
        return details;
    }

    /**
     * Define el valor de la propiedad details.
     * 
     * @param value
     *     allowed object is
     *     {@link Recepcion.Details }
     *     
     */
    public void setDetails(Recepcion.Details value) {
        this.details = value;
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
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="detail"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="skubuyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="skuvendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="receivedquantity" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *                   &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
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
        "detail"
    })
    public static class Details {

        @XmlElement(required = true)
        protected List<Recepcion.Details.Detail> detail;

        /**
         * Gets the value of the detail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the detail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Recepcion.Details.Detail }
         * 
         * 
         */
        public List<Recepcion.Details.Detail> getDetail() {
            if (detail == null) {
                detail = new ArrayList<Recepcion.Details.Detail>();
            }
            return this.detail;
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
         *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="skubuyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="skuvendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="receivedquantity" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
         *         &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
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
            "position",
            "skubuyer",
            "skuvendor",
            "ean13",
            "productdescription",
            "codeumc",
            "descriptionumc",
            "descriptionumb",
            "quantityumc",
            "receivedquantity",
            "listcost",
            "finalcost"
        })
        public static class Detail {

            protected int position;
            @XmlElement(required = true)
            protected String skubuyer;
            protected String skuvendor;
            @XmlElement(required = true)
            protected String ean13;
            protected String productdescription;
            protected String codeumc;
            protected String descriptionumc;
            protected String descriptionumb;
            protected Integer quantityumc;
            protected float receivedquantity;
            protected Double listcost;
            protected Double finalcost;

            /**
             * Obtiene el valor de la propiedad position.
             * 
             */
            public int getPosition() {
                return position;
            }

            /**
             * Define el valor de la propiedad position.
             * 
             */
            public void setPosition(int value) {
                this.position = value;
            }

            /**
             * Obtiene el valor de la propiedad skubuyer.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSkubuyer() {
                return skubuyer;
            }

            /**
             * Define el valor de la propiedad skubuyer.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSkubuyer(String value) {
                this.skubuyer = value;
            }

            /**
             * Obtiene el valor de la propiedad skuvendor.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSkuvendor() {
                return skuvendor;
            }

            /**
             * Define el valor de la propiedad skuvendor.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSkuvendor(String value) {
                this.skuvendor = value;
            }

            /**
             * Obtiene el valor de la propiedad ean13.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan13() {
                return ean13;
            }

            /**
             * Define el valor de la propiedad ean13.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan13(String value) {
                this.ean13 = value;
            }

            /**
             * Obtiene el valor de la propiedad productdescription.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProductdescription() {
                return productdescription;
            }

            /**
             * Define el valor de la propiedad productdescription.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProductdescription(String value) {
                this.productdescription = value;
            }

            /**
             * Obtiene el valor de la propiedad codeumc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodeumc() {
                return codeumc;
            }

            /**
             * Define el valor de la propiedad codeumc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodeumc(String value) {
                this.codeumc = value;
            }

            /**
             * Obtiene el valor de la propiedad descriptionumc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescriptionumc() {
                return descriptionumc;
            }

            /**
             * Define el valor de la propiedad descriptionumc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescriptionumc(String value) {
                this.descriptionumc = value;
            }

            /**
             * Obtiene el valor de la propiedad descriptionumb.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescriptionumb() {
                return descriptionumb;
            }

            /**
             * Define el valor de la propiedad descriptionumb.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescriptionumb(String value) {
                this.descriptionumb = value;
            }

            /**
             * Obtiene el valor de la propiedad quantityumc.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getQuantityumc() {
                return quantityumc;
            }

            /**
             * Define el valor de la propiedad quantityumc.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setQuantityumc(Integer value) {
                this.quantityumc = value;
            }

            /**
             * Obtiene el valor de la propiedad receivedquantity.
             * 
             */
            public float getReceivedquantity() {
                return receivedquantity;
            }

            /**
             * Define el valor de la propiedad receivedquantity.
             * 
             */
            public void setReceivedquantity(float value) {
                this.receivedquantity = value;
            }

            /**
             * Obtiene el valor de la propiedad listcost.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getListcost() {
                return listcost;
            }

            /**
             * Define el valor de la propiedad listcost.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setListcost(Double value) {
                this.listcost = value;
            }

            /**
             * Obtiene el valor de la propiedad finalcost.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getFinalcost() {
                return finalcost;
            }

            /**
             * Define el valor de la propiedad finalcost.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setFinalcost(Double value) {
                this.finalcost = value;
            }

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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="local" type="{http://www.bbr.cl/RecCustomer}local"/&gt;
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
        "local"
    })
    public static class Locals {

        protected List<Local> local;

        /**
         * Gets the value of the local property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the local property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLocal().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Local }
         * 
         * 
         */
        public List<Local> getLocal() {
            if (local == null) {
                local = new ArrayList<Local>();
            }
            return this.local;
        }

    }

}

//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.10.07 a las 04:17:20 PM CLST 
//


package bbr.b2b.b2blink.logistic.xml.OC_Fullkom;

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
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ordertype" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ordertypename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="previousordertype" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="receiptnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="request" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="issuedate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="effectivdate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="expirationdate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="commitmentdate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="paymentcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="responsible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="responsibleemail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
 *         &lt;element name="saleplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
 *         &lt;element name="section" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="action" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="client" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="identity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="checkdigit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="phone2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="streetnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="apartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="housenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="commune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="discounts" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="discounts" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="charges" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="charges" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="details" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="detail" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="skubuyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="skuvendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean13buyer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="codeumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="umb_x_umc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="costaftertaxes" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="listprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="stylecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="styledescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="stylebrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tolerance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="productdeliverydate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                             &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="freightcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="freightweight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="discounts" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                       &lt;element name="discount" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="charges" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                       &lt;element name="charge" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="predistributions" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                       &lt;element name="predistribution"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
 *                                                 &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                                                 &lt;element name="shipping_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="received_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="pending_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
    "number",
    "ordertype",
    "ordertypename",
    "previousordertype",
    "complete",
    "ticket",
    "receiptnumber",
    "status",
    "numref1",
    "numref2",
    "numref3",
    "request",
    "issuedate",
    "effectivdate",
    "expirationdate",
    "commitmentdate",
    "total",
    "paymentcondition",
    "observation",
    "responsible",
    "responsibleemail",
    "currency",
    "valid",
    "deliveryplace",
    "saleplace",
    "section",
    "action",
    "client",
    "discounts",
    "charges",
    "details"
})
@XmlRootElement(name = "order")
public class Order {

    @XmlElement(required = true)
    protected String buyer;
    @XmlElement(required = true)
    protected String vendor;
    @XmlElement(required = true)
    protected String number;
    @XmlElement(required = true)
    protected String ordertype;
    protected String ordertypename;
    @XmlElement(required = true)
    protected String previousordertype;
    protected boolean complete;
    protected String ticket;
    protected String receiptnumber;
    protected String status;
    protected String numref1;
    protected String numref2;
    protected String numref3;
    protected String request;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issuedate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectivdate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expirationdate;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar commitmentdate;
    protected Double total;
    protected String paymentcondition;
    protected String observation;
    protected String responsible;
    protected String responsibleemail;
    protected String currency;
    protected String valid;
    protected Local deliveryplace;
    protected Local saleplace;
    protected Order.Section section;
    protected Order.Action action;
    protected Order.Client client;
    protected Order.Discounts discounts;
    protected Order.Charges charges;
    protected Order.Details details;

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
     * Obtiene el valor de la propiedad number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Define el valor de la propiedad number.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
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
     * Obtiene el valor de la propiedad previousordertype.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreviousordertype() {
        return previousordertype;
    }

    /**
     * Define el valor de la propiedad previousordertype.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviousordertype(String value) {
        this.previousordertype = value;
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
     * Obtiene el valor de la propiedad ticket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Define el valor de la propiedad ticket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Obtiene el valor de la propiedad receiptnumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptnumber() {
        return receiptnumber;
    }

    /**
     * Define el valor de la propiedad receiptnumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptnumber(String value) {
        this.receiptnumber = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad numref1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref1() {
        return numref1;
    }

    /**
     * Define el valor de la propiedad numref1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref1(String value) {
        this.numref1 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref2() {
        return numref2;
    }

    /**
     * Define el valor de la propiedad numref2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref2(String value) {
        this.numref2 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref3() {
        return numref3;
    }

    /**
     * Define el valor de la propiedad numref3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref3(String value) {
        this.numref3 = value;
    }

    /**
     * Obtiene el valor de la propiedad request.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Define el valor de la propiedad request.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

    /**
     * Obtiene el valor de la propiedad issuedate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssuedate() {
        return issuedate;
    }

    /**
     * Define el valor de la propiedad issuedate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssuedate(XMLGregorianCalendar value) {
        this.issuedate = value;
    }

    /**
     * Obtiene el valor de la propiedad effectivdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectivdate() {
        return effectivdate;
    }

    /**
     * Define el valor de la propiedad effectivdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectivdate(XMLGregorianCalendar value) {
        this.effectivdate = value;
    }

    /**
     * Obtiene el valor de la propiedad expirationdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationdate() {
        return expirationdate;
    }

    /**
     * Define el valor de la propiedad expirationdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationdate(XMLGregorianCalendar value) {
        this.expirationdate = value;
    }

    /**
     * Obtiene el valor de la propiedad commitmentdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCommitmentdate() {
        return commitmentdate;
    }

    /**
     * Define el valor de la propiedad commitmentdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCommitmentdate(XMLGregorianCalendar value) {
        this.commitmentdate = value;
    }

    /**
     * Obtiene el valor de la propiedad total.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Define el valor de la propiedad total.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotal(Double value) {
        this.total = value;
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
     * Obtiene el valor de la propiedad responsibleemail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsibleemail() {
        return responsibleemail;
    }

    /**
     * Define el valor de la propiedad responsibleemail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsibleemail(String value) {
        this.responsibleemail = value;
    }

    /**
     * Obtiene el valor de la propiedad currency.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Define el valor de la propiedad currency.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Obtiene el valor de la propiedad valid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValid() {
        return valid;
    }

    /**
     * Define el valor de la propiedad valid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValid(String value) {
        this.valid = value;
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
     * Obtiene el valor de la propiedad saleplace.
     * 
     * @return
     *     possible object is
     *     {@link Local }
     *     
     */
    public Local getSaleplace() {
        return saleplace;
    }

    /**
     * Define el valor de la propiedad saleplace.
     * 
     * @param value
     *     allowed object is
     *     {@link Local }
     *     
     */
    public void setSaleplace(Local value) {
        this.saleplace = value;
    }

    /**
     * Obtiene el valor de la propiedad section.
     * 
     * @return
     *     possible object is
     *     {@link Order.Section }
     *     
     */
    public Order.Section getSection() {
        return section;
    }

    /**
     * Define el valor de la propiedad section.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Section }
     *     
     */
    public void setSection(Order.Section value) {
        this.section = value;
    }

    /**
     * Obtiene el valor de la propiedad action.
     * 
     * @return
     *     possible object is
     *     {@link Order.Action }
     *     
     */
    public Order.Action getAction() {
        return action;
    }

    /**
     * Define el valor de la propiedad action.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Action }
     *     
     */
    public void setAction(Order.Action value) {
        this.action = value;
    }

    /**
     * Obtiene el valor de la propiedad client.
     * 
     * @return
     *     possible object is
     *     {@link Order.Client }
     *     
     */
    public Order.Client getClient() {
        return client;
    }

    /**
     * Define el valor de la propiedad client.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Client }
     *     
     */
    public void setClient(Order.Client value) {
        this.client = value;
    }

    /**
     * Obtiene el valor de la propiedad discounts.
     * 
     * @return
     *     possible object is
     *     {@link Order.Discounts }
     *     
     */
    public Order.Discounts getDiscounts() {
        return discounts;
    }

    /**
     * Define el valor de la propiedad discounts.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Discounts }
     *     
     */
    public void setDiscounts(Order.Discounts value) {
        this.discounts = value;
    }

    /**
     * Obtiene el valor de la propiedad charges.
     * 
     * @return
     *     possible object is
     *     {@link Order.Charges }
     *     
     */
    public Order.Charges getCharges() {
        return charges;
    }

    /**
     * Define el valor de la propiedad charges.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Charges }
     *     
     */
    public void setCharges(Order.Charges value) {
        this.charges = value;
    }

    /**
     * Obtiene el valor de la propiedad details.
     * 
     * @return
     *     possible object is
     *     {@link Order.Details }
     *     
     */
    public Order.Details getDetails() {
        return details;
    }

    /**
     * Define el valor de la propiedad details.
     * 
     * @param value
     *     allowed object is
     *     {@link Order.Details }
     *     
     */
    public void setDetails(Order.Details value) {
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
     *       &lt;sequence&gt;
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "code",
        "description"
    })
    public static class Action {

        protected String code;
        protected String description;

        /**
         * Obtiene el valor de la propiedad code.
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
         * Define el valor de la propiedad code.
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
         * Obtiene el valor de la propiedad description.
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
         * Define el valor de la propiedad description.
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
     *         &lt;element name="charges" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
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
        "charges"
    })
    public static class Charges {

        protected List<DiscountCharge> charges;

        /**
         * Gets the value of the charges property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the charges property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCharges().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DiscountCharge }
         * 
         * 
         */
        public List<DiscountCharge> getCharges() {
            if (charges == null) {
                charges = new ArrayList<DiscountCharge>();
            }
            return this.charges;
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
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="identity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="checkdigit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="phone2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="streetnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="apartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="housenumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="commune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "name",
        "identity",
        "checkdigit",
        "phone",
        "phone2",
        "address",
        "streetnumber",
        "apartment",
        "housenumber",
        "region",
        "commune",
        "city",
        "location",
        "observation"
    })
    public static class Client {

        @XmlElement(required = true)
        protected String name;
        @XmlElement(required = true)
        protected String identity;
        protected String checkdigit;
        protected String phone;
        protected String phone2;
        protected String address;
        protected String streetnumber;
        protected String apartment;
        protected String housenumber;
        protected String region;
        protected String commune;
        protected String city;
        protected String location;
        protected String observation;

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
         * Obtiene el valor de la propiedad identity.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdentity() {
            return identity;
        }

        /**
         * Define el valor de la propiedad identity.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdentity(String value) {
            this.identity = value;
        }

        /**
         * Obtiene el valor de la propiedad checkdigit.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCheckdigit() {
            return checkdigit;
        }

        /**
         * Define el valor de la propiedad checkdigit.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCheckdigit(String value) {
            this.checkdigit = value;
        }

        /**
         * Obtiene el valor de la propiedad phone.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhone() {
            return phone;
        }

        /**
         * Define el valor de la propiedad phone.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhone(String value) {
            this.phone = value;
        }

        /**
         * Obtiene el valor de la propiedad phone2.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPhone2() {
            return phone2;
        }

        /**
         * Define el valor de la propiedad phone2.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPhone2(String value) {
            this.phone2 = value;
        }

        /**
         * Obtiene el valor de la propiedad address.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAddress() {
            return address;
        }

        /**
         * Define el valor de la propiedad address.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAddress(String value) {
            this.address = value;
        }

        /**
         * Obtiene el valor de la propiedad streetnumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStreetnumber() {
            return streetnumber;
        }

        /**
         * Define el valor de la propiedad streetnumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStreetnumber(String value) {
            this.streetnumber = value;
        }

        /**
         * Obtiene el valor de la propiedad apartment.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getApartment() {
            return apartment;
        }

        /**
         * Define el valor de la propiedad apartment.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setApartment(String value) {
            this.apartment = value;
        }

        /**
         * Obtiene el valor de la propiedad housenumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHousenumber() {
            return housenumber;
        }

        /**
         * Define el valor de la propiedad housenumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHousenumber(String value) {
            this.housenumber = value;
        }

        /**
         * Obtiene el valor de la propiedad region.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegion() {
            return region;
        }

        /**
         * Define el valor de la propiedad region.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegion(String value) {
            this.region = value;
        }

        /**
         * Obtiene el valor de la propiedad commune.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCommune() {
            return commune;
        }

        /**
         * Define el valor de la propiedad commune.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCommune(String value) {
            this.commune = value;
        }

        /**
         * Obtiene el valor de la propiedad city.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCity() {
            return city;
        }

        /**
         * Define el valor de la propiedad city.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCity(String value) {
            this.city = value;
        }

        /**
         * Obtiene el valor de la propiedad location.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocation() {
            return location;
        }

        /**
         * Define el valor de la propiedad location.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocation(String value) {
            this.location = value;
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
     *         &lt;element name="detail" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="skubuyer" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="skuvendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean13buyer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="codeumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="umb_x_umc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="costaftertaxes" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="listprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="stylecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="styledescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="stylebrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tolerance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="productdeliverydate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *                   &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="freightcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="freightweight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="discounts" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                             &lt;element name="discount" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="charges" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                             &lt;element name="charge" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="predistributions" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                             &lt;element name="predistribution"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
     *                                       &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                                       &lt;element name="shipping_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="received_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="pending_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
        "detail"
    })
    public static class Details {

        protected List<Order.Details.Detail> detail;

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
         * {@link Order.Details.Detail }
         * 
         * 
         */
        public List<Order.Details.Detail> getDetail() {
            if (detail == null) {
                detail = new ArrayList<Order.Details.Detail>();
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
         *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean13buyer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="productdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="codeumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descriptionumc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="codeumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descriptionumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="umb_x_umc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="quantityumc" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="listcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="finalcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="costaftertaxes" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="listprice" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="stylecode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="styledescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="stylebrand" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tolerance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="productdeliverydate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
         *         &lt;element name="observation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="freightcost" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="freightweight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="discounts" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                   &lt;element name="discount" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="charges" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                   &lt;element name="charge" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="predistributions" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                   &lt;element name="predistribution"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
         *                             &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *                             &lt;element name="shipping_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="received_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="pending_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "position",
            "skubuyer",
            "skuvendor",
            "ean13",
            "ean13Buyer",
            "productdescription",
            "codeumc",
            "descriptionumc",
            "codeumb",
            "descriptionumb",
            "umbXUmc",
            "quantityumc",
            "innerpack",
            "listcost",
            "finalcost",
            "costaftertaxes",
            "listprice",
            "item",
            "ean1",
            "ean2",
            "ean3",
            "stylecode",
            "styledescription",
            "stylebrand",
            "numref1",
            "numref2",
            "tolerance",
            "productdeliverydate",
            "observation",
            "freightcost",
            "freightweight",
            "discounts",
            "charges",
            "predistributions"
        })
        public static class Detail {

            protected int position;
            @XmlElement(required = true)
            protected String skubuyer;
            protected String skuvendor;
            protected String ean13;
            @XmlElement(name = "ean13buyer")
            protected String ean13Buyer;
            protected String productdescription;
            protected String codeumc;
            protected String descriptionumc;
            protected String codeumb;
            protected String descriptionumb;
            @XmlElement(name = "umb_x_umc")
            protected Integer umbXUmc;
            protected Integer quantityumc;
            protected Integer innerpack;
            protected Double listcost;
            protected Double finalcost;
            protected Double costaftertaxes;
            protected Double listprice;
            protected String item;
            protected String ean1;
            protected String ean2;
            protected String ean3;
            protected String stylecode;
            protected String styledescription;
            protected String stylebrand;
            protected String numref1;
            protected String numref2;
            protected String tolerance;
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar productdeliverydate;
            protected String observation;
            protected Double freightcost;
            protected Double freightweight;
            protected Order.Details.Detail.Discounts discounts;
            protected Order.Details.Detail.Charges charges;
            protected Order.Details.Detail.Predistributions predistributions;

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
             * Obtiene el valor de la propiedad ean13Buyer.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan13Buyer() {
                return ean13Buyer;
            }

            /**
             * Define el valor de la propiedad ean13Buyer.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan13Buyer(String value) {
                this.ean13Buyer = value;
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
             * Obtiene el valor de la propiedad codeumb.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodeumb() {
                return codeumb;
            }

            /**
             * Define el valor de la propiedad codeumb.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodeumb(String value) {
                this.codeumb = value;
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
             * Obtiene el valor de la propiedad umbXUmc.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getUmbXUmc() {
                return umbXUmc;
            }

            /**
             * Define el valor de la propiedad umbXUmc.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setUmbXUmc(Integer value) {
                this.umbXUmc = value;
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
             * Obtiene el valor de la propiedad innerpack.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getInnerpack() {
                return innerpack;
            }

            /**
             * Define el valor de la propiedad innerpack.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setInnerpack(Integer value) {
                this.innerpack = value;
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

            /**
             * Obtiene el valor de la propiedad costaftertaxes.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getCostaftertaxes() {
                return costaftertaxes;
            }

            /**
             * Define el valor de la propiedad costaftertaxes.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setCostaftertaxes(Double value) {
                this.costaftertaxes = value;
            }

            /**
             * Obtiene el valor de la propiedad listprice.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getListprice() {
                return listprice;
            }

            /**
             * Define el valor de la propiedad listprice.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setListprice(Double value) {
                this.listprice = value;
            }

            /**
             * Obtiene el valor de la propiedad item.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItem() {
                return item;
            }

            /**
             * Define el valor de la propiedad item.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItem(String value) {
                this.item = value;
            }

            /**
             * Obtiene el valor de la propiedad ean1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan1() {
                return ean1;
            }

            /**
             * Define el valor de la propiedad ean1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan1(String value) {
                this.ean1 = value;
            }

            /**
             * Obtiene el valor de la propiedad ean2.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan2() {
                return ean2;
            }

            /**
             * Define el valor de la propiedad ean2.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan2(String value) {
                this.ean2 = value;
            }

            /**
             * Obtiene el valor de la propiedad ean3.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan3() {
                return ean3;
            }

            /**
             * Define el valor de la propiedad ean3.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan3(String value) {
                this.ean3 = value;
            }

            /**
             * Obtiene el valor de la propiedad stylecode.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStylecode() {
                return stylecode;
            }

            /**
             * Define el valor de la propiedad stylecode.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStylecode(String value) {
                this.stylecode = value;
            }

            /**
             * Obtiene el valor de la propiedad styledescription.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStyledescription() {
                return styledescription;
            }

            /**
             * Define el valor de la propiedad styledescription.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStyledescription(String value) {
                this.styledescription = value;
            }

            /**
             * Obtiene el valor de la propiedad stylebrand.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStylebrand() {
                return stylebrand;
            }

            /**
             * Define el valor de la propiedad stylebrand.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStylebrand(String value) {
                this.stylebrand = value;
            }

            /**
             * Obtiene el valor de la propiedad numref1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumref1() {
                return numref1;
            }

            /**
             * Define el valor de la propiedad numref1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumref1(String value) {
                this.numref1 = value;
            }

            /**
             * Obtiene el valor de la propiedad numref2.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumref2() {
                return numref2;
            }

            /**
             * Define el valor de la propiedad numref2.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumref2(String value) {
                this.numref2 = value;
            }

            /**
             * Obtiene el valor de la propiedad tolerance.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTolerance() {
                return tolerance;
            }

            /**
             * Define el valor de la propiedad tolerance.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTolerance(String value) {
                this.tolerance = value;
            }

            /**
             * Obtiene el valor de la propiedad productdeliverydate.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getProductdeliverydate() {
                return productdeliverydate;
            }

            /**
             * Define el valor de la propiedad productdeliverydate.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setProductdeliverydate(XMLGregorianCalendar value) {
                this.productdeliverydate = value;
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
             * Obtiene el valor de la propiedad freightcost.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getFreightcost() {
                return freightcost;
            }

            /**
             * Define el valor de la propiedad freightcost.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setFreightcost(Double value) {
                this.freightcost = value;
            }

            /**
             * Obtiene el valor de la propiedad freightweight.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getFreightweight() {
                return freightweight;
            }

            /**
             * Define el valor de la propiedad freightweight.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setFreightweight(Double value) {
                this.freightweight = value;
            }

            /**
             * Obtiene el valor de la propiedad discounts.
             * 
             * @return
             *     possible object is
             *     {@link Order.Details.Detail.Discounts }
             *     
             */
            public Order.Details.Detail.Discounts getDiscounts() {
                return discounts;
            }

            /**
             * Define el valor de la propiedad discounts.
             * 
             * @param value
             *     allowed object is
             *     {@link Order.Details.Detail.Discounts }
             *     
             */
            public void setDiscounts(Order.Details.Detail.Discounts value) {
                this.discounts = value;
            }

            /**
             * Obtiene el valor de la propiedad charges.
             * 
             * @return
             *     possible object is
             *     {@link Order.Details.Detail.Charges }
             *     
             */
            public Order.Details.Detail.Charges getCharges() {
                return charges;
            }

            /**
             * Define el valor de la propiedad charges.
             * 
             * @param value
             *     allowed object is
             *     {@link Order.Details.Detail.Charges }
             *     
             */
            public void setCharges(Order.Details.Detail.Charges value) {
                this.charges = value;
            }

            /**
             * Obtiene el valor de la propiedad predistributions.
             * 
             * @return
             *     possible object is
             *     {@link Order.Details.Detail.Predistributions }
             *     
             */
            public Order.Details.Detail.Predistributions getPredistributions() {
                return predistributions;
            }

            /**
             * Define el valor de la propiedad predistributions.
             * 
             * @param value
             *     allowed object is
             *     {@link Order.Details.Detail.Predistributions }
             *     
             */
            public void setPredistributions(Order.Details.Detail.Predistributions value) {
                this.predistributions = value;
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
             *         &lt;element name="charge" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
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
                "charge"
            })
            public static class Charges {

                protected List<DiscountCharge> charge;

                /**
                 * Gets the value of the charge property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the charge property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCharge().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link DiscountCharge }
                 * 
                 * 
                 */
                public List<DiscountCharge> getCharge() {
                    if (charge == null) {
                        charge = new ArrayList<DiscountCharge>();
                    }
                    return this.charge;
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
             *         &lt;element name="discount" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
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
                "discount"
            })
            public static class Discounts {

                protected List<DiscountCharge> discount;

                /**
                 * Gets the value of the discount property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the discount property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDiscount().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link DiscountCharge }
                 * 
                 * 
                 */
                public List<DiscountCharge> getDiscount() {
                    if (discount == null) {
                        discount = new ArrayList<DiscountCharge>();
                    }
                    return this.discount;
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
             *         &lt;element name="predistribution"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
             *                   &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
             *                   &lt;element name="shipping_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="received_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="pending_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "predistribution"
            })
            public static class Predistributions {

                protected List<Order.Details.Detail.Predistributions.Predistribution> predistribution;

                /**
                 * Gets the value of the predistribution property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the predistribution property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getPredistribution().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Order.Details.Detail.Predistributions.Predistribution }
                 * 
                 * 
                 */
                public List<Order.Details.Detail.Predistributions.Predistribution> getPredistribution() {
                    if (predistribution == null) {
                        predistribution = new ArrayList<Order.Details.Detail.Predistributions.Predistribution>();
                    }
                    return this.predistribution;
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
                 *         &lt;element name="deliveryplace" type="{http://www.bbr.cl/OC_Fullkom}local" minOccurs="0"/&gt;
                 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
                 *         &lt;element name="shipping_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="received_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="pending_quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "deliveryplace",
                    "quantity",
                    "shippingQuantity",
                    "receivedQuantity",
                    "pendingQuantity"
                })
                public static class Predistribution {

                    protected Local deliveryplace;
                    protected int quantity;
                    @XmlElement(name = "shipping_quantity")
                    protected String shippingQuantity;
                    @XmlElement(name = "received_quantity")
                    protected String receivedQuantity;
                    @XmlElement(name = "pending_quantity")
                    protected String pendingQuantity;

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
                     * Obtiene el valor de la propiedad quantity.
                     * 
                     */
                    public int getQuantity() {
                        return quantity;
                    }

                    /**
                     * Define el valor de la propiedad quantity.
                     * 
                     */
                    public void setQuantity(int value) {
                        this.quantity = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad shippingQuantity.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getShippingQuantity() {
                        return shippingQuantity;
                    }

                    /**
                     * Define el valor de la propiedad shippingQuantity.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setShippingQuantity(String value) {
                        this.shippingQuantity = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad receivedQuantity.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getReceivedQuantity() {
                        return receivedQuantity;
                    }

                    /**
                     * Define el valor de la propiedad receivedQuantity.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setReceivedQuantity(String value) {
                        this.receivedQuantity = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad pendingQuantity.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPendingQuantity() {
                        return pendingQuantity;
                    }

                    /**
                     * Define el valor de la propiedad pendingQuantity.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPendingQuantity(String value) {
                        this.pendingQuantity = value;
                    }

                }

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
     *         &lt;element name="discounts" type="{http://www.bbr.cl/OC_Fullkom}discount_charge"/&gt;
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
        "discounts"
    })
    public static class Discounts {

        protected List<DiscountCharge> discounts;

        /**
         * Gets the value of the discounts property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the discounts property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDiscounts().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DiscountCharge }
         * 
         * 
         */
        public List<DiscountCharge> getDiscounts() {
            if (discounts == null) {
                discounts = new ArrayList<DiscountCharge>();
            }
            return this.discounts;
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
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "code",
        "name"
    })
    public static class Section {

        @XmlElement(required = true)
        protected String code;
        protected String name;

        /**
         * Obtiene el valor de la propiedad code.
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
         * Define el valor de la propiedad code.
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

    }

}

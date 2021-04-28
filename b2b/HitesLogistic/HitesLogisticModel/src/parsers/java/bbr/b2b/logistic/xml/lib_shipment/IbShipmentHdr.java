//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:10:58 PM CLST 
//


package bbr.b2b.logistic.xml.lib_shipment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}shipment_nbr"/&gt;
 *         &lt;element ref="{}facility_code"/&gt;
 *         &lt;element ref="{}company_code"/&gt;
 *         &lt;element ref="{}trailer_nbr"/&gt;
 *         &lt;element ref="{}action_code"/&gt;
 *         &lt;element ref="{}ref_nbr"/&gt;
 *         &lt;element ref="{}shipment_type"/&gt;
 *         &lt;element ref="{}load_nbr"/&gt;
 *         &lt;element ref="{}manifest_nbr"/&gt;
 *         &lt;element ref="{}trailer_type"/&gt;
 *         &lt;element ref="{}vendor_info"/&gt;
 *         &lt;element ref="{}origin_info"/&gt;
 *         &lt;element ref="{}origin_code"/&gt;
 *         &lt;element ref="{}orig_shipped_units"/&gt;
 *         &lt;element ref="{}lock_code"/&gt;
 *         &lt;element ref="{}shipped_date"/&gt;
 *         &lt;element ref="{}orig_shipped_lpns"/&gt;
 *         &lt;element ref="{}cust_field_1"/&gt;
 *         &lt;element ref="{}cust_field_2"/&gt;
 *         &lt;element ref="{}cust_field_3"/&gt;
 *         &lt;element ref="{}cust_field_4"/&gt;
 *         &lt;element ref="{}cust_field_5"/&gt;
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
    "shipmentNbr",
    "facilityCode",
    "companyCode",
    "trailerNbr",
    "actionCode",
    "refNbr",
    "shipmentType",
    "loadNbr",
    "manifestNbr",
    "trailerType",
    "vendorInfo",
    "originInfo",
    "originCode",
    "origShippedUnits",
    "lockCode",
    "shippedDate",
    "origShippedLpns",
    "custField1",
    "custField2",
    "custField3",
    "custField4",
    "custField5"
})
@XmlRootElement(name = "ib_shipment_hdr")
public class IbShipmentHdr {

    @XmlElement(name = "shipment_nbr", required = true)
    protected String shipmentNbr;
    @XmlElement(name = "facility_code", required = true)
    protected String facilityCode;
    @XmlElement(name = "company_code", required = true)
    protected String companyCode;
    @XmlElement(name = "trailer_nbr", required = true)
    protected String trailerNbr;
    @XmlElement(name = "action_code", required = true)
    protected String actionCode;
    @XmlElement(name = "ref_nbr", required = true)
    protected String refNbr;
    @XmlElement(name = "shipment_type", required = true)
    protected String shipmentType;
    @XmlElement(name = "load_nbr", required = true)
    protected String loadNbr;
    @XmlElement(name = "manifest_nbr", required = true)
    protected String manifestNbr;
    @XmlElement(name = "trailer_type", required = true)
    protected String trailerType;
    @XmlElement(name = "vendor_info", required = true)
    protected String vendorInfo;
    @XmlElement(name = "origin_info", required = true)
    protected String originInfo;
    @XmlElement(name = "origin_code", required = true)
    protected String originCode;
    @XmlElement(name = "orig_shipped_units")
    protected int origShippedUnits;
    @XmlElement(name = "lock_code", required = true)
    protected String lockCode;
    @XmlElement(name = "shipped_date", required = true)
    protected String shippedDate;
    @XmlElement(name = "orig_shipped_lpns")
    protected int origShippedLpns;
    @XmlElement(name = "cust_field_1", required = true)
    protected String custField1;
    @XmlElement(name = "cust_field_2", required = true)
    protected String custField2;
    @XmlElement(name = "cust_field_3", required = true)
    protected String custField3;
    @XmlElement(name = "cust_field_4", required = true)
    protected String custField4;
    @XmlElement(name = "cust_field_5", required = true)
    protected String custField5;

    /**
     * Obtiene el valor de la propiedad shipmentNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentNbr() {
        return shipmentNbr;
    }

    /**
     * Define el valor de la propiedad shipmentNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentNbr(String value) {
        this.shipmentNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad facilityCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * Define el valor de la propiedad facilityCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCode(String value) {
        this.facilityCode = value;
    }

    /**
     * Obtiene el valor de la propiedad companyCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Define el valor de la propiedad companyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

    /**
     * Obtiene el valor de la propiedad trailerNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrailerNbr() {
        return trailerNbr;
    }

    /**
     * Define el valor de la propiedad trailerNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrailerNbr(String value) {
        this.trailerNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad actionCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * Define el valor de la propiedad actionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionCode(String value) {
        this.actionCode = value;
    }

    /**
     * Obtiene el valor de la propiedad refNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNbr() {
        return refNbr;
    }

    /**
     * Define el valor de la propiedad refNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNbr(String value) {
        this.refNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad shipmentType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentType() {
        return shipmentType;
    }

    /**
     * Define el valor de la propiedad shipmentType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentType(String value) {
        this.shipmentType = value;
    }

    /**
     * Obtiene el valor de la propiedad loadNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadNbr() {
        return loadNbr;
    }

    /**
     * Define el valor de la propiedad loadNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadNbr(String value) {
        this.loadNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad manifestNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManifestNbr() {
        return manifestNbr;
    }

    /**
     * Define el valor de la propiedad manifestNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManifestNbr(String value) {
        this.manifestNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad trailerType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrailerType() {
        return trailerType;
    }

    /**
     * Define el valor de la propiedad trailerType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrailerType(String value) {
        this.trailerType = value;
    }

    /**
     * Obtiene el valor de la propiedad vendorInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorInfo() {
        return vendorInfo;
    }

    /**
     * Define el valor de la propiedad vendorInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorInfo(String value) {
        this.vendorInfo = value;
    }

    /**
     * Obtiene el valor de la propiedad originInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginInfo() {
        return originInfo;
    }

    /**
     * Define el valor de la propiedad originInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginInfo(String value) {
        this.originInfo = value;
    }

    /**
     * Obtiene el valor de la propiedad originCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginCode() {
        return originCode;
    }

    /**
     * Define el valor de la propiedad originCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginCode(String value) {
        this.originCode = value;
    }

    /**
     * Obtiene el valor de la propiedad origShippedUnits.
     * 
     */
    public int getOrigShippedUnits() {
        return origShippedUnits;
    }

    /**
     * Define el valor de la propiedad origShippedUnits.
     * 
     */
    public void setOrigShippedUnits(int value) {
        this.origShippedUnits = value;
    }

    /**
     * Obtiene el valor de la propiedad lockCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockCode() {
        return lockCode;
    }

    /**
     * Define el valor de la propiedad lockCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockCode(String value) {
        this.lockCode = value;
    }

    /**
     * Obtiene el valor de la propiedad shippedDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippedDate() {
        return shippedDate;
    }

    /**
     * Define el valor de la propiedad shippedDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippedDate(String value) {
        this.shippedDate = value;
    }

    /**
     * Obtiene el valor de la propiedad origShippedLpns.
     * 
     */
    public int getOrigShippedLpns() {
        return origShippedLpns;
    }

    /**
     * Define el valor de la propiedad origShippedLpns.
     * 
     */
    public void setOrigShippedLpns(int value) {
        this.origShippedLpns = value;
    }

    /**
     * Obtiene el valor de la propiedad custField1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustField1() {
        return custField1;
    }

    /**
     * Define el valor de la propiedad custField1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustField1(String value) {
        this.custField1 = value;
    }

    /**
     * Obtiene el valor de la propiedad custField2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustField2() {
        return custField2;
    }

    /**
     * Define el valor de la propiedad custField2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustField2(String value) {
        this.custField2 = value;
    }

    /**
     * Obtiene el valor de la propiedad custField3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustField3() {
        return custField3;
    }

    /**
     * Define el valor de la propiedad custField3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustField3(String value) {
        this.custField3 = value;
    }

    /**
     * Obtiene el valor de la propiedad custField4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustField4() {
        return custField4;
    }

    /**
     * Define el valor de la propiedad custField4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustField4(String value) {
        this.custField4 = value;
    }

    /**
     * Obtiene el valor de la propiedad custField5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustField5() {
        return custField5;
    }

    /**
     * Define el valor de la propiedad custField5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustField5(String value) {
        this.custField5 = value;
    }

}

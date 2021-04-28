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
 *         &lt;element ref="{}seq_nbr"/&gt;
 *         &lt;element ref="{}action_code"/&gt;
 *         &lt;element ref="{}lpn_nbr"/&gt;
 *         &lt;element ref="{}lpn_weight"/&gt;
 *         &lt;element ref="{}lpn_volume"/&gt;
 *         &lt;element ref="{}item_alternate_code"/&gt;
 *         &lt;element ref="{}item_part_a"/&gt;
 *         &lt;element ref="{}item_part_b"/&gt;
 *         &lt;element ref="{}item_part_c"/&gt;
 *         &lt;element ref="{}item_part_d"/&gt;
 *         &lt;element ref="{}item_part_e"/&gt;
 *         &lt;element ref="{}item_part_f"/&gt;
 *         &lt;element ref="{}pre_pack_code"/&gt;
 *         &lt;element ref="{}pre_pack_ratio"/&gt;
 *         &lt;element ref="{}pre_pack_total_units"/&gt;
 *         &lt;element ref="{}invn_attr_a"/&gt;
 *         &lt;element ref="{}invn_attr_b"/&gt;
 *         &lt;element ref="{}invn_attr_c"/&gt;
 *         &lt;element ref="{}shipped_qty"/&gt;
 *         &lt;element ref="{}priority_date"/&gt;
 *         &lt;element ref="{}po_nbr"/&gt;
 *         &lt;element ref="{}pallet_nbr"/&gt;
 *         &lt;element ref="{}putaway_type"/&gt;
 *         &lt;element ref="{}expiry_date"/&gt;
 *         &lt;element ref="{}batch_nbr"/&gt;
 *         &lt;element ref="{}recv_xdock_facility_code"/&gt;
 *         &lt;element ref="{}cust_field_1"/&gt;
 *         &lt;element ref="{}cust_field_2"/&gt;
 *         &lt;element ref="{}cust_field_3"/&gt;
 *         &lt;element ref="{}cust_field_4"/&gt;
 *         &lt;element ref="{}cust_field_5"/&gt;
 *         &lt;element ref="{}lpn_is_physical_pallet_flg"/&gt;
 *         &lt;element ref="{}po_seq_nbr"/&gt;
 *         &lt;element ref="{}pre_pack_ratio_seq"/&gt;
 *         &lt;element ref="{}lpn_lock_code"/&gt;
 *         &lt;element ref="{}item_barcode"/&gt;
 *         &lt;element ref="{}uom"/&gt;
 *         &lt;element ref="{}lpn_length"/&gt;
 *         &lt;element ref="{}lpn_width"/&gt;
 *         &lt;element ref="{}lpn_height"/&gt;
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
    "seqNbr",
    "actionCode",
    "lpnNbr",
    "lpnWeight",
    "lpnVolume",
    "itemAlternateCode",
    "itemPartA",
    "itemPartB",
    "itemPartC",
    "itemPartD",
    "itemPartE",
    "itemPartF",
    "prePackCode",
    "prePackRatio",
    "prePackTotalUnits",
    "invnAttrA",
    "invnAttrB",
    "invnAttrC",
    "shippedQty",
    "priorityDate",
    "poNbr",
    "palletNbr",
    "putawayType",
    "expiryDate",
    "batchNbr",
    "recvXdockFacilityCode",
    "custField1",
    "custField2",
    "custField3",
    "custField4",
    "custField5",
    "lpnIsPhysicalPalletFlg",
    "poSeqNbr",
    "prePackRatioSeq",
    "lpnLockCode",
    "itemBarcode",
    "uom",
    "lpnLength",
    "lpnWidth",
    "lpnHeight"
})
@XmlRootElement(name = "ib_shipment_dtl")
public class IbShipmentDtl {

    @XmlElement(name = "seq_nbr")
    protected int seqNbr;
    @XmlElement(name = "action_code", required = true)
    protected String actionCode;
    @XmlElement(name = "lpn_nbr", required = true)
    protected String lpnNbr;
    @XmlElement(name = "lpn_weight", required = true)
    protected String lpnWeight;
    @XmlElement(name = "lpn_volume", required = true)
    protected String lpnVolume;
    @XmlElement(name = "item_alternate_code", required = true)
    protected String itemAlternateCode;
    @XmlElement(name = "item_part_a", required = true)
    protected String itemPartA;
    @XmlElement(name = "item_part_b", required = true)
    protected String itemPartB;
    @XmlElement(name = "item_part_c", required = true)
    protected String itemPartC;
    @XmlElement(name = "item_part_d", required = true)
    protected String itemPartD;
    @XmlElement(name = "item_part_e", required = true)
    protected String itemPartE;
    @XmlElement(name = "item_part_f", required = true)
    protected String itemPartF;
    @XmlElement(name = "pre_pack_code", required = true)
    protected String prePackCode;
    @XmlElement(name = "pre_pack_ratio")
    protected int prePackRatio;
    @XmlElement(name = "pre_pack_total_units")
    protected int prePackTotalUnits;
    @XmlElement(name = "invn_attr_a", required = true)
    protected String invnAttrA;
    @XmlElement(name = "invn_attr_b", required = true)
    protected String invnAttrB;
    @XmlElement(name = "invn_attr_c", required = true)
    protected String invnAttrC;
    @XmlElement(name = "shipped_qty", required = true)
    protected String shippedQty;
    @XmlElement(name = "priority_date", required = true)
    protected String priorityDate;
    @XmlElement(name = "po_nbr", required = true)
    protected String poNbr;
    @XmlElement(name = "pallet_nbr", required = true)
    protected String palletNbr;
    @XmlElement(name = "putaway_type", required = true)
    protected String putawayType;
    @XmlElement(name = "expiry_date", required = true)
    protected String expiryDate;
    @XmlElement(name = "batch_nbr", required = true)
    protected String batchNbr;
    @XmlElement(name = "recv_xdock_facility_code", required = true)
    protected String recvXdockFacilityCode;
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
    @XmlElement(name = "lpn_is_physical_pallet_flg")
    protected boolean lpnIsPhysicalPalletFlg;
    @XmlElement(name = "po_seq_nbr")
    protected int poSeqNbr;
    @XmlElement(name = "pre_pack_ratio_seq")
    protected int prePackRatioSeq;
    @XmlElement(name = "lpn_lock_code", required = true)
    protected String lpnLockCode;
    @XmlElement(name = "item_barcode", required = true)
    protected String itemBarcode;
    @XmlElement(required = true)
    protected String uom;
    @XmlElement(name = "lpn_length", required = true)
    protected String lpnLength;
    @XmlElement(name = "lpn_width", required = true)
    protected String lpnWidth;
    @XmlElement(name = "lpn_height", required = true)
    protected String lpnHeight;

    /**
     * Obtiene el valor de la propiedad seqNbr.
     * 
     */
    public int getSeqNbr() {
        return seqNbr;
    }

    /**
     * Define el valor de la propiedad seqNbr.
     * 
     */
    public void setSeqNbr(int value) {
        this.seqNbr = value;
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
     * Obtiene el valor de la propiedad lpnNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnNbr() {
        return lpnNbr;
    }

    /**
     * Define el valor de la propiedad lpnNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnNbr(String value) {
        this.lpnNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnWeight.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnWeight() {
        return lpnWeight;
    }

    /**
     * Define el valor de la propiedad lpnWeight.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnWeight(String value) {
        this.lpnWeight = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnVolume.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnVolume() {
        return lpnVolume;
    }

    /**
     * Define el valor de la propiedad lpnVolume.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnVolume(String value) {
        this.lpnVolume = value;
    }

    /**
     * Obtiene el valor de la propiedad itemAlternateCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemAlternateCode() {
        return itemAlternateCode;
    }

    /**
     * Define el valor de la propiedad itemAlternateCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemAlternateCode(String value) {
        this.itemAlternateCode = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartA() {
        return itemPartA;
    }

    /**
     * Define el valor de la propiedad itemPartA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartA(String value) {
        this.itemPartA = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartB.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartB() {
        return itemPartB;
    }

    /**
     * Define el valor de la propiedad itemPartB.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartB(String value) {
        this.itemPartB = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartC() {
        return itemPartC;
    }

    /**
     * Define el valor de la propiedad itemPartC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartC(String value) {
        this.itemPartC = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartD.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartD() {
        return itemPartD;
    }

    /**
     * Define el valor de la propiedad itemPartD.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartD(String value) {
        this.itemPartD = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartE() {
        return itemPartE;
    }

    /**
     * Define el valor de la propiedad itemPartE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartE(String value) {
        this.itemPartE = value;
    }

    /**
     * Obtiene el valor de la propiedad itemPartF.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemPartF() {
        return itemPartF;
    }

    /**
     * Define el valor de la propiedad itemPartF.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemPartF(String value) {
        this.itemPartF = value;
    }

    /**
     * Obtiene el valor de la propiedad prePackCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrePackCode() {
        return prePackCode;
    }

    /**
     * Define el valor de la propiedad prePackCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrePackCode(String value) {
        this.prePackCode = value;
    }

    /**
     * Obtiene el valor de la propiedad prePackRatio.
     * 
     */
    public int getPrePackRatio() {
        return prePackRatio;
    }

    /**
     * Define el valor de la propiedad prePackRatio.
     * 
     */
    public void setPrePackRatio(int value) {
        this.prePackRatio = value;
    }

    /**
     * Obtiene el valor de la propiedad prePackTotalUnits.
     * 
     */
    public int getPrePackTotalUnits() {
        return prePackTotalUnits;
    }

    /**
     * Define el valor de la propiedad prePackTotalUnits.
     * 
     */
    public void setPrePackTotalUnits(int value) {
        this.prePackTotalUnits = value;
    }

    /**
     * Obtiene el valor de la propiedad invnAttrA.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvnAttrA() {
        return invnAttrA;
    }

    /**
     * Define el valor de la propiedad invnAttrA.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvnAttrA(String value) {
        this.invnAttrA = value;
    }

    /**
     * Obtiene el valor de la propiedad invnAttrB.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvnAttrB() {
        return invnAttrB;
    }

    /**
     * Define el valor de la propiedad invnAttrB.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvnAttrB(String value) {
        this.invnAttrB = value;
    }

    /**
     * Obtiene el valor de la propiedad invnAttrC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvnAttrC() {
        return invnAttrC;
    }

    /**
     * Define el valor de la propiedad invnAttrC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvnAttrC(String value) {
        this.invnAttrC = value;
    }

    /**
     * Obtiene el valor de la propiedad shippedQty.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippedQty() {
        return shippedQty;
    }

    /**
     * Define el valor de la propiedad shippedQty.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippedQty(String value) {
        this.shippedQty = value;
    }

    /**
     * Obtiene el valor de la propiedad priorityDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorityDate() {
        return priorityDate;
    }

    /**
     * Define el valor de la propiedad priorityDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorityDate(String value) {
        this.priorityDate = value;
    }

    /**
     * Obtiene el valor de la propiedad poNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoNbr() {
        return poNbr;
    }

    /**
     * Define el valor de la propiedad poNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoNbr(String value) {
        this.poNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad palletNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPalletNbr() {
        return palletNbr;
    }

    /**
     * Define el valor de la propiedad palletNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPalletNbr(String value) {
        this.palletNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad putawayType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPutawayType() {
        return putawayType;
    }

    /**
     * Define el valor de la propiedad putawayType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPutawayType(String value) {
        this.putawayType = value;
    }

    /**
     * Obtiene el valor de la propiedad expiryDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Define el valor de la propiedad expiryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }

    /**
     * Obtiene el valor de la propiedad batchNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchNbr() {
        return batchNbr;
    }

    /**
     * Define el valor de la propiedad batchNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchNbr(String value) {
        this.batchNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad recvXdockFacilityCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecvXdockFacilityCode() {
        return recvXdockFacilityCode;
    }

    /**
     * Define el valor de la propiedad recvXdockFacilityCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecvXdockFacilityCode(String value) {
        this.recvXdockFacilityCode = value;
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

    /**
     * Obtiene el valor de la propiedad lpnIsPhysicalPalletFlg.
     * 
     */
    public boolean isLpnIsPhysicalPalletFlg() {
        return lpnIsPhysicalPalletFlg;
    }

    /**
     * Define el valor de la propiedad lpnIsPhysicalPalletFlg.
     * 
     */
    public void setLpnIsPhysicalPalletFlg(boolean value) {
        this.lpnIsPhysicalPalletFlg = value;
    }

    /**
     * Obtiene el valor de la propiedad poSeqNbr.
     * 
     */
    public int getPoSeqNbr() {
        return poSeqNbr;
    }

    /**
     * Define el valor de la propiedad poSeqNbr.
     * 
     */
    public void setPoSeqNbr(int value) {
        this.poSeqNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad prePackRatioSeq.
     * 
     */
    public int getPrePackRatioSeq() {
        return prePackRatioSeq;
    }

    /**
     * Define el valor de la propiedad prePackRatioSeq.
     * 
     */
    public void setPrePackRatioSeq(int value) {
        this.prePackRatioSeq = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnLockCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnLockCode() {
        return lpnLockCode;
    }

    /**
     * Define el valor de la propiedad lpnLockCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnLockCode(String value) {
        this.lpnLockCode = value;
    }

    /**
     * Obtiene el valor de la propiedad itemBarcode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemBarcode() {
        return itemBarcode;
    }

    /**
     * Define el valor de la propiedad itemBarcode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemBarcode(String value) {
        this.itemBarcode = value;
    }

    /**
     * Obtiene el valor de la propiedad uom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUom() {
        return uom;
    }

    /**
     * Define el valor de la propiedad uom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUom(String value) {
        this.uom = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnLength.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnLength() {
        return lpnLength;
    }

    /**
     * Define el valor de la propiedad lpnLength.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnLength(String value) {
        this.lpnLength = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnWidth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnWidth() {
        return lpnWidth;
    }

    /**
     * Define el valor de la propiedad lpnWidth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnWidth(String value) {
        this.lpnWidth = value;
    }

    /**
     * Obtiene el valor de la propiedad lpnHeight.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLpnHeight() {
        return lpnHeight;
    }

    /**
     * Define el valor de la propiedad lpnHeight.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLpnHeight(String value) {
        this.lpnHeight = value;
    }

}

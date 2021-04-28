//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}SGTYP" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZLTYP" minOccurs="0"/&gt;
 *         &lt;element ref="{}LVALT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ALTNO" minOccurs="0"/&gt;
 *         &lt;element ref="{}ALREF" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZLART" minOccurs="0"/&gt;
 *         &lt;element ref="{}POSEX" minOccurs="0"/&gt;
 *         &lt;element ref="{}RANG" minOccurs="0"/&gt;
 *         &lt;element ref="{}EXGRP" minOccurs="0"/&gt;
 *         &lt;element ref="{}UEPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MATKL" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENEE" minOccurs="0"/&gt;
 *         &lt;element ref="{}BMNG2" minOccurs="0"/&gt;
 *         &lt;element ref="{}PMENE" minOccurs="0"/&gt;
 *         &lt;element ref="{}BPUMN" minOccurs="0"/&gt;
 *         &lt;element ref="{}BPUMZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VPREI" minOccurs="0"/&gt;
 *         &lt;element ref="{}PEINH" minOccurs="0"/&gt;
 *         &lt;element ref="{}NETWR" minOccurs="0"/&gt;
 *         &lt;element ref="{}ANETW" minOccurs="0"/&gt;
 *         &lt;element ref="{}SKFBP" minOccurs="0"/&gt;
 *         &lt;element ref="{}CURCY" minOccurs="0"/&gt;
 *         &lt;element ref="{}PREIS" minOccurs="0"/&gt;
 *         &lt;element ref="{}ACTION" minOccurs="0"/&gt;
 *         &lt;element ref="{}KZABS" minOccurs="0"/&gt;
 *         &lt;element ref="{}UEBTO" minOccurs="0"/&gt;
 *         &lt;element ref="{}UEBTK" minOccurs="0"/&gt;
 *         &lt;element ref="{}LBNUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUSGB" minOccurs="0"/&gt;
 *         &lt;element ref="{}FRPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}TOPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}KTXT1" minOccurs="0"/&gt;
 *         &lt;element ref="{}KTXT2" minOccurs="0"/&gt;
 *         &lt;element ref="{}PERNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}LGART" minOccurs="0"/&gt;
 *         &lt;element ref="{}STELL" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZWERT" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC02" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC03" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC04" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC05" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC06" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC07" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDCA1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC19" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC17" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC18" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDCT1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="SEGMENT" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sgtyp",
    "zltyp",
    "lvalt",
    "altno",
    "alref",
    "zlart",
    "posex",
    "rang",
    "exgrp",
    "uepos",
    "matkl",
    "menge",
    "menee",
    "bmng2",
    "pmene",
    "bpumn",
    "bpumz",
    "vprei",
    "peinh",
    "netwr",
    "anetw",
    "skfbp",
    "curcy",
    "preis",
    "action",
    "kzabs",
    "uebto",
    "uebtk",
    "lbnum",
    "ausgb",
    "frpos",
    "topos",
    "ktxt1",
    "ktxt2",
    "pernr",
    "lgart",
    "stell",
    "zwert",
    "e1EDC02",
    "e1EDC03",
    "e1EDC04",
    "e1EDC05",
    "e1EDC06",
    "e1EDC07",
    "e1EDCA1",
    "e1EDC19",
    "e1EDC17",
    "e1EDC18",
    "e1EDCT1"
})
@XmlRootElement(name = "E1EDC01")
public class E1EDC01 {

    @XmlElement(name = "SGTYP")
    protected SGTYP sgtyp;
    @XmlElement(name = "ZLTYP")
    protected ZLTYP zltyp;
    @XmlElement(name = "LVALT")
    protected LVALT lvalt;
    @XmlElement(name = "ALTNO")
    protected ALTNO altno;
    @XmlElement(name = "ALREF")
    protected ALREF alref;
    @XmlElement(name = "ZLART")
    protected ZLART zlart;
    @XmlElement(name = "POSEX")
    protected POSEX posex;
    @XmlElement(name = "RANG")
    protected RANG rang;
    @XmlElement(name = "EXGRP")
    protected EXGRP exgrp;
    @XmlElement(name = "UEPOS")
    protected UEPOS uepos;
    @XmlElement(name = "MATKL")
    protected MATKL matkl;
    @XmlElement(name = "MENGE")
    protected MENGE menge;
    @XmlElement(name = "MENEE")
    protected MENEE menee;
    @XmlElement(name = "BMNG2")
    protected BMNG2 bmng2;
    @XmlElement(name = "PMENE")
    protected PMENE pmene;
    @XmlElement(name = "BPUMN")
    protected BPUMN bpumn;
    @XmlElement(name = "BPUMZ")
    protected BPUMZ bpumz;
    @XmlElement(name = "VPREI")
    protected VPREI vprei;
    @XmlElement(name = "PEINH")
    protected PEINH peinh;
    @XmlElement(name = "NETWR")
    protected NETWR netwr;
    @XmlElement(name = "ANETW")
    protected ANETW anetw;
    @XmlElement(name = "SKFBP")
    protected SKFBP skfbp;
    @XmlElement(name = "CURCY")
    protected CURCY curcy;
    @XmlElement(name = "PREIS")
    protected PREIS preis;
    @XmlElement(name = "ACTION")
    protected ACTION action;
    @XmlElement(name = "KZABS")
    protected KZABS kzabs;
    @XmlElement(name = "UEBTO")
    protected UEBTO uebto;
    @XmlElement(name = "UEBTK")
    protected UEBTK uebtk;
    @XmlElement(name = "LBNUM")
    protected LBNUM lbnum;
    @XmlElement(name = "AUSGB")
    protected AUSGB ausgb;
    @XmlElement(name = "FRPOS")
    protected FRPOS frpos;
    @XmlElement(name = "TOPOS")
    protected TOPOS topos;
    @XmlElement(name = "KTXT1")
    protected KTXT1 ktxt1;
    @XmlElement(name = "KTXT2")
    protected KTXT2 ktxt2;
    @XmlElement(name = "PERNR")
    protected PERNR pernr;
    @XmlElement(name = "LGART")
    protected LGART lgart;
    @XmlElement(name = "STELL")
    protected STELL stell;
    @XmlElement(name = "ZWERT")
    protected ZWERT zwert;
    @XmlElement(name = "E1EDC02")
    protected List<E1EDC02> e1EDC02;
    @XmlElement(name = "E1EDC03")
    protected List<E1EDC03> e1EDC03;
    @XmlElement(name = "E1EDC04")
    protected List<E1EDC04> e1EDC04;
    @XmlElement(name = "E1EDC05")
    protected List<E1EDC05> e1EDC05;
    @XmlElement(name = "E1EDC06")
    protected E1EDC06 e1EDC06;
    @XmlElement(name = "E1EDC07")
    protected E1EDC07 e1EDC07;
    @XmlElement(name = "E1EDCA1")
    protected List<E1EDCA1> e1EDCA1;
    @XmlElement(name = "E1EDC19")
    protected List<E1EDC19> e1EDC19;
    @XmlElement(name = "E1EDC17")
    protected List<E1EDC17> e1EDC17;
    @XmlElement(name = "E1EDC18")
    protected List<E1EDC18> e1EDC18;
    @XmlElement(name = "E1EDCT1")
    protected List<E1EDCT1> e1EDCT1;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad sgtyp.
     * 
     * @return
     *     possible object is
     *     {@link SGTYP }
     *     
     */
    public SGTYP getSGTYP() {
        return sgtyp;
    }

    /**
     * Define el valor de la propiedad sgtyp.
     * 
     * @param value
     *     allowed object is
     *     {@link SGTYP }
     *     
     */
    public void setSGTYP(SGTYP value) {
        this.sgtyp = value;
    }

    /**
     * Obtiene el valor de la propiedad zltyp.
     * 
     * @return
     *     possible object is
     *     {@link ZLTYP }
     *     
     */
    public ZLTYP getZLTYP() {
        return zltyp;
    }

    /**
     * Define el valor de la propiedad zltyp.
     * 
     * @param value
     *     allowed object is
     *     {@link ZLTYP }
     *     
     */
    public void setZLTYP(ZLTYP value) {
        this.zltyp = value;
    }

    /**
     * Obtiene el valor de la propiedad lvalt.
     * 
     * @return
     *     possible object is
     *     {@link LVALT }
     *     
     */
    public LVALT getLVALT() {
        return lvalt;
    }

    /**
     * Define el valor de la propiedad lvalt.
     * 
     * @param value
     *     allowed object is
     *     {@link LVALT }
     *     
     */
    public void setLVALT(LVALT value) {
        this.lvalt = value;
    }

    /**
     * Obtiene el valor de la propiedad altno.
     * 
     * @return
     *     possible object is
     *     {@link ALTNO }
     *     
     */
    public ALTNO getALTNO() {
        return altno;
    }

    /**
     * Define el valor de la propiedad altno.
     * 
     * @param value
     *     allowed object is
     *     {@link ALTNO }
     *     
     */
    public void setALTNO(ALTNO value) {
        this.altno = value;
    }

    /**
     * Obtiene el valor de la propiedad alref.
     * 
     * @return
     *     possible object is
     *     {@link ALREF }
     *     
     */
    public ALREF getALREF() {
        return alref;
    }

    /**
     * Define el valor de la propiedad alref.
     * 
     * @param value
     *     allowed object is
     *     {@link ALREF }
     *     
     */
    public void setALREF(ALREF value) {
        this.alref = value;
    }

    /**
     * Obtiene el valor de la propiedad zlart.
     * 
     * @return
     *     possible object is
     *     {@link ZLART }
     *     
     */
    public ZLART getZLART() {
        return zlart;
    }

    /**
     * Define el valor de la propiedad zlart.
     * 
     * @param value
     *     allowed object is
     *     {@link ZLART }
     *     
     */
    public void setZLART(ZLART value) {
        this.zlart = value;
    }

    /**
     * Obtiene el valor de la propiedad posex.
     * 
     * @return
     *     possible object is
     *     {@link POSEX }
     *     
     */
    public POSEX getPOSEX() {
        return posex;
    }

    /**
     * Define el valor de la propiedad posex.
     * 
     * @param value
     *     allowed object is
     *     {@link POSEX }
     *     
     */
    public void setPOSEX(POSEX value) {
        this.posex = value;
    }

    /**
     * Obtiene el valor de la propiedad rang.
     * 
     * @return
     *     possible object is
     *     {@link RANG }
     *     
     */
    public RANG getRANG() {
        return rang;
    }

    /**
     * Define el valor de la propiedad rang.
     * 
     * @param value
     *     allowed object is
     *     {@link RANG }
     *     
     */
    public void setRANG(RANG value) {
        this.rang = value;
    }

    /**
     * Obtiene el valor de la propiedad exgrp.
     * 
     * @return
     *     possible object is
     *     {@link EXGRP }
     *     
     */
    public EXGRP getEXGRP() {
        return exgrp;
    }

    /**
     * Define el valor de la propiedad exgrp.
     * 
     * @param value
     *     allowed object is
     *     {@link EXGRP }
     *     
     */
    public void setEXGRP(EXGRP value) {
        this.exgrp = value;
    }

    /**
     * Obtiene el valor de la propiedad uepos.
     * 
     * @return
     *     possible object is
     *     {@link UEPOS }
     *     
     */
    public UEPOS getUEPOS() {
        return uepos;
    }

    /**
     * Define el valor de la propiedad uepos.
     * 
     * @param value
     *     allowed object is
     *     {@link UEPOS }
     *     
     */
    public void setUEPOS(UEPOS value) {
        this.uepos = value;
    }

    /**
     * Obtiene el valor de la propiedad matkl.
     * 
     * @return
     *     possible object is
     *     {@link MATKL }
     *     
     */
    public MATKL getMATKL() {
        return matkl;
    }

    /**
     * Define el valor de la propiedad matkl.
     * 
     * @param value
     *     allowed object is
     *     {@link MATKL }
     *     
     */
    public void setMATKL(MATKL value) {
        this.matkl = value;
    }

    /**
     * Obtiene el valor de la propiedad menge.
     * 
     * @return
     *     possible object is
     *     {@link MENGE }
     *     
     */
    public MENGE getMENGE() {
        return menge;
    }

    /**
     * Define el valor de la propiedad menge.
     * 
     * @param value
     *     allowed object is
     *     {@link MENGE }
     *     
     */
    public void setMENGE(MENGE value) {
        this.menge = value;
    }

    /**
     * Obtiene el valor de la propiedad menee.
     * 
     * @return
     *     possible object is
     *     {@link MENEE }
     *     
     */
    public MENEE getMENEE() {
        return menee;
    }

    /**
     * Define el valor de la propiedad menee.
     * 
     * @param value
     *     allowed object is
     *     {@link MENEE }
     *     
     */
    public void setMENEE(MENEE value) {
        this.menee = value;
    }

    /**
     * Obtiene el valor de la propiedad bmng2.
     * 
     * @return
     *     possible object is
     *     {@link BMNG2 }
     *     
     */
    public BMNG2 getBMNG2() {
        return bmng2;
    }

    /**
     * Define el valor de la propiedad bmng2.
     * 
     * @param value
     *     allowed object is
     *     {@link BMNG2 }
     *     
     */
    public void setBMNG2(BMNG2 value) {
        this.bmng2 = value;
    }

    /**
     * Obtiene el valor de la propiedad pmene.
     * 
     * @return
     *     possible object is
     *     {@link PMENE }
     *     
     */
    public PMENE getPMENE() {
        return pmene;
    }

    /**
     * Define el valor de la propiedad pmene.
     * 
     * @param value
     *     allowed object is
     *     {@link PMENE }
     *     
     */
    public void setPMENE(PMENE value) {
        this.pmene = value;
    }

    /**
     * Obtiene el valor de la propiedad bpumn.
     * 
     * @return
     *     possible object is
     *     {@link BPUMN }
     *     
     */
    public BPUMN getBPUMN() {
        return bpumn;
    }

    /**
     * Define el valor de la propiedad bpumn.
     * 
     * @param value
     *     allowed object is
     *     {@link BPUMN }
     *     
     */
    public void setBPUMN(BPUMN value) {
        this.bpumn = value;
    }

    /**
     * Obtiene el valor de la propiedad bpumz.
     * 
     * @return
     *     possible object is
     *     {@link BPUMZ }
     *     
     */
    public BPUMZ getBPUMZ() {
        return bpumz;
    }

    /**
     * Define el valor de la propiedad bpumz.
     * 
     * @param value
     *     allowed object is
     *     {@link BPUMZ }
     *     
     */
    public void setBPUMZ(BPUMZ value) {
        this.bpumz = value;
    }

    /**
     * Obtiene el valor de la propiedad vprei.
     * 
     * @return
     *     possible object is
     *     {@link VPREI }
     *     
     */
    public VPREI getVPREI() {
        return vprei;
    }

    /**
     * Define el valor de la propiedad vprei.
     * 
     * @param value
     *     allowed object is
     *     {@link VPREI }
     *     
     */
    public void setVPREI(VPREI value) {
        this.vprei = value;
    }

    /**
     * Obtiene el valor de la propiedad peinh.
     * 
     * @return
     *     possible object is
     *     {@link PEINH }
     *     
     */
    public PEINH getPEINH() {
        return peinh;
    }

    /**
     * Define el valor de la propiedad peinh.
     * 
     * @param value
     *     allowed object is
     *     {@link PEINH }
     *     
     */
    public void setPEINH(PEINH value) {
        this.peinh = value;
    }

    /**
     * Obtiene el valor de la propiedad netwr.
     * 
     * @return
     *     possible object is
     *     {@link NETWR }
     *     
     */
    public NETWR getNETWR() {
        return netwr;
    }

    /**
     * Define el valor de la propiedad netwr.
     * 
     * @param value
     *     allowed object is
     *     {@link NETWR }
     *     
     */
    public void setNETWR(NETWR value) {
        this.netwr = value;
    }

    /**
     * Obtiene el valor de la propiedad anetw.
     * 
     * @return
     *     possible object is
     *     {@link ANETW }
     *     
     */
    public ANETW getANETW() {
        return anetw;
    }

    /**
     * Define el valor de la propiedad anetw.
     * 
     * @param value
     *     allowed object is
     *     {@link ANETW }
     *     
     */
    public void setANETW(ANETW value) {
        this.anetw = value;
    }

    /**
     * Obtiene el valor de la propiedad skfbp.
     * 
     * @return
     *     possible object is
     *     {@link SKFBP }
     *     
     */
    public SKFBP getSKFBP() {
        return skfbp;
    }

    /**
     * Define el valor de la propiedad skfbp.
     * 
     * @param value
     *     allowed object is
     *     {@link SKFBP }
     *     
     */
    public void setSKFBP(SKFBP value) {
        this.skfbp = value;
    }

    /**
     * Obtiene el valor de la propiedad curcy.
     * 
     * @return
     *     possible object is
     *     {@link CURCY }
     *     
     */
    public CURCY getCURCY() {
        return curcy;
    }

    /**
     * Define el valor de la propiedad curcy.
     * 
     * @param value
     *     allowed object is
     *     {@link CURCY }
     *     
     */
    public void setCURCY(CURCY value) {
        this.curcy = value;
    }

    /**
     * Obtiene el valor de la propiedad preis.
     * 
     * @return
     *     possible object is
     *     {@link PREIS }
     *     
     */
    public PREIS getPREIS() {
        return preis;
    }

    /**
     * Define el valor de la propiedad preis.
     * 
     * @param value
     *     allowed object is
     *     {@link PREIS }
     *     
     */
    public void setPREIS(PREIS value) {
        this.preis = value;
    }

    /**
     * Obtiene el valor de la propiedad action.
     * 
     * @return
     *     possible object is
     *     {@link ACTION }
     *     
     */
    public ACTION getACTION() {
        return action;
    }

    /**
     * Define el valor de la propiedad action.
     * 
     * @param value
     *     allowed object is
     *     {@link ACTION }
     *     
     */
    public void setACTION(ACTION value) {
        this.action = value;
    }

    /**
     * Obtiene el valor de la propiedad kzabs.
     * 
     * @return
     *     possible object is
     *     {@link KZABS }
     *     
     */
    public KZABS getKZABS() {
        return kzabs;
    }

    /**
     * Define el valor de la propiedad kzabs.
     * 
     * @param value
     *     allowed object is
     *     {@link KZABS }
     *     
     */
    public void setKZABS(KZABS value) {
        this.kzabs = value;
    }

    /**
     * Obtiene el valor de la propiedad uebto.
     * 
     * @return
     *     possible object is
     *     {@link UEBTO }
     *     
     */
    public UEBTO getUEBTO() {
        return uebto;
    }

    /**
     * Define el valor de la propiedad uebto.
     * 
     * @param value
     *     allowed object is
     *     {@link UEBTO }
     *     
     */
    public void setUEBTO(UEBTO value) {
        this.uebto = value;
    }

    /**
     * Obtiene el valor de la propiedad uebtk.
     * 
     * @return
     *     possible object is
     *     {@link UEBTK }
     *     
     */
    public UEBTK getUEBTK() {
        return uebtk;
    }

    /**
     * Define el valor de la propiedad uebtk.
     * 
     * @param value
     *     allowed object is
     *     {@link UEBTK }
     *     
     */
    public void setUEBTK(UEBTK value) {
        this.uebtk = value;
    }

    /**
     * Obtiene el valor de la propiedad lbnum.
     * 
     * @return
     *     possible object is
     *     {@link LBNUM }
     *     
     */
    public LBNUM getLBNUM() {
        return lbnum;
    }

    /**
     * Define el valor de la propiedad lbnum.
     * 
     * @param value
     *     allowed object is
     *     {@link LBNUM }
     *     
     */
    public void setLBNUM(LBNUM value) {
        this.lbnum = value;
    }

    /**
     * Obtiene el valor de la propiedad ausgb.
     * 
     * @return
     *     possible object is
     *     {@link AUSGB }
     *     
     */
    public AUSGB getAUSGB() {
        return ausgb;
    }

    /**
     * Define el valor de la propiedad ausgb.
     * 
     * @param value
     *     allowed object is
     *     {@link AUSGB }
     *     
     */
    public void setAUSGB(AUSGB value) {
        this.ausgb = value;
    }

    /**
     * Obtiene el valor de la propiedad frpos.
     * 
     * @return
     *     possible object is
     *     {@link FRPOS }
     *     
     */
    public FRPOS getFRPOS() {
        return frpos;
    }

    /**
     * Define el valor de la propiedad frpos.
     * 
     * @param value
     *     allowed object is
     *     {@link FRPOS }
     *     
     */
    public void setFRPOS(FRPOS value) {
        this.frpos = value;
    }

    /**
     * Obtiene el valor de la propiedad topos.
     * 
     * @return
     *     possible object is
     *     {@link TOPOS }
     *     
     */
    public TOPOS getTOPOS() {
        return topos;
    }

    /**
     * Define el valor de la propiedad topos.
     * 
     * @param value
     *     allowed object is
     *     {@link TOPOS }
     *     
     */
    public void setTOPOS(TOPOS value) {
        this.topos = value;
    }

    /**
     * Obtiene el valor de la propiedad ktxt1.
     * 
     * @return
     *     possible object is
     *     {@link KTXT1 }
     *     
     */
    public KTXT1 getKTXT1() {
        return ktxt1;
    }

    /**
     * Define el valor de la propiedad ktxt1.
     * 
     * @param value
     *     allowed object is
     *     {@link KTXT1 }
     *     
     */
    public void setKTXT1(KTXT1 value) {
        this.ktxt1 = value;
    }

    /**
     * Obtiene el valor de la propiedad ktxt2.
     * 
     * @return
     *     possible object is
     *     {@link KTXT2 }
     *     
     */
    public KTXT2 getKTXT2() {
        return ktxt2;
    }

    /**
     * Define el valor de la propiedad ktxt2.
     * 
     * @param value
     *     allowed object is
     *     {@link KTXT2 }
     *     
     */
    public void setKTXT2(KTXT2 value) {
        this.ktxt2 = value;
    }

    /**
     * Obtiene el valor de la propiedad pernr.
     * 
     * @return
     *     possible object is
     *     {@link PERNR }
     *     
     */
    public PERNR getPERNR() {
        return pernr;
    }

    /**
     * Define el valor de la propiedad pernr.
     * 
     * @param value
     *     allowed object is
     *     {@link PERNR }
     *     
     */
    public void setPERNR(PERNR value) {
        this.pernr = value;
    }

    /**
     * Obtiene el valor de la propiedad lgart.
     * 
     * @return
     *     possible object is
     *     {@link LGART }
     *     
     */
    public LGART getLGART() {
        return lgart;
    }

    /**
     * Define el valor de la propiedad lgart.
     * 
     * @param value
     *     allowed object is
     *     {@link LGART }
     *     
     */
    public void setLGART(LGART value) {
        this.lgart = value;
    }

    /**
     * Obtiene el valor de la propiedad stell.
     * 
     * @return
     *     possible object is
     *     {@link STELL }
     *     
     */
    public STELL getSTELL() {
        return stell;
    }

    /**
     * Define el valor de la propiedad stell.
     * 
     * @param value
     *     allowed object is
     *     {@link STELL }
     *     
     */
    public void setSTELL(STELL value) {
        this.stell = value;
    }

    /**
     * Obtiene el valor de la propiedad zwert.
     * 
     * @return
     *     possible object is
     *     {@link ZWERT }
     *     
     */
    public ZWERT getZWERT() {
        return zwert;
    }

    /**
     * Define el valor de la propiedad zwert.
     * 
     * @param value
     *     allowed object is
     *     {@link ZWERT }
     *     
     */
    public void setZWERT(ZWERT value) {
        this.zwert = value;
    }

    /**
     * Gets the value of the e1EDC02 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC02 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC02().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC02 }
     * 
     * 
     */
    public List<E1EDC02> getE1EDC02() {
        if (e1EDC02 == null) {
            e1EDC02 = new ArrayList<E1EDC02>();
        }
        return this.e1EDC02;
    }

    /**
     * Gets the value of the e1EDC03 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC03 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC03().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC03 }
     * 
     * 
     */
    public List<E1EDC03> getE1EDC03() {
        if (e1EDC03 == null) {
            e1EDC03 = new ArrayList<E1EDC03>();
        }
        return this.e1EDC03;
    }

    /**
     * Gets the value of the e1EDC04 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC04 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC04().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC04 }
     * 
     * 
     */
    public List<E1EDC04> getE1EDC04() {
        if (e1EDC04 == null) {
            e1EDC04 = new ArrayList<E1EDC04>();
        }
        return this.e1EDC04;
    }

    /**
     * Gets the value of the e1EDC05 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC05 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC05().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC05 }
     * 
     * 
     */
    public List<E1EDC05> getE1EDC05() {
        if (e1EDC05 == null) {
            e1EDC05 = new ArrayList<E1EDC05>();
        }
        return this.e1EDC05;
    }

    /**
     * Obtiene el valor de la propiedad e1EDC06.
     * 
     * @return
     *     possible object is
     *     {@link E1EDC06 }
     *     
     */
    public E1EDC06 getE1EDC06() {
        return e1EDC06;
    }

    /**
     * Define el valor de la propiedad e1EDC06.
     * 
     * @param value
     *     allowed object is
     *     {@link E1EDC06 }
     *     
     */
    public void setE1EDC06(E1EDC06 value) {
        this.e1EDC06 = value;
    }

    /**
     * Obtiene el valor de la propiedad e1EDC07.
     * 
     * @return
     *     possible object is
     *     {@link E1EDC07 }
     *     
     */
    public E1EDC07 getE1EDC07() {
        return e1EDC07;
    }

    /**
     * Define el valor de la propiedad e1EDC07.
     * 
     * @param value
     *     allowed object is
     *     {@link E1EDC07 }
     *     
     */
    public void setE1EDC07(E1EDC07 value) {
        this.e1EDC07 = value;
    }

    /**
     * Gets the value of the e1EDCA1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDCA1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDCA1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDCA1 }
     * 
     * 
     */
    public List<E1EDCA1> getE1EDCA1() {
        if (e1EDCA1 == null) {
            e1EDCA1 = new ArrayList<E1EDCA1>();
        }
        return this.e1EDCA1;
    }

    /**
     * Gets the value of the e1EDC19 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC19 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC19().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC19 }
     * 
     * 
     */
    public List<E1EDC19> getE1EDC19() {
        if (e1EDC19 == null) {
            e1EDC19 = new ArrayList<E1EDC19>();
        }
        return this.e1EDC19;
    }

    /**
     * Gets the value of the e1EDC17 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC17 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC17().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC17 }
     * 
     * 
     */
    public List<E1EDC17> getE1EDC17() {
        if (e1EDC17 == null) {
            e1EDC17 = new ArrayList<E1EDC17>();
        }
        return this.e1EDC17;
    }

    /**
     * Gets the value of the e1EDC18 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC18 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC18().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC18 }
     * 
     * 
     */
    public List<E1EDC18> getE1EDC18() {
        if (e1EDC18 == null) {
            e1EDC18 = new ArrayList<E1EDC18>();
        }
        return this.e1EDC18;
    }

    /**
     * Gets the value of the e1EDCT1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDCT1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDCT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDCT1 }
     * 
     * 
     */
    public List<E1EDCT1> getE1EDCT1() {
        if (e1EDCT1 == null) {
            e1EDCT1 = new ArrayList<E1EDCT1>();
        }
        return this.e1EDCT1;
    }

    /**
     * Obtiene el valor de la propiedad segment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT() {
        return segment;
    }

    /**
     * Define el valor de la propiedad segment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT(String value) {
        this.segment = value;
    }

}

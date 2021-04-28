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
 *         &lt;element ref="{}POSEX" minOccurs="0"/&gt;
 *         &lt;element ref="{}ACTION" minOccurs="0"/&gt;
 *         &lt;element ref="{}PSTYP" minOccurs="0"/&gt;
 *         &lt;element ref="{}KZABS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}MENEE" minOccurs="0"/&gt;
 *         &lt;element ref="{}BMNG2" minOccurs="0"/&gt;
 *         &lt;element ref="{}PMENE" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABFTZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}VPREI" minOccurs="0"/&gt;
 *         &lt;element ref="{}PEINH" minOccurs="0"/&gt;
 *         &lt;element ref="{}NETWR" minOccurs="0"/&gt;
 *         &lt;element ref="{}ANETW" minOccurs="0"/&gt;
 *         &lt;element ref="{}SKFBP" minOccurs="0"/&gt;
 *         &lt;element ref="{}NTGEW" minOccurs="0"/&gt;
 *         &lt;element ref="{}GEWEI" minOccurs="0"/&gt;
 *         &lt;element ref="{}EINKZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}CURCY" minOccurs="0"/&gt;
 *         &lt;element ref="{}PREIS" minOccurs="0"/&gt;
 *         &lt;element ref="{}MATKL" minOccurs="0"/&gt;
 *         &lt;element ref="{}UEPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}GRKOR" minOccurs="0"/&gt;
 *         &lt;element ref="{}EVERS" minOccurs="0"/&gt;
 *         &lt;element ref="{}BPUMN" minOccurs="0"/&gt;
 *         &lt;element ref="{}BPUMZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABGRU" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABGRT" minOccurs="0"/&gt;
 *         &lt;element ref="{}ANTLF" minOccurs="0"/&gt;
 *         &lt;element ref="{}FIXMG" minOccurs="0"/&gt;
 *         &lt;element ref="{}KZAZU" minOccurs="0"/&gt;
 *         &lt;element ref="{}BRGEW" minOccurs="0"/&gt;
 *         &lt;element ref="{}PSTYV" minOccurs="0"/&gt;
 *         &lt;element ref="{}EMPST" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABTNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABRVW" minOccurs="0"/&gt;
 *         &lt;element ref="{}WERKS" minOccurs="0"/&gt;
 *         &lt;element ref="{}LPRIO" minOccurs="0"/&gt;
 *         &lt;element ref="{}LPRIO_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}ROUTE" minOccurs="0"/&gt;
 *         &lt;element ref="{}ROUTE_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}LGORT" minOccurs="0"/&gt;
 *         &lt;element ref="{}VSTEL" minOccurs="0"/&gt;
 *         &lt;element ref="{}DELCO" minOccurs="0"/&gt;
 *         &lt;element ref="{}MATNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}VALTG" minOccurs="0"/&gt;
 *         &lt;element ref="{}HIPOS" minOccurs="0"/&gt;
 *         &lt;element ref="{}HIEVW" minOccurs="0"/&gt;
 *         &lt;element ref="{}POSGUID" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP01" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP02" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP03" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDP09" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP02" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1CUREF" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1ADDI1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP03" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP04" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP05" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP20" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDPA1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP19" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDPAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP17" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP18" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDP35" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDPT1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDC01" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "posex",
    "action",
    "pstyp",
    "kzabs",
    "menge",
    "menee",
    "bmng2",
    "pmene",
    "abftz",
    "vprei",
    "peinh",
    "netwr",
    "anetw",
    "skfbp",
    "ntgew",
    "gewei",
    "einkz",
    "curcy",
    "preis",
    "matkl",
    "uepos",
    "grkor",
    "evers",
    "bpumn",
    "bpumz",
    "abgru",
    "abgrt",
    "antlf",
    "fixmg",
    "kzazu",
    "brgew",
    "pstyv",
    "empst",
    "abtnr",
    "abrvw",
    "werks",
    "lprio",
    "lpriobez",
    "route",
    "routebez",
    "lgort",
    "vstel",
    "delco",
    "matnr",
    "valtg",
    "hipos",
    "hievw",
    "posguid",
    "z1EDP01",
    "z1EDP02",
    "z1EDP03",
    "z1EDP09",
    "e1EDP02",
    "e1CUREF",
    "e1ADDI1",
    "e1EDP03",
    "e1EDP04",
    "e1EDP05",
    "e1EDP20",
    "e1EDPA1",
    "e1EDP19",
    "e1EDPAD",
    "e1EDP17",
    "e1EDP18",
    "e1EDP35",
    "e1EDPT1",
    "e1EDC01"
})
@XmlRootElement(name = "E1EDP01")
public class E1EDP01 {

    @XmlElement(name = "POSEX")
    protected POSEX posex;
    @XmlElement(name = "ACTION")
    protected ACTION action;
    @XmlElement(name = "PSTYP")
    protected PSTYP pstyp;
    @XmlElement(name = "KZABS")
    protected KZABS kzabs;
    @XmlElement(name = "MENGE")
    protected MENGE menge;
    @XmlElement(name = "MENEE")
    protected MENEE menee;
    @XmlElement(name = "BMNG2")
    protected BMNG2 bmng2;
    @XmlElement(name = "PMENE")
    protected PMENE pmene;
    @XmlElement(name = "ABFTZ")
    protected ABFTZ abftz;
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
    @XmlElement(name = "NTGEW")
    protected NTGEW ntgew;
    @XmlElement(name = "GEWEI")
    protected GEWEI gewei;
    @XmlElement(name = "EINKZ")
    protected EINKZ einkz;
    @XmlElement(name = "CURCY")
    protected CURCY curcy;
    @XmlElement(name = "PREIS")
    protected PREIS preis;
    @XmlElement(name = "MATKL")
    protected MATKL matkl;
    @XmlElement(name = "UEPOS")
    protected UEPOS uepos;
    @XmlElement(name = "GRKOR")
    protected GRKOR grkor;
    @XmlElement(name = "EVERS")
    protected EVERS evers;
    @XmlElement(name = "BPUMN")
    protected BPUMN bpumn;
    @XmlElement(name = "BPUMZ")
    protected BPUMZ bpumz;
    @XmlElement(name = "ABGRU")
    protected ABGRU abgru;
    @XmlElement(name = "ABGRT")
    protected ABGRT abgrt;
    @XmlElement(name = "ANTLF")
    protected ANTLF antlf;
    @XmlElement(name = "FIXMG")
    protected FIXMG fixmg;
    @XmlElement(name = "KZAZU")
    protected KZAZU kzazu;
    @XmlElement(name = "BRGEW")
    protected BRGEW brgew;
    @XmlElement(name = "PSTYV")
    protected PSTYV pstyv;
    @XmlElement(name = "EMPST")
    protected EMPST empst;
    @XmlElement(name = "ABTNR")
    protected ABTNR abtnr;
    @XmlElement(name = "ABRVW")
    protected ABRVW abrvw;
    @XmlElement(name = "WERKS")
    protected WERKS werks;
    @XmlElement(name = "LPRIO")
    protected LPRIO lprio;
    @XmlElement(name = "LPRIO_BEZ")
    protected LPRIOBEZ lpriobez;
    @XmlElement(name = "ROUTE")
    protected ROUTE route;
    @XmlElement(name = "ROUTE_BEZ")
    protected ROUTEBEZ routebez;
    @XmlElement(name = "LGORT")
    protected LGORT lgort;
    @XmlElement(name = "VSTEL")
    protected VSTEL vstel;
    @XmlElement(name = "DELCO")
    protected DELCO delco;
    @XmlElement(name = "MATNR")
    protected MATNR matnr;
    @XmlElement(name = "VALTG")
    protected VALTG valtg;
    @XmlElement(name = "HIPOS")
    protected HIPOS hipos;
    @XmlElement(name = "HIEVW")
    protected HIEVW hievw;
    @XmlElement(name = "POSGUID")
    protected POSGUID posguid;
    @XmlElement(name = "Z1EDP01")
    protected List<Z1EDP01> z1EDP01;
    @XmlElement(name = "Z1EDP02")
    protected Z1EDP02 z1EDP02;
    @XmlElement(name = "Z1EDP03")
    protected Z1EDP03 z1EDP03;
    @XmlElement(name = "Z1EDP09")
    protected Z1EDP09 z1EDP09;
    @XmlElement(name = "E1EDP02")
    protected List<E1EDP02> e1EDP02;
    @XmlElement(name = "E1CUREF")
    protected E1CUREF e1CUREF;
    @XmlElement(name = "E1ADDI1")
    protected List<E1ADDI1> e1ADDI1;
    @XmlElement(name = "E1EDP03")
    protected List<E1EDP03> e1EDP03;
    @XmlElement(name = "E1EDP04")
    protected List<E1EDP04> e1EDP04;
    @XmlElement(name = "E1EDP05")
    protected List<E1EDP05> e1EDP05;
    @XmlElement(name = "E1EDP20")
    protected List<E1EDP20> e1EDP20;
    @XmlElement(name = "E1EDPA1")
    protected List<E1EDPA1> e1EDPA1;
    @XmlElement(name = "E1EDP19")
    protected List<E1EDP19> e1EDP19;
    @XmlElement(name = "E1EDPAD")
    protected E1EDPAD e1EDPAD;
    @XmlElement(name = "E1EDP17")
    protected List<E1EDP17> e1EDP17;
    @XmlElement(name = "E1EDP18")
    protected List<E1EDP18> e1EDP18;
    @XmlElement(name = "E1EDP35")
    protected List<E1EDP35> e1EDP35;
    @XmlElement(name = "E1EDPT1")
    protected List<E1EDPT1> e1EDPT1;
    @XmlElement(name = "E1EDC01")
    protected List<E1EDC01> e1EDC01;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

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
     * Obtiene el valor de la propiedad pstyp.
     * 
     * @return
     *     possible object is
     *     {@link PSTYP }
     *     
     */
    public PSTYP getPSTYP() {
        return pstyp;
    }

    /**
     * Define el valor de la propiedad pstyp.
     * 
     * @param value
     *     allowed object is
     *     {@link PSTYP }
     *     
     */
    public void setPSTYP(PSTYP value) {
        this.pstyp = value;
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
     * Obtiene el valor de la propiedad abftz.
     * 
     * @return
     *     possible object is
     *     {@link ABFTZ }
     *     
     */
    public ABFTZ getABFTZ() {
        return abftz;
    }

    /**
     * Define el valor de la propiedad abftz.
     * 
     * @param value
     *     allowed object is
     *     {@link ABFTZ }
     *     
     */
    public void setABFTZ(ABFTZ value) {
        this.abftz = value;
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
     * Obtiene el valor de la propiedad ntgew.
     * 
     * @return
     *     possible object is
     *     {@link NTGEW }
     *     
     */
    public NTGEW getNTGEW() {
        return ntgew;
    }

    /**
     * Define el valor de la propiedad ntgew.
     * 
     * @param value
     *     allowed object is
     *     {@link NTGEW }
     *     
     */
    public void setNTGEW(NTGEW value) {
        this.ntgew = value;
    }

    /**
     * Obtiene el valor de la propiedad gewei.
     * 
     * @return
     *     possible object is
     *     {@link GEWEI }
     *     
     */
    public GEWEI getGEWEI() {
        return gewei;
    }

    /**
     * Define el valor de la propiedad gewei.
     * 
     * @param value
     *     allowed object is
     *     {@link GEWEI }
     *     
     */
    public void setGEWEI(GEWEI value) {
        this.gewei = value;
    }

    /**
     * Obtiene el valor de la propiedad einkz.
     * 
     * @return
     *     possible object is
     *     {@link EINKZ }
     *     
     */
    public EINKZ getEINKZ() {
        return einkz;
    }

    /**
     * Define el valor de la propiedad einkz.
     * 
     * @param value
     *     allowed object is
     *     {@link EINKZ }
     *     
     */
    public void setEINKZ(EINKZ value) {
        this.einkz = value;
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
     * Obtiene el valor de la propiedad grkor.
     * 
     * @return
     *     possible object is
     *     {@link GRKOR }
     *     
     */
    public GRKOR getGRKOR() {
        return grkor;
    }

    /**
     * Define el valor de la propiedad grkor.
     * 
     * @param value
     *     allowed object is
     *     {@link GRKOR }
     *     
     */
    public void setGRKOR(GRKOR value) {
        this.grkor = value;
    }

    /**
     * Obtiene el valor de la propiedad evers.
     * 
     * @return
     *     possible object is
     *     {@link EVERS }
     *     
     */
    public EVERS getEVERS() {
        return evers;
    }

    /**
     * Define el valor de la propiedad evers.
     * 
     * @param value
     *     allowed object is
     *     {@link EVERS }
     *     
     */
    public void setEVERS(EVERS value) {
        this.evers = value;
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
     * Obtiene el valor de la propiedad abgru.
     * 
     * @return
     *     possible object is
     *     {@link ABGRU }
     *     
     */
    public ABGRU getABGRU() {
        return abgru;
    }

    /**
     * Define el valor de la propiedad abgru.
     * 
     * @param value
     *     allowed object is
     *     {@link ABGRU }
     *     
     */
    public void setABGRU(ABGRU value) {
        this.abgru = value;
    }

    /**
     * Obtiene el valor de la propiedad abgrt.
     * 
     * @return
     *     possible object is
     *     {@link ABGRT }
     *     
     */
    public ABGRT getABGRT() {
        return abgrt;
    }

    /**
     * Define el valor de la propiedad abgrt.
     * 
     * @param value
     *     allowed object is
     *     {@link ABGRT }
     *     
     */
    public void setABGRT(ABGRT value) {
        this.abgrt = value;
    }

    /**
     * Obtiene el valor de la propiedad antlf.
     * 
     * @return
     *     possible object is
     *     {@link ANTLF }
     *     
     */
    public ANTLF getANTLF() {
        return antlf;
    }

    /**
     * Define el valor de la propiedad antlf.
     * 
     * @param value
     *     allowed object is
     *     {@link ANTLF }
     *     
     */
    public void setANTLF(ANTLF value) {
        this.antlf = value;
    }

    /**
     * Obtiene el valor de la propiedad fixmg.
     * 
     * @return
     *     possible object is
     *     {@link FIXMG }
     *     
     */
    public FIXMG getFIXMG() {
        return fixmg;
    }

    /**
     * Define el valor de la propiedad fixmg.
     * 
     * @param value
     *     allowed object is
     *     {@link FIXMG }
     *     
     */
    public void setFIXMG(FIXMG value) {
        this.fixmg = value;
    }

    /**
     * Obtiene el valor de la propiedad kzazu.
     * 
     * @return
     *     possible object is
     *     {@link KZAZU }
     *     
     */
    public KZAZU getKZAZU() {
        return kzazu;
    }

    /**
     * Define el valor de la propiedad kzazu.
     * 
     * @param value
     *     allowed object is
     *     {@link KZAZU }
     *     
     */
    public void setKZAZU(KZAZU value) {
        this.kzazu = value;
    }

    /**
     * Obtiene el valor de la propiedad brgew.
     * 
     * @return
     *     possible object is
     *     {@link BRGEW }
     *     
     */
    public BRGEW getBRGEW() {
        return brgew;
    }

    /**
     * Define el valor de la propiedad brgew.
     * 
     * @param value
     *     allowed object is
     *     {@link BRGEW }
     *     
     */
    public void setBRGEW(BRGEW value) {
        this.brgew = value;
    }

    /**
     * Obtiene el valor de la propiedad pstyv.
     * 
     * @return
     *     possible object is
     *     {@link PSTYV }
     *     
     */
    public PSTYV getPSTYV() {
        return pstyv;
    }

    /**
     * Define el valor de la propiedad pstyv.
     * 
     * @param value
     *     allowed object is
     *     {@link PSTYV }
     *     
     */
    public void setPSTYV(PSTYV value) {
        this.pstyv = value;
    }

    /**
     * Obtiene el valor de la propiedad empst.
     * 
     * @return
     *     possible object is
     *     {@link EMPST }
     *     
     */
    public EMPST getEMPST() {
        return empst;
    }

    /**
     * Define el valor de la propiedad empst.
     * 
     * @param value
     *     allowed object is
     *     {@link EMPST }
     *     
     */
    public void setEMPST(EMPST value) {
        this.empst = value;
    }

    /**
     * Obtiene el valor de la propiedad abtnr.
     * 
     * @return
     *     possible object is
     *     {@link ABTNR }
     *     
     */
    public ABTNR getABTNR() {
        return abtnr;
    }

    /**
     * Define el valor de la propiedad abtnr.
     * 
     * @param value
     *     allowed object is
     *     {@link ABTNR }
     *     
     */
    public void setABTNR(ABTNR value) {
        this.abtnr = value;
    }

    /**
     * Obtiene el valor de la propiedad abrvw.
     * 
     * @return
     *     possible object is
     *     {@link ABRVW }
     *     
     */
    public ABRVW getABRVW() {
        return abrvw;
    }

    /**
     * Define el valor de la propiedad abrvw.
     * 
     * @param value
     *     allowed object is
     *     {@link ABRVW }
     *     
     */
    public void setABRVW(ABRVW value) {
        this.abrvw = value;
    }

    /**
     * Obtiene el valor de la propiedad werks.
     * 
     * @return
     *     possible object is
     *     {@link WERKS }
     *     
     */
    public WERKS getWERKS() {
        return werks;
    }

    /**
     * Define el valor de la propiedad werks.
     * 
     * @param value
     *     allowed object is
     *     {@link WERKS }
     *     
     */
    public void setWERKS(WERKS value) {
        this.werks = value;
    }

    /**
     * Obtiene el valor de la propiedad lprio.
     * 
     * @return
     *     possible object is
     *     {@link LPRIO }
     *     
     */
    public LPRIO getLPRIO() {
        return lprio;
    }

    /**
     * Define el valor de la propiedad lprio.
     * 
     * @param value
     *     allowed object is
     *     {@link LPRIO }
     *     
     */
    public void setLPRIO(LPRIO value) {
        this.lprio = value;
    }

    /**
     * Obtiene el valor de la propiedad lpriobez.
     * 
     * @return
     *     possible object is
     *     {@link LPRIOBEZ }
     *     
     */
    public LPRIOBEZ getLPRIOBEZ() {
        return lpriobez;
    }

    /**
     * Define el valor de la propiedad lpriobez.
     * 
     * @param value
     *     allowed object is
     *     {@link LPRIOBEZ }
     *     
     */
    public void setLPRIOBEZ(LPRIOBEZ value) {
        this.lpriobez = value;
    }

    /**
     * Obtiene el valor de la propiedad route.
     * 
     * @return
     *     possible object is
     *     {@link ROUTE }
     *     
     */
    public ROUTE getROUTE() {
        return route;
    }

    /**
     * Define el valor de la propiedad route.
     * 
     * @param value
     *     allowed object is
     *     {@link ROUTE }
     *     
     */
    public void setROUTE(ROUTE value) {
        this.route = value;
    }

    /**
     * Obtiene el valor de la propiedad routebez.
     * 
     * @return
     *     possible object is
     *     {@link ROUTEBEZ }
     *     
     */
    public ROUTEBEZ getROUTEBEZ() {
        return routebez;
    }

    /**
     * Define el valor de la propiedad routebez.
     * 
     * @param value
     *     allowed object is
     *     {@link ROUTEBEZ }
     *     
     */
    public void setROUTEBEZ(ROUTEBEZ value) {
        this.routebez = value;
    }

    /**
     * Obtiene el valor de la propiedad lgort.
     * 
     * @return
     *     possible object is
     *     {@link LGORT }
     *     
     */
    public LGORT getLGORT() {
        return lgort;
    }

    /**
     * Define el valor de la propiedad lgort.
     * 
     * @param value
     *     allowed object is
     *     {@link LGORT }
     *     
     */
    public void setLGORT(LGORT value) {
        this.lgort = value;
    }

    /**
     * Obtiene el valor de la propiedad vstel.
     * 
     * @return
     *     possible object is
     *     {@link VSTEL }
     *     
     */
    public VSTEL getVSTEL() {
        return vstel;
    }

    /**
     * Define el valor de la propiedad vstel.
     * 
     * @param value
     *     allowed object is
     *     {@link VSTEL }
     *     
     */
    public void setVSTEL(VSTEL value) {
        this.vstel = value;
    }

    /**
     * Obtiene el valor de la propiedad delco.
     * 
     * @return
     *     possible object is
     *     {@link DELCO }
     *     
     */
    public DELCO getDELCO() {
        return delco;
    }

    /**
     * Define el valor de la propiedad delco.
     * 
     * @param value
     *     allowed object is
     *     {@link DELCO }
     *     
     */
    public void setDELCO(DELCO value) {
        this.delco = value;
    }

    /**
     * Obtiene el valor de la propiedad matnr.
     * 
     * @return
     *     possible object is
     *     {@link MATNR }
     *     
     */
    public MATNR getMATNR() {
        return matnr;
    }

    /**
     * Define el valor de la propiedad matnr.
     * 
     * @param value
     *     allowed object is
     *     {@link MATNR }
     *     
     */
    public void setMATNR(MATNR value) {
        this.matnr = value;
    }

    /**
     * Obtiene el valor de la propiedad valtg.
     * 
     * @return
     *     possible object is
     *     {@link VALTG }
     *     
     */
    public VALTG getVALTG() {
        return valtg;
    }

    /**
     * Define el valor de la propiedad valtg.
     * 
     * @param value
     *     allowed object is
     *     {@link VALTG }
     *     
     */
    public void setVALTG(VALTG value) {
        this.valtg = value;
    }

    /**
     * Obtiene el valor de la propiedad hipos.
     * 
     * @return
     *     possible object is
     *     {@link HIPOS }
     *     
     */
    public HIPOS getHIPOS() {
        return hipos;
    }

    /**
     * Define el valor de la propiedad hipos.
     * 
     * @param value
     *     allowed object is
     *     {@link HIPOS }
     *     
     */
    public void setHIPOS(HIPOS value) {
        this.hipos = value;
    }

    /**
     * Obtiene el valor de la propiedad hievw.
     * 
     * @return
     *     possible object is
     *     {@link HIEVW }
     *     
     */
    public HIEVW getHIEVW() {
        return hievw;
    }

    /**
     * Define el valor de la propiedad hievw.
     * 
     * @param value
     *     allowed object is
     *     {@link HIEVW }
     *     
     */
    public void setHIEVW(HIEVW value) {
        this.hievw = value;
    }

    /**
     * Obtiene el valor de la propiedad posguid.
     * 
     * @return
     *     possible object is
     *     {@link POSGUID }
     *     
     */
    public POSGUID getPOSGUID() {
        return posguid;
    }

    /**
     * Define el valor de la propiedad posguid.
     * 
     * @param value
     *     allowed object is
     *     {@link POSGUID }
     *     
     */
    public void setPOSGUID(POSGUID value) {
        this.posguid = value;
    }

    /**
     * Gets the value of the z1EDP01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDP01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDP01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDP01 }
     * 
     * 
     */
    public List<Z1EDP01> getZ1EDP01() {
        if (z1EDP01 == null) {
            z1EDP01 = new ArrayList<Z1EDP01>();
        }
        return this.z1EDP01;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP02.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP02 }
     *     
     */
    public Z1EDP02 getZ1EDP02() {
        return z1EDP02;
    }

    /**
     * Define el valor de la propiedad z1EDP02.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP02 }
     *     
     */
    public void setZ1EDP02(Z1EDP02 value) {
        this.z1EDP02 = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP03.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP03 }
     *     
     */
    public Z1EDP03 getZ1EDP03() {
        return z1EDP03;
    }

    /**
     * Define el valor de la propiedad z1EDP03.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP03 }
     *     
     */
    public void setZ1EDP03(Z1EDP03 value) {
        this.z1EDP03 = value;
    }

    /**
     * Obtiene el valor de la propiedad z1EDP09.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDP09 }
     *     
     */
    public Z1EDP09 getZ1EDP09() {
        return z1EDP09;
    }

    /**
     * Define el valor de la propiedad z1EDP09.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDP09 }
     *     
     */
    public void setZ1EDP09(Z1EDP09 value) {
        this.z1EDP09 = value;
    }

    /**
     * Gets the value of the e1EDP02 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP02 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP02().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP02 }
     * 
     * 
     */
    public List<E1EDP02> getE1EDP02() {
        if (e1EDP02 == null) {
            e1EDP02 = new ArrayList<E1EDP02>();
        }
        return this.e1EDP02;
    }

    /**
     * Obtiene el valor de la propiedad e1CUREF.
     * 
     * @return
     *     possible object is
     *     {@link E1CUREF }
     *     
     */
    public E1CUREF getE1CUREF() {
        return e1CUREF;
    }

    /**
     * Define el valor de la propiedad e1CUREF.
     * 
     * @param value
     *     allowed object is
     *     {@link E1CUREF }
     *     
     */
    public void setE1CUREF(E1CUREF value) {
        this.e1CUREF = value;
    }

    /**
     * Gets the value of the e1ADDI1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1ADDI1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1ADDI1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1ADDI1 }
     * 
     * 
     */
    public List<E1ADDI1> getE1ADDI1() {
        if (e1ADDI1 == null) {
            e1ADDI1 = new ArrayList<E1ADDI1>();
        }
        return this.e1ADDI1;
    }

    /**
     * Gets the value of the e1EDP03 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP03 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP03().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP03 }
     * 
     * 
     */
    public List<E1EDP03> getE1EDP03() {
        if (e1EDP03 == null) {
            e1EDP03 = new ArrayList<E1EDP03>();
        }
        return this.e1EDP03;
    }

    /**
     * Gets the value of the e1EDP04 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP04 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP04().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP04 }
     * 
     * 
     */
    public List<E1EDP04> getE1EDP04() {
        if (e1EDP04 == null) {
            e1EDP04 = new ArrayList<E1EDP04>();
        }
        return this.e1EDP04;
    }

    /**
     * Gets the value of the e1EDP05 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP05 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP05().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP05 }
     * 
     * 
     */
    public List<E1EDP05> getE1EDP05() {
        if (e1EDP05 == null) {
            e1EDP05 = new ArrayList<E1EDP05>();
        }
        return this.e1EDP05;
    }

    /**
     * Gets the value of the e1EDP20 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP20 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP20().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP20 }
     * 
     * 
     */
    public List<E1EDP20> getE1EDP20() {
        if (e1EDP20 == null) {
            e1EDP20 = new ArrayList<E1EDP20>();
        }
        return this.e1EDP20;
    }

    /**
     * Gets the value of the e1EDPA1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDPA1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDPA1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDPA1 }
     * 
     * 
     */
    public List<E1EDPA1> getE1EDPA1() {
        if (e1EDPA1 == null) {
            e1EDPA1 = new ArrayList<E1EDPA1>();
        }
        return this.e1EDPA1;
    }

    /**
     * Gets the value of the e1EDP19 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP19 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP19().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP19 }
     * 
     * 
     */
    public List<E1EDP19> getE1EDP19() {
        if (e1EDP19 == null) {
            e1EDP19 = new ArrayList<E1EDP19>();
        }
        return this.e1EDP19;
    }

    /**
     * Obtiene el valor de la propiedad e1EDPAD.
     * 
     * @return
     *     possible object is
     *     {@link E1EDPAD }
     *     
     */
    public E1EDPAD getE1EDPAD() {
        return e1EDPAD;
    }

    /**
     * Define el valor de la propiedad e1EDPAD.
     * 
     * @param value
     *     allowed object is
     *     {@link E1EDPAD }
     *     
     */
    public void setE1EDPAD(E1EDPAD value) {
        this.e1EDPAD = value;
    }

    /**
     * Gets the value of the e1EDP17 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP17 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP17().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP17 }
     * 
     * 
     */
    public List<E1EDP17> getE1EDP17() {
        if (e1EDP17 == null) {
            e1EDP17 = new ArrayList<E1EDP17>();
        }
        return this.e1EDP17;
    }

    /**
     * Gets the value of the e1EDP18 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP18 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP18().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP18 }
     * 
     * 
     */
    public List<E1EDP18> getE1EDP18() {
        if (e1EDP18 == null) {
            e1EDP18 = new ArrayList<E1EDP18>();
        }
        return this.e1EDP18;
    }

    /**
     * Gets the value of the e1EDP35 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDP35 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDP35().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDP35 }
     * 
     * 
     */
    public List<E1EDP35> getE1EDP35() {
        if (e1EDP35 == null) {
            e1EDP35 = new ArrayList<E1EDP35>();
        }
        return this.e1EDP35;
    }

    /**
     * Gets the value of the e1EDPT1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDPT1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDPT1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDPT1 }
     * 
     * 
     */
    public List<E1EDPT1> getE1EDPT1() {
        if (e1EDPT1 == null) {
            e1EDPT1 = new ArrayList<E1EDPT1>();
        }
        return this.e1EDPT1;
    }

    /**
     * Gets the value of the e1EDC01 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDC01 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDC01().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDC01 }
     * 
     * 
     */
    public List<E1EDC01> getE1EDC01() {
        if (e1EDC01 == null) {
            e1EDC01 = new ArrayList<E1EDC01>();
        }
        return this.e1EDC01;
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

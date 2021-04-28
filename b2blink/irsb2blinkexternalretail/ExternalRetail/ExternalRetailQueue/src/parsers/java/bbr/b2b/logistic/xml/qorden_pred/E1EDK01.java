//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantaci�n de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perder�n si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:44 PM CLT 
//


package bbr.b2b.logistic.xml.qorden_pred;

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
 *         &lt;element ref="{}ACTION" minOccurs="0"/&gt;
 *         &lt;element ref="{}KZABS" minOccurs="0"/&gt;
 *         &lt;element ref="{}CURCY" minOccurs="0"/&gt;
 *         &lt;element ref="{}HWAER" minOccurs="0"/&gt;
 *         &lt;element ref="{}WKURS" minOccurs="0"/&gt;
 *         &lt;element ref="{}ZTERM" minOccurs="0"/&gt;
 *         &lt;element ref="{}KUNDEUINR" minOccurs="0"/&gt;
 *         &lt;element ref="{}EIGENUINR" minOccurs="0"/&gt;
 *         &lt;element ref="{}BSART" minOccurs="0"/&gt;
 *         &lt;element ref="{}BELNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}NTGEW" minOccurs="0"/&gt;
 *         &lt;element ref="{}BRGEW" minOccurs="0"/&gt;
 *         &lt;element ref="{}GEWEI" minOccurs="0"/&gt;
 *         &lt;element ref="{}FKART_RL" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABLAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}BSTZD" minOccurs="0"/&gt;
 *         &lt;element ref="{}VSART" minOccurs="0"/&gt;
 *         &lt;element ref="{}VSART_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}RECIPNT_NO" minOccurs="0"/&gt;
 *         &lt;element ref="{}KZAZU" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUTLF" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUGRU" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUGRU_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABRVW" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABRVW_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}FKTYP" minOccurs="0"/&gt;
 *         &lt;element ref="{}LIFSK" minOccurs="0"/&gt;
 *         &lt;element ref="{}LIFSK_BEZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}EMPST" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABTNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}DELCO" minOccurs="0"/&gt;
 *         &lt;element ref="{}WKURS_M" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK02" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK04" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK05" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK08" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDK13" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "action",
    "kzabs",
    "curcy",
    "hwaer",
    "wkurs",
    "zterm",
    "kundeuinr",
    "eigenuinr",
    "bsart",
    "belnr",
    "ntgew",
    "brgew",
    "gewei",
    "fkartrl",
    "ablad",
    "bstzd",
    "vsart",
    "vsartbez",
    "recipntno",
    "kzazu",
    "autlf",
    "augru",
    "augrubez",
    "abrvw",
    "abrvwbez",
    "fktyp",
    "lifsk",
    "lifskbez",
    "empst",
    "abtnr",
    "delco",
    "wkursm",
    "z1EDK02",
    "z1EDK04",
    "z1EDK05",
    "z1EDK08",
    "z1EDK13"
})
@XmlRootElement(name = "E1EDK01")
public class E1EDK01 {

    @XmlElement(name = "ACTION")
    protected ACTION action;
    @XmlElement(name = "KZABS")
    protected KZABS kzabs;
    @XmlElement(name = "CURCY")
    protected CURCY curcy;
    @XmlElement(name = "HWAER")
    protected HWAER hwaer;
    @XmlElement(name = "WKURS")
    protected WKURS wkurs;
    @XmlElement(name = "ZTERM")
    protected ZTERM zterm;
    @XmlElement(name = "KUNDEUINR")
    protected KUNDEUINR kundeuinr;
    @XmlElement(name = "EIGENUINR")
    protected EIGENUINR eigenuinr;
    @XmlElement(name = "BSART")
    protected BSART bsart;
    @XmlElement(name = "BELNR")
    protected BELNR belnr;
    @XmlElement(name = "NTGEW")
    protected NTGEW ntgew;
    @XmlElement(name = "BRGEW")
    protected BRGEW brgew;
    @XmlElement(name = "GEWEI")
    protected GEWEI gewei;
    @XmlElement(name = "FKART_RL")
    protected FKARTRL fkartrl;
    @XmlElement(name = "ABLAD")
    protected ABLAD ablad;
    @XmlElement(name = "BSTZD")
    protected BSTZD bstzd;
    @XmlElement(name = "VSART")
    protected VSART vsart;
    @XmlElement(name = "VSART_BEZ")
    protected VSARTBEZ vsartbez;
    @XmlElement(name = "RECIPNT_NO")
    protected RECIPNTNO recipntno;
    @XmlElement(name = "KZAZU")
    protected KZAZU kzazu;
    @XmlElement(name = "AUTLF")
    protected AUTLF autlf;
    @XmlElement(name = "AUGRU")
    protected AUGRU augru;
    @XmlElement(name = "AUGRU_BEZ")
    protected AUGRUBEZ augrubez;
    @XmlElement(name = "ABRVW")
    protected ABRVW abrvw;
    @XmlElement(name = "ABRVW_BEZ")
    protected ABRVWBEZ abrvwbez;
    @XmlElement(name = "FKTYP")
    protected FKTYP fktyp;
    @XmlElement(name = "LIFSK")
    protected LIFSK lifsk;
    @XmlElement(name = "LIFSK_BEZ")
    protected LIFSKBEZ lifskbez;
    @XmlElement(name = "EMPST")
    protected EMPST empst;
    @XmlElement(name = "ABTNR")
    protected ABTNR abtnr;
    @XmlElement(name = "DELCO")
    protected DELCO delco;
    @XmlElement(name = "WKURS_M")
    protected WKURSM wkursm;
    @XmlElement(name = "Z1EDK02")
    protected List<Z1EDK02> z1EDK02;
    @XmlElement(name = "Z1EDK04")
    protected List<Z1EDK04> z1EDK04;
    @XmlElement(name = "Z1EDK05")
    protected List<Z1EDK05> z1EDK05;
    @XmlElement(name = "Z1EDK08")
    protected Z1EDK08 z1EDK08;
    @XmlElement(name = "Z1EDK13")
    protected List<Z1EDK13> z1EDK13;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

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
     * Obtiene el valor de la propiedad hwaer.
     * 
     * @return
     *     possible object is
     *     {@link HWAER }
     *     
     */
    public HWAER getHWAER() {
        return hwaer;
    }

    /**
     * Define el valor de la propiedad hwaer.
     * 
     * @param value
     *     allowed object is
     *     {@link HWAER }
     *     
     */
    public void setHWAER(HWAER value) {
        this.hwaer = value;
    }

    /**
     * Obtiene el valor de la propiedad wkurs.
     * 
     * @return
     *     possible object is
     *     {@link WKURS }
     *     
     */
    public WKURS getWKURS() {
        return wkurs;
    }

    /**
     * Define el valor de la propiedad wkurs.
     * 
     * @param value
     *     allowed object is
     *     {@link WKURS }
     *     
     */
    public void setWKURS(WKURS value) {
        this.wkurs = value;
    }

    /**
     * Obtiene el valor de la propiedad zterm.
     * 
     * @return
     *     possible object is
     *     {@link ZTERM }
     *     
     */
    public ZTERM getZTERM() {
        return zterm;
    }

    /**
     * Define el valor de la propiedad zterm.
     * 
     * @param value
     *     allowed object is
     *     {@link ZTERM }
     *     
     */
    public void setZTERM(ZTERM value) {
        this.zterm = value;
    }

    /**
     * Obtiene el valor de la propiedad kundeuinr.
     * 
     * @return
     *     possible object is
     *     {@link KUNDEUINR }
     *     
     */
    public KUNDEUINR getKUNDEUINR() {
        return kundeuinr;
    }

    /**
     * Define el valor de la propiedad kundeuinr.
     * 
     * @param value
     *     allowed object is
     *     {@link KUNDEUINR }
     *     
     */
    public void setKUNDEUINR(KUNDEUINR value) {
        this.kundeuinr = value;
    }

    /**
     * Obtiene el valor de la propiedad eigenuinr.
     * 
     * @return
     *     possible object is
     *     {@link EIGENUINR }
     *     
     */
    public EIGENUINR getEIGENUINR() {
        return eigenuinr;
    }

    /**
     * Define el valor de la propiedad eigenuinr.
     * 
     * @param value
     *     allowed object is
     *     {@link EIGENUINR }
     *     
     */
    public void setEIGENUINR(EIGENUINR value) {
        this.eigenuinr = value;
    }

    /**
     * Obtiene el valor de la propiedad bsart.
     * 
     * @return
     *     possible object is
     *     {@link BSART }
     *     
     */
    public BSART getBSART() {
        return bsart;
    }

    /**
     * Define el valor de la propiedad bsart.
     * 
     * @param value
     *     allowed object is
     *     {@link BSART }
     *     
     */
    public void setBSART(BSART value) {
        this.bsart = value;
    }

    /**
     * Obtiene el valor de la propiedad belnr.
     * 
     * @return
     *     possible object is
     *     {@link BELNR }
     *     
     */
    public BELNR getBELNR() {
        return belnr;
    }

    /**
     * Define el valor de la propiedad belnr.
     * 
     * @param value
     *     allowed object is
     *     {@link BELNR }
     *     
     */
    public void setBELNR(BELNR value) {
        this.belnr = value;
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
     * Obtiene el valor de la propiedad fkartrl.
     * 
     * @return
     *     possible object is
     *     {@link FKARTRL }
     *     
     */
    public FKARTRL getFKARTRL() {
        return fkartrl;
    }

    /**
     * Define el valor de la propiedad fkartrl.
     * 
     * @param value
     *     allowed object is
     *     {@link FKARTRL }
     *     
     */
    public void setFKARTRL(FKARTRL value) {
        this.fkartrl = value;
    }

    /**
     * Obtiene el valor de la propiedad ablad.
     * 
     * @return
     *     possible object is
     *     {@link ABLAD }
     *     
     */
    public ABLAD getABLAD() {
        return ablad;
    }

    /**
     * Define el valor de la propiedad ablad.
     * 
     * @param value
     *     allowed object is
     *     {@link ABLAD }
     *     
     */
    public void setABLAD(ABLAD value) {
        this.ablad = value;
    }

    /**
     * Obtiene el valor de la propiedad bstzd.
     * 
     * @return
     *     possible object is
     *     {@link BSTZD }
     *     
     */
    public BSTZD getBSTZD() {
        return bstzd;
    }

    /**
     * Define el valor de la propiedad bstzd.
     * 
     * @param value
     *     allowed object is
     *     {@link BSTZD }
     *     
     */
    public void setBSTZD(BSTZD value) {
        this.bstzd = value;
    }

    /**
     * Obtiene el valor de la propiedad vsart.
     * 
     * @return
     *     possible object is
     *     {@link VSART }
     *     
     */
    public VSART getVSART() {
        return vsart;
    }

    /**
     * Define el valor de la propiedad vsart.
     * 
     * @param value
     *     allowed object is
     *     {@link VSART }
     *     
     */
    public void setVSART(VSART value) {
        this.vsart = value;
    }

    /**
     * Obtiene el valor de la propiedad vsartbez.
     * 
     * @return
     *     possible object is
     *     {@link VSARTBEZ }
     *     
     */
    public VSARTBEZ getVSARTBEZ() {
        return vsartbez;
    }

    /**
     * Define el valor de la propiedad vsartbez.
     * 
     * @param value
     *     allowed object is
     *     {@link VSARTBEZ }
     *     
     */
    public void setVSARTBEZ(VSARTBEZ value) {
        this.vsartbez = value;
    }

    /**
     * Obtiene el valor de la propiedad recipntno.
     * 
     * @return
     *     possible object is
     *     {@link RECIPNTNO }
     *     
     */
    public RECIPNTNO getRECIPNTNO() {
        return recipntno;
    }

    /**
     * Define el valor de la propiedad recipntno.
     * 
     * @param value
     *     allowed object is
     *     {@link RECIPNTNO }
     *     
     */
    public void setRECIPNTNO(RECIPNTNO value) {
        this.recipntno = value;
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
     * Obtiene el valor de la propiedad autlf.
     * 
     * @return
     *     possible object is
     *     {@link AUTLF }
     *     
     */
    public AUTLF getAUTLF() {
        return autlf;
    }

    /**
     * Define el valor de la propiedad autlf.
     * 
     * @param value
     *     allowed object is
     *     {@link AUTLF }
     *     
     */
    public void setAUTLF(AUTLF value) {
        this.autlf = value;
    }

    /**
     * Obtiene el valor de la propiedad augru.
     * 
     * @return
     *     possible object is
     *     {@link AUGRU }
     *     
     */
    public AUGRU getAUGRU() {
        return augru;
    }

    /**
     * Define el valor de la propiedad augru.
     * 
     * @param value
     *     allowed object is
     *     {@link AUGRU }
     *     
     */
    public void setAUGRU(AUGRU value) {
        this.augru = value;
    }

    /**
     * Obtiene el valor de la propiedad augrubez.
     * 
     * @return
     *     possible object is
     *     {@link AUGRUBEZ }
     *     
     */
    public AUGRUBEZ getAUGRUBEZ() {
        return augrubez;
    }

    /**
     * Define el valor de la propiedad augrubez.
     * 
     * @param value
     *     allowed object is
     *     {@link AUGRUBEZ }
     *     
     */
    public void setAUGRUBEZ(AUGRUBEZ value) {
        this.augrubez = value;
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
     * Obtiene el valor de la propiedad abrvwbez.
     * 
     * @return
     *     possible object is
     *     {@link ABRVWBEZ }
     *     
     */
    public ABRVWBEZ getABRVWBEZ() {
        return abrvwbez;
    }

    /**
     * Define el valor de la propiedad abrvwbez.
     * 
     * @param value
     *     allowed object is
     *     {@link ABRVWBEZ }
     *     
     */
    public void setABRVWBEZ(ABRVWBEZ value) {
        this.abrvwbez = value;
    }

    /**
     * Obtiene el valor de la propiedad fktyp.
     * 
     * @return
     *     possible object is
     *     {@link FKTYP }
     *     
     */
    public FKTYP getFKTYP() {
        return fktyp;
    }

    /**
     * Define el valor de la propiedad fktyp.
     * 
     * @param value
     *     allowed object is
     *     {@link FKTYP }
     *     
     */
    public void setFKTYP(FKTYP value) {
        this.fktyp = value;
    }

    /**
     * Obtiene el valor de la propiedad lifsk.
     * 
     * @return
     *     possible object is
     *     {@link LIFSK }
     *     
     */
    public LIFSK getLIFSK() {
        return lifsk;
    }

    /**
     * Define el valor de la propiedad lifsk.
     * 
     * @param value
     *     allowed object is
     *     {@link LIFSK }
     *     
     */
    public void setLIFSK(LIFSK value) {
        this.lifsk = value;
    }

    /**
     * Obtiene el valor de la propiedad lifskbez.
     * 
     * @return
     *     possible object is
     *     {@link LIFSKBEZ }
     *     
     */
    public LIFSKBEZ getLIFSKBEZ() {
        return lifskbez;
    }

    /**
     * Define el valor de la propiedad lifskbez.
     * 
     * @param value
     *     allowed object is
     *     {@link LIFSKBEZ }
     *     
     */
    public void setLIFSKBEZ(LIFSKBEZ value) {
        this.lifskbez = value;
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
     * Obtiene el valor de la propiedad wkursm.
     * 
     * @return
     *     possible object is
     *     {@link WKURSM }
     *     
     */
    public WKURSM getWKURSM() {
        return wkursm;
    }

    /**
     * Define el valor de la propiedad wkursm.
     * 
     * @param value
     *     allowed object is
     *     {@link WKURSM }
     *     
     */
    public void setWKURSM(WKURSM value) {
        this.wkursm = value;
    }

    /**
     * Gets the value of the z1EDK02 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDK02 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDK02().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDK02 }
     * 
     * 
     */
    public List<Z1EDK02> getZ1EDK02() {
        if (z1EDK02 == null) {
            z1EDK02 = new ArrayList<Z1EDK02>();
        }
        return this.z1EDK02;
    }

    /**
     * Gets the value of the z1EDK04 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDK04 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDK04().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDK04 }
     * 
     * 
     */
    public List<Z1EDK04> getZ1EDK04() {
        if (z1EDK04 == null) {
            z1EDK04 = new ArrayList<Z1EDK04>();
        }
        return this.z1EDK04;
    }

    /**
     * Gets the value of the z1EDK05 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDK05 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDK05().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDK05 }
     * 
     * 
     */
    public List<Z1EDK05> getZ1EDK05() {
        if (z1EDK05 == null) {
            z1EDK05 = new ArrayList<Z1EDK05>();
        }
        return this.z1EDK05;
    }

    /**
     * Obtiene el valor de la propiedad z1EDK08.
     * 
     * @return
     *     possible object is
     *     {@link Z1EDK08 }
     *     
     */
    public Z1EDK08 getZ1EDK08() {
        return z1EDK08;
    }

    /**
     * Define el valor de la propiedad z1EDK08.
     * 
     * @param value
     *     allowed object is
     *     {@link Z1EDK08 }
     *     
     */
    public void setZ1EDK08(Z1EDK08 value) {
        this.z1EDK08 = value;
    }

    /**
     * Gets the value of the z1EDK13 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDK13 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDK13().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDK13 }
     * 
     * 
     */
    public List<Z1EDK13> getZ1EDK13() {
        if (z1EDK13 == null) {
            z1EDK13 = new ArrayList<Z1EDK13>();
        }
        return this.z1EDK13;
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

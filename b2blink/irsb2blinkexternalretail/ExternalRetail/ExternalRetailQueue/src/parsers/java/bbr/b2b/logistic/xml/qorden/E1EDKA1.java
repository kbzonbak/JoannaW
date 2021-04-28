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
 *         &lt;element ref="{}PARVW" minOccurs="0"/&gt;
 *         &lt;element ref="{}PARTN" minOccurs="0"/&gt;
 *         &lt;element ref="{}LIFNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}NAME1" minOccurs="0"/&gt;
 *         &lt;element ref="{}NAME2" minOccurs="0"/&gt;
 *         &lt;element ref="{}NAME3" minOccurs="0"/&gt;
 *         &lt;element ref="{}NAME4" minOccurs="0"/&gt;
 *         &lt;element ref="{}STRAS" minOccurs="0"/&gt;
 *         &lt;element ref="{}STRS2" minOccurs="0"/&gt;
 *         &lt;element ref="{}PFACH" minOccurs="0"/&gt;
 *         &lt;element ref="{}ORT01" minOccurs="0"/&gt;
 *         &lt;element ref="{}COUNC" minOccurs="0"/&gt;
 *         &lt;element ref="{}PSTLZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}PSTL2" minOccurs="0"/&gt;
 *         &lt;element ref="{}LAND1" minOccurs="0"/&gt;
 *         &lt;element ref="{}ABLAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}PERNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}PARNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELF1" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELF2" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELBX" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELFX" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELTX" minOccurs="0"/&gt;
 *         &lt;element ref="{}TELX1" minOccurs="0"/&gt;
 *         &lt;element ref="{}SPRAS" minOccurs="0"/&gt;
 *         &lt;element ref="{}ANRED" minOccurs="0"/&gt;
 *         &lt;element ref="{}ORT02" minOccurs="0"/&gt;
 *         &lt;element ref="{}HAUSN" minOccurs="0"/&gt;
 *         &lt;element ref="{}STOCK" minOccurs="0"/&gt;
 *         &lt;element ref="{}REGIO" minOccurs="0"/&gt;
 *         &lt;element ref="{}PARGE" minOccurs="0"/&gt;
 *         &lt;element ref="{}ISOAL" minOccurs="0"/&gt;
 *         &lt;element ref="{}ISONU" minOccurs="0"/&gt;
 *         &lt;element ref="{}FCODE" minOccurs="0"/&gt;
 *         &lt;element ref="{}IHREZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}BNAME" minOccurs="0"/&gt;
 *         &lt;element ref="{}PAORG" minOccurs="0"/&gt;
 *         &lt;element ref="{}ORGTX" minOccurs="0"/&gt;
 *         &lt;element ref="{}PAGRU" minOccurs="0"/&gt;
 *         &lt;element ref="{}KNREF" minOccurs="0"/&gt;
 *         &lt;element ref="{}ILNNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}PFORT" minOccurs="0"/&gt;
 *         &lt;element ref="{}SPRAS_ISO" minOccurs="0"/&gt;
 *         &lt;element ref="{}TITLE" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDKA1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDKA2" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}Z1EDKA3" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{}E1EDKA3" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "parvw",
    "partn",
    "lifnr",
    "name1",
    "name2",
    "name3",
    "name4",
    "stras",
    "strs2",
    "pfach",
    "ort01",
    "counc",
    "pstlz",
    "pstl2",
    "land1",
    "ablad",
    "pernr",
    "parnr",
    "telf1",
    "telf2",
    "telbx",
    "telfx",
    "teltx",
    "telx1",
    "spras",
    "anred",
    "ort02",
    "hausn",
    "stock",
    "regio",
    "parge",
    "isoal",
    "isonu",
    "fcode",
    "ihrez",
    "bname",
    "paorg",
    "orgtx",
    "pagru",
    "knref",
    "ilnnr",
    "pfort",
    "sprasiso",
    "title",
    "z1EDKA1",
    "z1EDKA2",
    "z1EDKA3",
    "e1EDKA3"
})
@XmlRootElement(name = "E1EDKA1")
public class E1EDKA1 {

    @XmlElement(name = "PARVW")
    protected PARVW parvw;
    @XmlElement(name = "PARTN")
    protected PARTN partn;
    @XmlElement(name = "LIFNR")
    protected LIFNR lifnr;
    @XmlElement(name = "NAME1")
    protected NAME1 name1;
    @XmlElement(name = "NAME2")
    protected NAME2 name2;
    @XmlElement(name = "NAME3")
    protected NAME3 name3;
    @XmlElement(name = "NAME4")
    protected NAME4 name4;
    @XmlElement(name = "STRAS")
    protected STRAS stras;
    @XmlElement(name = "STRS2")
    protected STRS2 strs2;
    @XmlElement(name = "PFACH")
    protected PFACH pfach;
    @XmlElement(name = "ORT01")
    protected ORT01 ort01;
    @XmlElement(name = "COUNC")
    protected COUNC counc;
    @XmlElement(name = "PSTLZ")
    protected PSTLZ pstlz;
    @XmlElement(name = "PSTL2")
    protected PSTL2 pstl2;
    @XmlElement(name = "LAND1")
    protected LAND1 land1;
    @XmlElement(name = "ABLAD")
    protected ABLAD ablad;
    @XmlElement(name = "PERNR")
    protected PERNR pernr;
    @XmlElement(name = "PARNR")
    protected PARNR parnr;
    @XmlElement(name = "TELF1")
    protected TELF1 telf1;
    @XmlElement(name = "TELF2")
    protected TELF2 telf2;
    @XmlElement(name = "TELBX")
    protected TELBX telbx;
    @XmlElement(name = "TELFX")
    protected TELFX telfx;
    @XmlElement(name = "TELTX")
    protected TELTX teltx;
    @XmlElement(name = "TELX1")
    protected TELX1 telx1;
    @XmlElement(name = "SPRAS")
    protected SPRAS spras;
    @XmlElement(name = "ANRED")
    protected ANRED anred;
    @XmlElement(name = "ORT02")
    protected ORT02 ort02;
    @XmlElement(name = "HAUSN")
    protected HAUSN hausn;
    @XmlElement(name = "STOCK")
    protected STOCK stock;
    @XmlElement(name = "REGIO")
    protected REGIO regio;
    @XmlElement(name = "PARGE")
    protected PARGE parge;
    @XmlElement(name = "ISOAL")
    protected ISOAL isoal;
    @XmlElement(name = "ISONU")
    protected ISONU isonu;
    @XmlElement(name = "FCODE")
    protected FCODE fcode;
    @XmlElement(name = "IHREZ")
    protected IHREZ ihrez;
    @XmlElement(name = "BNAME")
    protected BNAME bname;
    @XmlElement(name = "PAORG")
    protected PAORG paorg;
    @XmlElement(name = "ORGTX")
    protected ORGTX orgtx;
    @XmlElement(name = "PAGRU")
    protected PAGRU pagru;
    @XmlElement(name = "KNREF")
    protected KNREF knref;
    @XmlElement(name = "ILNNR")
    protected ILNNR ilnnr;
    @XmlElement(name = "PFORT")
    protected PFORT pfort;
    @XmlElement(name = "SPRAS_ISO")
    protected SPRASISO sprasiso;
    @XmlElement(name = "TITLE")
    protected TITLE title;
    @XmlElement(name = "Z1EDKA1")
    protected List<Z1EDKA1> z1EDKA1;
    @XmlElement(name = "Z1EDKA2")
    protected List<Z1EDKA2> z1EDKA2;
    @XmlElement(name = "Z1EDKA3")
    protected List<Z1EDKA3> z1EDKA3;
    @XmlElement(name = "E1EDKA3")
    protected List<E1EDKA3> e1EDKA3;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad parvw.
     * 
     * @return
     *     possible object is
     *     {@link PARVW }
     *     
     */
    public PARVW getPARVW() {
        return parvw;
    }

    /**
     * Define el valor de la propiedad parvw.
     * 
     * @param value
     *     allowed object is
     *     {@link PARVW }
     *     
     */
    public void setPARVW(PARVW value) {
        this.parvw = value;
    }

    /**
     * Obtiene el valor de la propiedad partn.
     * 
     * @return
     *     possible object is
     *     {@link PARTN }
     *     
     */
    public PARTN getPARTN() {
        return partn;
    }

    /**
     * Define el valor de la propiedad partn.
     * 
     * @param value
     *     allowed object is
     *     {@link PARTN }
     *     
     */
    public void setPARTN(PARTN value) {
        this.partn = value;
    }

    /**
     * Obtiene el valor de la propiedad lifnr.
     * 
     * @return
     *     possible object is
     *     {@link LIFNR }
     *     
     */
    public LIFNR getLIFNR() {
        return lifnr;
    }

    /**
     * Define el valor de la propiedad lifnr.
     * 
     * @param value
     *     allowed object is
     *     {@link LIFNR }
     *     
     */
    public void setLIFNR(LIFNR value) {
        this.lifnr = value;
    }

    /**
     * Obtiene el valor de la propiedad name1.
     * 
     * @return
     *     possible object is
     *     {@link NAME1 }
     *     
     */
    public NAME1 getNAME1() {
        return name1;
    }

    /**
     * Define el valor de la propiedad name1.
     * 
     * @param value
     *     allowed object is
     *     {@link NAME1 }
     *     
     */
    public void setNAME1(NAME1 value) {
        this.name1 = value;
    }

    /**
     * Obtiene el valor de la propiedad name2.
     * 
     * @return
     *     possible object is
     *     {@link NAME2 }
     *     
     */
    public NAME2 getNAME2() {
        return name2;
    }

    /**
     * Define el valor de la propiedad name2.
     * 
     * @param value
     *     allowed object is
     *     {@link NAME2 }
     *     
     */
    public void setNAME2(NAME2 value) {
        this.name2 = value;
    }

    /**
     * Obtiene el valor de la propiedad name3.
     * 
     * @return
     *     possible object is
     *     {@link NAME3 }
     *     
     */
    public NAME3 getNAME3() {
        return name3;
    }

    /**
     * Define el valor de la propiedad name3.
     * 
     * @param value
     *     allowed object is
     *     {@link NAME3 }
     *     
     */
    public void setNAME3(NAME3 value) {
        this.name3 = value;
    }

    /**
     * Obtiene el valor de la propiedad name4.
     * 
     * @return
     *     possible object is
     *     {@link NAME4 }
     *     
     */
    public NAME4 getNAME4() {
        return name4;
    }

    /**
     * Define el valor de la propiedad name4.
     * 
     * @param value
     *     allowed object is
     *     {@link NAME4 }
     *     
     */
    public void setNAME4(NAME4 value) {
        this.name4 = value;
    }

    /**
     * Obtiene el valor de la propiedad stras.
     * 
     * @return
     *     possible object is
     *     {@link STRAS }
     *     
     */
    public STRAS getSTRAS() {
        return stras;
    }

    /**
     * Define el valor de la propiedad stras.
     * 
     * @param value
     *     allowed object is
     *     {@link STRAS }
     *     
     */
    public void setSTRAS(STRAS value) {
        this.stras = value;
    }

    /**
     * Obtiene el valor de la propiedad strs2.
     * 
     * @return
     *     possible object is
     *     {@link STRS2 }
     *     
     */
    public STRS2 getSTRS2() {
        return strs2;
    }

    /**
     * Define el valor de la propiedad strs2.
     * 
     * @param value
     *     allowed object is
     *     {@link STRS2 }
     *     
     */
    public void setSTRS2(STRS2 value) {
        this.strs2 = value;
    }

    /**
     * Obtiene el valor de la propiedad pfach.
     * 
     * @return
     *     possible object is
     *     {@link PFACH }
     *     
     */
    public PFACH getPFACH() {
        return pfach;
    }

    /**
     * Define el valor de la propiedad pfach.
     * 
     * @param value
     *     allowed object is
     *     {@link PFACH }
     *     
     */
    public void setPFACH(PFACH value) {
        this.pfach = value;
    }

    /**
     * Obtiene el valor de la propiedad ort01.
     * 
     * @return
     *     possible object is
     *     {@link ORT01 }
     *     
     */
    public ORT01 getORT01() {
        return ort01;
    }

    /**
     * Define el valor de la propiedad ort01.
     * 
     * @param value
     *     allowed object is
     *     {@link ORT01 }
     *     
     */
    public void setORT01(ORT01 value) {
        this.ort01 = value;
    }

    /**
     * Obtiene el valor de la propiedad counc.
     * 
     * @return
     *     possible object is
     *     {@link COUNC }
     *     
     */
    public COUNC getCOUNC() {
        return counc;
    }

    /**
     * Define el valor de la propiedad counc.
     * 
     * @param value
     *     allowed object is
     *     {@link COUNC }
     *     
     */
    public void setCOUNC(COUNC value) {
        this.counc = value;
    }

    /**
     * Obtiene el valor de la propiedad pstlz.
     * 
     * @return
     *     possible object is
     *     {@link PSTLZ }
     *     
     */
    public PSTLZ getPSTLZ() {
        return pstlz;
    }

    /**
     * Define el valor de la propiedad pstlz.
     * 
     * @param value
     *     allowed object is
     *     {@link PSTLZ }
     *     
     */
    public void setPSTLZ(PSTLZ value) {
        this.pstlz = value;
    }

    /**
     * Obtiene el valor de la propiedad pstl2.
     * 
     * @return
     *     possible object is
     *     {@link PSTL2 }
     *     
     */
    public PSTL2 getPSTL2() {
        return pstl2;
    }

    /**
     * Define el valor de la propiedad pstl2.
     * 
     * @param value
     *     allowed object is
     *     {@link PSTL2 }
     *     
     */
    public void setPSTL2(PSTL2 value) {
        this.pstl2 = value;
    }

    /**
     * Obtiene el valor de la propiedad land1.
     * 
     * @return
     *     possible object is
     *     {@link LAND1 }
     *     
     */
    public LAND1 getLAND1() {
        return land1;
    }

    /**
     * Define el valor de la propiedad land1.
     * 
     * @param value
     *     allowed object is
     *     {@link LAND1 }
     *     
     */
    public void setLAND1(LAND1 value) {
        this.land1 = value;
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
     * Obtiene el valor de la propiedad parnr.
     * 
     * @return
     *     possible object is
     *     {@link PARNR }
     *     
     */
    public PARNR getPARNR() {
        return parnr;
    }

    /**
     * Define el valor de la propiedad parnr.
     * 
     * @param value
     *     allowed object is
     *     {@link PARNR }
     *     
     */
    public void setPARNR(PARNR value) {
        this.parnr = value;
    }

    /**
     * Obtiene el valor de la propiedad telf1.
     * 
     * @return
     *     possible object is
     *     {@link TELF1 }
     *     
     */
    public TELF1 getTELF1() {
        return telf1;
    }

    /**
     * Define el valor de la propiedad telf1.
     * 
     * @param value
     *     allowed object is
     *     {@link TELF1 }
     *     
     */
    public void setTELF1(TELF1 value) {
        this.telf1 = value;
    }

    /**
     * Obtiene el valor de la propiedad telf2.
     * 
     * @return
     *     possible object is
     *     {@link TELF2 }
     *     
     */
    public TELF2 getTELF2() {
        return telf2;
    }

    /**
     * Define el valor de la propiedad telf2.
     * 
     * @param value
     *     allowed object is
     *     {@link TELF2 }
     *     
     */
    public void setTELF2(TELF2 value) {
        this.telf2 = value;
    }

    /**
     * Obtiene el valor de la propiedad telbx.
     * 
     * @return
     *     possible object is
     *     {@link TELBX }
     *     
     */
    public TELBX getTELBX() {
        return telbx;
    }

    /**
     * Define el valor de la propiedad telbx.
     * 
     * @param value
     *     allowed object is
     *     {@link TELBX }
     *     
     */
    public void setTELBX(TELBX value) {
        this.telbx = value;
    }

    /**
     * Obtiene el valor de la propiedad telfx.
     * 
     * @return
     *     possible object is
     *     {@link TELFX }
     *     
     */
    public TELFX getTELFX() {
        return telfx;
    }

    /**
     * Define el valor de la propiedad telfx.
     * 
     * @param value
     *     allowed object is
     *     {@link TELFX }
     *     
     */
    public void setTELFX(TELFX value) {
        this.telfx = value;
    }

    /**
     * Obtiene el valor de la propiedad teltx.
     * 
     * @return
     *     possible object is
     *     {@link TELTX }
     *     
     */
    public TELTX getTELTX() {
        return teltx;
    }

    /**
     * Define el valor de la propiedad teltx.
     * 
     * @param value
     *     allowed object is
     *     {@link TELTX }
     *     
     */
    public void setTELTX(TELTX value) {
        this.teltx = value;
    }

    /**
     * Obtiene el valor de la propiedad telx1.
     * 
     * @return
     *     possible object is
     *     {@link TELX1 }
     *     
     */
    public TELX1 getTELX1() {
        return telx1;
    }

    /**
     * Define el valor de la propiedad telx1.
     * 
     * @param value
     *     allowed object is
     *     {@link TELX1 }
     *     
     */
    public void setTELX1(TELX1 value) {
        this.telx1 = value;
    }

    /**
     * Obtiene el valor de la propiedad spras.
     * 
     * @return
     *     possible object is
     *     {@link SPRAS }
     *     
     */
    public SPRAS getSPRAS() {
        return spras;
    }

    /**
     * Define el valor de la propiedad spras.
     * 
     * @param value
     *     allowed object is
     *     {@link SPRAS }
     *     
     */
    public void setSPRAS(SPRAS value) {
        this.spras = value;
    }

    /**
     * Obtiene el valor de la propiedad anred.
     * 
     * @return
     *     possible object is
     *     {@link ANRED }
     *     
     */
    public ANRED getANRED() {
        return anred;
    }

    /**
     * Define el valor de la propiedad anred.
     * 
     * @param value
     *     allowed object is
     *     {@link ANRED }
     *     
     */
    public void setANRED(ANRED value) {
        this.anred = value;
    }

    /**
     * Obtiene el valor de la propiedad ort02.
     * 
     * @return
     *     possible object is
     *     {@link ORT02 }
     *     
     */
    public ORT02 getORT02() {
        return ort02;
    }

    /**
     * Define el valor de la propiedad ort02.
     * 
     * @param value
     *     allowed object is
     *     {@link ORT02 }
     *     
     */
    public void setORT02(ORT02 value) {
        this.ort02 = value;
    }

    /**
     * Obtiene el valor de la propiedad hausn.
     * 
     * @return
     *     possible object is
     *     {@link HAUSN }
     *     
     */
    public HAUSN getHAUSN() {
        return hausn;
    }

    /**
     * Define el valor de la propiedad hausn.
     * 
     * @param value
     *     allowed object is
     *     {@link HAUSN }
     *     
     */
    public void setHAUSN(HAUSN value) {
        this.hausn = value;
    }

    /**
     * Obtiene el valor de la propiedad stock.
     * 
     * @return
     *     possible object is
     *     {@link STOCK }
     *     
     */
    public STOCK getSTOCK() {
        return stock;
    }

    /**
     * Define el valor de la propiedad stock.
     * 
     * @param value
     *     allowed object is
     *     {@link STOCK }
     *     
     */
    public void setSTOCK(STOCK value) {
        this.stock = value;
    }

    /**
     * Obtiene el valor de la propiedad regio.
     * 
     * @return
     *     possible object is
     *     {@link REGIO }
     *     
     */
    public REGIO getREGIO() {
        return regio;
    }

    /**
     * Define el valor de la propiedad regio.
     * 
     * @param value
     *     allowed object is
     *     {@link REGIO }
     *     
     */
    public void setREGIO(REGIO value) {
        this.regio = value;
    }

    /**
     * Obtiene el valor de la propiedad parge.
     * 
     * @return
     *     possible object is
     *     {@link PARGE }
     *     
     */
    public PARGE getPARGE() {
        return parge;
    }

    /**
     * Define el valor de la propiedad parge.
     * 
     * @param value
     *     allowed object is
     *     {@link PARGE }
     *     
     */
    public void setPARGE(PARGE value) {
        this.parge = value;
    }

    /**
     * Obtiene el valor de la propiedad isoal.
     * 
     * @return
     *     possible object is
     *     {@link ISOAL }
     *     
     */
    public ISOAL getISOAL() {
        return isoal;
    }

    /**
     * Define el valor de la propiedad isoal.
     * 
     * @param value
     *     allowed object is
     *     {@link ISOAL }
     *     
     */
    public void setISOAL(ISOAL value) {
        this.isoal = value;
    }

    /**
     * Obtiene el valor de la propiedad isonu.
     * 
     * @return
     *     possible object is
     *     {@link ISONU }
     *     
     */
    public ISONU getISONU() {
        return isonu;
    }

    /**
     * Define el valor de la propiedad isonu.
     * 
     * @param value
     *     allowed object is
     *     {@link ISONU }
     *     
     */
    public void setISONU(ISONU value) {
        this.isonu = value;
    }

    /**
     * Obtiene el valor de la propiedad fcode.
     * 
     * @return
     *     possible object is
     *     {@link FCODE }
     *     
     */
    public FCODE getFCODE() {
        return fcode;
    }

    /**
     * Define el valor de la propiedad fcode.
     * 
     * @param value
     *     allowed object is
     *     {@link FCODE }
     *     
     */
    public void setFCODE(FCODE value) {
        this.fcode = value;
    }

    /**
     * Obtiene el valor de la propiedad ihrez.
     * 
     * @return
     *     possible object is
     *     {@link IHREZ }
     *     
     */
    public IHREZ getIHREZ() {
        return ihrez;
    }

    /**
     * Define el valor de la propiedad ihrez.
     * 
     * @param value
     *     allowed object is
     *     {@link IHREZ }
     *     
     */
    public void setIHREZ(IHREZ value) {
        this.ihrez = value;
    }

    /**
     * Obtiene el valor de la propiedad bname.
     * 
     * @return
     *     possible object is
     *     {@link BNAME }
     *     
     */
    public BNAME getBNAME() {
        return bname;
    }

    /**
     * Define el valor de la propiedad bname.
     * 
     * @param value
     *     allowed object is
     *     {@link BNAME }
     *     
     */
    public void setBNAME(BNAME value) {
        this.bname = value;
    }

    /**
     * Obtiene el valor de la propiedad paorg.
     * 
     * @return
     *     possible object is
     *     {@link PAORG }
     *     
     */
    public PAORG getPAORG() {
        return paorg;
    }

    /**
     * Define el valor de la propiedad paorg.
     * 
     * @param value
     *     allowed object is
     *     {@link PAORG }
     *     
     */
    public void setPAORG(PAORG value) {
        this.paorg = value;
    }

    /**
     * Obtiene el valor de la propiedad orgtx.
     * 
     * @return
     *     possible object is
     *     {@link ORGTX }
     *     
     */
    public ORGTX getORGTX() {
        return orgtx;
    }

    /**
     * Define el valor de la propiedad orgtx.
     * 
     * @param value
     *     allowed object is
     *     {@link ORGTX }
     *     
     */
    public void setORGTX(ORGTX value) {
        this.orgtx = value;
    }

    /**
     * Obtiene el valor de la propiedad pagru.
     * 
     * @return
     *     possible object is
     *     {@link PAGRU }
     *     
     */
    public PAGRU getPAGRU() {
        return pagru;
    }

    /**
     * Define el valor de la propiedad pagru.
     * 
     * @param value
     *     allowed object is
     *     {@link PAGRU }
     *     
     */
    public void setPAGRU(PAGRU value) {
        this.pagru = value;
    }

    /**
     * Obtiene el valor de la propiedad knref.
     * 
     * @return
     *     possible object is
     *     {@link KNREF }
     *     
     */
    public KNREF getKNREF() {
        return knref;
    }

    /**
     * Define el valor de la propiedad knref.
     * 
     * @param value
     *     allowed object is
     *     {@link KNREF }
     *     
     */
    public void setKNREF(KNREF value) {
        this.knref = value;
    }

    /**
     * Obtiene el valor de la propiedad ilnnr.
     * 
     * @return
     *     possible object is
     *     {@link ILNNR }
     *     
     */
    public ILNNR getILNNR() {
        return ilnnr;
    }

    /**
     * Define el valor de la propiedad ilnnr.
     * 
     * @param value
     *     allowed object is
     *     {@link ILNNR }
     *     
     */
    public void setILNNR(ILNNR value) {
        this.ilnnr = value;
    }

    /**
     * Obtiene el valor de la propiedad pfort.
     * 
     * @return
     *     possible object is
     *     {@link PFORT }
     *     
     */
    public PFORT getPFORT() {
        return pfort;
    }

    /**
     * Define el valor de la propiedad pfort.
     * 
     * @param value
     *     allowed object is
     *     {@link PFORT }
     *     
     */
    public void setPFORT(PFORT value) {
        this.pfort = value;
    }

    /**
     * Obtiene el valor de la propiedad sprasiso.
     * 
     * @return
     *     possible object is
     *     {@link SPRASISO }
     *     
     */
    public SPRASISO getSPRASISO() {
        return sprasiso;
    }

    /**
     * Define el valor de la propiedad sprasiso.
     * 
     * @param value
     *     allowed object is
     *     {@link SPRASISO }
     *     
     */
    public void setSPRASISO(SPRASISO value) {
        this.sprasiso = value;
    }

    /**
     * Obtiene el valor de la propiedad title.
     * 
     * @return
     *     possible object is
     *     {@link TITLE }
     *     
     */
    public TITLE getTITLE() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     * 
     * @param value
     *     allowed object is
     *     {@link TITLE }
     *     
     */
    public void setTITLE(TITLE value) {
        this.title = value;
    }

    /**
     * Gets the value of the z1EDKA1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDKA1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDKA1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDKA1 }
     * 
     * 
     */
    public List<Z1EDKA1> getZ1EDKA1() {
        if (z1EDKA1 == null) {
            z1EDKA1 = new ArrayList<Z1EDKA1>();
        }
        return this.z1EDKA1;
    }

    /**
     * Gets the value of the z1EDKA2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDKA2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDKA2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDKA2 }
     * 
     * 
     */
    public List<Z1EDKA2> getZ1EDKA2() {
        if (z1EDKA2 == null) {
            z1EDKA2 = new ArrayList<Z1EDKA2>();
        }
        return this.z1EDKA2;
    }

    /**
     * Gets the value of the z1EDKA3 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the z1EDKA3 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZ1EDKA3().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Z1EDKA3 }
     * 
     * 
     */
    public List<Z1EDKA3> getZ1EDKA3() {
        if (z1EDKA3 == null) {
            z1EDKA3 = new ArrayList<Z1EDKA3>();
        }
        return this.z1EDKA3;
    }

    /**
     * Gets the value of the e1EDKA3 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the e1EDKA3 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getE1EDKA3().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link E1EDKA3 }
     * 
     * 
     */
    public List<E1EDKA3> getE1EDKA3() {
        if (e1EDKA3 == null) {
            e1EDKA3 = new ArrayList<E1EDKA3>();
        }
        return this.e1EDKA3;
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

//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

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
 *         &lt;element ref="{}VELIN" minOccurs="0"/&gt;
 *         &lt;element ref="{}VBELN" minOccurs="0"/&gt;
 *         &lt;element ref="{}POSNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}EXIDV" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEMNG" minOccurs="0"/&gt;
 *         &lt;element ref="{}VEMEH" minOccurs="0"/&gt;
 *         &lt;element ref="{}MATNR" minOccurs="0"/&gt;
 *         &lt;element ref="{}KDMAT" minOccurs="0"/&gt;
 *         &lt;element ref="{}CHARG" minOccurs="0"/&gt;
 *         &lt;element ref="{}WERKS" minOccurs="0"/&gt;
 *         &lt;element ref="{}LGORT" minOccurs="0"/&gt;
 *         &lt;element ref="{}CUOBJ" minOccurs="0"/&gt;
 *         &lt;element ref="{}BESTQ" minOccurs="0"/&gt;
 *         &lt;element ref="{}SOBKZ" minOccurs="0"/&gt;
 *         &lt;element ref="{}SONUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}ANZSN" minOccurs="0"/&gt;
 *         &lt;element ref="{}WDATU" minOccurs="0"/&gt;
 *         &lt;element ref="{}PARID" minOccurs="0"/&gt;
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
    "velin",
    "vbeln",
    "posnr",
    "exidv",
    "vemng",
    "vemeh",
    "matnr",
    "kdmat",
    "charg",
    "werks",
    "lgort",
    "cuobj",
    "bestq",
    "sobkz",
    "sonum",
    "anzsn",
    "wdatu",
    "parid"
})
@XmlRootElement(name = "E1EDL44")
public class E1EDL44 {

    @XmlElement(name = "VELIN")
    protected VELIN velin;
    @XmlElement(name = "VBELN")
    protected VBELN vbeln;
    @XmlElement(name = "POSNR")
    protected POSNR posnr;
    @XmlElement(name = "EXIDV")
    protected EXIDV exidv;
    @XmlElement(name = "VEMNG")
    protected VEMNG vemng;
    @XmlElement(name = "VEMEH")
    protected VEMEH vemeh;
    @XmlElement(name = "MATNR")
    protected MATNR matnr;
    @XmlElement(name = "KDMAT")
    protected KDMAT kdmat;
    @XmlElement(name = "CHARG")
    protected CHARG charg;
    @XmlElement(name = "WERKS")
    protected WERKS werks;
    @XmlElement(name = "LGORT")
    protected LGORT lgort;
    @XmlElement(name = "CUOBJ")
    protected CUOBJ cuobj;
    @XmlElement(name = "BESTQ")
    protected BESTQ bestq;
    @XmlElement(name = "SOBKZ")
    protected SOBKZ sobkz;
    @XmlElement(name = "SONUM")
    protected SONUM sonum;
    @XmlElement(name = "ANZSN")
    protected ANZSN anzsn;
    @XmlElement(name = "WDATU")
    protected WDATU wdatu;
    @XmlElement(name = "PARID")
    protected PARID parid;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad velin.
     * 
     * @return
     *     possible object is
     *     {@link VELIN }
     *     
     */
    public VELIN getVELIN() {
        return velin;
    }

    /**
     * Define el valor de la propiedad velin.
     * 
     * @param value
     *     allowed object is
     *     {@link VELIN }
     *     
     */
    public void setVELIN(VELIN value) {
        this.velin = value;
    }

    /**
     * Obtiene el valor de la propiedad vbeln.
     * 
     * @return
     *     possible object is
     *     {@link VBELN }
     *     
     */
    public VBELN getVBELN() {
        return vbeln;
    }

    /**
     * Define el valor de la propiedad vbeln.
     * 
     * @param value
     *     allowed object is
     *     {@link VBELN }
     *     
     */
    public void setVBELN(VBELN value) {
        this.vbeln = value;
    }

    /**
     * Obtiene el valor de la propiedad posnr.
     * 
     * @return
     *     possible object is
     *     {@link POSNR }
     *     
     */
    public POSNR getPOSNR() {
        return posnr;
    }

    /**
     * Define el valor de la propiedad posnr.
     * 
     * @param value
     *     allowed object is
     *     {@link POSNR }
     *     
     */
    public void setPOSNR(POSNR value) {
        this.posnr = value;
    }

    /**
     * Obtiene el valor de la propiedad exidv.
     * 
     * @return
     *     possible object is
     *     {@link EXIDV }
     *     
     */
    public EXIDV getEXIDV() {
        return exidv;
    }

    /**
     * Define el valor de la propiedad exidv.
     * 
     * @param value
     *     allowed object is
     *     {@link EXIDV }
     *     
     */
    public void setEXIDV(EXIDV value) {
        this.exidv = value;
    }

    /**
     * Obtiene el valor de la propiedad vemng.
     * 
     * @return
     *     possible object is
     *     {@link VEMNG }
     *     
     */
    public VEMNG getVEMNG() {
        return vemng;
    }

    /**
     * Define el valor de la propiedad vemng.
     * 
     * @param value
     *     allowed object is
     *     {@link VEMNG }
     *     
     */
    public void setVEMNG(VEMNG value) {
        this.vemng = value;
    }

    /**
     * Obtiene el valor de la propiedad vemeh.
     * 
     * @return
     *     possible object is
     *     {@link VEMEH }
     *     
     */
    public VEMEH getVEMEH() {
        return vemeh;
    }

    /**
     * Define el valor de la propiedad vemeh.
     * 
     * @param value
     *     allowed object is
     *     {@link VEMEH }
     *     
     */
    public void setVEMEH(VEMEH value) {
        this.vemeh = value;
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
     * Obtiene el valor de la propiedad kdmat.
     * 
     * @return
     *     possible object is
     *     {@link KDMAT }
     *     
     */
    public KDMAT getKDMAT() {
        return kdmat;
    }

    /**
     * Define el valor de la propiedad kdmat.
     * 
     * @param value
     *     allowed object is
     *     {@link KDMAT }
     *     
     */
    public void setKDMAT(KDMAT value) {
        this.kdmat = value;
    }

    /**
     * Obtiene el valor de la propiedad charg.
     * 
     * @return
     *     possible object is
     *     {@link CHARG }
     *     
     */
    public CHARG getCHARG() {
        return charg;
    }

    /**
     * Define el valor de la propiedad charg.
     * 
     * @param value
     *     allowed object is
     *     {@link CHARG }
     *     
     */
    public void setCHARG(CHARG value) {
        this.charg = value;
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
     * Obtiene el valor de la propiedad cuobj.
     * 
     * @return
     *     possible object is
     *     {@link CUOBJ }
     *     
     */
    public CUOBJ getCUOBJ() {
        return cuobj;
    }

    /**
     * Define el valor de la propiedad cuobj.
     * 
     * @param value
     *     allowed object is
     *     {@link CUOBJ }
     *     
     */
    public void setCUOBJ(CUOBJ value) {
        this.cuobj = value;
    }

    /**
     * Obtiene el valor de la propiedad bestq.
     * 
     * @return
     *     possible object is
     *     {@link BESTQ }
     *     
     */
    public BESTQ getBESTQ() {
        return bestq;
    }

    /**
     * Define el valor de la propiedad bestq.
     * 
     * @param value
     *     allowed object is
     *     {@link BESTQ }
     *     
     */
    public void setBESTQ(BESTQ value) {
        this.bestq = value;
    }

    /**
     * Obtiene el valor de la propiedad sobkz.
     * 
     * @return
     *     possible object is
     *     {@link SOBKZ }
     *     
     */
    public SOBKZ getSOBKZ() {
        return sobkz;
    }

    /**
     * Define el valor de la propiedad sobkz.
     * 
     * @param value
     *     allowed object is
     *     {@link SOBKZ }
     *     
     */
    public void setSOBKZ(SOBKZ value) {
        this.sobkz = value;
    }

    /**
     * Obtiene el valor de la propiedad sonum.
     * 
     * @return
     *     possible object is
     *     {@link SONUM }
     *     
     */
    public SONUM getSONUM() {
        return sonum;
    }

    /**
     * Define el valor de la propiedad sonum.
     * 
     * @param value
     *     allowed object is
     *     {@link SONUM }
     *     
     */
    public void setSONUM(SONUM value) {
        this.sonum = value;
    }

    /**
     * Obtiene el valor de la propiedad anzsn.
     * 
     * @return
     *     possible object is
     *     {@link ANZSN }
     *     
     */
    public ANZSN getANZSN() {
        return anzsn;
    }

    /**
     * Define el valor de la propiedad anzsn.
     * 
     * @param value
     *     allowed object is
     *     {@link ANZSN }
     *     
     */
    public void setANZSN(ANZSN value) {
        this.anzsn = value;
    }

    /**
     * Obtiene el valor de la propiedad wdatu.
     * 
     * @return
     *     possible object is
     *     {@link WDATU }
     *     
     */
    public WDATU getWDATU() {
        return wdatu;
    }

    /**
     * Define el valor de la propiedad wdatu.
     * 
     * @param value
     *     allowed object is
     *     {@link WDATU }
     *     
     */
    public void setWDATU(WDATU value) {
        this.wdatu = value;
    }

    /**
     * Obtiene el valor de la propiedad parid.
     * 
     * @return
     *     possible object is
     *     {@link PARID }
     *     
     */
    public PARID getPARID() {
        return parid;
    }

    /**
     * Define el valor de la propiedad parid.
     * 
     * @param value
     *     allowed object is
     *     {@link PARID }
     *     
     */
    public void setPARID(PARID value) {
        this.parid = value;
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

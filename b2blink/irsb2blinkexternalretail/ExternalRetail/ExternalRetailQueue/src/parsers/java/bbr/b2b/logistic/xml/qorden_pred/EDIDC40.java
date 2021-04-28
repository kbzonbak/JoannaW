//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:44 PM CLT 
//


package bbr.b2b.logistic.xml.qorden_pred;

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
 *         &lt;element ref="{}TABNAM"/&gt;
 *         &lt;element ref="{}MANDT" minOccurs="0"/&gt;
 *         &lt;element ref="{}DOCNUM" minOccurs="0"/&gt;
 *         &lt;element ref="{}DOCREL" minOccurs="0"/&gt;
 *         &lt;element ref="{}STATUS" minOccurs="0"/&gt;
 *         &lt;element ref="{}DIRECT"/&gt;
 *         &lt;element ref="{}OUTMOD" minOccurs="0"/&gt;
 *         &lt;element ref="{}EXPRSS" minOccurs="0"/&gt;
 *         &lt;element ref="{}TEST" minOccurs="0"/&gt;
 *         &lt;element ref="{}IDOCTYP"/&gt;
 *         &lt;element ref="{}CIMTYP" minOccurs="0"/&gt;
 *         &lt;element ref="{}MESTYP"/&gt;
 *         &lt;element ref="{}MESCOD" minOccurs="0"/&gt;
 *         &lt;element ref="{}MESFCT" minOccurs="0"/&gt;
 *         &lt;element ref="{}STD" minOccurs="0"/&gt;
 *         &lt;element ref="{}STDVRS" minOccurs="0"/&gt;
 *         &lt;element ref="{}STDMES" minOccurs="0"/&gt;
 *         &lt;element ref="{}SNDPOR"/&gt;
 *         &lt;element ref="{}SNDPRT"/&gt;
 *         &lt;element ref="{}SNDPFC" minOccurs="0"/&gt;
 *         &lt;element ref="{}SNDPRN"/&gt;
 *         &lt;element ref="{}SNDSAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}SNDLAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}RCVPOR"/&gt;
 *         &lt;element ref="{}RCVPRT"/&gt;
 *         &lt;element ref="{}RCVPFC" minOccurs="0"/&gt;
 *         &lt;element ref="{}RCVPRN"/&gt;
 *         &lt;element ref="{}RCVSAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}RCVLAD" minOccurs="0"/&gt;
 *         &lt;element ref="{}CREDAT" minOccurs="0"/&gt;
 *         &lt;element ref="{}CRETIM" minOccurs="0"/&gt;
 *         &lt;element ref="{}REFINT" minOccurs="0"/&gt;
 *         &lt;element ref="{}REFGRP" minOccurs="0"/&gt;
 *         &lt;element ref="{}REFMES" minOccurs="0"/&gt;
 *         &lt;element ref="{}ARCKEY" minOccurs="0"/&gt;
 *         &lt;element ref="{}SERIAL" minOccurs="0"/&gt;
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
    "tabnam",
    "mandt",
    "docnum",
    "docrel",
    "status",
    "direct",
    "outmod",
    "exprss",
    "test",
    "idoctyp",
    "cimtyp",
    "mestyp",
    "mescod",
    "mesfct",
    "std",
    "stdvrs",
    "stdmes",
    "sndpor",
    "sndprt",
    "sndpfc",
    "sndprn",
    "sndsad",
    "sndlad",
    "rcvpor",
    "rcvprt",
    "rcvpfc",
    "rcvprn",
    "rcvsad",
    "rcvlad",
    "credat",
    "cretim",
    "refint",
    "refgrp",
    "refmes",
    "arckey",
    "serial"
})
@XmlRootElement(name = "EDI_DC40")
public class EDIDC40 {

    @XmlElement(name = "TABNAM", required = true)
    protected TABNAM tabnam;
    @XmlElement(name = "MANDT")
    protected MANDT mandt;
    @XmlElement(name = "DOCNUM")
    protected DOCNUM docnum;
    @XmlElement(name = "DOCREL")
    protected DOCREL docrel;
    @XmlElement(name = "STATUS")
    protected STATUS status;
    @XmlElement(name = "DIRECT", required = true)
    protected DIRECT direct;
    @XmlElement(name = "OUTMOD")
    protected OUTMOD outmod;
    @XmlElement(name = "EXPRSS")
    protected EXPRSS exprss;
    @XmlElement(name = "TEST")
    protected TEST test;
    @XmlElement(name = "IDOCTYP", required = true)
    protected IDOCTYP idoctyp;
    @XmlElement(name = "CIMTYP")
    protected CIMTYP cimtyp;
    @XmlElement(name = "MESTYP", required = true)
    protected MESTYP mestyp;
    @XmlElement(name = "MESCOD")
    protected MESCOD mescod;
    @XmlElement(name = "MESFCT")
    protected MESFCT mesfct;
    @XmlElement(name = "STD")
    protected STD std;
    @XmlElement(name = "STDVRS")
    protected STDVRS stdvrs;
    @XmlElement(name = "STDMES")
    protected STDMES stdmes;
    @XmlElement(name = "SNDPOR", required = true)
    protected SNDPOR sndpor;
    @XmlElement(name = "SNDPRT", required = true)
    protected SNDPRT sndprt;
    @XmlElement(name = "SNDPFC")
    protected SNDPFC sndpfc;
    @XmlElement(name = "SNDPRN", required = true)
    protected SNDPRN sndprn;
    @XmlElement(name = "SNDSAD")
    protected SNDSAD sndsad;
    @XmlElement(name = "SNDLAD")
    protected SNDLAD sndlad;
    @XmlElement(name = "RCVPOR", required = true)
    protected RCVPOR rcvpor;
    @XmlElement(name = "RCVPRT", required = true)
    protected RCVPRT rcvprt;
    @XmlElement(name = "RCVPFC")
    protected RCVPFC rcvpfc;
    @XmlElement(name = "RCVPRN", required = true)
    protected RCVPRN rcvprn;
    @XmlElement(name = "RCVSAD")
    protected RCVSAD rcvsad;
    @XmlElement(name = "RCVLAD")
    protected RCVLAD rcvlad;
    @XmlElement(name = "CREDAT")
    protected CREDAT credat;
    @XmlElement(name = "CRETIM")
    protected CRETIM cretim;
    @XmlElement(name = "REFINT")
    protected REFINT refint;
    @XmlElement(name = "REFGRP")
    protected REFGRP refgrp;
    @XmlElement(name = "REFMES")
    protected REFMES refmes;
    @XmlElement(name = "ARCKEY")
    protected ARCKEY arckey;
    @XmlElement(name = "SERIAL")
    protected SERIAL serial;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad tabnam.
     * 
     * @return
     *     possible object is
     *     {@link TABNAM }
     *     
     */
    public TABNAM getTABNAM() {
        return tabnam;
    }

    /**
     * Define el valor de la propiedad tabnam.
     * 
     * @param value
     *     allowed object is
     *     {@link TABNAM }
     *     
     */
    public void setTABNAM(TABNAM value) {
        this.tabnam = value;
    }

    /**
     * Obtiene el valor de la propiedad mandt.
     * 
     * @return
     *     possible object is
     *     {@link MANDT }
     *     
     */
    public MANDT getMANDT() {
        return mandt;
    }

    /**
     * Define el valor de la propiedad mandt.
     * 
     * @param value
     *     allowed object is
     *     {@link MANDT }
     *     
     */
    public void setMANDT(MANDT value) {
        this.mandt = value;
    }

    /**
     * Obtiene el valor de la propiedad docnum.
     * 
     * @return
     *     possible object is
     *     {@link DOCNUM }
     *     
     */
    public DOCNUM getDOCNUM() {
        return docnum;
    }

    /**
     * Define el valor de la propiedad docnum.
     * 
     * @param value
     *     allowed object is
     *     {@link DOCNUM }
     *     
     */
    public void setDOCNUM(DOCNUM value) {
        this.docnum = value;
    }

    /**
     * Obtiene el valor de la propiedad docrel.
     * 
     * @return
     *     possible object is
     *     {@link DOCREL }
     *     
     */
    public DOCREL getDOCREL() {
        return docrel;
    }

    /**
     * Define el valor de la propiedad docrel.
     * 
     * @param value
     *     allowed object is
     *     {@link DOCREL }
     *     
     */
    public void setDOCREL(DOCREL value) {
        this.docrel = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link STATUS }
     *     
     */
    public STATUS getSTATUS() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link STATUS }
     *     
     */
    public void setSTATUS(STATUS value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad direct.
     * 
     * @return
     *     possible object is
     *     {@link DIRECT }
     *     
     */
    public DIRECT getDIRECT() {
        return direct;
    }

    /**
     * Define el valor de la propiedad direct.
     * 
     * @param value
     *     allowed object is
     *     {@link DIRECT }
     *     
     */
    public void setDIRECT(DIRECT value) {
        this.direct = value;
    }

    /**
     * Obtiene el valor de la propiedad outmod.
     * 
     * @return
     *     possible object is
     *     {@link OUTMOD }
     *     
     */
    public OUTMOD getOUTMOD() {
        return outmod;
    }

    /**
     * Define el valor de la propiedad outmod.
     * 
     * @param value
     *     allowed object is
     *     {@link OUTMOD }
     *     
     */
    public void setOUTMOD(OUTMOD value) {
        this.outmod = value;
    }

    /**
     * Obtiene el valor de la propiedad exprss.
     * 
     * @return
     *     possible object is
     *     {@link EXPRSS }
     *     
     */
    public EXPRSS getEXPRSS() {
        return exprss;
    }

    /**
     * Define el valor de la propiedad exprss.
     * 
     * @param value
     *     allowed object is
     *     {@link EXPRSS }
     *     
     */
    public void setEXPRSS(EXPRSS value) {
        this.exprss = value;
    }

    /**
     * Obtiene el valor de la propiedad test.
     * 
     * @return
     *     possible object is
     *     {@link TEST }
     *     
     */
    public TEST getTEST() {
        return test;
    }

    /**
     * Define el valor de la propiedad test.
     * 
     * @param value
     *     allowed object is
     *     {@link TEST }
     *     
     */
    public void setTEST(TEST value) {
        this.test = value;
    }

    /**
     * Obtiene el valor de la propiedad idoctyp.
     * 
     * @return
     *     possible object is
     *     {@link IDOCTYP }
     *     
     */
    public IDOCTYP getIDOCTYP() {
        return idoctyp;
    }

    /**
     * Define el valor de la propiedad idoctyp.
     * 
     * @param value
     *     allowed object is
     *     {@link IDOCTYP }
     *     
     */
    public void setIDOCTYP(IDOCTYP value) {
        this.idoctyp = value;
    }

    /**
     * Obtiene el valor de la propiedad cimtyp.
     * 
     * @return
     *     possible object is
     *     {@link CIMTYP }
     *     
     */
    public CIMTYP getCIMTYP() {
        return cimtyp;
    }

    /**
     * Define el valor de la propiedad cimtyp.
     * 
     * @param value
     *     allowed object is
     *     {@link CIMTYP }
     *     
     */
    public void setCIMTYP(CIMTYP value) {
        this.cimtyp = value;
    }

    /**
     * Obtiene el valor de la propiedad mestyp.
     * 
     * @return
     *     possible object is
     *     {@link MESTYP }
     *     
     */
    public MESTYP getMESTYP() {
        return mestyp;
    }

    /**
     * Define el valor de la propiedad mestyp.
     * 
     * @param value
     *     allowed object is
     *     {@link MESTYP }
     *     
     */
    public void setMESTYP(MESTYP value) {
        this.mestyp = value;
    }

    /**
     * Obtiene el valor de la propiedad mescod.
     * 
     * @return
     *     possible object is
     *     {@link MESCOD }
     *     
     */
    public MESCOD getMESCOD() {
        return mescod;
    }

    /**
     * Define el valor de la propiedad mescod.
     * 
     * @param value
     *     allowed object is
     *     {@link MESCOD }
     *     
     */
    public void setMESCOD(MESCOD value) {
        this.mescod = value;
    }

    /**
     * Obtiene el valor de la propiedad mesfct.
     * 
     * @return
     *     possible object is
     *     {@link MESFCT }
     *     
     */
    public MESFCT getMESFCT() {
        return mesfct;
    }

    /**
     * Define el valor de la propiedad mesfct.
     * 
     * @param value
     *     allowed object is
     *     {@link MESFCT }
     *     
     */
    public void setMESFCT(MESFCT value) {
        this.mesfct = value;
    }

    /**
     * Obtiene el valor de la propiedad std.
     * 
     * @return
     *     possible object is
     *     {@link STD }
     *     
     */
    public STD getSTD() {
        return std;
    }

    /**
     * Define el valor de la propiedad std.
     * 
     * @param value
     *     allowed object is
     *     {@link STD }
     *     
     */
    public void setSTD(STD value) {
        this.std = value;
    }

    /**
     * Obtiene el valor de la propiedad stdvrs.
     * 
     * @return
     *     possible object is
     *     {@link STDVRS }
     *     
     */
    public STDVRS getSTDVRS() {
        return stdvrs;
    }

    /**
     * Define el valor de la propiedad stdvrs.
     * 
     * @param value
     *     allowed object is
     *     {@link STDVRS }
     *     
     */
    public void setSTDVRS(STDVRS value) {
        this.stdvrs = value;
    }

    /**
     * Obtiene el valor de la propiedad stdmes.
     * 
     * @return
     *     possible object is
     *     {@link STDMES }
     *     
     */
    public STDMES getSTDMES() {
        return stdmes;
    }

    /**
     * Define el valor de la propiedad stdmes.
     * 
     * @param value
     *     allowed object is
     *     {@link STDMES }
     *     
     */
    public void setSTDMES(STDMES value) {
        this.stdmes = value;
    }

    /**
     * Obtiene el valor de la propiedad sndpor.
     * 
     * @return
     *     possible object is
     *     {@link SNDPOR }
     *     
     */
    public SNDPOR getSNDPOR() {
        return sndpor;
    }

    /**
     * Define el valor de la propiedad sndpor.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDPOR }
     *     
     */
    public void setSNDPOR(SNDPOR value) {
        this.sndpor = value;
    }

    /**
     * Obtiene el valor de la propiedad sndprt.
     * 
     * @return
     *     possible object is
     *     {@link SNDPRT }
     *     
     */
    public SNDPRT getSNDPRT() {
        return sndprt;
    }

    /**
     * Define el valor de la propiedad sndprt.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDPRT }
     *     
     */
    public void setSNDPRT(SNDPRT value) {
        this.sndprt = value;
    }

    /**
     * Obtiene el valor de la propiedad sndpfc.
     * 
     * @return
     *     possible object is
     *     {@link SNDPFC }
     *     
     */
    public SNDPFC getSNDPFC() {
        return sndpfc;
    }

    /**
     * Define el valor de la propiedad sndpfc.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDPFC }
     *     
     */
    public void setSNDPFC(SNDPFC value) {
        this.sndpfc = value;
    }

    /**
     * Obtiene el valor de la propiedad sndprn.
     * 
     * @return
     *     possible object is
     *     {@link SNDPRN }
     *     
     */
    public SNDPRN getSNDPRN() {
        return sndprn;
    }

    /**
     * Define el valor de la propiedad sndprn.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDPRN }
     *     
     */
    public void setSNDPRN(SNDPRN value) {
        this.sndprn = value;
    }

    /**
     * Obtiene el valor de la propiedad sndsad.
     * 
     * @return
     *     possible object is
     *     {@link SNDSAD }
     *     
     */
    public SNDSAD getSNDSAD() {
        return sndsad;
    }

    /**
     * Define el valor de la propiedad sndsad.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDSAD }
     *     
     */
    public void setSNDSAD(SNDSAD value) {
        this.sndsad = value;
    }

    /**
     * Obtiene el valor de la propiedad sndlad.
     * 
     * @return
     *     possible object is
     *     {@link SNDLAD }
     *     
     */
    public SNDLAD getSNDLAD() {
        return sndlad;
    }

    /**
     * Define el valor de la propiedad sndlad.
     * 
     * @param value
     *     allowed object is
     *     {@link SNDLAD }
     *     
     */
    public void setSNDLAD(SNDLAD value) {
        this.sndlad = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvpor.
     * 
     * @return
     *     possible object is
     *     {@link RCVPOR }
     *     
     */
    public RCVPOR getRCVPOR() {
        return rcvpor;
    }

    /**
     * Define el valor de la propiedad rcvpor.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVPOR }
     *     
     */
    public void setRCVPOR(RCVPOR value) {
        this.rcvpor = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvprt.
     * 
     * @return
     *     possible object is
     *     {@link RCVPRT }
     *     
     */
    public RCVPRT getRCVPRT() {
        return rcvprt;
    }

    /**
     * Define el valor de la propiedad rcvprt.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVPRT }
     *     
     */
    public void setRCVPRT(RCVPRT value) {
        this.rcvprt = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvpfc.
     * 
     * @return
     *     possible object is
     *     {@link RCVPFC }
     *     
     */
    public RCVPFC getRCVPFC() {
        return rcvpfc;
    }

    /**
     * Define el valor de la propiedad rcvpfc.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVPFC }
     *     
     */
    public void setRCVPFC(RCVPFC value) {
        this.rcvpfc = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvprn.
     * 
     * @return
     *     possible object is
     *     {@link RCVPRN }
     *     
     */
    public RCVPRN getRCVPRN() {
        return rcvprn;
    }

    /**
     * Define el valor de la propiedad rcvprn.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVPRN }
     *     
     */
    public void setRCVPRN(RCVPRN value) {
        this.rcvprn = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvsad.
     * 
     * @return
     *     possible object is
     *     {@link RCVSAD }
     *     
     */
    public RCVSAD getRCVSAD() {
        return rcvsad;
    }

    /**
     * Define el valor de la propiedad rcvsad.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVSAD }
     *     
     */
    public void setRCVSAD(RCVSAD value) {
        this.rcvsad = value;
    }

    /**
     * Obtiene el valor de la propiedad rcvlad.
     * 
     * @return
     *     possible object is
     *     {@link RCVLAD }
     *     
     */
    public RCVLAD getRCVLAD() {
        return rcvlad;
    }

    /**
     * Define el valor de la propiedad rcvlad.
     * 
     * @param value
     *     allowed object is
     *     {@link RCVLAD }
     *     
     */
    public void setRCVLAD(RCVLAD value) {
        this.rcvlad = value;
    }

    /**
     * Obtiene el valor de la propiedad credat.
     * 
     * @return
     *     possible object is
     *     {@link CREDAT }
     *     
     */
    public CREDAT getCREDAT() {
        return credat;
    }

    /**
     * Define el valor de la propiedad credat.
     * 
     * @param value
     *     allowed object is
     *     {@link CREDAT }
     *     
     */
    public void setCREDAT(CREDAT value) {
        this.credat = value;
    }

    /**
     * Obtiene el valor de la propiedad cretim.
     * 
     * @return
     *     possible object is
     *     {@link CRETIM }
     *     
     */
    public CRETIM getCRETIM() {
        return cretim;
    }

    /**
     * Define el valor de la propiedad cretim.
     * 
     * @param value
     *     allowed object is
     *     {@link CRETIM }
     *     
     */
    public void setCRETIM(CRETIM value) {
        this.cretim = value;
    }

    /**
     * Obtiene el valor de la propiedad refint.
     * 
     * @return
     *     possible object is
     *     {@link REFINT }
     *     
     */
    public REFINT getREFINT() {
        return refint;
    }

    /**
     * Define el valor de la propiedad refint.
     * 
     * @param value
     *     allowed object is
     *     {@link REFINT }
     *     
     */
    public void setREFINT(REFINT value) {
        this.refint = value;
    }

    /**
     * Obtiene el valor de la propiedad refgrp.
     * 
     * @return
     *     possible object is
     *     {@link REFGRP }
     *     
     */
    public REFGRP getREFGRP() {
        return refgrp;
    }

    /**
     * Define el valor de la propiedad refgrp.
     * 
     * @param value
     *     allowed object is
     *     {@link REFGRP }
     *     
     */
    public void setREFGRP(REFGRP value) {
        this.refgrp = value;
    }

    /**
     * Obtiene el valor de la propiedad refmes.
     * 
     * @return
     *     possible object is
     *     {@link REFMES }
     *     
     */
    public REFMES getREFMES() {
        return refmes;
    }

    /**
     * Define el valor de la propiedad refmes.
     * 
     * @param value
     *     allowed object is
     *     {@link REFMES }
     *     
     */
    public void setREFMES(REFMES value) {
        this.refmes = value;
    }

    /**
     * Obtiene el valor de la propiedad arckey.
     * 
     * @return
     *     possible object is
     *     {@link ARCKEY }
     *     
     */
    public ARCKEY getARCKEY() {
        return arckey;
    }

    /**
     * Define el valor de la propiedad arckey.
     * 
     * @param value
     *     allowed object is
     *     {@link ARCKEY }
     *     
     */
    public void setARCKEY(ARCKEY value) {
        this.arckey = value;
    }

    /**
     * Obtiene el valor de la propiedad serial.
     * 
     * @return
     *     possible object is
     *     {@link SERIAL }
     *     
     */
    public SERIAL getSERIAL() {
        return serial;
    }

    /**
     * Define el valor de la propiedad serial.
     * 
     * @param value
     *     allowed object is
     *     {@link SERIAL }
     *     
     */
    public void setSERIAL(SERIAL value) {
        this.serial = value;
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

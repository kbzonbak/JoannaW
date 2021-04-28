//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.02.19 a las 12:18:30 PM CLST 
//


package bbr.b2b.logistic.xml.lib_appointment;

import java.math.BigInteger;
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
 *         &lt;element name="facility_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="company_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="appt_nbr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="load_nbr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dock_type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="action_code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="preferred_dock_nbr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="planned_start_ts" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="estimated_units" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="carrier_info" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="trailer_nbr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="load_type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "facilityCode",
    "companyCode",
    "apptNbr",
    "loadNbr",
    "dockType",
    "actionCode",
    "preferredDockNbr",
    "plannedStartTs",
    "duration",
    "estimatedUnits",
    "carrierInfo",
    "trailerNbr",
    "loadType"
})
@XmlRootElement(name = "appointment")
public class Appointment {

    @XmlElement(name = "facility_code", required = true)
    protected String facilityCode;
    @XmlElement(name = "company_code", required = true)
    protected String companyCode;
    @XmlElement(name = "appt_nbr", required = true)
    protected String apptNbr;
    @XmlElement(name = "load_nbr", required = true)
    protected String loadNbr;
    @XmlElement(name = "dock_type", required = true)
    protected String dockType;
    @XmlElement(name = "action_code", required = true)
    protected String actionCode;
    @XmlElement(name = "preferred_dock_nbr", required = true)
    protected String preferredDockNbr;
    @XmlElement(name = "planned_start_ts", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedStartTs;
    @XmlElement(required = true)
    protected BigInteger duration;
    @XmlElement(name = "estimated_units", required = true)
    protected BigInteger estimatedUnits;
    @XmlElement(name = "carrier_info", required = true)
    protected String carrierInfo;
    @XmlElement(name = "trailer_nbr", required = true)
    protected String trailerNbr;
    @XmlElement(name = "load_type", required = true)
    protected String loadType;

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
     * Obtiene el valor de la propiedad apptNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApptNbr() {
        return apptNbr;
    }

    /**
     * Define el valor de la propiedad apptNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApptNbr(String value) {
        this.apptNbr = value;
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
     * Obtiene el valor de la propiedad dockType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDockType() {
        return dockType;
    }

    /**
     * Define el valor de la propiedad dockType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDockType(String value) {
        this.dockType = value;
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
     * Obtiene el valor de la propiedad preferredDockNbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredDockNbr() {
        return preferredDockNbr;
    }

    /**
     * Define el valor de la propiedad preferredDockNbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredDockNbr(String value) {
        this.preferredDockNbr = value;
    }

    /**
     * Obtiene el valor de la propiedad plannedStartTs.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedStartTs() {
        return plannedStartTs;
    }

    /**
     * Define el valor de la propiedad plannedStartTs.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedStartTs(XMLGregorianCalendar value) {
        this.plannedStartTs = value;
    }

    /**
     * Obtiene el valor de la propiedad duration.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuration() {
        return duration;
    }

    /**
     * Define el valor de la propiedad duration.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuration(BigInteger value) {
        this.duration = value;
    }

    /**
     * Obtiene el valor de la propiedad estimatedUnits.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEstimatedUnits() {
        return estimatedUnits;
    }

    /**
     * Define el valor de la propiedad estimatedUnits.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEstimatedUnits(BigInteger value) {
        this.estimatedUnits = value;
    }

    /**
     * Obtiene el valor de la propiedad carrierInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierInfo() {
        return carrierInfo;
    }

    /**
     * Define el valor de la propiedad carrierInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierInfo(String value) {
        this.carrierInfo = value;
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
     * Obtiene el valor de la propiedad loadType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadType() {
        return loadType;
    }

    /**
     * Define el valor de la propiedad loadType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadType(String value) {
        this.loadType = value;
    }

}

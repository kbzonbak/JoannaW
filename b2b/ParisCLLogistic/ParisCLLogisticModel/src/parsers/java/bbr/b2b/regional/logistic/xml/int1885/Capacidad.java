//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.02 at 11:47:32 AM CLST 
//


package bbr.b2b.regional.logistic.xml.int1885;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}cod_zona"/>
 *         &lt;element ref="{}cap_lunes"/>
 *         &lt;element ref="{}cap_martes"/>
 *         &lt;element ref="{}cap_miercoles"/>
 *         &lt;element ref="{}cap_jueves"/>
 *         &lt;element ref="{}cap_viernes"/>
 *         &lt;element ref="{}cap_sabado"/>
 *         &lt;element ref="{}cap_domingo"/>
 *         &lt;element ref="{}jerarquia"/>
 *         &lt;element ref="{}fecha_inicio"/>
 *         &lt;element ref="{}fecha_termino"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codZona",
    "capLunes",
    "capMartes",
    "capMiercoles",
    "capJueves",
    "capViernes",
    "capSabado",
    "capDomingo",
    "jerarquia",
    "fechaInicio",
    "fechaTermino"
})
@XmlRootElement(name = "capacidad")
public class Capacidad {

    @XmlElement(name = "cod_zona")
    protected int codZona;
    @XmlElement(name = "cap_lunes")
    protected int capLunes;
    @XmlElement(name = "cap_martes")
    protected int capMartes;
    @XmlElement(name = "cap_miercoles")
    protected int capMiercoles;
    @XmlElement(name = "cap_jueves")
    protected int capJueves;
    @XmlElement(name = "cap_viernes")
    protected int capViernes;
    @XmlElement(name = "cap_sabado")
    protected int capSabado;
    @XmlElement(name = "cap_domingo")
    protected int capDomingo;
    protected int jerarquia;
    @XmlElement(name = "fecha_inicio", required = true)
    protected String fechaInicio;
    @XmlElement(name = "fecha_termino", required = true)
    protected String fechaTermino;

    /**
     * Gets the value of the codZona property.
     * 
     */
    public int getCodZona() {
        return codZona;
    }

    /**
     * Sets the value of the codZona property.
     * 
     */
    public void setCodZona(int value) {
        this.codZona = value;
    }

    /**
     * Gets the value of the capLunes property.
     * 
     */
    public int getCapLunes() {
        return capLunes;
    }

    /**
     * Sets the value of the capLunes property.
     * 
     */
    public void setCapLunes(int value) {
        this.capLunes = value;
    }

    /**
     * Gets the value of the capMartes property.
     * 
     */
    public int getCapMartes() {
        return capMartes;
    }

    /**
     * Sets the value of the capMartes property.
     * 
     */
    public void setCapMartes(int value) {
        this.capMartes = value;
    }

    /**
     * Gets the value of the capMiercoles property.
     * 
     */
    public int getCapMiercoles() {
        return capMiercoles;
    }

    /**
     * Sets the value of the capMiercoles property.
     * 
     */
    public void setCapMiercoles(int value) {
        this.capMiercoles = value;
    }

    /**
     * Gets the value of the capJueves property.
     * 
     */
    public int getCapJueves() {
        return capJueves;
    }

    /**
     * Sets the value of the capJueves property.
     * 
     */
    public void setCapJueves(int value) {
        this.capJueves = value;
    }

    /**
     * Gets the value of the capViernes property.
     * 
     */
    public int getCapViernes() {
        return capViernes;
    }

    /**
     * Sets the value of the capViernes property.
     * 
     */
    public void setCapViernes(int value) {
        this.capViernes = value;
    }

    /**
     * Gets the value of the capSabado property.
     * 
     */
    public int getCapSabado() {
        return capSabado;
    }

    /**
     * Sets the value of the capSabado property.
     * 
     */
    public void setCapSabado(int value) {
        this.capSabado = value;
    }

    /**
     * Gets the value of the capDomingo property.
     * 
     */
    public int getCapDomingo() {
        return capDomingo;
    }

    /**
     * Sets the value of the capDomingo property.
     * 
     */
    public void setCapDomingo(int value) {
        this.capDomingo = value;
    }

    /**
     * Gets the value of the jerarquia property.
     * 
     */
    public int getJerarquia() {
        return jerarquia;
    }

    /**
     * Sets the value of the jerarquia property.
     * 
     */
    public void setJerarquia(int value) {
        this.jerarquia = value;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaInicio(String value) {
        this.fechaInicio = value;
    }

    /**
     * Gets the value of the fechaTermino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaTermino() {
        return fechaTermino;
    }

    /**
     * Sets the value of the fechaTermino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaTermino(String value) {
        this.fechaTermino = value;
    }

}


package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespuestaAdmisionTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaAdmisionTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/>
 *         &lt;element name="CodigoSucursal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreSucursal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cuartel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SDP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Movil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AbreviaturaCentro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoDelegacionDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreDelegacionDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DireccionDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoEncaminamiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrabarEnvio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroEnvio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComunaDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AbreviaturaServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdTransaccional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoAdmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaAdmisionTO", propOrder = {
    "extensionData",
    "codigoSucursal",
    "nombreSucursal",
    "cuartel",
    "sector",
    "sdp",
    "movil",
    "abreviaturaCentro",
    "codigoDelegacionDestino",
    "nombreDelegacionDestino",
    "direccionDestino",
    "codigoEncaminamiento",
    "grabarEnvio",
    "numeroEnvio",
    "comunaDestino",
    "abreviaturaServicio",
    "idTransaccional",
    "codigoAdmision"
})
public class RespuestaAdmisionTO {

    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "CodigoSucursal")
    protected String codigoSucursal;
    @XmlElement(name = "NombreSucursal")
    protected String nombreSucursal;
    @XmlElement(name = "Cuartel")
    protected String cuartel;
    @XmlElement(name = "Sector")
    protected String sector;
    @XmlElement(name = "SDP")
    protected String sdp;
    @XmlElement(name = "Movil")
    protected String movil;
    @XmlElement(name = "AbreviaturaCentro")
    protected String abreviaturaCentro;
    @XmlElement(name = "CodigoDelegacionDestino")
    protected String codigoDelegacionDestino;
    @XmlElement(name = "NombreDelegacionDestino")
    protected String nombreDelegacionDestino;
    @XmlElement(name = "DireccionDestino")
    protected String direccionDestino;
    @XmlElement(name = "CodigoEncaminamiento")
    protected String codigoEncaminamiento;
    @XmlElement(name = "GrabarEnvio")
    protected String grabarEnvio;
    @XmlElement(name = "NumeroEnvio")
    protected String numeroEnvio;
    @XmlElement(name = "ComunaDestino")
    protected String comunaDestino;
    @XmlElement(name = "AbreviaturaServicio")
    protected String abreviaturaServicio;
    @XmlElement(name = "IdTransaccional")
    protected String idTransaccional;
    @XmlElement(name = "CodigoAdmision")
    protected String codigoAdmision;

    /**
     * Gets the value of the extensionData property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionDataObject }
     *     
     */
    public ExtensionDataObject getExtensionData() {
        return extensionData;
    }

    /**
     * Sets the value of the extensionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionDataObject }
     *     
     */
    public void setExtensionData(ExtensionDataObject value) {
        this.extensionData = value;
    }

    /**
     * Gets the value of the codigoSucursal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * Sets the value of the codigoSucursal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSucursal(String value) {
        this.codigoSucursal = value;
    }

    /**
     * Gets the value of the nombreSucursal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    /**
     * Sets the value of the nombreSucursal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreSucursal(String value) {
        this.nombreSucursal = value;
    }

    /**
     * Gets the value of the cuartel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuartel() {
        return cuartel;
    }

    /**
     * Sets the value of the cuartel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuartel(String value) {
        this.cuartel = value;
    }

    /**
     * Gets the value of the sector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSector(String value) {
        this.sector = value;
    }

    /**
     * Gets the value of the sdp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSDP() {
        return sdp;
    }

    /**
     * Sets the value of the sdp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSDP(String value) {
        this.sdp = value;
    }

    /**
     * Gets the value of the movil property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovil() {
        return movil;
    }

    /**
     * Sets the value of the movil property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovil(String value) {
        this.movil = value;
    }

    /**
     * Gets the value of the abreviaturaCentro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbreviaturaCentro() {
        return abreviaturaCentro;
    }

    /**
     * Sets the value of the abreviaturaCentro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbreviaturaCentro(String value) {
        this.abreviaturaCentro = value;
    }

    /**
     * Gets the value of the codigoDelegacionDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoDelegacionDestino() {
        return codigoDelegacionDestino;
    }

    /**
     * Sets the value of the codigoDelegacionDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoDelegacionDestino(String value) {
        this.codigoDelegacionDestino = value;
    }

    /**
     * Gets the value of the nombreDelegacionDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDelegacionDestino() {
        return nombreDelegacionDestino;
    }

    /**
     * Sets the value of the nombreDelegacionDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDelegacionDestino(String value) {
        this.nombreDelegacionDestino = value;
    }

    /**
     * Gets the value of the direccionDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionDestino() {
        return direccionDestino;
    }

    /**
     * Sets the value of the direccionDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionDestino(String value) {
        this.direccionDestino = value;
    }

    /**
     * Gets the value of the codigoEncaminamiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoEncaminamiento() {
        return codigoEncaminamiento;
    }

    /**
     * Sets the value of the codigoEncaminamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoEncaminamiento(String value) {
        this.codigoEncaminamiento = value;
    }

    /**
     * Gets the value of the grabarEnvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrabarEnvio() {
        return grabarEnvio;
    }

    /**
     * Sets the value of the grabarEnvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrabarEnvio(String value) {
        this.grabarEnvio = value;
    }

    /**
     * Gets the value of the numeroEnvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroEnvio() {
        return numeroEnvio;
    }

    /**
     * Sets the value of the numeroEnvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroEnvio(String value) {
        this.numeroEnvio = value;
    }

    /**
     * Gets the value of the comunaDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComunaDestino() {
        return comunaDestino;
    }

    /**
     * Sets the value of the comunaDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComunaDestino(String value) {
        this.comunaDestino = value;
    }

    /**
     * Gets the value of the abreviaturaServicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbreviaturaServicio() {
        return abreviaturaServicio;
    }

    /**
     * Sets the value of the abreviaturaServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbreviaturaServicio(String value) {
        this.abreviaturaServicio = value;
    }

    /**
     * Gets the value of the idTransaccional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTransaccional() {
        return idTransaccional;
    }

    /**
     * Sets the value of the idTransaccional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTransaccional(String value) {
        this.idTransaccional = value;
    }

    /**
     * Gets the value of the codigoAdmision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoAdmision() {
        return codigoAdmision;
    }

    /**
     * Sets the value of the codigoAdmision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoAdmision(String value) {
        this.codigoAdmision = value;
    }

}

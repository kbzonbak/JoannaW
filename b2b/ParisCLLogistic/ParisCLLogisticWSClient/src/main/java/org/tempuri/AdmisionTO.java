
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdmisionTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdmisionTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExtensionData" type="{http://tempuri.org/}ExtensionDataObject" minOccurs="0"/>
 *         &lt;element name="CodigoAdmision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClienteRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CentroRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DireccionRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaisRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoPostalRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComunaRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RutRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaContactoRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TelefonoContactoRemitente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClienteDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CentroDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NombreDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DireccionDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaisDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoPostalDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComunaDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RutDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonaContactoDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TelefonoContactoDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroTotalPiezas" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Kilos" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Volumen" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="NumeroReferencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImporteReembolso" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ImporteValorDeclarado" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="TipoPortes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Observaciones2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoMercancia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DevolucionConforme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroDocumentos" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PagoSeguro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdmisionTO", propOrder = {
    "extensionData",
    "codigoAdmision",
    "clienteRemitente",
    "centroRemitente",
    "nombreRemitente",
    "direccionRemitente",
    "paisRemitente",
    "codigoPostalRemitente",
    "comunaRemitente",
    "rutRemitente",
    "personaContactoRemitente",
    "telefonoContactoRemitente",
    "clienteDestinatario",
    "centroDestinatario",
    "nombreDestinatario",
    "direccionDestinatario",
    "paisDestinatario",
    "codigoPostalDestinatario",
    "comunaDestinatario",
    "rutDestinatario",
    "personaContactoDestinatario",
    "telefonoContactoDestinatario",
    "codigoServicio",
    "numeroTotalPiezas",
    "kilos",
    "volumen",
    "numeroReferencia",
    "importeReembolso",
    "importeValorDeclarado",
    "tipoPortes",
    "observaciones",
    "observaciones2",
    "emailDestino",
    "tipoMercancia",
    "devolucionConforme",
    "numeroDocumentos",
    "pagoSeguro"
})
public class AdmisionTO {

    @XmlElement(name = "ExtensionData")
    protected ExtensionDataObject extensionData;
    @XmlElement(name = "CodigoAdmision")
    protected String codigoAdmision;
    @XmlElement(name = "ClienteRemitente")
    protected String clienteRemitente;
    @XmlElement(name = "CentroRemitente")
    protected String centroRemitente;
    @XmlElement(name = "NombreRemitente")
    protected String nombreRemitente;
    @XmlElement(name = "DireccionRemitente")
    protected String direccionRemitente;
    @XmlElement(name = "PaisRemitente")
    protected String paisRemitente;
    @XmlElement(name = "CodigoPostalRemitente")
    protected String codigoPostalRemitente;
    @XmlElement(name = "ComunaRemitente")
    protected String comunaRemitente;
    @XmlElement(name = "RutRemitente")
    protected String rutRemitente;
    @XmlElement(name = "PersonaContactoRemitente")
    protected String personaContactoRemitente;
    @XmlElement(name = "TelefonoContactoRemitente")
    protected String telefonoContactoRemitente;
    @XmlElement(name = "ClienteDestinatario")
    protected String clienteDestinatario;
    @XmlElement(name = "CentroDestinatario")
    protected String centroDestinatario;
    @XmlElement(name = "NombreDestinatario")
    protected String nombreDestinatario;
    @XmlElement(name = "DireccionDestinatario")
    protected String direccionDestinatario;
    @XmlElement(name = "PaisDestinatario")
    protected String paisDestinatario;
    @XmlElement(name = "CodigoPostalDestinatario")
    protected String codigoPostalDestinatario;
    @XmlElement(name = "ComunaDestinatario")
    protected String comunaDestinatario;
    @XmlElement(name = "RutDestinatario")
    protected String rutDestinatario;
    @XmlElement(name = "PersonaContactoDestinatario")
    protected String personaContactoDestinatario;
    @XmlElement(name = "TelefonoContactoDestinatario")
    protected String telefonoContactoDestinatario;
    @XmlElement(name = "CodigoServicio")
    protected String codigoServicio;
    @XmlElement(name = "NumeroTotalPiezas")
    protected int numeroTotalPiezas;
    @XmlElement(name = "Kilos")
    protected double kilos;
    @XmlElement(name = "Volumen")
    protected double volumen;
    @XmlElement(name = "NumeroReferencia")
    protected String numeroReferencia;
    @XmlElement(name = "ImporteReembolso")
    protected long importeReembolso;
    @XmlElement(name = "ImporteValorDeclarado")
    protected long importeValorDeclarado;
    @XmlElement(name = "TipoPortes")
    protected String tipoPortes;
    @XmlElement(name = "Observaciones")
    protected String observaciones;
    @XmlElement(name = "Observaciones2")
    protected String observaciones2;
    @XmlElement(name = "EmailDestino")
    protected String emailDestino;
    @XmlElement(name = "TipoMercancia")
    protected String tipoMercancia;
    @XmlElement(name = "DevolucionConforme")
    protected String devolucionConforme;
    @XmlElement(name = "NumeroDocumentos")
    protected long numeroDocumentos;
    @XmlElement(name = "PagoSeguro")
    protected String pagoSeguro;

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

    /**
     * Gets the value of the clienteRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClienteRemitente() {
        return clienteRemitente;
    }

    /**
     * Sets the value of the clienteRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClienteRemitente(String value) {
        this.clienteRemitente = value;
    }

    /**
     * Gets the value of the centroRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroRemitente() {
        return centroRemitente;
    }

    /**
     * Sets the value of the centroRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroRemitente(String value) {
        this.centroRemitente = value;
    }

    /**
     * Gets the value of the nombreRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreRemitente() {
        return nombreRemitente;
    }

    /**
     * Sets the value of the nombreRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreRemitente(String value) {
        this.nombreRemitente = value;
    }

    /**
     * Gets the value of the direccionRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionRemitente() {
        return direccionRemitente;
    }

    /**
     * Sets the value of the direccionRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionRemitente(String value) {
        this.direccionRemitente = value;
    }

    /**
     * Gets the value of the paisRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisRemitente() {
        return paisRemitente;
    }

    /**
     * Sets the value of the paisRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisRemitente(String value) {
        this.paisRemitente = value;
    }

    /**
     * Gets the value of the codigoPostalRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPostalRemitente() {
        return codigoPostalRemitente;
    }

    /**
     * Sets the value of the codigoPostalRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPostalRemitente(String value) {
        this.codigoPostalRemitente = value;
    }

    /**
     * Gets the value of the comunaRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComunaRemitente() {
        return comunaRemitente;
    }

    /**
     * Sets the value of the comunaRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComunaRemitente(String value) {
        this.comunaRemitente = value;
    }

    /**
     * Gets the value of the rutRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutRemitente() {
        return rutRemitente;
    }

    /**
     * Sets the value of the rutRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutRemitente(String value) {
        this.rutRemitente = value;
    }

    /**
     * Gets the value of the personaContactoRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonaContactoRemitente() {
        return personaContactoRemitente;
    }

    /**
     * Sets the value of the personaContactoRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonaContactoRemitente(String value) {
        this.personaContactoRemitente = value;
    }

    /**
     * Gets the value of the telefonoContactoRemitente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoContactoRemitente() {
        return telefonoContactoRemitente;
    }

    /**
     * Sets the value of the telefonoContactoRemitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoContactoRemitente(String value) {
        this.telefonoContactoRemitente = value;
    }

    /**
     * Gets the value of the clienteDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClienteDestinatario() {
        return clienteDestinatario;
    }

    /**
     * Sets the value of the clienteDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClienteDestinatario(String value) {
        this.clienteDestinatario = value;
    }

    /**
     * Gets the value of the centroDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroDestinatario() {
        return centroDestinatario;
    }

    /**
     * Sets the value of the centroDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroDestinatario(String value) {
        this.centroDestinatario = value;
    }

    /**
     * Gets the value of the nombreDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    /**
     * Sets the value of the nombreDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDestinatario(String value) {
        this.nombreDestinatario = value;
    }

    /**
     * Gets the value of the direccionDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionDestinatario() {
        return direccionDestinatario;
    }

    /**
     * Sets the value of the direccionDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionDestinatario(String value) {
        this.direccionDestinatario = value;
    }

    /**
     * Gets the value of the paisDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisDestinatario() {
        return paisDestinatario;
    }

    /**
     * Sets the value of the paisDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisDestinatario(String value) {
        this.paisDestinatario = value;
    }

    /**
     * Gets the value of the codigoPostalDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPostalDestinatario() {
        return codigoPostalDestinatario;
    }

    /**
     * Sets the value of the codigoPostalDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPostalDestinatario(String value) {
        this.codigoPostalDestinatario = value;
    }

    /**
     * Gets the value of the comunaDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComunaDestinatario() {
        return comunaDestinatario;
    }

    /**
     * Sets the value of the comunaDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComunaDestinatario(String value) {
        this.comunaDestinatario = value;
    }

    /**
     * Gets the value of the rutDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutDestinatario() {
        return rutDestinatario;
    }

    /**
     * Sets the value of the rutDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutDestinatario(String value) {
        this.rutDestinatario = value;
    }

    /**
     * Gets the value of the personaContactoDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonaContactoDestinatario() {
        return personaContactoDestinatario;
    }

    /**
     * Sets the value of the personaContactoDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonaContactoDestinatario(String value) {
        this.personaContactoDestinatario = value;
    }

    /**
     * Gets the value of the telefonoContactoDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoContactoDestinatario() {
        return telefonoContactoDestinatario;
    }

    /**
     * Sets the value of the telefonoContactoDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoContactoDestinatario(String value) {
        this.telefonoContactoDestinatario = value;
    }

    /**
     * Gets the value of the codigoServicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoServicio() {
        return codigoServicio;
    }

    /**
     * Sets the value of the codigoServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoServicio(String value) {
        this.codigoServicio = value;
    }

    /**
     * Gets the value of the numeroTotalPiezas property.
     * 
     */
    public int getNumeroTotalPiezas() {
        return numeroTotalPiezas;
    }

    /**
     * Sets the value of the numeroTotalPiezas property.
     * 
     */
    public void setNumeroTotalPiezas(int value) {
        this.numeroTotalPiezas = value;
    }

    /**
     * Gets the value of the kilos property.
     * 
     */
    public double getKilos() {
        return kilos;
    }

    /**
     * Sets the value of the kilos property.
     * 
     */
    public void setKilos(double value) {
        this.kilos = value;
    }

    /**
     * Gets the value of the volumen property.
     * 
     */
    public double getVolumen() {
        return volumen;
    }

    /**
     * Sets the value of the volumen property.
     * 
     */
    public void setVolumen(double value) {
        this.volumen = value;
    }

    /**
     * Gets the value of the numeroReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroReferencia() {
        return numeroReferencia;
    }

    /**
     * Sets the value of the numeroReferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroReferencia(String value) {
        this.numeroReferencia = value;
    }

    /**
     * Gets the value of the importeReembolso property.
     * 
     */
    public long getImporteReembolso() {
        return importeReembolso;
    }

    /**
     * Sets the value of the importeReembolso property.
     * 
     */
    public void setImporteReembolso(long value) {
        this.importeReembolso = value;
    }

    /**
     * Gets the value of the importeValorDeclarado property.
     * 
     */
    public long getImporteValorDeclarado() {
        return importeValorDeclarado;
    }

    /**
     * Sets the value of the importeValorDeclarado property.
     * 
     */
    public void setImporteValorDeclarado(long value) {
        this.importeValorDeclarado = value;
    }

    /**
     * Gets the value of the tipoPortes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPortes() {
        return tipoPortes;
    }

    /**
     * Sets the value of the tipoPortes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPortes(String value) {
        this.tipoPortes = value;
    }

    /**
     * Gets the value of the observaciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the value of the observaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Gets the value of the observaciones2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones2() {
        return observaciones2;
    }

    /**
     * Sets the value of the observaciones2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones2(String value) {
        this.observaciones2 = value;
    }

    /**
     * Gets the value of the emailDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailDestino() {
        return emailDestino;
    }

    /**
     * Sets the value of the emailDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailDestino(String value) {
        this.emailDestino = value;
    }

    /**
     * Gets the value of the tipoMercancia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMercancia() {
        return tipoMercancia;
    }

    /**
     * Sets the value of the tipoMercancia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMercancia(String value) {
        this.tipoMercancia = value;
    }

    /**
     * Gets the value of the devolucionConforme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDevolucionConforme() {
        return devolucionConforme;
    }

    /**
     * Sets the value of the devolucionConforme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDevolucionConforme(String value) {
        this.devolucionConforme = value;
    }

    /**
     * Gets the value of the numeroDocumentos property.
     * 
     */
    public long getNumeroDocumentos() {
        return numeroDocumentos;
    }

    /**
     * Sets the value of the numeroDocumentos property.
     * 
     */
    public void setNumeroDocumentos(long value) {
        this.numeroDocumentos = value;
    }

    /**
     * Gets the value of the pagoSeguro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPagoSeguro() {
        return pagoSeguro;
    }

    /**
     * Sets the value of the pagoSeguro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPagoSeguro(String value) {
        this.pagoSeguro = value;
    }

}

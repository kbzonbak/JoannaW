
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenerarIntegracionAsistidaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenerarIntegracionAsistidaRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoProducto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoServicio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="comunaOrigen" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroTCC" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="referenciaEnvio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referenciaEnvio2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="montoCobrar" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="eoc" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Remitente" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}RemitenteType"/>
 *         &lt;element name="Destinatario" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}DestinatarioType"/>
 *         &lt;element name="Direccion" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}DireccionType"/>
 *         &lt;element name="DireccionDevolucion" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}DireccionType"/>
 *         &lt;element name="Pieza" type="{http://ws.chilexpress.cl/OSB/INTERNO/COM/GenerarIntegracionAsistidaReq}PiezaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenerarIntegracionAsistidaRequestType", propOrder = {
    "codigoProducto",
    "codigoServicio",
    "comunaOrigen",
    "numeroTCC",
    "referenciaEnvio",
    "referenciaEnvio2",
    "montoCobrar",
    "eoc",
    "remitente",
    "destinatario",
    "direccion",
    "direccionDevolucion",
    "pieza"
})
public class GenerarIntegracionAsistidaRequestType {

    protected int codigoProducto;
    protected int codigoServicio;
    @XmlElement(required = true)
    protected String comunaOrigen;
    protected int numeroTCC;
    @XmlElement(required = true)
    protected String referenciaEnvio;
    @XmlElement(required = true)
    protected String referenciaEnvio2;
    @XmlElement(defaultValue = "0")
    protected int montoCobrar;
    protected int eoc;
    @XmlElement(name = "Remitente", required = true)
    protected RemitenteType remitente;
    @XmlElement(name = "Destinatario", required = true)
    protected DestinatarioType destinatario;
    @XmlElement(name = "Direccion", required = true)
    protected DireccionType direccion;
    @XmlElement(name = "DireccionDevolucion", required = true)
    protected DireccionType direccionDevolucion;
    @XmlElement(name = "Pieza", required = true)
    protected PiezaType pieza;

    /**
     * Gets the value of the codigoProducto property.
     * 
     */
    public int getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Sets the value of the codigoProducto property.
     * 
     */
    public void setCodigoProducto(int value) {
        this.codigoProducto = value;
    }

    /**
     * Gets the value of the codigoServicio property.
     * 
     */
    public int getCodigoServicio() {
        return codigoServicio;
    }

    /**
     * Sets the value of the codigoServicio property.
     * 
     */
    public void setCodigoServicio(int value) {
        this.codigoServicio = value;
    }

    /**
     * Gets the value of the comunaOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComunaOrigen() {
        return comunaOrigen;
    }

    /**
     * Sets the value of the comunaOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComunaOrigen(String value) {
        this.comunaOrigen = value;
    }

    /**
     * Gets the value of the numeroTCC property.
     * 
     */
    public int getNumeroTCC() {
        return numeroTCC;
    }

    /**
     * Sets the value of the numeroTCC property.
     * 
     */
    public void setNumeroTCC(int value) {
        this.numeroTCC = value;
    }

    /**
     * Gets the value of the referenciaEnvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaEnvio() {
        return referenciaEnvio;
    }

    /**
     * Sets the value of the referenciaEnvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaEnvio(String value) {
        this.referenciaEnvio = value;
    }

    /**
     * Gets the value of the referenciaEnvio2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaEnvio2() {
        return referenciaEnvio2;
    }

    /**
     * Sets the value of the referenciaEnvio2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaEnvio2(String value) {
        this.referenciaEnvio2 = value;
    }

    /**
     * Gets the value of the montoCobrar property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public int getMontoCobrar() {
        return montoCobrar;
    }

    /**
     * Sets the value of the montoCobrar property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoCobrar(int value) {
        this.montoCobrar = value;
    }

    /**
     * Gets the value of the eoc property.
     * 
     */
    public int isEoc() {
        return eoc;
    }

    /**
     * Sets the value of the eoc property.
     * 
     */
    public void setEoc(int value) {
        this.eoc = value;
    }

    /**
     * Gets the value of the remitente property.
     * 
     * @return
     *     possible object is
     *     {@link RemitenteType }
     *     
     */
    public RemitenteType getRemitente() {
        return remitente;
    }

    /**
     * Sets the value of the remitente property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemitenteType }
     *     
     */
    public void setRemitente(RemitenteType value) {
        this.remitente = value;
    }

    /**
     * Gets the value of the destinatario property.
     * 
     * @return
     *     possible object is
     *     {@link DestinatarioType }
     *     
     */
    public DestinatarioType getDestinatario() {
        return destinatario;
    }

    /**
     * Sets the value of the destinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinatarioType }
     *     
     */
    public void setDestinatario(DestinatarioType value) {
        this.destinatario = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link DireccionType }
     *     
     */
    public DireccionType getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DireccionType }
     *     
     */
    public void setDireccion(DireccionType value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the direccionDevolucion property.
     * 
     * @return
     *     possible object is
     *     {@link DireccionType }
     *     
     */
    public DireccionType getDireccionDevolucion() {
        return direccionDevolucion;
    }

    /**
     * Sets the value of the direccionDevolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DireccionType }
     *     
     */
    public void setDireccionDevolucion(DireccionType value) {
        this.direccionDevolucion = value;
    }

    /**
     * Gets the value of the pieza property.
     * 
     * @return
     *     possible object is
     *     {@link PiezaType }
     *     
     */
    public PiezaType getPieza() {
        return pieza;
    }

    /**
     * Sets the value of the pieza property.
     * 
     * @param value
     *     allowed object is
     *     {@link PiezaType }
     *     
     */
    public void setPieza(PiezaType value) {
        this.pieza = value;
    }

}

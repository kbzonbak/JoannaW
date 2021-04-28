
package cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosEtiquetaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosEtiquetaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroOT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numeroOTPadre" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="glosaProductoOT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="glosaServicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreDestinatario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroGuia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="glosaCobertura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoRegion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="adicionales" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ancho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="largo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="xmlSalidaEpl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="informacionProducto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GlosaCortaProductoOT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaImpresion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroBulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centroDistribucionDestino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="imagenEtiqueta" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosEtiquetaType", propOrder = {
    "numeroOT",
    "numeroOTPadre",
    "glosaProductoOT",
    "glosaServicio",
    "nombreDestinatario",
    "numeroGuia",
    "glosaCobertura",
    "direccion",
    "codigoRegion",
    "adicionales",
    "peso",
    "alto",
    "ancho",
    "largo",
    "xmlSalidaEpl",
    "barcode",
    "referencia2",
    "informacionProducto",
    "glosaCortaProductoOT",
    "fechaImpresion",
    "numeroBulto",
    "centroDistribucionDestino",
    "imagenEtiqueta"
})
public class DatosEtiquetaType {

    protected BigDecimal numeroOT;
    protected BigDecimal numeroOTPadre;
    protected String glosaProductoOT;
    protected String glosaServicio;
    protected String nombreDestinatario;
    protected String numeroGuia;
    protected String glosaCobertura;
    protected String direccion;
    protected String codigoRegion;
    protected String adicionales;
    protected String peso;
    protected String alto;
    protected String ancho;
    protected String largo;
    protected String xmlSalidaEpl;
    protected String barcode;
    protected String referencia2;
    protected String informacionProducto;
    @XmlElement(name = "GlosaCortaProductoOT")
    protected String glosaCortaProductoOT;
    protected String fechaImpresion;
    protected String numeroBulto;
    protected String centroDistribucionDestino;
    protected byte[] imagenEtiqueta;

    /**
     * Gets the value of the numeroOT property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroOT() {
        return numeroOT;
    }

    /**
     * Sets the value of the numeroOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroOT(BigDecimal value) {
        this.numeroOT = value;
    }

    /**
     * Gets the value of the numeroOTPadre property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNumeroOTPadre() {
        return numeroOTPadre;
    }

    /**
     * Sets the value of the numeroOTPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNumeroOTPadre(BigDecimal value) {
        this.numeroOTPadre = value;
    }

    /**
     * Gets the value of the glosaProductoOT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlosaProductoOT() {
        return glosaProductoOT;
    }

    /**
     * Sets the value of the glosaProductoOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlosaProductoOT(String value) {
        this.glosaProductoOT = value;
    }

    /**
     * Gets the value of the glosaServicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlosaServicio() {
        return glosaServicio;
    }

    /**
     * Sets the value of the glosaServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlosaServicio(String value) {
        this.glosaServicio = value;
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
     * Gets the value of the numeroGuia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroGuia() {
        return numeroGuia;
    }

    /**
     * Sets the value of the numeroGuia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroGuia(String value) {
        this.numeroGuia = value;
    }

    /**
     * Gets the value of the glosaCobertura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlosaCobertura() {
        return glosaCobertura;
    }

    /**
     * Sets the value of the glosaCobertura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlosaCobertura(String value) {
        this.glosaCobertura = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the codigoRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoRegion() {
        return codigoRegion;
    }

    /**
     * Sets the value of the codigoRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoRegion(String value) {
        this.codigoRegion = value;
    }

    /**
     * Gets the value of the adicionales property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdicionales() {
        return adicionales;
    }

    /**
     * Sets the value of the adicionales property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdicionales(String value) {
        this.adicionales = value;
    }

    /**
     * Gets the value of the peso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeso() {
        return peso;
    }

    /**
     * Sets the value of the peso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeso(String value) {
        this.peso = value;
    }

    /**
     * Gets the value of the alto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlto() {
        return alto;
    }

    /**
     * Sets the value of the alto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlto(String value) {
        this.alto = value;
    }

    /**
     * Gets the value of the ancho property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAncho() {
        return ancho;
    }

    /**
     * Sets the value of the ancho property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAncho(String value) {
        this.ancho = value;
    }

    /**
     * Gets the value of the largo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLargo() {
        return largo;
    }

    /**
     * Sets the value of the largo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLargo(String value) {
        this.largo = value;
    }

    /**
     * Gets the value of the xmlSalidaEpl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlSalidaEpl() {
        return xmlSalidaEpl;
    }

    /**
     * Sets the value of the xmlSalidaEpl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlSalidaEpl(String value) {
        this.xmlSalidaEpl = value;
    }

    /**
     * Gets the value of the barcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the value of the barcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcode(String value) {
        this.barcode = value;
    }

    /**
     * Gets the value of the referencia2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia2() {
        return referencia2;
    }

    /**
     * Sets the value of the referencia2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia2(String value) {
        this.referencia2 = value;
    }

    /**
     * Gets the value of the informacionProducto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformacionProducto() {
        return informacionProducto;
    }

    /**
     * Sets the value of the informacionProducto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformacionProducto(String value) {
        this.informacionProducto = value;
    }

    /**
     * Gets the value of the glosaCortaProductoOT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlosaCortaProductoOT() {
        return glosaCortaProductoOT;
    }

    /**
     * Sets the value of the glosaCortaProductoOT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlosaCortaProductoOT(String value) {
        this.glosaCortaProductoOT = value;
    }

    /**
     * Gets the value of the fechaImpresion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaImpresion() {
        return fechaImpresion;
    }

    /**
     * Sets the value of the fechaImpresion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaImpresion(String value) {
        this.fechaImpresion = value;
    }

    /**
     * Gets the value of the numeroBulto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroBulto() {
        return numeroBulto;
    }

    /**
     * Sets the value of the numeroBulto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroBulto(String value) {
        this.numeroBulto = value;
    }

    /**
     * Gets the value of the centroDistribucionDestino property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroDistribucionDestino() {
        return centroDistribucionDestino;
    }

    /**
     * Sets the value of the centroDistribucionDestino property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroDistribucionDestino(String value) {
        this.centroDistribucionDestino = value;
    }

    /**
     * Gets the value of the imagenEtiqueta property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImagenEtiqueta() {
        return imagenEtiqueta;
    }

    /**
     * Sets the value of the imagenEtiqueta property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImagenEtiqueta(byte[] value) {
        this.imagenEtiqueta = ((byte[]) value);
    }

}

//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2019.06.24 a las 09:55:42 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.OC_consolidado;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="numeroconsolidado" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="shipto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="edi_data"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="senderIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="recipientIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="comprador"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="unidad_negocio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="vendedor"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="clientes"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="cliente" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="telefono_alternativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="numcalle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="numdepto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="numcasa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="comuna" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="locales"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="local"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ordenes"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="orden"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="digito_verificador_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="estado_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nosolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="f12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cod_seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="nro_boleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="fecha_vigencia" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="codigo_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="fecha_compromiso" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="fecha_vto" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                             &lt;element name="forma_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="responsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="detalles"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                       &lt;element name="detalle"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                                                 &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                                                 &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="dv_codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="cant_sol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="cant_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                                                 &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_producto"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                           &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="cargos_prod"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                                           &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="predistribuciones"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="predistribucion"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "numeroconsolidado",
    "nombreportal",
    "ticket",
    "shipto",
    "fecha",
    "ediData",
    "comprador",
    "vendedor",
    "clientes",
    "locales",
    "ordenes",
    "predistribuciones"
})
@XmlRootElement(name = "consolidado")
public class Consolidado {

    @XmlElement(required = true)
    protected String numeroconsolidado;
    @XmlElement(required = true)
    protected String nombreportal;
    protected String ticket;
    @XmlElement(required = true)
    protected String shipto;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fecha;
    @XmlElement(name = "edi_data", required = true)
    protected Consolidado.EdiData ediData;
    @XmlElement(required = true)
    protected Consolidado.Comprador comprador;
    @XmlElement(required = true)
    protected Consolidado.Vendedor vendedor;
    @XmlElement(required = true)
    protected Consolidado.Clientes clientes;
    @XmlElement(required = true)
    protected Consolidado.Locales locales;
    @XmlElement(required = true)
    protected Consolidado.Ordenes ordenes;
    @XmlElement(required = true)
    protected Consolidado.Predistribuciones predistribuciones;

    /**
     * Obtiene el valor de la propiedad numeroconsolidado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroconsolidado() {
        return numeroconsolidado;
    }

    /**
     * Define el valor de la propiedad numeroconsolidado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroconsolidado(String value) {
        this.numeroconsolidado = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreportal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreportal() {
        return nombreportal;
    }

    /**
     * Define el valor de la propiedad nombreportal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreportal(String value) {
        this.nombreportal = value;
    }

    /**
     * Obtiene el valor de la propiedad ticket.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Define el valor de la propiedad ticket.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicket(String value) {
        this.ticket = value;
    }

    /**
     * Obtiene el valor de la propiedad shipto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipto() {
        return shipto;
    }

    /**
     * Define el valor de la propiedad shipto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipto(String value) {
        this.shipto = value;
    }

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad ediData.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.EdiData }
     *     
     */
    public Consolidado.EdiData getEdiData() {
        return ediData;
    }

    /**
     * Define el valor de la propiedad ediData.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.EdiData }
     *     
     */
    public void setEdiData(Consolidado.EdiData value) {
        this.ediData = value;
    }

    /**
     * Obtiene el valor de la propiedad comprador.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Comprador }
     *     
     */
    public Consolidado.Comprador getComprador() {
        return comprador;
    }

    /**
     * Define el valor de la propiedad comprador.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Comprador }
     *     
     */
    public void setComprador(Consolidado.Comprador value) {
        this.comprador = value;
    }

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Vendedor }
     *     
     */
    public Consolidado.Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Vendedor }
     *     
     */
    public void setVendedor(Consolidado.Vendedor value) {
        this.vendedor = value;
    }

    /**
     * Obtiene el valor de la propiedad clientes.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Clientes }
     *     
     */
    public Consolidado.Clientes getClientes() {
        return clientes;
    }

    /**
     * Define el valor de la propiedad clientes.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Clientes }
     *     
     */
    public void setClientes(Consolidado.Clientes value) {
        this.clientes = value;
    }

    /**
     * Obtiene el valor de la propiedad locales.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Locales }
     *     
     */
    public Consolidado.Locales getLocales() {
        return locales;
    }

    /**
     * Define el valor de la propiedad locales.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Locales }
     *     
     */
    public void setLocales(Consolidado.Locales value) {
        this.locales = value;
    }

    /**
     * Obtiene el valor de la propiedad ordenes.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Ordenes }
     *     
     */
    public Consolidado.Ordenes getOrdenes() {
        return ordenes;
    }

    /**
     * Define el valor de la propiedad ordenes.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Ordenes }
     *     
     */
    public void setOrdenes(Consolidado.Ordenes value) {
        this.ordenes = value;
    }

    /**
     * Obtiene el valor de la propiedad predistribuciones.
     * 
     * @return
     *     possible object is
     *     {@link Consolidado.Predistribuciones }
     *     
     */
    public Consolidado.Predistribuciones getPredistribuciones() {
        return predistribuciones;
    }

    /**
     * Define el valor de la propiedad predistribuciones.
     * 
     * @param value
     *     allowed object is
     *     {@link Consolidado.Predistribuciones }
     *     
     */
    public void setPredistribuciones(Consolidado.Predistribuciones value) {
        this.predistribuciones = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="cliente" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="telefono_alternativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="numcalle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="numdepto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="numcasa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="comuna" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "cliente"
    })
    public static class Clientes {

        protected List<Consolidado.Clientes.Cliente> cliente;

        /**
         * Gets the value of the cliente property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cliente property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCliente().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Consolidado.Clientes.Cliente }
         * 
         * 
         */
        public List<Consolidado.Clientes.Cliente> getCliente() {
            if (cliente == null) {
                cliente = new ArrayList<Consolidado.Clientes.Cliente>();
            }
            return this.cliente;
        }


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
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="telefono_alternativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="numcalle" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="numdepto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="numcasa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="comuna" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "id",
            "nombre",
            "rut",
            "telefono",
            "telefonoAlternativo",
            "direccion",
            "numcalle",
            "numdepto",
            "numcasa",
            "region",
            "comuna",
            "ciudad",
            "comentario",
            "localidad"
        })
        public static class Cliente {

            @XmlElement(required = true)
            protected String id;
            @XmlElement(required = true)
            protected String nombre;
            @XmlElement(required = true)
            protected String rut;
            @XmlElement(required = true)
            protected String telefono;
            @XmlElement(name = "telefono_alternativo")
            protected String telefonoAlternativo;
            @XmlElement(required = true)
            protected String direccion;
            @XmlElement(required = true)
            protected String numcalle;
            @XmlElement(required = true)
            protected String numdepto;
            @XmlElement(required = true)
            protected String numcasa;
            @XmlElement(required = true)
            protected String region;
            @XmlElement(required = true)
            protected String comuna;
            @XmlElement(required = true)
            protected String ciudad;
            @XmlElement(required = true)
            protected String comentario;
            protected String localidad;

            /**
             * Obtiene el valor de la propiedad id.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getId() {
                return id;
            }

            /**
             * Define el valor de la propiedad id.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setId(String value) {
                this.id = value;
            }

            /**
             * Obtiene el valor de la propiedad nombre.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombre() {
                return nombre;
            }

            /**
             * Define el valor de la propiedad nombre.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombre(String value) {
                this.nombre = value;
            }

            /**
             * Obtiene el valor de la propiedad rut.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRut() {
                return rut;
            }

            /**
             * Define el valor de la propiedad rut.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRut(String value) {
                this.rut = value;
            }

            /**
             * Obtiene el valor de la propiedad telefono.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTelefono() {
                return telefono;
            }

            /**
             * Define el valor de la propiedad telefono.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTelefono(String value) {
                this.telefono = value;
            }

            /**
             * Obtiene el valor de la propiedad telefonoAlternativo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTelefonoAlternativo() {
                return telefonoAlternativo;
            }

            /**
             * Define el valor de la propiedad telefonoAlternativo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTelefonoAlternativo(String value) {
                this.telefonoAlternativo = value;
            }

            /**
             * Obtiene el valor de la propiedad direccion.
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
             * Define el valor de la propiedad direccion.
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
             * Obtiene el valor de la propiedad numcalle.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumcalle() {
                return numcalle;
            }

            /**
             * Define el valor de la propiedad numcalle.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumcalle(String value) {
                this.numcalle = value;
            }

            /**
             * Obtiene el valor de la propiedad numdepto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumdepto() {
                return numdepto;
            }

            /**
             * Define el valor de la propiedad numdepto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumdepto(String value) {
                this.numdepto = value;
            }

            /**
             * Obtiene el valor de la propiedad numcasa.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumcasa() {
                return numcasa;
            }

            /**
             * Define el valor de la propiedad numcasa.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumcasa(String value) {
                this.numcasa = value;
            }

            /**
             * Obtiene el valor de la propiedad region.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRegion() {
                return region;
            }

            /**
             * Define el valor de la propiedad region.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRegion(String value) {
                this.region = value;
            }

            /**
             * Obtiene el valor de la propiedad comuna.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComuna() {
                return comuna;
            }

            /**
             * Define el valor de la propiedad comuna.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComuna(String value) {
                this.comuna = value;
            }

            /**
             * Obtiene el valor de la propiedad ciudad.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCiudad() {
                return ciudad;
            }

            /**
             * Define el valor de la propiedad ciudad.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCiudad(String value) {
                this.ciudad = value;
            }

            /**
             * Obtiene el valor de la propiedad comentario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComentario() {
                return comentario;
            }

            /**
             * Define el valor de la propiedad comentario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComentario(String value) {
                this.comentario = value;
            }

            /**
             * Obtiene el valor de la propiedad localidad.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocalidad() {
                return localidad;
            }

            /**
             * Define el valor de la propiedad localidad.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocalidad(String value) {
                this.localidad = value;
            }

        }

    }


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
     *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="unidad_negocio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "rut",
        "razonSocial",
        "unidadNegocio"
    })
    public static class Comprador {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "razon_social", required = true)
        protected String razonSocial;
        @XmlElement(name = "unidad_negocio", required = true)
        protected String unidadNegocio;

        /**
         * Obtiene el valor de la propiedad rut.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRut() {
            return rut;
        }

        /**
         * Define el valor de la propiedad rut.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRut(String value) {
            this.rut = value;
        }

        /**
         * Obtiene el valor de la propiedad razonSocial.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonSocial() {
            return razonSocial;
        }

        /**
         * Define el valor de la propiedad razonSocial.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonSocial(String value) {
            this.razonSocial = value;
        }

        /**
         * Obtiene el valor de la propiedad unidadNegocio.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnidadNegocio() {
            return unidadNegocio;
        }

        /**
         * Define el valor de la propiedad unidadNegocio.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnidadNegocio(String value) {
            this.unidadNegocio = value;
        }

    }


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
     *         &lt;element name="senderIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="recipientIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "senderIdentification",
        "recipientIdentification",
        "buyerLocationCode"
    })
    public static class EdiData {

        @XmlElement(required = true)
        protected String senderIdentification;
        @XmlElement(required = true)
        protected String recipientIdentification;
        @XmlElement(required = true)
        protected String buyerLocationCode;

        /**
         * Obtiene el valor de la propiedad senderIdentification.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSenderIdentification() {
            return senderIdentification;
        }

        /**
         * Define el valor de la propiedad senderIdentification.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSenderIdentification(String value) {
            this.senderIdentification = value;
        }

        /**
         * Obtiene el valor de la propiedad recipientIdentification.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientIdentification() {
            return recipientIdentification;
        }

        /**
         * Define el valor de la propiedad recipientIdentification.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientIdentification(String value) {
            this.recipientIdentification = value;
        }

        /**
         * Obtiene el valor de la propiedad buyerLocationCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuyerLocationCode() {
            return buyerLocationCode;
        }

        /**
         * Define el valor de la propiedad buyerLocationCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuyerLocationCode(String value) {
            this.buyerLocationCode = value;
        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="local"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "local"
    })
    public static class Locales {

        @XmlElement(required = true)
        protected List<Consolidado.Locales.Local> local;

        /**
         * Gets the value of the local property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the local property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLocal().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Consolidado.Locales.Local }
         * 
         * 
         */
        public List<Consolidado.Locales.Local> getLocal() {
            if (local == null) {
                local = new ArrayList<Consolidado.Locales.Local>();
            }
            return this.local;
        }


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
         *         &lt;element name="cod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ean" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "cod",
            "nombre",
            "direccion",
            "ean"
        })
        public static class Local {

            @XmlElement(required = true)
            protected String cod;
            @XmlElement(required = true)
            protected String nombre;
            @XmlElement(required = true)
            protected String direccion;
            protected String ean;

            /**
             * Obtiene el valor de la propiedad cod.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCod() {
                return cod;
            }

            /**
             * Define el valor de la propiedad cod.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCod(String value) {
                this.cod = value;
            }

            /**
             * Obtiene el valor de la propiedad nombre.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombre() {
                return nombre;
            }

            /**
             * Define el valor de la propiedad nombre.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombre(String value) {
                this.nombre = value;
            }

            /**
             * Obtiene el valor de la propiedad direccion.
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
             * Define el valor de la propiedad direccion.
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
             * Obtiene el valor de la propiedad ean.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEan() {
                return ean;
            }

            /**
             * Define el valor de la propiedad ean.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEan(String value) {
                this.ean = value;
            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="orden"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="digito_verificador_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="estado_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nosolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="f12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cod_seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="nro_boleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="fecha_vigencia" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="codigo_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="fecha_compromiso" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="fecha_vto" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *                   &lt;element name="forma_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="responsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="detalles"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                             &lt;element name="detalle"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *                                       &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                                       &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="dv_codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="cant_sol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="cant_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                                       &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_producto"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                                                 &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="cargos_prod"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                                                 &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "orden"
    })
    public static class Ordenes {

        @XmlElement(required = true)
        protected List<Consolidado.Ordenes.Orden> orden;

        /**
         * Gets the value of the orden property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the orden property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOrden().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Consolidado.Ordenes.Orden }
         * 
         * 
         */
        public List<Consolidado.Ordenes.Orden> getOrden() {
            if (orden == null) {
                orden = new ArrayList<Consolidado.Ordenes.Orden>();
            }
            return this.orden;
        }


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
         *         &lt;element name="digito_verificador_oc" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="estado_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nosolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="f12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cod_seccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="nro_boleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="fecha_vigencia" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="codigo_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="fecha_compromiso" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="fecha_vto" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
         *         &lt;element name="forma_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="responsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="detalles"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                   &lt;element name="detalle"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
         *                             &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *                             &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *                             &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="dv_codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="cant_sol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="cant_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="desc_producto"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                                       &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                             &lt;element name="cargos_prod"&gt;
         *                               &lt;complexType&gt;
         *                                 &lt;complexContent&gt;
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                                       &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
         *                                     &lt;/sequence&gt;
         *                                   &lt;/restriction&gt;
         *                                 &lt;/complexContent&gt;
         *                               &lt;/complexType&gt;
         *                             &lt;/element&gt;
         *                           &lt;/sequence&gt;
         *                         &lt;/restriction&gt;
         *                       &lt;/complexContent&gt;
         *                     &lt;/complexType&gt;
         *                   &lt;/element&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
            "digitoVerificadorOc",
            "item",
            "estadoOc",
            "tipoOc",
            "numOc",
            "nosolicitud",
            "f12",
            "seccion",
            "codSeccion",
            "nroBoleta",
            "idCliente",
            "fechaEmision",
            "fechaVigencia",
            "codigoLocalVenta",
            "codLocalEntrega",
            "fechaCompromiso",
            "total",
            "fechaVto",
            "formaPago",
            "responsable",
            "detalles"
        })
        public static class Orden {

            @XmlElement(name = "digito_verificador_oc")
            protected int digitoVerificadorOc;
            protected int item;
            @XmlElement(name = "estado_oc", required = true)
            protected String estadoOc;
            @XmlElement(name = "tipo_oc", required = true)
            protected String tipoOc;
            @XmlElement(name = "num_oc", required = true)
            protected String numOc;
            protected String nosolicitud;
            protected String f12;
            protected String seccion;
            @XmlElement(name = "cod_seccion")
            protected String codSeccion;
            @XmlElement(name = "nro_boleta")
            protected String nroBoleta;
            @XmlElement(name = "id_cliente")
            protected String idCliente;
            @XmlElement(name = "fecha_emision", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaEmision;
            @XmlElement(name = "fecha_vigencia", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaVigencia;
            @XmlElement(name = "codigo_local_venta")
            protected String codigoLocalVenta;
            @XmlElement(name = "cod_local_entrega", required = true)
            protected String codLocalEntrega;
            @XmlElement(name = "fecha_compromiso", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaCompromiso;
            protected double total;
            @XmlElement(name = "fecha_vto", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaVto;
            @XmlElement(name = "forma_pago")
            protected String formaPago;
            @XmlElement(required = true)
            protected String responsable;
            @XmlElement(required = true)
            protected Consolidado.Ordenes.Orden.Detalles detalles;

            /**
             * Obtiene el valor de la propiedad digitoVerificadorOc.
             * 
             */
            public int getDigitoVerificadorOc() {
                return digitoVerificadorOc;
            }

            /**
             * Define el valor de la propiedad digitoVerificadorOc.
             * 
             */
            public void setDigitoVerificadorOc(int value) {
                this.digitoVerificadorOc = value;
            }

            /**
             * Obtiene el valor de la propiedad item.
             * 
             */
            public int getItem() {
                return item;
            }

            /**
             * Define el valor de la propiedad item.
             * 
             */
            public void setItem(int value) {
                this.item = value;
            }

            /**
             * Obtiene el valor de la propiedad estadoOc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEstadoOc() {
                return estadoOc;
            }

            /**
             * Define el valor de la propiedad estadoOc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEstadoOc(String value) {
                this.estadoOc = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoOc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoOc() {
                return tipoOc;
            }

            /**
             * Define el valor de la propiedad tipoOc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoOc(String value) {
                this.tipoOc = value;
            }

            /**
             * Obtiene el valor de la propiedad numOc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumOc() {
                return numOc;
            }

            /**
             * Define el valor de la propiedad numOc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumOc(String value) {
                this.numOc = value;
            }

            /**
             * Obtiene el valor de la propiedad nosolicitud.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNosolicitud() {
                return nosolicitud;
            }

            /**
             * Define el valor de la propiedad nosolicitud.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNosolicitud(String value) {
                this.nosolicitud = value;
            }

            /**
             * Obtiene el valor de la propiedad f12.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getF12() {
                return f12;
            }

            /**
             * Define el valor de la propiedad f12.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setF12(String value) {
                this.f12 = value;
            }

            /**
             * Obtiene el valor de la propiedad seccion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeccion() {
                return seccion;
            }

            /**
             * Define el valor de la propiedad seccion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeccion(String value) {
                this.seccion = value;
            }

            /**
             * Obtiene el valor de la propiedad codSeccion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodSeccion() {
                return codSeccion;
            }

            /**
             * Define el valor de la propiedad codSeccion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodSeccion(String value) {
                this.codSeccion = value;
            }

            /**
             * Obtiene el valor de la propiedad nroBoleta.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNroBoleta() {
                return nroBoleta;
            }

            /**
             * Define el valor de la propiedad nroBoleta.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNroBoleta(String value) {
                this.nroBoleta = value;
            }

            /**
             * Obtiene el valor de la propiedad idCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdCliente() {
                return idCliente;
            }

            /**
             * Define el valor de la propiedad idCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdCliente(String value) {
                this.idCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaEmision.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaEmision() {
                return fechaEmision;
            }

            /**
             * Define el valor de la propiedad fechaEmision.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaEmision(XMLGregorianCalendar value) {
                this.fechaEmision = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaVigencia.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaVigencia() {
                return fechaVigencia;
            }

            /**
             * Define el valor de la propiedad fechaVigencia.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaVigencia(XMLGregorianCalendar value) {
                this.fechaVigencia = value;
            }

            /**
             * Obtiene el valor de la propiedad codigoLocalVenta.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodigoLocalVenta() {
                return codigoLocalVenta;
            }

            /**
             * Define el valor de la propiedad codigoLocalVenta.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodigoLocalVenta(String value) {
                this.codigoLocalVenta = value;
            }

            /**
             * Obtiene el valor de la propiedad codLocalEntrega.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLocalEntrega() {
                return codLocalEntrega;
            }

            /**
             * Define el valor de la propiedad codLocalEntrega.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLocalEntrega(String value) {
                this.codLocalEntrega = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaCompromiso.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaCompromiso() {
                return fechaCompromiso;
            }

            /**
             * Define el valor de la propiedad fechaCompromiso.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaCompromiso(XMLGregorianCalendar value) {
                this.fechaCompromiso = value;
            }

            /**
             * Obtiene el valor de la propiedad total.
             * 
             */
            public double getTotal() {
                return total;
            }

            /**
             * Define el valor de la propiedad total.
             * 
             */
            public void setTotal(double value) {
                this.total = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaVto.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaVto() {
                return fechaVto;
            }

            /**
             * Define el valor de la propiedad fechaVto.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaVto(XMLGregorianCalendar value) {
                this.fechaVto = value;
            }

            /**
             * Obtiene el valor de la propiedad formaPago.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFormaPago() {
                return formaPago;
            }

            /**
             * Define el valor de la propiedad formaPago.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFormaPago(String value) {
                this.formaPago = value;
            }

            /**
             * Obtiene el valor de la propiedad responsable.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponsable() {
                return responsable;
            }

            /**
             * Define el valor de la propiedad responsable.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponsable(String value) {
                this.responsable = value;
            }

            /**
             * Obtiene el valor de la propiedad detalles.
             * 
             * @return
             *     possible object is
             *     {@link Consolidado.Ordenes.Orden.Detalles }
             *     
             */
            public Consolidado.Ordenes.Orden.Detalles getDetalles() {
                return detalles;
            }

            /**
             * Define el valor de la propiedad detalles.
             * 
             * @param value
             *     allowed object is
             *     {@link Consolidado.Ordenes.Orden.Detalles }
             *     
             */
            public void setDetalles(Consolidado.Ordenes.Orden.Detalles value) {
                this.detalles = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
             *         &lt;element name="detalle"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
             *                   &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
             *                   &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
             *                   &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="dv_codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="cant_sol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="cant_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
             *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="desc_producto"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
             *                             &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                   &lt;element name="cargos_prod"&gt;
             *                     &lt;complexType&gt;
             *                       &lt;complexContent&gt;
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
             *                             &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
             *                           &lt;/sequence&gt;
             *                         &lt;/restriction&gt;
             *                       &lt;/complexContent&gt;
             *                     &lt;/complexType&gt;
             *                   &lt;/element&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
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
                "detalle"
            })
            public static class Detalles {

                protected List<Consolidado.Ordenes.Orden.Detalles.Detalle> detalle;

                /**
                 * Gets the value of the detalle property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the detalle property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDetalle().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Consolidado.Ordenes.Orden.Detalles.Detalle }
                 * 
                 * 
                 */
                public List<Consolidado.Ordenes.Orden.Detalles.Detalle> getDetalle() {
                    if (detalle == null) {
                        detalle = new ArrayList<Consolidado.Ordenes.Orden.Detalles.Detalle>();
                    }
                    return this.detalle;
                }


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
                 *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
                 *         &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
                 *         &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
                 *         &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="dv_codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="codigo_etiq" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="cant_sol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="cant_des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
                 *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="desc_producto"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
                 *                   &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
                 *         &lt;element name="cargos_prod"&gt;
                 *           &lt;complexType&gt;
                 *             &lt;complexContent&gt;
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
                 *                   &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
                 *                 &lt;/sequence&gt;
                 *               &lt;/restriction&gt;
                 *             &lt;/complexContent&gt;
                 *           &lt;/complexType&gt;
                 *         &lt;/element&gt;
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
                    "sku",
                    "codProdVendedor",
                    "prodEmpaque",
                    "cantidadEmpaque",
                    "innerpack",
                    "descEmpaque",
                    "descUmUnidad",
                    "dvCodigoEtiq",
                    "codigoEtiq",
                    "costoLista",
                    "cantSol",
                    "cantDes",
                    "costoFinal",
                    "precioLista",
                    "ean13",
                    "rubro",
                    "ean1",
                    "ean2",
                    "ean3",
                    "codEstilo",
                    "descEstilo",
                    "marcaEstilo",
                    "descProducto",
                    "cargosProd"
                })
                public static class Detalle {

                    @XmlElement(required = true)
                    protected String sku;
                    @XmlElement(name = "cod_prod_vendedor", required = true)
                    protected String codProdVendedor;
                    @XmlElement(name = "prod_empaque")
                    protected float prodEmpaque;
                    @XmlElement(name = "cantidad_empaque")
                    protected int cantidadEmpaque;
                    protected Integer innerpack;
                    @XmlElement(name = "desc_empaque")
                    protected String descEmpaque;
                    @XmlElement(name = "desc_um_unidad")
                    protected String descUmUnidad;
                    @XmlElement(name = "dv_codigo_etiq", required = true)
                    protected String dvCodigoEtiq;
                    @XmlElement(name = "codigo_etiq", required = true)
                    protected String codigoEtiq;
                    @XmlElement(name = "costo_lista", required = true)
                    protected String costoLista;
                    @XmlElement(name = "cant_sol", required = true)
                    protected String cantSol;
                    @XmlElement(name = "cant_des")
                    protected String cantDes;
                    @XmlElement(name = "costo_final", required = true)
                    protected String costoFinal;
                    @XmlElement(name = "precio_lista")
                    protected Double precioLista;
                    @XmlElement(required = true)
                    protected String ean13;
                    protected String rubro;
                    protected String ean1;
                    protected String ean2;
                    protected String ean3;
                    @XmlElement(name = "cod_estilo")
                    protected String codEstilo;
                    @XmlElement(name = "desc_estilo")
                    protected String descEstilo;
                    @XmlElement(name = "marca_estilo")
                    protected String marcaEstilo;
                    @XmlElement(name = "desc_producto", required = true)
                    protected Consolidado.Ordenes.Orden.Detalles.Detalle.DescProducto descProducto;
                    @XmlElement(name = "cargos_prod", required = true)
                    protected Consolidado.Ordenes.Orden.Detalles.Detalle.CargosProd cargosProd;

                    /**
                     * Obtiene el valor de la propiedad sku.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSku() {
                        return sku;
                    }

                    /**
                     * Define el valor de la propiedad sku.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSku(String value) {
                        this.sku = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codProdVendedor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdVendedor() {
                        return codProdVendedor;
                    }

                    /**
                     * Define el valor de la propiedad codProdVendedor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdVendedor(String value) {
                        this.codProdVendedor = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad prodEmpaque.
                     * 
                     */
                    public float getProdEmpaque() {
                        return prodEmpaque;
                    }

                    /**
                     * Define el valor de la propiedad prodEmpaque.
                     * 
                     */
                    public void setProdEmpaque(float value) {
                        this.prodEmpaque = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cantidadEmpaque.
                     * 
                     */
                    public int getCantidadEmpaque() {
                        return cantidadEmpaque;
                    }

                    /**
                     * Define el valor de la propiedad cantidadEmpaque.
                     * 
                     */
                    public void setCantidadEmpaque(int value) {
                        this.cantidadEmpaque = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad innerpack.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Integer }
                     *     
                     */
                    public Integer getInnerpack() {
                        return innerpack;
                    }

                    /**
                     * Define el valor de la propiedad innerpack.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Integer }
                     *     
                     */
                    public void setInnerpack(Integer value) {
                        this.innerpack = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descEmpaque.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescEmpaque() {
                        return descEmpaque;
                    }

                    /**
                     * Define el valor de la propiedad descEmpaque.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescEmpaque(String value) {
                        this.descEmpaque = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descUmUnidad.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescUmUnidad() {
                        return descUmUnidad;
                    }

                    /**
                     * Define el valor de la propiedad descUmUnidad.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescUmUnidad(String value) {
                        this.descUmUnidad = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad dvCodigoEtiq.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDvCodigoEtiq() {
                        return dvCodigoEtiq;
                    }

                    /**
                     * Define el valor de la propiedad dvCodigoEtiq.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDvCodigoEtiq(String value) {
                        this.dvCodigoEtiq = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codigoEtiq.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodigoEtiq() {
                        return codigoEtiq;
                    }

                    /**
                     * Define el valor de la propiedad codigoEtiq.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodigoEtiq(String value) {
                        this.codigoEtiq = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad costoLista.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCostoLista() {
                        return costoLista;
                    }

                    /**
                     * Define el valor de la propiedad costoLista.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCostoLista(String value) {
                        this.costoLista = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cantSol.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCantSol() {
                        return cantSol;
                    }

                    /**
                     * Define el valor de la propiedad cantSol.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCantSol(String value) {
                        this.cantSol = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cantDes.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCantDes() {
                        return cantDes;
                    }

                    /**
                     * Define el valor de la propiedad cantDes.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCantDes(String value) {
                        this.cantDes = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad costoFinal.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCostoFinal() {
                        return costoFinal;
                    }

                    /**
                     * Define el valor de la propiedad costoFinal.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCostoFinal(String value) {
                        this.costoFinal = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad precioLista.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Double }
                     *     
                     */
                    public Double getPrecioLista() {
                        return precioLista;
                    }

                    /**
                     * Define el valor de la propiedad precioLista.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Double }
                     *     
                     */
                    public void setPrecioLista(Double value) {
                        this.precioLista = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad ean13.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEan13() {
                        return ean13;
                    }

                    /**
                     * Define el valor de la propiedad ean13.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEan13(String value) {
                        this.ean13 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad rubro.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getRubro() {
                        return rubro;
                    }

                    /**
                     * Define el valor de la propiedad rubro.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setRubro(String value) {
                        this.rubro = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad ean1.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEan1() {
                        return ean1;
                    }

                    /**
                     * Define el valor de la propiedad ean1.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEan1(String value) {
                        this.ean1 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad ean2.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEan2() {
                        return ean2;
                    }

                    /**
                     * Define el valor de la propiedad ean2.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEan2(String value) {
                        this.ean2 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad ean3.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEan3() {
                        return ean3;
                    }

                    /**
                     * Define el valor de la propiedad ean3.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEan3(String value) {
                        this.ean3 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codEstilo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodEstilo() {
                        return codEstilo;
                    }

                    /**
                     * Define el valor de la propiedad codEstilo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodEstilo(String value) {
                        this.codEstilo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descEstilo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescEstilo() {
                        return descEstilo;
                    }

                    /**
                     * Define el valor de la propiedad descEstilo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescEstilo(String value) {
                        this.descEstilo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad marcaEstilo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMarcaEstilo() {
                        return marcaEstilo;
                    }

                    /**
                     * Define el valor de la propiedad marcaEstilo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMarcaEstilo(String value) {
                        this.marcaEstilo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descProducto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Consolidado.Ordenes.Orden.Detalles.Detalle.DescProducto }
                     *     
                     */
                    public Consolidado.Ordenes.Orden.Detalles.Detalle.DescProducto getDescProducto() {
                        return descProducto;
                    }

                    /**
                     * Define el valor de la propiedad descProducto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Consolidado.Ordenes.Orden.Detalles.Detalle.DescProducto }
                     *     
                     */
                    public void setDescProducto(Consolidado.Ordenes.Orden.Detalles.Detalle.DescProducto value) {
                        this.descProducto = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cargosProd.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Consolidado.Ordenes.Orden.Detalles.Detalle.CargosProd }
                     *     
                     */
                    public Consolidado.Ordenes.Orden.Detalles.Detalle.CargosProd getCargosProd() {
                        return cargosProd;
                    }

                    /**
                     * Define el valor de la propiedad cargosProd.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Consolidado.Ordenes.Orden.Detalles.Detalle.CargosProd }
                     *     
                     */
                    public void setCargosProd(Consolidado.Ordenes.Orden.Detalles.Detalle.CargosProd value) {
                        this.cargosProd = value;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
                     *         &lt;element name="cargo" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
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
                        "cargo"
                    })
                    public static class CargosProd {

                        protected List<CargoDescuento> cargo;

                        /**
                         * Gets the value of the cargo property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the cargo property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getCargo().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link CargoDescuento }
                         * 
                         * 
                         */
                        public List<CargoDescuento> getCargo() {
                            if (cargo == null) {
                                cargo = new ArrayList<CargoDescuento>();
                            }
                            return this.cargo;
                        }

                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType&gt;
                     *   &lt;complexContent&gt;
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
                     *         &lt;element name="descuento" type="{http://www.bbr.cl/bbr/OCConsolidado}cargo_descuento"/&gt;
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
                        "descuento"
                    })
                    public static class DescProducto {

                        protected List<CargoDescuento> descuento;

                        /**
                         * Gets the value of the descuento property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the descuento property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getDescuento().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link CargoDescuento }
                         * 
                         * 
                         */
                        public List<CargoDescuento> getDescuento() {
                            if (descuento == null) {
                                descuento = new ArrayList<CargoDescuento>();
                            }
                            return this.descuento;
                        }

                    }

                }

            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="predistribucion"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "predistribucion"
    })
    public static class Predistribuciones {

        protected List<Consolidado.Predistribuciones.Predistribucion> predistribucion;

        /**
         * Gets the value of the predistribucion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the predistribucion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPredistribucion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Consolidado.Predistribuciones.Predistribucion }
         * 
         * 
         */
        public List<Consolidado.Predistribuciones.Predistribucion> getPredistribucion() {
            if (predistribucion == null) {
                predistribucion = new ArrayList<Consolidado.Predistribuciones.Predistribucion>();
            }
            return this.predistribucion;
        }


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
         *         &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "codLocal",
            "numOc",
            "sku",
            "cantidad"
        })
        public static class Predistribucion {

            @XmlElement(name = "cod_local", required = true)
            protected String codLocal;
            @XmlElement(name = "num_oc", required = true)
            protected String numOc;
            @XmlElement(required = true)
            protected String sku;
            @XmlElement(required = true)
            protected String cantidad;

            /**
             * Obtiene el valor de la propiedad codLocal.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLocal() {
                return codLocal;
            }

            /**
             * Define el valor de la propiedad codLocal.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLocal(String value) {
                this.codLocal = value;
            }

            /**
             * Obtiene el valor de la propiedad numOc.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumOc() {
                return numOc;
            }

            /**
             * Define el valor de la propiedad numOc.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumOc(String value) {
                this.numOc = value;
            }

            /**
             * Obtiene el valor de la propiedad sku.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSku() {
                return sku;
            }

            /**
             * Define el valor de la propiedad sku.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSku(String value) {
                this.sku = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidad.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCantidad() {
                return cantidad;
            }

            /**
             * Define el valor de la propiedad cantidad.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCantidad(String value) {
                this.cantidad = value;
            }

        }

    }


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
     *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "rut",
        "digitoVerificador",
        "razonSocial",
        "contacto",
        "direccion",
        "telefono"
    })
    public static class Vendedor {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "digito_verificador", required = true)
        protected String digitoVerificador;
        @XmlElement(name = "razon_social", required = true)
        protected String razonSocial;
        @XmlElement(required = true)
        protected String contacto;
        @XmlElement(required = true)
        protected String direccion;
        @XmlElement(required = true)
        protected String telefono;

        /**
         * Obtiene el valor de la propiedad rut.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRut() {
            return rut;
        }

        /**
         * Define el valor de la propiedad rut.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRut(String value) {
            this.rut = value;
        }

        /**
         * Obtiene el valor de la propiedad digitoVerificador.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDigitoVerificador() {
            return digitoVerificador;
        }

        /**
         * Define el valor de la propiedad digitoVerificador.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDigitoVerificador(String value) {
            this.digitoVerificador = value;
        }

        /**
         * Obtiene el valor de la propiedad razonSocial.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRazonSocial() {
            return razonSocial;
        }

        /**
         * Define el valor de la propiedad razonSocial.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRazonSocial(String value) {
            this.razonSocial = value;
        }

        /**
         * Obtiene el valor de la propiedad contacto.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContacto() {
            return contacto;
        }

        /**
         * Define el valor de la propiedad contacto.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContacto(String value) {
            this.contacto = value;
        }

        /**
         * Obtiene el valor de la propiedad direccion.
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
         * Define el valor de la propiedad direccion.
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
         * Obtiene el valor de la propiedad telefono.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTelefono() {
            return telefono;
        }

        /**
         * Define el valor de la propiedad telefono.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTelefono(String value) {
            this.telefono = value;
        }

    }

}

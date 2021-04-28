//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.07.15 a las 04:21:32 PM CLT 
//


package bbr.b2b.b2blink.logistic.xml.PL_Proveedor;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cod_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nro_cita" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fecha_cita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hora_inicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hora_termino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nro_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total_totes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total_bultos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total_colgados" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="total_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="doc_valido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vendedor"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="bultos"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="bulto"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="nro_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tipo_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="nro_pallet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descripcion_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descripcion_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="hora_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tipo_embalaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="nro_bulto_externo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="detalles"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded"&gt;
 *                                       &lt;element name="detalle"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="tipo_noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="dun14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cantidad_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cantidad_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="porcentaje_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="docs_tributario"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="doc_tributario"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="nro_serie_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tipo_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="neto_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="iva_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="total_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="detalles_doc_tributario"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded"&gt;
 *                                       &lt;element name="detalle_doc_tributario"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="porc_descto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="entregas"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded"&gt;
 *                   &lt;element name="entrega"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="total_bultos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="total_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="total_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="total_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="consolidado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="detalles"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded"&gt;
 *                                       &lt;element name="detalle"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="saldo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "ticket",
    "nombreportal",
    "codProveedor",
    "codCliente",
    "nroCita",
    "fechaCita",
    "horaInicio",
    "horaTermino",
    "nroEntrega",
    "codLocalEntrega",
    "totalTotes",
    "totalBultos",
    "totalColgados",
    "totalUnidades",
    "totalCajas",
    "numref1",
    "numref2",
    "numref3",
    "numref4",
    "numref5",
    "docValido",
    "vendedor",
    "bultos",
    "docsTributario",
    "entregas"
})
@XmlRootElement(name = "PLProveedor")
public class PLProveedor {

    protected String ticket;
    @XmlElement(required = true)
    protected String nombreportal;
    @XmlElement(name = "cod_proveedor", required = true)
    protected String codProveedor;
    @XmlElement(name = "cod_cliente", required = true)
    protected String codCliente;
    @XmlElement(name = "nro_cita", required = true)
    protected String nroCita;
    @XmlElement(name = "fecha_cita")
    protected String fechaCita;
    @XmlElement(name = "hora_inicio")
    protected String horaInicio;
    @XmlElement(name = "hora_termino")
    protected String horaTermino;
    @XmlElement(name = "nro_entrega")
    protected String nroEntrega;
    @XmlElement(name = "cod_local_entrega")
    protected String codLocalEntrega;
    @XmlElement(name = "total_totes")
    protected String totalTotes;
    @XmlElement(name = "total_bultos")
    protected String totalBultos;
    @XmlElement(name = "total_colgados")
    protected String totalColgados;
    @XmlElement(name = "total_unidades")
    protected String totalUnidades;
    @XmlElement(name = "total_cajas")
    protected String totalCajas;
    protected String numref1;
    protected String numref2;
    protected String numref3;
    protected String numref4;
    protected String numref5;
    @XmlElement(name = "doc_valido")
    protected String docValido;
    @XmlElement(required = true)
    protected PLProveedor.Vendedor vendedor;
    @XmlElement(required = true)
    protected PLProveedor.Bultos bultos;
    @XmlElement(name = "docs_tributario", required = true)
    protected PLProveedor.DocsTributario docsTributario;
    @XmlElement(required = true)
    protected PLProveedor.Entregas entregas;

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
     * Obtiene el valor de la propiedad codProveedor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodProveedor() {
        return codProveedor;
    }

    /**
     * Define el valor de la propiedad codProveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodProveedor(String value) {
        this.codProveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad codCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * Define el valor de la propiedad codCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCliente(String value) {
        this.codCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad nroCita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroCita() {
        return nroCita;
    }

    /**
     * Define el valor de la propiedad nroCita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroCita(String value) {
        this.nroCita = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaCita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaCita() {
        return fechaCita;
    }

    /**
     * Define el valor de la propiedad fechaCita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaCita(String value) {
        this.fechaCita = value;
    }

    /**
     * Obtiene el valor de la propiedad horaInicio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * Define el valor de la propiedad horaInicio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraInicio(String value) {
        this.horaInicio = value;
    }

    /**
     * Obtiene el valor de la propiedad horaTermino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraTermino() {
        return horaTermino;
    }

    /**
     * Define el valor de la propiedad horaTermino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraTermino(String value) {
        this.horaTermino = value;
    }

    /**
     * Obtiene el valor de la propiedad nroEntrega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroEntrega() {
        return nroEntrega;
    }

    /**
     * Define el valor de la propiedad nroEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroEntrega(String value) {
        this.nroEntrega = value;
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
     * Obtiene el valor de la propiedad totalTotes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalTotes() {
        return totalTotes;
    }

    /**
     * Define el valor de la propiedad totalTotes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalTotes(String value) {
        this.totalTotes = value;
    }

    /**
     * Obtiene el valor de la propiedad totalBultos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalBultos() {
        return totalBultos;
    }

    /**
     * Define el valor de la propiedad totalBultos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalBultos(String value) {
        this.totalBultos = value;
    }

    /**
     * Obtiene el valor de la propiedad totalColgados.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalColgados() {
        return totalColgados;
    }

    /**
     * Define el valor de la propiedad totalColgados.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalColgados(String value) {
        this.totalColgados = value;
    }

    /**
     * Obtiene el valor de la propiedad totalUnidades.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalUnidades() {
        return totalUnidades;
    }

    /**
     * Define el valor de la propiedad totalUnidades.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalUnidades(String value) {
        this.totalUnidades = value;
    }

    /**
     * Obtiene el valor de la propiedad totalCajas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalCajas() {
        return totalCajas;
    }

    /**
     * Define el valor de la propiedad totalCajas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalCajas(String value) {
        this.totalCajas = value;
    }

    /**
     * Obtiene el valor de la propiedad numref1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref1() {
        return numref1;
    }

    /**
     * Define el valor de la propiedad numref1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref1(String value) {
        this.numref1 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref2() {
        return numref2;
    }

    /**
     * Define el valor de la propiedad numref2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref2(String value) {
        this.numref2 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref3() {
        return numref3;
    }

    /**
     * Define el valor de la propiedad numref3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref3(String value) {
        this.numref3 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref4() {
        return numref4;
    }

    /**
     * Define el valor de la propiedad numref4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref4(String value) {
        this.numref4 = value;
    }

    /**
     * Obtiene el valor de la propiedad numref5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumref5() {
        return numref5;
    }

    /**
     * Define el valor de la propiedad numref5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumref5(String value) {
        this.numref5 = value;
    }

    /**
     * Obtiene el valor de la propiedad docValido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocValido() {
        return docValido;
    }

    /**
     * Define el valor de la propiedad docValido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocValido(String value) {
        this.docValido = value;
    }

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link PLProveedor.Vendedor }
     *     
     */
    public PLProveedor.Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link PLProveedor.Vendedor }
     *     
     */
    public void setVendedor(PLProveedor.Vendedor value) {
        this.vendedor = value;
    }

    /**
     * Obtiene el valor de la propiedad bultos.
     * 
     * @return
     *     possible object is
     *     {@link PLProveedor.Bultos }
     *     
     */
    public PLProveedor.Bultos getBultos() {
        return bultos;
    }

    /**
     * Define el valor de la propiedad bultos.
     * 
     * @param value
     *     allowed object is
     *     {@link PLProveedor.Bultos }
     *     
     */
    public void setBultos(PLProveedor.Bultos value) {
        this.bultos = value;
    }

    /**
     * Obtiene el valor de la propiedad docsTributario.
     * 
     * @return
     *     possible object is
     *     {@link PLProveedor.DocsTributario }
     *     
     */
    public PLProveedor.DocsTributario getDocsTributario() {
        return docsTributario;
    }

    /**
     * Define el valor de la propiedad docsTributario.
     * 
     * @param value
     *     allowed object is
     *     {@link PLProveedor.DocsTributario }
     *     
     */
    public void setDocsTributario(PLProveedor.DocsTributario value) {
        this.docsTributario = value;
    }

    /**
     * Obtiene el valor de la propiedad entregas.
     * 
     * @return
     *     possible object is
     *     {@link PLProveedor.Entregas }
     *     
     */
    public PLProveedor.Entregas getEntregas() {
        return entregas;
    }

    /**
     * Define el valor de la propiedad entregas.
     * 
     * @param value
     *     allowed object is
     *     {@link PLProveedor.Entregas }
     *     
     */
    public void setEntregas(PLProveedor.Entregas value) {
        this.entregas = value;
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
     *         &lt;element name="bulto"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="nro_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tipo_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="nro_pallet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descripcion_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descripcion_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="hora_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tipo_embalaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="nro_bulto_externo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="detalles"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded"&gt;
     *                             &lt;element name="detalle"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="tipo_noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="dun14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cantidad_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cantidad_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="porcentaje_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "bulto"
    })
    public static class Bultos {

        @XmlElement(required = true)
        protected List<PLProveedor.Bultos.Bulto> bulto;

        /**
         * Gets the value of the bulto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bulto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBulto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PLProveedor.Bultos.Bulto }
         * 
         * 
         */
        public List<PLProveedor.Bultos.Bulto> getBulto() {
            if (bulto == null) {
                bulto = new ArrayList<PLProveedor.Bultos.Bulto>();
            }
            return this.bulto;
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
         *         &lt;element name="nro_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tipo_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="nro_pallet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descripcion_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cod_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descripcion_local_destino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="hora_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_bulto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tipo_embalaje" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="nro_bulto_externo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="detalles"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded"&gt;
         *                   &lt;element name="detalle"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="tipo_noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="dun14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cantidad_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cantidad_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="porcentaje_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "nroBulto",
            "tipoBulto",
            "nroPallet",
            "codLocalEntrega",
            "descripcionLocalEntrega",
            "codLocalDestino",
            "descripcionLocalDestino",
            "fechaDespacho",
            "horaDespacho",
            "fechaBulto",
            "tipoEmbalaje",
            "cantidad",
            "nroBultoExterno",
            "detalles"
        })
        public static class Bulto {

            @XmlElement(name = "nro_bulto")
            protected String nroBulto;
            @XmlElement(name = "tipo_bulto")
            protected String tipoBulto;
            @XmlElement(name = "nro_pallet")
            protected String nroPallet;
            @XmlElement(name = "cod_local_entrega")
            protected String codLocalEntrega;
            @XmlElement(name = "descripcion_local_entrega")
            protected String descripcionLocalEntrega;
            @XmlElement(name = "cod_local_destino")
            protected String codLocalDestino;
            @XmlElement(name = "descripcion_local_destino")
            protected String descripcionLocalDestino;
            @XmlElement(name = "fecha_despacho")
            protected String fechaDespacho;
            @XmlElement(name = "hora_despacho")
            protected String horaDespacho;
            @XmlElement(name = "fecha_bulto")
            protected String fechaBulto;
            @XmlElement(name = "tipo_embalaje")
            protected String tipoEmbalaje;
            protected String cantidad;
            @XmlElement(name = "nro_bulto_externo")
            protected String nroBultoExterno;
            @XmlElement(required = true)
            protected PLProveedor.Bultos.Bulto.Detalles detalles;

            /**
             * Obtiene el valor de la propiedad nroBulto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNroBulto() {
                return nroBulto;
            }

            /**
             * Define el valor de la propiedad nroBulto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNroBulto(String value) {
                this.nroBulto = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoBulto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoBulto() {
                return tipoBulto;
            }

            /**
             * Define el valor de la propiedad tipoBulto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoBulto(String value) {
                this.tipoBulto = value;
            }

            /**
             * Obtiene el valor de la propiedad nroPallet.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNroPallet() {
                return nroPallet;
            }

            /**
             * Define el valor de la propiedad nroPallet.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNroPallet(String value) {
                this.nroPallet = value;
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
             * Obtiene el valor de la propiedad descripcionLocalEntrega.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcionLocalEntrega() {
                return descripcionLocalEntrega;
            }

            /**
             * Define el valor de la propiedad descripcionLocalEntrega.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcionLocalEntrega(String value) {
                this.descripcionLocalEntrega = value;
            }

            /**
             * Obtiene el valor de la propiedad codLocalDestino.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLocalDestino() {
                return codLocalDestino;
            }

            /**
             * Define el valor de la propiedad codLocalDestino.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLocalDestino(String value) {
                this.codLocalDestino = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcionLocalDestino.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcionLocalDestino() {
                return descripcionLocalDestino;
            }

            /**
             * Define el valor de la propiedad descripcionLocalDestino.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcionLocalDestino(String value) {
                this.descripcionLocalDestino = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaDespacho.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaDespacho() {
                return fechaDespacho;
            }

            /**
             * Define el valor de la propiedad fechaDespacho.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaDespacho(String value) {
                this.fechaDespacho = value;
            }

            /**
             * Obtiene el valor de la propiedad horaDespacho.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHoraDespacho() {
                return horaDespacho;
            }

            /**
             * Define el valor de la propiedad horaDespacho.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHoraDespacho(String value) {
                this.horaDespacho = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaBulto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaBulto() {
                return fechaBulto;
            }

            /**
             * Define el valor de la propiedad fechaBulto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaBulto(String value) {
                this.fechaBulto = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoEmbalaje.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoEmbalaje() {
                return tipoEmbalaje;
            }

            /**
             * Define el valor de la propiedad tipoEmbalaje.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoEmbalaje(String value) {
                this.tipoEmbalaje = value;
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

            /**
             * Obtiene el valor de la propiedad nroBultoExterno.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNroBultoExterno() {
                return nroBultoExterno;
            }

            /**
             * Define el valor de la propiedad nroBultoExterno.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNroBultoExterno(String value) {
                this.nroBultoExterno = value;
            }

            /**
             * Obtiene el valor de la propiedad detalles.
             * 
             * @return
             *     possible object is
             *     {@link PLProveedor.Bultos.Bulto.Detalles }
             *     
             */
            public PLProveedor.Bultos.Bulto.Detalles getDetalles() {
                return detalles;
            }

            /**
             * Define el valor de la propiedad detalles.
             * 
             * @param value
             *     allowed object is
             *     {@link PLProveedor.Bultos.Bulto.Detalles }
             *     
             */
            public void setDetalles(PLProveedor.Bultos.Bulto.Detalles value) {
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
             *       &lt;sequence maxOccurs="unbounded"&gt;
             *         &lt;element name="detalle"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="tipo_noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="dun14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cantidad_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cantidad_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="porcentaje_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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

                @XmlElement(required = true)
                protected List<PLProveedor.Bultos.Bulto.Detalles.Detalle> detalle;

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
                 * {@link PLProveedor.Bultos.Bulto.Detalles.Detalle }
                 * 
                 * 
                 */
                public List<PLProveedor.Bultos.Bulto.Detalles.Detalle> getDetalle() {
                    if (detalle == null) {
                        detalle = new ArrayList<PLProveedor.Bultos.Bulto.Detalles.Detalle>();
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
                 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="tipo_noc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="dun14" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cantidad_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cantidad_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="porcentaje_desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="numref3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="numref4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="numref5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "item",
                    "entrega",
                    "noc",
                    "tipoNoc",
                    "noDocTributario",
                    "codProdProveedor",
                    "codProdCliente",
                    "descripcionProd",
                    "ean13",
                    "dun14",
                    "cantidadUnidades",
                    "cantidadCajas",
                    "costoUnitario",
                    "costoFinal",
                    "descProducto",
                    "porcentajeDesc",
                    "guiaDespacho",
                    "numref1",
                    "numref2",
                    "numref3",
                    "numref4",
                    "numref5"
                })
                public static class Detalle {

                    protected String item;
                    protected String entrega;
                    protected String noc;
                    @XmlElement(name = "tipo_noc")
                    protected String tipoNoc;
                    @XmlElement(name = "no_doc_tributario")
                    protected String noDocTributario;
                    @XmlElement(name = "cod_prod_proveedor")
                    protected String codProdProveedor;
                    @XmlElement(name = "cod_prod_cliente")
                    protected String codProdCliente;
                    @XmlElement(name = "descripcion_prod")
                    protected String descripcionProd;
                    protected String ean13;
                    protected String dun14;
                    @XmlElement(name = "cantidad_unidades")
                    protected String cantidadUnidades;
                    @XmlElement(name = "cantidad_cajas")
                    protected String cantidadCajas;
                    @XmlElement(name = "costo_unitario")
                    protected String costoUnitario;
                    @XmlElement(name = "costo_final")
                    protected String costoFinal;
                    @XmlElement(name = "desc_producto")
                    protected String descProducto;
                    @XmlElement(name = "porcentaje_desc")
                    protected String porcentajeDesc;
                    @XmlElement(name = "guia_despacho")
                    protected String guiaDespacho;
                    protected String numref1;
                    protected String numref2;
                    protected String numref3;
                    protected String numref4;
                    protected String numref5;

                    /**
                     * Obtiene el valor de la propiedad item.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getItem() {
                        return item;
                    }

                    /**
                     * Define el valor de la propiedad item.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setItem(String value) {
                        this.item = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad entrega.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEntrega() {
                        return entrega;
                    }

                    /**
                     * Define el valor de la propiedad entrega.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEntrega(String value) {
                        this.entrega = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad noc.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNoc() {
                        return noc;
                    }

                    /**
                     * Define el valor de la propiedad noc.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNoc(String value) {
                        this.noc = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad tipoNoc.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoNoc() {
                        return tipoNoc;
                    }

                    /**
                     * Define el valor de la propiedad tipoNoc.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoNoc(String value) {
                        this.tipoNoc = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad noDocTributario.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNoDocTributario() {
                        return noDocTributario;
                    }

                    /**
                     * Define el valor de la propiedad noDocTributario.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNoDocTributario(String value) {
                        this.noDocTributario = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codProdProveedor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdProveedor() {
                        return codProdProveedor;
                    }

                    /**
                     * Define el valor de la propiedad codProdProveedor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdProveedor(String value) {
                        this.codProdProveedor = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codProdCliente.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdCliente() {
                        return codProdCliente;
                    }

                    /**
                     * Define el valor de la propiedad codProdCliente.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdCliente(String value) {
                        this.codProdCliente = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descripcionProd.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescripcionProd() {
                        return descripcionProd;
                    }

                    /**
                     * Define el valor de la propiedad descripcionProd.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescripcionProd(String value) {
                        this.descripcionProd = value;
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
                     * Obtiene el valor de la propiedad dun14.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDun14() {
                        return dun14;
                    }

                    /**
                     * Define el valor de la propiedad dun14.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDun14(String value) {
                        this.dun14 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cantidadUnidades.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCantidadUnidades() {
                        return cantidadUnidades;
                    }

                    /**
                     * Define el valor de la propiedad cantidadUnidades.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCantidadUnidades(String value) {
                        this.cantidadUnidades = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad cantidadCajas.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCantidadCajas() {
                        return cantidadCajas;
                    }

                    /**
                     * Define el valor de la propiedad cantidadCajas.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCantidadCajas(String value) {
                        this.cantidadCajas = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad costoUnitario.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCostoUnitario() {
                        return costoUnitario;
                    }

                    /**
                     * Define el valor de la propiedad costoUnitario.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCostoUnitario(String value) {
                        this.costoUnitario = value;
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
                     * Obtiene el valor de la propiedad descProducto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescProducto() {
                        return descProducto;
                    }

                    /**
                     * Define el valor de la propiedad descProducto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescProducto(String value) {
                        this.descProducto = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad porcentajeDesc.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPorcentajeDesc() {
                        return porcentajeDesc;
                    }

                    /**
                     * Define el valor de la propiedad porcentajeDesc.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPorcentajeDesc(String value) {
                        this.porcentajeDesc = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad guiaDespacho.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getGuiaDespacho() {
                        return guiaDespacho;
                    }

                    /**
                     * Define el valor de la propiedad guiaDespacho.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setGuiaDespacho(String value) {
                        this.guiaDespacho = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad numref1.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumref1() {
                        return numref1;
                    }

                    /**
                     * Define el valor de la propiedad numref1.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumref1(String value) {
                        this.numref1 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad numref2.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumref2() {
                        return numref2;
                    }

                    /**
                     * Define el valor de la propiedad numref2.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumref2(String value) {
                        this.numref2 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad numref3.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumref3() {
                        return numref3;
                    }

                    /**
                     * Define el valor de la propiedad numref3.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumref3(String value) {
                        this.numref3 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad numref4.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumref4() {
                        return numref4;
                    }

                    /**
                     * Define el valor de la propiedad numref4.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumref4(String value) {
                        this.numref4 = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad numref5.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getNumref5() {
                        return numref5;
                    }

                    /**
                     * Define el valor de la propiedad numref5.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setNumref5(String value) {
                        this.numref5 = value;
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
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="doc_tributario"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="nro_serie_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tipo_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="neto_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="iva_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="total_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="detalles_doc_tributario"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded"&gt;
     *                             &lt;element name="detalle_doc_tributario"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="porc_descto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "docTributario"
    })
    public static class DocsTributario {

        @XmlElement(name = "doc_tributario", required = true)
        protected List<PLProveedor.DocsTributario.DocTributario> docTributario;

        /**
         * Gets the value of the docTributario property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the docTributario property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDocTributario().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PLProveedor.DocsTributario.DocTributario }
         * 
         * 
         */
        public List<PLProveedor.DocsTributario.DocTributario> getDocTributario() {
            if (docTributario == null) {
                docTributario = new ArrayList<PLProveedor.DocsTributario.DocTributario>();
            }
            return this.docTributario;
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
         *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="no_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="nro_serie_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tipo_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="neto_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="iva_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="total_doc_tributario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="numref1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="numref2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="detalles_doc_tributario"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded"&gt;
         *                   &lt;element name="detalle_doc_tributario"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="porc_descto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "rut",
            "noDocTributario",
            "nroSerieDocTributario",
            "fechaDocTributario",
            "tipoDocTributario",
            "netoDocTributario",
            "ivaDocTributario",
            "totalDocTributario",
            "numref1",
            "numref2",
            "detallesDocTributario"
        })
        public static class DocTributario {

            protected String rut;
            @XmlElement(name = "no_doc_tributario")
            protected String noDocTributario;
            @XmlElement(name = "nro_serie_doc_tributario")
            protected String nroSerieDocTributario;
            @XmlElement(name = "fecha_doc_tributario")
            protected String fechaDocTributario;
            @XmlElement(name = "tipo_doc_tributario")
            protected String tipoDocTributario;
            @XmlElement(name = "neto_doc_tributario")
            protected String netoDocTributario;
            @XmlElement(name = "iva_doc_tributario")
            protected String ivaDocTributario;
            @XmlElement(name = "total_doc_tributario")
            protected String totalDocTributario;
            protected String numref1;
            protected String numref2;
            @XmlElement(name = "detalles_doc_tributario", required = true)
            protected PLProveedor.DocsTributario.DocTributario.DetallesDocTributario detallesDocTributario;

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
             * Obtiene el valor de la propiedad noDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNoDocTributario() {
                return noDocTributario;
            }

            /**
             * Define el valor de la propiedad noDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNoDocTributario(String value) {
                this.noDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad nroSerieDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNroSerieDocTributario() {
                return nroSerieDocTributario;
            }

            /**
             * Define el valor de la propiedad nroSerieDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNroSerieDocTributario(String value) {
                this.nroSerieDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaDocTributario() {
                return fechaDocTributario;
            }

            /**
             * Define el valor de la propiedad fechaDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaDocTributario(String value) {
                this.fechaDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad tipoDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoDocTributario() {
                return tipoDocTributario;
            }

            /**
             * Define el valor de la propiedad tipoDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoDocTributario(String value) {
                this.tipoDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad netoDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNetoDocTributario() {
                return netoDocTributario;
            }

            /**
             * Define el valor de la propiedad netoDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNetoDocTributario(String value) {
                this.netoDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad ivaDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIvaDocTributario() {
                return ivaDocTributario;
            }

            /**
             * Define el valor de la propiedad ivaDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIvaDocTributario(String value) {
                this.ivaDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad totalDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalDocTributario() {
                return totalDocTributario;
            }

            /**
             * Define el valor de la propiedad totalDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalDocTributario(String value) {
                this.totalDocTributario = value;
            }

            /**
             * Obtiene el valor de la propiedad numref1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumref1() {
                return numref1;
            }

            /**
             * Define el valor de la propiedad numref1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumref1(String value) {
                this.numref1 = value;
            }

            /**
             * Obtiene el valor de la propiedad numref2.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumref2() {
                return numref2;
            }

            /**
             * Define el valor de la propiedad numref2.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumref2(String value) {
                this.numref2 = value;
            }

            /**
             * Obtiene el valor de la propiedad detallesDocTributario.
             * 
             * @return
             *     possible object is
             *     {@link PLProveedor.DocsTributario.DocTributario.DetallesDocTributario }
             *     
             */
            public PLProveedor.DocsTributario.DocTributario.DetallesDocTributario getDetallesDocTributario() {
                return detallesDocTributario;
            }

            /**
             * Define el valor de la propiedad detallesDocTributario.
             * 
             * @param value
             *     allowed object is
             *     {@link PLProveedor.DocsTributario.DocTributario.DetallesDocTributario }
             *     
             */
            public void setDetallesDocTributario(PLProveedor.DocsTributario.DocTributario.DetallesDocTributario value) {
                this.detallesDocTributario = value;
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
             *         &lt;element name="detalle_doc_tributario"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="porc_descto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "detalleDocTributario"
            })
            public static class DetallesDocTributario {

                @XmlElement(name = "detalle_doc_tributario", required = true)
                protected List<PLProveedor.DocsTributario.DocTributario.DetallesDocTributario.DetalleDocTributario> detalleDocTributario;

                /**
                 * Gets the value of the detalleDocTributario property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the detalleDocTributario property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDetalleDocTributario().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link PLProveedor.DocsTributario.DocTributario.DetallesDocTributario.DetalleDocTributario }
                 * 
                 * 
                 */
                public List<PLProveedor.DocsTributario.DocTributario.DetallesDocTributario.DetalleDocTributario> getDetalleDocTributario() {
                    if (detalleDocTributario == null) {
                        detalleDocTributario = new ArrayList<PLProveedor.DocsTributario.DocTributario.DetallesDocTributario.DetalleDocTributario>();
                    }
                    return this.detalleDocTributario;
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
                 *         &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cod_prod_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="desc_producto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="costo_unitario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="porc_descto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "codProdProveedor",
                    "codProdCliente",
                    "descProducto",
                    "cantidad",
                    "costoUnitario",
                    "porcDescto",
                    "costoFinal"
                })
                public static class DetalleDocTributario {

                    @XmlElement(name = "cod_prod_proveedor")
                    protected String codProdProveedor;
                    @XmlElement(name = "cod_prod_cliente")
                    protected String codProdCliente;
                    @XmlElement(name = "desc_producto")
                    protected String descProducto;
                    protected String cantidad;
                    @XmlElement(name = "costo_unitario")
                    protected String costoUnitario;
                    @XmlElement(name = "porc_descto")
                    protected String porcDescto;
                    @XmlElement(name = "costo_final")
                    protected String costoFinal;

                    /**
                     * Obtiene el valor de la propiedad codProdProveedor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdProveedor() {
                        return codProdProveedor;
                    }

                    /**
                     * Define el valor de la propiedad codProdProveedor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdProveedor(String value) {
                        this.codProdProveedor = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad codProdCliente.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdCliente() {
                        return codProdCliente;
                    }

                    /**
                     * Define el valor de la propiedad codProdCliente.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdCliente(String value) {
                        this.codProdCliente = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descProducto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescProducto() {
                        return descProducto;
                    }

                    /**
                     * Define el valor de la propiedad descProducto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescProducto(String value) {
                        this.descProducto = value;
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

                    /**
                     * Obtiene el valor de la propiedad costoUnitario.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCostoUnitario() {
                        return costoUnitario;
                    }

                    /**
                     * Define el valor de la propiedad costoUnitario.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCostoUnitario(String value) {
                        this.costoUnitario = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad porcDescto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPorcDescto() {
                        return porcDescto;
                    }

                    /**
                     * Define el valor de la propiedad porcDescto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPorcDescto(String value) {
                        this.porcDescto = value;
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
     *       &lt;sequence maxOccurs="unbounded"&gt;
     *         &lt;element name="entrega"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="total_bultos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="total_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="total_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="total_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="consolidado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="detalles"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded"&gt;
     *                             &lt;element name="detalle"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="saldo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "entrega"
    })
    public static class Entregas {

        @XmlElement(required = true)
        protected List<PLProveedor.Entregas.Entrega> entrega;

        /**
         * Gets the value of the entrega property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entrega property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntrega().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PLProveedor.Entregas.Entrega }
         * 
         * 
         */
        public List<PLProveedor.Entregas.Entrega> getEntrega() {
            if (entrega == null) {
                entrega = new ArrayList<PLProveedor.Entregas.Entrega>();
            }
            return this.entrega;
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
         *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="total_bultos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="total_pedido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="total_unidades" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="total_cajas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="consolidado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="detalles"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded"&gt;
         *                   &lt;element name="detalle"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="saldo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "numero",
            "totalBultos",
            "totalPedido",
            "totalUnidades",
            "totalCajas",
            "consolidado",
            "detalles"
        })
        public static class Entrega {

            protected String numero;
            @XmlElement(name = "total_bultos")
            protected String totalBultos;
            @XmlElement(name = "total_pedido")
            protected String totalPedido;
            @XmlElement(name = "total_unidades")
            protected String totalUnidades;
            @XmlElement(name = "total_cajas")
            protected String totalCajas;
            protected String consolidado;
            @XmlElement(required = true)
            protected PLProveedor.Entregas.Entrega.Detalles detalles;

            /**
             * Obtiene el valor de la propiedad numero.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumero() {
                return numero;
            }

            /**
             * Define el valor de la propiedad numero.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumero(String value) {
                this.numero = value;
            }

            /**
             * Obtiene el valor de la propiedad totalBultos.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalBultos() {
                return totalBultos;
            }

            /**
             * Define el valor de la propiedad totalBultos.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalBultos(String value) {
                this.totalBultos = value;
            }

            /**
             * Obtiene el valor de la propiedad totalPedido.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalPedido() {
                return totalPedido;
            }

            /**
             * Define el valor de la propiedad totalPedido.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalPedido(String value) {
                this.totalPedido = value;
            }

            /**
             * Obtiene el valor de la propiedad totalUnidades.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalUnidades() {
                return totalUnidades;
            }

            /**
             * Define el valor de la propiedad totalUnidades.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalUnidades(String value) {
                this.totalUnidades = value;
            }

            /**
             * Obtiene el valor de la propiedad totalCajas.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTotalCajas() {
                return totalCajas;
            }

            /**
             * Define el valor de la propiedad totalCajas.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTotalCajas(String value) {
                this.totalCajas = value;
            }

            /**
             * Obtiene el valor de la propiedad consolidado.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getConsolidado() {
                return consolidado;
            }

            /**
             * Define el valor de la propiedad consolidado.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setConsolidado(String value) {
                this.consolidado = value;
            }

            /**
             * Obtiene el valor de la propiedad detalles.
             * 
             * @return
             *     possible object is
             *     {@link PLProveedor.Entregas.Entrega.Detalles }
             *     
             */
            public PLProveedor.Entregas.Entrega.Detalles getDetalles() {
                return detalles;
            }

            /**
             * Define el valor de la propiedad detalles.
             * 
             * @param value
             *     allowed object is
             *     {@link PLProveedor.Entregas.Entrega.Detalles }
             *     
             */
            public void setDetalles(PLProveedor.Entregas.Entrega.Detalles value) {
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
             *       &lt;sequence maxOccurs="unbounded"&gt;
             *         &lt;element name="detalle"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="saldo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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

                @XmlElement(required = true)
                protected List<PLProveedor.Entregas.Entrega.Detalles.Detalle> detalle;

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
                 * {@link PLProveedor.Entregas.Entrega.Detalles.Detalle }
                 * 
                 * 
                 */
                public List<PLProveedor.Entregas.Entrega.Detalles.Detalle> getDetalle() {
                    if (detalle == null) {
                        detalle = new ArrayList<PLProveedor.Entregas.Entrega.Detalles.Detalle>();
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
                 *         &lt;element name="cod_prod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="peso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="saldo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "codProdProveedor",
                    "peso",
                    "saldo"
                })
                public static class Detalle {

                    @XmlElement(name = "cod_prod_proveedor")
                    protected String codProdProveedor;
                    protected String peso;
                    protected String saldo;

                    /**
                     * Obtiene el valor de la propiedad codProdProveedor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCodProdProveedor() {
                        return codProdProveedor;
                    }

                    /**
                     * Define el valor de la propiedad codProdProveedor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCodProdProveedor(String value) {
                        this.codProdProveedor = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad peso.
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
                     * Define el valor de la propiedad peso.
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
                     * Obtiene el valor de la propiedad saldo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSaldo() {
                        return saldo;
                    }

                    /**
                     * Define el valor de la propiedad saldo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSaldo(String value) {
                        this.saldo = value;
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
     *       &lt;sequence&gt;
     *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "codigo",
        "razonSocial",
        "contacto",
        "direccion",
        "telefono"
    })
    public static class Vendedor {

        @XmlElement(required = true)
        protected String codigo;
        @XmlElement(name = "razon_social")
        protected String razonSocial;
        protected String contacto;
        protected String direccion;
        protected String telefono;

        /**
         * Obtiene el valor de la propiedad codigo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodigo() {
            return codigo;
        }

        /**
         * Define el valor de la propiedad codigo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodigo(String value) {
            this.codigo = value;
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

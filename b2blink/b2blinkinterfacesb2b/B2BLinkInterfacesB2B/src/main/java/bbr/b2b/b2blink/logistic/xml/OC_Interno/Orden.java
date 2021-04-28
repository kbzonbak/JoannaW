//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0.1 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2021.03.09 a las 09:01:39 AM CLT 
//


package bbr.b2b.b2blink.logistic.xml.OC_Interno;

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
 *         &lt;element name="flowid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ticket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nombreportal" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="no" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="nro_boleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="nosolicitud" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="num_ref_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="num_ref_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="num_ref_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="estado_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="estado_oc_bbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tipo_oc_bbr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VeV" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="fecha_vigencia" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="fecha_vto" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="fecha_compromiso" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="codigo_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cod_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="desc_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="desc_local_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="forma_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="comentarios" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="responsable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="email_responsable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="seccion"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="cod_seccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="seccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="edi_data"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="senderIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="recipientIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="correlativeVendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="messageReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="countrycode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="buyerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="billToPartner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                   &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *                   &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="contacto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="cliente" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
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
 *         &lt;element name="desc_generales"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="cargos_generales"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="action" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
 *                             &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                             &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *                             &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_entrega_producto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                             &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="costo_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="peso_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="num_ref_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="num_ref_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="num_ref_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="costo_con_impuesto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *                             &lt;element name="desc_producto"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                                       &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
 *                                       &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
 *                             &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *                             &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cantidad_endespacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cantidad_pendiente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "flowid",
    "ticket",
    "nombreportal",
    "no",
    "nroBoleta",
    "digitoVerificador",
    "nosolicitud",
    "numRef1",
    "numRef2",
    "numRef3",
    "estadoOc",
    "estadoOcBbr",
    "tipoOc",
    "tipoOcBbr",
    "veV",
    "fechaEmision",
    "fechaVigencia",
    "fechaVto",
    "fechaCompromiso",
    "total",
    "codigoLocalVenta",
    "codLocalEntrega",
    "descLocalVenta",
    "descLocalEntrega",
    "formaPago",
    "comentarios",
    "responsable",
    "emailResponsable",
    "moneda",
    "seccion",
    "ediData",
    "comprador",
    "vendedor",
    "cliente",
    "locales",
    "descGenerales",
    "cargosGenerales",
    "action",
    "detalles",
    "predistribuciones"
})
@XmlRootElement(name = "orden")
public class Orden {

    protected String flowid;
    protected String ticket;
    @XmlElement(required = true)
    protected String nombreportal;
    protected long no;
    @XmlElement(name = "nro_boleta")
    protected String nroBoleta;
    @XmlElement(name = "digito_verificador")
    protected int digitoVerificador;
    protected String nosolicitud;
    @XmlElement(name = "num_ref_1")
    protected String numRef1;
    @XmlElement(name = "num_ref_2")
    protected String numRef2;
    @XmlElement(name = "num_ref_3")
    protected String numRef3;
    @XmlElement(name = "estado_oc", required = true)
    protected String estadoOc;
    @XmlElement(name = "estado_oc_bbr")
    protected String estadoOcBbr;
    @XmlElement(name = "tipo_oc", required = true)
    protected String tipoOc;
    @XmlElement(name = "tipo_oc_bbr")
    protected String tipoOcBbr;
    @XmlElement(name = "VeV")
    protected Boolean veV;
    @XmlElement(name = "fecha_emision", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaEmision;
    @XmlElement(name = "fecha_vigencia", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaVigencia;
    @XmlElement(name = "fecha_vto", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaVto;
    @XmlElement(name = "fecha_compromiso")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaCompromiso;
    protected double total;
    @XmlElement(name = "codigo_local_venta")
    protected String codigoLocalVenta;
    @XmlElement(name = "cod_local_entrega", required = true)
    protected String codLocalEntrega;
    @XmlElement(name = "desc_local_venta")
    protected String descLocalVenta;
    @XmlElement(name = "desc_local_entrega")
    protected String descLocalEntrega;
    @XmlElement(name = "forma_pago")
    protected String formaPago;
    @XmlElement(required = true)
    protected String comentarios;
    @XmlElement(required = true)
    protected String responsable;
    @XmlElement(name = "email_responsable")
    protected String emailResponsable;
    protected String moneda;
    @XmlElement(required = true)
    protected Orden.Seccion seccion;
    @XmlElement(name = "edi_data", required = true)
    protected Orden.EdiData ediData;
    @XmlElement(required = true)
    protected Orden.Comprador comprador;
    @XmlElement(required = true)
    protected Orden.Vendedor vendedor;
    protected Orden.Cliente cliente;
    @XmlElement(required = true)
    protected Orden.Locales locales;
    @XmlElement(name = "desc_generales", required = true)
    protected Orden.DescGenerales descGenerales;
    @XmlElement(name = "cargos_generales", required = true)
    protected Orden.CargosGenerales cargosGenerales;
    protected Orden.Action action;
    @XmlElement(required = true)
    protected Orden.Detalles detalles;
    @XmlElement(required = true)
    protected Orden.Predistribuciones predistribuciones;

    /**
     * Obtiene el valor de la propiedad flowid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlowid() {
        return flowid;
    }

    /**
     * Define el valor de la propiedad flowid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlowid(String value) {
        this.flowid = value;
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
     * Obtiene el valor de la propiedad no.
     * 
     */
    public long getNo() {
        return no;
    }

    /**
     * Define el valor de la propiedad no.
     * 
     */
    public void setNo(long value) {
        this.no = value;
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
     * Obtiene el valor de la propiedad digitoVerificador.
     * 
     */
    public int getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * Define el valor de la propiedad digitoVerificador.
     * 
     */
    public void setDigitoVerificador(int value) {
        this.digitoVerificador = value;
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
     * Obtiene el valor de la propiedad numRef1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRef1() {
        return numRef1;
    }

    /**
     * Define el valor de la propiedad numRef1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRef1(String value) {
        this.numRef1 = value;
    }

    /**
     * Obtiene el valor de la propiedad numRef2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRef2() {
        return numRef2;
    }

    /**
     * Define el valor de la propiedad numRef2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRef2(String value) {
        this.numRef2 = value;
    }

    /**
     * Obtiene el valor de la propiedad numRef3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumRef3() {
        return numRef3;
    }

    /**
     * Define el valor de la propiedad numRef3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumRef3(String value) {
        this.numRef3 = value;
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
     * Obtiene el valor de la propiedad estadoOcBbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoOcBbr() {
        return estadoOcBbr;
    }

    /**
     * Define el valor de la propiedad estadoOcBbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoOcBbr(String value) {
        this.estadoOcBbr = value;
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
     * Obtiene el valor de la propiedad tipoOcBbr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOcBbr() {
        return tipoOcBbr;
    }

    /**
     * Define el valor de la propiedad tipoOcBbr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOcBbr(String value) {
        this.tipoOcBbr = value;
    }

    /**
     * Obtiene el valor de la propiedad veV.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVeV() {
        return veV;
    }

    /**
     * Define el valor de la propiedad veV.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVeV(Boolean value) {
        this.veV = value;
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
     * Obtiene el valor de la propiedad descLocalVenta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescLocalVenta() {
        return descLocalVenta;
    }

    /**
     * Define el valor de la propiedad descLocalVenta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescLocalVenta(String value) {
        this.descLocalVenta = value;
    }

    /**
     * Obtiene el valor de la propiedad descLocalEntrega.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescLocalEntrega() {
        return descLocalEntrega;
    }

    /**
     * Define el valor de la propiedad descLocalEntrega.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescLocalEntrega(String value) {
        this.descLocalEntrega = value;
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
     * Obtiene el valor de la propiedad comentarios.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Define el valor de la propiedad comentarios.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComentarios(String value) {
        this.comentarios = value;
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
     * Obtiene el valor de la propiedad emailResponsable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailResponsable() {
        return emailResponsable;
    }

    /**
     * Define el valor de la propiedad emailResponsable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailResponsable(String value) {
        this.emailResponsable = value;
    }

    /**
     * Obtiene el valor de la propiedad moneda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Define el valor de la propiedad moneda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Obtiene el valor de la propiedad seccion.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Seccion }
     *     
     */
    public Orden.Seccion getSeccion() {
        return seccion;
    }

    /**
     * Define el valor de la propiedad seccion.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Seccion }
     *     
     */
    public void setSeccion(Orden.Seccion value) {
        this.seccion = value;
    }

    /**
     * Obtiene el valor de la propiedad ediData.
     * 
     * @return
     *     possible object is
     *     {@link Orden.EdiData }
     *     
     */
    public Orden.EdiData getEdiData() {
        return ediData;
    }

    /**
     * Define el valor de la propiedad ediData.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.EdiData }
     *     
     */
    public void setEdiData(Orden.EdiData value) {
        this.ediData = value;
    }

    /**
     * Obtiene el valor de la propiedad comprador.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Comprador }
     *     
     */
    public Orden.Comprador getComprador() {
        return comprador;
    }

    /**
     * Define el valor de la propiedad comprador.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Comprador }
     *     
     */
    public void setComprador(Orden.Comprador value) {
        this.comprador = value;
    }

    /**
     * Obtiene el valor de la propiedad vendedor.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Vendedor }
     *     
     */
    public Orden.Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * Define el valor de la propiedad vendedor.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Vendedor }
     *     
     */
    public void setVendedor(Orden.Vendedor value) {
        this.vendedor = value;
    }

    /**
     * Obtiene el valor de la propiedad cliente.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Cliente }
     *     
     */
    public Orden.Cliente getCliente() {
        return cliente;
    }

    /**
     * Define el valor de la propiedad cliente.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Cliente }
     *     
     */
    public void setCliente(Orden.Cliente value) {
        this.cliente = value;
    }

    /**
     * Obtiene el valor de la propiedad locales.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Locales }
     *     
     */
    public Orden.Locales getLocales() {
        return locales;
    }

    /**
     * Define el valor de la propiedad locales.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Locales }
     *     
     */
    public void setLocales(Orden.Locales value) {
        this.locales = value;
    }

    /**
     * Obtiene el valor de la propiedad descGenerales.
     * 
     * @return
     *     possible object is
     *     {@link Orden.DescGenerales }
     *     
     */
    public Orden.DescGenerales getDescGenerales() {
        return descGenerales;
    }

    /**
     * Define el valor de la propiedad descGenerales.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.DescGenerales }
     *     
     */
    public void setDescGenerales(Orden.DescGenerales value) {
        this.descGenerales = value;
    }

    /**
     * Obtiene el valor de la propiedad cargosGenerales.
     * 
     * @return
     *     possible object is
     *     {@link Orden.CargosGenerales }
     *     
     */
    public Orden.CargosGenerales getCargosGenerales() {
        return cargosGenerales;
    }

    /**
     * Define el valor de la propiedad cargosGenerales.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.CargosGenerales }
     *     
     */
    public void setCargosGenerales(Orden.CargosGenerales value) {
        this.cargosGenerales = value;
    }

    /**
     * Obtiene el valor de la propiedad action.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Action }
     *     
     */
    public Orden.Action getAction() {
        return action;
    }

    /**
     * Define el valor de la propiedad action.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Action }
     *     
     */
    public void setAction(Orden.Action value) {
        this.action = value;
    }

    /**
     * Obtiene el valor de la propiedad detalles.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Detalles }
     *     
     */
    public Orden.Detalles getDetalles() {
        return detalles;
    }

    /**
     * Define el valor de la propiedad detalles.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Detalles }
     *     
     */
    public void setDetalles(Orden.Detalles value) {
        this.detalles = value;
    }

    /**
     * Obtiene el valor de la propiedad predistribuciones.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Predistribuciones }
     *     
     */
    public Orden.Predistribuciones getPredistribuciones() {
        return predistribuciones;
    }

    /**
     * Define el valor de la propiedad predistribuciones.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Predistribuciones }
     *     
     */
    public void setPredistribuciones(Orden.Predistribuciones value) {
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
     *       &lt;sequence&gt;
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "code",
        "description"
    })
    public static class Action {

        protected String code;
        protected String description;

        /**
         * Obtiene el valor de la propiedad code.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Define el valor de la propiedad code.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Obtiene el valor de la propiedad description.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Define el valor de la propiedad description.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
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
     *         &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
    public static class CargosGenerales {

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
     *       &lt;sequence&gt;
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
     *         &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "digitoVerificador",
        "razonSocial",
        "unidadNegocio"
    })
    public static class Comprador {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "digito_verificador")
        protected String digitoVerificador;
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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
    public static class DescGenerales {

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
     *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *                   &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
     *                   &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_entrega_producto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *                   &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="costo_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="peso_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="num_ref_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="num_ref_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="num_ref_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="costo_con_impuesto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
     *                   &lt;element name="desc_producto"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *                             &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
     *                             &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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

        @XmlElement(required = true)
        protected List<Orden.Detalles.Detalle> detalle;

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
         * {@link Orden.Detalles.Detalle }
         * 
         * 
         */
        public List<Orden.Detalles.Detalle> getDetalle() {
            if (detalle == null) {
                detalle = new ArrayList<Orden.Detalles.Detalle>();
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
         *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_prod_comp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_prod_vendedor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ean13" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="descripcion_prod" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="desc_empaque" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="desc_um_unidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="prod_empaque" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
         *         &lt;element name="cantidad_empaque" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="innerpack" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="costo_final" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
         *         &lt;element name="precio_lista" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="rubro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ean3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="desc_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="marca_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_entrega_producto" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
         *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="costo_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="peso_flete" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="num_ref_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="num_ref_2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="num_ref_3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="costo_con_impuesto" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
         *         &lt;element name="desc_producto"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
         *                   &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
         *                   &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
            "item",
            "codProdComp",
            "codProdVendedor",
            "ean13",
            "descripcionProd",
            "codEmpaque",
            "descEmpaque",
            "descUmUnidad",
            "prodEmpaque",
            "cantidadEmpaque",
            "innerpack",
            "costoLista",
            "costoFinal",
            "precioLista",
            "rubro",
            "ean1",
            "ean2",
            "ean3",
            "codEstilo",
            "descEstilo",
            "marcaEstilo",
            "tolerancia",
            "fechaEntregaProducto",
            "observacion",
            "costoFlete",
            "pesoFlete",
            "numRef1",
            "numRef2",
            "numRef3",
            "costoConImpuesto",
            "descProducto",
            "cargosProd"
        })
        public static class Detalle {

            protected int item;
            @XmlElement(name = "cod_prod_comp", required = true)
            protected String codProdComp;
            @XmlElement(name = "cod_prod_vendedor", required = true)
            protected String codProdVendedor;
            @XmlElement(required = true)
            protected String ean13;
            @XmlElement(name = "descripcion_prod", required = true)
            protected String descripcionProd;
            @XmlElement(name = "cod_empaque")
            protected String codEmpaque;
            @XmlElement(name = "desc_empaque")
            protected String descEmpaque;
            @XmlElement(name = "desc_um_unidad")
            protected String descUmUnidad;
            @XmlElement(name = "prod_empaque")
            protected float prodEmpaque;
            @XmlElement(name = "cantidad_empaque")
            protected int cantidadEmpaque;
            protected Integer innerpack;
            @XmlElement(name = "costo_lista")
            protected double costoLista;
            @XmlElement(name = "costo_final")
            protected double costoFinal;
            @XmlElement(name = "precio_lista")
            protected Double precioLista;
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
            protected String tolerancia;
            @XmlElement(name = "fecha_entrega_producto")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fechaEntregaProducto;
            protected String observacion;
            @XmlElement(name = "costo_flete")
            protected Double costoFlete;
            @XmlElement(name = "peso_flete")
            protected Double pesoFlete;
            @XmlElement(name = "num_ref_1")
            protected String numRef1;
            @XmlElement(name = "num_ref_2")
            protected String numRef2;
            @XmlElement(name = "num_ref_3")
            protected String numRef3;
            @XmlElement(name = "costo_con_impuesto")
            protected Double costoConImpuesto;
            @XmlElement(name = "desc_producto", required = true)
            protected Orden.Detalles.Detalle.DescProducto descProducto;
            @XmlElement(name = "cargos_prod", required = true)
            protected Orden.Detalles.Detalle.CargosProd cargosProd;

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
             * Obtiene el valor de la propiedad codProdComp.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProdComp() {
                return codProdComp;
            }

            /**
             * Define el valor de la propiedad codProdComp.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProdComp(String value) {
                this.codProdComp = value;
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
             * Obtiene el valor de la propiedad codEmpaque.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodEmpaque() {
                return codEmpaque;
            }

            /**
             * Define el valor de la propiedad codEmpaque.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodEmpaque(String value) {
                this.codEmpaque = value;
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
             * Obtiene el valor de la propiedad costoLista.
             * 
             */
            public double getCostoLista() {
                return costoLista;
            }

            /**
             * Define el valor de la propiedad costoLista.
             * 
             */
            public void setCostoLista(double value) {
                this.costoLista = value;
            }

            /**
             * Obtiene el valor de la propiedad costoFinal.
             * 
             */
            public double getCostoFinal() {
                return costoFinal;
            }

            /**
             * Define el valor de la propiedad costoFinal.
             * 
             */
            public void setCostoFinal(double value) {
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
             * Obtiene el valor de la propiedad tolerancia.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTolerancia() {
                return tolerancia;
            }

            /**
             * Define el valor de la propiedad tolerancia.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTolerancia(String value) {
                this.tolerancia = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaEntregaProducto.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getFechaEntregaProducto() {
                return fechaEntregaProducto;
            }

            /**
             * Define el valor de la propiedad fechaEntregaProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setFechaEntregaProducto(XMLGregorianCalendar value) {
                this.fechaEntregaProducto = value;
            }

            /**
             * Obtiene el valor de la propiedad observacion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getObservacion() {
                return observacion;
            }

            /**
             * Define el valor de la propiedad observacion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setObservacion(String value) {
                this.observacion = value;
            }

            /**
             * Obtiene el valor de la propiedad costoFlete.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getCostoFlete() {
                return costoFlete;
            }

            /**
             * Define el valor de la propiedad costoFlete.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setCostoFlete(Double value) {
                this.costoFlete = value;
            }

            /**
             * Obtiene el valor de la propiedad pesoFlete.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getPesoFlete() {
                return pesoFlete;
            }

            /**
             * Define el valor de la propiedad pesoFlete.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setPesoFlete(Double value) {
                this.pesoFlete = value;
            }

            /**
             * Obtiene el valor de la propiedad numRef1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumRef1() {
                return numRef1;
            }

            /**
             * Define el valor de la propiedad numRef1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumRef1(String value) {
                this.numRef1 = value;
            }

            /**
             * Obtiene el valor de la propiedad numRef2.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumRef2() {
                return numRef2;
            }

            /**
             * Define el valor de la propiedad numRef2.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumRef2(String value) {
                this.numRef2 = value;
            }

            /**
             * Obtiene el valor de la propiedad numRef3.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumRef3() {
                return numRef3;
            }

            /**
             * Define el valor de la propiedad numRef3.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumRef3(String value) {
                this.numRef3 = value;
            }

            /**
             * Obtiene el valor de la propiedad costoConImpuesto.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getCostoConImpuesto() {
                return costoConImpuesto;
            }

            /**
             * Define el valor de la propiedad costoConImpuesto.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setCostoConImpuesto(Double value) {
                this.costoConImpuesto = value;
            }

            /**
             * Obtiene el valor de la propiedad descProducto.
             * 
             * @return
             *     possible object is
             *     {@link Orden.Detalles.Detalle.DescProducto }
             *     
             */
            public Orden.Detalles.Detalle.DescProducto getDescProducto() {
                return descProducto;
            }

            /**
             * Define el valor de la propiedad descProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link Orden.Detalles.Detalle.DescProducto }
             *     
             */
            public void setDescProducto(Orden.Detalles.Detalle.DescProducto value) {
                this.descProducto = value;
            }

            /**
             * Obtiene el valor de la propiedad cargosProd.
             * 
             * @return
             *     possible object is
             *     {@link Orden.Detalles.Detalle.CargosProd }
             *     
             */
            public Orden.Detalles.Detalle.CargosProd getCargosProd() {
                return cargosProd;
            }

            /**
             * Define el valor de la propiedad cargosProd.
             * 
             * @param value
             *     allowed object is
             *     {@link Orden.Detalles.Detalle.CargosProd }
             *     
             */
            public void setCargosProd(Orden.Detalles.Detalle.CargosProd value) {
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
             *         &lt;element name="cargo" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
             *         &lt;element name="descuento" type="{http://www.bbr.cl/cencosud/OCInterno}cargo_descuento"/&gt;
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
     *         &lt;element name="correlativeVendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="messageReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="countrycode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="buyerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="buyerLocationCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="billToPartner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "correlativeVendor",
        "messageReferenceNumber",
        "countrycode",
        "buyerCode",
        "buyerLocationCode",
        "billToPartner"
    })
    public static class EdiData {

        @XmlElement(required = true)
        protected String senderIdentification;
        @XmlElement(required = true)
        protected String recipientIdentification;
        protected String correlativeVendor;
        @XmlElement(required = true)
        protected String messageReferenceNumber;
        @XmlElement(required = true)
        protected String countrycode;
        @XmlElement(required = true)
        protected String buyerCode;
        @XmlElement(required = true)
        protected String buyerLocationCode;
        protected String billToPartner;

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
         * Obtiene el valor de la propiedad correlativeVendor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelativeVendor() {
            return correlativeVendor;
        }

        /**
         * Define el valor de la propiedad correlativeVendor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelativeVendor(String value) {
            this.correlativeVendor = value;
        }

        /**
         * Obtiene el valor de la propiedad messageReferenceNumber.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMessageReferenceNumber() {
            return messageReferenceNumber;
        }

        /**
         * Define el valor de la propiedad messageReferenceNumber.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMessageReferenceNumber(String value) {
            this.messageReferenceNumber = value;
        }

        /**
         * Obtiene el valor de la propiedad countrycode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCountrycode() {
            return countrycode;
        }

        /**
         * Define el valor de la propiedad countrycode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCountrycode(String value) {
            this.countrycode = value;
        }

        /**
         * Obtiene el valor de la propiedad buyerCode.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBuyerCode() {
            return buyerCode;
        }

        /**
         * Define el valor de la propiedad buyerCode.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBuyerCode(String value) {
            this.buyerCode = value;
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

        /**
         * Obtiene el valor de la propiedad billToPartner.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBillToPartner() {
            return billToPartner;
        }

        /**
         * Define el valor de la propiedad billToPartner.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBillToPartner(String value) {
            this.billToPartner = value;
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
        protected List<Orden.Locales.Local> local;

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
         * {@link Orden.Locales.Local }
         * 
         * 
         */
        public List<Orden.Locales.Local> getLocal() {
            if (local == null) {
                local = new ArrayList<Orden.Locales.Local>();
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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="predistribucion"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
     *                   &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cantidad_endespacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cantidad_pendiente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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

        protected List<Orden.Predistribuciones.Predistribucion> predistribucion;

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
         * {@link Orden.Predistribuciones.Predistribucion }
         * 
         * 
         */
        public List<Orden.Predistribuciones.Predistribucion> getPredistribucion() {
            if (predistribucion == null) {
                predistribucion = new ArrayList<Orden.Predistribuciones.Predistribucion>();
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
         *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
         *         &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="sku" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_estilo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cantidad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cantidad_endespacho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cantidad_recepcionada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cantidad_pendiente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "posicion",
            "codLocal",
            "sku",
            "codEstilo",
            "cantidad",
            "cantidadEndespacho",
            "cantidadRecepcionada",
            "cantidadPendiente"
        })
        public static class Predistribucion {

            protected Integer posicion;
            @XmlElement(name = "cod_local", required = true)
            protected String codLocal;
            @XmlElement(required = true)
            protected String sku;
            @XmlElement(name = "cod_estilo")
            protected String codEstilo;
            @XmlElement(required = true)
            protected String cantidad;
            @XmlElement(name = "cantidad_endespacho")
            protected String cantidadEndespacho;
            @XmlElement(name = "cantidad_recepcionada")
            protected String cantidadRecepcionada;
            @XmlElement(name = "cantidad_pendiente")
            protected String cantidadPendiente;

            /**
             * Obtiene el valor de la propiedad posicion.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getPosicion() {
                return posicion;
            }

            /**
             * Define el valor de la propiedad posicion.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setPosicion(Integer value) {
                this.posicion = value;
            }

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
             * Obtiene el valor de la propiedad cantidadEndespacho.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCantidadEndespacho() {
                return cantidadEndespacho;
            }

            /**
             * Define el valor de la propiedad cantidadEndespacho.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCantidadEndespacho(String value) {
                this.cantidadEndespacho = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidadRecepcionada.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCantidadRecepcionada() {
                return cantidadRecepcionada;
            }

            /**
             * Define el valor de la propiedad cantidadRecepcionada.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCantidadRecepcionada(String value) {
                this.cantidadRecepcionada = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidadPendiente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCantidadPendiente() {
                return cantidadPendiente;
            }

            /**
             * Define el valor de la propiedad cantidadPendiente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCantidadPendiente(String value) {
                this.cantidadPendiente = value;
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
     *         &lt;element name="cod_seccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="seccion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "codSeccion",
        "seccion"
    })
    public static class Seccion {

        @XmlElement(name = "cod_seccion", required = true)
        protected String codSeccion;
        @XmlElement(required = true)
        protected String seccion;

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
     *         &lt;element name="digito_verificador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
        "code",
        "razonSocial",
        "contacto",
        "direccion",
        "telefono"
    })
    public static class Vendedor {

        @XmlElement(required = true)
        protected String rut;
        @XmlElement(name = "digito_verificador")
        protected String digitoVerificador;
        protected String code;
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
         * Obtiene el valor de la propiedad code.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Define el valor de la propiedad code.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
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

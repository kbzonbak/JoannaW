//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.05.11 a las 04:18:49 PM CLT 
//


package bbr.b2b.logistic.xml.order;

import java.math.BigDecimal;
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
 *         &lt;element name="maestro_locales"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="local" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="nombre_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="email_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
 *         &lt;element name="encabezado"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                   &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="monto_neto" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                   &lt;element name="impuesto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                   &lt;element name="monto_orden" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                   &lt;element name="comprador"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="responsable" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="proveedor"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="fecha_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="fecha_vencimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="condicion_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                   &lt;element name="otros_atributos_orden"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                   &lt;element name="datos_vev" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="rut_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="nombre_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="direccion_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="comuna_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="ciudad_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="referencia_venta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="telefono_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="mail_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_despacho_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
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
 *         &lt;element name="descuentos_generales" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                             &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
 *         &lt;element name="listado_productos"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="producto" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_barra1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_barra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="otros_atributos_producto"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="unidad_medida_base" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="unidad_medida_compra" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                             &lt;element name="cantidad_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                             &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                             &lt;element name="fecha_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                             &lt;element name="costo_compra" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                             &lt;element name="precio_normal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                             &lt;element name="precio_oferta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *                             &lt;element name="descuentos" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                                                 &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
 *                             &lt;element name="cargos" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="cargo" maxOccurs="unbounded" minOccurs="0"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="tipo_cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="descripcion_cargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                                 &lt;element name="valor_cargo_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                                                 &lt;element name="valor_cargo_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
 *                 &lt;sequence&gt;
 *                   &lt;element name="predistribucion" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *                             &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="unidades_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
    "maestroLocales",
    "encabezado",
    "descuentosGenerales",
    "listadoProductos",
    "predistribuciones"
})
@XmlRootElement(name = "orden")
public class Orden {

    @XmlElement(name = "maestro_locales", required = true)
    protected Orden.MaestroLocales maestroLocales;
    @XmlElement(required = true)
    protected Orden.Encabezado encabezado;
    @XmlElement(name = "descuentos_generales")
    protected Orden.DescuentosGenerales descuentosGenerales;
    @XmlElement(name = "listado_productos", required = true)
    protected Orden.ListadoProductos listadoProductos;
    @XmlElement(required = true)
    protected Orden.Predistribuciones predistribuciones;

    /**
     * Obtiene el valor de la propiedad maestroLocales.
     * 
     * @return
     *     possible object is
     *     {@link Orden.MaestroLocales }
     *     
     */
    public Orden.MaestroLocales getMaestroLocales() {
        return maestroLocales;
    }

    /**
     * Define el valor de la propiedad maestroLocales.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.MaestroLocales }
     *     
     */
    public void setMaestroLocales(Orden.MaestroLocales value) {
        this.maestroLocales = value;
    }

    /**
     * Obtiene el valor de la propiedad encabezado.
     * 
     * @return
     *     possible object is
     *     {@link Orden.Encabezado }
     *     
     */
    public Orden.Encabezado getEncabezado() {
        return encabezado;
    }

    /**
     * Define el valor de la propiedad encabezado.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.Encabezado }
     *     
     */
    public void setEncabezado(Orden.Encabezado value) {
        this.encabezado = value;
    }

    /**
     * Obtiene el valor de la propiedad descuentosGenerales.
     * 
     * @return
     *     possible object is
     *     {@link Orden.DescuentosGenerales }
     *     
     */
    public Orden.DescuentosGenerales getDescuentosGenerales() {
        return descuentosGenerales;
    }

    /**
     * Define el valor de la propiedad descuentosGenerales.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.DescuentosGenerales }
     *     
     */
    public void setDescuentosGenerales(Orden.DescuentosGenerales value) {
        this.descuentosGenerales = value;
    }

    /**
     * Obtiene el valor de la propiedad listadoProductos.
     * 
     * @return
     *     possible object is
     *     {@link Orden.ListadoProductos }
     *     
     */
    public Orden.ListadoProductos getListadoProductos() {
        return listadoProductos;
    }

    /**
     * Define el valor de la propiedad listadoProductos.
     * 
     * @param value
     *     allowed object is
     *     {@link Orden.ListadoProductos }
     *     
     */
    public void setListadoProductos(Orden.ListadoProductos value) {
        this.listadoProductos = value;
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
     *         &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                   &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
        "descuento"
    })
    public static class DescuentosGenerales {

        protected List<Orden.DescuentosGenerales.Descuento> descuento;

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
         * {@link Orden.DescuentosGenerales.Descuento }
         * 
         * 
         */
        public List<Orden.DescuentosGenerales.Descuento> getDescuento() {
            if (descuento == null) {
                descuento = new ArrayList<Orden.DescuentosGenerales.Descuento>();
            }
            return this.descuento;
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
         *         &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *         &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
            "tipoDescuento",
            "descripcionDescuento",
            "valorDescuentoPorc",
            "valorDescuentoMonto"
        })
        public static class Descuento {

            @XmlElement(name = "tipo_descuento", required = true)
            protected String tipoDescuento;
            @XmlElement(name = "descripcion_descuento")
            protected String descripcionDescuento;
            @XmlElement(name = "valor_descuento_porc", required = true)
            protected BigDecimal valorDescuentoPorc;
            @XmlElement(name = "valor_descuento_monto")
            protected BigDecimal valorDescuentoMonto;

            /**
             * Obtiene el valor de la propiedad tipoDescuento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoDescuento() {
                return tipoDescuento;
            }

            /**
             * Define el valor de la propiedad tipoDescuento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoDescuento(String value) {
                this.tipoDescuento = value;
            }

            /**
             * Obtiene el valor de la propiedad descripcionDescuento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcionDescuento() {
                return descripcionDescuento;
            }

            /**
             * Define el valor de la propiedad descripcionDescuento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcionDescuento(String value) {
                this.descripcionDescuento = value;
            }

            /**
             * Obtiene el valor de la propiedad valorDescuentoPorc.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValorDescuentoPorc() {
                return valorDescuentoPorc;
            }

            /**
             * Define el valor de la propiedad valorDescuentoPorc.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValorDescuentoPorc(BigDecimal value) {
                this.valorDescuentoPorc = value;
            }

            /**
             * Obtiene el valor de la propiedad valorDescuentoMonto.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValorDescuentoMonto() {
                return valorDescuentoMonto;
            }

            /**
             * Define el valor de la propiedad valorDescuentoMonto.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValorDescuentoMonto(BigDecimal value) {
                this.valorDescuentoMonto = value;
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
     *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="num_oc" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *         &lt;element name="tipo_oc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="monto_neto" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *         &lt;element name="impuesto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *         &lt;element name="monto_orden" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *         &lt;element name="comprador"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="responsable" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="proveedor"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="fecha_emision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="fecha_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="fecha_vencimiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="condicion_pago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="local_entrega" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *         &lt;element name="otros_atributos_orden"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *         &lt;element name="datos_vev" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="rut_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="direccion_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="comuna_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="ciudad_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="referencia_venta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="telefono_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="mail_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_despacho_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
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
        "accion",
        "numOc",
        "tipoOc",
        "montoNeto",
        "impuesto",
        "montoOrden",
        "comprador",
        "responsable",
        "proveedor",
        "fechaEmision",
        "fechaEntrega",
        "fechaVencimiento",
        "condicionPago",
        "localEntrega",
        "observaciones",
        "otrosAtributosOrden",
        "datosVev"
    })
    public static class Encabezado {

        @XmlElement(required = true)
        protected String accion;
        @XmlElement(name = "num_oc")
        protected long numOc;
        @XmlElement(name = "tipo_oc", required = true)
        protected String tipoOc;
        @XmlElement(name = "monto_neto", required = true)
        protected BigDecimal montoNeto;
        protected BigDecimal impuesto;
        @XmlElement(name = "monto_orden")
        protected BigDecimal montoOrden;
        @XmlElement(required = true)
        protected Orden.Encabezado.Comprador comprador;
        protected Orden.Encabezado.Responsable responsable;
        @XmlElement(required = true)
        protected Orden.Encabezado.Proveedor proveedor;
        @XmlElement(name = "fecha_emision", required = true)
        protected String fechaEmision;
        @XmlElement(name = "fecha_entrega", required = true)
        protected String fechaEntrega;
        @XmlElement(name = "fecha_vencimiento", required = true)
        protected String fechaVencimiento;
        @XmlElement(name = "condicion_pago")
        protected String condicionPago;
        @XmlElement(name = "local_entrega", required = true)
        protected String localEntrega;
        protected String observaciones;
        @XmlElement(name = "otros_atributos_orden", required = true)
        protected Orden.Encabezado.OtrosAtributosOrden otrosAtributosOrden;
        @XmlElement(name = "datos_vev")
        protected Orden.Encabezado.DatosVev datosVev;

        /**
         * Obtiene el valor de la propiedad accion.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccion() {
            return accion;
        }

        /**
         * Define el valor de la propiedad accion.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccion(String value) {
            this.accion = value;
        }

        /**
         * Obtiene el valor de la propiedad numOc.
         * 
         */
        public long getNumOc() {
            return numOc;
        }

        /**
         * Define el valor de la propiedad numOc.
         * 
         */
        public void setNumOc(long value) {
            this.numOc = value;
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
         * Obtiene el valor de la propiedad montoNeto.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMontoNeto() {
            return montoNeto;
        }

        /**
         * Define el valor de la propiedad montoNeto.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMontoNeto(BigDecimal value) {
            this.montoNeto = value;
        }

        /**
         * Obtiene el valor de la propiedad impuesto.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImpuesto() {
            return impuesto;
        }

        /**
         * Define el valor de la propiedad impuesto.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImpuesto(BigDecimal value) {
            this.impuesto = value;
        }

        /**
         * Obtiene el valor de la propiedad montoOrden.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMontoOrden() {
            return montoOrden;
        }

        /**
         * Define el valor de la propiedad montoOrden.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMontoOrden(BigDecimal value) {
            this.montoOrden = value;
        }

        /**
         * Obtiene el valor de la propiedad comprador.
         * 
         * @return
         *     possible object is
         *     {@link Orden.Encabezado.Comprador }
         *     
         */
        public Orden.Encabezado.Comprador getComprador() {
            return comprador;
        }

        /**
         * Define el valor de la propiedad comprador.
         * 
         * @param value
         *     allowed object is
         *     {@link Orden.Encabezado.Comprador }
         *     
         */
        public void setComprador(Orden.Encabezado.Comprador value) {
            this.comprador = value;
        }

        /**
         * Obtiene el valor de la propiedad responsable.
         * 
         * @return
         *     possible object is
         *     {@link Orden.Encabezado.Responsable }
         *     
         */
        public Orden.Encabezado.Responsable getResponsable() {
            return responsable;
        }

        /**
         * Define el valor de la propiedad responsable.
         * 
         * @param value
         *     allowed object is
         *     {@link Orden.Encabezado.Responsable }
         *     
         */
        public void setResponsable(Orden.Encabezado.Responsable value) {
            this.responsable = value;
        }

        /**
         * Obtiene el valor de la propiedad proveedor.
         * 
         * @return
         *     possible object is
         *     {@link Orden.Encabezado.Proveedor }
         *     
         */
        public Orden.Encabezado.Proveedor getProveedor() {
            return proveedor;
        }

        /**
         * Define el valor de la propiedad proveedor.
         * 
         * @param value
         *     allowed object is
         *     {@link Orden.Encabezado.Proveedor }
         *     
         */
        public void setProveedor(Orden.Encabezado.Proveedor value) {
            this.proveedor = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaEmision.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaEmision() {
            return fechaEmision;
        }

        /**
         * Define el valor de la propiedad fechaEmision.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaEmision(String value) {
            this.fechaEmision = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaEntrega.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaEntrega() {
            return fechaEntrega;
        }

        /**
         * Define el valor de la propiedad fechaEntrega.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaEntrega(String value) {
            this.fechaEntrega = value;
        }

        /**
         * Obtiene el valor de la propiedad fechaVencimiento.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFechaVencimiento() {
            return fechaVencimiento;
        }

        /**
         * Define el valor de la propiedad fechaVencimiento.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFechaVencimiento(String value) {
            this.fechaVencimiento = value;
        }

        /**
         * Obtiene el valor de la propiedad condicionPago.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCondicionPago() {
            return condicionPago;
        }

        /**
         * Define el valor de la propiedad condicionPago.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCondicionPago(String value) {
            this.condicionPago = value;
        }

        /**
         * Obtiene el valor de la propiedad localEntrega.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocalEntrega() {
            return localEntrega;
        }

        /**
         * Define el valor de la propiedad localEntrega.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocalEntrega(String value) {
            this.localEntrega = value;
        }

        /**
         * Obtiene el valor de la propiedad observaciones.
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
         * Define el valor de la propiedad observaciones.
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
         * Obtiene el valor de la propiedad otrosAtributosOrden.
         * 
         * @return
         *     possible object is
         *     {@link Orden.Encabezado.OtrosAtributosOrden }
         *     
         */
        public Orden.Encabezado.OtrosAtributosOrden getOtrosAtributosOrden() {
            return otrosAtributosOrden;
        }

        /**
         * Define el valor de la propiedad otrosAtributosOrden.
         * 
         * @param value
         *     allowed object is
         *     {@link Orden.Encabezado.OtrosAtributosOrden }
         *     
         */
        public void setOtrosAtributosOrden(Orden.Encabezado.OtrosAtributosOrden value) {
            this.otrosAtributosOrden = value;
        }

        /**
         * Obtiene el valor de la propiedad datosVev.
         * 
         * @return
         *     possible object is
         *     {@link Orden.Encabezado.DatosVev }
         *     
         */
        public Orden.Encabezado.DatosVev getDatosVev() {
            return datosVev;
        }

        /**
         * Define el valor de la propiedad datosVev.
         * 
         * @param value
         *     allowed object is
         *     {@link Orden.Encabezado.DatosVev }
         *     
         */
        public void setDatosVev(Orden.Encabezado.DatosVev value) {
            this.datosVev = value;
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
         *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "rut",
            "razonSocial"
        })
        public static class Comprador {

            @XmlElement(required = true)
            protected String codigo;
            @XmlElement(required = true)
            protected String rut;
            @XmlElement(name = "razon_social", required = true)
            protected String razonSocial;

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
         *         &lt;element name="rut_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="direccion_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="comuna_cliente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="ciudad_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="comentario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="referencia_venta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_local_venta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="telefono_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="mail_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="fecha_despacho_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="guia_despacho" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
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
            "rutCliente",
            "nombreCliente",
            "direccionCliente",
            "comunaCliente",
            "ciudadCliente",
            "comentario",
            "referenciaVenta",
            "codLocalVenta",
            "telefonoCliente",
            "mailCliente",
            "fechaDespachoCliente",
            "guiaDespacho"
        })
        public static class DatosVev {

            @XmlElement(name = "rut_cliente", required = true)
            protected String rutCliente;
            @XmlElement(name = "nombre_cliente", required = true)
            protected String nombreCliente;
            @XmlElement(name = "direccion_cliente", required = true)
            protected String direccionCliente;
            @XmlElement(name = "comuna_cliente", required = true)
            protected String comunaCliente;
            @XmlElement(name = "ciudad_cliente")
            protected String ciudadCliente;
            protected String comentario;
            @XmlElement(name = "referencia_venta", required = true)
            protected String referenciaVenta;
            @XmlElement(name = "cod_local_venta")
            protected String codLocalVenta;
            @XmlElement(name = "telefono_cliente")
            protected String telefonoCliente;
            @XmlElement(name = "mail_cliente")
            protected String mailCliente;
            @XmlElement(name = "fecha_despacho_cliente")
            protected String fechaDespachoCliente;
            @XmlElement(name = "guia_despacho")
            protected Long guiaDespacho;

            /**
             * Obtiene el valor de la propiedad rutCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRutCliente() {
                return rutCliente;
            }

            /**
             * Define el valor de la propiedad rutCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRutCliente(String value) {
                this.rutCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad nombreCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombreCliente() {
                return nombreCliente;
            }

            /**
             * Define el valor de la propiedad nombreCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombreCliente(String value) {
                this.nombreCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad direccionCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDireccionCliente() {
                return direccionCliente;
            }

            /**
             * Define el valor de la propiedad direccionCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDireccionCliente(String value) {
                this.direccionCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad comunaCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getComunaCliente() {
                return comunaCliente;
            }

            /**
             * Define el valor de la propiedad comunaCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setComunaCliente(String value) {
                this.comunaCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad ciudadCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCiudadCliente() {
                return ciudadCliente;
            }

            /**
             * Define el valor de la propiedad ciudadCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCiudadCliente(String value) {
                this.ciudadCliente = value;
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
             * Obtiene el valor de la propiedad referenciaVenta.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReferenciaVenta() {
                return referenciaVenta;
            }

            /**
             * Define el valor de la propiedad referenciaVenta.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReferenciaVenta(String value) {
                this.referenciaVenta = value;
            }

            /**
             * Obtiene el valor de la propiedad codLocalVenta.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodLocalVenta() {
                return codLocalVenta;
            }

            /**
             * Define el valor de la propiedad codLocalVenta.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodLocalVenta(String value) {
                this.codLocalVenta = value;
            }

            /**
             * Obtiene el valor de la propiedad telefonoCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTelefonoCliente() {
                return telefonoCliente;
            }

            /**
             * Define el valor de la propiedad telefonoCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTelefonoCliente(String value) {
                this.telefonoCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad mailCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMailCliente() {
                return mailCliente;
            }

            /**
             * Define el valor de la propiedad mailCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMailCliente(String value) {
                this.mailCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaDespachoCliente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaDespachoCliente() {
                return fechaDespachoCliente;
            }

            /**
             * Define el valor de la propiedad fechaDespachoCliente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaDespachoCliente(String value) {
                this.fechaDespachoCliente = value;
            }

            /**
             * Obtiene el valor de la propiedad guiaDespacho.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getGuiaDespacho() {
                return guiaDespacho;
            }

            /**
             * Define el valor de la propiedad guiaDespacho.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setGuiaDespacho(Long value) {
                this.guiaDespacho = value;
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
         *         &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "atributo"
        })
        public static class OtrosAtributosOrden {

            protected List<Orden.Encabezado.OtrosAtributosOrden.Atributo> atributo;

            /**
             * Gets the value of the atributo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the atributo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAtributo().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Orden.Encabezado.OtrosAtributosOrden.Atributo }
             * 
             * 
             */
            public List<Orden.Encabezado.OtrosAtributosOrden.Atributo> getAtributo() {
                if (atributo == null) {
                    atributo = new ArrayList<Orden.Encabezado.OtrosAtributosOrden.Atributo>();
                }
                return this.atributo;
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
             *         &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "tipoAtributo",
                "codigo",
                "valor"
            })
            public static class Atributo {

                @XmlElement(name = "tipo_atributo", required = true)
                protected String tipoAtributo;
                @XmlElement(required = true)
                protected String codigo;
                @XmlElement(required = true)
                protected String valor;

                /**
                 * Obtiene el valor de la propiedad tipoAtributo.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoAtributo() {
                    return tipoAtributo;
                }

                /**
                 * Define el valor de la propiedad tipoAtributo.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoAtributo(String value) {
                    this.tipoAtributo = value;
                }

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
                 * Obtiene el valor de la propiedad valor.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValor() {
                    return valor;
                }

                /**
                 * Define el valor de la propiedad valor.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValor(String value) {
                    this.valor = value;
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
         *         &lt;element name="rut" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="razon_social" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="ciudad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "rut",
            "razonSocial",
            "direccion",
            "ciudad",
            "tipo"
        })
        public static class Proveedor {

            @XmlElement(required = true)
            protected String codigo;
            @XmlElement(required = true)
            protected String rut;
            @XmlElement(name = "razon_social", required = true)
            protected String razonSocial;
            protected String direccion;
            protected String ciudad;
            protected String tipo;

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
             * Obtiene el valor de la propiedad tipo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipo() {
                return tipo;
            }

            /**
             * Define el valor de la propiedad tipo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipo(String value) {
                this.tipo = value;
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
         *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "correo"
        })
        public static class Responsable {

            @XmlElement(required = true)
            protected String nombre;
            @XmlElement(required = true)
            protected String correo;

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
             * Obtiene el valor de la propiedad correo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCorreo() {
                return correo;
            }

            /**
             * Define el valor de la propiedad correo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCorreo(String value) {
                this.correo = value;
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
     *         &lt;element name="producto" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_barra1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_barra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="otros_atributos_producto"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *                   &lt;element name="unidad_medida_base" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="unidad_medida_compra" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                   &lt;element name="cantidad_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                   &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                   &lt;element name="fecha_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                   &lt;element name="costo_compra" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                   &lt;element name="precio_normal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                   &lt;element name="precio_oferta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
     *                   &lt;element name="descuentos" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                                       &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
     *                   &lt;element name="cargos" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="cargo" maxOccurs="unbounded" minOccurs="0"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="tipo_cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="descripcion_cargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                                       &lt;element name="valor_cargo_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                                       &lt;element name="valor_cargo_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
        "producto"
    })
    public static class ListadoProductos {

        @XmlElement(required = true)
        protected List<Orden.ListadoProductos.Producto> producto;

        /**
         * Gets the value of the producto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the producto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProducto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Orden.ListadoProductos.Producto }
         * 
         * 
         */
        public List<Orden.ListadoProductos.Producto> getProducto() {
            if (producto == null) {
                producto = new ArrayList<Orden.ListadoProductos.Producto>();
            }
            return this.producto;
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
         *         &lt;element name="accion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_proveedor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_barra1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_barra2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="otros_atributos_producto"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
         *         &lt;element name="unidad_medida_base" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="unidad_medida_compra" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="contenido" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *         &lt;element name="cantidad_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *         &lt;element name="tolerancia" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *         &lt;element name="fecha_entrega" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="costo_lista" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *         &lt;element name="costo_compra" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *         &lt;element name="precio_normal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *         &lt;element name="precio_oferta" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
         *         &lt;element name="descuentos" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *                             &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
         *         &lt;element name="cargos" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="cargo" maxOccurs="unbounded" minOccurs="0"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="tipo_cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="descripcion_cargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                             &lt;element name="valor_cargo_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *                             &lt;element name="valor_cargo_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
            "accion",
            "posicion",
            "codProducto",
            "codProveedor",
            "descripcion",
            "codBarra1",
            "codBarra2",
            "otrosAtributosProducto",
            "unidadMedidaBase",
            "unidadMedidaCompra",
            "contenido",
            "cantidadSolicitadasUmc",
            "tolerancia",
            "fechaEntrega",
            "observaciones",
            "costoLista",
            "costoCompra",
            "precioNormal",
            "precioOferta",
            "descuentos",
            "cargos"
        })
        public static class Producto {

            @XmlElement(required = true)
            protected String accion;
            protected int posicion;
            @XmlElement(name = "cod_producto", required = true)
            protected String codProducto;
            @XmlElement(name = "cod_proveedor")
            protected String codProveedor;
            @XmlElement(required = true)
            protected String descripcion;
            @XmlElement(name = "cod_barra1", required = true)
            protected String codBarra1;
            @XmlElement(name = "cod_barra2")
            protected String codBarra2;
            @XmlElement(name = "otros_atributos_producto", required = true)
            protected Orden.ListadoProductos.Producto.OtrosAtributosProducto otrosAtributosProducto;
            @XmlElement(name = "unidad_medida_base", required = true)
            protected String unidadMedidaBase;
            @XmlElement(name = "unidad_medida_compra", required = true)
            protected String unidadMedidaCompra;
            @XmlElement(required = true)
            protected BigDecimal contenido;
            @XmlElement(name = "cantidad_solicitadas_umc", required = true)
            protected BigDecimal cantidadSolicitadasUmc;
            protected BigDecimal tolerancia;
            @XmlElement(name = "fecha_entrega")
            protected String fechaEntrega;
            protected String observaciones;
            @XmlElement(name = "costo_lista")
            protected BigDecimal costoLista;
            @XmlElement(name = "costo_compra", required = true)
            protected BigDecimal costoCompra;
            @XmlElement(name = "precio_normal")
            protected BigDecimal precioNormal;
            @XmlElement(name = "precio_oferta")
            protected BigDecimal precioOferta;
            protected Orden.ListadoProductos.Producto.Descuentos descuentos;
            protected Orden.ListadoProductos.Producto.Cargos cargos;

            /**
             * Obtiene el valor de la propiedad accion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAccion() {
                return accion;
            }

            /**
             * Define el valor de la propiedad accion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAccion(String value) {
                this.accion = value;
            }

            /**
             * Obtiene el valor de la propiedad posicion.
             * 
             */
            public int getPosicion() {
                return posicion;
            }

            /**
             * Define el valor de la propiedad posicion.
             * 
             */
            public void setPosicion(int value) {
                this.posicion = value;
            }

            /**
             * Obtiene el valor de la propiedad codProducto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProducto() {
                return codProducto;
            }

            /**
             * Define el valor de la propiedad codProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProducto(String value) {
                this.codProducto = value;
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
             * Obtiene el valor de la propiedad descripcion.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDescripcion() {
                return descripcion;
            }

            /**
             * Define el valor de la propiedad descripcion.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDescripcion(String value) {
                this.descripcion = value;
            }

            /**
             * Obtiene el valor de la propiedad codBarra1.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodBarra1() {
                return codBarra1;
            }

            /**
             * Define el valor de la propiedad codBarra1.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodBarra1(String value) {
                this.codBarra1 = value;
            }

            /**
             * Obtiene el valor de la propiedad codBarra2.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodBarra2() {
                return codBarra2;
            }

            /**
             * Define el valor de la propiedad codBarra2.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodBarra2(String value) {
                this.codBarra2 = value;
            }

            /**
             * Obtiene el valor de la propiedad otrosAtributosProducto.
             * 
             * @return
             *     possible object is
             *     {@link Orden.ListadoProductos.Producto.OtrosAtributosProducto }
             *     
             */
            public Orden.ListadoProductos.Producto.OtrosAtributosProducto getOtrosAtributosProducto() {
                return otrosAtributosProducto;
            }

            /**
             * Define el valor de la propiedad otrosAtributosProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link Orden.ListadoProductos.Producto.OtrosAtributosProducto }
             *     
             */
            public void setOtrosAtributosProducto(Orden.ListadoProductos.Producto.OtrosAtributosProducto value) {
                this.otrosAtributosProducto = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadMedidaBase.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnidadMedidaBase() {
                return unidadMedidaBase;
            }

            /**
             * Define el valor de la propiedad unidadMedidaBase.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnidadMedidaBase(String value) {
                this.unidadMedidaBase = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadMedidaCompra.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnidadMedidaCompra() {
                return unidadMedidaCompra;
            }

            /**
             * Define el valor de la propiedad unidadMedidaCompra.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnidadMedidaCompra(String value) {
                this.unidadMedidaCompra = value;
            }

            /**
             * Obtiene el valor de la propiedad contenido.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getContenido() {
                return contenido;
            }

            /**
             * Define el valor de la propiedad contenido.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setContenido(BigDecimal value) {
                this.contenido = value;
            }

            /**
             * Obtiene el valor de la propiedad cantidadSolicitadasUmc.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCantidadSolicitadasUmc() {
                return cantidadSolicitadasUmc;
            }

            /**
             * Define el valor de la propiedad cantidadSolicitadasUmc.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCantidadSolicitadasUmc(BigDecimal value) {
                this.cantidadSolicitadasUmc = value;
            }

            /**
             * Obtiene el valor de la propiedad tolerancia.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getTolerancia() {
                return tolerancia;
            }

            /**
             * Define el valor de la propiedad tolerancia.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setTolerancia(BigDecimal value) {
                this.tolerancia = value;
            }

            /**
             * Obtiene el valor de la propiedad fechaEntrega.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFechaEntrega() {
                return fechaEntrega;
            }

            /**
             * Define el valor de la propiedad fechaEntrega.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFechaEntrega(String value) {
                this.fechaEntrega = value;
            }

            /**
             * Obtiene el valor de la propiedad observaciones.
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
             * Define el valor de la propiedad observaciones.
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
             * Obtiene el valor de la propiedad costoLista.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCostoLista() {
                return costoLista;
            }

            /**
             * Define el valor de la propiedad costoLista.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCostoLista(BigDecimal value) {
                this.costoLista = value;
            }

            /**
             * Obtiene el valor de la propiedad costoCompra.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCostoCompra() {
                return costoCompra;
            }

            /**
             * Define el valor de la propiedad costoCompra.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCostoCompra(BigDecimal value) {
                this.costoCompra = value;
            }

            /**
             * Obtiene el valor de la propiedad precioNormal.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPrecioNormal() {
                return precioNormal;
            }

            /**
             * Define el valor de la propiedad precioNormal.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPrecioNormal(BigDecimal value) {
                this.precioNormal = value;
            }

            /**
             * Obtiene el valor de la propiedad precioOferta.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getPrecioOferta() {
                return precioOferta;
            }

            /**
             * Define el valor de la propiedad precioOferta.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setPrecioOferta(BigDecimal value) {
                this.precioOferta = value;
            }

            /**
             * Obtiene el valor de la propiedad descuentos.
             * 
             * @return
             *     possible object is
             *     {@link Orden.ListadoProductos.Producto.Descuentos }
             *     
             */
            public Orden.ListadoProductos.Producto.Descuentos getDescuentos() {
                return descuentos;
            }

            /**
             * Define el valor de la propiedad descuentos.
             * 
             * @param value
             *     allowed object is
             *     {@link Orden.ListadoProductos.Producto.Descuentos }
             *     
             */
            public void setDescuentos(Orden.ListadoProductos.Producto.Descuentos value) {
                this.descuentos = value;
            }

            /**
             * Obtiene el valor de la propiedad cargos.
             * 
             * @return
             *     possible object is
             *     {@link Orden.ListadoProductos.Producto.Cargos }
             *     
             */
            public Orden.ListadoProductos.Producto.Cargos getCargos() {
                return cargos;
            }

            /**
             * Define el valor de la propiedad cargos.
             * 
             * @param value
             *     allowed object is
             *     {@link Orden.ListadoProductos.Producto.Cargos }
             *     
             */
            public void setCargos(Orden.ListadoProductos.Producto.Cargos value) {
                this.cargos = value;
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
             *         &lt;element name="cargo" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="tipo_cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="descripcion_cargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="valor_cargo_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
             *                   &lt;element name="valor_cargo_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                "cargo"
            })
            public static class Cargos {

                protected List<Orden.ListadoProductos.Producto.Cargos.Cargo> cargo;

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
                 * {@link Orden.ListadoProductos.Producto.Cargos.Cargo }
                 * 
                 * 
                 */
                public List<Orden.ListadoProductos.Producto.Cargos.Cargo> getCargo() {
                    if (cargo == null) {
                        cargo = new ArrayList<Orden.ListadoProductos.Producto.Cargos.Cargo>();
                    }
                    return this.cargo;
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
                 *         &lt;element name="tipo_cargo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="descripcion_cargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="valor_cargo_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
                 *         &lt;element name="valor_cargo_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                    "tipoCargo",
                    "descripcionCargo",
                    "valorCargoPorc",
                    "valorCargoMonto"
                })
                public static class Cargo {

                    @XmlElement(name = "tipo_cargo", required = true)
                    protected String tipoCargo;
                    @XmlElement(name = "descripcion_cargo")
                    protected String descripcionCargo;
                    @XmlElement(name = "valor_cargo_porc", required = true)
                    protected BigDecimal valorCargoPorc;
                    @XmlElement(name = "valor_cargo_monto")
                    protected BigDecimal valorCargoMonto;

                    /**
                     * Obtiene el valor de la propiedad tipoCargo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoCargo() {
                        return tipoCargo;
                    }

                    /**
                     * Define el valor de la propiedad tipoCargo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoCargo(String value) {
                        this.tipoCargo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descripcionCargo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescripcionCargo() {
                        return descripcionCargo;
                    }

                    /**
                     * Define el valor de la propiedad descripcionCargo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescripcionCargo(String value) {
                        this.descripcionCargo = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad valorCargoPorc.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValorCargoPorc() {
                        return valorCargoPorc;
                    }

                    /**
                     * Define el valor de la propiedad valorCargoPorc.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValorCargoPorc(BigDecimal value) {
                        this.valorCargoPorc = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad valorCargoMonto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValorCargoMonto() {
                        return valorCargoMonto;
                    }

                    /**
                     * Define el valor de la propiedad valorCargoMonto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValorCargoMonto(BigDecimal value) {
                        this.valorCargoMonto = value;
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
             *         &lt;element name="descuento" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
             *                   &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
             *                   &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                "descuento"
            })
            public static class Descuentos {

                protected List<Orden.ListadoProductos.Producto.Descuentos.Descuento> descuento;

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
                 * {@link Orden.ListadoProductos.Producto.Descuentos.Descuento }
                 * 
                 * 
                 */
                public List<Orden.ListadoProductos.Producto.Descuentos.Descuento> getDescuento() {
                    if (descuento == null) {
                        descuento = new ArrayList<Orden.ListadoProductos.Producto.Descuentos.Descuento>();
                    }
                    return this.descuento;
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
                 *         &lt;element name="tipo_descuento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="descripcion_descuento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
                 *         &lt;element name="valor_descuento_porc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
                 *         &lt;element name="valor_descuento_monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
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
                    "tipoDescuento",
                    "descripcionDescuento",
                    "valorDescuentoPorc",
                    "valorDescuentoMonto"
                })
                public static class Descuento {

                    @XmlElement(name = "tipo_descuento", required = true)
                    protected String tipoDescuento;
                    @XmlElement(name = "descripcion_descuento")
                    protected String descripcionDescuento;
                    @XmlElement(name = "valor_descuento_porc", required = true)
                    protected BigDecimal valorDescuentoPorc;
                    @XmlElement(name = "valor_descuento_monto")
                    protected BigDecimal valorDescuentoMonto;

                    /**
                     * Obtiene el valor de la propiedad tipoDescuento.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoDescuento() {
                        return tipoDescuento;
                    }

                    /**
                     * Define el valor de la propiedad tipoDescuento.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoDescuento(String value) {
                        this.tipoDescuento = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad descripcionDescuento.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDescripcionDescuento() {
                        return descripcionDescuento;
                    }

                    /**
                     * Define el valor de la propiedad descripcionDescuento.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDescripcionDescuento(String value) {
                        this.descripcionDescuento = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad valorDescuentoPorc.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValorDescuentoPorc() {
                        return valorDescuentoPorc;
                    }

                    /**
                     * Define el valor de la propiedad valorDescuentoPorc.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValorDescuentoPorc(BigDecimal value) {
                        this.valorDescuentoPorc = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad valorDescuentoMonto.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getValorDescuentoMonto() {
                        return valorDescuentoMonto;
                    }

                    /**
                     * Define el valor de la propiedad valorDescuentoMonto.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setValorDescuentoMonto(BigDecimal value) {
                        this.valorDescuentoMonto = value;
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
             *         &lt;element name="atributo" maxOccurs="unbounded" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "atributo"
            })
            public static class OtrosAtributosProducto {

                protected List<Orden.ListadoProductos.Producto.OtrosAtributosProducto.Atributo> atributo;

                /**
                 * Gets the value of the atributo property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the atributo property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getAtributo().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Orden.ListadoProductos.Producto.OtrosAtributosProducto.Atributo }
                 * 
                 * 
                 */
                public List<Orden.ListadoProductos.Producto.OtrosAtributosProducto.Atributo> getAtributo() {
                    if (atributo == null) {
                        atributo = new ArrayList<Orden.ListadoProductos.Producto.OtrosAtributosProducto.Atributo>();
                    }
                    return this.atributo;
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
                 *         &lt;element name="tipo_atributo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                    "tipoAtributo",
                    "codigo",
                    "valor"
                })
                public static class Atributo {

                    @XmlElement(name = "tipo_atributo", required = true)
                    protected String tipoAtributo;
                    @XmlElement(required = true)
                    protected String codigo;
                    @XmlElement(required = true)
                    protected String valor;

                    /**
                     * Obtiene el valor de la propiedad tipoAtributo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoAtributo() {
                        return tipoAtributo;
                    }

                    /**
                     * Define el valor de la propiedad tipoAtributo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoAtributo(String value) {
                        this.tipoAtributo = value;
                    }

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
                     * Obtiene el valor de la propiedad valor.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValor() {
                        return valor;
                    }

                    /**
                     * Define el valor de la propiedad valor.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValor(String value) {
                        this.valor = value;
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
     *         &lt;element name="local" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="nombre_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="email_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    public static class MaestroLocales {

        protected List<Orden.MaestroLocales.Local> local;

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
         * {@link Orden.MaestroLocales.Local }
         * 
         * 
         */
        public List<Orden.MaestroLocales.Local> getLocal() {
            if (local == null) {
                local = new ArrayList<Orden.MaestroLocales.Local>();
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
         *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="nombre_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="email_encargado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "nombre",
            "direccion",
            "nombreEncargado",
            "emailEncargado"
        })
        public static class Local {

            @XmlElement(required = true)
            protected String codigo;
            @XmlElement(required = true)
            protected String nombre;
            protected String direccion;
            @XmlElement(name = "nombre_encargado")
            protected String nombreEncargado;
            @XmlElement(name = "email_encargado")
            protected String emailEncargado;

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
             * Obtiene el valor de la propiedad nombreEncargado.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNombreEncargado() {
                return nombreEncargado;
            }

            /**
             * Define el valor de la propiedad nombreEncargado.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNombreEncargado(String value) {
                this.nombreEncargado = value;
            }

            /**
             * Obtiene el valor de la propiedad emailEncargado.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEmailEncargado() {
                return emailEncargado;
            }

            /**
             * Define el valor de la propiedad emailEncargado.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEmailEncargado(String value) {
                this.emailEncargado = value;
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
     *         &lt;element name="predistribucion" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
     *                   &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="unidades_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
         *         &lt;element name="posicion" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
         *         &lt;element name="cod_local" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="cod_producto" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="unidades_solicitadas_umc" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
            "codProducto",
            "unidadesSolicitadasUmc"
        })
        public static class Predistribucion {

            protected int posicion;
            @XmlElement(name = "cod_local", required = true)
            protected String codLocal;
            @XmlElement(name = "cod_producto", required = true)
            protected String codProducto;
            @XmlElement(name = "unidades_solicitadas_umc", required = true)
            protected BigDecimal unidadesSolicitadasUmc;

            /**
             * Obtiene el valor de la propiedad posicion.
             * 
             */
            public int getPosicion() {
                return posicion;
            }

            /**
             * Define el valor de la propiedad posicion.
             * 
             */
            public void setPosicion(int value) {
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
             * Obtiene el valor de la propiedad codProducto.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodProducto() {
                return codProducto;
            }

            /**
             * Define el valor de la propiedad codProducto.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodProducto(String value) {
                this.codProducto = value;
            }

            /**
             * Obtiene el valor de la propiedad unidadesSolicitadasUmc.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUnidadesSolicitadasUmc() {
                return unidadesSolicitadasUmc;
            }

            /**
             * Define el valor de la propiedad unidadesSolicitadasUmc.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUnidadesSolicitadasUmc(BigDecimal value) {
                this.unidadesSolicitadasUmc = value;
            }

        }

    }

}

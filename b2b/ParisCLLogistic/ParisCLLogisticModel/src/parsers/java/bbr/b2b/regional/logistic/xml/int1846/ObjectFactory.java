//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.25 at 10:29:11 AM CLT 
//


package bbr.b2b.regional.logistic.xml.int1846;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bbr.b2b.regional.logistic.xml.int1846 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CodPostal_QNAME = new QName("", "cod_postal");
    private final static QName _Direccion_QNAME = new QName("", "direccion");
    private final static QName _Ciudad_QNAME = new QName("", "ciudad");
    private final static QName _FechaCreacion_QNAME = new QName("", "fecha_creacion");
    private final static QName _Pais_QNAME = new QName("", "pais");
    private final static QName _NumOrdencompra_QNAME = new QName("", "num_ordencompra");
    private final static QName _EstadoAsn_QNAME = new QName("", "estado_asn");
    private final static QName _CodProveedor_QNAME = new QName("", "cod_proveedor");
    private final static QName _IdCompania_QNAME = new QName("", "id_compania");
    private final static QName _TipoOrigen_QNAME = new QName("", "tipo_origen");
    private final static QName _NumeroAsn_QNAME = new QName("", "numero_asn");
    private final static QName _Accion_QNAME = new QName("", "accion");
    private final static QName _CodProducto_QNAME = new QName("", "cod_producto");
    private final static QName _Comuna_QNAME = new QName("", "comuna");
    private final static QName _TipoLpn_QNAME = new QName("", "tipo_lpn");
    private final static QName _Tipo_QNAME = new QName("", "tipo");
    private final static QName _Cantidad_QNAME = new QName("", "cantidad");
    private final static QName _Localizacion_QNAME = new QName("", "localizacion");
    private final static QName _UnidMedida_QNAME = new QName("", "unid_medida");
    private final static QName _NumLinea_QNAME = new QName("", "num_linea");
    private final static QName _Origen_QNAME = new QName("", "origen");
    private final static QName _TipoAsn_QNAME = new QName("", "tipo_asn");
    private final static QName _Cancelado_QNAME = new QName("", "cancelado");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bbr.b2b.regional.logistic.xml.int1846
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Detalle }
     * 
     */
    public Detalle createDetalle() {
        return new Detalle();
    }

    /**
     * Create an instance of {@link DetalleAsn }
     * 
     */
    public DetalleAsn createDetalleAsn() {
        return new DetalleAsn();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link Cliente }
     * 
     */
    public Cliente createCliente() {
        return new Cliente();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cod_postal")
    public JAXBElement<String> createCodPostal(String value) {
        return new JAXBElement<String>(_CodPostal_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "direccion")
    public JAXBElement<String> createDireccion(String value) {
        return new JAXBElement<String>(_Direccion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ciudad")
    public JAXBElement<String> createCiudad(String value) {
        return new JAXBElement<String>(_Ciudad_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecha_creacion")
    public JAXBElement<XMLGregorianCalendar> createFechaCreacion(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_FechaCreacion_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pais")
    public JAXBElement<String> createPais(String value) {
        return new JAXBElement<String>(_Pais_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "num_ordencompra")
    public JAXBElement<String> createNumOrdencompra(String value) {
        return new JAXBElement<String>(_NumOrdencompra_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "estado_asn")
    public JAXBElement<String> createEstadoAsn(String value) {
        return new JAXBElement<String>(_EstadoAsn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cod_proveedor")
    public JAXBElement<String> createCodProveedor(String value) {
        return new JAXBElement<String>(_CodProveedor_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id_compania")
    public JAXBElement<String> createIdCompania(String value) {
        return new JAXBElement<String>(_IdCompania_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo_origen")
    public JAXBElement<String> createTipoOrigen(String value) {
        return new JAXBElement<String>(_TipoOrigen_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "numero_asn")
    public JAXBElement<String> createNumeroAsn(String value) {
        return new JAXBElement<String>(_NumeroAsn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "accion")
    public JAXBElement<String> createAccion(String value) {
        return new JAXBElement<String>(_Accion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cod_producto")
    public JAXBElement<String> createCodProducto(String value) {
        return new JAXBElement<String>(_CodProducto_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "comuna")
    public JAXBElement<String> createComuna(String value) {
        return new JAXBElement<String>(_Comuna_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo_lpn")
    public JAXBElement<String> createTipoLpn(String value) {
        return new JAXBElement<String>(_TipoLpn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo")
    public JAXBElement<String> createTipo(String value) {
        return new JAXBElement<String>(_Tipo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cantidad")
    public JAXBElement<Double> createCantidad(Double value) {
        return new JAXBElement<Double>(_Cantidad_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "localizacion")
    public JAXBElement<String> createLocalizacion(String value) {
        return new JAXBElement<String>(_Localizacion_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "unid_medida")
    public JAXBElement<String> createUnidMedida(String value) {
        return new JAXBElement<String>(_UnidMedida_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "num_linea")
    public JAXBElement<Integer> createNumLinea(Integer value) {
        return new JAXBElement<Integer>(_NumLinea_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "origen")
    public JAXBElement<String> createOrigen(String value) {
        return new JAXBElement<String>(_Origen_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipo_asn")
    public JAXBElement<String> createTipoAsn(String value) {
        return new JAXBElement<String>(_TipoAsn_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cancelado")
    public JAXBElement<String> createCancelado(String value) {
        return new JAXBElement<String>(_Cancelado_QNAME, String.class, null, value);
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2-70- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.07 at 05:11:55 PM CLT 
//


package bbr.b2b.regional.logistic.xml.int1879;

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
 *         &lt;element ref="{}origen"/>
 *         &lt;element ref="{}accion"/>
 *         &lt;element ref="{}tipo"/>
 *         &lt;element ref="{}id_compania"/>
 *         &lt;element ref="{}num_despacho"/>
 *         &lt;element ref="{}num_ordencompra"/>
 *         &lt;element ref="{}tienda_origen"/>
 *         &lt;element ref="{}codigo_proveedor"/>
 *         &lt;element ref="{}kpi_proveedor"/>
 *         &lt;element ref="{}personalizado1"/>
 *         &lt;element ref="{}personalizado2"/>
 *         &lt;element ref="{}personalizado3"/>
 *         &lt;element ref="{}personalizado4"/>
 *         &lt;element ref="{}estado"/>
 *         &lt;element ref="{}listado_productos"/>
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
    "origen",
    "accion",
    "tipo",
    "idCompania",
    "numDespacho",
    "numOrdencompra",
    "tiendaOrigen",
    "codigoProveedor",
    "kpiProveedor",
    "personalizado1",
    "personalizado2",
    "personalizado3",
    "personalizado4",
    "estado",
    "listadoProductos"
})
@XmlRootElement(name = "Message")
public class Message {

    @XmlElement(required = true)
    protected String origen;
    @XmlElement(required = true)
    protected String accion;
    @XmlElement(required = true)
    protected String tipo;
    @XmlElement(name = "id_compania", required = true)
    protected String idCompania;
    @XmlElement(name = "num_despacho")
    protected long numDespacho;
    @XmlElement(name = "num_ordencompra", required = true)
    protected String numOrdencompra;
    @XmlElement(name = "tienda_origen", required = true)
    protected String tiendaOrigen;
    @XmlElement(name = "codigo_proveedor", required = true)
    protected String codigoProveedor;
    @XmlElement(name = "kpi_proveedor")
    protected double kpiProveedor;
    @XmlElement(required = true)
    protected String personalizado1;
    @XmlElement(required = true)
    protected String personalizado2;
    @XmlElement(required = true)
    protected String personalizado3;
    @XmlElement(required = true)
    protected String personalizado4;
    @XmlElement(required = true)
    protected Estado estado;
    @XmlElement(name = "listado_productos", required = true)
    protected ListadoProductos listadoProductos;

    /**
     * Gets the value of the origen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Sets the value of the origen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigen(String value) {
        this.origen = value;
    }

    /**
     * Gets the value of the accion property.
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
     * Sets the value of the accion property.
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
     * Gets the value of the tipo property.
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
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the idCompania property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCompania() {
        return idCompania;
    }

    /**
     * Sets the value of the idCompania property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCompania(String value) {
        this.idCompania = value;
    }

    /**
     * Gets the value of the numDespacho property.
     * 
     */
    public long getNumDespacho() {
        return numDespacho;
    }

    /**
     * Sets the value of the numDespacho property.
     * 
     */
    public void setNumDespacho(long value) {
        this.numDespacho = value;
    }

    /**
     * Gets the value of the numOrdencompra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumOrdencompra() {
        return numOrdencompra;
    }

    /**
     * Sets the value of the numOrdencompra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumOrdencompra(String value) {
        this.numOrdencompra = value;
    }

    /**
     * Gets the value of the tiendaOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiendaOrigen() {
        return tiendaOrigen;
    }

    /**
     * Sets the value of the tiendaOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiendaOrigen(String value) {
        this.tiendaOrigen = value;
    }

    /**
     * Gets the value of the codigoProveedor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    /**
     * Sets the value of the codigoProveedor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoProveedor(String value) {
        this.codigoProveedor = value;
    }

    /**
     * Gets the value of the kpiProveedor property.
     * 
     */
    public double getKpiProveedor() {
        return kpiProveedor;
    }

    /**
     * Sets the value of the kpiProveedor property.
     * 
     */
    public void setKpiProveedor(double value) {
        this.kpiProveedor = value;
    }

    /**
     * Gets the value of the personalizado1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalizado1() {
        return personalizado1;
    }

    /**
     * Sets the value of the personalizado1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalizado1(String value) {
        this.personalizado1 = value;
    }

    /**
     * Gets the value of the personalizado2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalizado2() {
        return personalizado2;
    }

    /**
     * Sets the value of the personalizado2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalizado2(String value) {
        this.personalizado2 = value;
    }

    /**
     * Gets the value of the personalizado3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalizado3() {
        return personalizado3;
    }

    /**
     * Sets the value of the personalizado3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalizado3(String value) {
        this.personalizado3 = value;
    }

    /**
     * Gets the value of the personalizado4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalizado4() {
        return personalizado4;
    }

    /**
     * Sets the value of the personalizado4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalizado4(String value) {
        this.personalizado4 = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link Estado }
     *     
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Estado }
     *     
     */
    public void setEstado(Estado value) {
        this.estado = value;
    }

    /**
     * Gets the value of the listadoProductos property.
     * 
     * @return
     *     possible object is
     *     {@link ListadoProductos }
     *     
     */
    public ListadoProductos getListadoProductos() {
        return listadoProductos;
    }

    /**
     * Sets the value of the listadoProductos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListadoProductos }
     *     
     */
    public void setListadoProductos(ListadoProductos value) {
        this.listadoProductos = value;
    }

}
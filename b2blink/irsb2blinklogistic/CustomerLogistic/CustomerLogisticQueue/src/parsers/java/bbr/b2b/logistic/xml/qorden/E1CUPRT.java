//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.05.22 a las 04:48:19 PM CLT 
//


package bbr.b2b.logistic.xml.qorden;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}PARENT_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}INST_ID" minOccurs="0"/&gt;
 *         &lt;element ref="{}PART_OF_NO" minOccurs="0"/&gt;
 *         &lt;element ref="{}OBJ_TYPE" minOccurs="0"/&gt;
 *         &lt;element ref="{}CLASS_TYPE" minOccurs="0"/&gt;
 *         &lt;element ref="{}OBJ_KEY" minOccurs="0"/&gt;
 *         &lt;element ref="{}AUTHOR" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="SEGMENT" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parentid",
    "instid",
    "partofno",
    "objtype",
    "classtype",
    "objkey",
    "author"
})
@XmlRootElement(name = "E1CUPRT")
public class E1CUPRT {

    @XmlElement(name = "PARENT_ID")
    protected PARENTID parentid;
    @XmlElement(name = "INST_ID")
    protected INSTID instid;
    @XmlElement(name = "PART_OF_NO")
    protected PARTOFNO partofno;
    @XmlElement(name = "OBJ_TYPE")
    protected OBJTYPE objtype;
    @XmlElement(name = "CLASS_TYPE")
    protected CLASSTYPE classtype;
    @XmlElement(name = "OBJ_KEY")
    protected OBJKEY objkey;
    @XmlElement(name = "AUTHOR")
    protected AUTHOR author;
    @XmlAttribute(name = "SEGMENT", required = true)
    protected String segment;

    /**
     * Obtiene el valor de la propiedad parentid.
     * 
     * @return
     *     possible object is
     *     {@link PARENTID }
     *     
     */
    public PARENTID getPARENTID() {
        return parentid;
    }

    /**
     * Define el valor de la propiedad parentid.
     * 
     * @param value
     *     allowed object is
     *     {@link PARENTID }
     *     
     */
    public void setPARENTID(PARENTID value) {
        this.parentid = value;
    }

    /**
     * Obtiene el valor de la propiedad instid.
     * 
     * @return
     *     possible object is
     *     {@link INSTID }
     *     
     */
    public INSTID getINSTID() {
        return instid;
    }

    /**
     * Define el valor de la propiedad instid.
     * 
     * @param value
     *     allowed object is
     *     {@link INSTID }
     *     
     */
    public void setINSTID(INSTID value) {
        this.instid = value;
    }

    /**
     * Obtiene el valor de la propiedad partofno.
     * 
     * @return
     *     possible object is
     *     {@link PARTOFNO }
     *     
     */
    public PARTOFNO getPARTOFNO() {
        return partofno;
    }

    /**
     * Define el valor de la propiedad partofno.
     * 
     * @param value
     *     allowed object is
     *     {@link PARTOFNO }
     *     
     */
    public void setPARTOFNO(PARTOFNO value) {
        this.partofno = value;
    }

    /**
     * Obtiene el valor de la propiedad objtype.
     * 
     * @return
     *     possible object is
     *     {@link OBJTYPE }
     *     
     */
    public OBJTYPE getOBJTYPE() {
        return objtype;
    }

    /**
     * Define el valor de la propiedad objtype.
     * 
     * @param value
     *     allowed object is
     *     {@link OBJTYPE }
     *     
     */
    public void setOBJTYPE(OBJTYPE value) {
        this.objtype = value;
    }

    /**
     * Obtiene el valor de la propiedad classtype.
     * 
     * @return
     *     possible object is
     *     {@link CLASSTYPE }
     *     
     */
    public CLASSTYPE getCLASSTYPE() {
        return classtype;
    }

    /**
     * Define el valor de la propiedad classtype.
     * 
     * @param value
     *     allowed object is
     *     {@link CLASSTYPE }
     *     
     */
    public void setCLASSTYPE(CLASSTYPE value) {
        this.classtype = value;
    }

    /**
     * Obtiene el valor de la propiedad objkey.
     * 
     * @return
     *     possible object is
     *     {@link OBJKEY }
     *     
     */
    public OBJKEY getOBJKEY() {
        return objkey;
    }

    /**
     * Define el valor de la propiedad objkey.
     * 
     * @param value
     *     allowed object is
     *     {@link OBJKEY }
     *     
     */
    public void setOBJKEY(OBJKEY value) {
        this.objkey = value;
    }

    /**
     * Obtiene el valor de la propiedad author.
     * 
     * @return
     *     possible object is
     *     {@link AUTHOR }
     *     
     */
    public AUTHOR getAUTHOR() {
        return author;
    }

    /**
     * Define el valor de la propiedad author.
     * 
     * @param value
     *     allowed object is
     *     {@link AUTHOR }
     *     
     */
    public void setAUTHOR(AUTHOR value) {
        this.author = value;
    }

    /**
     * Obtiene el valor de la propiedad segment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEGMENT() {
        return segment;
    }

    /**
     * Define el valor de la propiedad segment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEGMENT(String value) {
        this.segment = value;
    }

}

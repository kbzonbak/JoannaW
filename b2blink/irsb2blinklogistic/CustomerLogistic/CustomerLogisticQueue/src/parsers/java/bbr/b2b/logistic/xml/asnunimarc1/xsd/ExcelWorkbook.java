//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.08.28 a las 06:38:56 PM CLT 
//


package bbr.b2b.logistic.xml.asnunimarc1.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="WindowHeight" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
 *         &lt;element name="WindowWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/&gt;
 *         &lt;element name="WindowTopX" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *         &lt;element name="WindowTopY" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *         &lt;element name="ProtectStructure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProtectWindows" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "windowHeight",
    "windowWidth",
    "windowTopX",
    "windowTopY",
    "protectStructure",
    "protectWindows"
})
@XmlRootElement(name = "ExcelWorkbook")
public class ExcelWorkbook {

    @XmlElement(name = "WindowHeight")
    @XmlSchemaType(name = "unsignedShort")
    protected int windowHeight;
    @XmlElement(name = "WindowWidth")
    @XmlSchemaType(name = "unsignedShort")
    protected int windowWidth;
    @XmlElement(name = "WindowTopX")
    @XmlSchemaType(name = "unsignedByte")
    protected short windowTopX;
    @XmlElement(name = "WindowTopY")
    @XmlSchemaType(name = "unsignedByte")
    protected short windowTopY;
    @XmlElement(name = "ProtectStructure", required = true)
    protected String protectStructure;
    @XmlElement(name = "ProtectWindows", required = true)
    protected String protectWindows;

    /**
     * Obtiene el valor de la propiedad windowHeight.
     * 
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Define el valor de la propiedad windowHeight.
     * 
     */
    public void setWindowHeight(int value) {
        this.windowHeight = value;
    }

    /**
     * Obtiene el valor de la propiedad windowWidth.
     * 
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Define el valor de la propiedad windowWidth.
     * 
     */
    public void setWindowWidth(int value) {
        this.windowWidth = value;
    }

    /**
     * Obtiene el valor de la propiedad windowTopX.
     * 
     */
    public short getWindowTopX() {
        return windowTopX;
    }

    /**
     * Define el valor de la propiedad windowTopX.
     * 
     */
    public void setWindowTopX(short value) {
        this.windowTopX = value;
    }

    /**
     * Obtiene el valor de la propiedad windowTopY.
     * 
     */
    public short getWindowTopY() {
        return windowTopY;
    }

    /**
     * Define el valor de la propiedad windowTopY.
     * 
     */
    public void setWindowTopY(short value) {
        this.windowTopY = value;
    }

    /**
     * Obtiene el valor de la propiedad protectStructure.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectStructure() {
        return protectStructure;
    }

    /**
     * Define el valor de la propiedad protectStructure.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectStructure(String value) {
        this.protectStructure = value;
    }

    /**
     * Obtiene el valor de la propiedad protectWindows.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectWindows() {
        return protectWindows;
    }

    /**
     * Define el valor de la propiedad protectWindows.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectWindows(String value) {
        this.protectWindows = value;
    }

}

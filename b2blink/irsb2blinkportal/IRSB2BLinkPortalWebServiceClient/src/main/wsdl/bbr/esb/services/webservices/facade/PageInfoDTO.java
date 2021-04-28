
package bbr.esb.services.webservices.facade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pageInfoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pageInfoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pagenumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalpages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="totalrows" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pageInfoDTO", propOrder = {
    "pagenumber",
    "rows",
    "totalpages",
    "totalrows"
})
public class PageInfoDTO {

    protected int pagenumber;
    protected int rows;
    protected int totalpages;
    protected int totalrows;

    /**
     * Gets the value of the pagenumber property.
     * 
     */
    public int getPagenumber() {
        return pagenumber;
    }

    /**
     * Sets the value of the pagenumber property.
     * 
     */
    public void setPagenumber(int value) {
        this.pagenumber = value;
    }

    /**
     * Gets the value of the rows property.
     * 
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the value of the rows property.
     * 
     */
    public void setRows(int value) {
        this.rows = value;
    }

    /**
     * Gets the value of the totalpages property.
     * 
     */
    public int getTotalpages() {
        return totalpages;
    }

    /**
     * Sets the value of the totalpages property.
     * 
     */
    public void setTotalpages(int value) {
        this.totalpages = value;
    }

    /**
     * Gets the value of the totalrows property.
     * 
     */
    public int getTotalrows() {
        return totalrows;
    }

    /**
     * Sets the value of the totalrows property.
     * 
     */
    public void setTotalrows(int value) {
        this.totalrows = value;
    }

}

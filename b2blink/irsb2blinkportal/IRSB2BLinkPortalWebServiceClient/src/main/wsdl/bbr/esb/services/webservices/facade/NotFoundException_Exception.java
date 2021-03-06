
package bbr.esb.services.webservices.facade;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Dec 03 11:49:19 CLT 2020
 * Generated source version: 2.1.3
 * 
 */

@WebFault(name = "NotFoundException", targetNamespace = "http://facade.webservices.services.esb.bbr/")
public class NotFoundException_Exception extends Exception {
    public static final long serialVersionUID = 20201203114919L;
    
    private bbr.esb.services.webservices.facade.NotFoundException notFoundException;

    public NotFoundException_Exception() {
        super();
    }
    
    public NotFoundException_Exception(String message) {
        super(message);
    }
    
    public NotFoundException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException_Exception(String message, bbr.esb.services.webservices.facade.NotFoundException notFoundException) {
        super(message);
        this.notFoundException = notFoundException;
    }

    public NotFoundException_Exception(String message, bbr.esb.services.webservices.facade.NotFoundException notFoundException, Throwable cause) {
        super(message, cause);
        this.notFoundException = notFoundException;
    }

    public bbr.esb.services.webservices.facade.NotFoundException getFaultInfo() {
        return this.notFoundException;
    }
}

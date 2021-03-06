
package bbr.esb.services.webservices.facade;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Jul 01 17:24:33 CLT 2020
 * Generated source version: 2.1.3
 * 
 */

@WebFault(name = "OperationFailedException", targetNamespace = "http://facade.webservices.services.esb.bbr/")
public class OperationFailedException_Exception extends Exception {
    public static final long serialVersionUID = 20200701172433L;
    
    private bbr.esb.services.webservices.facade.OperationFailedException operationFailedException;

    public OperationFailedException_Exception() {
        super();
    }
    
    public OperationFailedException_Exception(String message) {
        super(message);
    }
    
    public OperationFailedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException_Exception(String message, bbr.esb.services.webservices.facade.OperationFailedException operationFailedException) {
        super(message);
        this.operationFailedException = operationFailedException;
    }

    public OperationFailedException_Exception(String message, bbr.esb.services.webservices.facade.OperationFailedException operationFailedException, Throwable cause) {
        super(message, cause);
        this.operationFailedException = operationFailedException;
    }

    public bbr.esb.services.webservices.facade.OperationFailedException getFaultInfo() {
        return this.operationFailedException;
    }
}

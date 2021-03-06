
/*
 * 
 */

package bbr.esb.services.webservices.facade;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.1.3
 * Wed Jul 01 17:24:33 CLT 2020
 * Generated source version: 2.1.3
 * 
 */


@WebServiceClient(name = "ServiceManagerServerService", 
                  wsdlLocation = "file:/D:/Development/workspace_wildfly_10.1/B2BLink/IRSB2BLinkLogistic/maven.1586802428284/CustomerLogistic/CustomerLogisticUtils/src/main/resources/ServiceManagerServer.wsdl",
                  targetNamespace = "http://facade.webservices.services.esb.bbr/") 
public class ServiceManagerServerService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://facade.webservices.services.esb.bbr/", "ServiceManagerServerService");
    public final static QName ServiceManagerServerPort = new QName("http://facade.webservices.services.esb.bbr/", "ServiceManagerServerPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/Development/workspace_wildfly_10.1/B2BLink/IRSB2BLinkLogistic/maven.1586802428284/CustomerLogistic/CustomerLogisticUtils/src/main/resources/ServiceManagerServer.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/D:/Development/workspace_wildfly_10.1/B2BLink/IRSB2BLinkLogistic/maven.1586802428284/CustomerLogistic/CustomerLogisticUtils/src/main/resources/ServiceManagerServer.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ServiceManagerServerService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ServiceManagerServerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServiceManagerServerService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns ServiceManagerServer
     */
    @WebEndpoint(name = "ServiceManagerServerPort")
    public ServiceManagerServer getServiceManagerServerPort() {
        return super.getPort(ServiceManagerServerPort, ServiceManagerServer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServiceManagerServer
     */
    @WebEndpoint(name = "ServiceManagerServerPort")
    public ServiceManagerServer getServiceManagerServerPort(WebServiceFeature... features) {
        return super.getPort(ServiceManagerServerPort, ServiceManagerServer.class, features);
    }

}

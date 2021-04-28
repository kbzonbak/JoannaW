package bbr.b2b.soa.integrator.utils;

import java.io.IOException;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

public class SecurityHeader implements WebServiceMessageCallback {

	private Authentication authentication;

	public SecurityHeader(Authentication authentication) {
		this.authentication = authentication;
	}

	// ESTE MÉTODO SOBREESCRIBE LA INSTANCIACIÓN DEL CLIENTE WS DE SODIMAC, 
	// PARA INCORPORAR AUTENTICACIÓN DE MANERA DINÁMICA POR PROVEEDOR
	
	@Override
	public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
		try {
			SaajSoapMessage saajSoapMessage = (SaajSoapMessage) message;
			SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			Name headerElementName = soapEnvelope.createName("Security", "wsse",
					"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
			SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(headerElementName);
			SOAPElement usernameTokenSOAPElement = soapHeaderElement.addChildElement("UsernameToken", "wsse");

			SOAPElement userNameSOAPElement = usernameTokenSOAPElement.addChildElement("Username", "wsse");
			userNameSOAPElement.addTextNode(this.authentication.getUserName());

			SOAPElement passwordSOAPElement = usernameTokenSOAPElement.addChildElement("Password", "wsse");
			passwordSOAPElement.addTextNode(this.authentication.getPassword());
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}
}

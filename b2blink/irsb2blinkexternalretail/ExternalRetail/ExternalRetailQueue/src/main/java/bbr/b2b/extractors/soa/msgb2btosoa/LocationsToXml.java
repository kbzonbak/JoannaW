package bbr.b2b.extractors.soa.msgb2btosoa;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.xml.bind.marshaller.DataWriter;
import bbr.b2b.b2blink.commercial.xml.ReporteLocales.*;
import bbr.b2b.b2blink.commercial.xml.ReporteLocales.Locales;
import bbr.b2b.b2blink.commercial.xml.ReporteLocales.Locales.Vendedor;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
//import bbr.b2b.connector.classes.EnvioProveedorServerLocal;
//import bbr.b2b.connector.classes.ProcessTypeServerLocal;
//import bbr.b2b.connector.classes.ProveedorServerLocal;
//import bbr.b2b.connector.classes.SoaStateTypeServerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;

@Stateless(name = "handlers/LocationsToXml")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LocationsToXml implements LocationsToXmlLocal {

	@EJB
	SchedulerManagerLocal schedulermanager;

//	@EJB
//	EnvioProveedorServerLocal envioProveedorServerLocal;
//	
//	@EJB
//	ProveedorServerLocal proveedorServerLocal;
//	
//	@EJB
//	ProcessTypeServerLocal processTypeServerLocal;
//	
//	@EJB
//	SoaStateTypeServerLocal soaStateTypeServerLocal;
	

	private static JAXBContext jc = null;

	private static JAXBContext getJCReportLocations() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.ReporteLocales");
		return jc;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String processMessage(String nombrePortal, String codProveedor, String formato, String codComprador)
			throws OperationFailedException, LoadDataException {
		
		return null;
	}
}

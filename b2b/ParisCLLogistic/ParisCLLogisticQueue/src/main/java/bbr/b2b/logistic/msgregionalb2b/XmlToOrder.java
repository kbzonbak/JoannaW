package bbr.b2b.logistic.msgregionalb2b;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.AtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.DetailDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.ItemAtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.AtcW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.DetailDiscountW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ItemAtcW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDiscountW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailPK;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.PreDeliveryDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtcId;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.DirectOrderReportManagerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderDetailServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailPK;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetailId;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemClassServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.classes.UnitServerLocal;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemClassW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemWithAtcW;
import bbr.b2b.regional.logistic.items.data.wrappers.UnitW;
import bbr.b2b.regional.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.regional.logistic.items.entities.VendorItemId;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticReportManagerLocal;
import bbr.b2b.regional.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.regional.logistic.soa.classes.DirectOrderSOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.data.classes.DirectOrderSOAStateW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.ResponsableW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.xml.qorder.A10;
import bbr.b2b.regional.logistic.xml.qorder.A14;
import bbr.b2b.regional.logistic.xml.qorder.A2;
import bbr.b2b.regional.logistic.xml.qorder.A3;
import bbr.b2b.regional.logistic.xml.qorder.A4;
import bbr.b2b.regional.logistic.xml.qorder.B2;
import bbr.b2b.regional.logistic.xml.qorder.DCS;
import bbr.b2b.regional.logistic.xml.qorder.DIS;
import bbr.b2b.regional.logistic.xml.qorder.DTS;
import bbr.b2b.regional.logistic.xml.qorder.LS;
import bbr.b2b.regional.logistic.xml.qorder.PDS;
import bbr.b2b.regional.logistic.xml.qorder.PR8;
import bbr.b2b.regional.logistic.xml.qorder.PRS;
import bbr.b2b.regional.logistic.xml.qorder.QORDEN;

@Stateless(name = "handlers/XmlToOrder")
public class XmlToOrder implements XmlToOrderLocal {

	@EJB
	ItemServerLocal itemserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	@EJB
	ResponsableServerLocal responsableserver;

	@EJB
	VendorItemServerLocal vendoritemserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ClientServerLocal clientserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	ItemClassServerLocal itemclassserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	OrderDiscountServerLocal orderdiscountserver;

	@EJB
	UnitServerLocal unitserver;

	@EJB
	DetailDiscountServerLocal detaildiscountserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;

	@EJB
	DirectOrderStateServerLocal directorderstateserver;

	@EJB
	DirectOrderDetailServerLocal directorderdetailserver;

	@EJB
	BuyOrderReportManagerLocal ordermanager;

	@EJB
	DirectOrderReportManagerLocal directordermanager;
	
	@EJB
	LogisticReportManagerLocal logisticreportmanager;

	@EJB
	DeliveryReportManagerLocal deliveryreportmanager;

	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	SOAStateServerLocal soastateserver;

	@EJB
	DirectOrderSOAStateServerLocal directordersoastateserver;
	
	@EJB
	NotificationsReportManagerLocal notificationmanager;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@EJB
	AtcServerLocal atcServer;
	
	@EJB
	ItemAtcServerLocal itemAtcServer;

	CommonQueueUtils qutils = CommonQueueUtils.getInstance();

	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private void doValidateOrderMessage(QORDEN qorden) throws LoadDataException {
		Long ordernumber = null;

		// A0: N°MERO DE SECUENCIA
		qutils.datoObligatorio(qorden.getA0(), "No se especifica N°mero de secuencia");
		qutils.validaLargo(qorden.getA0(), 12, "El N°mero de secuencia debe tener un largo de a los m�s 12 d�gitos");

		// A0: N°MERO DE ORDEN
		qutils.datoObligatorio(qorden.getA1(), "No se especifica N°mero de orden");
		qutils.validaLargo(qorden.getA1(), 6, "El N°mero de orden debe tener un largo de a los m�s 6 d�gitos");
		ordernumber = qorden.getA1();

		// A9: TIPO DE ORDEN
		qutils.datoObligatorio(qorden.getA9(), "No se especifica tipo de orden");
		qutils.validaLargo(qorden.getA9(), 1, "El tipo de orden debe tener un largo de a los m�s 1 caracter(es)");

		if (qorden.getA9().equalsIgnoreCase("V") || qorden.getA9().equalsIgnoreCase("P")) {
			// A2: BLOQUE VENTA EN VERDE
			if (qorden.getA2() == null) {
				throw new LoadDataException("No se especifica el bloque venta en verde, Orden " + ordernumber);
			}

			A2 a2parser = qorden.getA2();

			// B0: N°MERO DE SOLICITUD
			qutils.datoObligatorio(a2parser.getB0(), "No se especifica el N°mero de solicitud, Orden " + ordernumber);
			qutils.validaLargo(a2parser.getB0(), 20, "El N°mero de solicitud debe tener un largo de a los m�s 20 d�gitos, Orden " + ordernumber);

			// B1: N°MERO DE DISTRIBUCION
			if (qorden.getA9().equalsIgnoreCase("P")) {
				qutils.datoObligatorio(a2parser.getB1(), "No se especifica el N°mero de distribución (B1), Orden " + ordernumber);
			}

			// B2: CLIENTE
			if (a2parser.getB2() == null) {
				throw new LoadDataException("No se especifica el bloque cliente, Orden " + ordernumber);
			}

			B2 b2parser = a2parser.getB2();

			// CL0: NOMBRE CLIENTE
			qutils.datoObligatorio(b2parser.getCL0(), "No se especifica el nombre de cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL0(), 40, "El nombre de cliente debe tener un largo de a los m�s 40 caracter(es), Orden " + ordernumber);

			// CL1: RUT CLIENTE
			qutils.datoObligatorio(b2parser.getCL1(), "No se especifica el rut de cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL1(), 10, "El rut de cliente debe tener un largo de a los m�s 10 caracter(es), Orden " + ordernumber);

			// CL2: TEL�FONO CLIENTE
			qutils.datoObligatorio(b2parser.getCL2(), "No se especifica el tel�fono cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL2(), 10, "El tel�fono cliente debe tener un largo de a los m�s 10 caracter(es), Orden " + ordernumber);

			// CL3: DIRECción CLIENTE
			qutils.datoObligatorio(b2parser.getCL3(), "No se especifica la dirección cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL3(), 40, "La dirección cliente debe tener un largo de a los m�s 40 caracter(es), Orden " + ordernumber);

			// CL7: REGión CLIENTE
			qutils.datoObligatorio(b2parser.getCL7(), "No se especifica la región cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL7(), 30, "La región cliente debe tener un largo de a los m�s 30 caracter(es), Orden " + ordernumber);

			// CL8: COMUNA CLIENTE
			qutils.datoObligatorio(b2parser.getCL8(), "No se especifica la comuna cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL8(), 30, "La comuna cliente debe tener un largo de a los m�s 30 caracter(es), Orden " + ordernumber);

			// CL9: CIUDAD CLIENTE
			qutils.datoObligatorio(b2parser.getCL9(), "No se especifica la ciudad cliente, Orden " + ordernumber);
			qutils.validaLargo(b2parser.getCL9(), 30, "La ciudad cliente debe tener un largo de a los m�s 30 caracter(es), Orden " + ordernumber);

			// B4: FECHA COMPROMETIDA
			qutils.datoObligatorio(a2parser.getB4(), "No se especifica la fecha comprometida cliente, Orden " + ordernumber);
			qutils.validaLargo(a2parser.getB4(), 6, "La fecha comprometida cliente debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

			try {
				qutils.getDate(a2parser.getB4(), "yyMMdd");
			} catch (ParseException e) {
				throw new LoadDataException("La fecha comprometida cliente debe tener formato 'YYMMDD'");
			}
		}

		// A3: COMPRADOR (RESPONSABLE)
		if (qorden.getA3() == null) {
			throw new LoadDataException("No se especifica el bloque comprador, Orden " + ordernumber);
		}

		A3 a3parser = qorden.getA3();

		// C0: código COMPRADOR
		qutils.datoObligatorio(a3parser.getC0(), "No se especifica el código de comprador, Orden " + ordernumber);
		qutils.validaLargo(a3parser.getC0(), 6, "El código de comprador debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

		// C1: NOMBRE COMPRADOR
		qutils.datoObligatorio(a3parser.getC1(), "No se especifica el nombre de comprador, Orden " + ordernumber);
		qutils.validaLargo(a3parser.getC1(), 20, "El nombre de comprador debe tener un largo de a los m�s 20 caracter(es), Orden " + ordernumber);

		// C2: APELLIDOS COMPRADOR
		qutils.datoObligatorio(a3parser.getC2(), "No se especifica los apellidos de comprador, Orden " + ordernumber);
		qutils.validaLargo(a3parser.getC2(), 20, "Los apellidos de comprador debe tener un largo de a los m�s 20 caracter(es), Orden " + ordernumber);

		// A4: PROVEEDOR
		if (qorden.getA4() == null) {
			throw new LoadDataException("No se especifica el bloque proveedor, Orden " + ordernumber);
		}

		A4 a4parser = qorden.getA4();

		// P0: código PROVEEDOR
		qutils.datoObligatorio(a4parser.getP0(), "No se especifica el código de proveedor, Orden " + ordernumber);
		qutils.validaLargo(a4parser.getP0(), 6, "El código de proveedor debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

		// P1: RAZ�N SOCIAL PROVEEDOR
		qutils.datoObligatorio(a4parser.getP1(), "No se especifica la raz�n social de proveedor, Orden " + ordernumber);
		qutils.validaLargo(a4parser.getP1(), 50, "La raz�n social de proveedor debe tener un largo de a los m�s 50 caracter(es), Orden " + ordernumber);

		// P2: NOMBRE CORTO PROVEEDOR
		qutils.datoObligatorio(a4parser.getP2(), "No se especifica el nombre corto de proveedor, Orden " + ordernumber);
		qutils.validaLargo(a4parser.getP2(), 35, "El nombre corto de proveedor debe tener un largo de a los m�s 35 caracter(es), Orden " + ordernumber);

		// P3: RUT PROVEEDOR
		qutils.datoObligatorio(a4parser.getP3(), "No se especifica el rut de proveedor Orden " + ordernumber);
		qutils.validaLargo(a4parser.getP3(), 12, "El rut de proveedor debe tener un largo de a los m�s 12 caracter(es), Orden " + ordernumber);

		// P4: TIPO PROVEEDOR
		qutils.datoObligatorio(a4parser.getP4(), "No se especifica el tipo de proveedor, Orden " + ordernumber);
		qutils.validaLargo(a4parser.getP4(), 1, "El tipo de proveedor debe tener un largo de a los m�s 1 caracter(es), Orden " + ordernumber);

		if (!a4parser.getP4().equalsIgnoreCase("N") && !a4parser.getP4().equalsIgnoreCase("I")) {
			throw new LoadDataException("El tipo de proveedor solo puede tener valores 'N' o 'I'");
		}

		// A5: FECHA EMISión
		qutils.datoObligatorio(qorden.getA5(), "No se especifica la fecha de emisión, Orden " + ordernumber);
		qutils.validaLargo(qorden.getA5(), 6, "La fecha de emisión debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

		try {
			qutils.getDate(qorden.getA5(), "yyMMdd");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha de emisión debe tener formato 'YYMMDD'");
		}

		// A6: FECHA VIGENCIA
		qutils.datoObligatorio(qorden.getA6(), "No se especifica la fecha de vigencia, Orden " + ordernumber);
		qutils.validaLargo(qorden.getA6(), 6, "La fecha de vigencia debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

		try {
			qutils.getDate(qorden.getA6(), "yyMMdd");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha de vigencia debe tener formato 'YYMMDD'");
		}

		// A7: FECHA VENCIMIENTO
		qutils.datoObligatorio(qorden.getA7(), "No se especifica la fecha de vencimiento, Orden " + ordernumber);
		qutils.validaLargo(qorden.getA7(), 6, "La fecha de vencimiento debe tener un largo de a los m�s 6 caracter(es), Orden " + ordernumber);

		try {
			qutils.getDate(qorden.getA7(), "yyMMdd");
		} catch (ParseException e) {
			throw new LoadDataException("La fecha de vencimiento debe tener formato 'YYMMDD'");
		}

		// A10: DEPARTAMENTO
		if (qorden.getA10() == null) {
			throw new LoadDataException("No se especifica el bloque departamento, Orden " + ordernumber);
		}

		A10 a10parser = qorden.getA10();

		// D0: código DEPARTAMENTO
		qutils.datoObligatorio(a10parser.getD0(), "No se especifica el código de departamento, Orden " + ordernumber);
		qutils.validaLargo(a10parser.getD0(), 4, "El código de departamento debe tener un largo de a los m�s 4 caracter(es), Orden " + ordernumber);

		// A11: MAESTRO DE LOCALES
		if (qorden.getA11() == null || qorden.getA11().getLS() == null || qorden.getA11().getLS().size() <= 0) {

			throw new LoadDataException("No se especifica maestro de locales, Orden " + ordernumber);
		}

		List<LS> locales = qorden.getA11().getLS();

		for (int i = 0; i < locales.size(); i++) {
			// L0: código LOCAL
			qutils.datoObligatorio(locales.get(i).getL0(), "No se especifica código de local, Orden " + ordernumber);
			qutils.validaLargo(locales.get(i).getL0(), 5, "El código de local debe tener un largo de a los m�s 5 caracter(es), Orden " + ordernumber);

			// L1: DESCRIPción CORTA LOCAL
			qutils.datoObligatorio(locales.get(i).getL1(), "No se especifica descripción corta de local, Orden " + ordernumber);
			qutils.validaLargo(locales.get(i).getL1(), 3, "La descripción corta de local debe tener un largo de a los m�s 3 caracter(es), Orden " + ordernumber);

			// L2: DESCRIPción LOCAL
			qutils.datoObligatorio(locales.get(i).getL2(), "No se especifica descripción de local, Orden " + ordernumber);
			qutils.validaLargo(locales.get(i).getL2(), 30, "La descripción de local debe tener un largo de a los m�s 30 caracter(es), Orden " + ordernumber);
		}

		// A12: MAESTRO DE PRODUCTOS
		if (qorden.getA12() == null || qorden.getA12().getPRS() == null || qorden.getA12().getPRS().size() <= 0) {

			throw new LoadDataException("No se especifica maestro de productos, Orden " + ordernumber);
		}

		List<PRS> productos = qorden.getA12().getPRS();

		for (int i = 0; i < productos.size(); i++) {
			String sku;
			// PR0: SKU PRODUCTO
			qutils.datoObligatorio(productos.get(i).getPR0(), "No se especifica sku producto, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR0(), 9, "El sku producto debe tener un largo de a los m�s 9 caracter(es), Orden " + ordernumber);
			
			sku = productos.get(i).getPR0();

			// PR1: DESCRIPción PRODUCTO
			qutils.datoObligatorio(productos.get(i).getPR1(), "No se especifica descripción de producto, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR1(), 50, "La descripción de producto debe tener un largo de a los m�s 50 caracter(es), Orden " + ordernumber);

			// PR2: código CLASE
			qutils.datoObligatorio(productos.get(i).getPR2(), "No se especifica código clase, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR2(), 9, "El código clase debe tener un largo de a los m�s 9 caracter(es), Orden " + ordernumber);

			// PR3: DESCRIPción CLASE
			qutils.datoObligatorio(productos.get(i).getPR3(), "No se especifica descripción clase, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR3(), 20, "La descripción clase debe tener un largo de a los m�s 20 caracter(es), Orden " + ordernumber);

			// PR4: código BARRA PARIS
			qutils.datoObligatorio(productos.get(i).getPR4(), "No se especifica código de barras Paris, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR4(), 13, "El código de barras Paris debe tener un largo de a los m�s 13 caracter(es), Orden " + ordernumber);

			// PR8: DIMENSIONES
			if (productos.get(i).getPR8() != null) {

				PR8 pr8parser = productos.get(i).getPR8();

				List<DIS> dimensiones = pr8parser.getDIS();

				for (int j = 0; j < dimensiones.size(); j++) {

					// DI0: TIPO DIMENSión
					qutils.datoObligatorio(dimensiones.get(j).getDI0(), "No se especifica tipo de dimensión, Orden " + ordernumber);
					qutils.validaLargo(dimensiones.get(j).getDI0(), 3, "El tipo de dimensión debe tener un largo de a los m�s 3 caracter(es), Orden " + ordernumber);

					// DI1: código DIMENSión
					// qutils.datoObligatorio(dimensiones.get(j).getDI1(), "No se especifica código de dimensión, Orden
					// " + ordernumber);
					qutils.validaLargo(dimensiones.get(j).getDI1(), 4, "El código de dimensión debe tener un largo de a los m�s 4 caracter(es), Orden " + ordernumber);

					// DI2: VALOR DIMENSión
					qutils.datoObligatorio(dimensiones.get(j).getDI2(), "No se especifica valor de dimensión, Orden " + ordernumber);
					qutils.validaLargo(dimensiones.get(j).getDI2(), 20, "El valor de dimensión debe tener un largo de a los m�s 20 caracter(es), Orden " + ordernumber);
				}
			}

			// PR10: TIPO DE FLUJO
			qutils.datoObligatorio(productos.get(i).getPR10(), "No se especifica tipo de flujo, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR10(), 3, "El tipo de flujo debe tener un largo de a los m�s 3 caracter(es), Orden " + ordernumber);

			// PR11: INNERPACK
			qutils.datoObligatorio(productos.get(i).getPR11(), "No se especifica innerpack, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR11(), 7, "El innerpack debe tener un largo de a los m�s 7 d�gitos, Orden " + ordernumber);

			// PR12: CASEPACK
			qutils.datoObligatorio(productos.get(i).getPR12(), "No se especifica casepack, Orden " + ordernumber);
			qutils.validaLargo(productos.get(i).getPR12(), 7, "El casepack debe tener un largo de a los m�s 7 d�gitos, Orden " + ordernumber);
			
			if (productos.get(i).getPR13() != null && 
				(productos.get(i).getPR13().getATC0() == null || productos.get(i).getPR13().getATC0().isEmpty()) &&
					productos.get(i).getPR13().getATC1() != null) {
				qutils.datoObligatorio(productos.get(i).getPR13().getATC0(), "No se indica código de ATC para SKU "+sku+", orden de compra " + ordernumber);
			} else if (productos.get(i).getPR13() != null && 
						(productos.get(i).getPR13().getATC1() == null) &&
							productos.get(i).getPR13().getATC0() != null && !productos.get(i).getPR13().getATC0().isEmpty()) {
				qutils.datoObligatorio(productos.get(i).getPR13().getATC1(), ((productos.get(i).getPR13().getATC1() < 0) ? "No se indica el valor de curva para SKU "+sku+", orden de compra " : "El valor de curva debe ser mayor a cero, SKU XXXX y orden de compra ") + ordernumber);		
			}
			
			if (productos.get(i).getPR13() != null && productos.get(i).getPR13().getATC1() != null) {
				qutils.validaLargo(productos.get(i).getPR13().getATC1(), 4, "El valor de curva no debe ser mayor a 4 d�gitos, SKU "+sku+" y orden de compra "+ordernumber);
			}
		}

		// A13: código LOCAL DE ENTREGA
		qutils.datoObligatorio(qorden.getA13(), "No se especifica código de local de entrega, Orden " + ordernumber);
		qutils.validaLargo(qorden.getA13(), 5, "El código de local de entrega debe tener un largo de a los m�s 5 caracter(es), Orden " + ordernumber);

		// A14: DESCUENTOS GENERALES
		if (qorden.getA14() != null) {

			A14 a14parser = qorden.getA14();

			if (a14parser.getDCS() == null || a14parser.getDCS().size() <= 0) {
				throw new LoadDataException("No se especifica bloque descuentos, Orden " + ordernumber);
			}

			List<DCS> descuentos = a14parser.getDCS();
			for (int i = 0; i < descuentos.size(); i++) {

				// DC0: VALOR DESCUENTO
				qutils.datoObligatorio(descuentos.get(i).getDC0(), "No se especifica valor de descuento, Orden " + ordernumber);
				qutils.validaLargo(descuentos.get(i).getDC0(), 5, "El valor de descuento debe tener un largo de a los m�s 5 d�gitos, Orden " + ordernumber);

				// DC1: TIPO DESCUENTO
				qutils.datoObligatorio(descuentos.get(i).getDC1(), "No se especifica tipo de descuento, Orden " + ordernumber);
				qutils.validaLargo(descuentos.get(i).getDC1(), 1, "El tipo de descuento debe tener un largo de a los m�s 1 caracter(es), Orden " + ordernumber);
			}
		}

		// A15: DETALLE DE ORDEN
		if (qorden.getA15() == null || qorden.getA15().getDTS() == null || qorden.getA15().getDTS().size() <= 0) {
			throw new LoadDataException("No se especifica bloque de detalle de orden");
		}

		List<DTS> detalle = qorden.getA15().getDTS();
		String itemsku = null;

		for (int i = 0; i < detalle.size(); i++) {
			// DT0: código SKU PRODUCTO
			qutils.datoObligatorio(detalle.get(i).getDT0(), "No se especifica código sku producto, Orden " + ordernumber);
			qutils.validaLargo(detalle.get(i).getDT0(), 9, "El código sku producto debe tener un largo de a los m�s 9 caracter(es), Orden " + ordernumber);
			itemsku = detalle.get(i).getDT0();

			// DT1: CANTIDAD
			qutils.datoObligatorio(detalle.get(i).getDT1(), "No se especifica cantidad de producto, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(detalle.get(i).getDT1(), 11, "La cantidad de producto debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

			// DT2: UNIDAD DE MEDIDA
			qutils.datoObligatorio(detalle.get(i).getDT2(), "No se especifica unidad de medida, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(detalle.get(i).getDT2(), 7, "El unidad de medida debe tener un largo de a los m�s 7 caracter(es), Orden " + ordernumber + ", Producto " + itemsku);

			// JPE 20200708
			// Para la validación de largos en los costos y precios se convertir� a Long para que no considere la parte decimal
			// Esto es posible solo porque se trata de CL y para no cambiar el m�todo que se usa en otras validaciones (considerar)
			
			// DT3: COSTO LISTA
			qutils.datoObligatorio(detalle.get(i).getDT3(), "No se especifica costo lista, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(Long.valueOf((long)detalle.get(i).getDT3()), 11, "El costo lista debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

			// DT4: COSTO FINAL
			qutils.datoObligatorio(detalle.get(i).getDT4(), "No se especifica costo final, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(Long.valueOf((long)detalle.get(i).getDT4()), 11, "El costo final debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

			// DT5: PRECIO NORMAL
			qutils.datoObligatorio(detalle.get(i).getDT5(), "No se especifica precio normal, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(Long.valueOf((long)detalle.get(i).getDT5()), 11, "El precio normal debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

			// DT6: PRECIO OFERTA
			qutils.datoObligatorio(detalle.get(i).getDT6(), "No se especifica precio oferta, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(Long.valueOf((long)detalle.get(i).getDT6()), 11, "El precio oferta debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

			// DT7: DESCUENTOS
			if (detalle.get(i).getDT7() != null) {

				List<DCS> descuentos = detalle.get(i).getDT7().getDCS();
				for (int j = 0; j < descuentos.size(); j++) {

					// DC0: VALOR DESCUENTO
					qutils.datoObligatorio(descuentos.get(j).getDC0(), "No se especifica valor de descuento, Orden " + ordernumber + ", Producto " + itemsku);
					qutils.validaLargo(descuentos.get(j).getDC0(), 5, "El valor de descuento debe tener un largo de a los m�s 5 d�gitos, Orden " + ordernumber + ", Producto " + itemsku);

					// DC1: TIPO DESCUENTO
					qutils.datoObligatorio(descuentos.get(j).getDC1(), "No se especifica tipo de descuento, Orden " + ordernumber + ", Producto " + itemsku);
					qutils.validaLargo(descuentos.get(j).getDC1(), 1, "El tipo de descuento debe tener un largo de a los m�s 1 caracter(es), Orden " + ordernumber + ", Producto " + itemsku);
				}
			}
		}

		// A16: DETALLE DE PREDISTRIBUción
		if (qorden.getA16() == null || qorden.getA16().getPDS() == null || qorden.getA16().getPDS().size() <= 0) {
			throw new LoadDataException("No se especifica bloque de detalle de predistribución");
		}

		List<PDS> predistribucion = qorden.getA16().getPDS();
		String location = null;

		for (int i = 0; i < predistribucion.size(); i++) {
			// PD0: código TIENDA DESTINO
			qutils.datoObligatorio(predistribucion.get(i).getPD0(), "No se especifica código tienda destino, Orden " + ordernumber);
			qutils.validaLargo(predistribucion.get(i).getPD0(), 5, "El código tienda destino debe tener un largo de a los m�s 5 caracter(es), Orden " + ordernumber);
			location = predistribucion.get(i).getPD0();

			// PD1: SKU
			qutils.datoObligatorio(predistribucion.get(i).getPD1(), "No se especifica sku producto, Orden " + ordernumber + ", Producto " + itemsku);
			qutils.validaLargo(predistribucion.get(i).getPD1(), 9, "El sku producto debe tener un largo de a los m�s 9 caracter(es), Orden " + ordernumber);
			itemsku = predistribucion.get(i).getPD1();

			// PD2: CANTIDAD
			qutils.datoObligatorio(predistribucion.get(i).getPD2(), "No se especifica cantidad de predistribución, Orden " + ordernumber + ", Producto " + itemsku + ", Local " + location);
			qutils.validaLargo(predistribucion.get(i).getPD2(), 11, "La cantidad de predistribución debe tener un largo de a los m�s 11 d�gitos, Orden " + ordernumber + ", Producto " + itemsku + ", Local " + location);

			// PD3: SECPRED
			qutils.datoObligatorio(predistribucion.get(i).getPD3(), "No se especifica secpred, Orden " + ordernumber + ", Producto " + itemsku + ", Local " + location);
			qutils.validaLargo(predistribucion.get(i).getPD3(), 5, "El secpred debe tener un largo de a los m�s 5 d�gitos, Orden " + ordernumber + ", Producto " + itemsku + ", Local " + location);
		}
	}

	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}

	private Map<String, ItemWithAtcW> doAddItems(QORDEN orderParser, Long orderNumber, VendorW vendor) throws LoadDataException, ServiceException {

		List<PRS> itemParser = orderParser.getA12().getPRS();
		Map<String, ItemWithAtcW> itemMap = new HashMap<String, ItemWithAtcW>();
		ItemW itemw = null;
		ItemClassW itemclass = null;
		FlowTypeW flowtype = null;
		VendorItemW vendoritem = null;

		String atccode = "";
		Integer atcvalue = 0;
		String sku;
		String description;
		String classcode;
		String classdescription;
		String barcode;
		String trademark;
		String vendoritemcode;
		String flowtypecode;
		Integer innerpack;
		Integer casepack;
		boolean isAtc = false;
		
		for (int i = 0; i < itemParser.size(); i++) {
			PRS detParser = itemParser.get(i);
			
			isAtc = false;
			if (detParser.getPR13() != null &&
					detParser.getPR13().getATC0() != null && !detParser.getPR13().getATC0().isEmpty() &&
						detParser.getPR13().getATC1() != null) {
				isAtc = true;
				
				// ATC: código
				try {
					atccode = detParser.getPR13().getATC0().trim();
				} catch (Exception e) {
					throw new LoadDataException("No es posible obtener el code de atc, Orden " + orderNumber);
				}
				
				// ATC: VALUE
				try {
					atcvalue = detParser.getPR13().getATC1().intValue();
				} catch (Exception e) {
					throw new LoadDataException("No es posible obtener el name del atc, Orden " + orderNumber);
				}
			}

			// MAESTRO ITEM: SKU
			try {
				sku = detParser.getPR0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el sku (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: DESCRIPción
			try {
				description = detParser.getPR1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción de producto (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: código CLASE
			try {
				classcode = detParser.getPR2().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de clase de producto (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: DESCRIPción CLASE
			try {
				classdescription = detParser.getPR3().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción de clase de producto (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: código BARRA
			try {
				barcode = detParser.getPR4().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de barras (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: código PRODUCTO PROVEEDOR
			try {
				vendoritemcode = detParser.getPR5().trim();
			} catch (Exception e) {
				vendoritemcode = "";
			}

			// MAESTRO ITEM: CAPACIDAD
//			try {
//				detParser.getPR6().intValue();
//			} catch (Exception e) {
//				;
//			}

			// MAESTRO ITEM: LEADTIME
//			try {
//				detParser.getPR7();
//			} catch (Exception e) {
//				;
//			}

			// MAESTRO ITEM: TIPO DE FLUJO
			try {
				flowtypecode = detParser.getPR10().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el tipo de flujo (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: INNERPACK
			try {
				innerpack = detParser.getPR11();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el innerpack (Maestro de Productos), Orden " + orderNumber);
			}

			// MAESTRO ITEM: CASEPACK
			try {
				casepack = detParser.getPR12();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el casepack (Maestro de Productos), Orden " + orderNumber);
			}
			
			// MAESTRO ITEM: MARCA
			try {
				trademark = detParser.getPR14().trim();
			} catch (Exception e) {
				trademark = "";
			}

			// AGREGA CLASE
			ItemClassW[] itemclassArr = itemclassserver.getByPropertyAsArray("code", classcode);
			if (itemclassArr == null || itemclassArr.length <= 0) {
				itemclass = new ItemClassW();

				itemclass.setCode(classcode);
				itemclass.setName(classdescription);

				itemclass = itemclassserver.addItemClass(itemclass);
			} else {
				itemclass = itemclassArr[0];
				itemclass.setName(classdescription.equals("") ? itemclass.getName() : classdescription);

				itemclass = itemclassserver.updateItemClass(itemclass);
			}

			// VERIFICA TIPO DE FLUJO
			FlowTypeW[] flowtypeArr = flowtypeserver.getByPropertyAsArray("code", flowtypecode);
			if (flowtypeArr == null || flowtypeArr.length <= 0) {
				throw new LoadDataException("No existe el tipo de flujo " + flowtypecode + ", Orden " + orderNumber);
			}

			flowtype = flowtypeArr[0];

			ItemW[] itemArr = itemserver.getByPropertyAsArray("internalcode", sku);
			if (itemArr == null || itemArr.length <= 0) {
				itemw = new ItemW();

				itemw.setInternalcode(sku);
				itemw.setName(description);
				itemw.setBarcode(barcode);
				itemw.setItemclassid(itemclass.getId());
				itemw.setFlowtypeid(flowtype.getId());
				itemw.setUnitid(-1L);
				itemw.setCasepack(casepack);
				itemw.setInnerpack(innerpack);
				itemw.setTrademark(trademark);

				if ((casepack * innerpack) == 0) {
					// ENVIAR CORREO
					sendCasePackInnerPackMail(sku, casepack.intValue(), innerpack.intValue());
				}

				// MAESTRO ITEM : DIMENSIONES
				List<DIS> disList = null;
				try {
					disList = detParser.getPR8().getDIS();
				} catch (Exception e) {
					disList = null;
				}

				if (disList != null) {
					if (disList.size() != 3) {
						throw new LoadDataException("Deben venir solo 3 dimensiones, Producto " + sku + ", Orden " + orderNumber);
					}

					String dimtype;
					String dimvalue;

					for (int j = 0; j < disList.size(); j++) {
						// MAESTRO ITEM : TIPO DIMENSION
						try {
							dimtype = disList.get(j).getDI0().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica tipo de dimensión, Orden " + orderNumber);
						}

						// MAESTRO ITEM : código DIMENSión
						try {
							disList.get(j).getDI1().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica código de dimensión, Orden " + orderNumber);
						}

						// MAESTRO ITEM : VALOR DIMENSión
						try {
							dimvalue = disList.get(j).getDI2().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica valor de dimensión, Orden " + orderNumber);
						}

						if (dimtype.equalsIgnoreCase("TAL")) {
							itemw.setSize(dimvalue);
						} else if (dimtype.equalsIgnoreCase("COL")) {
							itemw.setColor(dimvalue);
						} else if (dimtype.equalsIgnoreCase("DIM")) {
							itemw.setDimension(dimvalue);
						}
					}
				}

				// MAESTRO ITEM: EAN HOMOLOGADO
				List<String> eanList = null;
				try {
					eanList = detParser.getPR9().getE0();
				} catch (Exception e) {
					eanList = null;
				}

				if (eanList != null) {

					String eancode;

					for (int j = 0; j < eanList.size(); j++) {
						// MAESTRO ITEM : EAN
						try {
							eancode = eanList.get(j).trim();
						} catch (Exception e) {
							eancode = "";
						}

						if (!eancode.equals("")) {
							itemw.setEan3(itemw.getEan2());
							itemw.setEan2(itemw.getEan1());
							itemw.setEan1(eancode);
						}
					}
				}

				itemw = itemserver.addItem(itemw);
			} else {
				itemw = itemArr[0];

				itemw.setName(description.equals(itemw.getName()) ? itemw.getName() : description);
				itemw.setBarcode(barcode.equals(itemw.getBarcode()) ? itemw.getBarcode() : barcode);
				itemw.setItemclassid(itemclass.getId().equals(itemw.getItemclassid()) ? itemw.getItemclassid() : itemclass.getId());
				itemw.setFlowtypeid(flowtype.getId().equals(itemw.getFlowtypeid()) ? itemw.getFlowtypeid() : flowtype.getId());
				itemw.setCasepack(casepack.intValue() == itemw.getCasepack() ? itemw.getCasepack() : casepack.intValue());
				itemw.setInnerpack(innerpack.intValue() == itemw.getInnerpack() ? itemw.getInnerpack() : innerpack.intValue());
				itemw.setTrademark(trademark.equals(itemw.getTrademark()) ? itemw.getTrademark() : trademark);

				if ((casepack * innerpack) == 0) {
					// ENVIAR CORREO
					sendCasePackInnerPackMail(sku, casepack.intValue(), innerpack.intValue());
				}

				// MAESTRO ITEM : DIMENSIONES
				List<DIS> disList = null;
				try {
					disList = detParser.getPR8().getDIS();
				} catch (Exception e) {
					disList = null;
				}

				if (disList != null) {
					if (disList.size() != 3) {
						throw new LoadDataException("Deben venir solo 3 dimensiones, Producto " + sku + ", Orden " + orderNumber);
					}

					String dimtype;
					String dimvalue;

					for (int j = 0; j < disList.size(); j++) {
						// MAESTRO ITEM : TIPO DIMENSION
						try {
							dimtype = disList.get(j).getDI0().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica tipo de dimensión, Orden " + orderNumber);
						}

						// MAESTRO ITEM : código DIMENSión
						try {
							disList.get(j).getDI1().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica código de dimensión, Orden " + orderNumber);
						}

						// MAESTRO ITEM : VALOR DIMENSión
						try {
							dimvalue = disList.get(j).getDI2().trim();
						} catch (Exception e) {
							throw new LoadDataException("No se especifica valor de dimensión, Orden " + orderNumber);
						}

						if (dimtype.equalsIgnoreCase("TAL")) {
							itemw.setSize(dimvalue.equals(itemw.getSize()) ? itemw.getSize() : dimvalue);
						} else if (dimtype.equalsIgnoreCase("COL")) {
							itemw.setColor(dimvalue.equals(itemw.getColor()) ? itemw.getColor() : dimvalue);
						} else if (dimtype.equalsIgnoreCase("DIM")) {
							itemw.setDimension(dimvalue.equals(itemw.getDimension()) ? itemw.getDimension() : dimvalue);
						}
					}
				}

				// MAESTRO ITEM: EAN HOMOLOGADO
				List<String> eanList = null;
				try {
					eanList = detParser.getPR9().getE0();
				} catch (Exception e) {
					eanList = null;
				}

				if (eanList != null) {

					String eancode;

					for (int j = 0; j < eanList.size(); j++) {
						// MAESTRO ITEM : EAN
						try {
							eancode = eanList.get(j).trim();
						} catch (Exception e) {
							eancode = "";
						}

						if (!eancode.equals("") && !eancode.equals(itemw.getEan1()) && !eancode.equals(itemw.getEan2()) && !eancode.equals(itemw.getEan3())) {

							itemw.setEan3(itemw.getEan2());
							itemw.setEan2(itemw.getEan1());
							itemw.setEan1(eancode);
						}
					}
				}

				itemw = itemserver.updateItem(itemw);
			}

			// Se agregan locales al mapa
			ItemWithAtcW itemack = new ItemWithAtcW();
			itemack.setItem(itemw);
			itemack.setHastAtc(isAtc);
			itemMap.put(sku, itemack);

			// AGREGA VENDOR ITEM
			VendorItemId viKey = new VendorItemId(vendor.getId(), itemw.getId());
			VendorItemW viArr = null;
			try {
				viArr = vendoritemserver.getById(viKey);

				vendoritem = viArr;
				vendoritem.setVendoritemcode(vendoritemcode.equals(vendoritem.getVendoritemcode()) ? vendoritem.getVendoritemcode() : vendoritemcode);

				vendoritem = vendoritemserver.updateVendorItem(vendoritem);
			} catch (NotFoundException e) {

				vendoritem = new VendorItemW();
				vendoritem.setItemid(itemw.getId());
				vendoritem.setVendorid(vendor.getId());
				vendoritem.setDirectdelivery(false);
				vendoritem.setVendoritemcode(vendoritemcode);

				vendoritem = vendoritemserver.addVendorItem(vendoritem);
			}
			
			vendoritemserver.flush();
		
			//Agregar ATC
			if(isAtc){
				AtcW atc = null;
				try {
					atc = atcServer.getByPropertyAsSingleResult("code", atccode);
				} catch (Exception e) {
					logger.debug("No se encontr� el atc "+atccode+". Ser� creado.");
				}
					
				if(atc == null){
					atc = new AtcW();
					atc.setCode(atccode);
					atc = atcServer.addElement(atc);
				}
					
				ItemAtcId id = new ItemAtcId(atc.getId(), itemw.getId());
				ItemAtcW itemAtc = null;
				try {
					itemAtc = itemAtcServer.getById(id);
				} catch (Exception e) {
					logger.debug("No se encontr� la relación del atc "+atccode
							+" con el sku "+itemw.getInternalcode()+". Ser� creada");
				}
					
				if(itemAtc == null){
					itemAtc = new ItemAtcW();
					itemAtc.setAtcid(atc.getId());
					itemAtc.setItemid(itemw.getId());
					itemAtc.setCurve(Long.valueOf(atcvalue));
					itemAtcServer.addIdentifiable(itemAtc);
				}else if(!itemAtc.getCurve().equals(Long.valueOf(atcvalue))){
					itemAtc.setCurve(Long.valueOf(atcvalue));
					itemAtcServer.updateIdentifiable(itemAtc);
				}
			}
		}
		return itemMap;
	}

	private Map<String, LocationW> doAddLocations(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {

		List<LS> localesParser = orderParser.getA11().getLS();
		LocationW locationw = null;
		Map<String, LocationW> locationMap = new HashMap<String, LocationW>();

		String code;
		String description;
		String shortdescription;
		String address;
		String commune;
		String city;

		for (int i = 0; i < localesParser.size(); i++) {
			LS detParser = localesParser.get(i);

			// MAESTRO LOCALES: código
			try {
				code = detParser.getL0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de local (Maestro de Locales)");
			}

			// MAESTRO LOCALES: DESCRIPción
			try {
				description = detParser.getL2().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción de local (Maestro de Locales)");
			}

			// MAESTRO LOCALES: DESCRIPción CORTA
			try {
				shortdescription = detParser.getL1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción corta de local (Maestro de Locales)");
			}

			// MAESTRO LOCALES: DIRECción
			try {
				address = detParser.getL3().trim();
			} catch (Exception e) {
				address = "";
			}

			// MAESTRO LOCALES: COMUNA
			try {
				commune = detParser.getL4().trim();
			} catch (Exception e) {
				commune = "";
			}

			// MAESTRO LOCALES: CIUDAD
			try {
				city = detParser.getL5().trim();
			} catch (Exception e) {
				city = "";
			}

			LocationW[] localArr = locationserver.getByPropertyAsArray("code", code);
			if (localArr == null || localArr.length <= 0) {
				locationw = new LocationW();

				locationw.setCode(code);
				locationw.setName(description);
				locationw.setShortname(shortdescription);
				locationw.setAddress(address);
				locationw.setCommune(commune);
				locationw.setCity(city);
				locationw.setEmail("");
				locationw.setFax("");
				locationw.setPhone("");
				locationw.setPriority(1);
				locationw.setWarehouse(false);

				locationw = locationserver.addLocation(locationw);
			} else {
				locationw = localArr[0];
				locationw.setName(description.equals("") ? locationw.getName() : description);

				// EBO 06-09-2014: No se actualiza el nombre corto
				// locationw.setShortname(shortdescription.equals("") ? locationw.getShortname() : shortdescription);
				locationw.setAddress((address.equals("") || address.equals("VeV")) ? locationw.getAddress() : address);
				locationw.setCommune(commune.equals("") ? locationw.getCommune() : commune);
				locationw.setCity(city.equals("") ? locationw.getCity() : city);

				locationw = locationserver.updateLocation(locationw);
			}

			// Se agregan locales al mapa
			locationMap.put(code, locationw);
		}
		return locationMap;
	}

	private LocationW doAddSaleStoreLocation(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {

		List<LS> localesParser = orderParser.getA11().getLS();

		String code;
		String description;
		String shortdescription;
		String address;
		LocationW locationw = null;
		for (int i = 0; i < localesParser.size(); i++) {
			LS detParser = localesParser.get(i);

			try {
				code = detParser.getL0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de local (Maestro de Locales)");
			}

			// Busca VeV en campo direccion
			try {
				address = detParser.getL3().trim();
			} catch (Exception e) {
				address = "";
			}

			// MAESTRO LOCALES: DESCRIPción
			try {
				description = detParser.getL2().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción de local (Maestro de Locales)");
			}

			// MAESTRO LOCALES: DESCRIPción CORTA
			try {
				shortdescription = detParser.getL1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la descripción corta de local (Maestro de Locales)");
			}

			if ("VeV".equals(address)) {
				LocationW[] localArr = locationserver.getByPropertyAsArray("code", code);
				if (localArr == null || localArr.length <= 0) {
					locationw = new LocationW();
					// L0, L1 y L2
					locationw.setCode(code);
					locationw.setName(description);
					locationw.setShortname(shortdescription);
					locationw.setAddress("");
					locationw.setCommune("");
					locationw.setCity("");
					locationw.setEmail("");
					locationw.setFax("");
					locationw.setPhone("");
					locationw.setPriority(1);
					locationw.setWarehouse(false);

					locationw = locationserver.addLocation(locationw);
				} else {
					// retorna el primer local encontrado
					locationw = localArr[0];
				}
				
				//sale con el primero que encuentre
				break;
			}

		}
		return locationw;
	}

	private void doAddOrderDiscounts(QORDEN orderParser, OrderW order) throws LoadDataException, ServiceException {

		List<DCS> discountParser = orderParser.getA14().getDCS();
		OrderDiscountW orderdiscountw = null;

		Double value;
		String type;
		String description;
		String discountcode;

		for (int i = 0; i < discountParser.size(); i++) {
			DCS detParser = discountParser.get(i);

			// DESCUENTOS GENERALES: VALOR
			try {
				value = detParser.getDC0() / 100;
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el valor de descuento general, Orden " + order.getNumber());
			}

			// DESCUENTOS GENERALES: TIPO
			try {
				type = detParser.getDC1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el tipo de descuento general, Orden " + order.getNumber());
			}

			// DESCUENTOS GENERALES: GLOSA
			try {
				description = detParser.getDC2().trim();
			} catch (Exception e) {
				description = "";
			}

			// DESCUENTOS GENERALES: código DESCUENTO
			try {
				discountcode = detParser.getDC3().trim();
			} catch (Exception e) {
				discountcode = "";
			}

			orderdiscountw = new OrderDiscountW();

			if (type.equalsIgnoreCase("P")) {
				value = value / 100;
				orderdiscountw.setPercentage(true);
			} else if (type.equalsIgnoreCase("M")) {
				orderdiscountw.setPercentage(false);
			} else
				throw new LoadDataException("El tipo de descuento " + type + " no existe para la Orden " + order.getNumber());

			orderdiscountw.setCode(discountcode);
			orderdiscountw.setValue(value);
			orderdiscountw.setComment(description);
			orderdiscountw.setOrderid(order.getId());

			orderdiscountw = orderdiscountserver.addOrderDiscount(orderdiscountw);
		}
	}

	private void doAddDetailDiscounts(DTS detailParser, OrderW order, ItemW item) throws LoadDataException, ServiceException {

		List<DCS> discountParser = detailParser.getDT7().getDCS();
		DetailDiscountW detaildiscountw = null;

		Double value;
		String type;
		String description;
		String discountcode;

		for (int i = 0; i < discountParser.size(); i++) {
			DCS detParser = discountParser.get(i);

			// DESCUENTOS GENERALES: VALOR
			try {
				value = detParser.getDC0() / 100;
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el valor de descuento general, Orden " + order.getNumber());
			}

			// DESCUENTOS GENERALES: TIPO
			try {
				type = detParser.getDC1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el tipo de descuento general, Orden " + order.getNumber());
			}

			// DESCUENTOS GENERALES: GLOSA
			try {
				description = detParser.getDC2().trim();
			} catch (Exception e) {
				description = "";
			}

			// DESCUENTOS GENERALES: código DESCUENTO
			try {
				discountcode = detParser.getDC3().trim();
			} catch (Exception e) {
				discountcode = "";
			}

			detaildiscountw = new DetailDiscountW();

			if (type.equalsIgnoreCase("P")) {
				value = value / 100;
				detaildiscountw.setPercentage(true);
			} else if (type.equalsIgnoreCase("M")) {
				detaildiscountw.setPercentage(false);
			} else
				throw new LoadDataException("El tipo de descuento " + type + " no existe para la Orden " + order.getNumber());

			detaildiscountw.setCode(discountcode);
			detaildiscountw.setValue(value);
			detaildiscountw.setComment(description);
			detaildiscountw.setOrderid(order.getId());
			detaildiscountw.setItemid(item.getId());

			detaildiscountw = detaildiscountserver.addDetailDiscount(detaildiscountw);
		}
	}

	private DepartmentW doAddDepartment(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {
		DepartmentW department = null;

		String code;
		String name;

		A10 a10parser = orderParser.getA10();

		// DEPARTAMENTO: código
		try {
			code = a10parser.getD0().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código del departamento");
		}

		// DEPARTAMENTO: NOMBRE
		try {
			name = a10parser.getD1().trim();
		} catch (Exception e) {
			name = "";
		}

		DepartmentW[] depArray = departmentserver.getByPropertyAsArray("code", code);
		if (depArray == null || depArray.length == 0) {

			department = new DepartmentW();
			department.setCode(code);
			department.setName(name);

			department = departmentserver.addDepartment(department);
		} else {
			department = depArray[0];
			department.setName(name.equals("") ? department.getName() : name);
			department = departmentserver.updateDepartment(department);
		}
		return department;
	}

	private VendorW doAddVendor(QORDEN orderParser) throws LoadDataException, ServiceException {
		VendorW vendor = null;

		String code;
		String socialreason;
		String shortname;
		String rut;
		String type;
		String address;
		String commune;
		String city;

		A4 a4parser = orderParser.getA4();

		// PROVEEDOR: código
		try {
			code = a4parser.getP0().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código del proveedor");
		}

		// PROVEEDOR: RAZ�N SOCIAL
		try {
			socialreason = a4parser.getP1().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener raz�n social del proveedor");
		}

		// PROVEEDOR: NOMBRE CORTO
		try {
			shortname = a4parser.getP2().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener nombre corto del proveedor");
		}

		// PROVEEDOR: RUT
		try {
			rut = a4parser.getP3().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener rut del proveedor");
		}

		// PROVEEDOR: TIPO
		try {
			type = a4parser.getP4().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener tipo del proveedor");
		}

		// PROVEEDOR: DIRECción
		try {
			address = a4parser.getP5().trim();
		} catch (Exception e) {
			address = "";
		}

		// PROVEEDOR: COMUNA
		try {
			commune = a4parser.getP6().trim();
		} catch (Exception e) {
			commune = "";
		}

		// PROVEEDOR: CIUDAD
		try {
			city = a4parser.getP7().trim();
		} catch (Exception e) {
			city = "";
		}

		// JPE 20201009
		// Para proveedores nacionales se eliminan los ceros a la izquierda del RUT
		boolean domestic = type.equalsIgnoreCase("N") ? true : false;
		rut = domestic ? ClassUtilities.trimZerosLeft(rut) : rut;

		VendorW[] vendorArray = vendorserver.getByPropertyAsArray("code", code);
		VendorW[] vendorByRutArray = null;
		if (vendorArray == null || vendorArray.length == 0) {
			// Validar que el RUT no exista previamente para otro proveedor
			vendorByRutArray = vendorserver.getByPropertyAsArray("rut", rut);
			if (vendorByRutArray == null || vendorByRutArray.length == 0) {
				// Crear el nuevo proveedor
				vendor = new VendorW();
				vendor.setRut(rut);
				vendor.setNumber(0);
				vendor.setName(socialreason);
				vendor.setTradename(shortname);
				vendor.setAddress(address);
				vendor.setPhone("");
				vendor.setCommune(commune);
				vendor.setCity(city);
				vendor.setEmail("");
				vendor.setFax("");
				vendor.setCode(code);			
				vendor.setDomestic(domestic);
				vendor.setLastbulknumber(0);
				vendor.setLogisticscode(logisticreportmanager.getNextAvailableVendorCode());
				vendor.setFirstvevcddate(null);
				vendor.setFirstvevpddate(null);
				// GLN no se setea
				
				vendor = vendorserver.addVendor(vendor);
			}
			else {
				throw new LoadDataException("El RUT " + vendorByRutArray[0].getRut() + " existe para otro proveedor con código " + vendorByRutArray[0].getCode());
			}
		}
		else {
			vendor = vendorArray[0];
			
			// Validar si cambió el RUT del proveedor del sistema con respecto al que viene en la OC
			if (!rut.equals("") && !rut.equals(vendor.getRut())) {
				// Validar que el nuevo RUT no exista previamente para otro proveedor
				vendorByRutArray = vendorserver.getByPropertyAsArray("rut", rut);
				if (vendorByRutArray != null && vendorByRutArray.length > 0) {
					throw new LoadDataException("El RUT " + vendorByRutArray[0].getRut() + " existe para otro proveedor con código " + vendorByRutArray[0].getCode());
				}
				
				vendor.setRut(rut); // JPE 20200813
			}			
			
			// Actualizar el proveedor
			vendor.setName(socialreason.equals("") ? vendor.getName() : socialreason);
			vendor.setTradename(shortname.equals("") ? vendor.getTradename() : shortname);
			vendor.setAddress(address.equals("") ? vendor.getAddress() : address);
			vendor.setCommune(commune.equals("") ? vendor.getCommune() : commune);
			vendor.setCity(city.equals("") ? vendor.getCity() : city);
			vendor.setLastbulknumber(0);
			vendor.setDomestic(type.equalsIgnoreCase("N") ? true : false);
			
			vendor = vendorserver.updateVendor(vendor);
		}
		
		return vendor;
	}

	private ResponsableW doAddResponsable(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {
		ResponsableW responsable = null;

		String code;
		String name;
		String lastname;
		String phone;
		String fax;
		String email;
		String rut;
		String position;

		A3 a3parser = orderParser.getA3();

		// RESPONSABLE: código
		try {
			code = a3parser.getC0().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código del responsable");
		}

		// RESPONSABLE: NOMBRE
		try {
			name = a3parser.getC1().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el nombre del responsable");
		}

		// RESPONSABLE: APELLIDOS
		try {
			lastname = a3parser.getC2().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el apellido del responsable");
		}

		// RESPONSABLE: TELEFONO
		try {
			phone = a3parser.getC3().trim();
		} catch (Exception e) {
			phone = "";
		}

		// RESPONSABLE: FAX
		try {
			fax = a3parser.getC4().trim();
		} catch (Exception e) {
			fax = "";
		}

		// RESPONSABLE: EMAIL
		try {
			email = a3parser.getC5().trim();
		} catch (Exception e) {
			email = "";
		}

		// RESPONSABLE: RUT
		try {
			rut = a3parser.getC6().trim();
		} catch (Exception e) {
			rut = "";
		}

		// RESPONSABLE: CARGO
		try {
			position = a3parser.getC7().trim();
		} catch (Exception e) {
			position = "";
		}

		ResponsableW[] respArray = responsableserver.getByPropertyAsArray("code", code);
		if (respArray == null || respArray.length == 0) {

			responsable = new ResponsableW();
			responsable.setCode(code);
			responsable.setName(name);
			responsable.setLastname(lastname);
			responsable.setPhone(phone);
			responsable.setFax(fax);
			responsable.setEmail(email);
			responsable.setRut(rut);
			responsable.setPosition(position);

			responsable = responsableserver.addResponsable(responsable);
		} else {
			responsable = respArray[0];
			responsable.setName(name.equals("") ? responsable.getName() : name);
			responsable.setLastname(lastname.equals("") ? responsable.getLastname() : lastname);
			responsable.setPhone(phone.equals("") ? responsable.getPhone() : phone);
			responsable.setFax(fax.equals("") ? responsable.getFax() : fax);
			responsable.setEmail(email.equals("") ? responsable.getEmail() : email);
			responsable.setRut(rut.equals("") ? responsable.getRut() : rut);
			responsable.setPosition(position.equals("") ? responsable.getPosition() : position);
			responsable = responsableserver.updateResponsable(responsable);
		}
		return responsable;
	}

	private OrderTypeW getOrderType(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {
		OrderTypeW ordertype = null;
		String code = "";

		// TIPO DE ORDEN: CODIGO
		try {
			code = orderParser.getA9().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el código de tipo");
		}

		OrderTypeW[] ordertypeArray = ordertypeserver.getByPropertyAsArray("code", code);
		if (ordertypeArray == null || ordertypeArray.length == 0) {
			return null;
		} else {
			ordertype = ordertypeArray[0];
		}
		return ordertype;
	}

	private ClientW doAddClient(QORDEN orderParser, Long orderNumber) throws LoadDataException, ServiceException {
		B2 clientParser = orderParser.getA2().getB2();

		ClientW client = null;

		String name = "";
		String rut = "";
		String phone = "";
		String address = "";
		String streetnumber = "";
		String departmentnumber = "";
		String homenumber = "";
		String region = "";
		String commune = "";
		String city = "";
		String comment = "";

		// CLIENTE: NOMBRE
		try {
			name = clientParser.getCL0().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se especifica nombre de cliente, Orden " + orderNumber);
		}

		// CLIENTE: RUT
		try {
			String ruttmp = clientParser.getCL1().trim();

			ruttmp = ClassUtilities.trimZerosLeft(ruttmp);
			rut = ruttmp + ClassUtilities.getDigitoVerificador(Integer.valueOf(ruttmp));

		} catch (Exception e) {
			throw new LoadDataException("No se especifica rut de cliente, Orden " + orderNumber);
		}

		// CLIENTE: TELEFONO
		try {
			phone = clientParser.getCL2().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener el tel�fono del cliente, Orden " + orderNumber);
		}

		// CLIENTE: DIRECción
		try {
			address = clientParser.getCL3().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la dirección del cliente, Orden " + orderNumber);
		}

		// CLIENTE: NUMERO DE CALLE
		try {
			streetnumber = clientParser.getCL4().trim();
		} catch (Exception e) {
			streetnumber = "";
		}

		// CLIENTE: NUMERO DEPARTAMENTO
		try {
			departmentnumber = clientParser.getCL5().trim();
		} catch (Exception e) {
			departmentnumber = "";
		}

		// CLIENTE: NUMERO DE CASA
		try {
			homenumber = clientParser.getCL6().trim();
		} catch (Exception e) {
			homenumber = "";
		}

		// CLIENTE: NOMBRE REGION
		try {
			region = clientParser.getCL7().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la región del cliente, Orden " + orderNumber);
		}

		// CLIENTE: NOMBRE COMUNA
		try {
			commune = clientParser.getCL8().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la comuna del cliente, Orden " + orderNumber);
		}

		// CLIENTE: NOMBRE CIUDAD
		try {
			city = clientParser.getCL9().trim();
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la ciudad del cliente, Orden " + orderNumber);
		}

		// CLIENTE: COMENTARIO
		try {
			comment = clientParser.getCL10().trim();
		} catch (Exception e) {
			comment = "";
		}

		// SIEMPRE SE CREAN NUEVOS CLIENTES
		// YA QUE LOS DATOS DE REFERENCIA PUEDEN CAMBIAR DE UN DESPACHO A OTRO PARA EL MISMO CLIENTE

		client = new ClientW();

		client.setName(name);
		client.setRut(rut);
		client.setPhone(phone);
		client.setAddress(address);
		client.setRoadnumber(streetnumber);
		client.setDeptnumber(departmentnumber);
		client.setHousenumber(homenumber);
		client.setRegion(region);
		client.setCommune(commune);
		client.setCity(city);
		client.setComment(comment);

		client = clientserver.addClient(client);

		return client;
	}

	private void doAddPredeliveryDetail(QORDEN orderParser, Map<String, ItemWithAtcW> itemMap, Map<String, LocationW> locationMap, OrderW orderw, TreeMap<OrderDetailPK, OrderDetailW> orderdetailMap) throws LoadDataException, ServiceException {
		List<PDS> predDetails = orderParser.getA16().getPDS();
		Double finalcost = null;
		OrderDetailPK odPK = null;

		PreDeliveryDetailW predeliveryw = null;
		TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW> predMap = new TreeMap<PreDeliveryDetailPK, PreDeliveryDetailW>();
		Map<String, Double> predUnitsMap = new HashMap<String, Double>();

		String localcode = "";
		String sku = "";

		LocationW locationw = null;
		ItemW itemw = null;

		Double units = 0.0;
		Long sequence = 0L;
		Double totalunits = 0.0;

		for (int i = 0; i < predDetails.size(); i++) {
			PDS destino = predDetails.get(i);

			// PREDISTRIBUción: código LOCAL
			try {
				localcode = destino.getPD0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de local de destino, Orden " + orderw.getNumber());
			}

			if (!locationMap.containsKey(localcode)) {
				throw new LoadDataException("En la distribución, el código del local " + localcode + " no est� especificado en el bloque de maestros (tag O8)");
			}

			locationw = locationMap.get(localcode);

			// PREDISTRIBUCION: SKU
			try {
				sku = destino.getPD1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código sku de producto, Orden " + orderw.getId());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + orderw.getNumber());
			}

			itemw = itemMap.get(sku).getItem();

			// PREDISTRIBUción: CANTIDAD
			try {
				units = destino.getPD2();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la cantidad para local de destino, Producto: " + itemw.getInternalcode());
			}

			// PREDISTRIBUción: SECUENCIA VISUALIZACION
			try {
				sequence = destino.getPD3();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la secuencia de visualización para predistribución, Producto: " + itemw.getInternalcode());
			}

			PreDeliveryDetailPK preKey = new PreDeliveryDetailPK(orderw.getId(), itemw.getId(), locationw.getId());
			if (predMap.containsKey(preKey)) {
				throw new LoadDataException("En la distribución del producto con código " + itemw.getInternalcode() + ", se est� informando m�s de una vez el local código " + localcode + ".");
			} else {
				odPK = new OrderDetailPK(orderw.getId(), itemw.getId());

				if (!orderdetailMap.containsKey(odPK)) {
					throw new LoadDataException("El producto " + sku + " no esta contenido en el detalle de la orden " + orderw.getNumber());
				}

				finalcost = orderdetailMap.get(odPK).getFinalcost();

				predeliveryw = new PreDeliveryDetailW();
				predeliveryw.setOrderid(orderw.getId());
				predeliveryw.setItemid(itemw.getId());
				predeliveryw.setLocationid(locationw.getId());
				predeliveryw.setSequence(sequence.intValue());
				predeliveryw.setUnits(units);
				predeliveryw.setTotalneed(units * finalcost);

				// ACUMULA LAS UNIDADES DE PREDISTRIBUCION POR PRODUCTO
				if (!predUnitsMap.containsKey(sku))
					predUnitsMap.put(sku, units);
				else
					predUnitsMap.put(sku, predUnitsMap.get(sku) + units);

				totalunits = totalunits + units;

				predMap.put(preKey, predeliveryw);
			}
		}

		// VALIDA QUE LA CANTIDAD SOLICITADA PARA EL DETALLE CORRESPONDA A LA DISTRIBUCION POR PRODUCTO
		for (Map.Entry<String, Double> pddMap : predUnitsMap.entrySet()) {
			OrderDetailPK odKey = new OrderDetailPK(orderw.getId(), itemMap.get(pddMap.getKey()).getItem().getId());

			if (!pddMap.getValue().equals(orderdetailMap.get(odKey).getUnits())) {
				throw new LoadDataException("La Predistribución no coincide con el detalle de la orden, para el código Producto " + pddMap.getKey() + " en la orden N° " + orderw.getNumber());
			}
		}

		// AGREGA DETALLES DE DISTRIBUCION
		for (Map.Entry<PreDeliveryDetailPK, PreDeliveryDetailW> pdd : predMap.entrySet()) {
			predeliverydetailserver.addPreDeliveryDetail(pdd.getValue());
		}
	}

	private void doAddOrderDetail(QORDEN orderParser, OrderW order, VendorW vendorw, Map<String, ItemWithAtcW> itemMap, Map<String, LocationW> locationMap, boolean isPred) throws LoadDataException, ServiceException {

		ItemW itemw = null;
		ItemWithAtcW itemAtc = null;
		OrderDetailW orderdetailw = null;
		UnitW unit = null;
		boolean isAtc = false;

		TreeMap<OrderDetailPK, OrderDetailW> orderdetailMap = new TreeMap<OrderDetailPK, OrderDetailW>();
		HashMap<String, UnitW> unitMap = new HashMap<String, UnitW>();

		DTS orderLine = null;

		Double finalcost = 0.0;
		Double finalprice = 0.0;
		Double listcost = 0.0;
		Double listprice = 0.0;
		Double totalunits = 0.0;

		Double octotalunits = 0.0;
		Double octotalneed = 0.0;

		String sku;
		String unitm;

		List<DTS> orderDetails = orderParser.getA15().getDTS();

		// Iteracion de los detalles de la orden
		for (int i = 0; i < orderDetails.size(); i++) {

			orderLine = orderDetails.get(i);
			orderdetailw = new OrderDetailW();

			// DETALLE DE ORDEN: SKU PRODUCTO
			try {
				sku = orderLine.getDT0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica código sku de producto, Orden " + order.getNumber());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + order.getNumber());
			}

			itemAtc = itemMap.get(sku);
			itemw = itemAtc.getItem();
			isAtc = itemAtc.getHastAtc();

			// DETALLE DE ORDEN: CANTIDAD
			try {
				totalunits = orderLine.getDT1();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: UNIDAD DE MEDIDA
			try {
				unitm = orderLine.getDT2().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO LISTA
			try {
				listcost = orderLine.getDT3();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo lista de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO FINAL
			try {
				finalcost = orderLine.getDT4();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo final de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO NORMAL
			try {
				listprice = orderLine.getDT5();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio normal de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO OFERTA
			try {
				finalprice = orderLine.getDT6();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio oferta de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// AGREGA UNIDAD DE MEDIDA
			if (!unitMap.containsKey(unitm)) {
				UnitW[] unitArr = unitserver.getByPropertyAsArray("code", unitm);

				if (unitArr == null || unitArr.length == 0) {
					unit = new UnitW();
					unit.setCode(unitm);
					unit.setName(unitm);

					unit = unitserver.addUnit(unit);
				} else {
					unit = unitArr[0];
				}

				unitMap.put(unitm, unit);
			}

			unit = unitMap.get(unitm);

			itemw.setUnitid(unit.getId().equals(itemw.getUnitid()) ? itemw.getUnitid() : unit.getId());
			itemw = itemserver.updateItem(itemw);

			itemMap.get(sku).setItem(itemw);
//			itemMap.put(itemw.getInternalcode(), itemw);

			orderdetailw.setOrderid(order.getId());
			orderdetailw.setItemid(itemw.getId());
			orderdetailw.setFinalcost(finalcost);
			orderdetailw.setFinalprice(finalprice);
			orderdetailw.setListcost(listcost);
			orderdetailw.setListprice(listprice);
			orderdetailw.setUnits(totalunits);
			orderdetailw.setTotalneed(finalcost * totalunits);
			orderdetailw.setVisualorder(0);
			orderdetailw.setPackingunitid(unit.getId());
			orderdetailw.setHasatc(isAtc);

			octotalunits += orderdetailw.getUnits();
			octotalneed += orderdetailw.getTotalneed();

			OrderDetailPK odKey = new OrderDetailPK(order.getId(), itemw.getId());
			if (orderdetailMap.containsKey(odKey)) {
				throw new LoadDataException("El producto con código " + itemw.getInternalcode() + " viene en m�s de un detalle de OC.");
			}
			orderdetailMap.put(odKey, orderdetailw);

			orderdetailserver.addOrderDetail(orderdetailw);
			orderdetailserver.flush();

			// DETALLE DE ORDEN: DESCUENTOS
			if (orderLine.getDT7() != null && orderLine.getDT7().getDCS() != null && orderLine.getDT7().getDCS().size() > 0)
				doAddDetailDiscounts(orderLine, order, itemw);
		}

		order.setNeedunits(octotalunits);
		order.setTotalneed(octotalneed);

		// AGREGAR PREDISTRIBUción
		doAddPredeliveryDetail(orderParser, itemMap, locationMap, order, orderdetailMap);
	}

	private OrderW doUpdateVeVCDOrder(QORDEN orderParser, OrderW order, VendorW vendorMsg) throws LoadDataException, ServiceException {

		Map<Long, OrderDetailW> odMap = new HashMap<Long, OrderDetailW>();
		Map<String, PreDeliveryDetailW> pddMap = new HashMap<String, PreDeliveryDetailW>();
		HashMap<String, UnitW> unitMap = new HashMap<String, UnitW>();
		Map<String, Double> predUnitsMap = new HashMap<String, Double>();
		// Map<String, ItemW> itMap = new HashMap<String, ItemW>();

		String pddItLoPK;

		// PROVEEDOR ASOCIADO A LA ORDEN
		VendorW vendor = vendorserver.getById(order.getVendorid());

		// VERIFICA QUE LOS PROVEEDORES SON IGUALES
		if (!vendorMsg.getId().equals(vendor.getId())) {
			throw new LoadDataException("El proveedor indicado en el mensaje no corresponde al proveedor actual de la Orden N° " + order.getNumber());
		}

		// ELIMINA LAS OC DENTRO DE LOS LOTES
		order = deliveryreportmanager.doDeleteOrderInDeliveries(order.getId());

		// DETALLES DE ORDEN POR ITEM
		OrderDetailW[] odArr = orderdetailserver.getByPropertyAsArray("id.orderid", order.getId());
		for (OrderDetailW od : odArr) {
			if (!odMap.containsKey(od.getItemid())) {
				od.setUnits(0D);
				odMap.put(od.getItemid(), od);
			}
		}

		// PREDISTRIBUCION DE LA ORDEN POR ITEM-LOCAL
		PreDeliveryDetailW[] pddArr = predeliverydetailserver.getByPropertyAsArray("id.orderid", order.getId());
		for (PreDeliveryDetailW pdd : pddArr) {
			pddItLoPK = pdd.getItemid() + "_" + pdd.getLocationid();
			if (!pddMap.containsKey(pddItLoPK)) {
				pdd.setUnits(0D);
				pddMap.put(pddItLoPK, pdd);
			}
		}

		// ITEM DE LA ORDEN POR INTERNALCODE
		// ItemW[] itArr = itemserver.getByQueryAsArray("select it from Item as it, OrderDetail as od where od.item = it
		// and od.order.id = " + order.getId());
		// for (ItemW it : itArr){
		// if (!itMap.containsKey(it.getInternalcode())){
		// itMap.put(it.getInternalcode(), it);
		// }
		// }

		// AGREGO MAESTRO DE LOCALES
		Map<String, LocationW> locationMap = doAddLocations(orderParser, order.getNumber());

		// AGREGO MAESTRO DE PRODUCTOS
		Map<String, ItemWithAtcW> itemMap = doAddItems(orderParser, order.getNumber(), vendor);

		OrderDetailW orderdetailw = null;
		PreDeliveryDetailW predeliveryw = null;
		ItemW itemw = null;
		LocationW locationw = null;
		UnitW unit = null;

		List<DTS> orderDetails = orderParser.getA15().getDTS();
		DTS orderLine = null;

		String sku;
		String unitm;
		String localcode;
		Double odunits;
		Double finalcost = 0.0;
		Double finalprice = 0.0;
		Double listcost = 0.0;
		Double listprice = 0.0;
		Double octotalunits = 0.0;
		Double octotalneed = 0.0;
		Double pddunits = 0.0;
		Long sequence = 0L;

		boolean isOpen = false;
		boolean isNew = false;

		OrderStateTypeW modSt = orderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");
		OrderStateTypeW delSt = orderstatetypeserver.getByPropertyAsSingleResult("code", "ELIMINADA");

		// Iteracion de los detalles de la orden
		for (int i = 0; i < orderDetails.size(); i++) {

			orderLine = orderDetails.get(i);

			// DETALLE DE ORDEN: SKU PRODUCTO
			try {
				sku = orderLine.getDT0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica código sku de producto, Orden " + order.getNumber());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + order.getNumber());
			}

			itemw = itemMap.get(sku).getItem();

			// DETALLE DE ORDEN: CANTIDAD
			try {
				odunits = orderLine.getDT1();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			if (!isOpen && odunits > 0)
				isOpen = true;

			// DETALLE DE ORDEN: UNIDAD DE MEDIDA
			try {
				unitm = orderLine.getDT2().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO LISTA
			try {
				listcost = orderLine.getDT3();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo lista de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO FINAL
			try {
				finalcost = orderLine.getDT4();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo final de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO NORMAL
			try {
				listprice = orderLine.getDT5();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio normal de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO OFERTA
			try {
				finalprice = orderLine.getDT6();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio oferta de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// AGREGA UNIDAD DE MEDIDA
			if (!unitMap.containsKey(unitm)) {
				UnitW[] unitArr = unitserver.getByPropertyAsArray("code", unitm);

				if (unitArr == null || unitArr.length == 0) {
					unit = new UnitW();
					unit.setCode(unitm);
					unit.setName(unitm);

					unit = unitserver.addUnit(unit);
				} else {
					unit = unitArr[0];
				}

				unitMap.put(unitm, unit);
			}

			unit = unitMap.get(unitm);

			itemw.setUnitid(unit.getId().equals(itemw.getUnitid()) ? itemw.getUnitid() : unit.getId());
			itemw = itemserver.updateItem(itemw);
			itemMap.get(sku).setItem(itemw);

			if (!odMap.containsKey(itemw.getId())) {
				orderdetailw = new OrderDetailW();
				orderdetailw.setOrderid(order.getId());
				orderdetailw.setItemid(itemw.getId());
				isNew = true;
			} else {
				orderdetailw = odMap.get(itemw.getId());
				isNew = false;
			}

			orderdetailw.setFinalcost(finalcost);
			orderdetailw.setFinalprice(finalprice);
			orderdetailw.setListcost(listcost);
			orderdetailw.setListprice(listprice);
			orderdetailw.setUnits(odunits);
			orderdetailw.setTotalneed(finalcost * odunits);
			orderdetailw.setVisualorder(0);
			orderdetailw.setPackingunitid(unit.getId());

			if (isNew)
				orderdetailserver.addOrderDetail(orderdetailw);
			else
				orderdetailserver.updateOrderDetail(orderdetailw);

			octotalunits += orderdetailw.getUnits();
			octotalneed += orderdetailw.getTotalneed();

			odMap.put(itemw.getId(), orderdetailw);
		}

		List<PDS> predDetails = orderParser.getA16().getPDS();

		for (int i = 0; i < predDetails.size(); i++) {
			PDS destino = predDetails.get(i);

			// PREDISTRIBUción: código LOCAL
			try {
				localcode = destino.getPD0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código de local de destino, Orden " + order.getNumber());
			}

			if (!locationMap.containsKey(localcode)) {
				throw new LoadDataException("En la distribución, el código del local " + localcode + " no est� especificado en el bloque de maestros (tag O8)");
			}

			locationw = locationMap.get(localcode);

			// PREDISTRIBUCION: SKU
			try {
				sku = destino.getPD1().trim();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener el código sku de producto, Orden " + order.getId());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + order.getNumber());
			}

			itemw = itemMap.get(sku).getItem();

			// PREDISTRIBUción: CANTIDAD
			try {
				pddunits = destino.getPD2();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la cantidad para local de destino, Producto: " + itemw.getInternalcode());
			}

			// PREDISTRIBUción: SECUENCIA VISUALIZACION
			try {
				sequence = destino.getPD3();
			} catch (Exception e) {
				throw new LoadDataException("No es posible obtener la secuencia de visualización para predistribución, Producto: " + itemw.getInternalcode());
			}

			pddItLoPK = itemw.getId() + "_" + locationw.getId();

			if (!pddMap.containsKey(pddItLoPK)) {
				predeliveryw = new PreDeliveryDetailW();
				predeliveryw.setOrderid(order.getId());
				predeliveryw.setItemid(itemw.getId());
				predeliveryw.setLocationid(locationw.getId());
				isNew = true;
			} else {
				predeliveryw = pddMap.get(pddItLoPK);
				isNew = false;
			}

			predeliveryw.setUnits(pddunits);
			predeliveryw.setTotalneed(odMap.get(itemw.getId()).getFinalcost() * pddunits);
			predeliveryw.setSequence(sequence.intValue());

			// ACUMULA LAS UNIDADES DE PREDISTRIBUCION POR PRODUCTO
			if (!predUnitsMap.containsKey(sku))
				predUnitsMap.put(sku, pddunits);
			else
				predUnitsMap.put(sku, predUnitsMap.get(sku) + pddunits);

			if (isNew)
				predeliverydetailserver.addPreDeliveryDetail(predeliveryw);
			else
				predeliverydetailserver.updatePreDeliveryDetail(predeliveryw);

			pddMap.put(pddItLoPK, predeliveryw);
		}

		// VALIDA QUE LA CANTIDAD SOLICITADA PARA EL DETALLE CORRESPONDA A LA DISTRIBUCION POR PRODUCTO
		for (Map.Entry<String, Double> pdMap : predUnitsMap.entrySet()) {
			if (!pdMap.getValue().equals(odMap.get(itemMap.get(pdMap.getKey()).getItem().getId()).getUnits())) {
				throw new LoadDataException("La Predistribución no coincide con el detalle de la orden, para el código Producto " + pdMap.getKey() + " en la orden N° " + order.getNumber());
			}
		}

		// ACTUALIZA FECHAS
		// SE VALIDAN LAS FECHAS PARA ACTUALIZARLAS
		Date now = new Date();
		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		// FECHA DE VIGENCIA
		Date f_valid = null;

		try {
			String f_validSTR = orderParser.getA6().trim();
			f_valid = sdf.parse(f_validSTR);

			cal.setTime(f_valid);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_valid = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
		}

		if (f_valid.before(order.getValid())) {
			// throw new LoadDataException("La fecha de vigencia debe ser posterior a la actual para actualizarla");
		} else {
			order.setValid(f_valid);
		}

		// FECHA DE VENCIMIENTO
		Date f_expiration = null;

		try {
			String f_expirationSTR = orderParser.getA7().trim();
			f_expiration = sdf.parse(f_expirationSTR);
			cal.setTime(f_expiration);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
			f_expiration = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de vencimiento de la orden");
		}

		if (f_expiration.before(order.getExpiration()) && f_expiration.before(now)) {
			// throw new LoadDataException("La fecha de expiración debe ser posterior a la actual y a hoy para
			// actualizarla");
		} else {
			order.setExpiration(f_expiration);
		}

		// FECHA DE COMPROMISO
		Date f_original = null;

		try {
			String f_originalSTR = orderParser.getA2().getB4().trim();
			f_original = sdf.parse(f_originalSTR);

			cal.setTime(f_original);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_original = cal.getTime();

			order.setOriginaldeliverydate(f_original);
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de origen de la orden");
		}

		order.setNeedunits(octotalunits);
		order.setTotalneed(octotalneed);

		OrderStateTypeW orderstatetype = null;

		if (isOpen)
			orderstatetype = modSt;
		else
			orderstatetype = delSt;

		order.setCurrentstatetypeid(orderstatetype.getId());
		order.setCurrentstatetypedate(now);

		// CLIENTE
		B2 clientParser = orderParser.getA2().getB2();

		if (clientParser != null) {
			ClientW client = doAddClient(orderParser, order.getNumber());
			order.setClientid(client.getId());
		}

		order = orderserver.updateOrder(order);

		// AGREGA EL ESTADO
		OrderStateW state = new OrderStateW();
		state.setOrderstatetypeid(orderstatetype.getId());
		state.setOrderid(order.getId());
		state.setWhen(now);

		state = orderstateserver.addOrderState(state);

		return order;
	}

	private void doAddDirectOrderDetail(QORDEN orderParser, DirectOrderW order, VendorW vendorw, Map<String, ItemWithAtcW> itemMap) throws LoadDataException, ServiceException {
		ItemW itemw = null;
		ItemWithAtcW itemAtc = null;
		DirectOrderDetailW directorderdetailw = null;

		TreeMap<DirectOrderDetailPK, DirectOrderDetailW> orderdetailMap = new TreeMap<DirectOrderDetailPK, DirectOrderDetailW>();

		DTS orderLine = null;

		Double finalcost = 0.0;
		Double finalprice = 0.0;
		Double listcost = 0.0;
		Double listprice = 0.0;
		Double totalunits = 0.0;

		Double octotalunits = 0.0;
		Double octotalneed = 0.0;

		String sku;
		PDS pred = null;

		List<DTS> orderDetails = orderParser.getA15().getDTS();

		HashMap<String, PDS> predBySku = new HashMap<String, PDS>();
		List<PDS> predList = orderParser.getA16().getPDS();
		if (predList != null) {
			for (int i = 0; i < predList.size(); i++) {
				predBySku.put(predList.get(i).getPD1(), predList.get(i));
			}
		}

		// Iteracion de los detalles de la orden
		for (int i = 0; i < orderDetails.size(); i++) {

			orderLine = orderDetails.get(i);
			directorderdetailw = new DirectOrderDetailW();

			// DETALLE DE ORDEN: SKU PRODUCTO
			try {
				sku = orderLine.getDT0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica código sku de producto, Orden " + order.getNumber());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + order.getNumber());
			}

			itemAtc = itemMap.get(sku);
			itemw = itemAtc.getItem();

			// DETALLE DE ORDEN: CANTIDAD
			try {
				totalunits = orderLine.getDT1();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO LISTA
			try {
				listcost = orderLine.getDT3();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo lista de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO FINAL
			try {
				finalcost = orderLine.getDT4();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo final de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO NORMAL
			try {
				listprice = orderLine.getDT5();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio normal de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO OFERTA
			try {
				finalprice = orderLine.getDT6();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio oferta de producto, Orden " + order.getNumber() + ", Producto " + sku);
			}

			itemAtc.setItem(itemw);
			itemMap.put(itemw.getInternalcode(), itemAtc);

			directorderdetailw.setOrderid(order.getId());
			directorderdetailw.setItemid(itemw.getId());
			directorderdetailw.setFinalcost(finalcost);
			directorderdetailw.setFinalprice(finalprice);
			directorderdetailw.setListcost(listcost);
			directorderdetailw.setListprice(listprice);
			directorderdetailw.setUnits(totalunits);
			directorderdetailw.setTotalneed(totalunits * directorderdetailw.getFinalcost());

			pred = predBySku.get(sku);
			if (pred != null)
				directorderdetailw.setVisualorder(Long.valueOf(pred.getPD3()).intValue());
			else
				directorderdetailw.setVisualorder(0);

			octotalunits += directorderdetailw.getUnits();
			octotalneed += directorderdetailw.getUnits() * directorderdetailw.getFinalcost();

			DirectOrderDetailPK odKey = new DirectOrderDetailPK(order.getId(), itemw.getId());
			if (orderdetailMap.containsKey(odKey)) {
				throw new LoadDataException("El producto con código " + itemw.getInternalcode() + " viene en m�s de un detalle de OC.");
			}
			orderdetailMap.put(odKey, directorderdetailw);

			directorderdetailserver.addDirectOrderDetail(directorderdetailw);
			directorderdetailserver.flush();

		}

		order.setNeedunits(octotalunits);
		order.setTotalneed(octotalneed);

		order = directorderserver.updateDirectOrder(order);
	}

	private DirectOrderW doUpdateDirectOrder(QORDEN orderParser, DirectOrderW directOrder, VendorW vendorMsg) throws LoadDataException, ServiceException {

		Map<Long, DirectOrderDetailW> dodMap = new HashMap<Long, DirectOrderDetailW>();
		DirectOrderDetailW directorderdetailw = null;

		// PROVEEDOR ASOCIADO A LA ORDEN
		VendorW vendor = vendorserver.getById(directOrder.getVendorid());

		// VERIFICA QUE LOS PROVEEDORES SON IGUALES
		if (!vendorMsg.getId().equals(vendor.getId())) {
			throw new LoadDataException("El proveedor indicado en el mensaje no corresponde al proveedor actual de la Orden N° " + directOrder.getNumber());
		}

		// DETALLES DE ORDEN POR ITEM
		DirectOrderDetailW[] dodArr = directorderdetailserver.getDirectOrderDetailDataofDirectOrder(directOrder.getId());
		for (DirectOrderDetailW dod : dodArr) {
			if (!dodMap.containsKey(dod.getItemid())) {
				dod.setUnits(0D);
				dodMap.put(dod.getItemid(), dod);
			}
		}

		// AGREGO MAESTRO DE PRODUCTOS
		Map<String, ItemWithAtcW> itemMap = doAddItems(orderParser, directOrder.getNumber(), vendor);

		ItemW itemw = null;
		
		List<DTS> orderDetails = orderParser.getA15().getDTS();

		HashMap<String, PDS> predBySku = new HashMap<String, PDS>();
		List<PDS> predList = orderParser.getA16().getPDS();
		if (predList != null) {
			for (int i = 0; i < predList.size(); i++) {
				predBySku.put(predList.get(i).getPD1(), predList.get(i));
			}
		}	
		PDS pred = null;
		
		DTS orderLine = null;

		String sku;
		Double odunits;
		Double finalcost = 0.0;
		Double finalprice = 0.0;
		Double listcost = 0.0;
		Double listprice = 0.0;
		Double octotalunits = 0.0;
		Double octotalneed = 0.0;
		Double newPendingunits;
		Double newUnits;
		Double availableunits;
		Double receivedunits;

		DirectOrderStateTypeW dostParisModified = directorderstatetypeserver.getByPropertyAsSingleResult("code", "MOD_PARIS");

		// Iteracion de los detalles de la orden
		for (int i = 0; i < orderDetails.size(); i++) {

			orderLine = orderDetails.get(i);
			DirectOrderDetailW dodetaildataw = new DirectOrderDetailW();

			// DETALLE DE ORDEN: SKU PRODUCTO
			try {
				sku = orderLine.getDT0().trim();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica código sku de producto, Orden " + directOrder.getNumber());
			}

			if (!itemMap.containsKey(sku)) {
				throw new LoadDataException("El código sku " + sku + " no se encuentra en el maestro de productos, Orden " + directOrder.getNumber());
			}

			itemw = itemMap.get(sku).getItem();

			// DETALLE DE ORDEN: CANTIDAD
			try {
				odunits = orderLine.getDT1();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica cantidad total de producto, Orden " + directOrder.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO LISTA
			try {
				listcost = orderLine.getDT3();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo lista de producto, Orden " + directOrder.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: COSTO FINAL
			try {
				finalcost = orderLine.getDT4();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica costo final de producto, Orden " + directOrder.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO NORMAL
			try {
				listprice = orderLine.getDT5();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio normal de producto, Orden " + directOrder.getNumber() + ", Producto " + sku);
			}

			// DETALLE DE ORDEN: PRECIO OFERTA
			try {
				finalprice = orderLine.getDT6();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica precio oferta de producto, Orden " + directOrder.getNumber() + ", Producto " + sku);
			}

			// Si el producto que viene especificado en el detalle de orden del mensaje est� en el mapa:
			// - se setea la nueva cantidad solicitada.
			// - se realiza el cálculo de las nuevas cantidades pendientes a partir de los datos recibidos

			// Si no est� en el mapa, se agrega el nuevo detalle de orden con los datos especificados en el mensaje
			if (dodMap.containsKey(itemw.getId())) {
				dodetaildataw = (DirectOrderDetailW) dodMap.get(itemw.getId());
				dodetaildataw.setUnits(odunits);
				dodMap.put(itemw.getId(), dodetaildataw);

				// cálculo de unidades pendientes de la OC con los nuevos datos recibidos
				newUnits = Math.max(dodetaildataw.getUnits(), 0);

				availableunits = dodetaildataw.getTodeliveryunits();
				receivedunits = dodetaildataw.getReceivedunits();

				newPendingunits = newUnits - (availableunits + receivedunits);

				// Verificar la nueva cantidad de unidades pendientes
				// si es mayor o igual a cero, se aceptan las cantidades y se carga la nueva orden
				// si no, se rechaza la Orden
				// if (newPendingunits < 0)
				// error("La OC VeV Prov existe con pendientes menores para el producto " + internalcode);

				// 13-08-2008 VGU. LA Oc se acepta aunque el pendiente quede negativo. Se despliega 0
				if (newPendingunits < 0)
					logger.warn("Oc VeVPD " + directOrder.getNumber() + ". El producto " + itemw.getInternalcode() + " quedar� con pendiente negativo");
			}

			directorderdetailw = new DirectOrderDetailW();

			// Asociamos Orden con Detalle Orden
			directorderdetailw.setOrderid(directOrder.getId());
			directorderdetailw.setItemid(itemw.getId());
			directorderdetailw.setUnits(odunits);
			directorderdetailw.setListcost(listcost);
			directorderdetailw.setFinalcost(finalcost);
			directorderdetailw.setListprice(listprice);
			directorderdetailw.setFinalprice(finalprice);
			directorderdetailw.setTotalneed(odunits * finalcost);
			pred = predBySku.get(sku);
			if (pred != null)
				directorderdetailw.setVisualorder(Long.valueOf(pred.getPD3()).intValue());
			else
				directorderdetailw.setVisualorder(0);
			

			octotalunits += directorderdetailw.getUnits();
			octotalneed += directorderdetailw.getUnits() * directorderdetailw.getFinalcost();

			// agregar o actualizar el detalle de orden
			if (!dodMap.containsKey(itemw.getId())) {
				directorderdetailw = directorderdetailserver.addDirectOrderDetail(directorderdetailw);
			} else {
				directorderdetailw = directorderdetailserver.updateDirectOrderDetail(directorderdetailw);
			}

		}

		// Actualiza el valor de las unidades a cero para aquellos productos que no se especifiquen en una nueva orden
		directorderdetailw = new DirectOrderDetailW();
		DirectOrderDetailId doDetailPK = new DirectOrderDetailId();
		for (int i = 0; i < dodMap.size(); i++) {
			DirectOrderDetailW dodetaildata = dodArr[i];

			// Identifica aquellos productos que no se especifican en la nueva orden,
			// ya que no se setearon las unidades totales al nuevo valor.
			if (dodetaildata.getUnits() == 0) {
				doDetailPK.setItemid(dodetaildata.getItemid());
				doDetailPK.setOrderid(dodetaildata.getOrderid());

				// se obtiene el detalle asociado a la orden y al producto
				directorderdetailw = directorderdetailserver.getById(doDetailPK);

				// el valor de las unidades totales se debe setear a cero para
				// aquellos productos que no vengan en la nueva orden
				directorderdetailw.setUnits(0D);
				directorderdetailw.setTotalneed(0D);

				// se actualiza el detalle con las unidades totales del producto a cero
				directorderdetailw = directorderdetailserver.updateDirectOrderDetail(directorderdetailw);

			}
		}

		// SE ACTUALIZAN LAS FECHAS
		// FECHA DE VIGENCIA
		Date now = new Date();

		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		Date f_valid = null;

		try {
			String f_validSTR = orderParser.getA6().trim();
			f_valid = sdf.parse(f_validSTR);

			cal.setTime(f_valid);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_valid = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de vigencia de la orden");
		}

		if (f_valid.before(directOrder.getValid())) {
			// throw new LoadDataException("La fecha de vigencia debe ser posterior a la actual para actualizarla");
		} else {
			directOrder.setValid(f_valid);
		}

		// FECHA DE VENCIMIENTO
		Date f_expiration = null;

		try {
			String f_expirationSTR = orderParser.getA7().trim();
			f_expiration = sdf.parse(f_expirationSTR);
			cal.setTime(f_expiration);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
			f_expiration = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de vencimiento de la orden");
		}

		if (f_expiration.before(directOrder.getExpiration()) && f_expiration.before(now)) {
			// throw new LoadDataException("La fecha de expiración debe ser posterior a la actual y a hoy para
			// actualizarla");
		} else {
			directOrder.setExpiration(f_expiration);
		}

		// Set totalunits y totalneed
		directOrder.setNeedunits(octotalunits);
		directOrder.setTotalneed(octotalneed);

		// Cambiar estado a 'Modificada Paris'
		directOrder.setCurrentstatetypeid(dostParisModified.getId());
		directOrder.setCurrentstatetypedate(now);
		directOrder = directorderserver.updateDirectOrder(directOrder);

		// Historial
		DirectOrderStateW dorderstatew = new DirectOrderStateW();
		dorderstatew.setWhen(now);
		dorderstatew.setOrderid(directOrder.getId());
		dorderstatew.setOrderstatetypeid(dostParisModified.getId());
		dorderstatew = directorderstateserver.addDirectOrderState(dorderstatew);

		return directOrder;
	}

	private DirectOrderW doAddDirectOrder(QORDEN orderParser, VendorW vendorMsg) throws LoadDataException, ServiceException {
		DirectOrderW directorder = new DirectOrderW();

		Date now = new Date();
		DirectOrderStateTypeW libStp = directorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");

		String requestnumber;
		Long orderNumber = new Long(orderParser.getA1());

		directorder.setNumber(orderNumber);

		// NUMERO DE SOLICITUD
		try {
			requestnumber = orderParser.getA2().getB0();
		} catch (Exception e) {
			throw new LoadDataException("No se especifica N°mero de solicitud, Orden " + orderNumber);
		}
		directorder.setRequestnumber(requestnumber);

		// DistributionOrderNumber
		String distributionOrderNumber;
		try {
			distributionOrderNumber = orderParser.getA2().getB1();

			if (distributionOrderNumber.startsWith("-"))
				throw new LoadDataException("El valor de numero de distribución (B1) debe ser num�rico, entero y mayor a cero, Orden " + orderNumber);

		} catch (Exception e) {
			throw new LoadDataException("El formato del numero del campo B1 debe ser num�rico, Orden" + orderNumber);
		}
		directorder.setDistributionordernumber(distributionOrderNumber);

		// AGREGA CLIENTE
		ClientW client = doAddClient(orderParser, orderNumber);
		directorder.setClientid(client.getId());

		// AGREGA DATOS DE CLIENTE A LA ORDEN
		directorder.setClientphone(client.getPhone());
		directorder.setClientaddress(client.getAddress());
		directorder.setClientroadnumber(client.getRoadnumber());
		directorder.setClientdeptnumber(client.getDeptnumber());
		directorder.setClienthousenumber(client.getHousenumber());
		directorder.setClientregion(client.getRegion());
		directorder.setClientcommune(client.getCommune());
		directorder.setClientcity(client.getCity());

		// AGREGO DEPARTAMENTO
		DepartmentW department = doAddDepartment(orderParser, orderNumber);
		directorder.setDepartmentid(department.getId());

		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		// FECHA DE ORIGEN
		Date f_original = null;

		try {
			String f_originalSTR = orderParser.getA2().getB4().trim();
			f_original = sdf.parse(f_originalSTR);

			cal.setTime(f_original);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_original = cal.getTime();

			directorder.setOriginaldeliverydate(f_original);
			directorder.setCurrentdeliverydate(f_original);
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de origen de la orden");
		}

		// AGREGO RESPONSABLE
		ResponsableW responsable = doAddResponsable(orderParser, orderNumber);
		directorder.setResponsableid(responsable.getId());

		// AGREGO PROVEEDOR
		directorder.setVendorid(vendorMsg.getId());

		// FECHA DE EMISión
		Date f_emission = null;

		try {
			String f_emissionSTR = orderParser.getA5().trim();
			f_emission = sdf.parse(f_emissionSTR);

			cal.setTime(f_emission);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_emission = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
		}

		if (f_emission.after(now)) {
			throw new LoadDataException("La fecha de emisión es futura");
		}

		directorder.setEmitted(f_emission);

		// FECHA DE VIGENCIA
		Date f_valid = null;

		try {
			String f_validSTR = orderParser.getA6().trim();
			f_valid = sdf.parse(f_validSTR);

			cal.setTime(f_valid);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_valid = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
		}

		if (f_valid.before(f_emission)) {
			throw new LoadDataException("La fecha de entrega es anterior a la de emisión");
		}

		directorder.setValid(f_valid);

		// FECHA DE VENCIMIENTO
		Date f_expiration = null;

		try {
			String f_expirationSTR = orderParser.getA7().trim();
			f_expiration = sdf.parse(f_expirationSTR);
			cal.setTime(f_expiration);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
			f_expiration = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de vencimiento de la orden");
		}

		if (f_expiration.before(now)) {
			throw new LoadDataException("La fecha de vencimiento es anterior al d�a en que se est� cargando la OC.");
		}

		if (f_expiration.before(f_emission)) {
			throw new LoadDataException("La fecha de vencimiento es anterior a la de emisión.");
		}

		directorder.setExpiration(f_expiration);

		// OBSERVACIONES
		String comment = "";
		try {
			comment = orderParser.getA8().trim();
		} catch (Exception e) {
			comment = "";
		}

		directorder.setComment(comment);

		// ACTIVA
		directorder.setActive(true);
		
		// RECIBIDO COURIER
		directorder.setCourierreceived(false);

		// Busca Local de Venta
		LocationW salestorelocation = doAddSaleStoreLocation(orderParser, orderNumber);
		
		if(salestorelocation != null)
			directorder.setSalestoreid(salestorelocation.getId());
		
		// AGREGO MAESTRO DE PRODUCTOS
		Map<String, ItemWithAtcW> itemMap = doAddItems(orderParser, orderNumber, vendorMsg);

		// ESTADO
		directorder.setCurrentstatetypeid(libStp.getId());
		directorder.setCurrentstatetypedate(now);
		directorder.setCurrentstatetypecomment("Creacion de Orden");
		directorder.setCurrentstatetypewho("Sistema");

		// LAS OC DEBEN QUEDAR EN ESTADO SOA POR NOTIFICAR
		SOAStateTypeW porNotSt = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
		directorder.setCurrentsoastatetypeid(porNotSt.getId());
		directorder.setCurrentsoastatetypedate(now);

		// SE AGREGA ORDEN
		directorder = directorderserver.addDirectOrder(directorder);

		// SE AGREGA ESTADO
		DirectOrderStateW dorderstatew = new DirectOrderStateW();
		dorderstatew.setWhen(now);
		dorderstatew.setOrderid(directorder.getId());
		dorderstatew.setOrderstatetypeid(libStp.getId());
		dorderstatew.setComment("Creacion de Orden");
		dorderstatew.setWho("Sistema");

		dorderstatew = directorderstateserver.addDirectOrderState(dorderstatew);

		// AGREGA DETALLE
		doAddDirectOrderDetail(orderParser, directorder, vendorMsg, itemMap);

		// NUEVO ESTADO SOA
		DirectOrderSOAStateW soastate = new DirectOrderSOAStateW();
		soastate.setOrderid(directorder.getId());
		soastate.setSoastatetypeid(porNotSt.getId());
		soastate.setWhen(now);
		soastate = directordersoastateserver.addSOAState(soastate);

		logger.info("Orden de compra directa N° " + orderNumber + " cargada");

		return directorder;
	}

	private DirectOrderW doProcessDirectOrder(QORDEN orderParser, VendorW vendorMsg) throws LoadDataException, ServiceException {
		DirectOrderW directorder = null;
		Long orderNumber = new Long(orderParser.getA1());

		OrderTypeW vevcdType = ordertypeserver.getByPropertyAsSingleResult("code", "V");

		// VALIDA QUE LA ORDEN DIRECTA NO EXISTA PREVIAMENTE
		List<DirectOrderW> directorders = directorderserver.getByProperty("number", orderNumber);

		if (directorders == null || directorders.size() <= 0) {
			// VALIDA QUE LA ORDEN NO EXISTA PREVIAMENTE
			List<OrderW> orders = orderserver.getByProperty("number", orderNumber);

			if (orders == null || orders.size() <= 0) {
				directorder = doAddDirectOrder(orderParser, vendorMsg);
			} else {
				// VALIDA SI ES VEVCD
				if (orders.get(0).getOrdertypeid().equals(vevcdType.getId())) {
					OrderStateTypeW ost = orderstatetypeserver.getByPropertyAsSingleResult("code", "ELIMINADA");

					// La orden VeV CD debe estar en estado "Eliminada", sino la nueva orden se rechaza
					if (!orders.get(0).getCurrentstatetypeid().equals(ost.getId())) {
						throw new LoadDataException("La orden con el N°mero " + orderNumber + " ya existe como VeV CD");
					}
				}
				else {
					throw new LoadDataException("La orden No " + orderNumber + " existe con otro tipo (XDB o Stock)");
				}
				
				directorder = doAddDirectOrder(orderParser, vendorMsg);
			}
		}
		else {
			directorder = directorders.get(0);
			directorder = doUpdateDirectOrder(orderParser, directorder, vendorMsg);
		}

		directordermanager.doCalculateTotalOfDirectOrder(directorder.getId());
		return directorder;
	}

	public Long processMessage(QORDEN orderParser) throws LoadDataException, ServiceException {

		boolean isPred = false;
		boolean isVeVCD = false;
		OrderW order = null;

		// Validación del Mensaje
		doValidateOrderMessage(orderParser);

		Calendar cal = null;
		Locale locale = null;
		locale = new Locale("es", "CL");
		cal = Calendar.getInstance(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		/** ************************************************************************************ */
		/** ***************************** CREACION DE NUEVA ORDEN ****************************** */
		/** ************************************************************************************ */
		OrderStateTypeW libStp = orderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
		OrderStateTypeW modStp = orderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");

		Date now = new Date();
		Long orderNumber = new Long(orderParser.getA1());

		// AGREGO TIPO DE ORDEN
		OrderTypeW ordertypew = getOrderType(orderParser, orderNumber);

		if (ordertypew == null)
			throw new LoadDataException("No existe el código de tipo de orden especificado");

		// AGREGO/ACTUALIZO PROVEEDOR DEL SISTEMA
		VendorW vendor = doAddVendor(orderParser);
				
		if (ordertypew.getCode().equalsIgnoreCase("P")) {
			// VENTA EN VERDE PROVEEDOR
			DirectOrderW directorder = doProcessDirectOrder(orderParser, vendor);
			try{
				notificationmanager.doAddNotificationData(vendor.getRut(), directorder.getNumber().toString(), "CARGA_OC_VEV_PD");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			// JPE 20180808
			// Validar que el proveedor asociado a la OC no tenga valor en campo 'Primera VeV PD'
			if (vendor.getFirstvevpddate() == null) {
				// Enviar correo para notificar a usuarios de Paris de la carga de la primera OC VeV PD del proveedor
				String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String subject = "B2B Paris: Notificación primera carga OC VeV PD " + vendor.getTradename();
				String from = RegionalLogisticConstants.getInstance().getMailSender();
				String[] to = B2BSystemPropertiesUtil.getProperty("VEV_NOTIFICATION_MAIL").split(",");
				String cc[] = null;
				String bcc[] = null;

				if (to != null && !to[0].equals("")) {
					DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat sdfTime = new SimpleDateFormat("HH:mm");
					String message =
						"<p>Estimado(a) usuario(a):</p>" + //
						"<p>Comunicamos a usted que con fecha " + sdfDate.format(now) + " a las " + sdfTime.format(now) + " " + //
						"se ha cargado por primera vez una orden de compra tipo VeV PD para el proveedor " + vendor.getTradename() + ".<br>" + //
						"El detalle es el siguiente:</p>" +
						"<table cellspacing='0' cellpadding='5' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" + //
							"<tbody>" + //
								"<tr>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Proveedor</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>código Paris Proveedor</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de orden Comercial</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de Orden</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha envío</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha compromiso cliente</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Sub tipo VeV</strong></center></td>" + //
								"</tr>" + //
								"<tr>" + //
									"<td style='border:1px solid black;'>" + vendor.getTradename() + "</td>" + //
									"<td style='border:1px solid black;'>" + vendor.getCode() + "</td>" + //
									"<td style='border:1px solid black;'>" + directorder.getNumber() + "</td>" + //
									"<td style='border:1px solid black;'>" + directorder.getRequestnumber() + "</td>" + //
									"<td style='border:1px solid black;'>" + sdfDate.format(directorder.getEmitted()) + "</td>" + //
									"<td style='border:1px solid black;'>" + sdfDate.format(directorder.getOriginaldeliverydate()) + "</td>" + //
									"<td style='border:1px solid black;'>VeV PD</td>" + //
								"</tr>" + //
							"</tbody>" + //
						"</table><br><br>" + //
						"<p><strong><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></strong></p>" + //
						"<p>Atte.<br>" + //
						"B2B Paris</p>"; //
			
					try {
						schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, "NOTIF_VEV",  "CD-" + vendor.getCode());
												
						// Actualizar al proveedor indicando la fecha y hora actual en el campo 'Primera VeV CD'
						vendor.setFirstvevpddate(now);
						vendorserver.updateVendor(vendor);
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("No fue posible enviar email para notificar a usuarios de Paris de la carga de la primera OC VeV PD del proveedor " + vendor.getTradename());
					}
				}				
			}
			
			return directorder.getNumber();
		}

		// VALIDA QUE LA ORDEN NO EXISTA PREVIAMENTE
		List<OrderW> orders = orderserver.getByProperty("number", orderNumber);
		String vendorcodeimp = null;
		VendorW notUsedVendor = null;
		if (ordertypew.getCode().equalsIgnoreCase("V")) {
			isVeVCD = true;
		}else if(orderParser.getA4().getP4().equalsIgnoreCase("I")){//Si no es vev y el proveedor no es nacional, se usar� el proveedor genérico "Cencosud Importado" para hacer las relaciones
			notUsedVendor = vendor;
			vendor = vendorserver.getByPropertyAsSingleResult("code", "IMP");
			vendorcodeimp = notUsedVendor.getCode();
		}

		if (orders == null || orders.size() <= 0) {
			order = new OrderW();

		} else {
			// S�lo se acepta una orden preexistente si es de tipo "VeV CD"
			OrderTypeW ordertype = ordertypeserver.getById(orders.get(0).getOrdertypeid());
			if (ordertype.getCode().equals("V")) {
				// ACTUALIZO ORDEN
				order = doUpdateVeVCDOrder(orderParser, orders.get(0), vendor);
				return order.getNumber();
				
			} else {
				// VERIFICA QUE LOS PROVEEDORES SON IGUALES
				if (!vendor.getId().equals(orders.get(0).getVendorid())) {
					throw new LoadDataException("El proveedor indicado en el mensaje no corresponde al proveedor actual de la Orden N° " + orders.get(0).getNumber());
				}

				// El sistema valida que la OC no tenga ning�n despacho asociado
				// El sistema borra del sistema la OC anterior
				OrderDeliveryW[] odlArr = orderdeliveryserver.getByQueryAsArray("select odl from OrderDelivery as odl where odl.order.id = " + orders.get(0).getId());

				if (odlArr == null || odlArr.length == 0) {
					predeliverydetailserver.deleteByProperty("id.orderid", orders.get(0).getId());
					orderdetailserver.deleteByProperty("id.orderid", orders.get(0).getId());
					orderstateserver.deleteByProperty("order.id", orders.get(0).getId());
					orderdiscountserver.deleteByProperty("order.id", orders.get(0).getId());
					soastateserver.deleteByProperty("order.id", orders.get(0).getId());
					orderserver.deleteElement(orders.get(0).getId());
					orderserver.flush();
					order = new OrderW();
				} else {
					// SE VALIDAN LAS FECHAS PARA ACTUALIZARLAS
					boolean mod = false;
					order = orders.get(0);

					// FECHA DE VIGENCIA
					Date f_valid = null;

					try {
						String f_validSTR = orderParser.getA6().trim();
						f_valid = sdf.parse(f_validSTR);

						cal.setTime(f_valid);
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						f_valid = cal.getTime();

					} catch (Exception e) {
						throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
					}

					order.setValid(f_valid);
					mod = true;

					// FECHA DE VENCIMIENTO
					Date f_expiration = null;

					try {
						String f_expirationSTR = orderParser.getA7().trim();
						f_expiration = sdf.parse(f_expirationSTR);
						cal.setTime(f_expiration);
						cal.set(Calendar.HOUR_OF_DAY, 23);
						cal.set(Calendar.MINUTE, 59);
						cal.set(Calendar.SECOND, 59);
						cal.set(Calendar.MILLISECOND, 0);
						f_expiration = cal.getTime();

					} catch (Exception e) {
						throw new LoadDataException("No se puede obtener la fecha de vencimiento de la orden");
					}

					order.setExpiration(f_expiration);
					mod = true;

					if (mod) {
						order.setCurrentstatetypeid(modStp.getId());
						order.setCurrentstatetypedate(now);

						order = orderserver.updateOrder(order);

						// NUEVO ESTADO
						OrderStateW state = new OrderStateW();
						state.setOrderid(order.getId());
						state.setWhen(now);
						state.setOrderstatetypeid(modStp.getId());

						state = orderstateserver.addOrderState(state);
					}

					return order.getNumber();
				}
			}
		}

		order.setOrdertypeid(ordertypew.getId());
		order.setNumber(orderNumber);
		order.setVendorcodeimp(vendorcodeimp);
		
		// SE CARGAN DATOS DE VENTA EN VERDE
		if (isVeVCD) {
			// Si la orden es nueva y es de tipo "VeV CD", se valida que no haya otra orden VeV Prov. activa
			// Verifica si una orden de tipo "VeV Prov" con el numero de orden "norden" existe
			DirectOrderW[] directorderArr = directorderserver.getByPropertyAsArray("number", orderNumber);

			// si la orden existe como "VeV Prov", se debe verificar que est� en estado "Cancelada Paris"
			if (directorderArr != null && directorderArr.length > 0) {
				DirectOrderStateTypeW dostatetype = directorderstatetypeserver.getById(directorderArr[0].getCurrentstatetypeid());
				if (!dostatetype.getCode().equals("CANCELADA_PARIS")) {
					throw new LoadDataException("La orden No " + orderNumber + " ya existe como VeV Prov.");
				}
			}

			String requestnumber;

			// NUMERO DE SOLICITUD
			try {
				requestnumber = orderParser.getA2().getB0();
			} catch (Exception e) {
				throw new LoadDataException("No se especifica N°mero de solicitud, Orden " + orderNumber);
			}
			order.setRequestnumber(requestnumber);

			// DistributionOrderNumber
			String distributionOrderNumber;
			try {
				distributionOrderNumber = orderParser.getA2().getB1();

				if (distributionOrderNumber.startsWith("-"))
					throw new LoadDataException("El valor de numero de distribución (B1) debe ser num�rico, entero y mayor a cero, Orden " + orderNumber);

			} catch (Exception e) {
				throw new LoadDataException("El formato del numero del campo B1 debe ser num�rico, Orden" + orderNumber);
			}
			order.setDistributionordernumber(distributionOrderNumber);

			// AGREGA CLIENTE
			ClientW client = doAddClient(orderParser, orderNumber);
			order.setClientid(client.getId());

			// FECHA DE COMPROMISO
			Date f_original = null;

			try {
				String f_originalSTR = orderParser.getA2().getB4().trim();
				f_original = sdf.parse(f_originalSTR);

				cal.setTime(f_original);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				f_original = cal.getTime();

				order.setOriginaldeliverydate(f_original);
			} catch (Exception e) {
				throw new LoadDataException("No se puede obtener la fecha de origen de la orden");
			}
						
			// Busca Local de Venta
			LocationW salestorelocation = doAddSaleStoreLocation(orderParser, orderNumber);
			
			if(salestorelocation != null) {
				order.setSalestoreid(salestorelocation.getId());
			}
		}
		
		// AGREGO PROVEEDOR
		order.setVendorid(vendor.getId());

		// AGREGO RESPONSABLE
		ResponsableW responsable = doAddResponsable(orderParser, orderNumber);
		order.setResponsableid(responsable.getId());

		// FECHA DE EMISión
		Date f_emission = null;

		try {
			String f_emissionSTR = orderParser.getA5().trim();
			f_emission = sdf.parse(f_emissionSTR);

			cal.setTime(f_emission);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_emission = cal.getTime();

			order.setEmitted(f_emission);
		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
		}

		// FECHA DE VIGENCIA
		Date f_valid = null;

		try {
			String f_validSTR = orderParser.getA6().trim();
			f_valid = sdf.parse(f_validSTR);

			cal.setTime(f_valid);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			f_valid = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de emision de la orden");
		}

		if (f_valid.before(f_emission)) {
			throw new LoadDataException("La fecha de entrega es anterior a la de emisión");
		}

		order.setValid(f_valid);

		// FECHA DE VENCIMIENTO
		Date f_expiration = null;

		try {
			String f_expirationSTR = orderParser.getA7().trim();
			f_expiration = sdf.parse(f_expirationSTR);
			cal.setTime(f_expiration);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);
			f_expiration = cal.getTime();

		} catch (Exception e) {
			throw new LoadDataException("No se puede obtener la fecha de vencimiento de la orden");
		}

		order.setExpiration(f_expiration);

		// OBSERVACIONES
		String comment = "";
		try {
			comment = orderParser.getA8().trim();
		} catch (Exception e) {
			comment = "";
		}

		logger.info("El comentario de la orden: "+order.getNumber()+". Es: "+comment);
		order.setComment(comment + (notUsedVendor != null ? "[Proveedor "+notUsedVendor.getName()+" - "+notUsedVendor.getCode()+"]" : ""));

		// AGREGO DEPARTAMENTO
		DepartmentW department = doAddDepartment(orderParser, orderNumber);
		order.setDepartmentid(department.getId());

		// AGREGO MAESTRO DE LOCALES
		Map<String, LocationW> locationMap = doAddLocations(orderParser, orderNumber);

		// AGREGO MAESTRO DE PRODUCTOS
		Map<String, ItemWithAtcW> itemMap = doAddItems(orderParser, orderNumber, vendor);

		// CODIGO DE LOCAL DE ENTREGA
		String deliverylocationcode = orderParser.getA13().trim();

		if (!locationMap.containsKey(deliverylocationcode)) {
			throw new LoadDataException("El código del local de entrega " + deliverylocationcode + " no est� especificado en el bloque de maestros");
		}

		order.setDeliverylocationid(locationMap.get(deliverylocationcode).getId());

		// SE CARGA EN ESTADO LIBERADA
		order.setCurrentstatetypedate(now);
		order.setCurrentstatetypeid(libStp.getId());

		// VENTA EN VERDE
		order.setVevcd(isVeVCD);

		// ACTIVA
		order.setActive(true);

		// EBO 01-07-2015
		// LAS OC DEBEN QUEDAR EN ESTADO SOA POR NOTIFICAR
		SOAStateTypeW porNotSt = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
		order.setCurrentsoastatetypeid(porNotSt.getId());
		order.setCurrentsoastatetypedate(now);

		// AGREGA LA ORDEN
		order = orderserver.addOrder(order);

		// AGREGA EL ESTADO
		OrderStateW state = new OrderStateW();
		state.setOrderstatetypeid(libStp.getId());
		state.setOrderid(order.getId());
		state.setWhen(now);

		state = orderstateserver.addOrderState(state);

		// EBO 01-07-2015
		// NUEVO ESTADO SOA
		SOAStateW soastate = new SOAStateW();
		soastate.setOrderid(order.getId());
		soastate.setSoastatetypeid(porNotSt.getId());
		soastate.setWhen(now);
		soastate = soastateserver.addSOAState(soastate);

		// DESCUENTOS GENERALES
		if (orderParser.getA14() != null && orderParser.getA14().getDCS() != null && orderParser.getA14().getDCS().size() > 0)
			doAddOrderDiscounts(orderParser, order);

		// AGREGA DETALLE DE ORDEN
		doAddOrderDetail(orderParser, order, vendor, itemMap, locationMap, isPred);

		logger.info("Orden de compra N° " + orderNumber + " cargada");

		// CALCULA LAS UNIDADES
		ordermanager.doCalculateTotalOfOrder(order.getId());

		try {
			if(isVeVCD) {
				notificationmanager.doAddNotificationData(vendor.getRut(), order.getNumber().toString(), "CARGA_OC_VEV_CD");
			}
			else {
				notificationmanager.doAddNotificationData(vendor.getRut(), order.getNumber().toString(), "CARGA_OC");
			}
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (isVeVCD) {
			// JPE 20180808
			// Validar que el proveedor asociado a la OC no tenga valor en campo 'Primera VeV CD'
			if (vendor.getFirstvevcddate() == null) {
				// Enviar correo para notificar a usuarios de Paris de la carga de la primera OC VeV CD del proveedor
				String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String subject = "B2B Paris: Notificación primera carga OC VeV CD " + vendor.getTradename();
				String from = RegionalLogisticConstants.getInstance().getMailSender();
				String[] to = B2BSystemPropertiesUtil.getProperty("VEV_NOTIFICATION_MAIL").split(",");
				String cc[] = null;
				String bcc[] = null;

				if (to != null && !to[0].equals("")) {
					DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
					DateFormat sdfTime = new SimpleDateFormat("HH:mm");
					String message =
						"<p>Estimado(a) usuario(a):</p>" + //
						"<p>Comunicamos a usted que con fecha " + sdfDate.format(now) + " a las " + sdfTime.format(now) + " " + //
						"se ha cargado por primera vez una orden de compra tipo VeV CD para el proveedor " + vendor.getTradename() + ".<br>" + //
						"El detalle es el siguiente:</p>" +
						"<table cellspacing='0' cellpadding='5' style='border:1px solid black; border-collapse:collapse; font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" + //
							"<tbody>" + //
								"<tr>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Proveedor</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>código Paris Proveedor</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de orden Comercial</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N°mero de Orden</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha envío</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Fecha compromiso cliente</strong></center></td>" + //
									"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Sub tipo VeV</strong></center></td>" + //
								"</tr>" + //
								"<tr>" + //
									"<td style='border:1px solid black;'>" + vendor.getTradename() + "</td>" + //
									"<td style='border:1px solid black;'>" + vendor.getCode() + "</td>" + //
									"<td style='border:1px solid black;'>" + order.getNumber() + "</td>" + //
									"<td style='border:1px solid black;'>" + order.getRequestnumber() + "</td>" + //
									"<td style='border:1px solid black;'>" + sdfDate.format(order.getEmitted()) + "</td>" + //
									"<td style='border:1px solid black;'>" + sdfDate.format(order.getOriginaldeliverydate()) + "</td>" + //
									"<td style='border:1px solid black;'>VeV CD</td>" + //
								"</tr>" + //
							"</tbody>" + //
						"</table><br><br>" + //
						"<p><strong><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></strong></p>" + //
						"<p>Atte.<br>" + //
						"B2B Paris</p>"; //
			
					try {
						schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, "NOTIF_VEV", "PD-" + vendor.getCode());
						
						// Actualizar al proveedor indicando la fecha y hora actual en el campo 'Primera VeV CD'
						vendor.setFirstvevcddate(now);
						vendorserver.updateVendor(vendor);
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("No fue posible enviar email para notificar a usuarios de Paris de la carga de la primera OC VeV CD del proveedor " + vendor.getTradename());
					}
				}
			}
		}
		
		return orderNumber;
	}

	private void sendCasePackInnerPackMail(String sku, Integer casepack, Integer innerpack) {
//		try {
//			String[] mailreciever = new String[2];
//			String subject = "B2B Paris Chile: Productos con innerpack o casepack en cero";
//			Mailer mailer = Mailer.getInstance();
//			mailreciever[0] = "eborne@bbr.cl";
//			mailreciever[1] = "jmansilla@bbr.cl";
//			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
//			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
//
//			String msgtext = "Estimado usuario:\n" + "El B2B recibio el producto " + sku + " que contiene los siguientes valores:\n" + "innerpack :" + innerpack + "\n" + "casepack :" + casepack + "\n" + "Atte.\n" + "Paris.";
//			mailer.sendMailBySession(mailreciever, mailreciever, null, mailsender, subject, msgtext, false, null, mailSession);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}

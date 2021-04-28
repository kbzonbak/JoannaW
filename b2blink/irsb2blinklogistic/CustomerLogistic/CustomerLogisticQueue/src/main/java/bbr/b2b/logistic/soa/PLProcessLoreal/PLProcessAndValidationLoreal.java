package bbr.b2b.logistic.soa.PLProcessLoreal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario.DocTributario;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario.DocTributario.DetallesDocTributario.DetalleDocTributario;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Entregas.Entrega;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.logistic.customer.data.dto.HeaderDTO;
import bbr.b2b.logistic.customer.data.dto.PLFileDTO;
import bbr.b2b.logistic.utils.BBRStringUtils;
import bbr.b2b.logistic.xml.PLFalabella.EPIR;
import bbr.b2b.logistic.xml.PLFalabella.EPIR.PIR;
import bbr.b2b.logistic.xml.PLFalabella.EPIR.PIR.PRODUCTO;
import bbr.b2b.logistic.xml.PLFalabella.EPIR.PIR.PRODUCTO.PRODUCTOROW;
import bbr.b2b.logistic.xml.PLFalabella.ObjectFactory;
import bbr.b2b.logistic.xml.PLRipleyBultoFactura.DOCBULTOSDEMODI;
import bbr.b2b.logistic.xml.PLRipleyBultoFactura.DOCBULTOSDEMODI.FACTURAGUIA;
import bbr.b2b.logistic.xml.PLRipleyBultoFactura.DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI;
import bbr.b2b.logistic.xml.PLRipleyBultoFactura.DOCBULTOSDEMODI.FACTURAGUIA.BULTODEMODI.DETALLEBULTODEMODI;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.DocumentProperties;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.ExcelWorkbook;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.PageBreaks;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.PageBreaks.RowBreaks;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.PageBreaks.RowBreaks.RowBreak;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.Alignment;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.Borders;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.Borders.Border;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.Font;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.Interior;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Styles.Style.NumberFormat;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet.Table;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet.Table.Column;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet.Table.Row;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet.Table.Row.Cell;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.Workbook.Worksheet.Table.Row.Cell.Data;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.PageSetup;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.PageSetup.Footer;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.PageSetup.Header;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.PageSetup.Layout;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.PageSetup.PageMargins;
import bbr.b2b.logistic.xml.asnunimarc1.xsd.WorksheetOptions.Print;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

public class PLProcessAndValidationLoreal implements PLProcessAndValidationLorealLocal {

	private static Logger logger = Logger.getLogger("SOALog");
	private static JAXBContext jc_falabella = null;
	private static JAXBContext jc_Ripley = null;
	private static JAXBContext jc_Unimarc= null;

	private static JAXBContext getJC_Falabella() throws JAXBException {
		if (jc_falabella == null)
			jc_falabella = JAXBContext.newInstance("bbr.b2b.logistic.xml.PLFalabella");
		return jc_falabella;
	}

	private static JAXBContext getJC_Ripley() throws JAXBException {
		if (jc_Ripley == null)
			jc_Ripley = JAXBContext.newInstance("bbr.b2b.logistic.xml.PLRipleyBultoFactura");
		return jc_Ripley;
	}
	
	private static JAXBContext getJC_Unimarc() throws JAXBException {
		if (jc_Unimarc == null)
			jc_Unimarc = JAXBContext.newInstance("bbr.b2b.logistic.xml.asnunimarc1.xsd");
		return jc_Unimarc;
	}

	public List<BaseResultDTO> doValidateUnimarc(PLProveedor message) {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getEntregas().getEntrega().get(0).getNumero() == null
				|| message.getEntregas().getEntrega().get(0).getNumero().isEmpty()) {
			erroresString.add("Debe indicar Nro de entrega");
		}

		int linea = 1;
		for (Bulto item : message.getBultos().getBulto()) {
			for (Detalle detail : item.getDetalles().getDetalle()) {

				if (detail.getNoc() == null || detail.getNoc().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de orden");
				}
				if (detail.getCodProdProveedor() == null || detail.getCodProdProveedor().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar código de proveedor");
				}
				if (detail.getDescripcionProd() == null || detail.getDescripcionProd().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la descripcion prod ");
				}
				if (detail.getNoDocTributario() == null || detail.getNoDocTributario().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de documento tributario en detalle");
				}
				if (detail.getEan13() == null || detail.getEan13().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar ean13");
				}
				if (detail.getDun14() == null || detail.getDun14().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar dun14");
				}
				if (detail.getCantidadUnidades() == null || detail.getCantidadUnidades().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar cantidad de unidades");
				}
				if (detail.getCantidadCajas() == null || detail.getCantidadCajas().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar cantidad de cajas");
				}
				linea++;
			}
		}

		for (String error : erroresString) {
			BaseResultDTO e = new BaseResultDTO();
			e.setStatuscode("-1");
			e.setStatusmessage(error);
			errores.add(e);
		}
		return errores;
	}

	public List<BaseResultDTO> doValidateFalabella(PLProveedor message) {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita() == null || message.getNroCita().isEmpty()) {
			erroresString.add("Debe indicar Nro de cita");
		}
		// if (message.getTotalBultos() == null ||
		// message.getTotalBultos().isEmpty()) {
		// erroresString.add("Debe indicar el nro total de bultos");
		// }
		if (message.getTotalTotes() == null || message.getTotalTotes().isEmpty()) {
			erroresString.add("Debe indicar el total totes");
		}
		if (message.getTotalColgados() == null || message.getTotalColgados().isEmpty()) {
			erroresString.add("Debe indicar el total colgados");
		}

		int linea = 1;
		int lineabulto = 1;
		int lineaentregas = 1;
		for (Bulto item : message.getBultos().getBulto()) {

			if (item.getNroBulto() == null || item.getNroBulto().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar el nro de bulto");
			}
			if (item.getCodLocalEntrega() == null || item.getCodLocalEntrega().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar el almacen");
			}
			if (item.getCodLocalDestino() == null) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar el nro de local");
			}
			if (item.getTipoEmbalaje() == null || item.getTipoEmbalaje().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar el tipo de embalaje");
			}
			if (item.getFechaDespacho() == null || item.getFechaDespacho().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar la fecha de despacho");
			}
			if (item.getHoraDespacho() == null || item.getHoraDespacho().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar la hora de despacho");
			}

			lineabulto++;

			for (Detalle detail : item.getDetalles().getDetalle()) {

				if (detail.getNoc() == null || detail.getNoc().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de orden");
				}
				if (detail.getNoDocTributario() == null || detail.getNoDocTributario().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de documento tributario en detalle");
				}
				if (detail.getEan13() == null || detail.getEan13().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el UPC (Ean13)");
				}
				if (detail.getDescripcionProd() == null || detail.getDescripcionProd().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la descripción del producto");
				}
				if (detail.getCantidadUnidades() == null || detail.getCantidadUnidades().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la cantidad de unidades");
				}
				linea++;
			}
		}

		if (message.getEntregas() == null) {
			erroresString.add("Entregas: El mensaje no contiene el tag entregas");
		} else {

			for (Entrega entrega : message.getEntregas().getEntrega()) {
				if (entrega.getTotalBultos() == null || entrega.getTotalBultos().isEmpty()) {
					erroresString.add("Entregas: " + lineaentregas + " Debe indicar la cantidad total de bultos");
				}
				lineaentregas++;
			}
		}

		for (String error : erroresString) {
			BaseResultDTO e = new BaseResultDTO();
			e.setStatuscode("-1");
			e.setStatusmessage(error);
			errores.add(e);
		}
		return errores;

	}

	public List<BaseResultDTO> doValidateTottus(PLProveedor message) {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita() == null || message.getNroCita().isEmpty()) {
			erroresString.add("Debe indicar Nro de cita");
		}
		if (message.getCodLocalEntrega() == null || message.getCodLocalEntrega().isEmpty()) {
			erroresString.add("Debe indicar codigo de almacen");
		}

		int linea = 1;
		int lineabulto = 1;
		for (Bulto item : message.getBultos().getBulto()) {

			if (item.getFechaDespacho() == null || item.getFechaDespacho().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar la fecha de despacho");
			}
			if (item.getHoraDespacho() == null || item.getHoraDespacho().isEmpty()) {
				erroresString.add("BULTO " + lineabulto + ": Debe indicar la hora de despacho");
			}

			lineabulto++;

			for (Detalle detail : item.getDetalles().getDetalle()) {

				if (detail.getNoc() == null || detail.getNoc().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de orden");
				}
				if (detail.getNoDocTributario() == null || detail.getNoDocTributario().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de documento tributario en detalle");
				}
				if (detail.getEan13() == null || detail.getEan13().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar ean13");
				}
				if (detail.getCantidadUnidades() == null || detail.getCantidadUnidades().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar cantidad de unidades");
				}
				linea++;
			}
		}

		for (String error : erroresString) {
			BaseResultDTO e = new BaseResultDTO();
			e.setStatuscode("-1");
			e.setStatusmessage(error);
			errores.add(e);
		}
		return errores;

	}

	public List<BaseResultDTO> doValidateRipley(PLProveedor message) {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita().isEmpty()) {
			if (message.getNroCita().isEmpty()) {
				erroresString.add("Debe indicar Nro de cita");
			}
		}

		int linea = 1;
		int lineabulto = 1;
		for (Bulto item : message.getBultos().getBulto()) {

			if (item.getNroBulto() == null || item.getNroBulto().isEmpty()) {
				erroresString.add("BULTO " + linea + ": Debe indicar el nro de bulto");
			}
			if (item.getFechaBulto() == null || item.getFechaBulto().isEmpty()) {
				erroresString.add("BULTO " + linea + ": Debe indicar la fecha del bulto");
			}

			lineabulto++;

			for (Detalle detail : item.getDetalles().getDetalle()) {

				if (detail.getNoc() == null || detail.getNoc().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de orden");
				}
				if (detail.getNoDocTributario() == null || detail.getNoDocTributario().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de documento tributario en detalle");
				}
				if (detail.getCodProdCliente() == null || detail.getCodProdCliente().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el nro producto Ripley");
				}
				if (detail.getCantidadUnidades() == null || detail.getCantidadUnidades().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la cantidad de unidades");
				}
				if (detail.getCodProdProveedor() == null || detail.getCodProdProveedor().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el cod prod proveedor");
				}
				linea++;
			}
		}

		linea = 1;
		int lineadoc = 1;
		// documentos tributarios
		for (DocTributario doc : message.getDocsTributario().getDocTributario()) {

			if (doc.getNumref1() == null || doc.getNumref1().isEmpty()) {
				erroresString.add("DocTributario " + lineadoc + ": Debe indicar tipo de documento");
			}
			if (doc.getFechaDocTributario() == null || doc.getFechaDocTributario().isEmpty()) {
				erroresString.add("DocTributario " + lineadoc + ": Debe indicar la fecha del documento");
			}
			if (doc.getNetoDocTributario() == null || doc.getNetoDocTributario().isEmpty()) {
				erroresString.add("DocTributario " + lineadoc + ": Debe indicar el valor NETO del documento");
			}
			if (doc.getIvaDocTributario() == null || doc.getIvaDocTributario().isEmpty()) {
				erroresString.add("DocTributario " + lineadoc + ": Debe indicar el valor IVA del documento");
			}
			if (doc.getTotalDocTributario() == null || doc.getTotalDocTributario().isEmpty()) {
				erroresString.add("DocTributario " + lineadoc + ": Debe indicar el total del documento");
			}
			lineadoc++;
			// Detalle documentos
			for (DetalleDocTributario docdetail : doc.getDetallesDocTributario().getDetalleDocTributario()) {

				if (docdetail.getCodProdProveedor() == null || docdetail.getCodProdProveedor().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el codigo del producto(proveedor)");
				}
				if (docdetail.getCodProdCliente() == null || docdetail.getCodProdCliente().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el codigo del producto(cliente)");
				}
				if (docdetail.getCantidad() == null || docdetail.getCantidad().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar la cantidad de unidades");
				}
				if (docdetail.getCostoUnitario() == null || docdetail.getCostoUnitario().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el costo unitario");
				}
				if (docdetail.getCostoFinal() == null || docdetail.getCostoFinal().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el costo final");
				}
				if (docdetail.getDescProducto() == null || docdetail.getDescProducto().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el descuento del producto");
				}
				if (docdetail.getPorcDescto() == null || docdetail.getPorcDescto().isEmpty()) {
					erroresString.add("DetalleDocumento " + linea + ": Debe indicar el porcentaje de descuento");
				}
			}
		}

		for (String error : erroresString) {
			BaseResultDTO e = new BaseResultDTO();
			e.setStatuscode("-1");
			e.setStatusmessage(error);
			errores.add(e);
		}
		return errores;

	}

	public List<BaseResultDTO> doValidateWalmart(PLProveedor message) {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita().isEmpty()) {
			if (message.getNroCita() == null || message.getNroCita().isEmpty()) {
				erroresString.add("Debe indicar Nro de cita");
			}
		}

		int linea = 1;
		for (Bulto item : message.getBultos().getBulto()) {
			for (Detalle detail : item.getDetalles().getDetalle()) {

				if (detail.getNoc() == null || detail.getNoc().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar número de orden");
				}
				if (detail.getCodProdCliente() == null || detail.getCodProdCliente().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el cod prod cliente");
				}
				if (detail.getCodProdProveedor() == null || detail.getCodProdProveedor().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el cod prod proveedor");
				}
				if (detail.getEan13() == null || detail.getEan13().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el ean13");
				}
				if (detail.getDun14() == null || detail.getDun14().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el dun14");
				}
				if (detail.getCantidadCajas() == null || detail.getCantidadCajas().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la cantidad de cajas");
				}
				if (detail.getDescripcionProd() == null || detail.getDescripcionProd().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar la descripción del producto");
				}
				if (detail.getEntrega() == null || detail.getEntrega().isEmpty()) {
					erroresString.add("Detalle " + linea + ": Debe indicar el numero de entrega");
				} else {
					// si viene el nro de entrega, se valida que venga el
					// detalle
					for (Entrega entrega : message.getEntregas().getEntrega()) {
						if (entrega.getNumero() == null || entrega.getNumero().isEmpty()) {
							erroresString.add("Entregas " + linea + ": Debe indicar el numero de entrega");
						} else {
							if (detail.getEntrega().equals(entrega.getNumero())) {
								for (bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Entregas.Entrega.Detalles.Detalle entdetail : entrega
										.getDetalles().getDetalle()) {
									if (entdetail.getPeso() == null || entdetail.getPeso().isEmpty()) {
										erroresString.add("Detalle Entrega: " + entrega.getNumero()
												+ " Debe indicar el peso facturado en Detalle Entrega");
									}
									if (entdetail.getSaldo() == null || entdetail.getSaldo().isEmpty()) {
										erroresString.add("Detalle Entrega: " + entrega.getNumero()
												+ " Debe indicar el saldo de la OC en Detalle Entrega");
									}
								}
							}
						}
					}
				}
				linea++;
			}
		}

		for (String error : erroresString) {
			BaseResultDTO e = new BaseResultDTO();
			e.setStatuscode("-1");
			e.setStatusmessage(error);
			errores.add(e);
		}
		return errores;

	}

//	public PLFileDTO doCreateFileUnimarc(PLProveedor message, String folderpath, Long TicketNumber) {
//
//		PLFileDTO result = new PLFileDTO();
//		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String statusCode = "0";
//		String statusMessage = "";
//
//		Map<String, String> map = new HashMap<String, String>();
//		for (Bulto item : message.getBultos().getBulto()) {
//			for (Detalle detail : item.getDetalles().getDetalle()) {
//				if (!map.containsKey(detail.getNoc())) {
//					map.put(detail.getNoc(), detail.getNoDocTributario());
//				}
//			}
//		}
//		
//		Map<String, List<String>> docmap = new HashMap<String, List<String>>();
//		for (Bulto item : message.getBultos().getBulto()) {
//			for (Detalle detail : item.getDetalles().getDetalle()) {
//				if (!docmap.containsKey(detail.getNoc())) {
//					List<String> tmpdoc = new ArrayList<String>();
//					tmpdoc.add(detail.getNoDocTributario());
//					docmap.put(detail.getNoc(), tmpdoc);
//				} else {
//					List<String> temLinea = docmap.get(detail.getNoc());
//					if (!temLinea.contains(detail.getNoDocTributario())) {
//						temLinea.add(detail.getNoDocTributario());
//						docmap.put(detail.getNoc(), temLinea);
//					}
//				}
//
//			}
//		}
//		
//	
//		
//				
//		List<String> files = new ArrayList<String>();
//		DataRow row = null;
//		String finalPath = "";
//		String filename = "";
//		filename = "PL_" + TicketNumber + "_" + sdf.format(new Date())+".xls";
//		//List<DataTable> tablelist = new ArrayList<DataTable>();
//		//int i = 0;
//		DataTable dt0 = new DataTable("PL");
//		boolean header = true;
//		
//		CellStyle headerstyle = getHeaderCellStyle();
//		
//		
//		SortedSet<String> keys = new TreeSet<>(docmap.keySet());
//		for (String key : keys) { 
//			for (String nodoc : docmap.get(key)) {
//				// Calculo cantidad cajas y unidades por codigo sap(cod prod
//				// proveedor)
//				Map<String, Integer> mapUnidades = new HashMap<String, Integer>();
//				Map<String, Integer> mapCajas = new HashMap<String, Integer>();
//				
//				for (Bulto item : message.getBultos().getBulto()) {
//					for (Detalle detail : item.getDetalles().getDetalle()) {
//						if (key.equals(detail.getNoc()) && nodoc.equals(detail.getNoDocTributario())) {
//							// cantidad unidades
//							if (!mapUnidades.containsKey(detail.getCodProdProveedor())) {
//								mapUnidades.put(detail.getCodProdProveedor(),
//										Integer.valueOf(detail.getCantidadUnidades()));
//							} else {
//								int cantidad = mapUnidades.get(detail.getCodProdProveedor());
//								mapUnidades.put(detail.getCodProdProveedor(),
//										cantidad + Integer.valueOf(detail.getCantidadUnidades()));
//	
//							}
//							// cantidad cajas
//							if (!mapCajas.containsKey(detail.getCodProdProveedor())) {
//								mapCajas.put(detail.getCodProdProveedor(), Integer.valueOf(detail.getCantidadCajas()));
//							} else {
//								int cantidad = mapCajas.get(detail.getCodProdProveedor());
//								mapCajas.put(detail.getCodProdProveedor(),
//										cantidad + Integer.valueOf(detail.getCantidadCajas()));
//	
//							}
//						}
//					}
//				}
//				String numEntrega = message.getEntregas().getEntrega().get(0).getNumero();
//				
//				
//				if(header){
//					dt0.addColumn(new DataColumn("order", "orden", String.class));
//					dt0.addColumn(new DataColumn("nfactura", "n° factura", String.class));
//					dt0.addColumn(new DataColumn("nentrega", "n° entrega", String.class));
//					dt0.addColumn(new DataColumn("dun14", "", String.class));
//					dt0.addColumn(new DataColumn("unidades", "", String.class));
//					dt0.addColumn(new DataColumn("cajas", "", String.class));
//					header = false;
//				}else{
//					// cabecera
//					row = dt0.newRow();
//					row.setCellValue("order", "orden", String.class, headerstyle);
//					row.setCellValue("nfactura", "n° factura",  String.class, headerstyle);
//					row.setCellValue("nentrega", "n° entrega", String.class, headerstyle);
//					row.setCellValue("dun14", "", String.class, headerstyle);
//					row.setCellValue("unidades", "", String.class, headerstyle);
//					row.setCellValue("cajas", "", String.class, headerstyle);
//					dt0.addRow(row);
//				}
//				
//				// cabecera
//				row = dt0.newRow();
//				row.setCellValue("order", (Long.valueOf(key)), Long.class);
//				row.setCellValue("nfactura", Integer.valueOf(nodoc), Integer.class);
//				row.setCellValue("nentrega", Integer.valueOf(numEntrega), Integer.class);
//				row.setCellValue("dun14", "", String.class);
//				row.setCellValue("unidades", "", String.class);
//				row.setCellValue("cajas", "", String.class);
//				dt0.addRow(row);
//	
//				// cabecera detalle
//				row = dt0.newRow();
//				row.setCellValue("order", "codigo sap", String.class);
//				row.setCellValue("nfactura", "denominación", String.class);
//				row.setCellValue("nentrega", "ean13", String.class);
//				row.setCellValue("dun14", "dun14", String.class);
//				row.setCellValue("unidades", "unidades", String.class);
//				row.setCellValue("cajas", "cajas", String.class);
//				dt0.addRow(row);
//				
//				// detalle
//				int totalUnidades = 0;
//				int totalCajas = 0;
//				for (Bulto item : message.getBultos().getBulto()) {
//					for (Detalle detail : item.getDetalles().getDetalle()) {
//						
//						if (key.equals(detail.getNoc()) && nodoc.equals(detail.getNoDocTributario())) {
//	
//							if (mapUnidades.containsKey(detail.getCodProdProveedor()) && mapCajas.containsKey(detail.getCodProdProveedor())) {
//	
//								row = dt0.newRow();
//								row.setCellValue("order", detail.getCodProdProveedor(), String.class);
//								row.setCellValue("nfactura", detail.getDescripcionProd(), String.class);
//								row.setCellValue("nentrega", (Long.valueOf(detail.getEan13())), Long.class);
//								row.setCellValue("dun14", (Long.valueOf(detail.getDun14())), Long.class);
//								row.setCellValue("unidades", mapUnidades.get(detail.getCodProdProveedor()), Integer.class);
//								row.setCellValue("cajas", mapCajas.get(detail.getCodProdProveedor()), Integer.class);
//								dt0.addRow(row);
//								totalUnidades += mapUnidades.get(detail.getCodProdProveedor());
//								totalCajas += mapCajas.get(detail.getCodProdProveedor());
//	
//								// se elimina la key del mapa, para no repetir el
//								// codigo sap
//								mapUnidades.remove(detail.getCodProdProveedor());
//								mapCajas.remove(detail.getCodProdProveedor());
//							}
//						}
//					}
//				}
//				// totales
//				row = dt0.newRow();
//				row.setCellValue("order", "");
//				row.setCellValue("nfactura", "");
//				row.setCellValue("nentrega", "total");
//				row.setCellValue("dun14", "");
//				row.setCellValue("unidades", totalUnidades, Integer.class);
//				row.setCellValue("cajas", totalCajas, Integer.class);
//				dt0.addRow(row);
//				
//				row = dt0.newRow();
//				dt0.addRow(row);
//			}
//		}
//		XLSConverterPOI converter = new XLSConverterPOI();
//		converter.setExcelheaderbold(true);
//		converter.setShowexceltableborder(true);
//		finalPath = folderpath + filename;
//		try {
//			converter.ExportToXLS(dt0, finalPath, Charset.forName("UTF-16"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
//			statusCode = "-1";
//			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
//			BaseResultDTO er = new BaseResultDTO();
//			er.setStatuscode("-1");
//			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
//			errores.add(er);
//		} finally {
//			files.add(finalPath);
//		}
//
//		result.setStatuscode(statusCode);
//		result.setStatusmessage(statusMessage);
//		result.setFiles(files);
//		result.setErrores(errores);
//		result.setFilename(files.size() == 1 ? filename : "");
//		return result;
//	}
	
	public PLFileDTO doCreateFileUnimarc(PLProveedor message, String folderpath, Long TicketNumber) {

		PLFileDTO result = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String statusCode = "0";
		String statusMessage = "";

		
		Map<String, List<String>> docmap = new HashMap<String, List<String>>();
		for (Bulto item : message.getBultos().getBulto()) {
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (!docmap.containsKey(detail.getNoc().trim())) {
					List<String> tmpdoc = new ArrayList<String>();
					tmpdoc.add(detail.getNoDocTributario().trim());
					docmap.put(detail.getNoc().trim(), tmpdoc);
				} else {
					List<String> temLinea = docmap.get(detail.getNoc().trim());
					if (!temLinea.contains(detail.getNoDocTributario().trim())) {
						temLinea.add(detail.getNoDocTributario().trim());
						docmap.put(detail.getNoc().trim(), temLinea);
					}
				}

			}
		}
		
		List<String> files = new ArrayList<String>();
		String finalPath = "";
		String filename = "";
		filename = "PL_" + TicketNumber + "_" + sdf.format(new Date())+".xls";
		
		Locale locale = new Locale("es", "CL");
		GregorianCalendar gcal = new GregorianCalendar(locale);
		gcal = GregorianCalendar.from(LocalDateTime.now().atZone(ZoneId.systemDefault()));
		
		XMLGregorianCalendar created = null;
		try {
			created = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode("-1");
			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
			errores.add(er);
		}
		
		Workbook wb = new Workbook();
		
		DocumentProperties header = new DocumentProperties();
		header.setAuthor("Administrador");
		header.setLastAuthor("Administrador");
		header.setCompany("LOREAL CHILE S.A.");
		header.setCreated(created);
		header.setLastSaved(created);
		header.setVersion(BigDecimal.valueOf(11.9999));
		wb.setDocumentProperties(header);
		
		ExcelWorkbook excelworknbook = new ExcelWorkbook();
		excelworknbook.setWindowHeight(9975);
		excelworknbook.setWindowWidth(15195);
		excelworknbook.setWindowTopX((short)0);
		excelworknbook.setWindowTopY((short)45);
		excelworknbook.setProtectStructure("False");
		excelworknbook.setProtectWindows("False");
		wb.setExcelWorkbook(excelworknbook);
		
		
		Styles styles = new Styles();
		Style style = new Style();
		style.setID("Default");
		style.setName("Normal");
		Alignment al = new Alignment();
		al.setVertical("Bottom");
		style.setAlignment(al);
		style.setBorders(new Borders());
		style.setFont(new Font());
		style.setInterior(new Interior());
		style.setNumberFormat(new NumberFormat());
		style.setProtection("");
		styles.getStyle().add(style);
		
		Style styles21 = new Style();
		styles21.setID("s21");
		Font fonts21 = new Font();
		fonts21.setFontName("MS Sans Serif");
		fonts21.setFamily("Swiss");
		fonts21.setSize(BigDecimal.valueOf(8.5));
		styles21.setFont(fonts21);
		styles.getStyle().add(styles21);
		
		
		
		Style styles22 = new Style();
		styles22.setID("s22");
		Borders borders = new Borders();
		Border borders22_1= new Border();
		borders22_1.setPosition("Bottom");
		borders22_1.setLineStyle("Continuous");
		borders22_1.setWeight((short)1);
		borders.getBorder().add(borders22_1);
		
		Border borders22_2= new Border();
		borders22_2.setPosition("Left");
		borders22_2.setLineStyle("Continuous");
		borders22_2.setWeight((short)1);
		borders.getBorder().add(borders22_2);
		
		Border borders22_3= new Border();
		borders22_3.setPosition("Right");
		borders22_3.setLineStyle("Continuous");
		borders22_3.setWeight((short)1);
		borders.getBorder().add(borders22_3);
		
		Border borders22_4= new Border();
		borders22_4.setPosition("Top");
		borders22_4.setLineStyle("Continuous");
		borders22_4.setWeight((short)1);
		borders.getBorder().add(borders22_4);
		
		styles22.setBorders(borders);
		Font fonts22 = new Font();
		fonts22.setFontName("MS Sans Serif");
		fonts22.setFamily("Swiss");
		fonts22.setSize(BigDecimal.valueOf(8.5));
		styles22.setFont(fonts22);
		
		Interior interiors22 = new Interior();
		interiors22.setColor("#FFCC99");
		interiors22.setPattern("Solid");
		styles22.setInterior(interiors22);
		
		NumberFormat numberfs22 = new NumberFormat();
		numberfs22.setFormat((short)0);
		styles22.setNumberFormat(numberfs22);
		
		styles.getStyle().add(styles22);
		
		Style styles23 = new Style();
		styles23.setID("s23");
		Borders borders23 = new Borders();
		borders23.getBorder().add(borders22_1);
		borders23.getBorder().add(borders22_2);
		borders23.getBorder().add(borders22_3);
		borders23.getBorder().add(borders22_4);
		
		styles23.setBorders(borders23);
		styles23.setFont(fonts22);
		styles23.setNumberFormat(numberfs22);
		
		styles.getStyle().add(styles23);
		
		Style styles24 = new Style();
		styles24.setID("s24");
		Borders borders24 = new Borders();
		borders24.getBorder().add(borders22_1);
		borders24.getBorder().add(borders22_2);
		borders24.getBorder().add(borders22_3);
		borders24.getBorder().add(borders22_4);
		
		styles24.setBorders(borders24);
		styles24.setFont(fonts22);
		Interior interiors24 = new Interior();
		interiors24.setColor("#C0C0C0");
		interiors24.setPattern("Solid");
		styles24.setInterior(interiors24);
		styles24.setNumberFormat(numberfs22);
		
		styles.getStyle().add(styles24);
		
		Style styles25 = new Style();
		styles25.setID("s25");
		styles25.setFont(fonts22);
		styles25.setNumberFormat(numberfs22);
		
		styles.getStyle().add(styles25);
		
		wb.setStyles(styles);
		
		Worksheet worksheet = new Worksheet();
		worksheet.setName("Hoja1");
		Table table = new Table();
		table.setFullColumns((short)1);
		table.setFullRows((short)1);
		table.setStyleID("s21");
		table.setDefaultColumnWidth((short)60);
		table.setDefaultRowHeight(BigDecimal.valueOf(10.5));
		
		Column col1 = new Column();
		col1.setStyleID("s25");
		col1.setWidth(BigDecimal.valueOf(45.75));
		Column col2 = new Column();
		col2.setStyleID("s25");
		col2.setWidth(BigDecimal.valueOf(204.75));
		Column col3 = new Column();
		col3.setStyleID("s25");
		col3.setWidth(BigDecimal.valueOf(63.75));
		Column col4 = new Column();
		col4.setStyleID("s25");
		col4.setWidth(BigDecimal.valueOf(68.25));
		Column col5 = new Column();
		col5.setStyleID("s25");
		col5.setWidth(BigDecimal.valueOf(37.5));
		Column col6 = new Column();
		col6.setStyleID("s25");
		col6.setWidth(BigDecimal.valueOf(24));
		
		table.getColumn().add(col1);
		table.getColumn().add(col2);
		table.getColumn().add(col3);
		table.getColumn().add(col4);
		table.getColumn().add(col5);
		table.getColumn().add(col6);

		
		SortedSet<String> keys = new TreeSet<>(docmap.keySet());
		int count = 1;
		for (String key : keys) { 
			for (String nodoc : docmap.get(key)) {
				// Calculo cantidad cajas y unidades por codigo sap(cod prod
				// proveedor)
				Map<String, Integer> mapUnidades = new HashMap<String, Integer>();
				Map<String, Integer> mapCajas = new HashMap<String, Integer>();
				
				for (Bulto item : message.getBultos().getBulto()) {
					for (Detalle detail : item.getDetalles().getDetalle()) {
						if (key.equals(detail.getNoc().trim()) && nodoc.equals(detail.getNoDocTributario().trim())) {
							// cantidad unidades
							if (!mapUnidades.containsKey(detail.getCodProdProveedor().trim())) {
								mapUnidades.put(detail.getCodProdProveedor().trim(),
										Integer.valueOf(detail.getCantidadUnidades().trim()));
							} else {
								int cantidad = mapUnidades.get(detail.getCodProdProveedor().trim());
								mapUnidades.put(detail.getCodProdProveedor().trim(),
										cantidad + Integer.valueOf(detail.getCantidadUnidades().trim()));
	
							}
							// cantidad cajas
							if (!mapCajas.containsKey(detail.getCodProdProveedor().trim())) {
								mapCajas.put(detail.getCodProdProveedor().trim(), Integer.valueOf(detail.getCantidadCajas().trim()));
							} else {
								int cantidad = mapCajas.get(detail.getCodProdProveedor().trim());
								mapCajas.put(detail.getCodProdProveedor().trim(),
										cantidad + Integer.valueOf(detail.getCantidadCajas().trim()));
	
							}
						}
					}
				}
				String numEntrega = message.getEntregas().getEntrega().get(0).getNumero().trim();
				

				
				
				
				Row row = new Row();
				table.getRow().add(row);
				//header1
				Row header1 = new Row();
				Cell cellh11 = new Cell();
				cellh11.setStyleID("s22");
				Data datah11 = new Data();
				datah11.setType("String");
				datah11.setValue("orden");
				
				cellh11.setData(datah11);
				header1.getCell().add(cellh11);
				
				Cell cellh12 = new Cell();
				cellh12.setStyleID("s22");
				Data datah12 = new Data();
				datah12.setType("String");
				datah12.setValue("n° factura");
				
				cellh12.setData(datah12);
				header1.getCell().add(cellh12);
				
				Cell cellh13 = new Cell();
				cellh13.setStyleID("s22");
				Data datah13 = new Data();
				datah13.setType("String");
				datah13.setValue("n° entrega");
				
				cellh13.setData(datah13);
				header1.getCell().add(cellh13);
				
				Cell cellh14 = new Cell();
				cellh14.setStyleID("s22");
				
				header1.getCell().add(cellh14);
				
				Cell cellh15 = new Cell();
				cellh15.setStyleID("s22");
				
				header1.getCell().add(cellh15);
				
				Cell cellh16 = new Cell();
				cellh16.setStyleID("s22");
				
				header1.getCell().add(cellh16);
				
				//header2
				Row header2 = new Row();
				Cell cellh21 = new Cell();
				cellh21.setStyleID("s23");
				Data datah21 = new Data();
				datah21.setType("Number");
				datah21.setValue(key);//nro orden
				
				cellh21.setData(datah21);
				header2.getCell().add(cellh21);
				
				Cell cellh22 = new Cell();
				cellh22.setStyleID("s23");
				Data datah22 = new Data();
				datah22.setType("Number");
				datah22.setValue(nodoc);//n factura
				
				cellh22.setData(datah22);
				header2.getCell().add(cellh22);
				
				Cell cellh23 = new Cell();
				cellh23.setStyleID("s23");
				Data datah23 = new Data();
				datah23.setType("Number");
				datah23.setValue(numEntrega.replaceFirst("^0+(?!$)", ""));//n entrega
				
				cellh23.setData(datah23);
				header2.getCell().add(cellh23);
				
				Cell cellh24 = new Cell();
				cellh24.setStyleID("s23");
				header2.getCell().add(cellh24);
				
				Cell cellh25 = new Cell();
				cellh25.setStyleID("s23");	
				header2.getCell().add(cellh25);
				
				Cell cellh26 = new Cell();
				cellh26.setStyleID("s23");
				header2.getCell().add(cellh26);
				
				
				
				//header3
				Row header3 = new Row();
				Cell cellh31 = new Cell();
				cellh31.setStyleID("s24");
				Data datah31 = new Data();
				datah31.setType("String");
				datah31.setValue("codigo sap");
				
				cellh31.setData(datah31);
				header3.getCell().add(cellh31);
				
				Cell cellh32 = new Cell();
				cellh32.setStyleID("s24");
				Data datah32 = new Data();
				datah32.setType("String");
				datah32.setValue("denominación");
				
				cellh32.setData(datah32);
				header3.getCell().add(cellh32);
				
				Cell cellh33 = new Cell();
				cellh33.setStyleID("s24");
				Data datah33 = new Data();
				datah33.setType("String");
				datah33.setValue("ean13");
				
				cellh33.setData(datah33);
				header3.getCell().add(cellh33);
				
				Cell cellh34 = new Cell();
				cellh34.setStyleID("s24");
				Data datah34 = new Data();
				datah34.setType("String");
				datah34.setValue("dun14");
				
				cellh34.setData(datah34);
				header3.getCell().add(cellh34);
				
				Cell cellh35 = new Cell();
				cellh35.setStyleID("s24");
				Data datah35 = new Data();
				datah35.setType("String");
				datah35.setValue("unidades");
				
				cellh35.setData(datah35);
				header3.getCell().add(cellh35);
				
				Cell cellh36 = new Cell();
				cellh36.setStyleID("s24");
				Data datah36 = new Data();
				datah36.setType("String");
				datah36.setValue("cajas");
				
				cellh36.setData(datah36);
				header3.getCell().add(cellh36);
				
				table.getRow().add(header1);
				table.getRow().add(header2);
				table.getRow().add(header3);
				
				
				// detalle
				int totalUnidades = 0;
				int totalCajas = 0;
				int i = 0;
				for (Bulto item : message.getBultos().getBulto()) {
					for (Detalle detail : item.getDetalles().getDetalle()) {
						
						if (key.equals(detail.getNoc().trim()) && nodoc.equals(detail.getNoDocTributario().trim())) {
	
							if (mapUnidades.containsKey(detail.getCodProdProveedor().trim()) && mapCajas.containsKey(detail.getCodProdProveedor().trim())) {
								
								Row row1 = new Row();
								Cell cell1 = new Cell();
								cell1.setStyleID("s23");
								Data data1 = new Data();
								data1.setType("String");
								data1.setValue(detail.getCodProdProveedor().trim());
								
								cell1.setData(data1);
								row1.getCell().add(cell1);
								
								Cell cell2 = new Cell();
								cell2.setStyleID("s23");
								Data data2 = new Data();
								data2.setType("String");
								data2.setValue(detail.getDescripcionProd().trim());
								
								cell2.setData(data2);
								row1.getCell().add(cell2);
								
								Cell cell3 = new Cell();
								cell3.setStyleID("s23");
								Data data3 = new Data();
								data3.setType("Number");
								data3.setValue(detail.getEan13().trim());
								
								cell3.setData(data3);
								row1.getCell().add(cell3);
								
								Cell cell4 = new Cell();
								cell4.setStyleID("s23");
								Data data4 = new Data();
								data4.setType("Number");
								data4.setValue(detail.getDun14().trim());
								
								cell4.setData(data4);
								row1.getCell().add(cell4);
								
								Cell cell5 = new Cell();
								cell5.setStyleID("s23");
								Data data5 = new Data();
								data5.setType("Number");
								data5.setValue(String.valueOf(mapUnidades.get(detail.getCodProdProveedor().trim())));
								
								cell5.setData(data5);
								row1.getCell().add(cell5);
								
								Cell cell6 = new Cell();
								cell6.setStyleID("s23");
								Data data6 = new Data();
								data6.setType("Number");
								data6.setValue(String.valueOf(mapCajas.get(detail.getCodProdProveedor().trim())));
								
								cell6.setData(data6);
								row1.getCell().add(cell6);
								
								table.getRow().add(row1);
								
								totalUnidades += mapUnidades.get(detail.getCodProdProveedor().trim());
								totalCajas += mapCajas.get(detail.getCodProdProveedor().trim());
	
								// se elimina la key del mapa, para no repetir el
								// codigo sap
								mapUnidades.remove(detail.getCodProdProveedor().trim());
								mapCajas.remove(detail.getCodProdProveedor().trim());
							}
						}
					}
				}
				
				Row rowtotal = new Row();
				
				Cell cell1 = new Cell();
				cell1.setStyleID("s23");
				rowtotal.getCell().add(cell1);
				
				Cell cell2 = new Cell();
				cell2.setStyleID("s23");
				rowtotal.getCell().add(cell2);
				
				Cell cell3 = new Cell();
				cell3.setStyleID("s23");
				Data data3 = new Data();
				data3.setType("String");
				data3.setValue("total");
				cell3.setData(data3);
				rowtotal.getCell().add(cell3);
				
				Cell cell4 = new Cell();
				cell4.setStyleID("s23");
				rowtotal.getCell().add(cell4);
				
				Cell cell5 = new Cell();
				cell5.setStyleID("s23");
				Data data5 = new Data();
				data5.setType("Number");
				data5.setValue(String.valueOf(totalUnidades));
				
				cell5.setData(data5);
				rowtotal.getCell().add(cell5);
				
				Cell cell6 = new Cell();
				cell6.setStyleID("s23");
				Data data6 = new Data();
				data6.setType("Number");
				data6.setValue(String.valueOf(totalCajas));
				
				cell6.setData(data6);
				rowtotal.getCell().add(cell6);
				
				table.getRow().add(rowtotal);
				
//				count++;
//				if(count < keys.size()){
//					Row emptyrow = new Row();
//					table.getRow().add(emptyrow);//separador de tabla
//				}
				worksheet.setTable(table);
			}
		}
		
		WorksheetOptions wso1 = new WorksheetOptions();

		
		//WorksheetOptions1
		PageSetup pagesetup = new PageSetup();
		Layout layout = new Layout();
		layout.setCenterHorizontal((short)1);
		Header header1 = new Header();
		header1.setMargin((short)0);
		Footer footer = new Footer();
		footer.setMargin((short)0);
		
		pagesetup.setLayout(layout);
		pagesetup.setHeader(header1);
		pagesetup.setFooter(footer);
		
		PageMargins pagemargins = new PageMargins();
		pagemargins.setBottom(BigDecimal.valueOf(0.98425196850393748));
		pagemargins.setLeft(BigDecimal.valueOf(0.1968503937007875));
		pagemargins.setRight(BigDecimal.valueOf(0.1968503937007875));
		pagemargins.setTop(BigDecimal.valueOf(0.98425196850393748));
		
		pagesetup.setPageMargins(pagemargins);
		
		Print print = new Print();
		print.setValidPrinterInfo("");
		print.setHorizontalResolution(600);
		print.setVerticalResolution(600);
		
		wso1.setPageSetup(pagesetup);
		wso1.setPrint(print);
		wso1.setSelected("");
		wso1.setDoNotDisplayGridlines("");
		wso1.setProtectObjects("False");
		wso1.setProtectScenarios("False");
		
		//PageBreaks
		PageBreaks pb = new PageBreaks();
		RowBreaks rbs = new RowBreaks(); 
		RowBreak rb = new RowBreak();
		rb.setRow((short)67);
		rbs.setRowBreak(rb);
		pb.setRowBreaks(rbs);
		
		worksheet.setWorksheetOptions(wso1);
		worksheet.setPageBreaks(pb);
		//hoja2
		
		Worksheet worksheet2 = new Worksheet();
		worksheet2.setName("Hoja2");
		Table table2 = new Table();
		table2.setFullColumns((short)1);
		table2.setFullRows((short)1);
		table2.setDefaultColumnWidth((short)60);
		
		worksheet2.setTable(table2);
		WorksheetOptions wso2 = new WorksheetOptions();
		
		PageSetup pagesetup2 = new PageSetup();
		Header header2 = new Header();
		header2.setMargin((short)0);
		Footer footer2 = new Footer();
		footer2.setMargin((short)0);
		PageMargins pagemargins2 = new PageMargins();
		pagemargins2.setBottom(BigDecimal.valueOf(0.984251969));
		pagemargins2.setLeft(BigDecimal.valueOf(0.78740157499999996));
		pagemargins2.setRight(BigDecimal.valueOf(0.78740157499999996));
		pagemargins2.setTop(BigDecimal.valueOf(0.984251969));
		
		pagesetup2.setHeader(header2);
		pagesetup2.setFooter(footer);
		pagesetup2.setPageMargins(pagemargins2);
		
		wso2.setPageSetup(pagesetup2);
		wso2.setProtectObjects("False");
		wso2.setProtectScenarios("False");
		
		worksheet2.setWorksheetOptions(wso2);
		
		//hoja3
		Worksheet worksheet3 = new Worksheet();
		worksheet3.setName("Hoja3");
		Table table3 = new Table();
		table3.setFullColumns((short)1);
		table3.setFullRows((short)1);
		table3.setDefaultColumnWidth((short)60);
		
		worksheet3.setTable(table3);
		WorksheetOptions wso3 = new WorksheetOptions();
		
		PageSetup pagesetup3 = new PageSetup();
		Header header3 = new Header();
		header3.setMargin((short)0);
		Footer footer3 = new Footer();
		footer3.setMargin((short)0);
		PageMargins pagemargins3 = new PageMargins();
		pagemargins3.setBottom(BigDecimal.valueOf(0.984251969));
		pagemargins3.setLeft(BigDecimal.valueOf(0.78740157499999996));
		pagemargins3.setRight(BigDecimal.valueOf(0.78740157499999996));
		pagemargins3.setTop(BigDecimal.valueOf(0.984251969));
		
		pagesetup3.setHeader(header3);
		pagesetup3.setFooter(footer3);
		pagesetup3.setPageMargins(pagemargins3);
		
		wso3.setPageSetup(pagesetup3);
		wso3.setProtectObjects("False");
		wso3.setProtectScenarios("False");
		
		worksheet3.setWorksheetOptions(wso3);
		
		wb.getWorksheet().add(worksheet);
		wb.getWorksheet().add(worksheet2);
		wb.getWorksheet().add(worksheet3);
		
		finalPath = folderpath + filename;
		
		JAXBContext jc;
		try {
			jc = getJC_Unimarc();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(wb, sw);
			String strResult = sw.toString();
			
			strResult = strResult.substring(strResult.indexOf('\n') + 1);
			strResult = strResult.substring(strResult.indexOf('\n') + 1);
			strResult = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
					"<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n"+
					strResult;
			strResult = strResult.replace("\n","\r\n");
			strResult = strResult.replace("<ns2:","<");
			strResult = strResult.replace("</ns2:","</");
			strResult = strResult.replace("<ns1:","<");
			strResult = strResult.replace("</ns1:","</");
			strResult = strResult.replace("<ns3:","<");
			strResult = strResult.replace("</ns3:","</");
			strResult = strResult.replace("/>"," />");
		
			if(strResult.contains("ns2:ID=\"Default\" ns2:Name=\"Normal\"")){
				strResult = strResult.replace(" ns2:"," ss:");
				strResult = strResult.replace(" ns1:"," x:");
			}else if(strResult.contains("ns3:ID=\"Default\" ns3:Name=\"Normal\"")){
				strResult = strResult.replace(" ns1:"," x:");
				strResult = strResult.replace(" ns3:"," ss:");
				strResult = strResult.replace(" ns2:"," x:");
			}else{
				strResult = strResult.replace(" ns1:"," x:");
			}
			
			strResult = strResult.replace("<ExcelWorkbook>","<ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			strResult = strResult.replace("<DocumentProperties>","<DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">");
			
			strResult = strResult.replace("<WorksheetOptions>","<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			strResult = strResult.replace("<PageBreaks>","<PageBreaks xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			
			
			strResult = strResult.replace("<Selected xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Selected>","<Selected />");
			strResult = strResult.replace("<DoNotDisplayGridlines xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></DoNotDisplayGridlines>","<DoNotDisplayGridlines />");
			strResult = strResult.replace("<ValidPrinterInfo xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ValidPrinterInfo>","<ValidPrinterInfo />");
			strResult = strResult.replace("<Protection xsi:type=\"xs:string\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Protection>" , "<Protection />");
			
			strResult = strResult.replace("<Row />\r\n","<Row>\r\n			</Row>\r\n");
			
//			PrintWriter out = new PrintWriter(new File(finalPath), "UTF-8");
//			out.write(strResult);
			
			new_file_with_text(strResult, finalPath);
			
//			java.io.FileWriter fw = new java.io.FileWriter(finalPath);
//			fw.write(strResult);
//			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode("-1");
			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		result.setStatuscode(statusCode);
		result.setStatusmessage(statusMessage);
		result.setFiles(files);
		result.setErrores(errores);
		result.setFilename(files.size() == 1 ? filename : "");
		return result;
	}

	public PLFileDTO doCreateFileFalabella(PLProveedor message, String folderpath) {
		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String statusCode = "0";
		String statusMessage = "";
		ObjectFactory objFactory = new ObjectFactory();

		Map<String, List<Bulto>> map = new HashMap<String, List<Bulto>>();
		Map<String, List<String>> docmap = new HashMap<String, List<String>>();

		for (Bulto item : message.getBultos().getBulto()) {
			int numdetails = 0;
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (numdetails == 0) {
					if (!map.containsKey(detail.getNoc().trim())) {
						List<Bulto> tmp = new ArrayList<Bulto>();
						tmp.add(item);
						map.put(detail.getNoc().trim(), tmp);
					} else {
						List<Bulto> temLinea = map.get(detail.getNoc().trim());
						temLinea.add(item);
						map.put(detail.getNoc().trim(), temLinea);
					}
					numdetails++;
				}

				if (!docmap.containsKey(detail.getNoc().trim())) {
					List<String> tmpdoc = new ArrayList<String>();
					tmpdoc.add(detail.getNoDocTributario().trim());
					docmap.put(detail.getNoc().trim(), tmpdoc);
				} else {
					List<String> temLinea = docmap.get(detail.getNoc().trim());
					if (!temLinea.contains(detail.getNoDocTributario().trim())) {
						temLinea.add(detail.getNoDocTributario().trim());
						docmap.put(detail.getNoc().trim(), temLinea);
					}
				}
			}
		}

		List<String> files = new ArrayList<String>();
		String finalPath = "";
		String filename = "";

		for (Map.Entry<String, List<String>> entry : docmap.entrySet()) {
			for (String nodoc : entry.getValue()) {

				boolean header = true;
				filename = "ePIR-" +nodoc + "_Loreal_"+ sdf.format(new Date()) + ".xml";
				EPIR epir = objFactory.createEPIR();
				int bulto = 0;
				String nrobulto = "";
				PIR pir = objFactory.createEPIRPIR();
				int rownum = 1;
				PRODUCTO producto = objFactory.createEPIRPIRPRODUCTO();
				for (Bulto item : map.get(entry.getKey())) {// se obtienen los
															// bultos del mapa
															// OC/Bulto
					for (Detalle detail : item.getDetalles().getDetalle()) {

						if (nodoc.equals(detail.getNoDocTributario().trim())) {

							if (!nrobulto.equals(item.getNroBulto().trim())) {
								nrobulto = item.getNroBulto().trim();
								bulto++;
							}
							if (header) {
								String pirnum = "";
								for (DocTributario doc : message.getDocsTributario().getDocTributario()) {
									if (doc.getNoDocTributario().trim().equals(detail.getNoDocTributario().trim())) {
										pirnum = doc.getNumref1().trim();
									}
								}
								String totalbultos = "";// se obtiene el total
														// de bultos de la
														// entrega
								for (Entrega entrega : message.getEntregas().getEntrega()) {
									if (detail.getEntrega().trim().equals(entrega.getNumero().trim())) {
										totalbultos = entrega.getTotalBultos().trim();
									}
								}

								pir.setNum(Long.valueOf(pirnum));
								pir.setNROOC(entry.getKey());
								String str = item.getFechaDespacho().trim();
								LocalDate dateTime = LocalDate.parse(str);
								String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
								pir.setFECHADESPACHO(formattedDate);
								pir.setHORADESPACHO(item.getHoraDespacho().trim());
								pir.setTOTALBULTOS(Long.valueOf(totalbultos));
								pir.setTOTALTOTES(Long.valueOf(message.getTotalTotes().trim()));
								pir.setTOTALCOLGADOS(Long.valueOf(message.getTotalColgados().trim()));
								pir.setNROSERIEFACT("");// FIJO
								pir.setNROFACTURA(detail.getNoDocTributario().trim());
								pir.setALMACEN(message.getCodLocalEntrega().trim());
								pir.setGUIASDESPACHO("");

								header = false;
							}

							PRODUCTOROW producto_row = objFactory.createEPIRPIRPRODUCTOPRODUCTOROW();
							producto_row.setNum(rownum);
							producto_row.setUPC(Long.valueOf(detail.getEan13().trim()));
							producto_row.setDESCRIPCIONLARGA(detail.getDescripcionProd().length() <= 20 ? detail.getDescripcionProd().trim() : detail.getDescripcionProd().substring(0, 20).trim());
							producto_row
									.setNROLOCAL(item.getCodLocalDestino().isEmpty() ? "" : item.getCodLocalDestino().trim());
							producto_row.setLOCAL(
									item.getDescripcionLocalDestino() != null ? item.getDescripcionLocalDestino().trim() : "");
							producto_row.setCANTIDAD(Long.valueOf(detail.getCantidadUnidades().trim()));
							producto_row.setTIPOEMBALAJE(item.getTipoEmbalaje().trim());
							producto_row.setNROBULTO(Long.valueOf(item.getNroBulto().trim()));

							producto.getPRODUCTOROW().add(producto_row);
							rownum++;
						}
					}
				}
				epir.setPIR(pir);
				pir.setPRODUCTO(producto);
				finalPath = folderpath + filename;

				JAXBContext jc;
				try {
					jc = getJC_Falabella();
					Marshaller m = jc.createMarshaller();
					// m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
					m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					StringWriter sw = new StringWriter();
					m.marshal(epir, sw);
					String strResult = sw.toString();
					// result = "<?xml version=\"1.0\"
					// encoding=\"iso-8859-1\"?>\n"+result;
					// para generar selfclosing tags en elementos vacíos
//					StreamSource source = new StreamSource(new StringReader(result));
//					StringWriter writer = new StringWriter();
//					StreamResult result2 = new StreamResult(writer);
//					TransformerFactory tFactory = TransformerFactory.newInstance();
//					Transformer transformer = tFactory.newTransformer();
//					transformer.transform(source, result2);
//					String strResult = writer.toString();
					

					strResult = strResult.substring(strResult.indexOf('\n') + 1);
					strResult = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n" + strResult;

					strResult = strResult.replace("<NRO_SERIE_FACT></NRO_SERIE_FACT>","<NRO_SERIE_FACT />");
					strResult = strResult.replace("<GUIAS_DESPACHO></GUIAS_DESPACHO>","<GUIAS_DESPACHO />");
					strResult = strResult.replace("\n","\r\n");
					
					
//					String encodedWithUTF8 = strResult;
//					String decodedToISO88591 = new String(encodedWithUTF8.getBytes("UTF-8"), "ISO-8859-1");
					//StringBuilder sb = HtmlEncoder.escapeNonLatin(strResult, new StringBuilder());
					//System.out.println(sb.toString());
					//String iso8859str = sb.toString();
					// convert to file
					String iso8859str = new String(strResult.getBytes(),"ISO-8859-1");
					
					java.io.FileWriter fw = new java.io.FileWriter(finalPath);
					fw.write(iso8859str);
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
					statusCode = "-1";
					statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
					BaseResultDTO er = new BaseResultDTO();
					er.setStatuscode("-1");
					er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
					errores.add(er);
				} finally {
					files.add(finalPath);
				}
			}
		}
		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? filename : "");
		return plresult;
	}
	
	public PLFileDTO doCreateFileTottus(PLProveedor message, String folderpath) {
		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String statusCode = "0";
		String statusMessage = "";
		String finalPath = "";
		int numlineas = 0;

		Map<String, List<Bulto>> map = new HashMap<String, List<Bulto>>();

		for (Bulto item : message.getBultos().getBulto()) {
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (!map.containsKey(detail.getNoc().trim())) {
					List<Bulto> tmp = new ArrayList<Bulto>();
					tmp.add(item);
					map.put(detail.getNoc().trim(), tmp);
				} else {
					List<Bulto> temLinea = map.get(detail.getNoc().trim());
					temLinea.add(item);
					map.put(detail.getNoc().trim(), temLinea);
				}
			}
		}

		List<String> files = new ArrayList<String>();
		String filename = "";

		for (Map.Entry<String, List<Bulto>> entry : map.entrySet()) {
			Map<EanFactDTO, Integer> mapEan13 = new HashMap<EanFactDTO, Integer>();
			for (Bulto item : entry.getValue()) {
				for (Detalle detail : item.getDetalles().getDetalle()) {
					EanFactDTO tmp = new EanFactDTO();
					tmp.setEan13(detail.getEan13().trim());
					tmp.setNodoc(detail.getNoDocTributario().trim());
					if (!mapEan13.containsKey(tmp)) {
						mapEan13.put(tmp, Integer.valueOf(detail.getCantidadUnidades().trim()));
						numlineas++;
					} else {
						int cantidad = mapEan13.get(tmp);
						mapEan13.put(tmp, cantidad + Integer.valueOf(detail.getCantidadUnidades().trim()));

					}
				}
			}

			filename = "PL_" + message.getNroCita().trim() + "_" + sdf.format(new Date()) + "_" + ".txt";

			String[][] data = new String[numlineas + 2][11];

			// header
			data[0][0] = "1"; // fija
			data[0][1] = ""; // vacia
			data[0][2] = entry.getKey();// numOC
			data[0][5] = String.valueOf(numlineas);
			data[0][6] = "0"; // fija
			data[0][7] = "0"; // fija
			data[0][8] = "0"; // fija
			data[0][9] = "0"; // fija

			// detalle
			int line = 1;
			boolean header = false;
			List<TottusDetailDTO> listdetail = new ArrayList<TottusDetailDTO>();
			for (Bulto item : entry.getValue()) {
				// header
                if (!header) {

                  String str = item.getFechaBulto().trim();
                  LocalDate dateTime = LocalDate.parse(str);
                  String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
                    data[0][3] = formattedDate;
                    data[0][4] = item.getHoraDespacho().trim();
                    data[0][10] = item.getCodLocalEntrega().trim();
                    header = true;
                }
				// detalle
				for (Detalle detail : item.getDetalles().getDetalle()) {
					EanFactDTO tmp1 = new EanFactDTO();
					tmp1.setEan13(detail.getEan13().trim());
					tmp1.setNodoc(detail.getNoDocTributario().trim());

					if (mapEan13.containsKey(tmp1)) {
						TottusDetailDTO tdetail = new TottusDetailDTO();
						tdetail.setEan13(detail.getEan13().trim());
						tdetail.setCodlocalentrega(item.getCodLocalEntrega().trim());
						tdetail.setEpu("EPU");
						tdetail.setUnidades(mapEan13.get(tmp1));
						tdetail.setBu("BU");
						tdetail.setNodoctributario(detail.getNoDocTributario().trim());
						listdetail.add(tdetail);
						mapEan13.remove(tmp1);// se quita del mapa para no
												// volver a crear una fila en el
												// archivo
					}
				}
			}

			// se ordena por ean13 y luego por numero de documento
			listdetail.sort((l1, l2) -> l1.getEan13().compareTo(l2.getEan13()));
			listdetail.sort((l1, l2) -> l1.getNodoctributario().compareTo(l2.getNodoctributario()));
			// Collections.sort(listdetail);

			for (TottusDetailDTO detail : listdetail) {
				data[line][0] = "2";
				data[line][1] = detail.getEan13().trim();
				data[line][2] = detail.getCodlocalentrega().trim();
				data[line][3] = "EPU";// fija
				data[line][4] = detail.getUnidades().toString();
				data[line][5] = "BU";// fija
				data[line][6] = "";// vacía
				data[line][7] = detail.getNodoctributario().trim();
				data[line][8] = "";// vacia
				data[line][9] = "";// vacia
				data[line][10] = "";// vacia
				line++;
			}
			// final
			data[line][0] = "3";
			data[line][1] = listdetail.get(0).getNodoctributario().trim();
			data[line][2] = "";
			data[line][3] = "";
			data[line][4] = "";
			data[line][5] = "";
			data[line][6] = "";
			data[line][7] = "";
			data[line][8] = "";
			data[line][9] = "";
			data[line][10] = "";

			finalPath = folderpath + filename;

			try {
				BBRStringUtils.doCSVStringTottus(data, "|", filename);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
				statusCode = "-1";
				statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
				BaseResultDTO er = new BaseResultDTO();
				er.setStatuscode(statusCode);
				er.setStatusmessage(statusMessage);
				errores.add(er);
			} finally {
				files.add(finalPath);
			}
		}

		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? filename : "");
		return plresult;
	}

	public PLFileDTO doCreateFileRipley(PLProveedor message, String folderpath) {
		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String statusCode = "0";
		String statusMessage = "";
		bbr.b2b.logistic.xml.PLRipleyBultoFactura.ObjectFactory objFactory = new bbr.b2b.logistic.xml.PLRipleyBultoFactura.ObjectFactory();

		Map<String, List<Bulto>> map = new HashMap<String, List<Bulto>>();
		Map<String, List<String>> docmap = new HashMap<String, List<String>>();

		for (Bulto item : message.getBultos().getBulto()) {
			int numdetails = 0;
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (numdetails == 0) {
					if (!map.containsKey(detail.getNoDocTributario().trim())) {
						List<Bulto> tmp = new ArrayList<Bulto>();
						tmp.add(item);
						map.put(detail.getNoc().trim(), tmp);
					} else {
						List<Bulto> temLinea = map.get(detail.getNoc().trim());
						temLinea.add(item);
						map.put(detail.getNoc().trim(), temLinea);
					}
					numdetails++;
				}
				if (!docmap.containsKey(detail.getNoc().trim())) {
					List<String> tmpdoc = new ArrayList<String>();
					tmpdoc.add(detail.getNoDocTributario().trim());
					docmap.put(detail.getNoc().trim(), tmpdoc);
				} else {
					List<String> temLinea = docmap.get(detail.getNoc().trim());
					if (!temLinea.contains(detail.getNoDocTributario().trim())) {
						temLinea.add(detail.getNoDocTributario().trim());
						docmap.put(detail.getNoc().trim(), temLinea);
					}
				}
			}
		}

		// mapa con doc tributario y tipo
		Map<String, String> mapdoc = new HashMap<String, String>();
		for (DocTributario doc : message.getDocsTributario().getDocTributario()) {
			if (!mapdoc.containsKey(doc.getNoDocTributario().trim())) {
				mapdoc.put(doc.getNoDocTributario().trim(), doc.getTipoDocTributario().trim());
			}
		}

		Map<HeaderDTO, List<Bulto>> mapdocbulto = new HashMap<HeaderDTO, List<Bulto>>();
		for (Bulto item : message.getBultos().getBulto()) {
			int numdetails = 0;
			HeaderDTO header = new HeaderDTO();
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (numdetails == 0) {

					header.setNoc(detail.getNoc().trim());
					header.setNodoc(detail.getNoDocTributario().trim());
	
					if (!mapdocbulto.containsKey(header)) {
						List<Bulto> tmp = new ArrayList<Bulto>();
						tmp.add(item);
						mapdocbulto.put(header, tmp);
					} else {
						List<Bulto> temLinea = mapdocbulto.get(header);
						temLinea.add(item);
						mapdocbulto.put(header, temLinea);
					}
					numdetails++;
				}
			}
		}
		
		List<String> files = new ArrayList<String>();
		String finalPath = "";
		String filename = "";
		filename = "OPC" + "Bulto-Factura_Loreal" + "_" + message.getNroCita().trim() + "_" + sdf.format(new Date()) + ".xml";
		DOCBULTOSDEMODI docbultos = objFactory.createDOCBULTOSDEMODI();
		docbultos.setRUT("79693930");
		FACTURAGUIA factguia;
		
		List<HeaderDTO> keys = new ArrayList<>(mapdocbulto.keySet());

		Collections.sort(keys, Comparator.comparing(HeaderDTO::getNodoc));

		for (HeaderDTO headerDTO : keys) {
			
			factguia = new FACTURAGUIA();
			String tipo = mapdoc.get(headerDTO.getNodoc());
			factguia.setFACTURAOGUIA(tipo);
			factguia.setNUMFACGUI(headerDTO.getNodoc());
			factguia.setNUMODI(headerDTO.getNoc());

			BULTODEMODI bultodemodi = objFactory.createDOCBULTOSDEMODIFACTURAGUIABULTODEMODI();
			for (Bulto bulto : mapdocbulto.get(headerDTO)) {

				bultodemodi = objFactory.createDOCBULTOSDEMODIFACTURAGUIABULTODEMODI();
				String str = bulto.getFechaBulto().trim();
				LocalDate dateTime = LocalDate.parse(str);
				String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
				bultodemodi.setFECHABULTO(formattedDate);
				bultodemodi.setNUMBULTO(/* "6488"+"10039"+"0"+ */bulto.getNroBulto().trim());
				int cantidadunidades = 0;
				String numprodripley = "";
				String provcasepack = "";

				DETALLEBULTODEMODI detallebulto = new DETALLEBULTODEMODI();
				for (Detalle detail : bulto.getDetalles().getDetalle()) {

					//cantidadunidades += Integer.valueOf(detail.getCantidadUnidades());
					numprodripley = detail.getCodProdCliente().trim();
					provcasepack = detail.getCodProdProveedor().trim();
					detallebulto = objFactory.createDOCBULTOSDEMODIFACTURAGUIABULTODEMODIDETALLEBULTODEMODI();
					detallebulto.setNUMPRODRIPLEY(numprodripley);
					detallebulto.setPROVCASEPACK(provcasepack);
					//detallebulto.setCANTIDAD(Long.valueOf(cantidadunidades));
					detallebulto.setCANTIDAD(Long.valueOf(detail.getCantidadUnidades()));
					bultodemodi.getDETALLEBULTODEMODI().add(detallebulto);

				}
				factguia.getBULTODEMODI().add(bultodemodi);
			}
			docbultos.getFACTURAGUIA().add(factguia);
		}

		finalPath = folderpath + filename;

		JAXBContext jc;
		try {

			jc = getJC_Ripley();
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(docbultos, sw);
			String result = sw.toString();//TODO fix
			result = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"no\"?>\n"
					+ "<!DOCTYPE DOC_BULTOS_DEM_ODI SYSTEM \"dtd/odi_asignada.dtd\">\n" + result;
			// System.out.println(result);
			// convert to file
			result = result.replace("\n","\r\n");
			java.io.FileWriter fw = new java.io.FileWriter(finalPath);
			fw.write(result);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode("-1");
			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		// creacion segundo archivo
		ArrayList<String> arr = new ArrayList<String>();
		filename = "OPC" + "E-Fact_Loreal" + "_" + message.getNroCita().trim() + "_" + sdf.format(new Date()) + ".txt";

		for (HeaderDTO headerDTO : keys) {
			for (DocTributario doc : message.getDocsTributario().getDocTributario()) {
				
				if (!headerDTO.getNodoc().equals(doc.getNoDocTributario().trim())) {
					continue;
				}
				
				String format = "0000000000";
				String odiformat = "00000000";
				int docsize = 10 - doc.getNoDocTributario().trim().length();
				int odisize = 8 - headerDTO.getNoc().length();
				arr.add("RUT79693930-3");// fijo
				arr.add(doc.getNumref1().trim());// Prefijo "DOC" + Tipo documento
											// (EFAC si es factura electrónica,
											// de lo contrario es FAC)
				arr.add(docsize > 0 ? "NUM" + format.substring(0, docsize) + doc.getNoDocTributario().trim()
						: "NUM" + doc.getNoDocTributario().trim());// se reemplazan la
															// cantidad de 0
															// correspondientes
															// por el número
															// legal de la
															// factura
				arr.add(odisize > 0 ? "ODI" + odiformat.substring(0, odisize) + headerDTO.getNoc()
						: "ODI" + headerDTO.getNoc());// se reemplazan la cantidad
													// de 0 correspondientes por
													// el número de OC
				arr.add(doc.getFechaDocTributario().trim());
				arr.add(doc.getNetoDocTributario().trim()); // se reemplazan la
														// cantidad de 0
														// correspondientes por
														// el Valor Neto.
				arr.add("%PP00.00");
				arr.add("%CO00.00");
				arr.add("%VO00.00");
				arr.add("%SP00.00");
				arr.add("%AD00.00");
				arr.add("$PP0000000000");
				arr.add("$CO0000000000");
				arr.add("$VO0000000000");
				arr.add("$SP0000000000");
				arr.add("$AD0000000000");
				arr.add(doc.getIvaDocTributario().trim());
				arr.add(doc.getTotalDocTributario().trim());
				// Detalle

				for (DetalleDocTributario docdetail : doc.getDetallesDocTributario().getDetalleDocTributario()) {

					arr.add(docdetail.getCodProdProveedor().trim());// , RS20 se
																// reemplaza por
																// 12 tabulares
																// más el código
																// SAP del
																// material
					arr.add(docdetail.getCodProdCliente().trim());// RS8 se remplaza
															// por el código
															// interno del
															// producto que
															// viene en la
															// información
															// complementaria
					arr.add(docdetail.getCantidad().trim()); // se reemplazan la
														// cantidad de 0
														// correspondientes por
														// la cantidad de
														// productos obtenida
														// desde
														// TDF_DetalleFacturaNCredito.DFN_Cantidad
					arr.add(docdetail.getCostoUnitario().trim());// , 00000000.######
															// se reemplaza por
															// el precio
															// unitario del
															// producto//
															// revisar
					arr.add(docdetail.getCostoFinal().trim()); // , se reemplazan la
														// cantidad de 0
														// correspondientes por
														// el total de ese
														// material (Cant *
														// precio Uni)
					arr.add(docdetail.getDescProducto().trim()); // se reemplazan la
															// cantidad de 0
															// correspondientes
															// por Valor total
															// del descuento del
															// material
															// revisar
					arr.add(docdetail.getPorcDescto().trim()); // se reemplazan la
														// cantidad de 0
														// correspondientes por
														// Valor el Porcentaje
														// de descuento del
														// material
					arr.add("FIA");
				}
				// final
				arr.add("FID");

			}
		}
		arr.add("FIT");
		finalPath = folderpath + filename;
		try {
			FileWriter writer = new FileWriter(finalPath);
			for (String str : arr) {
				writer.write(str + System.lineSeparator());
				// System.out.println(str);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode("-1");
			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		// }
		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? filename : "");
		return plresult;
	}

	public PLFileDTO doCreateFileWalmart(PLProveedor message, String folderpath) {

		PLFileDTO result = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String statusCode = "0";
		String statusMessage = "";

		List<String> files = new ArrayList<String>();
		DataRow row = null;
		String finalPath = "";

		// mapa agrupa por ean13
		Map<String, String> map = new HashMap<String, String>();
		for (Bulto item : message.getBultos().getBulto()) {
			for (Detalle detail : item.getDetalles().getDetalle()) {
				if (!map.containsKey(detail.getNoc().trim())) {
					map.put(detail.getEan13().trim(), detail.getNoc().trim());
				}
			}
		}

		// Calculo cantidad cajas por ean13
		Map<String, Integer> mapCajas = new HashMap<String, Integer>();
		for (Map.Entry<String, String> entry : map.entrySet()) {

			for (Bulto item : message.getBultos().getBulto()) {
				for (Detalle detail : item.getDetalles().getDetalle()) {
					if (entry.getKey().trim().equals(detail.getEan13().trim()) && entry.getValue().trim().equals(detail.getNoc().trim())) {
						// cantidad cajas
						if (!mapCajas.containsKey(detail.getEan13().trim())) {
							mapCajas.put(detail.getEan13().trim(), Integer.valueOf(detail.getCantidadCajas().trim()));
						} else {
							int cantidad = mapCajas.get(detail.getEan13().trim());
							mapCajas.put(detail.getEan13().trim(), cantidad + Integer.valueOf(detail.getCantidadCajas().trim()));

						}
					}
				}
			}
		}

		String filename = "PL_" + message.getNroCita() + "_" + sdf.format(new Date()) + "_" + ".xls";
		DataTable dt0 = new DataTable("PL");
		dt0.addColumn(new DataColumn("oc", "OC", String.class));
		dt0.addColumn(new DataColumn("ean13", "EAN13", String.class));
		dt0.addColumn(new DataColumn("item", "ITEM", String.class));
		dt0.addColumn(new DataColumn("dun14", "DUN14", String.class));
		dt0.addColumn(new DataColumn("cajasfacturadas", "Cajas Facturadas", Integer.class));
		dt0.addColumn(new DataColumn("pesofacturado", "Peso Facturado", Double.class));
		dt0.addColumn(new DataColumn("saldooc", "Saldo OC", Double.class));
		dt0.addColumn(new DataColumn("descripcion", "DESCRIPCION", String.class));

		for (Map.Entry<String, String> entry : map.entrySet()) {

			for (Bulto item : message.getBultos().getBulto()) {
				for (Detalle detail : item.getDetalles().getDetalle()) {
					// detalle

					if (detail.getNoc().trim().equals(entry.getValue().trim()) && detail.getEan13().trim().equals(entry.getKey().trim())) {

						row = dt0.newRow();
						row.setCellValue("oc", Long.valueOf(detail.getNoc().trim()), Long.class);
						row.setCellValue("ean13", detail.getEan13().trim(), String.class);
						row.setCellValue("item", Long.valueOf(detail.getCodProdCliente().trim()), Long.class);
						row.setCellValue("dun14", Long.valueOf(detail.getDun14().trim()), Long.class);
						row.setCellValue("cajasfacturadas", mapCajas.get(detail.getEan13().trim()));

						for (Entrega entrega : message.getEntregas().getEntrega()) {
							if (detail.getEntrega().trim().equals(entrega.getNumero().trim())) {
								for (bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Entregas.Entrega.Detalles.Detalle entdetail : entrega
										.getDetalles().getDetalle()) {
									if (entdetail.getCodProdProveedor().trim().equals(detail.getCodProdProveedor().trim())) {
										row.setCellValue("pesofacturado", Double.valueOf(entdetail.getPeso().trim()));
										row.setCellValue("saldooc", Double.valueOf(entdetail.getSaldo().trim()));
									}
								}
							}
						}
						row.setCellValue("descripcion", detail.getDescripcionProd().trim());
						dt0.addRow(row);
						map.put(detail.getEan13(), "");
					}
				}
			}
		}

		XLSConverterPOI converter = new XLSConverterPOI();
		converter.setExcelheaderbold(true);
		converter.setShowexceltableborder(true);
		finalPath = folderpath + filename;
		try {
			converter.ExportToXLS(dt0, null, finalPath, Charset.forName("UTF-16"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita());
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita();
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode("-1");
			er.setStatusmessage("No fue posible generar archivo de Packing list " + message.getNroCita());
			errores.add(er);
		} finally {
			files.add(filename);
		}

		result.setStatuscode(statusCode);
		result.setStatusmessage(statusMessage);
		result.setFiles(files);
		result.setErrores(errores);
		result.setFilename(files.size() == 1 ? filename : "");
		return result;
	}
	
//	private CellStyle getHeaderCellStyle() {
//		Workbook workbook = new HSSFWorkbook();
//		CellStyle cs_header = workbook.createCellStyle();
//		cs_header.setAlignment(CellStyle.ALIGN_CENTER);
//		cs_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		cs_header.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		cs_header.setBorderTop(CellStyle.BORDER_THIN);
//		cs_header.setBorderBottom(CellStyle.BORDER_THIN);
//		cs_header.setBorderLeft(CellStyle.BORDER_THIN);
//		cs_header.setBorderRight(CellStyle.BORDER_THIN);
//		Font f_header = workbook.createFont();
//		f_header.setFontHeightInPoints((short) 10);
//		f_header.setBoldweight(Font.BOLDWEIGHT_BOLD);
//		cs_header.setFont(f_header);
//		return cs_header;
//	}
	
	public void new_file_with_text(String text, String fname) {
	    File f = null;
	    try {
	        f = new File(fname);
	        f.createNewFile();
	        //System.out.println(text);           
	        PrintWriter out = new PrintWriter(f, "UTF-8");
	        try {
	            out.print(text);
	        } finally {
	            out.close();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

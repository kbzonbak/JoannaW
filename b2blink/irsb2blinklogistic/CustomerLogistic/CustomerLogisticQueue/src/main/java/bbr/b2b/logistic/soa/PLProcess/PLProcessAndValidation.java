package bbr.b2b.logistic.soa.PLProcess;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.Bultos.Bulto.Detalles.Detalle;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario;
import bbr.b2b.b2blink.logistic.xml.PL_Proveedor.PLProveedor.DocsTributario.DocTributario;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.customer.data.dto.PLFileDTO;
import bbr.b2b.logistic.customer.data.dto.PLKeyDTO;
import bbr.common.dataset.util.CSVConverter;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataColumnStyle;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

public class PLProcessAndValidation implements PLProcessAndValidationLocal {

	private static Logger logger = Logger.getLogger("SOALog");

	public List<BaseResultDTO> doValidateHites(PLProveedor message) {

		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		// validaciones generales
		if (message.getNroCita() == null || message.getNroCita().equals("")) {
			erroresString.add("Header: El nro de cita es obligatorio");
		}
		if (message.getCodProveedor() == null || message.getCodProveedor().equals("")) {
			erroresString.add("Header: El Código de proveedor es obligatorio");
		}
		try {
			Long.valueOf(message.getNroCita());
		} catch (Exception e) {
			erroresString.add("Cita " + message.getNroCita() + " Error de formato");
		}

		int rownumber = 0;
		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				rownumber += 1;

				if (detalle.getNoc() == null || detalle.getNoc().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de orden es obligatorio");
				}

				if (bulto.getCodLocalDestino() == null && bulto.getCodLocalDestino().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El código de local de destino es obligatorio");
				}

				if (bulto.getNroBulto() == null && bulto.getNroBulto().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de bulto es obligatorio");
				}

				if (detalle.getCodProdCliente() == null && detalle.getCodProdCliente().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El código de producto es obligatorio");
				}

				if (detalle.getCantidadUnidades() == null && detalle.getCantidadUnidades().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": La cantidad de unidades es obligatoria");
				}

				if (detalle.getNoDocTributario() == null && detalle.getNoDocTributario().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de documento es obligatorio");
				} else {
					// si existe el nro de documento se busca el tipo
					if (!(getDocType(message.getDocsTributario(), detalle.getNoDocTributario())).equals("")) {
						String doctype = getDocType(message.getDocsTributario(), detalle.getNoDocTributario());
						if (doctype.isEmpty()) {
							erroresString.add(
									"Fila " + (rownumber) + ": El tipo de documento es obligatorio para el documento: "
											+ detalle.getNoDocTributario());
						}
					} else {
						erroresString.add("Fila " + (rownumber) + ": No existe documento asociado a la orden: "
								+ detalle.getNoc());
					}
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

	public List<BaseResultDTO> doValidateSuperCL(PLProveedor message) throws OperationFailedException {
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita() == null || message.getNroCita().equals("")) {
			erroresString.add("Header : El número de cita es obligatorio");
		}

		if (message.getCodProveedor() == null || message.getCodProveedor().equals("")) {
			erroresString.add("Header: El Código de proveedor es obligatorio ");
		}

		try {
			Long.valueOf(message.getNroCita());
		} catch (Exception e) {
			erroresString.add("Cita " + message.getNroCita() + " Error de formato");
		}

		int rownumber = 0;
		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				rownumber += 1;

				String entregaoc = "Entrega: " + (detalle.getEntrega() != null ? detalle.getEntrega() : "")
						+ " Nro OC: " + (detalle.getNoc() != null ? detalle.getNoc() : "");
				if (detalle.getNoc() == null || detalle.getNoc().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de orden es obligatorio, Entrega: "
							+ detalle.getEntrega() != null ? detalle.getEntrega() : "");
				}
				if (detalle.getNoDocTributario() == null || detalle.getNoDocTributario().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de documento es obligatorio, " + entregaoc);
				}
				if (bulto.getCodLocalDestino() == null || bulto.getCodLocalDestino().isEmpty()) {
					erroresString.add(
							"Fila " + (rownumber) + ": El código de local de destino es obligatorio, " + entregaoc);
				}
				if (detalle.getCodProdCliente() == null || detalle.getCodProdCliente().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El código de producto es obligatorio, " + entregaoc);
				}
				if (detalle.getCantidadUnidades() == null || detalle.getCantidadUnidades().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": La cantidad de unidades es obligatoria, " + entregaoc);
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

	public List<BaseResultDTO> doValidateLaPolar(PLProveedor message) {

		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		// validaciones generales
		if (message.getNroCita() == null || message.getNroCita().equals("")) {
			erroresString.add("Header: El nro de cita es obligatorio");
		}
		if (message.getCodProveedor() == null || message.getCodProveedor().equals("")) {
			erroresString.add("Header: El Código de proveedor es obligatorio");
		}
		try {
			Long.valueOf(message.getNroCita());
		} catch (Exception e) {
			erroresString.add("Cita " + message.getNroCita() + " Error de formato");
		}
		boolean isVeV = message.getNumref1() != null && !message.getNumref1().isEmpty();

		int rownumber = 0;
		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				rownumber += 1;

				if (detalle.getNoc() == null || detalle.getNoc().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de orden es obligatorio");
				}

				if (detalle.getTipoNoc() == null || detalle.getTipoNoc().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El tipo de orden es obligatorio");
				}

				if (bulto.getCodLocalDestino() == null && bulto.getCodLocalDestino().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El código de local de destino es obligatorio");
				}

				if (bulto.getDescripcionLocalDestino() == null && bulto.getDescripcionLocalDestino().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": La descripción de local de destino es obligatorio");
				}

				if (detalle.getCodProdCliente() == null && detalle.getCodProdCliente().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": Item PLU es obligatorio");
				}

				if (detalle.getDescripcionProd() == null && detalle.getDescripcionProd().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": La descripción del item es obligatoria");
				}

				if (bulto.getNroBulto() == null && bulto.getNroBulto().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El número de bulto es obligatorio");
				}

				if (detalle.getCantidadUnidades() == null && detalle.getCantidadUnidades().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": La cantidad de unidades es obligatoria");
				}

				if (isVeV) {
					if (detalle.getNoDocTributario() == null && detalle.getNoDocTributario().isEmpty()) {
						erroresString.add("Fila " + (rownumber) + ": El número de documento es obligatorio");
					}
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

	public List<BaseResultDTO> doValidateParisCL(PLProveedor message) {

		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		List<String> erroresString = new ArrayList<String>();

		if (message.getNroCita() == null || message.getNroCita().equals("")) {
			erroresString.add("Header : El número de cita es obligatorio");
		}

		if (message.getVendedor().getCodigo() == null || message.getVendedor().getCodigo().equals("")) {
			erroresString.add("Vendedor: El Código de proveedor es obligatorio ");
		}

		try {
			Long.valueOf(message.getNroCita());
		} catch (Exception e) {
			erroresString.add("Cita " + message.getNroCita() + " Error de formato");
		}

		int rownumber = 0;
		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			if ((bulto.getNroBulto() == null && bulto.getNroBulto().isEmpty())
					|| (bulto.getNroPallet() == null && bulto.getNroPallet().isEmpty())) {
				erroresString.add("Bulto " + (i) + ": Debe ingresar NROBULTO o NOPALLET");
			}
			if (bulto.getCodLocalDestino() == null || bulto.getCodLocalDestino().isEmpty()) {
				erroresString.add("Bulto " + (i) + ": El código de local es obligatorio");
			}

			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				rownumber += 1;

				if (detalle.getNoc() == null && detalle.getNoc().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El N°mero de orden es obligatorio");
				}
				
				if (detalle.getCodProdCliente() == null && detalle.getCodProdCliente().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": El SKU (COD_PROD_CTE) es obligatorio");
				}
				if (detalle.getCantidadUnidades() == null && detalle.getCantidadUnidades().isEmpty()) {
					erroresString.add("Fila " + (rownumber) + ": N°mero de unidades es obligatorio");
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

	public PLFileDTO doCreateFileHites(PLProveedor message, String folderpath, Long ticketNumber)
			throws LoadDataException, OperationFailedException {

		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		String statusCode = "0";
		String statusMessage = "";
		String finalPath = "";

		// construccion del excel
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
		String realfilename = "PL_HITES_" + message.getNroCita().trim() + "_" + dateFormat.format(new Date()) + " hrs.xls";
		DataRow row = null;

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("Detalle Packing List");

		// DataColumn col01 = new DataColumn("rownumber", Integer.class);
		DataColumn col01 = new DataColumn("ordernumber", "Número Orden", Long.class);
		DataColumn col02 = new DataColumn("locationcode", "Local Destino", String.class);
		DataColumn col03 = new DataColumn("itemcode", "Código Hites", String.class);
		DataColumn col04 = new DataColumn("lpnnumber", "LPN", String.class);
		DataColumn col05 = new DataColumn("units", "Cantidad", Double.class);
		DataColumn col06 = new DataColumn("documentnumber", "Número Documento", String.class);
		DataColumn col07 = new DataColumn("documenttype", "Tipo Documento", String.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.setShowcolumnheaders(true);

		List<String> files = new ArrayList<String>();

		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				row = dt0.newRow();
				row.setCellValue("ordernumber", Long.parseLong(detalle.getNoc().trim()));
				row.setCellValue("locationcode", bulto.getCodLocalDestino().trim());
				row.setCellValue("itemcode", detalle.getCodProdCliente().trim());
				row.setCellValue("lpnnumber", bulto.getNroBulto().trim());
				row.setCellValue("units", Double.valueOf(detalle.getCantidadUnidades().trim()));

				if (detalle.getNoDocTributario() != null && !detalle.getNoDocTributario().isEmpty()) {
					row.setCellValue("documentnumber", detalle.getNoDocTributario().trim());
					// si existe el nro de documento se busca el tipo
					if (!(getDocType(message.getDocsTributario(), detalle.getNoDocTributario().trim())).equals("")) {
						String doctype = getDocType(message.getDocsTributario(), detalle.getNoDocTributario().trim());
						row.setCellValue("documenttype", doctype);
					}
				}
				dt0.addRow(row);
			}
		}

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		DataColumnStyle style1 = new DataColumnStyle("decimal", df, 20);

		DataColumnStyleInfo info = new DataColumnStyleInfo();
		info.addDataColumnStyles(style1, col06);

		// Crear Archivo xls
		finalPath = folderpath + realfilename;
		XLSConverterPOI converter = new XLSConverterPOI();
		try {
			converter.ExportToXLS(dt0, finalPath, Charset.forName("ISO-8859-1"));
			logger.info("Archivo " + finalPath + " se ha generado correctamente.");

		} catch (IOException e) {

			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita().trim() + "Ticket : "
					+ ticketNumber);
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita().trim() + "Ticket : "
					+ ticketNumber;
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode(statusCode);
			er.setStatusmessage(statusMessage);
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? realfilename : "");
		return plresult;

	}

	public PLFileDTO doCreateFileSuperCL(PLProveedor message, String folderpath, Long ticketNumber)
			throws LoadDataException, OperationFailedException {

		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		String statusCode = "0";
		String statusMessage = "";
		String finalPath = "";

		Map<PLKeyDTO, Integer> itemMap = new HashMap<PLKeyDTO, Integer>();

		for (Bulto bulto : message.getBultos().getBulto()) {
			for (Detalle detail : bulto.getDetalles().getDetalle()) {

				PLKeyDTO key = new PLKeyDTO();
				key.setLocationcode(bulto.getCodLocalDestino().trim());
				key.setOcnumber(detail.getNoc().trim());
				key.setSku(detail.getCodProdCliente().trim());
				key.setNumfact(detail.getNoDocTributario().trim());

				if (!itemMap.containsKey(key)) {
					itemMap.put(key, Integer.valueOf(detail.getCantidadCajas().trim()));
				} else {
					int cantidad = itemMap.get(key);
					itemMap.put(key, cantidad + Integer.valueOf(detail.getCantidadCajas().trim()));
				}
			}
		}

		// construccion del excel
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
		String realfilename = "PL_SUPERCL_" + message.getNroCita().trim() + "_" + dateFormat.format(new Date()) + " hrs.xls";

		DataRow row = null;

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("Detalle Packing List");

		// rownumber, ordernumber, documentnumber, lpnnumber, locationcode,
		// itemcode, units, batchnumber, expiration, cost
		DataColumn col01 = new DataColumn("ocnumber", "Numero de OC", Long.class);
		DataColumn col02 = new DataColumn("documentnumber", "Numero de Factura", Long.class);
		DataColumn col03 = new DataColumn("lpn", "HU", String.class);
		DataColumn col04 = new DataColumn("locationcode", "Local de Destino", String.class);
		DataColumn col05 = new DataColumn("itemcode", "Cod. Producto", String.class);
		DataColumn col06 = new DataColumn("units", "Cantidad", Double.class);
		DataColumn col07 = new DataColumn("batchnumber", "Numero de Lote", String.class);
		DataColumn col08 = new DataColumn("expiration", "Fecha de Vencimiento", String.class);
		DataColumn col09 = new DataColumn("cost", "Costo", String.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);

		dt0.setShowcolumnheaders(true);
		List<String> files = new ArrayList<String>();
		for (Map.Entry<PLKeyDTO, Integer> entry : itemMap.entrySet()) {
			row = dt0.newRow();
			row.setCellValue("ocnumber", Long.parseLong(entry.getKey().getOcnumber()));
			row.setCellValue("documentnumber", Long.valueOf(entry.getKey().getNumfact()));
			row.setCellValue("lpn", "");
			row.setCellValue("locationcode", entry.getKey().getLocationcode());
			row.setCellValue("itemcode", entry.getKey().getSku());
			row.setCellValue("units", Double.valueOf(entry.getValue()));
			row.setCellValue("batchnumber", "");
			row.setCellValue("expiration", "");
			row.setCellValue("cost", "");

			dt0.addRow(row);
		}

//		DecimalFormat df = new DecimalFormat();
//		df.setMaximumFractionDigits(0);
//		DataColumnStyle style1 = new DataColumnStyle("decimal", df, 15);
//
//		DataColumnStyleInfo info = new DataColumnStyleInfo();
//		info.addDataColumnStyles(style1, col09);
		
		// Crear Archivo xls
		finalPath = folderpath + realfilename;
		XLSConverterPOI converter = new XLSConverterPOI();
		try {
			converter.ExportToXLS(dt0, finalPath, Charset.forName("ISO-8859-1"));
			logger.info("Archivo " + finalPath + " se ha generado correctamente.");
		} catch (IOException e) {

			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita().trim() + "Ticket : "
					+ ticketNumber);
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita().trim() + "Ticket : "
					+ ticketNumber;
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode(statusCode);
			er.setStatusmessage(statusMessage);
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? realfilename : "");
		return plresult;
	}

	//
	public PLFileDTO doCreateFileLaPolar(PLProveedor message, String folderpath, Long ticketNumber)
			throws LoadDataException, OperationFailedException {

		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		String statusCode = "0";
		String statusMessage = "";
		String finalPath = "";

		boolean isVeV = message.getNumref1() != null && !message.getNumref1().isEmpty();
		List<String> files = new ArrayList<String>();
		// construccion del excel
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
		String realfilename = "PL_LAPOLAR_" + message.getNroCita().trim() + "_" + dateFormat.format(new Date()) + " hrs.xls";

		DataRow row = null;
		DataTable dt0 = new DataTable("PACKINGLIST");

		DataColumn col01 = new DataColumn("nroorden", "Nro. Orden", Long.class);
		DataColumn col02 = new DataColumn("ordertype", "Tipo de Orden", String.class);
		DataColumn col03 = new DataColumn("locationcode", "Cód. Local", String.class);
		DataColumn col04 = new DataColumn("locationname", "Local", String.class);
		DataColumn col05 = new DataColumn("itemplu", "PLU", String.class);
		DataColumn col06 = new DataColumn("itemstyle", "Estilo", String.class);
		DataColumn col07 = new DataColumn("itemdescription", "Descripción", String.class);
		DataColumn col08 = new DataColumn("units", "Cantidad", Long.class);
		DataColumn col09 = new DataColumn("bulklabel", "Etiqueta", String.class);
		DataColumn col10 = new DataColumn("palletlabel", "Etiqueta Pallet", String.class);
		DataColumn col11 = null;
		DataColumn col12 = null;
		if (isVeV) {
			col11 = new DataColumn("ndnumber", "Nro ND", String.class);
			col12 = new DataColumn("taxdocument", "Nro Factura", String.class);
		}

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);
		dt0.addColumn(col10);
		if (isVeV) {
			dt0.addColumn(col11);
			dt0.addColumn(col12);
		}

		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				row = dt0.newRow();

				row.setCellValue("nroorden", Long.valueOf(detalle.getNoc().trim()));
				row.setCellValue("ordertype", detalle.getTipoNoc().trim());
				row.setCellValue("locationcode", bulto.getCodLocalDestino().trim());
				row.setCellValue("locationname", bulto.getDescripcionLocalDestino() != null ? bulto.getDescripcionLocalDestino().trim() : "");
				row.setCellValue("itemplu", detalle.getCodProdCliente().trim());
				row.setCellValue("itemstyle", "");
				row.setCellValue("itemdescription", detalle.getDescripcionProd().trim());
				row.setCellValue("units", Long.valueOf(detalle.getCantidadUnidades().trim()));
				row.setCellValue("bulklabel", bulto.getNroBulto().trim());
				row.setCellValue("palletlabel", "-1");
				if (isVeV) {
					row.setCellValue("ndnumber", message.getNumref1().trim());
					row.setCellValue("taxdocument",
							(detalle.getNoDocTributario() != null && !detalle.getNoDocTributario().isEmpty())
									? detalle.getNoDocTributario() : "");
				}
				dt0.addRow(row);
			}
		}

		// ESTILOS
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		DataColumnStyle style1 = new DataColumnStyle("decimal", df, 20);

		DataColumnStyleInfo info = new DataColumnStyleInfo();
		info.addDataColumnStyles(style1, col08);

		// CREAR ARCHIVO
		XLSConverterPOI converter = new XLSConverterPOI();
		converter.setExcelheaderbold(true);
		converter.setShowexceltableborder(true);

		finalPath = folderpath + realfilename;
		try {
			converter.ExportToXLS(dt0, finalPath, Charset.forName("ISO-8859-1"));
		} catch (IOException e) {

			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita() + "Ticket : "
					+ ticketNumber);
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita() + "Ticket : "
					+ ticketNumber;
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode(statusCode);
			er.setStatusmessage(statusMessage);
			errores.add(er);
		} finally {
			files.add(finalPath);
		}
		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? realfilename : "");
		return plresult;
	}

	public PLFileDTO doCreateFileParisCL(PLProveedor message, String folderpath, Long ticketNumber)
			throws LoadDataException, OperationFailedException {

		PLFileDTO plresult = new PLFileDTO();
		List<BaseResultDTO> errores = new ArrayList<BaseResultDTO>();
		String statusCode = "0";
		String statusMessage = "";
		String finalPath = "";

		// construccion del excel
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
		String realfilename = "PL_PARISCL_" + message.getNroCita() + "_" + dateFormat.format(new Date()) + " hrs.xls";
		DataRow row = null;

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("Detalle Packing List");

		DataColumn col01 = new DataColumn("flowtype", "TIPO FLUJO", String.class);
		DataColumn col02 = new DataColumn("bulkumber", "ID BULTO", String.class);
		DataColumn col03 = new DataColumn("palletnumber", "ID PALLET", String.class);
		DataColumn col04 = new DataColumn("itemdescrition", "DESC. PRODUCTO", String.class);
		DataColumn col05 = new DataColumn("locationcode", "COD. LOCAL", String.class);
		DataColumn col06 = new DataColumn("departmentcode", "COD. DEPTO.", String.class);
		DataColumn col07 = new DataColumn("department", "DEPTO.", String.class);
		DataColumn col08 = new DataColumn("locationdescription", "DESC. LOCAL", String.class);
		DataColumn col09 = new DataColumn("clientcode", "COD. PARIS", String.class);
		DataColumn col10 = new DataColumn("provcode", "COD. PROV.", String.class);
		DataColumn col11 = new DataColumn("ordernumber", "No. ORDEN", Long.class);
		DataColumn col12 = new DataColumn("quantity", "CANTIDAD", Double.class);
		DataColumn col13 = new DataColumn("taxnumber", "No. DOC. TRIBUTARIO", Long.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);
		dt0.addColumn(col10);
		dt0.addColumn(col11);
		dt0.addColumn(col12);
		dt0.addColumn(col13);
		dt0.setShowcolumnheaders(true);

		List<String> files = new ArrayList<String>();

		for (int i = 0; i < message.getBultos().getBulto().size(); i++) {
			Bulto bulto = message.getBultos().getBulto().get(i);
			for (Detalle detalle : bulto.getDetalles().getDetalle()) {
				row = dt0.newRow();

				row.setCellValue("flowtype", ""); // TODO ??
				row.setCellValue("bulkumber", bulto.getNroBulto().trim());
				row.setCellValue("palletnumber", bulto.getNroPallet().trim());
				row.setCellValue("itemdescrition", detalle.getDescripcionProd() != null ? detalle.getDescripcionProd().trim() : "");
				row.setCellValue("locationcode", bulto.getCodLocalDestino().trim());
				row.setCellValue("departmentcode", ""); // TODO ??
				row.setCellValue("department", ""); // TODO ??
				row.setCellValue("locationdescription", bulto.getDescripcionLocalDestino() != null ? bulto.getDescripcionLocalDestino().trim() : "");
				row.setCellValue("clientcode", detalle.getCodProdCliente().trim());
				row.setCellValue("provcode", detalle.getCodProdProveedor() != null ? detalle.getCodProdProveedor().trim() : "");
				row.setCellValue("ordernumber", Long.parseLong(detalle.getNoc()));
				row.setCellValue("quantity", Double.valueOf(detalle.getCantidadUnidades().trim()));
				row.setCellValue("taxnumber", detalle.getNoDocTributario() != null ? Long.parseLong(detalle.getNoDocTributario().trim()): "");
				dt0.addRow(row);
			}
		}
		

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		DataColumnStyle style1 = new DataColumnStyle("decimal", df, 20);

		DataColumnStyleInfo info = new DataColumnStyleInfo();
		info.addDataColumnStyles(style1, col11, col12, col13);

		// Crear Archivo xls
		finalPath = folderpath + realfilename;
		XLSConverterPOI converter = new XLSConverterPOI();
		try {
			converter.ExportToXLS(dt0, finalPath, Charset.forName("ISO-8859-1"));
			logger.info("Archivo " + finalPath + " se ha generado correctamente.");

		} catch (IOException e) {

			e.printStackTrace();
			logger.error("No fue posible generar archivo de Packing list " + message.getNroCita() + "Ticket : "
					+ ticketNumber);
			statusCode = "-1";
			statusMessage = "No fue posible generar archivo de Packing list " + message.getNroCita() + "Ticket : "
					+ ticketNumber;
			BaseResultDTO er = new BaseResultDTO();
			er.setStatuscode(statusCode);
			er.setStatusmessage(statusMessage);
			errores.add(er);
		} finally {
			files.add(finalPath);
		}

		plresult.setStatuscode(statusCode);
		plresult.setStatusmessage(statusMessage);
		plresult.setFiles(files);
		plresult.setErrores(errores);
		plresult.setFilename(files.size() == 1 ? realfilename : "");
		return plresult;

	}

	private String getDocType(DocsTributario docs, String docnumber) {

		String tipodoc = "";
		for (DocTributario doc : docs.getDocTributario()) {
			if (docnumber.equals(doc.getNoDocTributario())) {
				return doc.getTipoDocTributario();
			}
		}
		return tipodoc;
	}

}

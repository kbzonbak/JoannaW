
package bbr.b2b.regional.logistic.datings.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.datings.entities.Dating;
import bbr.b2b.regional.logistic.datings.report.classes.DailyReportDatesDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.utils.RuntimeProcessUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/datings/DatingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingServer extends LogisticElementServer<Dating, DatingW> implements DatingServerLocal {

	private Map<String, String> mapGetImportedContainerDatingSQL = new HashMap<String, String>();
	{
		mapGetImportedContainerDatingSQL.put("DATINGDATE", "datingdate");		
		mapGetImportedContainerDatingSQL.put("DOCKCODE", "doc.code");
		mapGetImportedContainerDatingSQL.put("DATINGTIME", "datingtime");
		mapGetImportedContainerDatingSQL.put("DATINGINTERVAL", "re.datinginterval");
		mapGetImportedContainerDatingSQL.put("DELIVERYLOCATIONCODE", "lo.code");
		mapGetImportedContainerDatingSQL.put("DELIVERYLOCATIONNAME", "lo.name");
		mapGetImportedContainerDatingSQL.put("FLOWTYPECODE", "ft.code");
		mapGetImportedContainerDatingSQL.put("FLOWTYPENAME", "ft.name");
	}	

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	LocationServerLocal locationserver;

	public DatingW addDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW) addElement(dating);
	}
	public DatingW[] getDatings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW[]) getIdentifiables();
	}
	public DatingW updateDating(DatingW dating) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingW) updateElement(dating);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Dating entity, DatingW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDeliveryid(entity.getDelivery() != null ? new Long(entity.getDelivery().getId()) : new Long(0));
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Dating entity, DatingW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) { 
			Delivery delivery = deliveryserver.getReferenceById(wrapper.getDeliveryid());
			if (delivery != null) { 
				entity.setDelivery(delivery);
			}
		}
		
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Dating";
	}
	@Override
	protected void setEntityname() {
		entityname = "Dating";
	}
	
	public String getFormattedDatingDateTime(Long datingid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Dating.getFormattedDatingDateTime");
		query.setLong("datingid", datingid);
		String result = (String)query.uniqueResult();
        return result;
	}
	
	public DatingDataDTO[] getDatingDataByDateAndLocation(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Dating.getDatingDataByDateAndLocation");
		query.setDate("since", since);
		query.setDate("until", until);
		query.setLong("locationid", locationid.longValue());
		query.setResultTransformer(new LowerCaseResultTransformer(DatingDataDTO.class));
		List list = query.list();
		DatingDataDTO[] result = (DatingDataDTO[]) list.toArray(new DatingDataDTO[list.size()]);
		return result;
	}
	
	public Long getNextSequenceDatingNumber() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.DATINGNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
	
	public ExcelDatingReportDataDTO[] getExcelDatingReport(Date since, Date until, Long locationid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.Dating.getExcelDatingReport");
		query.setTimestamp("since", since);
		query.setTimestamp("until", until);
		query.setLong("locationid", locationid);
		query.setResultTransformer(new LowerCaseResultTransformer(ExcelDatingReportDataDTO.class));
		List list = query.list();
		return (ExcelDatingReportDataDTO[]) list.toArray(new ExcelDatingReportDataDTO[list.size()]);
	}
	
	public FileDownloadInfoResultDTO getCSVDatingToDeliveryReport(Long locationid, Long userID) throws OperationFailedException, NotFoundException{
		
		// formato hora postgres
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calvalid = Calendar.getInstance();
		calvalid.setTime(new Date());
		calvalid.add(Calendar.HOUR_OF_DAY, - 24); //dia anterior
		calvalid.set(Calendar.HOUR_OF_DAY, 23);
		calvalid.set(Calendar.MINUTE, 59);
		calvalid.set(Calendar.SECOND, 59);
		calvalid.set(Calendar.MILLISECOND, 0);
		Date since = calvalid.getTime();

		String sinceStr = df.format(since);
		
		
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

		StringBuffer SQL = new StringBuffer("SELECT  " +
				"to_char(DATE(RE.WHEN1), 'YYYYMMDD') as WHEN1 , " +
				"D.ID as deliveryid,  " +
				"V.NAME as vendorname,  " +
				"V.RUT as vendorrut,  " +
				"O.NUMBER as oc,  " +
				"DEP.NAME as department,  " +
				"L.CODE as localcode,  " +
				"I.INTERNALCODE as sku,  " +
				"case when ODD.AVAILABLEUNITS >= 0 then '+' || trim(TO_CHAR(ODD.AVAILABLEUNITS , '000000000000000.00')) else '-' || trim(TO_CHAR(ODD.AVAILABLEUNITS , '000000000000000.00')) END as units, " +
				"case when ODT.FINALCOST*ODD.AVAILABLEUNITS >= 0 then '+' || trim(TO_CHAR(ODT.FINALCOST*ODD.AVAILABLEUNITS , '000000000000000.00')) else '-' || trim(TO_CHAR(ODT.FINALCOST*ODD.AVAILABLEUNITS , '000000000000000.00')) END as mount, " +
				"FT.NAME as flowtype,  " +
				"DTYPE.NAME as delivertytype " +
				"from LOGISTICA.DELIVERY D  " +
				"INNER JOIN LOGISTICA.DATING DT ON (D.ID = DT.DELIVERY_ID)  " +
				"INNER JOIN LOGISTICA.RESERVE RE ON (DT.ID = RE.ID)  " +
				"INNER JOIN LOGISTICA.VENDOR V ON (V.ID = D.VENDOR_ID)  " +
				"INNER JOIN LOGISTICA.DELIVERYSTATETYPE DST ON (DST.ID = D.CURRENTSTATETYPE_ID)  " +
				"INNER JOIN LOGISTICA.ORDERDELIVERY OD ON (OD.DELIVERY_ID = D.ID)  " +
				"INNER JOIN LOGISTICA.ORDER O ON (O.ID = OD.ORDER_ID)  " +
				"INNER JOIN LOGISTICA.DEPARTMENT DEP ON (DEP.ID = O.DEPARTMENT_ID)  " +
				"INNER JOIN LOGISTICA.ORDERDELIVERYDETAIL ODD ON (ODD.ORDER_ID = OD.ORDER_ID AND ODD.DELIVERY_ID = OD.DELIVERY_ID)  " +
				"INNER JOIN LOGISTICA.ITEM I ON (I.ID = ODD.ITEM_ID)  " +
				"INNER JOIN LOGISTICA.LOCATION L ON (L.ID = ODD.LOCATION_ID)  " +
				"INNER JOIN LOGISTICA.FLOWTYPE FT ON (FT.ID = OD.FLOWTYPE_ID)  " +
				"INNER JOIN LOGISTICA.ORDERDETAIL ODT ON (ODT.ORDER_ID = O.ID AND ODT.ITEM_ID = I.ID)  " +
				"INNER JOIN LOGISTICA.DELIVERYTYPE DTYPE ON (DTYPE.ID = D.DELIVERYTYPE_ID)  " +
				"WHERE RE.LOCATION_id= "+locationid.longValue()+" AND  RE.WHEN1>='"+sinceStr+"' AND (DST.CODE = 'CITA_ASIGNADA' OR DST.CODE = 'AGENDADA')");
		
		
		
		
		



		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL.toString());

//**//
			String realfilename = "ReporteCitasPorEntregar_" + date.getTime() + "_" + userID.longValue()+".csv";

//**//
		
			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();

//**//
			String downloadfilename = "ReporteCitasPorEntregar_" + dateFormat.format(date) + ".zip";


			result.setRealfilename(realfilename.replace(".csv", ".zip"));
			result.setDownloadfilename(downloadfilename);

			// Obtener el comando para generar reporte, usando la metada de conexión de la sesión
			comando1 = session.doReturningWork(new ReturningWork<String>() {
				public String execute(Connection connection) throws SQLException {


					String urlDB = connection.getMetaData().getURL();
					String[] aux1 = urlDB.split(":");
					String dbserver = aux1[2].substring(2); // quita '//'
					String dbname = aux1[3].split("/")[1]; // quita el puerto
					dbname = dbname.split("\\?")[0];

					log.info(dbserver);

					// dbname, dbserver, queryfile, realname
					//String comando = "sh /b2b/shell/fileToDownload/getFileFromDB.sh " + dbname + " " + dbserver + " " + filetemp + " " + filename;
					String comando = "sh /b2b/shell/fileToDownload/getFileFromDB_datingToDelivery.sh " + dbname + " " + dbserver + " " + filetemp + " " + filename;
					log.info(comando);

					return comando;
				}
			});

			/** ******** llamo a shell que mueve archivo desde AS comercial a PORTAL *** */
			// Obtener el comando para mover el archivo de reporte generado
			// lleva como parámetro el nombre del archivo y los titulos del archivo

			comando2 = "sh /b2b/shell/fileToDownload/moveFile_datingToDelivery.sh " + realfilename;
//**//			
			log.info(comando2);

		} catch (HibernateException e) {
			throw new OperationFailedException("Error al obtener la query", e);
		} catch (IOException e) {
			throw new OperationFailedException("Error al escribir el archivo", e);
		}

		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando1);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}		
		
		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando2);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}	


		return result;
		
	}
	
	public FileDownloadInfoResultDTO getCSVDatingReport(Long locationid, Date since, Long userID) throws OperationFailedException, NotFoundException{
		// formato hora postgres
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		
		String todayStr = df.format(since);	
		
		
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

		StringBuffer SQL = new StringBuffer(
				"WITH v_datingstart AS (" + //
							"SELECT DISTINCT " + //
								"r.id AS reserve_id, " + //
								"MIN(m.starts) AS minstarts " + //
							"FROM " + //
								"logistica.reserve AS r JOIN " + //
								"logistica.reservedetail AS rd ON rd.reserve_id = r.id JOIN " + //
								"logistica.module AS m ON m.id = rd.module_id " + //
							"WHERE " + //
								"date(r.when1) = '" + todayStr + "' AND r.location_id = " + locationid.longValue() + //
							"GROUP BY " + //
								"r.id), " + //
					 "d_datsummary AS (" + //
					 		"SELECT DISTINCT " + //
					 			"r.id AS reserve_id, " + //
					 			"od.order_id, " + //
					 			"CASE WHEN v.domestic " + //
					 					"THEN dt.number " + //
					 					"ELSE od.asnimp " + //
					 					"END AS number, " + //
					 			"CASE WHEN v.domestic " + //
					 					"THEN d.refdocument " + //
					 					"ELSE od.refdocument " + //
					 					"END AS refdocument, " + //
					 			"CASE WHEN v.domestic " + //
					 					"THEN d.imp " + //
					 					"ELSE od.imp " + //
					 					"END AS imp " + //
					 		"FROM " + //
					 			"logistica.reserve AS r JOIN " + //
					 			"logistica.dating AS dt ON dt.id = r.id JOIN " + //
					 			"logistica.delivery AS d ON d.id = dt.delivery_id JOIN " + //
					 			"logistica.orderdelivery AS od ON od.delivery_id = d.id JOIN " + //
					 			"logistica.order AS o ON o.id = od.order_id JOIN " + //
					 			"logistica.vendor AS v ON v.id = o.vendor_id " + //
					 		"WHERE "+ //
					 			"date(r.when1) = '" + todayStr + "' AND r.location_id = " + locationid.longValue() + "), " + //
					 "v_datsummary AS (" + //
					 		"SELECT " + //
					 			"r.id AS datingid, " + //
					 			"r.location_id AS locationid, " + //
					 			"r.when1 AS when, " + //
					 			"vd.minstarts, " + //
					 			"dd.number, " + //
					 			"dt.vendor_id AS vendorid, " + //
					 			"o.department_id AS departmentid, " + //
					 			"d.flowtype_id AS flowtypeid, " + //
					 			"d.id AS deliveryid, " + //
					 			"SUM(od.estimatedunits) AS estimatedunits, " + //
					 			"dr.estimatedpallets, " + //
					 			"dr.estimatedboxes, " + //
					 			"d.complementscount, " + //
					 			"d.currentstatetype_id AS deliverystatetypeid, " + //
					 			"dt.confirmed, " + //
					 			"dt.confirmationdate, " + //
					 			"COALESCE(re.id, 0) AS receptionevalid, " + //
					 			"dr.trucks, " + //
					 			"dd.refdocument, " + //
					 			"dd.imp, " + //
					 			"d.container, " + //
					 			"d.deliverytype_id AS deliverytypeid, " + //
					 			"o.ordertype_id AS ordertypeid, " + //
					 			"SUM(od.realbulkcount) AS realbulkcount, " + //
					 			"SUM(od.realpalletcount) AS realpalletcount, " + //
					 			"SUM(od.realpackedunits) AS realpackedunits " + //
					 		"FROM " + //
					 			"logistica.reserve AS r JOIN " + //
					 			"v_datingstart AS vd ON vd.reserve_id = r.id JOIN " + //
					 			"d_datsummary AS dd ON dd.reserve_id = r.id JOIN " + //
					 			"logistica.dating AS dt ON dt.id = r.id JOIN " + //
					 			"logistica.delivery AS d ON d.id = dt.delivery_id JOIN " + //
					 			"logistica.datingrequest AS dr ON dr.delivery_id = d.id JOIN " + //
					 			"logistica.orderdelivery AS od ON od.delivery_id = d.id AND od.order_id = dd.order_id JOIN " + //
					 			"logistica.order AS o ON o.id = od.order_id LEFT JOIN " + //
					 			"logistica.receptionevaluation AS re ON re.dating_id = dt.id " + //
					 		"GROUP BY " + //
					 			"r.when1, r.id, r.location_id, dd.number, dt.confirmed, dt.confirmationdate, dt.vendor_id, d.id, dr.trucks, " + //
					 			"dr.estimatedpallets, dr.estimatedboxes, d.currentstatetype_id, d.complementscount, dd.refdocument, dd.imp, " + //
					 			"d.container, o.department_id, d.flowtype_id, re.id, vd.minstarts, d.deliverytype_id, o.ordertype_id) " + //
					 	
				"SELECT DISTINCT " + //
					"to_char(r1.when, 'DD-MM-YYYY') AS when, " + //
					"ft.name, " + //
					"r1.number, " + //
					"dt.name, " + //
					"dst.name, " + //
					"r1.estimatedunits, " + //
					"r1.estimatedpallets, " + //
					"r1.estimatedboxes, " + //
					"to_char(r1.minstarts, 'HH24:MI') AS minstarts, " + //
					"v.name, " + //
					"dep.code, " + //
					"dep.name, " + //
					"r1.container, " + //
					"r1.refdocument, " + //
					"r1.imp, " + //
					"CAST(r1.realpackedunits AS REAL) AS units, " + //
					"r1.realpalletcount AS palletcount, " + //
					"r1.realbulkcount AS bulkcount, " + //
					"r1.complementscount " + //
				"FROM " + //
					"v_datsummary AS r1 JOIN " + //
					"logistica.deliverytype AS dt ON dt.id = r1.deliverytypeid JOIN " + //
					"logistica.vendor AS v ON v.id = r1.vendorid JOIN " + //
					"logistica.deliverystatetype AS dst ON dst.id = r1.deliverystatetypeid JOIN " + //
					"logistica.flowtype AS ft ON ft.id = r1.flowtypeid JOIN " + //
					"logistica.department AS dep ON dep.id = r1.departmentid"); //
				
		
		
		



		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL.toString());

//**//
			String realfilename = "DATINGREPORT" + date.getTime() + "_" + userID.longValue()+".csv";

//**//
		
			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();

//**//
			String downloadfilename = "ReporteCitas_" + dateFormat.format(date) + ".zip";
//**//

			result.setRealfilename(realfilename.replace(".csv", ".zip"));
			result.setDownloadfilename(downloadfilename);

			// Obtener el comando para generar reporte, usando la metada de conexión de la sesión
			comando1 = session.doReturningWork(new ReturningWork<String>() {
				public String execute(Connection connection) throws SQLException {


					String urlDB = connection.getMetaData().getURL();
					String[] aux1 = urlDB.split(":");
					String dbserver = aux1[2].substring(2); // quita '//'
					String dbname = aux1[3].split("/")[1]; // quita el puerto
					dbname = dbname.split("\\?")[0];

					log.info(dbserver);

					// dbname, dbserver, queryfile, realname
					String comando = "sh /b2b/shell/fileToDownload/getFileFromDB.sh " + dbname + " " + dbserver + " " + filetemp + " " + filename;
					log.info(comando);

					return comando;
				}
			});

			/** ******** llamo a shell que mueve archivo desde AS comercial a PORTAL *** */
			// Obtener el comando para mover el archivo de reporte generado
			// lleva como parámetro el nombre del archivo y los titulos del archivo
		String titulos = "\"Fecha\",\"Tipo_de_Flujo\",\"N_Cita\",\"Tipo_Lote\",\"Estado\",\"Unidades_Aprox\",\"Pallets_Aprox\",\"Bultos_Aprox\",\"Hora_Inicio\",\"Proveedor\",\"Cod_Depto\",\"Departamento\",\"No_Contenedor\",\"No_Guía\",\"No_Importación\",\"Unidades_Reales\",\"Pallets_Reales\",\"Bultos_Reales\",\"No_Complementos\"";

		comando2 = "sh /b2b/shell/fileToDownload/moveFile1.sh " + realfilename + " " + titulos;
//**//			
			log.info(comando2);

		} catch (HibernateException e) {
			throw new OperationFailedException("Error al obtener la query", e);
		} catch (IOException e) {
			throw new OperationFailedException("Error al escribir el archivo", e);
		}

		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando1);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}		
		
		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando2);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}	


		return result;
	}
	
	public DailyReportDatesDTO[] getDailyReportDates(Date since, Date until) throws OperationFailedException {		
		StringBuffer SQL = new StringBuffer(
			"SELECT TO_CHAR(when1,'YYYY-MM-DD') as datingdate,"+ 
			" CAST(d.number as TEXT)as number,"+
			" v.rut, "+
			" CAST(o.number as TEXT )||TO_CHAR(o.emitted, 'YYMMDD') as buynumber,"+ 
			" l.code,"+ 
			" i.internalcode,"+ 
			" odd.availableunits,"+ 
			" od.finalcost*odd.availableunits as amount,"+ 
			" f.name,"+
			" dt.name as batchname"+
			" FROM logistica.reserve r"+
			" inner join logistica.dating dat on (r.id=dat.id)"+
			" inner join logistica.delivery d on (dat.delivery_id=d.id)"+
			" inner join logistica.vendor v on (d.vendor_id=v.id)"+
			" inner join logistica.orderdeliverydetail odd on (odd.delivery_id=d.id)"+
			" inner join logistica.order o on (odd.order_id=o.id)"+
			" inner join logistica.location l on (odd.location_id=l.id)"+
			" inner join logistica.item i on (odd.item_id=i.id)"+
			" inner join logistica.orderdetail od on (od.order_id=o.id and od.item_id=i.id)"+
			" inner join logistica.flowtype f on (d.flowtype_id=f.id)"+
			" inner join logistica.deliverytype dt on (d.deliverytype_id=dt.id)"+
			//" where date(when1)>=date('"+since+"') and date(when1)<=date('"+until+"')"
			" where date(confirmationdate)>=date('"+since+"') and date(confirmationdate)<=date('"+until+"') ");

		DailyReportDatesDTO[] result = null;
		try {
			SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL.toString());
			query.setResultTransformer(new LowerCaseResultTransformer(DailyReportDatesDTO.class));
			List list = query.list();		
			result = (DailyReportDatesDTO[]) list.toArray(new DailyReportDatesDTO[list.size()]);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage());
		}		
		return result;
	}
	
	public Long getNextSequenceDailyReportDates() throws OperationFailedException, NotFoundException {
        Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.DAILYREPORTDATES_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
	
	public DatingRequestContainerDTO[] getDatingsByDatingRequestContainer(Long datingrequestid, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
				"WITH rd AS(" + //
						"SELECT " +
							"dl.container " + //
						"FROM " + //
							"logistica.datingrequest AS dr JOIN " + //
							"logistica.delivery AS dl ON dl.id = dr.delivery_id " + //
						"WHERE " + //  
							"dr.id = :datingrequestid), " +
				     "dt AS(" + //
						"SELECT " + //
							"re.id, " + //
							"re.location_id, " + //
							"dl.flowtype_id, " + //
							"re.when1, " + //
							"MIN(mo.starts) AS starts " + //
						"FROM " + //
							"rd JOIN " + //
							"logistica.delivery AS dl ON dl.container = rd.container JOIN " + //
							"logistica.dating AS dt ON dt.delivery_id = dl.id JOIN " + //
							"logistica.reserve AS re ON re.id = dt.id JOIN " + //
							"logistica.reservedetail AS red ON red.reserve_id = re.id JOIN " + //
							"logistica.module AS mo ON mo.id = red.module_id " + //
						"WHERE " + //
							"re.when1 >= current_date " + //
						"GROUP BY " + //
							"re.id, re.location_id, dl.flowtype_id, re.when1), " + //
					 "re AS(" + //
					 	"SELECT " + //
					 		"re.id, " + //
					 		"red.dock_id AS dock_id, " + //
					 		"MIN(mo.starts) AS starts, " + //
					 		"MAX(mo.ends) AS ends, " + //
					 		"to_char(MAX(mo.ends) - MIN(mo.starts), 'HH24:MI') AS datinginterval " + //
					 	"FROM " + //
							"rd JOIN " + //
							"logistica.delivery AS dl ON dl.container = rd.container JOIN " + //
							"logistica.dating AS dt ON dt.delivery_id = dl.id JOIN " + //
							"logistica.reserve AS re ON re.id = dt.id JOIN " + //
							"logistica.reservedetail AS red ON red.reserve_id = re.id JOIN " + //
							"logistica.module AS mo ON mo.id = red.module_id " + //
						"WHERE " + //
							"re.when1 >= current_date " + //
						"GROUP BY " + //
							"re.id, red.dock_id) " + //
				"SELECT DISTINCT " + //
					"to_char(dt.when1, 'YYYY-MM-DD') || ' ' || to_char(dt.starts, 'HH24:MI') AS datingdate, " + //
					"doc.id AS dockid, " + //
					"doc.code AS dockcode, " + //
					"to_char(re.starts, 'HH24:MI') || '-' || to_char(re.ends, 'HH24:MI') AS datingtime, " + //
					"re.datinginterval AS datinginterval, " + //
					"lo.id AS deliverylocationid, " + //
					"lo.code AS deliverylocationcode, " + //
					"lo.name AS deliverylocationname, " + //
					"ft.id AS flowtypeid, " + //
					"ft.code AS flowtypecode, " + //
					"ft.name AS flowtypename " + //
				"FROM " + //
					"dt JOIN " + //
					"re ON re.id = dt.id JOIN " + //
					"logistica.location AS lo ON lo.id = dt.location_id JOIN " + //
					"logistica.flowtype AS ft ON ft.id = dt.flowtype_id JOIN " + //
					"logistica.dock AS doc ON doc.id = re.dock_id " + //
				getOrderByString(mapGetImportedContainerDatingSQL, orderby); //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("datingrequestid", datingrequestid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DatingRequestContainerDTO.class));
		
		List list = query.list();
		DatingRequestContainerDTO[] result = (DatingRequestContainerDTO[]) list.toArray(new DatingRequestContainerDTO[list.size()]);		
		return result;
	}
	
	public DatingDataDTO getDatingDataByDelivery(Long deliveryid) {
		
		String SQL =
			"WITH dtn AS(" + //
					"SELECT " +
						"delivery_id, " + //
						"array_to_string(array_agg(asnimp ORDER BY asnimp ASC), '-') AS numbers " + //
					"FROM " + //
						"logistica.orderdelivery " + //
					"WHERE " + //  
						"delivery_id = :deliveryid " + //
					"GROUP BY " + //
						"delivery_id), " +
			     "ad AS(" + //
					"SELECT " + //
						"ds.delivery_id, " + //
						"MAX(ds.when1) AS assigneddate " + //
					"FROM " + //
						"logistica.deliverystate AS ds JOIN " + //
						"logistica.deliverystatetype AS dst ON dst.id = ds.deliverystatetype_id " + //
					"WHERE " + //
						"ds.delivery_id = :deliveryid AND dst.code = 'CITA_ASIGNADA' " + //
					"GROUP BY " + //
						"ds.delivery_id) " + //
			"SELECT " + //
				"dt.id AS id, " + //}
				"CASE WHEN vd.domestic " + //
						"THEN CAST(dt.number AS VARCHAR) " + //
						"ELSE dtn.numbers " + //
						"END AS number, " + //
				"logistica.tostr(re.when1) AS when, " + //
				"dt.confirmed AS confirmed, " + //
				"COALESCE(logistica.tostr(dt.confirmationdate), '') AS confirmationdate, " + //
				"COALESCE(logistica.tostr(ad.assigneddate), '') AS assigneddate, " + //
				"COALESCE(dt.comment, '') AS comment, " + //
				"dt.vendor_id AS vendorid, " + //
				"dt.delivery_id AS deliveryid, " + //
				"re.location_id AS locationid " + //
			"FROM " + //
				"logistica.dating AS dt JOIN " + //
				"logistica.reserve AS re ON re.id = dt.id JOIN " + //
				"dtn ON dtn.delivery_id = dt.delivery_id JOIN " + //
				"logistica.vendor AS vd ON dt.vendor_id = vd.id LEFT JOIN " + //
				"ad ON ad.delivery_id = dt.delivery_id " + //
			"WHERE " + //
				"dt.delivery_id = :deliveryid "; //
	
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DatingDataDTO.class));
		
		List<DatingDataDTO> list = (List<DatingDataDTO>)query.list();
		DatingDataDTO result = list == null || list.size() == 0 ? null : list.get(0);		
		return result;
	}
	
	private String getOrderByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}	
	
	@Resource
	private ManagedExecutorService executor;

	private void handleStream(InputStream inputStream, String type) {

		// EJECUTA UNA TAREA INDEPENDIENTE PARA MANEJAR LOS STREAM DE SALIDA
		try{
			executor.submit(new RuntimeProcessUtils(inputStream, type));			
		
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}				
	}
}

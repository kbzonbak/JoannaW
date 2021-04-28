package bbr.b2b.regional.logistic.directorders.classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetail;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetailId;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.utils.RuntimeProcessUtils;

@Stateless(name = "servers/directorders/DirectOrderDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderDetailServer extends BaseLogisticEJB3Server<DirectOrderDetail, DirectOrderDetailId, DirectOrderDetailW> implements DirectOrderDetailServerLocal {

	private Map<String, String> mapGetOrderDetailSQL = new HashMap<String, String>();
	{
		mapGetOrderDetailSQL.put("ORDERID", "OD.ORDER_ID ");
		mapGetOrderDetailSQL.put("ITEMID", "OD.ITEM_ID ");
		mapGetOrderDetailSQL.put("ITEMSKU", "IT.INTERNALCODE ");
		mapGetOrderDetailSQL.put("VENDORITEMCODE", "VI.VENDORITEMCODE");
		mapGetOrderDetailSQL.put("ITEMDESCRIPTION", "IT.NAME ");
		mapGetOrderDetailSQL.put("LISTPRICE", "OD.LISTPRICE");
		mapGetOrderDetailSQL.put("FINALPRICE", "OD.FINALPRICE");
		mapGetOrderDetailSQL.put("LISTCOST", "OD.LISTCOST");
		mapGetOrderDetailSQL.put("FINALCOST", "OD.FINALCOST");
		mapGetOrderDetailSQL.put("NEEDUNITS", "OD.UNITS");
		mapGetOrderDetailSQL.put("TODELIVERYUNITS", "OD.TODELIVERYUNITS");
		mapGetOrderDetailSQL.put("RECEIVEDUNITS", "OD.RECEIVEDUNITS");
		mapGetOrderDetailSQL.put("PENDINGUNITS", "OD.PENDINGUNITS");
		mapGetOrderDetailSQL.put("TOTALNEED", "OD.TOTALNEED");
		mapGetOrderDetailSQL.put("TOTALRECEIVED", "OD.TOTALRECEIVED");
		mapGetOrderDetailSQL.put("TOTALTODELIVERY", "OD.TOTALTODELIVERY");
		mapGetOrderDetailSQL.put("TOTALPENDING", "OD.TOTALPENDING");	
		mapGetOrderDetailSQL.put("SALESTORECODE", "LOSS.CODE");
		mapGetOrderDetailSQL.put("SALESTORENAME", "LOSS.NAME");
	}		

	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	ItemServerLocal itemserver;

	public DirectOrderDetailW addDirectOrderDetail(DirectOrderDetailW directorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderDetailW) addIdentifiable(directorderdetail);
	}
	public DirectOrderDetailW[] getDirectOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderDetailW[]) getIdentifiables();
	}
	public DirectOrderDetailW updateDirectOrderDetail(DirectOrderDetailW directorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderDetailW) updateIdentifiable(directorderdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DirectOrderDetail entity, DirectOrderDetailW wrapper) {
		wrapper.setOrderid(entity.getDirectorder() != null ? new Long(entity.getDirectorder().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DirectOrderDetail entity, DirectOrderDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			DirectOrder directorder = directorderserver.getReferenceById(wrapper.getOrderid());
			if (directorder != null) { 
				entity.setDirectorder(directorder);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) { 
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) { 
				entity.setItem(item);
			}
		}
	}
	
private String getPDFOrderDetailQueryString() throws AccessDeniedException{
		
		String SQL 				= "";
		String condicionOrderBy	= "";
				
			SQL = 	
			"SELECT	" +  	
				"IT.INTERNALCODE AS ITEMSKU, " +
				"IT.NAME AS ITEMDESCRIPTION, " +
				"OD.TOTALNEED " ;
		
		SQL =		
			SQL +
			"FROM " + 
				"LOGISTICA.DIRECTORDERDETAIL OD JOIN " +				
				"LOGISTICA.DIRECTORDER O ON OD.ORDER_ID = O.ID JOIN " +
				"LOGISTICA.VENDOR AS VD ON VD.ID = O.VENDOR_ID JOIN " +
				"LOGISTICA.ITEM AS IT ON IT.ID = OD.ITEM_ID " + 
			"WHERE " +
				"O.ID = :orderid AND VD.ID = :vendorid " +			
			condicionOrderBy;		
		return SQL;		
	}
	
	private String getOrderDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL 				= "";
		String condicionOrderBy	= "";
				
		if (queryType == 1){
			SQL = 
			"SELECT " +
				"CAST(COUNT(OD.ORDER_ID) AS integer) AS TOTALREG, " +
				"SUM(OD.UNITS) AS NEEDUNITS, " +
				"SUM(OD.TOTALNEED) AS TOTALNEED, " +					
				"SUM(OD.TODELIVERYUNITS) AS TODELIVERYUNITS, " + 
				"SUM(OD.RECEIVEDUNITS) AS RECEIVEDUNITS, " + 
				"SUM(OD.PENDINGUNITS) AS PENDINGUNITS, " + 
				"SUM(OD.TOTALRECEIVED) AS TOTALRECEIVED, " + 
				"SUM(OD.TOTALTODELIVERY) AS TOTALTODELIVERY, " + 
				"SUM(OD.TOTALPENDING) AS TOTALPENDING ";
		}
		if (queryType == 2) {
			SQL = 	
			"SELECT	" +  
				"OD.ORDER_ID AS ORDERID, " +
				"OD.ITEM_ID AS ITEMID, " + 					
				"IT.INTERNALCODE AS ITEMSKU, " +
				"VI.VENDORITEMCODE, " +
				"IT.NAME AS ITEMDESCRIPTION, " +					
				"OD.LISTPRICE, " +
				"OD.FINALPRICE, " +
				"OD.LISTCOST, " +
				"OD.FINALCOST, " +					
				"OD.UNITS AS NEEDUNITS, " +
				"OD.TODELIVERYUNITS, " +
				"OD.RECEIVEDUNITS, " +
				"OD.PENDINGUNITS, " +
				"OD.TOTALNEED, " +
				"OD.TOTALRECEIVED, " +
				"OD.TOTALTODELIVERY, " +
				"OD.TOTALPENDING "; 				
			condicionOrderBy = getOrderByString(mapGetOrderDetailSQL, orderby);		
		}	
		
		SQL =		
			SQL +
			"FROM " + 
				"LOGISTICA.DIRECTORDERDETAIL OD JOIN " +				
				"LOGISTICA.DIRECTORDER O ON OD.ORDER_ID = O.ID JOIN " + 
				"LOGISTICA.ITEM AS IT ON IT.ID = OD.ITEM_ID JOIN " +
				"LOGISTICA.VENDOR AS VD ON VD.ID = O.VENDOR_ID JOIN " +
				"LOGISTICA.VENDORITEM AS VI ON VI.ITEM_ID = IT.ID AND VI.VENDOR_ID = VD.ID " + 
			"WHERE " +
				"O.ID = :orderid AND VD.ID = :vendorid " +			
			condicionOrderBy;		
		return SQL;		
	}
	
	public VeVPDOrderDetailReportDataDTO[] getVeVPDOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException{
		String SQL = getOrderDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(VeVPDOrderDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		VeVPDOrderDetailReportDataDTO[] result = (VeVPDOrderDetailReportDataDTO[]) list.toArray(new VeVPDOrderDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public PDFVeVPDOrderDetailReportDataDTO[] getPDFVeVPDOrdersDetailsByOrder(Long orderid, Long vendorid) throws ServiceException{
		String SQL = getPDFOrderDetailQueryString();		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(PDFVeVPDOrderDetailReportDataDTO.class));
//		if(ispaginated){//si quiero el reporte paginado
//			query.setFirstResult((pagenumber-1) * rows);
//			query.setMaxResults(rows);
//		}
		List list = query.list();
		PDFVeVPDOrderDetailReportDataDTO[] result = (PDFVeVPDOrderDetailReportDataDTO[]) list.toArray(new PDFVeVPDOrderDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public VeVPDOrderDetailReportTotalDataDTO getCountVeVPDOrdersDetailsByOrder(Long orderid,  Long vendorid) throws ServiceException{
		String SQL = getOrderDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVPDOrderDetailReportTotalDataDTO.class));
		VeVPDOrderDetailReportTotalDataDTO result = (VeVPDOrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}
	
	public DirectOrderDetailW[] getDirectOrderDetailDataofDirectOrder(Long directorderid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrderDetail.getDirectOrderDetailDataofDirectOrder");
		query.setLong("orderid", directorderid);
		query.setResultTransformer(new LowerCaseResultTransformer(DirectOrderDetailW.class));
		List list = query.list();
		DirectOrderDetailW[] result = (DirectOrderDetailW[]) list.toArray(new DirectOrderDetailW[list.size()]);
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
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DirectOrderDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DirectOrderDetail";
	}
	
	
	public FileDownloadInfoResultDTO getCSVDirectOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long userID, boolean courier) throws OperationFailedException, NotFoundException {
		
		// formato hora postgres
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sinceStr = df.format(since);
		String untilStr = df.format(until);		
		
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

		StringBuffer SQL = new StringBuffer(
				"WITH ordertmp AS (" + //
								"SELECT " + //
									"o.id, " + //
									"o.number, " + //
									"o.requestnumber, " + //
									"o.emitted, " + //
									"o.originaldeliverydate, " + //
									"o.currentdeliverydate, " + //
									"o.clientphone, " + //
									"o.clientaddress, " + //
									"o.clientcommune, " + //
									"o.clientcity, " + //
									"o.clientroadnumber, " + //
									"o.clientdeptnumber, " + //
									"o.clienthousenumber, " + //
									"o.clientregion, " + //
									"o.courierreceived, " + //
									"o.vendor_id, " + //
									"v.code AS vendorcode, " + //
									"v.name AS vendorname, " + //
									"o.salestore_id, " + //
									"o.department_id, " + //
									"o.client_id, " + //
									"o.dodelivery_id, " + //
									"o.currentstatetype_id, " + //
									"MIN(dos.when1) AS loaddate " + //
							"FROM " + //
								"logistica.directorder AS o JOIN " + //
								"logistica.vendor AS v ON v.id = o.vendor_id LEFT JOIN " + //
								"logistica.hiredcourier AS hc ON hc.vendor_id = v.id JOIN " + //
								"logistica.directorderstate AS dos ON dos.order_id = o.id JOIN " + //
								"logistica.directorderstatetype AS dost ON dost.id = dos.orderstatetype_id " + //
							"WHERE " + //
								"o.number > 0 AND dost.code = 'LIBERADA' AND "); //

		SQL.append(courier ?
								"hc.vendor_id IS NOT NULL AND " :
								"hc.vendor_id IS NULL AND "); //
		
					 SQL.append("o.originaldeliverydate >= '" + sinceStr + "' AND o.originaldeliverydate <= '" + untilStr + "' "); //
		
		SQL.append(vendorid != null && vendorid.longValue() > 0 ?
								"AND o.vendor_id = " + vendorid.longValue() :
								"");

		SQL.append(orderstatetypeid != null && orderstatetypeid.longValue() > 0 ?
								"AND o.currentstatetype_id = " + orderstatetypeid.longValue() :
								"");

		if (salestoreid != null && salestoreid.length > 0) {
			String and = "AND o.salestore_id IN (";
			for (int i = 0; i < salestoreid.length; i++) {
				and += salestoreid[i];
				if (i < salestoreid.length - 1) {
					and += ",";
				}
				else if (i == salestoreid.length - 1) {
					and += ") ";
				}
			}
			SQL.append(and);
		}

				SQL.append("GROUP BY " + //
								"o.id, o.number, o.requestnumber, o.emitted, o.originaldeliverydate, o.currentdeliverydate, o.clientphone, o.clientaddress, " + //
								"o.clientcommune, o.clientcity, o.clientroadnumber, o.clientdeptnumber, o.clienthousenumber, o.clientregion, o.courierreceived, " + //
								"o.vendor_id, v.code, v.name, o.salestore_id, o.department_id, o.client_id, o.dodelivery_id, " + //
								"o.currentstatetype_id), "); //
		
		SQL.append(
				"dods AS(" + //
							"SELECT " + //
								"MAX(when1) AS when1, " + //
								"delivery_id, " + //
								"deliverystatetype_id " + //
							"FROM " + //
								"logistica.dodeliverystate " + //
							"GROUP BY " + //
								"delivery_id, deliverystatetype_id), " + //
				"min AS(" + //
							"SELECT " + //
								"couriertag_id, " + //
								"MIN(when1) AS when1 " + //
							"FROM " + //
								"logistica.courierschedulelog " + //
							"GROUP BY " + //
								"couriertag_id), " + //
				"lastdesc AS(" + //
							"SELECT " + //
								"* " + //
							"FROM " + //
								"crosstab('" + //
									"SELECT " + //
										"* " + //
									"FROM (" + //
										"SELECT " + //
											"ct.dodelivery_id, " + //
											"ROW_NUMBER() OVER (" + //
																"PARTITION BY ct.dodelivery_id " + //
																"ORDER BY cs.uploaddate DESC), " + //
											"description " + //
										"FROM " + //
											"logistica.courierstate AS cs JOIN " + //
											"logistica.couriertag AS ct ON cs.couriertag_id = ct.id JOIN " + //
											"logistica.directorder AS o ON o.dodelivery_id = ct.dodelivery_id " + //
										"WHERE " + //
											"o.originaldeliverydate >= ''" + sinceStr + "'' AND " + //
											"o.originaldeliverydate <= ''" + untilStr + "'')x " + //
									"WHERE " + //
										"ROW_NUMBER IN (1, 2)') AS ct " + //
							"(\"dodelivery_id\" bigint, \"ultimoestado\" character varying, \"penultimoestado\" character varying)) " + //
				"SELECT " + //
					"loss.code AS salestorecode, " + //
					"loss.name AS salestorename, " + //
					"o.vendorcode AS vendorcode, " + //
					"o.vendorname AS vendorname, " + //
					"o.number AS order_number, " +
					"o.requestnumber AS requestnumber, " + //
					"d.code AS departmentcode, " + //
					"d.name AS departmentname, " + //
					"dost.name AS currentorderstate, " + //
					"COALESCE(dst.name, '') AS currentdeliverystate, " + //
					"TO_CHAR(dod.currentstdate, 'DD-MM-YYYY HH24:MI') AS realdeliverydate, " + //
					"TO_CHAR(dods.when1, 'DD-MM-YYYY HH24:MI') AS deliverycurrentdate, " + //
					"'VeV PD' AS ordertype, " + //
					"TO_CHAR(o.loaddate, 'DD-MM-YYYY HH24:MI') AS loaddate, " + //
					"TO_CHAR(o.emitted, 'DD-MM-YYYY HH24:MI') AS order_emitted, " + //
					"'' AS order_fpi, " + // 
					"TO_CHAR(o.originaldeliverydate, 'DD-MM-YYYY HH24:MI') AS order_originaldeliverydate, " + //
					"TO_CHAR(o.currentdeliverydate, 'DD-MM-YYYY HH24:MI') AS order_currentdeliverydate, " + //
					"c.rut AS client_rut, " + //
					"c.name AS client_name, " + //
					"o.clientphone AS client_phone, " + //
					"o.clientaddress AS client_address, " + //
					"o.clientcommune AS client_commune, " + //
					"o.clientcity AS client_city, " + //
					"o.clientroadnumber AS client_roadnumber, " + //
					"o.clientdeptnumber AS client_deptnumber, " + //
					"o.clienthousenumber AS client_housenumber, " + //
					"o.clientregion AS client_region, " + //
					"c.comment AS client_comment, " + //
					"i.internalcode AS item_internalcode, " + //
					"vi.vendoritemcode AS vendoritem_vendoritemcode, " + //
					"i.name AS item_name, " + //
					"odt.units AS detail_units, " +
					"odt.todeliveryunits AS detail_todeliveryunits, " + //
					"odt.receivedunits AS detail_receivedunits, " + //
					"odt.pendingunits AS detail_pendingunits, " + //
					"odt.finalcost AS detail_finalcost, " + //
					"'' AS deliverylocationcode, " + //
					"'' AS deliverylocationname, " + //
					"'' AS finallocationcode, " + //
					"'' AS finallocationname, " + //
					"'' AS item_flowtype, " + //
					"0 AS item_innerpack, " + //
					"ct.sendnumber, " + //
					"ct.withdrawalnumber AS withdrawalnumber, " + //
					"TO_CHAR(ct.withdrawaldate, 'DD-MM-YYYY') AS withdrawaldate, " + //
					"csl.withdrawalnumber AS firstwithdrawalnumber, " + //
					"TO_CHAR(csl.withdrawaldate, 'DD-MM-YYYY') AS firstwithdrawaldate, " + //
					"ld.penultimoestado AS penultimatestate, " + //
					"ld.ultimoestado AS ultimatestate, " + //
					"CASE WHEN o.courierreceived " + //
						 "THEN 'Si' " + //
						 "ELSE 'No' " + //
						 "END " + //
				"FROM " + //
					"ordertmp AS o LEFT JOIN " + //
					"logistica.couriertag AS ct ON ct.dodelivery_id = o.dodelivery_id JOIN " + //
					"logistica.directorderstatetype AS dost ON dost.id = o.currentstatetype_id JOIN " + //
					"logistica.department AS d ON d.id = o.department_id JOIN " + //
					"logistica.directorderdetail AS odt ON odt.order_id = o.id JOIN " + //				
					"logistica.client AS c ON c.id = o.client_id JOIN " + //
					"logistica.item AS i ON i.id = odt.item_id LEFT JOIN " + //
					"logistica.vendoritem AS vi ON vi.item_id = i.id AND vi.vendor_id = o.vendor_id LEFT JOIN " + //
					"logistica.dodelivery AS dod ON dod.id = o.dodelivery_id LEFT JOIN " + //
					"logistica.dodeliverystatetype AS dst ON dst.id = dod.currentstatetype_id LEFT JOIN " + //
					"logistica.location AS loss ON loss.id = o.salestore_id LEFT JOIN " + //
					"dods ON dods.delivery_id = dod.id AND dods.deliverystatetype_id = dod.currentstatetype_id LEFT JOIN " + //
					"lastdesc AS ld ON ld.dodelivery_id = ct.dodelivery_id LEFT JOIN " + //
					"min ON min.couriertag_id = ct.id LEFT JOIN " + //
					"logistica.courierschedulelog AS csl ON csl.couriertag_id = min.couriertag_id AND csl.when1 = min.when1 "); //
					
		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL.toString());

			String realfilename = (courier ? "DOVEVREPORTCOURIER" : "DOVEVREPORT") + date.getTime() + "_" + userID.longValue() + ".csv";
	
			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();
	
			String downloadfilename = (courier ? "ReporteVeVPDCourier_" : "ReporteVeVPD_") + dateFormat.format(date) + ".zip";
	
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
			String titulos = "\"Cod_Tienda\",\"Tienda\",\"Codigo_de_proveedor\",\"Proveedor\",\"N_Orden\",\"N_Solicitud\",\"Cod_Depto\",\"Departamento\",\"Estado\",\"Estado_de_ultimo_despacho\",\"Fecha_Estado_Ultimo_Despacho\",\"Fecha_Cambio_Estado_Despacho\",\"Tipo_Orden\",\"Fecha_Carga\",\"Fecha_Envio\",\"FPI\",\"Fecha_Compromiso\",\"Fecha_Reprogramada\",\"Rut\",\"Nombre\",\"Telefono\",\"Direccion\",\"Comuna\",\"Ciudad\",\"N_Calle\",\"N_Depto\",\"N_Casa\",\"Region\",\"Comentario\",\"SKU_Paris\",\"SKU_Proveedor\",\"Descripcion\",\"Solicitado\",\"En_Despacho\",\"Recepcionado\",\"Pendiente\",\"Precio_Costo\",\"Cod_Local_Entrega\",\"Local_Entrega\",\"Cod_Local_Destino\",\"Local_Destino\",\"Tipo_Flujo\",\"Innerpack\",\"N_Courier\",\"Nro_Retiro_Actual\",\"Fecha_Retiro_Actual\",\"Nro_Retiro_Inicial\",\"Fecha_Retiro_Inicial\",\"Penultimo_Estado_Courier\",\"Ultimo_Estado_Courier\",\"Recibido_Courier\"";
			comando2 = "sh /b2b/shell/fileToDownload/moveFile1.sh " + realfilename + " " + titulos;
			
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
	
	public int countCSVDirectOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, boolean courier) {
		
		String whereCondition = (courier ?
									"hc.vendor_id IS NOT NULL AND " : //
									"hc.vendor_id IS NULL AND ") + //
								"o.originaldeliverydate >= :since AND o.originaldeliverydate <= :until "; //
		
		if (vendorid != null && vendorid.longValue() > 0) {
			whereCondition += "AND o.vendor_id = :vendorid ";
		}
		
		if (orderstatetypeid != null && orderstatetypeid.longValue() > 0) {
			whereCondition += "AND o.currentstatetype_id = :orderstatetypeid ";
		}
		
		if (salestoreid != null && salestoreid.length > 0) {
			whereCondition += "AND o.salestore_id IN (:salestoreid) ";
		}
		
		String SQL =
				"WITH ordertmp AS (" + //
						"SELECT " + //
							"o.id, " + //
							"MIN(dos.when1) AS loaddate " + //
						"FROM " + //
							"logistica.directorder AS o JOIN " + //
							"logistica.vendor AS v ON v.id = o.vendor_id LEFT JOIN " + //
							"logistica.hiredcourier AS hc ON hc.vendor_id = v.id JOIN " + //
							"logistica.directorderstate AS dos ON dos.order_id = o.id JOIN " + //
							"logistica.directorderstatetype AS dost ON dost.id = dos.orderstatetype_id " + //
						"WHERE " + //
							"o.number > 0 AND dost.code = 'LIBERADA' AND " + //
							whereCondition + //
						"GROUP BY " + //
							"o.id) " + //							
				"SELECT " + //
					"COUNT(o.id) " +
				"FROM " + //
					"ordertmp AS o JOIN " + //
					"logistica.directorderdetail AS odt ON odt.order_id = o.id "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != null && vendorid.longValue() > 0) {
			query.setLong("vendorid", vendorid);
		}
		if (orderstatetypeid != null && orderstatetypeid.longValue() > 0) {
			query.setLong("orderstatetypeid", orderstatetypeid);
		}
		if (salestoreid != null && salestoreid.length > 0) {
			query.setParameterList("salestoreid", salestoreid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
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

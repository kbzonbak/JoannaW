package bbr.b2b.regional.logistic.buyorders.classes;

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
import java.util.Set;
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
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailW;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetailId;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailFillRateDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVCDOrderDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.classes.UnitServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.utils.RuntimeProcessUtils;

@Stateless(name = "servers/buyorders/OrderDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDetailServer extends BaseLogisticEJB3Server<OrderDetail, OrderDetailId, OrderDetailW> implements OrderDetailServerLocal {
	
	private Map<String, String> mapGetOrderDetailSQL = new HashMap<String, String>();
	{
		mapGetOrderDetailSQL.put("SKU", "it.internalcode");
		mapGetOrderDetailSQL.put("VENDORITEMCODE", "vi.vendoritemcode");
		mapGetOrderDetailSQL.put("FLOWTYPECODE", "ft.code");
		mapGetOrderDetailSQL.put("FLOWTYPEDESC", "ft.name");
		mapGetOrderDetailSQL.put("ITEMDESC", "it.name");
		mapGetOrderDetailSQL.put("FINALCOST", "od.finalcost");
		mapGetOrderDetailSQL.put("TOTALUNITS", "od.units");
		mapGetOrderDetailSQL.put("PENDINGUNITS", "od.pendingunits");
		mapGetOrderDetailSQL.put("RECEIVEDUNITS", "od.receivedunits");
		mapGetOrderDetailSQL.put("TODELIVERYUNITS", "od.todeliveryunits");
		mapGetOrderDetailSQL.put("OUTRECEIVEDUNITS", "od.outreceivedunits");
		mapGetOrderDetailSQL.put("TOTALAMOUNT", "od.totalneed");
		mapGetOrderDetailSQL.put("TOTALPENDING", "od.totalpending");
		mapGetOrderDetailSQL.put("TOTALRECEIVED", "od.totalreceived");
		mapGetOrderDetailSQL.put("TOTALTODELIVERY", "od.totaltodelivery");
		mapGetOrderDetailSQL.put("CURVE", "ia.curve");
		mapGetOrderDetailSQL.put("ATCCODE", "atccode");
	}	
	
	@EJB
	OrderServerLocal orderserver;

	@EJB
	UnitServerLocal packingunitserver;

	@EJB
	ItemServerLocal itemserver;

	public OrderDetailW addOrderDetail(OrderDetailW orderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDetailW) addIdentifiable(orderdetail);
	}

	public OrderDetailW[] getOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDetailW[]) getIdentifiables();
	}

	public OrderDetailW updateOrderDetail(OrderDetailW orderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDetailW) updateIdentifiable(orderdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderDetail entity, OrderDetailW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
		wrapper.setPackingunitid(entity.getPackingunit() != null ? new Long(entity.getPackingunit().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(OrderDetail entity, OrderDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) {
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) {
				entity.setOrder(order);
			}
		}
		if (wrapper.getPackingunitid() != null && wrapper.getPackingunitid() > 0) {
			Unit packingunit = packingunitserver.getReferenceById(wrapper.getPackingunitid());
			if (packingunit != null) {
				entity.setPackingunit(packingunit);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) {
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) {
				entity.setItem(item);
			}
		}
	}
	
	public OrderDetailW[] getOrderDetailsByOrderIds(List<Long> orderids) throws OperationFailedException, NotFoundException {
		
		StringBuilder sb = new StringBuilder(
									"SELECT DISTINCT " + //
										"od " + //
									"FROM " + //
										"OrderDetail AS od " + //
									"WHERE " + //
										"od.order.id IN (:orderids)");
        
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("order.id", "orderids", orderids);

        List list = getByQuery(sb.toString(), properties);

        return (OrderDetailW[]) list.toArray(new OrderDetailW[list.size()]);		
	}
	
	public boolean doValidateOrderATCs(Long orderid, Set<String> itemcodes) {
		
		String SQL =
			"SELECT DISTINCT " + //
				"it.id " + //
			"FROM " + //
				"logistica.orderdetail AS od JOIN " + //
				"logistica.item AS it ON it.id = od.item_id " + //
			"WHERE " + //
				"od.order_id = :orderid AND od.hasatc IS FALSE AND it.internalcode IN (:itemcodes)";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setParameterList("itemcodes", itemcodes);
				
		return query.list().size() == 0;
	}
	
	public boolean doValidateOrderSKUs(Long orderid, List<String> itemcodes) {
		
		String SQL =
			"SELECT DISTINCT " + //
				"it.id " + //
			"FROM " + //
				"logistica.orderdetail AS od JOIN " + //
				"logistica.item AS it ON it.id = od.item_id " + //
			"WHERE " + //
				"od.order_id = :orderid AND od.hasatc IS TRUE AND it.internalcode IN (:itemcodes)";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setParameterList("itemcodes", itemcodes);
				
		return query.list().size() == 0;
	}
	
	private String getOrderDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		
		String SQL 				= "";
		String condicionOrderBy	= "";
		
		if (queryType == 1) {
			SQL = 
				"SELECT " + //
					"CAST(COUNT(od.order_id) AS INTEGER) AS totalreg, " + //
					"SUM(od.units) AS totalunits, " + //
					"SUM(od.pendingunits) AS pendingunits, " + //
					"SUM(od.receivedunits) AS receivedunits, " + //
					"SUM(od.todeliveryunits) AS todeliveryunits, " + //
					"SUM(od.outreceivedunits) AS outreceivedunits, " + //
					"SUM(od.totalneed) AS totalamount, " + //
					"SUM(od.totalpending) AS totalpending, " + //
					"SUM(od.totalreceived) AS totalreceived, " + //
					"SUM(od.totaltodelivery) AS totaltodelivery "; //
		}
		else if (queryType == 2) {
			SQL = 	
				"SELECT	" + //
					"od.order_id AS orderid, " + //
					"od.item_id AS itemid, " + //
					"it.internalcode AS sku, " + //
					"vi.vendoritemcode, " + //
					"ft.code AS flowtypecode, " + //
					"ft.name AS flowtypedesc, " + //
					"it.name AS itemdesc, " + //
					"od.finalcost, " + //
					"od.units AS totalunits, " + //
					"od.pendingunits, " + //
					"od.receivedunits, " + //
					"od.todeliveryunits, " + //
					"od.outreceivedunits, " + //
					"od.totalneed AS totalamount, " + //
					"od.totalpending, " + //
					"od.totalreceived, " + //
					"od.totaltodelivery, " + //
					"ia.curve, " + //
					"atc.id AS atcid, " + //
					"COALESCE(atc.code, '') AS atccode "; //
			
			condicionOrderBy = getOrderByString(mapGetOrderDetailSQL, orderby);		
		}	
		
		SQL +=
			"FROM " + //
				"logistica.order AS oc JOIN " + //
				"logistica.vendor AS vd ON vd.id = oc.vendor_id JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = oc.id JOIN " + //
				"logistica.item AS it ON it.id = od.item_id LEFT JOIN " + //
				"logistica.flowtype AS ft ON ft.id = it.flowtype_id JOIN " + //
				"logistica.vendoritem AS vi ON vi.item_id = it.id AND vi.vendor_id = vd.id LEFT JOIN " + //
				"logistica.item_atc AS ia ON ia.item_id = it.id LEFT JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id " + //
			"WHERE " + //
				"od.order_id = :orderid AND vd.id = :vendorid " + //
			condicionOrderBy; //
		
		return SQL;		
	}
	
	public OrderDetailReportDataDTO[] getOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException{
		String SQL = getOrderDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		OrderDetailReportDataDTO[] result = (OrderDetailReportDataDTO[]) list.toArray(new OrderDetailReportDataDTO[list.size()]);		
		return result;	
	}	
	
	public OrderDetailReportTotalDataDTO getCountOrdersDetailsByOrder(Long orderid, Long vendorid) throws ServiceException{
		String SQL = getOrderDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDetailReportTotalDataDTO.class));
		OrderDetailReportTotalDataDTO result = (OrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}
	
	public VeVCDOrderDetailReportDataDTO[] getVeVCDOrdersDetailsByOrder(Long orderid, Long vendorid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws ServiceException{
		String SQL = getOrderDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(VeVCDOrderDetailReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		VeVCDOrderDetailReportDataDTO[] result = (VeVCDOrderDetailReportDataDTO[]) list.toArray(new VeVCDOrderDetailReportDataDTO[list.size()]);		
		return result;	
	}	
	
	public VeVCDOrderDetailReportTotalDataDTO getCountVeVCDOrdersDetailsByOrder(Long orderid,  Long vendorid) throws ServiceException{
		String SQL = getOrderDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVCDOrderDetailReportTotalDataDTO.class));
		VeVCDOrderDetailReportTotalDataDTO result = (VeVCDOrderDetailReportTotalDataDTO) query.uniqueResult();
		
		return result;
	}	
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderDetail";
	}

	@Override
	protected void setEntityname() {
		entityname = "OrderDetail";
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
	
	public FileDownloadInfoResultDTO getCSVOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long locationid, int filtertype, Long userId) throws OperationFailedException, NotFoundException {

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
									"o.vevcd, " + //
									"o.number, " + //
									"o.requestnumber, " + //
									"o.emitted, " + //
									"o.valid, " + //
									"o.originaldeliverydate, " + //
									"o.currentstatetypedate, " + //
									"o.vendor_id, " + //
									"o.deliverylocation_id, " + //
									"o.salestore_id, " + //
									"o.department_id, " + //
									"o.currentstatetype_id, " + //
									"MIN(os.when1) AS loaddate " + //
								"FROM " + //
									"logistica.order AS o JOIN " + //
									"logistica.orderstate AS os ON os.order_id = o.id JOIN " + //
									"logistica.orderstatetype AS ost ON ost.id = os.orderstatetype_id " + //
								"WHERE " + //
									"o.vevcd IS TRUE AND o.number > 0 AND o.deliverylocation_id = " + locationid.longValue() + " AND ost.code = 'LIBERADA' AND "); //
		
		SQL.append(filtertype == 0 ?
									"o.originaldeliverydate >= '" + sinceStr + "' AND o.originaldeliverydate <= '" + untilStr + "' " :
									"o.valid >= '" + sinceStr + "' AND o.valid <= '" + untilStr + "' "); //
		
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
									"o.id, o.vevcd, o.number, o.requestnumber, o.emitted, o.valid, o.originaldeliverydate, o.currentstatetypedate, o.vendor_id, " + //
									"o.deliverylocation_id, o.salestore_id, o.department_id, o.currentstatetype_id) "); //

		SQL.append(
				"SELECT " + //
					"loss.code AS salestorecode, " + //
					"loss.name AS salestorename, " + //
					"v.code AS vendorcode, " + //
					"v.name AS vendorname, " + //
					"o.number AS order_number, " + //
					"o.requestnumber AS requestnumber, " + //
					"d.code AS departmentcode, " + //
					"d.name AS departmentname, " + //
					"ost.name AS currentorderstate, " + //
					"'' AS currentdeliverystate, " + //
					"TO_CHAR(o.currentstatetypedate, 'DD-MM-YYYY HH24:MI') AS realdeliverydate, " + //
					"TO_CHAR(o.currentstatetypedate, 'DD-MM-YYYY HH24:MI') AS deliverycurrentdate, " + //
					"'VeV CD' AS ordertype, " + //
					"TO_CHAR(o.loaddate, 'DD-MM-YYYY HH24:MI') AS loaddate, " + //
					"TO_CHAR(o.emitted, 'DD-MM-YYYY HH24:MI') AS order_emitted, " + //
					"TO_CHAR(o.valid, 'DD-MM-YYYY HH24:MI') AS order_fpi, " + //
					"TO_CHAR(o.originaldeliverydate, 'DD-MM-YYYY HH24:MI') AS order_originaldeliverydate, " + //
					"TO_CHAR(CAST(NULL AS TIMESTAMP), 'DD-MM-YYYY HH24:MI') AS order_currentdeliverydate, " + //
					"'' AS client_rut, " + //
					"'' AS client_name, " + //
					"'' AS client_phone, " + //
					"'' AS client_address, " + //
					"'' AS client_commune, " + //
					"'' AS client_city, " + //
					"'' AS client_roadnumber, " + //
					"'' AS client_deptnumber, " + //
					"'' AS client_housenumber, " + //
					"'' AS client_region, " + //
					"'' AS client_comment, " + //
					"i.internalcode AS item_internalcode, " + //
					"vi.vendoritemcode AS vendoritem_vendoritemcode, " + //
					"i.name AS item_name, " + //
					"pd.units AS detail_units, " + //
					"pd.todeliveryunits AS detail_todeliveryunits, " + //
					"pd.receivedunits AS detail_receivedunits, " + //
					"pd.pendingunits AS detail_pendingunits, " + //
					"od.finalcost AS detail_finalcost, " + //
					"le.code AS deliverylocationcode, " + //
					"le.name AS deliverylocationname, " + //
					"ld.code AS finallocationcode, " + //
					"ld.name AS finallocationname, " + //
					"COALESCE(ft.name, '') AS item_flowtype, " + //
					"i.innerpack AS item_innerpack " + //
				"FROM " + //
					"ordertmp AS o JOIN " + //
					"logistica.vendor AS v ON v.id = o.vendor_id JOIN " + //
					"logistica.department AS d ON d.id = o.department_id JOIN " + //
					"logistica.orderstatetype AS ost ON ost.id = o.currentstatetype_id JOIN " + //
					"logistica.orderdetail AS od ON od.order_id = o.id JOIN " + //
					"logistica.predeliverydetail AS pd ON pd.order_id = od.order_id AND pd.item_id = od.item_id JOIN " + //
					"logistica.location AS le ON le.id = o.deliverylocation_id JOIN " + //
					"logistica.location AS ld ON ld.id = pd.location_id LEFT JOIN " + //
					"logistica.location AS loss ON loss.id = o.salestore_id JOIN " + //
					"logistica.item AS i ON i.id = pd.item_id LEFT JOIN " + //
					"logistica.flowtype AS ft ON ft.id = i.flowtype_id LEFT JOIN " + //
					"logistica.vendoritem AS vi ON vi.item_id = i.id AND vi.vendor_id = v.id "); //

		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL.toString());

			String realfilename = "VEVREPORT" + date.getTime() + "_" + userId.longValue()+".csv";

			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();

			String downloadfilename = "ReporteVeVCD_" + dateFormat.format(date) + ".zip";

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

			String titulos = "\"Cod_Tienda\",\"Tienda\",\"Codigo_de_proveedor\",\"Proveedor\",\"N_Orden\",\"N_Solicitud\",\"Cod_Depto\",\"Departamento\",\"Estado\",\"Estado_de_ultimo_despacho\",\"Fecha_Estado_Ultimo_Despacho\",\"Fecha_Cambio_Estado_Despacho\",\"Tipo_Orden\",\"Fecha_Carga\",\"Fecha_Envio\",\"FPI\",\"Fecha_Compromiso\",\"Fecha_Reprogramada\",\"Rut\",\"Nombre\",\"Telefono\",\"Direccion\",\"Comuna\",\"Ciudad\",\"N_Calle\",\"N_Depto\",\"N_Casa\",\"Region\",\"Comentario\",\"SKU_Paris\",\"SKU_Proveedor\",\"Descripcion\",\"Solicitado\",\"En_Despacho\",\"Recepcionado\",\"Pendiente\",\"Precio_Costo\",\"Cod_Local_Entrega\",\"Local_Entrega\",\"Cod_Local_Destino\",\"Local_Destino\",\"Tipo_Flujo\",\"Innerpack\"";
			comando2  = "sh /b2b/shell/fileToDownload/moveFile1.sh " + realfilename + " " + titulos;

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
	
	public int countCSVOrderVeVReport(Long[] salestoreid, Long vendorid, Long orderstatetypeid, Date since, Date until, Long locationid, int filtertype) {
		
		String whereCondition = filtertype == 0 ?
										"o.originaldeliverydate >= :since AND o.originaldeliverydate <= :until " :
										"o.valid >= :since AND o.valid <= :until ";
		
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
							"MIN(os.when1) AS loaddate " + //
						"FROM " + //
							"logistica.order AS o JOIN " + //
							"logistica.orderstate AS os ON os.order_id = o.id JOIN " + //
							"logistica.orderstatetype AS ost ON ost.id = os.orderstatetype_id " + //
						"WHERE " + //
							"o.vevcd IS TRUE AND o.number > 0 AND o.deliverylocation_id = :locationid AND ost.code = 'LIBERADA' AND " + //
							whereCondition + //
						"GROUP BY " + //
							"o.id) " + //							
				"SELECT " + //
					"COUNT(o.id) " +
				"FROM " + //
					"ordertmp AS o JOIN " + //
					"logistica.predeliverydetail AS pd ON pd.order_id = o.id "; //

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("locationid", locationid);
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
	
	public OrderDetailFillRateDTO[] getOrderDetailFillrate(Date since, Date until){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.OrderDetail.getOrderDetailFillrate");
		query.setLong("since", since.getTime()/1000);
		query.setLong("until", until.getTime()/1000);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDetailFillRateDTO.class));
		List list = query.list();
		OrderDetailFillRateDTO[] result = (OrderDetailFillRateDTO[]) list.toArray(new OrderDetailFillRateDTO[list.size()]);
		return result;		
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

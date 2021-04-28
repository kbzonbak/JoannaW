package bbr.b2b.portal.customer.managers.classes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.customer.managers.interfaces.ContextUtil;
import bbr.b2b.customer.managers.interfaces.ICustomerManager;
import bbr.b2b.customer.report.classes.AddOrUpdatePendingFileInitParamDTO;
import bbr.b2b.customer.report.classes.AssociateProductProviderProductClientInitParamDTO;
import bbr.b2b.customer.report.classes.ClientArrayResultDTO;
import bbr.b2b.customer.report.classes.CustomerServiceControlPanelResultDTO;
import bbr.b2b.customer.report.classes.LastInventorySendArrayResultDTO;
import bbr.b2b.customer.report.classes.LastSalesSendArrayResultDTO;
import bbr.b2b.customer.report.classes.LateSalesArrayResultDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailInitParamDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalArrayResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductArrayResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailResultDTO;
import bbr.b2b.customer.report.classes.PendingLoadFileInitParamDTO;
import bbr.b2b.customer.report.classes.PendingLoadFileResultDTO;
import bbr.b2b.customer.report.classes.PendingLoadFilesResultDTO;
import bbr.b2b.customer.report.classes.PendingProcessInventoryDetailInitParamDTO;
import bbr.b2b.customer.report.classes.PendingProcessSalesDetailInitParamDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryArrayResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDetailDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDetailResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesArrayResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDetailDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDetailResultDTO;
import bbr.b2b.customer.report.classes.ProductProviderArrayResultDTO;
import bbr.b2b.customer.report.classes.ProductReportDataDTO;
import bbr.b2b.customer.report.classes.ProductReportInitParamDTO;
import bbr.b2b.customer.report.classes.ProductReportResultDTO;
import bbr.b2b.customer.report.classes.ScoreCardTableBbrDTO;
import bbr.b2b.customer.report.classes.SearchProductProviderInitParamDTO;
import bbr.b2b.customer.report.classes.SearchProductProviderWithoutHomologationInitParamDTO;
import bbr.b2b.customer.report.classes.UploadMastersLoadInitParamDTO;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterPOI;

@Stateless(name = "managers/CustomerReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CustomerReportManager implements CustomerReportManagerLocal
{

	private ICustomerManager		customerManagerRemote	= null;

	@EJB
	private UserReportManagerLocal	userreportmanager;

	@PostConstruct
	public void getRemote()
	{
		try
		{
			customerManagerRemote = ContextUtil.getInstance().getICustomerManager();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public PendingReprocessSalesArrayResultDTO countPendingReprocessSalesByClient(String pvcode)
	{
		try
		{
			return customerManagerRemote.countPendingReprocessSalesByClient(pvcode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PendingReprocessSalesArrayResultDTO resultDTO = new PendingReprocessSalesArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public PendingReprocessInventoryArrayResultDTO countPendingReprocessInventoryByClient(String pvcode)
	{
		try
		{
			return customerManagerRemote.countPendingReprocessInventoryByClient(pvcode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PendingReprocessInventoryArrayResultDTO resultDTO = new PendingReprocessInventoryArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public NotLoadedLocalDetailResultDTO getNotLoadedLocalDetail(NotLoadedLocalDetailInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getNotLoadedLocalDetail(initParamDTO, true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			NotLoadedLocalDetailResultDTO resultDTO = new NotLoadedLocalDetailResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public NotLoadedProductDetailResultDTO getNotLoadedProductDetail(NotLoadedProductDetailInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getNotLoadedProductDetail(initParamDTO, true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			NotLoadedProductDetailResultDTO resultDTO = new NotLoadedProductDetailResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public PendingReprocessSalesDetailResultDTO getPendingReprocessSalesDetail(PendingProcessSalesDetailInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getPendingReprocessSalesDetail(initParamDTO, true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PendingReprocessSalesDetailResultDTO resultDTO = new PendingReprocessSalesDetailResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public LateSalesDetailResultDTO getLateSalesByClient(LateSalesDetailInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getLateSalesByClient(initParamDTO, true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LateSalesDetailResultDTO resultDTO = new LateSalesDetailResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public CustomerServiceControlPanelResultDTO getCustomerServiceControlPanel(Long uskey, String pvcode)
	{

		CustomerServiceControlPanelResultDTO resultDTO = new CustomerServiceControlPanelResultDTO();

		userreportmanager.saveCompanySelectedAndAddCountUserProvider(uskey, pvcode);
		try
		{
			LastInventorySendArrayResultDTO lastInventorySendArrayResultDTO = customerManagerRemote.getLastSendInventoryByClient(pvcode);
			if (!lastInventorySendArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(lastInventorySendArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(lastInventorySendArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setLastInventorySendDTOs(lastInventorySendArrayResultDTO.getLastInventorySendDTOs());
			}

			LastSalesSendArrayResultDTO lastSalesSendArrayResultDTO = customerManagerRemote.getLastSendSalesByClient(pvcode);
			if (!lastSalesSendArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(lastSalesSendArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(lastSalesSendArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setLastSalesSendDTOs(lastSalesSendArrayResultDTO.getLastSalesSendDTOs());
			}

			NotLoadedLocalArrayResultDTO notLoadedLocalArrayResultDTO = customerManagerRemote.countNotLoadedLocalByClient(pvcode);
			if (!notLoadedLocalArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(notLoadedLocalArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(notLoadedLocalArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setNotLoadedLocalDTOs(notLoadedLocalArrayResultDTO.getNotLoadedLocalDTOs());
			}

			NotLoadedProductArrayResultDTO notLoadedProductArrayResultDTO = customerManagerRemote.countNotLoadedProductByClient(pvcode);
			if (!notLoadedProductArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(notLoadedProductArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(notLoadedProductArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setNotLoadedProductDTOs(notLoadedProductArrayResultDTO.getNotLoadedProductDTOs());
			}

			PendingReprocessSalesArrayResultDTO pendingReprocessSalesArrayResultDTO = customerManagerRemote.countPendingReprocessSalesByClient(pvcode);
			if (!pendingReprocessSalesArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(pendingReprocessSalesArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(pendingReprocessSalesArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setPendingReprocessSalesDTOs(pendingReprocessSalesArrayResultDTO.getPendingReprocessSalesDTOs());
			}

			PendingReprocessInventoryArrayResultDTO pendingReprocessInventoryArrayResultDTO = customerManagerRemote.countPendingReprocessInventoryByClient(pvcode);
			if (!pendingReprocessInventoryArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(pendingReprocessInventoryArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(pendingReprocessInventoryArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setPendingReprocessInventoryDTOs(pendingReprocessInventoryArrayResultDTO.getPendingReprocessInventoryDTOs());
			}

			LateSalesArrayResultDTO lateSalesArrayResultDTO = customerManagerRemote.countLateSalesByClient(pvcode);
			if (!lateSalesArrayResultDTO.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(lateSalesArrayResultDTO.getStatuscode());
				resultDTO.setStatusmessage(lateSalesArrayResultDTO.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setLateSalesDTOs(lateSalesArrayResultDTO.getLateSalesDTOs());
			}

			return resultDTO;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public PendingReprocessInventoryDetailResultDTO getPendingReprocessInventoryDetail(PendingProcessInventoryDetailInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getPendingReprocessInventoryDetail(initParamDTO, true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			PendingReprocessInventoryDetailResultDTO resultDTO = new PendingReprocessInventoryDetailResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public FileDownloadInfoResultDTO downloadNotLoadedLocalDetail(NotLoadedLocalDetailInitParamDTO initParamDTO, Long uskey)
	{

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		NotLoadedLocalDetailResultDTO notLoadedLocalDetailResultDTO = null;

		try
		{
			notLoadedLocalDetailResultDTO = customerManagerRemote.getNotLoadedLocalDetail(initParamDTO, false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");// modulo
																						// no
																						// disp.
		}

		NotLoadedLocalDetailDTO[] notLoadedLocalDetailDTOs = notLoadedLocalDetailResultDTO.getNotLoadedLocalDetailDTOs();

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_loc_title", initParamDTO.getLocale()));

		/*
		 * Rut Proveedor|Cod. Local. Proveedor|Nombre Cliente|Código Cliente
		 * BBR|Cod. Local Cliente|Nombre
		 * Local|Región|Ciudad|Comuna|Estado|Tipo|Dirección|Atributo 1|
		 * Atributo10
		 */

		DataColumn col01 = new DataColumn("rutprov", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "provider_rut", initParamDTO.getLocale()), String.class);
		DataColumn col02 = new DataColumn("codlocalprov", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "local_code_provider", initParamDTO.getLocale()), String.class);
		DataColumn col03 = new DataColumn("nombrecliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_name", initParamDTO.getLocale()), String.class);
		DataColumn col04 = new DataColumn("codcliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_code_BBR", initParamDTO.getLocale()), String.class);
		DataColumn col05 = new DataColumn("codloccliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "local_code_client", initParamDTO.getLocale()), String.class);
		DataColumn col06 = new DataColumn("nombrelocalprov", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "local_name", initParamDTO.getLocale()), String.class);
		DataColumn col07 = new DataColumn("region", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "region", initParamDTO.getLocale()), String.class);
		DataColumn col08 = new DataColumn("ciudad", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "city", initParamDTO.getLocale()), String.class);
		DataColumn col09 = new DataColumn("comuna", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "commune", initParamDTO.getLocale()), String.class);
		DataColumn col10 = new DataColumn("estado", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "status", initParamDTO.getLocale()), String.class);
		DataColumn col11 = new DataColumn("tipo", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "type", initParamDTO.getLocale()), String.class);
		DataColumn col12 = new DataColumn("direccion", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "address", initParamDTO.getLocale()), String.class);
		DataColumn col13 = new DataColumn("atrib1", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib1", initParamDTO.getLocale()), String.class);
		DataColumn col14 = new DataColumn("atrib2", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib2", initParamDTO.getLocale()), String.class);
		DataColumn col15 = new DataColumn("atrib3", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib3", initParamDTO.getLocale()), String.class);
		DataColumn col16 = new DataColumn("atrib4", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib4", initParamDTO.getLocale()), String.class);
		DataColumn col17 = new DataColumn("atrib5", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib5", initParamDTO.getLocale()), String.class);
		DataColumn col18 = new DataColumn("atrib6", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib6", initParamDTO.getLocale()), String.class);
		DataColumn col19 = new DataColumn("atrib7", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib7", initParamDTO.getLocale()), String.class);
		DataColumn col20 = new DataColumn("atrib8", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib8", initParamDTO.getLocale()), String.class);
		DataColumn col21 = new DataColumn("atrib9", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib9", initParamDTO.getLocale()), String.class);
		DataColumn col22 = new DataColumn("atrib10", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "atrib10", initParamDTO.getLocale()), String.class);
		DataColumn col23 = new DataColumn("retaildescription", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "retail_description", initParamDTO.getLocale()), String.class);

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
		dt0.addColumn(col14);
		dt0.addColumn(col15);
		dt0.addColumn(col16);
		dt0.addColumn(col17);
		dt0.addColumn(col18);
		dt0.addColumn(col19);
		dt0.addColumn(col20);
		dt0.addColumn(col21);
		dt0.addColumn(col22);
		dt0.addColumn(col23);

		DataRow row = null;
		NotLoadedLocalDetailDTO line = null;

		for (int i = 0; i < notLoadedLocalDetailDTOs.length; i++)
		{
			line = notLoadedLocalDetailDTOs[i];

			row = dt0.newRow();
			row.setCellValue("rutprov", initParamDTO.getPvcode());
			row.setCellValue("nombrecliente", line.getClientname());
			row.setCellValue("codcliente", line.getClientcode());
			row.setCellValue("codloccliente", line.getCodlocalclient());
			row.setCellValue("retaildescription", line.getDesclocalclient());

			dt0.addRow(row);
		}

		String clientcode = "";
		if (initParamDTO.getClkey().longValue() != -1)
		{
			clientcode = notLoadedLocalDetailDTOs[0].getClientcode();
		}
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_loc_title", initParamDTO.getLocale()) + "_" + initParamDTO.getPvcode() + "_" + clientcode;
		String realname = "NOTLOADEDLOCAL";

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "csv", downloadname, realname, uskey, null, "|");

		return resultDTO;

	}

	@Override
	public FileDownloadInfoResultDTO downloadNotLoadedProductDetail(NotLoadedProductDetailInitParamDTO initParamDTO, Long uskey)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		NotLoadedProductDetailResultDTO notLoadedProductDetailResultDTO = null;

		try
		{
			notLoadedProductDetailResultDTO = customerManagerRemote.getNotLoadedProductDetail(initParamDTO, false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");// modulo
																						// no
																						// disp.
		}

		NotLoadedProductDetailDTO[] notLoadedProductDetailDTOs = notLoadedProductDetailResultDTO.getNotLoadedProductDetailDTOs();

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_prod_title", initParamDTO.getLocale()));

		/*
		 * Razón Social|Rut Proveedor|Cod. Produ. Proveedor|Cliente|Codigo
		 * Cliente BBR|Sku Cliente|Conversión|Descripción cliente
		 */

		DataColumn col01 = new DataColumn("rz", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "social_reason_prov", initParamDTO.getLocale()), String.class);
		DataColumn col02 = new DataColumn("rutprov", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "provider_rut", initParamDTO.getLocale()), String.class);
		DataColumn col03 = new DataColumn("codprodprov", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "product_code_provider", initParamDTO.getLocale()), String.class);
		DataColumn col04 = new DataColumn("client", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client", initParamDTO.getLocale()), String.class);
		DataColumn col05 = new DataColumn("codclientbbr", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_code_BBR", initParamDTO.getLocale()), String.class);
		DataColumn col06 = new DataColumn("skucliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "product_code_client", initParamDTO.getLocale()), String.class);
		DataColumn col07 = new DataColumn("conversion", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "conversion", initParamDTO.getLocale()), String.class);
		DataColumn col08 = new DataColumn("clientdescription", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_description", initParamDTO.getLocale()), String.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);

		DataRow row = null;
		NotLoadedProductDetailDTO line = null;

		for (int i = 0; i < notLoadedProductDetailDTOs.length; i++)
		{
			line = notLoadedProductDetailDTOs[i];

			row = dt0.newRow();
			row.setCellValue("rz", initParamDTO.getProvidername());
			row.setCellValue("rutprov", initParamDTO.getPvcode());
			row.setCellValue("client", line.getClientname());
			row.setCellValue("codclientbbr", line.getClientcode());
			row.setCellValue("skucliente", line.getCodprodclient());
			row.setCellValue("clientdescription", line.getDescprodclient());

			dt0.addRow(row);
		}

		String clientcode = "";
		if (initParamDTO.getClkey().longValue() != -1)
		{
			clientcode = notLoadedProductDetailDTOs[0].getClientcode();
		}
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_prod_title", initParamDTO.getLocale()) + "_" + initParamDTO.getPvcode() + "_" + clientcode;
		String realname = "NOTLOADEDPROD";

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "csv", downloadname, realname, uskey, null, "|");

		return resultDTO;
	}

	@Override
	public FileDownloadInfoResultDTO downloadPendingReprocessSalesDetail(PendingProcessSalesDetailInitParamDTO initParamDTO, Long uskey)
	{

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		PendingReprocessSalesDetailResultDTO pendingReprocessSalesDetailResultDTO = null;

		try
		{
			pendingReprocessSalesDetailResultDTO = customerManagerRemote.getPendingReprocessSalesDetail(initParamDTO, false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");// modulo
																						// no
																						// disp.
		}

		PendingReprocessSalesDetailDTO[] pendingReprocessSalesDetailDTOs = pendingReprocessSalesDetailResultDTO.getPendingReprocessSalesDetailDTOs();

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_pendingsales_title", initParamDTO.getLocale()));

		/*
		 * Fecha, Cod Cliente, SKU,Cod Local,Vta Unidades, Monto IVA, Tipo Vta
		 * (OPC), Monto Costo
		 */

		DataColumn col01 = new DataColumn("fecha", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "date", initParamDTO.getLocale()), String.class);
		DataColumn col02 = new DataColumn("codcliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_code", initParamDTO.getLocale()), String.class);
		DataColumn col021 = new DataColumn("cliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client", initParamDTO.getLocale()), String.class);
		DataColumn col03 = new DataColumn("sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sku", initParamDTO.getLocale()), String.class);
		DataColumn col04 = new DataColumn("codlocal", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "local_code", initParamDTO.getLocale()), String.class);
		DataColumn col05 = new DataColumn("unidades", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sales_units", initParamDTO.getLocale()), Double.class);
		DataColumn col06 = new DataColumn("montoiva", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "iva_amount", initParamDTO.getLocale()), Double.class);
		DataColumn col07 = new DataColumn("tipoventa", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sales_type", initParamDTO.getLocale()), String.class);
		DataColumn col08 = new DataColumn("montocosto", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "cost_amount", initParamDTO.getLocale()), Double.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col021);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);

		DataRow row = null;
		PendingReprocessSalesDetailDTO line = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (int i = 0; i < pendingReprocessSalesDetailDTOs.length; i++)
		{
			line = pendingReprocessSalesDetailDTOs[i];

			row = dt0.newRow();
			row.setCellValue("fecha", line.getDate().format(formatter));
			row.setCellValue("codcliente", line.getClientcode());
			row.setCellValue("cliente", line.getClientname());
			row.setCellValue("sku", line.getSkuclient());
			row.setCellValue("codlocal", line.getLocalclient());
			row.setCellValue("unidades", line.getUnits());
			row.setCellValue("montoiva", line.getSalesamount());
			row.setCellValue("tipoventa", line.getSalestype());
			row.setCellValue("montocosto", line.getCostamount());

			dt0.addRow(row);
		}

		String clientcode = "";
		if (initParamDTO.getClkey().longValue() != -1)
		{
			clientcode = pendingReprocessSalesDetailDTOs[0].getClientcode();
		}
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_pendingsales_title", initParamDTO.getLocale()) + "_" + initParamDTO.getPvcode() + "_" + clientcode;
		String realname = "PENDINGSALES";

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "csv", downloadname, realname, uskey);

		return resultDTO;
	}

	@Override
	public FileDownloadInfoResultDTO downloadPendingReprocessInventoryDetail(PendingProcessInventoryDetailInitParamDTO initParamDTO, Long uskey)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		PendingReprocessInventoryDetailResultDTO pendingReprocessInventoryDetailResultDTO = null;

		try
		{
			pendingReprocessInventoryDetailResultDTO = customerManagerRemote.getPendingReprocessInventoryDetail(initParamDTO, false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");// modulo
																						// no
																						// disp.
		}

		PendingReprocessInventoryDetailDTO[] pendingReprocessInventoryDetailDTOs = pendingReprocessInventoryDetailResultDTO.getPendingReprocessInventoryDetailDTOs();

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_pendinginventory_title", initParamDTO.getLocale()));

		/*
		 * Fecha,Código Cliente, SKU, Código Local,Stock Unidades , Stock
		 * Monto,Transito, Quiebre,InStock,Mix,Días Venta
		 */

		DataColumn col01 = new DataColumn("fecha", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "date", initParamDTO.getLocale()), String.class);
		DataColumn col02 = new DataColumn("codcliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_code", initParamDTO.getLocale()), String.class);
		DataColumn col021 = new DataColumn("cliente", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client", initParamDTO.getLocale()), String.class);
		DataColumn col03 = new DataColumn("sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sku", initParamDTO.getLocale()), String.class);
		DataColumn col04 = new DataColumn("codlocal", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "local_code", initParamDTO.getLocale()), String.class);

		DataColumn col05 = new DataColumn("unidadesstock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "stock_units", initParamDTO.getLocale()), Double.class);
		DataColumn col06 = new DataColumn("montostock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "stock_amount", initParamDTO.getLocale()), Double.class);
		DataColumn col07 = new DataColumn("transito", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "transit", initParamDTO.getLocale()), Double.class);
		DataColumn col08 = new DataColumn("quiebre", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "break", initParamDTO.getLocale()), Integer.class);
		DataColumn col09 = new DataColumn("instock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "instock", initParamDTO.getLocale()), Integer.class);
		DataColumn col10 = new DataColumn("mix", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "mix", initParamDTO.getLocale()), Integer.class);
		DataColumn col11 = new DataColumn("diasventa", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "daysales", initParamDTO.getLocale()), Double.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col021);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);
		dt0.addColumn(col10);
		dt0.addColumn(col11);

		DataRow row = null;
		PendingReprocessInventoryDetailDTO line = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (int i = 0; i < pendingReprocessInventoryDetailDTOs.length; i++)
		{
			line = pendingReprocessInventoryDetailDTOs[i];

			row = dt0.newRow();
			row.setCellValue("fecha", line.getDate().format(formatter));
			row.setCellValue("codcliente", line.getClientcode());
			row.setCellValue("cliente", line.getClientname());
			row.setCellValue("sku", line.getSkuclient());
			row.setCellValue("codlocal", line.getLocalclient());
			row.setCellValue("unidadesstock", line.getStockunits());
			row.setCellValue("montostock", line.getStockamount());
			row.setCellValue("transito", line.getTransit());
			row.setCellValue("quiebre", line.getQ());
			row.setCellValue("instock", line.getInstock());
			row.setCellValue("mix", line.getMix());
			row.setCellValue("diasventa", line.getDaysales());

			dt0.addRow(row);
		}

		String clientcode = "";
		if (initParamDTO.getClkey().longValue() != -1)
		{
			clientcode = pendingReprocessInventoryDetailDTOs[0].getClientcode();
		}
		String downloadname = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "download_pendinginventory_title", initParamDTO.getLocale()) + "_" + initParamDTO.getPvcode() + "_" + clientcode;
		String realname = "PENDINGINV";

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "csv", downloadname, realname, uskey);

		return resultDTO;
	}

	/*
	 * @Override
	 * public PendingLoadFileResultDTO
	 * getPendingLoadProductFileByProvider(String pvcode) {
	 * PendingLoadFileResultDTO resultDTO = new PendingLoadFileResultDTO();
	 * PendingLoadFileInitParamDTO initParamDTO = new
	 * PendingLoadFileInitParamDTO();
	 * initParamDTO.setPvcode(pvcode);
	 * initParamDTO.setFiletype(PortalConstants.PRODUCTFILE);
	 * try {
	 * resultDTO =
	 * customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO,
	 * "P700");
	 * }
	 * return resultDTO;
	 * }
	 * /*@Override
	 * public PendingLoadFileResultDTO getPendingLoadLocalFileByProvider(String
	 * pvcode) {
	 * PendingLoadFileResultDTO resultDTO = new PendingLoadFileResultDTO();
	 * PendingLoadFileInitParamDTO initParamDTO = new
	 * PendingLoadFileInitParamDTO();
	 * initParamDTO.setPvcode(pvcode);
	 * initParamDTO.setFiletype(PortalConstants.LOCALFILE);
	 * try {
	 * resultDTO =
	 * customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO,
	 * "P700");
	 * }
	 * return resultDTO;
	 * }
	 * @Override
	 * public PendingLoadFileResultDTO
	 * getPendingLoadHomologatorFileByProvider(String pvcode) {
	 * PendingLoadFileResultDTO resultDTO = new PendingLoadFileResultDTO();
	 * PendingLoadFileInitParamDTO initParamDTO = new
	 * PendingLoadFileInitParamDTO();
	 * initParamDTO.setPvcode(pvcode);
	 * initParamDTO.setFiletype(PortalConstants.HOMOLOGATORFILE);
	 * try {
	 * resultDTO =
	 * customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO,
	 * "P700");
	 * }
	 * return resultDTO;
	 * }
	 */

	@Override
	public PendingLoadFilesResultDTO getPendingLoadFilesByProvider(String pvcode)
	{
		PendingLoadFilesResultDTO resultDTO = new PendingLoadFilesResultDTO();
		try
		{
			PendingLoadFileInitParamDTO initParamDTO = new PendingLoadFileInitParamDTO();
			initParamDTO.setPvcode(pvcode);
			initParamDTO.setFiletype(CustomerServiceConstants.PRODUCTFILE);

			PendingLoadFileResultDTO product = customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);

			if (!product.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(product.getStatuscode());
				resultDTO.setStatusmessage(product.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setProduct(product);
			}

			initParamDTO.setFiletype(CustomerServiceConstants.LOCALFILE);

			PendingLoadFileResultDTO location = customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);

			if (!location.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(location.getStatuscode());
				resultDTO.setStatusmessage(location.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setLocation(location);
			}

			initParamDTO.setFiletype(CustomerServiceConstants.HOMOLOGATORFILE);

			PendingLoadFileResultDTO homologation = customerManagerRemote.getPendingLoadFileByProviderAndType(initParamDTO);

			if (!homologation.getStatuscode().equals("0"))
			{
				resultDTO.setStatuscode(homologation.getStatuscode());
				resultDTO.setStatusmessage(homologation.getStatusmessage());
				return resultDTO;
			}
			else
			{
				resultDTO.setHomologation(homologation);
			}

			return resultDTO;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public BaseResultDTO addOrUpdatePendingProductFileByProvider(AddOrUpdatePendingFileInitParamDTO initParamDTO)
	{

		try
		{
			return customerManagerRemote.addOrUpdatePendingFileByProviderAndType(initParamDTO, CustomerServiceConstants.PRODUCTFILE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}

	}

	@Override
	public BaseResultDTO addOrUpdatePendingLocalFileByProvider(AddOrUpdatePendingFileInitParamDTO initParamDTO)
	{

		try
		{
			return customerManagerRemote.addOrUpdatePendingFileByProviderAndType(initParamDTO, CustomerServiceConstants.LOCALFILE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public BaseResultDTO addOrUpdatePendingHomologatorFileByProvider(AddOrUpdatePendingFileInitParamDTO initParamDTO)
	{

		try
		{
			return customerManagerRemote.addOrUpdatePendingFileByProviderAndType(initParamDTO, CustomerServiceConstants.HOMOLOGATORFILE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");
		}
	}

	@Override
	public BaseResultsDTO doUploadMasters(UploadMastersLoadInitParamDTO initParams)
	{

		BaseResultsDTO result = new BaseResultsDTO();
		try
		{
			String extension = FileHandlerUtils.getFileExtension(initParams.getFileUploadInitParamDTO().getFileName());

			if (extension != null)
			{
				if (!extension.equalsIgnoreCase("csv"))
				{
					BaseResultsDTO resultDTO = new BaseResultsDTO();
					return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P7003");
				}
				else
				{
					// 1.VALIDAR SI EXISTE EL ARCHIVO
					File inputFile = new File(initParams.getFileUploadInitParamDTO().getFilePath() + initParams.getAddOrUpdatePendingFileInitParamDTO().getFilenameprov());
					boolean exists = inputFile.exists();
					if (exists)
					{
						// 2.CAMBIAR EL NOMBRE DEL ARCHIVO POR EL DE BBR
						FileHandlerUtils.getInstance().renameFile(initParams.getFileUploadInitParamDTO().getFilePath(),
								initParams.getAddOrUpdatePendingFileInitParamDTO().getFilenameprov(),
								initParams.getAddOrUpdatePendingFileInitParamDTO().getFilenamebbr());
					}
					else
					{
						System.out.println("File not found. " + initParams.getFileUploadInitParamDTO().getFilePath() + initParams.getAddOrUpdatePendingFileInitParamDTO().getFilenameprov());
					}

					AddOrUpdatePendingFileInitParamDTO initParamDTO = initParams.getAddOrUpdatePendingFileInitParamDTO();

					if (initParams.getFileType().equals(CustomerServiceConstants.HOMOLOGATORFILE))
					{
						this.addOrUpdatePendingHomologatorFileByProvider(initParamDTO);
					}
					else if (initParams.getFileType().equals(CustomerServiceConstants.LOCALFILE))
					{
						this.addOrUpdatePendingLocalFileByProvider(initParamDTO);
					}
					else if (initParams.getFileType().equals(CustomerServiceConstants.PRODUCTFILE))
					{
						this.addOrUpdatePendingProductFileByProvider(initParamDTO);
					}
				}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result,
					"P4000");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P7003");
		}

		return result;
	}

	@Override
	public ProductProviderArrayResultDTO searchProviderProductWhitoutHomologation(SearchProductProviderWithoutHomologationInitParamDTO initParamDTO)
	{

		try
		{
			return customerManagerRemote.searchProviderProductWhitoutHomologation(initParamDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ProductProviderArrayResultDTO resultDTO = new ProductProviderArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");

		}
	}

	@Override
	public ProductProviderArrayResultDTO searchProviderProduct(SearchProductProviderInitParamDTO initParamDTO)
	{

		try
		{
			return customerManagerRemote.searchProviderProduct(initParamDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ProductProviderArrayResultDTO resultDTO = new ProductProviderArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");

		}
	}

	@Override
	public BaseResultDTO associateProductProviderProductClient(AssociateProductProviderProductClientInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.associateProductProviderProductClient(initParamDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");

		}
	}

	@Override
	public void saveCompanySelectedAndAddCountUserProvider(Long uskey, String pvcode)
	{
		userreportmanager.saveCompanySelectedAndAddCountUserProvider(uskey, pvcode);
	}

	@Override
	public ClientArrayResultDTO getAllClients()
	{
		try
		{
			return customerManagerRemote.getAllClients();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ClientArrayResultDTO resultDTO = new ClientArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");

		}
	}

	@Override
	public ProductReportResultDTO getProductReport(ProductReportInitParamDTO initParamDTO)
	{
		try
		{
			return customerManagerRemote.getProductReport(initParamDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ProductReportResultDTO resultDTO = new ProductReportResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");

		}

	}

	@Override
	public FileDownloadInfoResultDTO downloadProductReport(ProductReportInitParamDTO initParamDTO, Long uskey)
	{

		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		ProductReportResultDTO productReportResultDTO = null;

		try
		{

			productReportResultDTO = customerManagerRemote.getProductReport(initParamDTO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P700");// modulo
																						// no
																						// disp.
		}

		ProductReportDataDTO[] productReportDataDTOs = productReportResultDTO.getProductReportDataDTOs();

		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("REPORT_PROD_" + initParamDTO.getPvcode() + "_" + productReportDataDTOs[0].getClcode());

		/*
		 * o Cliente: código Retail, por ejemplo CL0102.
		 * o Descripción cliente: nombre del cliente, por ejemplo Jumbo.
		 * o Producto cliente: SKU cliente.
		 * o Producto proveedor: SKU proveedor.
		 * o Descripción producto proveedor.
		 * o Descripción marca proveedor.
		 * o Cp_descn1
		 * o Cp_descn2
		 * o Cp_descn3
		 */
		DataColumn col01 = new DataColumn("client", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client", initParamDTO.getLocale()), String.class);
		DataColumn col02 = new DataColumn("client_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client_description", initParamDTO.getLocale()), String.class);
		DataColumn col03 = new DataColumn("product_code_client", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "product_code_client", initParamDTO.getLocale()), String.class);
		DataColumn col04 = new DataColumn("product_code_provider", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "product_code_provider", initParamDTO.getLocale()), String.class);
		DataColumn col05 = new DataColumn("product_provider_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "product_provider_description", initParamDTO.getLocale()), String.class);
		DataColumn col06 = new DataColumn("trademark_provider", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "trademark_provider", initParamDTO.getLocale()), String.class);
		DataColumn col07 = new DataColumn("catn1", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "catn1", initParamDTO.getLocale()), String.class);
		DataColumn col08 = new DataColumn("catn2", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "catn2", initParamDTO.getLocale()), String.class);
		DataColumn col09 = new DataColumn("catn3", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "catn3", initParamDTO.getLocale()), String.class);

		dt0.addColumn(col01);
		dt0.addColumn(col02);
		dt0.addColumn(col03);
		dt0.addColumn(col04);
		dt0.addColumn(col05);
		dt0.addColumn(col06);
		dt0.addColumn(col07);
		dt0.addColumn(col08);
		dt0.addColumn(col09);

		DataRow row = null;
		ProductReportDataDTO line = null;

		for (int i = 0; i < productReportDataDTOs.length; i++)
		{
			line = productReportDataDTOs[i];

			row = dt0.newRow();
			row.setCellValue("client", line.getClcode());
			row.setCellValue("client_description", line.getCldesc());
			row.setCellValue("product_code_client", line.getProductclientcode());
			row.setCellValue("product_code_provider", line.getProductprovidercode());
			row.setCellValue("product_provider_description", line.getProductdescription());
			row.setCellValue("trademark_provider", line.getBrand());
			row.setCellValue("catn1", line.getCatn1());
			row.setCellValue("catn2", line.getCatn2());
			row.setCellValue("catn3", line.getCatn3());

			dt0.addRow(row);
		}

		String downloadname = "REPORT_PROD_" + initParamDTO.getPvcode() + "_" + productReportDataDTOs[0].getClcode();
		String realname = "PRODUCTREPORT";

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, "csv", downloadname, realname, uskey, null, "|");

		return resultDTO;
	}

	
	public FileDownloadInfoResultDTO downloadScoreCardDetailReport(ScoreCardTableBbrDTO[] data, Long uskey)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		// Formato de fecha hora para descargas
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();
		
		//	Formato de fecha en el excel
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		

		// Construccion del excel
		// 1 - Obtener el nombre del archivo
		String downloadfilename = "";
		String realfilename = "";
					

		downloadfilename = "Reporte_OC_Recibidas " + now.format(dtf) + " hrs.xls";				
		realfilename = "OC_RECEIVED" + now.format(dtf) + " hrs_"+ uskey + ".xls";

		DataRow row = null;
		
		
		// Escribir descripcion del filtro seleccionado
		DataTable dt0 = new DataTable("REPORTE_OC_RECIBIDAS");

		DataColumn col01 = new DataColumn("ocnumber", "Número OC", Long.class);
		DataColumn col02 = new DataColumn("retail", "Retail", String.class);
		DataColumn col03 = new DataColumn("sentdate", "Fecha de Envío", String.class);
		DataColumn col04 = new DataColumn("state", "Estado", String.class);
			
		dt0.addColumn(col01);		dt0.addColumn(col02);		dt0.addColumn(col03);		dt0.addColumn(col04);
		
		// Carga datos en el reporte
		ScoreCardTableBbrDTO lineData = null;
		
		for (int i = 0 ; i < data.length; i++){
			lineData = data[i];			
			row = dt0.newRow();
			
			row.setCellValue("ocnumber", lineData.getNumdoc());
			row.setCellValue("retail", lineData.getRetail());
			row.setCellValue("sentdate", lineData.getFecha() != null ? dtf2.format(lineData.getFecha()) : "");
			row.setCellValue("state", lineData.getEstado());
			
			dt0.addRow(row);			
		}
	
		
		DataColumnStyleInfo info = new DataColumnStyleInfo();
		
		XLSConverterPOI converter = new XLSConverterPOI();
		converter.setExcelheaderbold(true);
		converter.setShowexceltableborder(true);

		try {
			converter.ExportToXLS(dt0, info, PortalConstants.getInstance().getFileTransferPath() + realfilename, Charset.forName("UTF-16"));
		} catch (IOException e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P300");
		}
		
		resultDTO.setDownloadfilename(downloadfilename);
		resultDTO.setRealfilename(realfilename);
		return resultDTO;	
		
	}
	
	
	
	
}

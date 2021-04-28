package bbr.b2b.portal.classes.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.vaadin.data.TreeData;

import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.modules.customer_service.ScoreCardDetails;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.modules.cool.AdvancedReport;
import bbr.b2b.portal.modules.cool.AttentionRequests;
import bbr.b2b.portal.modules.cool.CollaborativeProjects;
import bbr.b2b.portal.modules.customer.ApprovedProducts;
import bbr.b2b.portal.modules.customer.ControlPanelManagement;
import bbr.b2b.portal.modules.customer.MastersLoadManagement;
import bbr.b2b.portal.modules.customer.PendingReprocessInventoryManagement;
import bbr.b2b.portal.modules.customer.PendingReprocessSalesManagement;
import bbr.b2b.portal.modules.customer.RequestReport;
import bbr.b2b.portal.modules.customer.ScoreCardManagement;
// import bbr.b2b.portal.modules.exagon.ExagoDashboardReport;
// import bbr.b2b.portal.modules.exagon.ExagonReport;
import bbr.b2b.portal.modules.external.QlikExternalModule;
import bbr.b2b.portal.modules.fep.AttributeStandardizer;
import bbr.b2b.portal.modules.fep.ManageAttributes;
import bbr.b2b.portal.modules.fep.ManagePrivileges;
import bbr.b2b.portal.modules.fep.ProductsManagement;
import bbr.b2b.portal.modules.fep.ProductsReleases;
import bbr.b2b.portal.modules.fep.ProductsStatus;
import bbr.b2b.portal.modules.fep.TemplatesManagement;
import bbr.b2b.portal.modules.logistic.PucharseOrdersReport;
import bbr.b2b.portal.modules.stockpool.AlertsReport;
import bbr.b2b.portal.modules.stockpool.AvailabilityReport;
import bbr.b2b.portal.modules.stockpool.AvailableStockReport;
import bbr.b2b.portal.modules.stockpool.ProductHomologationReport;
import bbr.b2b.portal.modules.stockpool.ProductWithoutHomologationReport;
import bbr.b2b.portal.modules.stockpool.StockPoolReport;
import bbr.b2b.portal.modules.users.audit.UserAudit;
import bbr.b2b.portal.modules.users.contact_zone.ListRetailsContacts;
import bbr.b2b.portal.modules.users.contact_zone.ProvidersContactsManagement;
import bbr.b2b.portal.modules.users.management.CompaniesClassificationManagement;
import bbr.b2b.portal.modules.users.management.FaqManagement;
import bbr.b2b.portal.modules.users.management.MandatoryPositionsManagement;
import bbr.b2b.portal.modules.users.management.MarketPlace;
import bbr.b2b.portal.modules.users.management.NotificationManagement;
import bbr.b2b.portal.modules.users.management.PopupAuditManagement;
import bbr.b2b.portal.modules.users.management.ProfilesUsersManagement;
import bbr.b2b.portal.modules.users.management.ProvidersContactsAdministrator;
import bbr.b2b.portal.modules.users.management.RequestsManagement;
import bbr.b2b.portal.modules.users.management.RetailsContactsManagement;
import bbr.b2b.portal.modules.users.management.UserManagement;
import bbr.b2b.portal.modules.users.management.UserRequestsManagement;
import bbr.b2b.users.report.classes.UserTreeFunctionalityDTO;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class FunctionalityMngr
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static FunctionalityMngr instance = new FunctionalityMngr();

	public static FunctionalityMngr getInstance()
	{
		return instance;
	}

	private HashMap<Long, UserTreeFunctionalityDTO>				mapFunctionalitiesId		= new HashMap<Long, UserTreeFunctionalityDTO>();
	private HashMap<String, UserTreeFunctionalityDTO>			mapFunctionalitiesCode		= new HashMap<String, UserTreeFunctionalityDTO>();
	private HashMap<Long, ArrayList<UserTreeFunctionalityDTO>>	mapFunctionalitiesParentId	= new HashMap<>();

	private UserTreeFunctionalityDTO							rootFunctionality;

	public void setRootFunctionality(UserTreeFunctionalityDTO root)
	{
		this.rootFunctionality = root;
	}

	public UserTreeFunctionalityDTO getRootFunctionality()
	{
		return this.rootFunctionality;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
//	private FunctionalityMngr()
//	{
//
//	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public void initializeFunctionalitiesMap(UserTreeFunctionalityDTO[] functionalities)
	{
		if (functionalities != null && functionalities.length > 0)
		{
			this.mapFunctionalitiesId = new HashMap<>();
			this.mapFunctionalitiesCode = new HashMap<>();
			this.mapFunctionalitiesParentId = new HashMap<>();
			Arrays.sort(functionalities, new Comparator<UserTreeFunctionalityDTO>()
			{
				@Override
				public int compare(UserTreeFunctionalityDTO o1, UserTreeFunctionalityDTO o2)
				{
					return Integer.compare(o1.getVisualorder(), o2.getVisualorder());
				}

			});
			for (UserTreeFunctionalityDTO functionality : functionalities)
			{
				this.mapFunctionalitiesId.put(functionality.getId(), functionality);
				this.mapFunctionalitiesCode.put(functionality.getName(), functionality);

				if (!mapFunctionalitiesParentId.containsKey(functionality.getParentkey()))
				{
					mapFunctionalitiesParentId.put(functionality.getParentkey(), new ArrayList<>());
				}

				mapFunctionalitiesParentId.get(functionality.getParentkey()).add(functionality);
			}

		}

	}

	public BbrModule getModuleByCode(String code, BbrUI parent)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.CUS_PHO: // ProductosHomologados
				result = new ApprovedProducts(parent);
				break;
			case ModulesCodes.CUS_DOC: // DetalleDeOrdenesDeCompra
				result = new ScoreCardDetails(parent);
				break;
			case ModulesCodes.CUS_SCA: // ScoreCard
				result = new ScoreCardManagement(parent);
				break;
			case ModulesCodes.CUS_PAC: // ControlPanel
				result = new ControlPanelManagement(parent);
				break;
			case ModulesCodes.CUS_REPVTA: // ReprocesoVentasPendientes
				result = new PendingReprocessSalesManagement(parent);
				break;
			case ModulesCodes.CUS_REPINV: // ReprocesoInventarioPendientes
				result = new PendingReprocessInventoryManagement(parent);
				break;
			case ModulesCodes.CUS_CMA: // CargaDeMaestros
				result = new MastersLoadManagement(parent);
				break;
			case ModulesCodes.CUS_RES: // Monitor Solicitudes
				result = new RequestReport(parent);
				break;
				
			case ModulesCodes.USR_CU1300: // Adm. Usuarios
				result = new UserManagement(parent, this);
				break;
			case ModulesCodes.USR_NOT: // Adm. Notificaciones
				result = new NotificationManagement(parent);
				break;
			case ModulesCodes.USR_ACR: // Adm. Contactos Retail
				result = new RetailsContactsManagement(parent);
				break;
			case ModulesCodes.USR_CRT: // Listar Contactos Retail
				result = new ListRetailsContacts(parent);
				break;
			case ModulesCodes.USR_CPV: // Contactos Proveedor
				result = new ProvidersContactsManagement(parent);
				break;
			case ModulesCodes.USR_CTO_00: // Adm. Contactos Proveedor
				result = new ProvidersContactsAdministrator(parent);// providercontacts
				break;
			case ModulesCodes.USR_CTO_03: // Adm.Contactos Obligatorios
				result = new MandatoryPositionsManagement(parent);
				break;
			case ModulesCodes.USR_CTO_04: // Adm.Clasificacion de empresas
				result = new CompaniesClassificationManagement(parent);
				break;
			case ModulesCodes.USR_FAQ: // Adm. FAQs
				result = new FaqManagement(parent);
				break;
			case ModulesCodes.USR_GPV: // Solicitud de usuarios
				result = new UserRequestsManagement(parent);
				break;
			case ModulesCodes.USR_GRT_04: // Adm. Autorización Perfiles
				result = new ProfilesUsersManagement(parent);
				break;
			case ModulesCodes.USR_GRT_01: // Gestion de Solicitudes
				result = new RequestsManagement(parent);
				break;
			case ModulesCodes.USR_APP: // Adm. Auditoría de Pop-Up
				result = new PopupAuditManagement(parent);
				break;
			case ModulesCodes.USR_AUD: // Auditoría de Usuarios
				result = new UserAudit(parent);
				break;
			case ModulesCodes.QLIK_CU100: // Módulo Qlik
				result = new QlikExternalModule(parent, "https://sense-demo.qlik.com/sense/app/b5b0919e-9456-44cf-a8d6-8f54ffa400ad/sheet/dCLJQ/state/analysis");
				break;
			case ModulesCodes.COO_SOA: // Solicitudes de Atención
				result = new AttentionRequests(parent);
				break;
			case ModulesCodes.COO_PRC: // Proyectos Colaborativos
				result = new CollaborativeProjects(parent);
				break;
			case ModulesCodes.COO_LOGI: // Reporte Avanzado
				result = new AdvancedReport(parent);
				break;
			case ModulesCodes.FEP_ATT_C: // Administrar Atrib. Productos
				result = new ManageAttributes(parent, FEPConstants.MODULE_TYPE_NAME_CP);
				break;
			case ModulesCodes.FEP_PLA_C: // Administrar Plantillas Atributos
				result = new TemplatesManagement(parent, FEPConstants.ARTICLETYPE_CLASS_NAME_CP);
				break;
			case ModulesCodes.FEP_PRI_C: // Administrar Privilegios
				result = new ManagePrivileges(parent);
				break;
			case ModulesCodes.FEP_PROV_C: // Nuevos productos
				result = new ProductsReleases(parent);
				break;
			case ModulesCodes.FEP_ADM_PROD: // Administracion de Productos
				result = new ProductsManagement(parent);
				break;
			case ModulesCodes.FEP_EST: // Estado
				result = new ProductsStatus(parent);
				break;
			case ModulesCodes.FEP_ATT_HOM: // Homologador de atributos
				result = new AttributeStandardizer(parent);
				break;
			case ModulesCodes.LOG_OC: // ÓrdenesDeCompra
				result = new PucharseOrdersReport(parent, this);
				break;
			case ModulesCodes.STKP_PNLCTL: // Panel de Control
				result = new StockPoolReport(parent, this);
				break;
			case ModulesCodes.STKP_STKA: // stock Disponible
				result = new AvailableStockReport(parent, this);
				break;
			case ModulesCodes.STKP_HOMOLOG: // Productos homologados
				result = new ProductHomologationReport(parent);
				break;
			case ModulesCodes.STKP_WITHOUT_HOMOLOG: // Productos sin homologados
				result = new ProductWithoutHomologationReport(parent);
				break;
			case ModulesCodes.STKP_ALERT: // Aletas
				result = new AlertsReport(parent);
				break;
			case ModulesCodes.STKP_DISP: // Disponibilidad de productos
				result = new AvailabilityReport(parent);
				break;
			case ModulesCodes.AST_MKPL: // MarketPlace
				result = new MarketPlace(parent);
				break;	
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}

	public BbrModule getModuleByCodeAndCardInfo(String code, BbrUI parent, ControlPanelCardInfo cardInfo)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.CUS_REPVTA: // ReprocesoVentasPendientes
				result = new PendingReprocessSalesManagement(parent, cardInfo);
				break;
			case ModulesCodes.CUS_REPINV: // ReprocesoInventarioPendientes
				result = new PendingReprocessInventoryManagement(parent, cardInfo);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}

	public BbrModule getModuleByCodeAndState(String code, BbrUI parent, String state)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.CUS_DOC: // DetalleDeOrdenesDeCompra
				result = new ScoreCardDetails(parent, state);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}
	
	public BbrModule getModuleByAvailableStockCodeState(String code, BbrUI parent, String codeState)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.STKP_STKA: //stock Disponible
				result = new AvailableStockReport(parent, this, codeState);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}

	public BbrModule getModuleByAlertReportCodeState(String code, BbrUI parent, String codeState)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.STKP_ALERT: //stock Disponible
				result = new AlertsReport(parent, this, codeState);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}
	
	public BbrModule getModuleByProductWithoutHomologation(String code, BbrUI parent, String codeState)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.STKP_WITHOUT_HOMOLOG: // Productos sin homologados
				result = new ProductWithoutHomologationReport(parent, this, codeState);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}
	
	public BbrModule getModuleByAvailabilityReport(String code, BbrUI parent, String clientName)
	{
		BbrModule result = null;
		switch (code)
		{
			case ModulesCodes.STKP_DISP: // Disponibilidad de productos
				result = new AvailabilityReport(parent, this, clientName);
				break;
			default:
				break;
		}

		result = initializeModule(code, result);

		return result;
	}
	
	public String getAppFunctionalityPath(Long functionalityId)
	{
		String result = "";
		UserTreeFunctionalityDTO appfunctionality = this.mapFunctionalitiesId.get(functionalityId);
		if (appfunctionality != null)
		{
			if (appfunctionality.getParentkey() != null && appfunctionality.getParentkey() > 0)
			{
				result = getAppFunctionalityPath(appfunctionality.getParentkey()) + "/"
						+ BbrUtils.getInstance().replaceSlashChar(appfunctionality.getDescription(), "");
			}
			else
			{
				result = "/App Home";
			}
		}

		return result;
	}

	public String getAppFunctionalityPath(String functionalityCode)
	{
		String result = "";
		UserTreeFunctionalityDTO appfunctionality = this.mapFunctionalitiesCode.get(functionalityCode);
		if (appfunctionality != null)
		{
			result = this.getAppFunctionalityPath(appfunctionality.getId());
		}

		return result;
	}

	public Boolean hasFunctionalityAssigned(String functionalityCode)
	{
		return (this.mapFunctionalitiesCode.get(functionalityCode) != null) ? true : false;
	}

	public UserTreeFunctionalityDTO[] getFuntionalitiesByParenId(long parentId)
	{
		UserTreeFunctionalityDTO[] result = null;

		if (mapFunctionalitiesParentId != null && mapFunctionalitiesParentId.size() > 0)
		{
			ArrayList<UserTreeFunctionalityDTO> functionalities = mapFunctionalitiesParentId.get(parentId);
			if (functionalities != null && functionalities.size() > 0)
			{
				result = functionalities.toArray(new UserTreeFunctionalityDTO[functionalities.size()]);
			}
		}

		return result;
	}

	public UserTreeFunctionalityDTO getUserFunctionalityById(Long id)
	{
		return mapFunctionalitiesId.get(id);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private BbrModule initializeModule(String code, BbrModule module)
	{
		if (module != null)
		{
			String name = I18NManager.getI18NString(BbrUtilsResources.RES_MENU, code);
			module.setModuleCode(code);
			module.setModuleName(name);
			module.setModulePath(this.getAppFunctionalityPath(code));
			module.initializeView();
		}

		return module;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	public TreeData<UserTreeFunctionalityDTO> getTreeData(UserTreeFunctionalityDTO item, UserTreeFunctionalityDTO parent, TreeData<UserTreeFunctionalityDTO> treeData)
	{
		if (treeData == null)
		{
			treeData = new TreeData<>();
		}

		treeData.addItem(parent, item);

		UserTreeFunctionalityDTO[] functionalities = FunctionalityMngr.getInstance().getFuntionalitiesByParenId(item.getId());
		if (functionalities != null)
		{
			item.setChildren(functionalities);
			for (UserTreeFunctionalityDTO node : functionalities)
			{
				node.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MENU, node.getName()));

				if (!(node.isTerminal() || node.isHasfunctionalities()))
				{
					treeData = getTreeData(node, item, treeData);
				}
				else
				{
					treeData.addItem(item, node);
				}

			}
		}

		return treeData;
	}
}

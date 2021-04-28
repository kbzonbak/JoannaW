//package bbr.b2b.portal.classes.wrappers.management;
//
//import bbr.b2b.portal.classes.basics.CustomUserDTO;
//import bbr.b2b.portal.classes.constants.EnumTicketOperation;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.trac.report.classes.SupervisorTicketInitParamDTO;
//import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//
//public class SupervisorTicketOperationInfo 
//{
//	private TicketReportDataDTO ticket	= null 	;
//	private Long vendorId				= null	;
//	private String vendorName			= null	;
//	private String htmlcontent			= null	;
//	private CustomUserDTO user 			= null;
//	
//	private EnumTicketOperation ticketOperation = null;
//	
//	
//	public String getHtmlcontent() {
//		return htmlcontent;
//	}
//
//	public void setHtmlcontent(String htmlcontent) {
//		this.htmlcontent = htmlcontent;
//	}
//
//	public TicketReportDataDTO getTicket() {
//		return ticket;
//	}
//
//	public void setTicket(TicketReportDataDTO ticket) {
//		this.ticket = ticket;
//	}
//
//	public Long getVendorId() {
//		return vendorId;
//	}
//
//	public void setVendorId(Long vendorId) {
//		this.vendorId = vendorId;
//	}
//
//	public String getVendorName() {
//		return vendorName;
//	}
//
//	public void setVendorName(String vendorName) {
//		this.vendorName = vendorName;
//	}
//
//	public EnumTicketOperation getTicketOperation() {
//		return ticketOperation;
//	}
//
//	public void setTicketOperation(EnumTicketOperation ticketOperation) {
//		this.ticketOperation = ticketOperation;
//	}
//
//	public CustomUserDTO getUser() {
//		return user;
//	}
//
//	public void setUser(CustomUserDTO user) {
//		this.user = user;
//	}
//	public SupervisorTicketOperationInfo(TicketReportDataDTO ticket, Long vendorId, String vendorName, String htmlcontent, EnumTicketOperation ticketOperation, CustomUserDTO user) 
//	{
//		super();
//		this.ticket = ticket;
//		this.vendorId = vendorId;
//		this.vendorName = vendorName;
//		this.htmlcontent = htmlcontent;
//		this.user = user;
//	}
//
//	public String validateCreateData()
//	{
//		String result = null ;
//		
//		if(this.user == null)
//		{
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "valid_analyst");
//		}
//		
//		return result;
//	}
//	
//	public SupervisorTicketInitParamDTO toSupervisorTicketInitParamDTO()
//	{
//		SupervisorTicketInitParamDTO result = new SupervisorTicketInitParamDTO();
//
//		result.setVendorId(this.vendorId);
//		result.setVendorName(this.vendorName);
//		result.setTicketId(this.ticket.getTicketid());
//		result.setDescription(this.htmlcontent);
//		result.setUserId(this.user.getId());
//		return result;
//	}
//
//
//
//}

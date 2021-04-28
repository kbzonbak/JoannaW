//package bbr.b2b.portal.classes.wrappers.management;
//
//import bbr.b2b.portal.classes.constants.EnumTicketOperation;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//import bbr.b2b.trac.report.classes.VendorTicketInitParamDTO;
//
//public class VendorTicketOperationInfo 
//{
//	private TicketReportDataDTO ticket	= null 	;
//	private Long vendorId				= null	;
//	private String vendorName			= null	;
//	private String htmlcontent			= null	;
//	private EnumTicketOperation ticketOperation = null;
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
//	public VendorTicketOperationInfo(TicketReportDataDTO ticket, Long vendorId, String vendorName, String htmlcontent, EnumTicketOperation ticketOperation) 
//	{
//		super();
//		this.ticket = ticket;
//		this.vendorId = vendorId;
//		this.vendorName = vendorName;
//		this.htmlcontent = htmlcontent;
//	}
//
//	public String validateCreateData()
//	{
//		String result = null ;
//
//		if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
//		{
//
//			switch (this.ticketOperation) 
//			{
//			case ACCEPT:
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "valid_accept");
//				break;
//			case CLOSE:
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "valid_close");
//				break;
//			case REOPEN:
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "valid_reopen");
//				break;
//			case REPLY:
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "valid_reply");
//				break;
//
//			default:
//				break;
//			}
//		}
//
//		return result;
//	}
//	
////	public VendorTicketInitParamDTO toVendorTicketInitParamDTO()
////	{
////		VendorTicketInitParamDTO result = new VendorTicketInitParamDTO();
////
////		result.setVendorId(this.vendorId);
////		result.setVendorName(this.vendorName);
////		result.setTicketId(this.ticket.getTicketid());
////		result.setDescription(this.htmlcontent);
////
////		return result;
////	}
//
//}

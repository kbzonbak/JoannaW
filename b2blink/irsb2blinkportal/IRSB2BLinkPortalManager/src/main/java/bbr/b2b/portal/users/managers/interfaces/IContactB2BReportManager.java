package bbr.b2b.portal.users.managers.interfaces;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.FileUploadInitParamDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.ContactDataInitParamDTO;
import bbr.b2b.users.report.classes.ContactPhoneTypeResultDTO;
import bbr.b2b.users.report.classes.ContactPositionArrayResultDTO;
import bbr.b2b.users.report.classes.ContactProvArrayResultDTO;
import bbr.b2b.users.report.classes.EditContactProviderInfoResultDTO;
import bbr.b2b.users.report.classes.CompanyClassificationArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyClassificationInitParamDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportInitParamDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportResultDTO;
import bbr.b2b.users.report.classes.ContactB2BArrayResultDTO;
import bbr.b2b.users.report.classes.ContactLogDataDTO;
import bbr.b2b.users.report.classes.LogInfoResultDTO;
import bbr.b2b.users.report.classes.LogInfoUserDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.ProviderContactReportInitParamDTO;
import bbr.b2b.users.report.classes.ProviderContactReportResultDTO;
import bbr.b2b.users.report.classes.SendMailContactInitParamDTO;

public interface IContactB2BReportManager extends IGenericEJBInterface{

	public ContactB2BArrayResultDTO getRetailerContacts(int pageNumber, int rows, boolean isPaginated);
	public FileDownloadInfoResultDTO downloadRetailerContacts(Long userId, String fileformat, Locale locale);
	public BaseResultsDTO doUploadRetailerContacts(FileUploadInitParamDTO initParams, Long userId);	
	public BaseResultDTO sendEmailContact(SendMailContactInitParamDTO initParams, String userRut);
	
	public PositionResultDTO getAllProvidersAndPositions();
	public ProviderContactReportResultDTO getProviderContactReportByProviderAndPosition(ProviderContactReportInitParamDTO initParams, boolean isPaginated, Long uskey);//reporteadm
	public FileDownloadInfoResultDTO downloadProviderContactReportByProviderAndPosition(ProviderContactReportInitParamDTO initParams, Long uskey, Locale locale, String fileformat);
	
	public ContactProvArrayResultDTO getProviderContacts(Long pvkey);
	public PositionResultDTO validateContactProviders(Long pvkey, Long uskey);
	public BaseResultDTO addProviderContact(ContactDataInitParamDTO contactProvWs, Long pvkey, ContactLogDataDTO logdata);
	public BaseResultDTO updateProviderContact(ContactDataInitParamDTO contactProvWs, Long pvkey, ContactLogDataDTO logdata);
	public BaseResultDTO deleteProviderContact(Long contactProvId, Long pvkey, ContactLogDataDTO logdata);
	public BaseResultDTO doConfirmContactInformation(ContactLogDataDTO logdata, Long pvkey);
	public ContactPhoneTypeResultDTO getPhoneTypes();
	public ContactPositionArrayResultDTO getPositionsAndContactAssigned(Long ctpkey, Long prkey);
	public CompanyClassificationArrayResultDTO getCompanyClassifications();
	public BaseResultDTO doAddCompanyClassification(CompanyClassificationInitParamDTO initParamDTO);
	public BaseResultDTO doEditCompanyClassification(CompanyClassificationInitParamDTO initParamDTO);
	public BaseResultDTO doDeleteCompanyClassification(Long[] csekey, LogInfoUserDTO loginfo);
	public LogInfoResultDTO getLastCompanyClassificationLogByType(Integer type);
	public EditContactProviderInfoResultDTO getEditContactProviderInfo(Long ctpkey, Long prkey);
	public BaseResultsDTO doUpdateCompaniesClassificationExcel(FileUploadInitParamDTO initParams, boolean isMassive, LogInfoUserDTO loginfoDTO);
	public CompanyClassificationReportResultDTO getCompanyClassificationReportByFilterType(CompanyClassificationReportInitParamDTO initParamDTO);
	public FileDownloadInfoResultDTO downloadCompanyClassificationUpdateFileFormat(Long uskey, String fileformat, Locale locale );
	public FileDownloadInfoResultDTO downloadCompanyClassificationExcelReport(CompanyClassificationReportInitParamDTO initParamDTO, Long uskey, String fileformat);
}

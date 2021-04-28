package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.AddPopUpInitParamsDTO;
import bbr.b2b.users.report.classes.AddPublishingInitParamsDTO;
import bbr.b2b.users.report.classes.AttachmentDTO;
import bbr.b2b.users.report.classes.AuditPopUpInitParamDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportInitParamDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportResultDTO;
import bbr.b2b.users.report.classes.AuditPopUpTitleResultDTO;
import bbr.b2b.users.report.classes.AuditPopUpYearResultDTO;
import bbr.b2b.users.report.classes.DoChangePublishingStateInitParamsDTO;
import bbr.b2b.users.report.classes.ImageDTO;
import bbr.b2b.users.report.classes.ImageResultDTO;
import bbr.b2b.users.report.classes.PopUpBehaviorResultDTO;
import bbr.b2b.users.report.classes.PopUpReportInitParamsDTO;
import bbr.b2b.users.report.classes.PopUpReportResultDTO;
import bbr.b2b.users.report.classes.PopUpResultDTO;
import bbr.b2b.users.report.classes.PopUpTemplateResultDTO;
import bbr.b2b.users.report.classes.PopUpTypeResultDTO;
import bbr.b2b.users.report.classes.PublishingDetailReportResultDTO;
import bbr.b2b.users.report.classes.PublishingReportInitParamsDTO;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import bbr.b2b.users.report.classes.PublishingResultDTO;
import bbr.b2b.users.report.classes.PublishingStateResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeResultDTO;
import bbr.b2b.users.report.classes.RuleTypeResultDTO;
import bbr.b2b.users.report.classes.RuleTypesResultDTO;
import bbr.b2b.users.report.classes.UpdatePopUpInitParamsDTO;
import bbr.b2b.users.report.classes.UpdatePublishingInitParamsDTO;
import bbr.b2b.users.report.classes.UploadUserPublishingFileInitParamDTO;
import bbr.b2b.users.report.classes.UserPublishingRelationshipsInitParamsDTO;


public interface IPublishingManager extends IGenericEJBInterface{

	public PublishingTypeResultDTO getPublishingTypes();
	public PublishingStateResultDTO getPublishingStates();
	public PublishingReportResultDTO getPublishingReport(PublishingReportInitParamsDTO initParams, boolean byfilter);
	public PublishingDetailReportResultDTO getPublishingDetailReportById(Long publishingid);
	public PublishingReportResultDTO getActivePublishingsByType(String publishingtypecode);
	public PublishingTypeResultDTO getProcedureTypes();
	public PublishingReportResultDTO getActivePublishingsByUserAndType(Long userId, String publishingtypecode, boolean isProcedure);
	public PublishingResultDTO doAddPublishing(AddPublishingInitParamsDTO initParams, Long userkey);
	public ImageResultDTO getImages();
	public AttachmentDTO getAttachmentById(Long attachid);
	public ImageDTO getImageById(Long imageid);
	public BaseResultDTO doDeleteAttachmentById(Long attachid);
	public BaseResultDTO doChangePublishingState(DoChangePublishingStateInitParamsDTO initParams);
	public BaseResultDTO doAddImage(ImageDTO imagedto);
	public BaseResultDTO doDeletePublishingById(Long[] publishingids);
	public PopUpBehaviorResultDTO getPopUpBehaviors();
	public PopUpReportResultDTO getPopUpReport(PopUpReportInitParamsDTO initParams, boolean byfilter);
	public PopUpReportResultDTO getActivePopUpByUserAndFunctionality(Long userId, Long functionalityid);
	public PopUpReportResultDTO getActivePopUps(Long uskey);
	public PopUpResultDTO doAddPopUp(AddPopUpInitParamsDTO initParams, Long userkey);
	public BaseResultDTO doChangePopUpState(DoChangePublishingStateInitParamsDTO initParams);
	public BaseResultDTO doDeletePopUpById(Long[] popupids);
	public PublishingResultDTO doUpdatePublishing(UpdatePublishingInitParamsDTO initParams, Long userkey);
	public PopUpResultDTO doUpdatePopUp(UpdatePopUpInitParamsDTO initParams, Long userkey);
	public BaseResultsDTO doAddUserPublicationRelationshipsByRule(UserPublishingRelationshipsInitParamsDTO initParams);
	public BaseResultDTO doDeleteUserPublicationRelationshipsByRule(UserPublishingRelationshipsInitParamsDTO initParams);
	public BaseResultDTO doActivatePublishing(Long[] publishingIds, String lastUser);
	public BaseResultDTO doActivatePopUp(Long[] popupIds, String lastUser);
	public BaseResultDTO doInactivatePublishing(Long[] publishingIds, String lastUser);
	public BaseResultDTO doInactivatePopUp(Long[] popupIds, String lastUser);
	public RuleTypesResultDTO getRuleTypes();
	public RuleTypeResultDTO getRuleTypeByPublishingId(Long publishingId);
	public BaseResultsDTO doUpdateUserPublishingRelationships(UploadUserPublishingFileInitParamDTO initParams);
	public BaseResultsDTO doUpdateCompanyPublishingRelationships(UploadUserPublishingFileInitParamDTO initParams);
	
	public FileDownloadInfoResultDTO getExcelUserPublishingRelationshipReport(Long publishingId);
	public FileDownloadInfoResultDTO getExcelCompanyPublishingRelationshipReport(Long publishingId);
	
	public PopUpTemplateResultDTO getPopUpTemplates();
	public PopUpTypeResultDTO getPopUpTypes();   // tipos de popups (informativo, auditoria por usuario, auditoria por empresa)
	public PopUpBehaviorResultDTO getPopUpBehaviorByPopUpType(Long poupuptypeid); //comportamiento del poppup segun el tipo de popup que escogió
	public BaseResultDTO getAuditPopUp(AuditPopUpInitParamDTO initParamDTO, Long uskey); //agrega registro a auditoria y agrega relacion de exclusion en caso de aceptar
	
	/********* reporte auditoria popup *****************/
	public AuditPopUpYearResultDTO getAuditPopUpYears();
	public AuditPopUpTitleResultDTO getTitlesOfAuditPopUpByYear(int year);
	public AuditPopUpReportResultDTO getAuditPopUpReport (AuditPopUpReportInitParamDTO initParamDTO);
	public FileDownloadInfoResultDTO downloadAuditPopUpReport (AuditPopUpReportInitParamDTO initParamDTO, String filetype, Long uskey);
}

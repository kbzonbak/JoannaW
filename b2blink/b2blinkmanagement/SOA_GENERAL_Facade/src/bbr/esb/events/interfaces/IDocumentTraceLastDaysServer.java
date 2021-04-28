package bbr.esb.events.interfaces;

import java.text.ParseException;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.DocumentTraceLastDaysDTO;
import bbr.esb.events.data.classes.InternalScoreCardDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.events.data.classes.ScoreCardTableOfCompanyReturnDTO;
import bbr.esb.events.entities.DocumentTraceLastDays;

public interface IDocumentTraceLastDaysServer extends IElementServer<DocumentTraceLastDays, DocumentTraceLastDaysDTO> {

	public DocumentTraceLastDaysDTO addDocumentTraceLastDays(DocumentTraceLastDaysDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteDocumentTraceLastDays(Long documenttracelastdaysid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceLastDaysDTO getDocumentTraceLastDaysByPK(Long documenttracelastdaysid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceLastDaysDTO updateDocumentTraceLastDays(DocumentTraceLastDaysDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public InternalScoreCardDTO[] getScordCardValuesForSupplier(String companyrut, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException ;
	
	public ScoreCardTableOfCompanyReturnDTO getScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam) throws ParseException;
	
	public int getCountScoreCardTableOfCompany(ScoreCardTableOfCompanyInitParamDTO initparam) throws ParseException;	
	
	
	
	}

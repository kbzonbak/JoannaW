package bbr.b2b.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.buyorders.data.wrappers.SectionW;

public interface ISectionServer extends IElementServer<Section, SectionW> {

	SectionW addSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException;
	SectionW[] getSections() throws AccessDeniedException, OperationFailedException, NotFoundException;
	SectionW updateSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException;

}

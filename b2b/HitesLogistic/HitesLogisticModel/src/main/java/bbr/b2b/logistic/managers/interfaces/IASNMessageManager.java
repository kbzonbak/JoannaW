package bbr.b2b.logistic.managers.interfaces;

import java.util.List;

import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResultDTO;

public interface IASNMessageManager {

	WSSentMessageResultDTO doSendDocumentASNCreationMessage(DocumentW documentW, Long vendorId, List<ASNDataToMessageDTO> header, List<ASNDetailDataToMessageDTO> details, String userName, String userType);
	WSSentMessageResultDTO doSendDocumentASNDeleteMessage(DocumentW documentW, List<ASNDataToMessageDTO> header, List<ASNDetailDataToMessageDTO> details, List<Long> bulkIds);
	WSSentMessageResultDTO doSendDatingCreationMessage(DatingW datingW, DvrDeliveryW dvrDeliveryW, String userName, String userType);
	WSSentMessageResultDTO doSendDatingDeleteMessage(DvrDeliveryW dvrDeliveryW);
}

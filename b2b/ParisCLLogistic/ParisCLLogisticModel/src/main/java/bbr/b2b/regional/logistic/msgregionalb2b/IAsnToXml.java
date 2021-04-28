package bbr.b2b.regional.logistic.msgregionalb2b;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataDTO;

@Local
public interface IAsnToXml {

	public void processDeliveryMessages(DeliveryW delivery, DatingW dating, Long[] orderIds, Date requestedDate, int messageType, boolean isImported, boolean send) throws LoadDataException, ServiceException, Exception;
	public String processPredistributedPackingListMessage(List<PredistributedPackingListDataDTO> pplData, String datingnumber, Long deliverylocationid, String datingdate, String datingtime, Integer durationinminutes, String dockcode) throws LoadDataException, ServiceException, Exception;
}

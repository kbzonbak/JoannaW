package bbr.b2b.soa.mercadolibre.dto.classes;

import bbr.b2b.soa.mercadolibre.dto.interfaces.INotificationData;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class NotificationData implements INotificationData {

	private String resource;

	private Long user_id;

	private String topic;

	private Long application_id;

	private Integer attempts;

	private String sent;

	private String received;

}

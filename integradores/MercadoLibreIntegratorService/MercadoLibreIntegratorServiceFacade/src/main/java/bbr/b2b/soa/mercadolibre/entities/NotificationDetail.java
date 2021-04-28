package bbr.b2b.soa.mercadolibre.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDetail {

	private Long id;

	private int version;

	private LocalDateTime when;

	private Notification notification;

	private String jsonBody;

	private boolean informed;

	private LocalDateTime dateInformed;

	private boolean sent;

	private LocalDateTime dateSent;

	private boolean received;

	private LocalDateTime dateReceived;
	
}
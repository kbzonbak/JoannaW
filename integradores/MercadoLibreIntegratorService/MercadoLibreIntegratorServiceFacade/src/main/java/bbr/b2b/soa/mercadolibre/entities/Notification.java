package bbr.b2b.soa.mercadolibre.entities;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class Notification {

	private Long id;

	private int version;

	private String resource;

	private Long userId;

	private String topic;

	private Long applicationId;

	private Integer attempts;

	private ZonedDateTime sent;

	private ZonedDateTime received;

	private boolean processed;

	private LocalDateTime dateProcessed;

}
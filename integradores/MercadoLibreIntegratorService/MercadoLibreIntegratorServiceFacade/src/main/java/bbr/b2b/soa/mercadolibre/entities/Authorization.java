package bbr.b2b.soa.mercadolibre.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Authorization {

	private Long id;

	private int version;

	private String accessToken;

	private String refreshToken;

	private LocalDateTime when;

	private int duration;

	private LocalDateTime expiration;

	private boolean valid;

	private Company company;

}

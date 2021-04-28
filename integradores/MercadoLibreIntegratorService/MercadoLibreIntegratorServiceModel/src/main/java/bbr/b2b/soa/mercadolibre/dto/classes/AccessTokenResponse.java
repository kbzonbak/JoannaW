package bbr.b2b.soa.mercadolibre.dto.classes;

import bbr.b2b.soa.mercadolibre.dto.interfaces.IAccessTokenResponse;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class AccessTokenResponse implements IAccessTokenResponse {

	private String access_token;

	private String token_type;

	private Integer expires_in;

	private String scope;

	private String user_id;

	private String refresh_token;

}

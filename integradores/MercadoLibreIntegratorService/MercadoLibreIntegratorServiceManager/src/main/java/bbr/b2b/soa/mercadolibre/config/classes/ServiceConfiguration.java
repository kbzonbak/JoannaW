package bbr.b2b.soa.mercadolibre.config.classes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConditionalOnProperty(prefix = "meli.api", value = "client-id")
@ConfigurationProperties(prefix = "meli.api", ignoreUnknownFields = false)
@Getter
@Setter
public class ServiceConfiguration {

	private String clientId;

	private String clientSecret;

	private String serviceHost;

	private String grantTypeAuthorization;

	private String grantTypeRefresh;

	private String redirectUri;
	
	private String pathToken;

	private String pathItem;

	private String pathQuestion;
	
	private String pathOrder;

}

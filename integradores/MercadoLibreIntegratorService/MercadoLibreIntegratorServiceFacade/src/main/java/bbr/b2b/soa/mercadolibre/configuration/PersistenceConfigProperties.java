package bbr.b2b.soa.mercadolibre.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConditionalOnProperty(prefix = "primary", value = "persistenceUnit")
@ConfigurationProperties(prefix = "primary", ignoreUnknownFields = false)
@Getter
@Setter
public class PersistenceConfigProperties {

	private String persistenceUnit;

	private String[] basePackages;

	private String[] packagesToScan;

	private DataSources datasource;

	@Getter
	@Setter
	public static class DataSources {

		private String name;

		private DataSource postgresql;

	}

	@Getter
	@Setter
	public static class DataSource {

		private String jdbcUrl;

		private String driverClassName;

		private String username;

		private String password;

		private String schema;

	}

}

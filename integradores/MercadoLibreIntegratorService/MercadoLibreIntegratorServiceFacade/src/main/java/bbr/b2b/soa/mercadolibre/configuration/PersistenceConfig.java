package bbr.b2b.soa.mercadolibre.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.support.ClasspathScanningPersistenceUnitPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "bbr.b2b.soa.mercadolibre.repository", "bbr.b2b.soa.mercadolibre.dao" }, entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
public class PersistenceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

	@Autowired
	private PersistenceConfigProperties configProperties;

	@Bean(name = "primaryDataSource")
	@Primary
	@ConfigurationProperties(prefix = "primary.datasource.postgresql")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	@PersistenceContext(unitName = "UserPU")
	@Primary
	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
		LOGGER.info("PersistenceUnit = {}", configProperties.getPersistenceUnit());
		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		result.setDataSource(primaryDataSource());
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setPersistenceUnitName(configProperties.getPersistenceUnit());
		result.setJpaProperties(jpaProperties());
		// ESTA PROPIEDAD PERMITE QUE SPRING BUSQUE ARCHIVOS DE MAPPING EN LAS RUTAS ESPECIFICADAS
		if (configProperties.getBasePackages() != null && configProperties.getBasePackages().length > 0) {
			LOGGER.info("BasePackages = {}", Arrays.asList(configProperties.getBasePackages()).toString());
			PersistenceUnitPostProcessor[] postProcessors = getPersistenceUnitPostProcessors(configProperties.getBasePackages());
			result.setPersistenceUnitPostProcessors(postProcessors);
		}
		// ESTA PROPIEDAD PERMITE ESPECIFICAR LOS PACKAGES DONDE SE ENCUENTRAN LAS CLASES ANOTADAS
		if (configProperties.getPackagesToScan() != null && configProperties.getPackagesToScan().length > 0) {
			LOGGER.info("PackagesToScan = {}", Arrays.asList(configProperties.getPackagesToScan()).toString());
			result.setPackagesToScan(configProperties.getPackagesToScan());
		}
		LOGGER.info("LocalContainerEntityManagerFactoryBean JPAVendorAdapter: " + result.getJpaVendorAdapter());
		return result;
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManager")
	EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	private PersistenceUnitPostProcessor[] getPersistenceUnitPostProcessors(String... basePackages) {
		LOGGER.info("Configurando PersistenceUnitPostProcessors (basePackages=" + Arrays.asList(basePackages).toString() + ")");
		PersistenceUnitPostProcessor[] result = new ClasspathScanningPersistenceUnitPostProcessor[basePackages.length];
		for (int i = 0; i < basePackages.length; i++) {
			String basePackage = basePackages[i];
			ClasspathScanningPersistenceUnitPostProcessor postProcessor = new ClasspathScanningPersistenceUnitPostProcessor(basePackage);
			postProcessor.setMappingFileNamePattern("*hbm.xml");
			result[i] = postProcessor;
		}
		return result;
	}

	private Map<String, String> jpaPropertiesMap() {
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.archive.autodetection", "none");
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		props.put("hibernate.show_sql", "false");
		props.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
		props.put("hibernate.default_schema", configProperties.getDatasource().getPostgresql().getSchema());
		return props;
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		Map<String, String> map = jpaPropertiesMap();
		properties.putAll(map);
		return properties;
	}
}
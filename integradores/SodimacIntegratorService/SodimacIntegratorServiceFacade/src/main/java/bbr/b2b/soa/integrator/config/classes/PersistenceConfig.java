package bbr.b2b.soa.integrator.config.classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@EnableJpaRepositories(basePackages = {"bbr.b2b.soa.integrator.facade.repository" , "bbr.b2b.soa.integrator.facade.dao"}, entityManagerFactoryRef = "entityManager", transactionManagerRef = "transactionManager")
public class PersistenceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);

	@Value("${primary.persistenceUnit}")
	private String persistenceUnit;

	@Value("${primary.basePackages}")
	private String[] basePackages;

	@Value("${primary.packagesToScan}")
	private String[] packagesToScan;

	@Value("${primary.datasource.postgresql.schema}")
	private String schema;
	
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
		LOGGER.info("PersistenceUnit = {}", persistenceUnit);
		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		result.setDataSource(primaryDataSource());
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setPersistenceUnitName(persistenceUnit);
		result.setJpaProperties(jpaProperties());
		// ESTA PROPIEDAD PERMITE QUE SPRING BUSQUE ARCHIVOS DE MAPPING EN LAS RUTAS ESPECIFICADAS
		if (basePackages != null && basePackages.length > 0) {
			LOGGER.info("BasePackages = {}", Arrays.asList(basePackages).toString());
			PersistenceUnitPostProcessor[] postProcessors = getPersistenceUnitPostProcessors(basePackages);
			result.setPersistenceUnitPostProcessors(postProcessors);
		}
		// ESTA PROPIEDAD PERMITE ESPECIFICAR LOS PACKAGES DONDE SE ENCUENTRAN LAS CLASES ANOTADAS
		if (packagesToScan != null && packagesToScan.length > 0) {
			LOGGER.info("PackagesToScan = {}", Arrays.asList(packagesToScan).toString());
			result.setPackagesToScan(packagesToScan);
		}
		LOGGER.info("LocalContainerEntityManagerFactoryBean JPAVendorAdapter: " + result.getJpaVendorAdapter());
		return result;
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManager") EntityManagerFactory entityManagerFactory) {
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
		props.put("hibernate.default_schema", schema);
		return props;
	}

	private Properties jpaProperties() {
		Properties properties = new Properties();
		Map<String, String> map = jpaPropertiesMap();
		properties.putAll(map);
		return properties;
	}
}
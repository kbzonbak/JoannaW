package bbr.b2b.soa.integrator.config.classes;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConditionalOnProperty(prefix = "service.client", value = "endpoint")
@ConfigurationProperties(prefix = "service.client", ignoreUnknownFields = false)
@Getter
@Setter
public class ServiceConfiguration {

	private String siteCode;	
	private String siteName;
	private String portalName;	
	private String buyerCode;
	private String buyerName;
	private String buyerArea;
	private String lgRut;
	private String billTo;
	private Integer soaPendingReceptionTime;
	private Integer maxPendingOrders;
	private String endpoint;	
	private Boolean	proxy;	
	private String proxyHost;
	private Integer proxyPort;	
	private String orderServiceContracted;
	private String salesServiceContracted;
	private String inventoryServiceContracted;
	private String filePath;
	private String eocHeader;	
	private String predeliveryHeader;	
	private String vevHeader;	
}

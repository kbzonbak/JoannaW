package bbr.b2b.soa.mercadolibre.constants;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConditionalOnProperty(prefix = "service.constants", value = "nombre-portal")
@ConfigurationProperties(prefix = "service.constants", ignoreUnknownFields = false)
@Getter
@Setter
public class ServiceConstants {

	private String retailerRut;

	private String retailerDv;

	private String retailerName;

	private String retailerUn;

	private String nombrePortal;

	private String moduleCode;

	/** Tiempo de holganza de recepción **/
	private Integer soaPendingReceptionTime;

	/** Máxima cantidad de mensajes a enviar **/
	private Integer maxPendingMessages;

	/** Carpeta para archivos descargados **/
	private String downloadedFilesFolder;

}

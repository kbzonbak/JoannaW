package bbr.b2b.soa.mercadolibre.managers.classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.soa.mercadolibre.dto.classes.AccessTokenResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.AuthorizationData;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemStockUpdateData;
import bbr.b2b.soa.mercadolibre.entities.Authorization;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.managers.interfaces.IAuthorizationManager;
import bbr.b2b.soa.mercadolibre.repository.classes.AuthorizationRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.CompanyRepository;

@Service
@Transactional
public class AuthorizationManager implements IAuthorizationManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationManager.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AuthorizationRepository authorizationRepository;

	@Autowired
	private MeliRestClient meliClient;

	@Override
	public void refreshAuthorizationTokens() {
		// Obtener las autorizaciones validas que esten por vencer (<3 horas de validez)
		LocalDateTime exp_threshold = LocalDateTime.now().plusHours(3);
		// List<Authorization> list = authorizationRepository.findAll();
		List<Authorization> list = authorizationRepository.findByValidAndExpirationLessThan(true, exp_threshold);
		// Por cada autorizacion, obtener el refresh_token actual
		for (Authorization authorization : list) {
			// Para refrescar la autorizacion se debe invocar el servicio correspondiente
			Optional<AccessTokenResponse> tokenResponse = meliClient.getRefreshToken(authorization.getRefreshToken());
			if (tokenResponse.isPresent()) {
				AccessTokenResponse objToken = tokenResponse.get();
				authorization.setAccessToken(objToken.getAccess_token());
				authorization.setRefreshToken(objToken.getRefresh_token());
				authorization.setWhen(LocalDateTime.now());
				authorization.setDuration(objToken.getExpires_in());
				// La expiracion se calcula desde la fecha de creacion mas la duracion
				LocalDateTime expiration = authorization.getWhen().plusSeconds(authorization.getDuration());
				authorization.setExpiration(expiration);
				authorization.setValid(true);
			}
			authorizationRepository.save(authorization);
		}
	}

	@Override
	public Optional<Authorization> addOrUpdateAuthorizationForCompany(String companyCode, AuthorizationData data) {
		Authorization authorization = null;

		LOGGER.info("Se crea o actualiza datos de autorización para empresa: {}. data: {}", companyCode, data);
		// Buscar la empresa por codigo
		Optional<Company> optCompany = companyRepository.findByRut(companyCode);
		if (optCompany.isEmpty()) {
			return Optional.empty();
		}

		Company company = optCompany.get();

		// Validar que el código de autorización no sea nulo y termine con el userId de la empresa
		if (data == null || data.getAuthorizationCode() == null || data.getAuthorizationCode().isEmpty()) {
			LOGGER.error("El código de autorización especificado es nulo o vacío");
			return Optional.empty();
		}
		String userId = company.getUserId().toString();
		if (!data.getAuthorizationCode().endsWith(userId)) {
			LOGGER.error("El código de autorización especificado: {} no corresponde al userId de la empresa ({})", data.getAuthorizationCode(), userId);
			return Optional.empty();
		}

		// Obtener la autorizacion para la empresa, si no existe, crearla
		Optional<Authorization> optAuth = authorizationRepository.findByCompanyId(company.getId());
		if (optAuth.isEmpty()) {
			authorization = new Authorization();
			authorization.setCompany(company);
		} else {
			authorization = optAuth.get();
		}
		// Para crear la nueva autorizacion se debe invocar el servicio para obtener el access token
		Optional<AccessTokenResponse> tokenResponse = meliClient.getAccessToken(data.getAuthorizationCode());
		if (tokenResponse.isPresent()) {
			AccessTokenResponse objToken = tokenResponse.get();
			authorization.setAccessToken(objToken.getAccess_token());
			authorization.setRefreshToken(objToken.getRefresh_token());
			authorization.setWhen(LocalDateTime.now());
			authorization.setDuration(objToken.getExpires_in());
			// La expiracion se calcula desde la fecha de creacion mas la duracion
			LocalDateTime expiration = authorization.getWhen().plusSeconds(authorization.getDuration());
			authorization.setExpiration(expiration);
			authorization.setValid(true);
		}
		authorizationRepository.save(authorization);
		return Optional.of(authorization);
	}

	@Override
	public Optional<Authorization> findAuthorizationsByCompanyRut(String companyRut) {
		return authorizationRepository.findByCompanyRut(companyRut);
	}

	@Override
	public Optional<ItemResponse> getItemData(String companyCode, String itemId) {
		Optional<Authorization> optAuth = authorizationRepository.findByCompanyRut(companyCode);
		Authorization authorization = null;
		if (optAuth.isEmpty()) {
			return Optional.empty();
		} else {
			authorization = optAuth.get();
		}
		Optional<ItemResponse> result = meliClient.getItemData(itemId, authorization.getAccessToken());

		return result;
	}

	@Override
	public Optional<ItemResponse> updateStockOfItem(String companyRut, String itemId, ItemStockUpdateData data) {
		Optional<Authorization> optAuth = authorizationRepository.findByCompanyRut(companyRut);
		Authorization authorization = null;
		if (optAuth.isEmpty()) {
			return Optional.empty();
		} else {
			authorization = optAuth.get();
		}
		Optional<ItemResponse> result = meliClient.updateStockOfItem(itemId, data.getAvailableQuantity(), authorization.getAccessToken());
		return result;
	}

}

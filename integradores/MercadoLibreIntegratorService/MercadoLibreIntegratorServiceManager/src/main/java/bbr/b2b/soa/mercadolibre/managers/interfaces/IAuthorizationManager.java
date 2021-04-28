package bbr.b2b.soa.mercadolibre.managers.interfaces;

import java.util.Optional;

import bbr.b2b.soa.mercadolibre.dto.classes.AuthorizationData;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemResponse;
import bbr.b2b.soa.mercadolibre.dto.classes.ItemStockUpdateData;
import bbr.b2b.soa.mercadolibre.entities.Authorization;

public interface IAuthorizationManager {

	public Optional<Authorization> findAuthorizationsByCompanyRut(String companyRut);

	/**
	 * Crea o actualiza la información de autorización (tokens de acceso y refresco) para una empresa
	 * 
	 * @param companyRut
	 *            El RUT de la empresa
	 * @param data
	 *            Los datos de autorización para invocar la API REST de MELI para renovar la autorización
	 * @return
	 */
	public Optional<Authorization> addOrUpdateAuthorizationForCompany(String companyRut, AuthorizationData data);

	/**
	 * Obtiene las autorizaciones con token de acceso próximos a expirar e invoca la API REST de MELI para renovarlos
	 */
	public void refreshAuthorizationTokens();

	public Optional<ItemResponse> updateStockOfItem(String companyRut, String itemId, ItemStockUpdateData data);

	public Optional<ItemResponse> getItemData(String companyRut, String itemId);

}

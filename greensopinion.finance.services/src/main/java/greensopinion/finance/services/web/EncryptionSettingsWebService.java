package greensopinion.finance.services.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.model.EncryptionSettings;

@Path(EncryptionSettingsWebService.BASE_PATH)
public class EncryptionSettingsWebService {
	static final String BASE_PATH = "/encryption-settings";

	static final String PATH_CURRENT = "current";

	private final EncryptorService encryptorService;

	@Inject
	EncryptionSettingsWebService(EncryptorService encryptorService) {
		this.encryptorService = encryptorService;
	}

	@Path(PATH_CURRENT)
	@GET
	public EncryptionSettings get() {
		return new EncryptionSettings(encryptorService.isConfigured(), encryptorService.isInitialized());
	}
}
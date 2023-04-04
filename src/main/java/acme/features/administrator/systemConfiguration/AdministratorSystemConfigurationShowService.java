
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository systemConfigurationRepository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SystemConfiguration object;
		object = this.systemConfigurationRepository.currentConfiguration();
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");
		super.getResponse().setData(tuple);
	}
}

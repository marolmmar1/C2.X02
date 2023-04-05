
package acme.features.administrator.bulletin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinPostService extends AbstractService<Administrator, Bulletin> {

	@Autowired
	protected AdministratorBulletinRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void bind(final Bulletin object) {
		super.bind(object);
	}

	@Override
	public void validate(final Bulletin object) {
		super.validate(object);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void perform(final Bulletin object) {
		super.perform(object);
	}

	@Override
	public void load() {
		super.load();
	}

	@Override
	public void unbind(final Bulletin object) {
		super.unbind(object);
	}

}

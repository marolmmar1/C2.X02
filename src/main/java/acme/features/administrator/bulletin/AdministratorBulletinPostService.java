
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		assert object != null;
		super.bind(object, "instantiation", "title", "message", "critical", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
		boolean conf;
		conf = super.getRequest().getData("conf", boolean.class);
		super.state(conf, "conf", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstantiation(moment);
		this.repo.save(object);
	}

	@Override
	public void load() {
		Bulletin object;
		object = new Bulletin();
		final Date actDate = MomentHelper.getCurrentMoment();
		object.setInstantiation(actDate);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;
		Tuple tupla;
		tupla = super.unbind(object, "instantiation", "title", "message", "critical", "link");
		tupla.put("conf", false);
		super.getResponse().setData(tupla);
	}

}

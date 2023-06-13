
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


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
		final Peep object = new Peep();

		final Principal principal = super.getRequest().getPrincipal();

		final Date moment = MomentHelper.getCurrentMoment();
		object.setInstantiation(moment);

		if (principal.isAuthenticated()) {
			final int userAccountId = principal.getAccountId();
			final UserAccount userAccount = this.repository.findUserAccountById(userAccountId);
			final String Name = userAccount.getIdentity().getName();
			final String Surname = userAccount.getIdentity().getSurname();
			final String nickFinalFormat = "<" + Surname + ", " + Name + ">";
			object.setNick(nickFinalFormat);
		}

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;

		super.bind(object, "title", "nick", "message", "email", "link");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "title", "nick", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}

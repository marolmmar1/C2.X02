
package acme.features.authenticated.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantUpdateService extends AbstractService<Authenticated, Assistant> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAssistantRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Assistant object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repository.findOneAssistantByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Assistant object) {
		assert object != null;

		super.bind(object, "supervisor", "expertiseField", "resume", "link");
	}

	@Override
	public void validate(final Assistant object) {
		assert object != null;
	}

	@Override
	public void perform(final Assistant object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Assistant object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "supervisor", "expertiseField", "resume", "link");

		super.getResponse().setData(tuple);
	}

}

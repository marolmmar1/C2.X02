
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNotePostService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;
		super.bind(object, "instantiation", "title", "author", "message", "email", "link");
	}

	@Override
	public void validate(final Note object) {
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
	public void perform(final Note object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstantiation(moment);
		this.repo.save(object);
	}

	@Override
	public void load() {
		Note object;

		object = new Note();
		final Date actDate = MomentHelper.getCurrentMoment();

		final Tuple tuple;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		final UserAccount userAccount = this.repo.findUserAccountById(userAccountId);
		final String authorName = userAccount.getIdentity().getName();
		final String authorSurname = userAccount.getIdentity().getSurname();
		final String authorUsername = userAccount.getUsername();
		final String authorFinalFormat = "<" + authorUsername + "> - <" + authorSurname + ", " + authorName + ">";

		object.setInstantiation(actDate);
		object.setAuthor(authorFinalFormat);
		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Note object) {
		assert object != null;
		Tuple tupla;
		tupla = super.unbind(object, "instantiation", "title", "author", "message", "email", "link");
		tupla.put("conf", false);
		super.getResponse().setData(tupla);
	}

}

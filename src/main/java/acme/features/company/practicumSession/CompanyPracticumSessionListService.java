
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean result;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		result = practicum != null && (!practicum.isDraftMode() || super.getRequest().getPrincipal().hasRole(practicum.getCompany()));
		super.getResponse().setAuthorised(result);
	}

	@Override
	public void load() {
		final Collection<PracticumSession> objects;
		final int practicumId = super.getRequest().getData("id", int.class);
		objects = this.repository.findPracticumSessionByPracticumlId(practicumId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		int practicumId;
		Practicum practicum;
		final boolean showCreate;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		showCreate = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setGlobal("practicumId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}

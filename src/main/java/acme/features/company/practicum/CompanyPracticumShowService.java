
package acme.features.company.practicum;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumShowService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticumRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Principal principal;
		Practicum object;
		int objectId;

		objectId = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(objectId);
		principal = super.getRequest().getPrincipal();
		status = object.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		final Tuple tupla;
		Collection<Course> courses;
		SelectChoices selectChoices;
		PracticumSession practicumSessions;
		double diferenciaHoras = 0.0;
		double total = 0.0;
		final Collection<PracticumSession> ps = this.repository.findPracticumSessionsByPracticumId(object.getId());
		final List<PracticumSession> PracticumSessionsList = ps.stream().collect(Collectors.toList());
		courses = this.repository.findAllCourse(false);
		selectChoices = SelectChoices.from(courses, "code", object.getCourse());

		if (PracticumSessionsList == null)
			total = 0.0;
		for (int i = 0; i < PracticumSessionsList.size(); i++) {
			practicumSessions = PracticumSessionsList.get(i);
			final Date initialPeriod = practicumSessions.getInicialPeriod();
			final Date finalPeriod = practicumSessions.getFinalPeriod();
			final long milisegundosInicio = initialPeriod.getTime();
			final long milisengundosFin = finalPeriod.getTime();
			final long diferenciaMilisegundos = milisengundosFin - milisegundosInicio;
			if (diferenciaMilisegundos > 0) {
				diferenciaHoras = (double) diferenciaMilisegundos / (1000 * 60 * 60);
				total += diferenciaHoras;
			}
		}

		final int hours = (int) total;
		final int minutes = (int) ((total - hours) * 60);
		final double diffHours = Double.parseDouble(hours + "." + minutes);

		tupla = super.unbind(object, "code", "title", "abstracts", "goals", "draftMode");
		tupla.put("course", selectChoices.getSelected().getKey());
		tupla.put("courses", selectChoices);
		tupla.put("estimatedTime", diffHours);
		super.getResponse().setData(tupla);
	}

}

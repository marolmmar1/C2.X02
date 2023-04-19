
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
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumUpdateService extends AbstractService<Company, Practicum> {

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
		int practicumId;
		Practicum practicum;
		Company company;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		company = practicum == null ? null : practicum.getCompany();
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(company);

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
	public void bind(final Practicum object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "abstracts", "goals");
		object.setCourse(course);
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.findPracticumByCode(object.getCode()) == null || this.repository.findPracticumByCode(object.getCode()).equals(object), "code", "company.practicum.form.error.duplicated");
	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		PracticumSession practicumSessions;
		SelectChoices choices;
		Tuple tuple;
		double diferenciaHoras = 0.0;
		double total = 0.0;
		final Collection<PracticumSession> ps = this.repository.findPracticumSessionsByPracticumId(object.getId());
		final List<PracticumSession> PracticumSessionsList = ps.stream().collect(Collectors.toList());

		courses = this.repository.findAllCourse(false);
		choices = SelectChoices.from(courses, "code", object.getCourse());
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

		tuple = super.unbind(object, "code", "title", "abstracts", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTime", diffHours);
		super.getResponse().setData(tuple);
	}

}

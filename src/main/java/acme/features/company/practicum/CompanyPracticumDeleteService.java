
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.PracticumSessions;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumDeleteService extends AbstractService<Company, Practicum> {

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
		int companyId;

		companyId = super.getRequest().getPrincipal().getActiveRoleId();
		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		final boolean sameCompany = practicum.getCompany().getId() == companyId;
		status = sameCompany && super.getRequest().getPrincipal().hasRole(Company.class);

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
	}

	@Override
	public void perform(final Practicum object) {
		Collection<PracticumSessions> practicumSessions;

		practicumSessions = this.repository.findPracticumSessionsByPracticumId(object.getId());
		this.repository.deleteAll(practicumSessions);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tupla;

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tupla = super.unbind(object, "code", "title", "abstracts", "goals");
		tupla.put("course", choices.getSelected().getKey());
		tupla.put("courses", choices);

		super.getResponse().setData(tupla);
	}

}

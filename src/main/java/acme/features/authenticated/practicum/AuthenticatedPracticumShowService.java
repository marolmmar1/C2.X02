
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repository;


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
		Practicum practicum;
		int id;
		id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumById(id);
		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;
		final Collection<Course> courses = this.repository.findAllCourses();
		final SelectChoices choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "title", "goals", "abstracts");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
	}

}


package acme.features.lecturer.course;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		Lecturer lecturer;

		lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abstracts", "price", "nature", "link");

	}

	@Override
	public void validate(final Course object) {

		assert object != null;

		String code;
		Optional<Course> repeatCode;

		code = super.getRequest().getData("code", String.class);
		repeatCode = this.repository.findCourseByCode(code);

		super.state(!repeatCode.isPresent(), "code", "lecturer.course.form.error.code.repeated");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tupla;

		choices = SelectChoices.from(Nature.class, object.getNature());
		tupla = super.unbind(object, "code", "title", "abstracts", "price", "nature", "link");
		tupla.put("nature", choices.getSelected().getKey());
		tupla.put("natures", choices);

		super.getResponse().setData(tupla);
	}

}
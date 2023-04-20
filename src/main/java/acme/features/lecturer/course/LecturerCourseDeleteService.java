
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lecturerId;
		int courseId;
		Course object;
		boolean lecturer;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courseId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(courseId);
		lecturer = object.getLecturer().getId() == lecturerId;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class) && lecturer;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

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
		final boolean inDraftMode = object.isDraftMode();

		super.state(inDraftMode, "*", "lecturer.course.form.error.delete.draft");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tupla;

		tupla = super.unbind(object, "code", "title", "abstracts", "price", "nature", "link");

		super.getResponse().setData(tupla);
	}

}

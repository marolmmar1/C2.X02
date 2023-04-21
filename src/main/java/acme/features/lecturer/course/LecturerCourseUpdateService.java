
package acme.features.lecturer.course;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

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
		int id;
		Course object;

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

		boolean inDraftMode;
		String code;
		Optional<Course> repeatCode;

		inDraftMode = object.isDraftMode();
		code = super.getRequest().getData("code", String.class);
		repeatCode = this.repository.findCourseByCode(code);

		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(this.repository.findCourseByCode(object.getCode()) == null || this.repository.findCourseByCode(object.getCode()).equals(object), "code", "lecturer.course.form.error.code");

		super.state(!repeatCode.isPresent() || object.getId() == repeatCode.get().getId(), "code", "lecturer.course.form.error.update.code.repeated");
		super.state(inDraftMode, "*", "lecturer.course.form.error.update.draft");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		boolean draftMode;

		draftMode = super.getRequest().getData("draft", boolean.class);
		object.setDraftMode(draftMode);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstracts", "price", "nature", "link");

		super.getResponse().setData(tuple);
	}

}

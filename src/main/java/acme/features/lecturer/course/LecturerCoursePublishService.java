
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Lecture;
import acme.entities.Nature;
import acme.features.lecturer.courseLecture.LecturerCourseLectureRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository			repository;

	@Autowired
	protected LecturerCourseLectureRepository	lclRepository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Course object;
		Principal principal;
		int courseId;

		courseId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(courseId);
		principal = super.getRequest().getPrincipal();

		status = object.getLecturer().getId() == principal.getActiveRoleId();

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Optional<Course> existing;

			existing = this.repository.findCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.update.code.repeated");
		}
		final Collection<Lecture> lectures = this.lclRepository.findLecturesByCourseId(object.getId());

		super.state(!lectures.isEmpty(), "*", "lecturer.course.form.error.noSession");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		SelectChoices choices;
		Tuple tupla;

		choices = SelectChoices.from(Nature.class, object.getNature());
		tupla = super.unbind(object, "code", "title", "abstracts", "price", "nature", "link", "draftMode");
		tupla.put("nature", choices.getSelected().getKey());
		tupla.put("natures", choices);

		super.getResponse().setData(tupla);
	}

}

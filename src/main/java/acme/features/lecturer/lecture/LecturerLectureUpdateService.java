
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.entities.Nature;
import acme.features.lecturer.courseLecture.LecturerCourseLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository			repository;

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
		int lecturerId;
		final int lectureId;
		CourseLecture object;
		boolean lecturer;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lectureId = super.getRequest().getData("id", int.class);
		object = this.repository.findCourseLectureByLectureId(lectureId);
		lecturer = object.getCourse().getLecturer().getId() == lecturerId;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class) && lecturer;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Lecture object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		boolean inDraftMode;
		CourseLecture cl;

		cl = this.repository.findCourseLectureByLectureId(object.getId());
		inDraftMode = cl.getCourse().isDraftMode();
		super.state(inDraftMode, "title", "lecturer.lecture.form.error.draft.update");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		int ppalId;
		Course course;
		CourseLecture cl;

		cl = new CourseLecture();
		ppalId = super.getRequest().getData("ppalId", int.class);
		course = this.repository.findCourseById(ppalId);

		cl.setCourse(course);
		cl.setLecture(object);

		this.repository.save(object);
		this.lclRepository.save(cl);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tupla;
		final SelectChoices choices;

		choices = SelectChoices.from(Nature.class, object.getNature());
		tupla = super.unbind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
		tupla.put("ppalId", super.getRequest().getData("ppalId", int.class));
		tupla.put("nature", choices.getSelected().getKey());
		tupla.put("natures", choices);

		super.getResponse().setData(tupla);
	}

}

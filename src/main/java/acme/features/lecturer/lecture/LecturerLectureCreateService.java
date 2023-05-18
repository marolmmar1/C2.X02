
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
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository			repository;

	@Autowired
	protected LecturerCourseLectureRepository	lclRepository;


	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("courseId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		//		boolean status;
		//		int courseId;
		//		Course course;
		//
		//		courseId = super.getRequest().getData("courseId", int.class);
		//		course = this.repository.findOneCourseById(courseId);
		//
		//		status = super.getRequest().getPrincipal().hasRole(course.getLecturer());
		//		super.getResponse().setAuthorised(status);
		boolean status;
		int lecturerId;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		course = this.repository.findOneCourseById(courseId);

		status = course.getLecturer().getId() == lecturerId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		final CourseLecture courseLecture;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		Lecturer lecturer;

		lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Lecture();
		object.setLecturer(lecturer);
		courseLecture = new CourseLecture();
		courseLecture.setCourse(course);
		courseLecture.setLecture(object);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");

	}

	@Override
	public void validate(final Lecture object) {

		assert object != new Lecture();

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		//		int courseId;
		//		Course course;
		//		final CourseLecture courseLecture = new CourseLecture();
		//
		//		courseId = super.getRequest().getData("id", int.class);
		//		course = this.repository.findOneCourseById(courseId);
		//
		//		courseLecture.setCourse(course);
		//		courseLecture.setLecture(object);

		//		this.repository.save(course);
		this.repository.save(object);
		//		this.lclRepository.save(courseLecture);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tupla;
		final SelectChoices choices;

		choices = SelectChoices.from(Nature.class, object.getNature());
		tupla = super.unbind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
		tupla.put("courseId", super.getRequest().getData("courseId", int.class));
		tupla.put("nature", choices.getSelected().getKey());
		tupla.put("natures", choices);

		super.getResponse().setData(tupla);
	}

}

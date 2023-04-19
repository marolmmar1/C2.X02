
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.features.lecturer.courseLecture.LecturerCourseLectureRepository;
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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		final int ppalId = super.getRequest().getData("ppalId", int.class);
		final int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();

		final Course course = this.repository.findCourseById(ppalId);
		status = course.getLecturer().getId() == lecturerId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;

		object = new Lecture();

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

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		int ppalId;
		Course course;
		CourseLecture courseLecture;

		courseLecture = new CourseLecture();
		ppalId = super.getRequest().getData("ppalId", int.class);
		course = this.repository.findCourseById(ppalId);

		courseLecture.setCourse(course);
		courseLecture.setLecture(object);

		this.repository.save(object);
		this.lclRepository.save(courseLecture);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tupla;

		tupla = super.unbind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
		tupla.put("ppalId", super.getRequest().getData("ppalId", int.class));

		super.getResponse().setData(tupla);
	}

}

//
//package acme.features.lecturer.lecture;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import acme.entities.CourseLecture;
//import acme.entities.Lecture;
//import acme.features.lecturer.courseLecture.LecturerCourseLectureRepository;
//import acme.framework.components.models.Tuple;
//import acme.framework.services.AbstractService;
//import acme.roles.Lecturer;
//
//@Service
//public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {
//
//	@Autowired
//	protected LecturerLectureRepository			repository;
//
//	@Autowired
//	protected LecturerCourseLectureRepository	lclRepository;
//
//
//	@Override
//	public void check() {
//		boolean status;
//
//		status = super.getRequest().hasData("id", int.class);
//
//		super.getResponse().setChecked(status);
//	}
//
//	@Override
//	public void authorise() {
//		boolean status;
//		int lecturerId;
//		int lectureId;
//		CourseLecture object;
//		boolean lecturer;
//
//		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
//		lectureId = super.getRequest().getData("id", int.class);
//		object = this.repository.findCourseLectureByLectureId(lectureId);
//		lecturer = object.getCourse().getLecturer().getId() == lecturerId;
//
//		status = super.getRequest().getPrincipal().hasRole(Lecturer.class) && lecturer;
//
//		super.getResponse().setAuthorised(status);
//	}
//
//	@Override
//	public void load() {
//		Lecture object;
//		int id;
//
//		id = super.getRequest().getData("id", int.class);
//		object = this.repository.findOneLectureById(id);
//
//		super.getBuffer().setData(object);
//	}
//
//	@Override
//	public void bind(final Lecture object) {
//		assert object != null;
//
//		super.bind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
//	}
//
//	@Override
//	public void validate(final Lecture object) {
//		assert object != null;
//
//		final CourseLecture cl = this.repository.findCourseLectureByLectureId(object.getId());
//		final boolean inDraftMode = cl.getCourse().isDraftMode();
//
//		super.state(inDraftMode, "title", "lecturer.lecture.form.error.draft.delete");
//	}
//
//	@Override
//	public void perform(final Lecture object) {
//		assert object != null;
//
//		this.repository.delete(object);
//	}
//
//	@Override
//	public void unbind(final Lecture object) {
//		assert object != null;
//		Tuple tupla;
//
//		tupla = super.unbind(object, "title", "abstracts", "estimatedTime", "body", "nature", "link");
//
//		super.getResponse().setData(tupla);
//	}
//
//}

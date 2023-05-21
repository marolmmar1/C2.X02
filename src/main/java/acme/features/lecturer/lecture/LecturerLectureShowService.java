
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Lecture;
import acme.entities.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

	// Internal state --------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Lecture lecture;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		lecture = this.repository.findLectureById(masterId);
		lecturer = lecture == null ? null : lecture.getLecturer();
		status = super.getRequest().getPrincipal().hasRole(lecturer) || lecture != null && !lecture.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture lecture;
		int id;

		id = super.getRequest().getData("id", int.class);
		lecture = this.repository.findLectureById(id);

		super.getBuffer().setData(lecture);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstracts", "body", "estimatedTime", "nature", "link", "draftMode");
		final SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);

		super.getResponse().setData(tuple);
	}

}

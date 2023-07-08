
package acme.features.lecturer.course;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.SystemConfiguration;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = true;

		super.getResponse().setChecked(status);
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
		object = new Course();
		final Lecturer lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setLecturer(lecturer);
		object.setDraftMode(true);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "abstracts", "price", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		SystemConfiguration sc = new SystemConfiguration();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}
		if (object.getPrice() != null) {
			if (!super.getBuffer().getErrors().hasErrors("price")) {
				sc = this.repository.findAllSystemConfiguration().get(0);
				final String acceptedCurrencies = sc.getAcceptedCurrencies();
				final List<String> listCurrenciesAccepted = Arrays.asList(acceptedCurrencies.split(","));
				super.state(listCurrenciesAccepted.contains(object.getPrice().getCurrency()), "price", "administrator.offer.form.error.price-error");
			}

			if (!super.getBuffer().getErrors().hasErrors("price"))
				super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.wrong-price");
		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstracts", "price", "link", "draftMode", "lecturer");
		super.getResponse().setData(tuple);
	}
}

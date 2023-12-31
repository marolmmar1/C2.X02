
package acme.features.company.practicumSession;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("practicumId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		object = new PracticumSession();
		object.setTitle("");
		object.setAbstracts("");
		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		if (!super.getBuffer().getErrors().hasErrors("inicialPeriod")) {
			long diferenciaDias = 0;
			final long num = 7;
			final Date moment = MomentHelper.getCurrentMoment();
			final Date inicialPeriod = object.getInicialPeriod();
			final long milisegundosInicio = moment.getTime();
			final long milisegundosFin = inicialPeriod.getTime();
			final long diferenciaMilisegundos = milisegundosFin - milisegundosInicio;

			if (diferenciaMilisegundos > 0)
				diferenciaDias = TimeUnit.MILLISECONDS.toDays(diferenciaMilisegundos);

			super.state(diferenciaDias >= num, "inicialPeriod", "company.practicumSession.form.error.before");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod")) {

			long diferenciaHorasMax = 0;
			long diferenciaHorasMin = 0;
			final Date inicialPeriod = object.getInicialPeriod();
			final Date finalPeriod = object.getFinalPeriod();
			final long milisegundosInicio = inicialPeriod.getTime();
			final long milisegundosFin = finalPeriod.getTime();
			long diferenciaMilisegundosMax = milisegundosFin - milisegundosInicio;
			final long diferenciaMilisegundosMin = milisegundosFin - milisegundosInicio;

			diferenciaMilisegundosMax -= 60000;
			diferenciaHorasMax = TimeUnit.MILLISECONDS.toHours(diferenciaMilisegundosMax);
			diferenciaHorasMin = TimeUnit.MILLISECONDS.toHours(diferenciaMilisegundosMin);

			final long numMin = 168;
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "company.practicumSession.form.error.minor");
			super.state(diferenciaHorasMin >= numMin, "finalPeriod", "company.practicumSession.form.error.horaMin");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		super.getResponse().setData(tuple);
	}

}

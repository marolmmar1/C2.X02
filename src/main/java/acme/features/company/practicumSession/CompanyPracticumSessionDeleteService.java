
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
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumSessionId;
		Practicum practicum;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumByPracticumSessionId(practicumSessionId);
		status = practicum != null && practicum.isDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("inicialPeriod")) {
			long diferenciaDias = 0;
			final long num = 1;
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

			final long numMax = 5;
			final long numMin = 1;
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "company.practicumSession.form.error.minor");
			super.state(diferenciaHorasMax < numMax, "finalPeriod", "company.practicumSession.form.error.horaMax");
			super.state(diferenciaHorasMin >= numMin, "finalPeriod", "company.practicumSession.form.error.horaMin");
		}
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.delete(object);
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

/*
 * EmployerJobUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "inicialPeriod", "finalPeriod", "slogan", "link", "linkImage");

	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("inicialPeriod"))
			super.state(MomentHelper.isAfter(object.getInicialPeriod(), object.getInstantiation()), "inicialPeriod", "administrator.banner.form.error.antes");

		//		if (!super.getBuffer().getErrors().hasErrors("finalPeriod")) {
		//			final Date inicialPeriod = object.getInicialPeriod();
		//			final Date finalPeriod = object.getInicialPeriod();
		//			final LocalDate localDateInicial = inicialPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//			final LocalDate localDateFinal = finalPeriod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//			final Duration duration = Duration.between(localDateInicial.atStartOfDay(), localDateFinal.atStartOfDay());
		//			final long days = duration.toDays();
		//			final long num = 7;
		//			super.state(days > num, "finalPeriod", "administrator.banner.form.error.menos");
		//		}
	}
	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "inicialPeriod", "finalPeriod", "slogan", "link", "linkImage");

		super.getResponse().setData(tuple);
	}

}

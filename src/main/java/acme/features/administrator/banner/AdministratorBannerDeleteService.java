/*
 * AdministratorCompanyDeleteService.java
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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

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
		Banner banner;
		masterId = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(masterId);
		final Date moment = MomentHelper.getCurrentMoment();
		status = moment.before(banner.getInicialPeriod()) || moment.after(banner.getFinalPeriod());

		super.getResponse().setAuthorised(status);
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

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod")) {
			long diferenciaDias = 0;
			final long numMax = 7;
			final Date inicialPeriod = object.getInicialPeriod();
			final Date finalPeriod = object.getFinalPeriod();
			final long milisegundosInicio = inicialPeriod.getTime();
			final long milisegundosFin = finalPeriod.getTime();
			final long diferenciaMilisegundos = milisegundosFin - milisegundosInicio;

			if (diferenciaMilisegundos > 0)
				diferenciaDias = TimeUnit.MILLISECONDS.toDays(diferenciaMilisegundos);

			super.state(diferenciaDias >= numMax, "finalPeriod", "administrator.banner.form.error.menos");
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "administrator.banner.form.error.menor");
		}

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;
		final boolean showUpdateDelete;
		final Date moment = MomentHelper.getCurrentMoment();

		showUpdateDelete = moment.before(object.getInicialPeriod()) || moment.after(object.getFinalPeriod());
		tuple = super.unbind(object, "inicialPeriod", "finalPeriod", "slogan", "link", "linkImage");
		tuple.put("showUpdateDelete", showUpdateDelete);
		super.getResponse().setData(tuple);
	}

}

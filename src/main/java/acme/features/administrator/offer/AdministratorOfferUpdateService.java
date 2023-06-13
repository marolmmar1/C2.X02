/*
 * 
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Offer;
import acme.entities.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;


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
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "heading", "summary", "inicialPeriod", "finalPeriod", "price", "link");

	}

	@Override
	public void validate(final Offer object) {
		assert object != null;
		SystemConfiguration sc = new SystemConfiguration();

		if (!super.getBuffer().getErrors().hasErrors("inicialPeriod"))
			super.state(MomentHelper.isAfter(object.getInicialPeriod(), object.getInstantiation()), "inicialPeriod", "administrator.offer.form.error.antes");

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

			super.state(diferenciaDias >= numMax, "finalPeriod", "administrator.offer.form.error.menos");
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "administrator.offer.form.error.menor");
		}

	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "inicialPeriod", "finalPeriod", "price", "link");

		super.getResponse().setData(tuple);
	}

}

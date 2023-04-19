/*
 * EmployerDutyCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialSession;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Nature;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionsCreateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("tutorialId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);

		object = new TutorialSession();
		object.setTitle("");
		object.setAbstracts("");
		object.setNature(Nature.BALANCE);
		object.setTutorial(tutorial);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "nature", "inicialPeriod", "finalPeriod", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
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

			super.state(diferenciaDias >= num, "inicialPeriod", "assistant.tutorialSession.form.error.antes");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod")) {
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "assistant.tutorialSession.form.error.menor");

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
			super.state(diferenciaHorasMin >= numMin, "finalPeriod", "assistant.tutorialSession.form.error.horaMin");
			super.state(diferenciaHorasMax < numMax, "finalPeriod", "assistant.tutorialSession.form.error.horaMax");

		}

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;
		choices = SelectChoices.from(Nature.class, object.getNature());

		tuple = super.unbind(object, "title", "abstracts", "nature", "inicialPeriod", "finalPeriod", "link");
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));
		tuple.put("natures", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);

	}

}

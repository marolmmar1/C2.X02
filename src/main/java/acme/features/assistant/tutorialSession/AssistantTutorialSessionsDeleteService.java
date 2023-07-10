/*
 * EmployerDutyDeleteService.java
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

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.entities.TutorialSessionType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionsDeleteService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionsRepository repository;

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
		int tutorialSessionId;
		Tutorial tutorial;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialByTutorialSessionId(tutorialSessionId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionsById(id);

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
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "assistant.tutorialSession.form.error.menor");
			super.state(diferenciaHorasMax < numMax, "finalPeriod", "assistant.tutorialSession.form.error.horaMax");
			super.state(diferenciaHorasMin >= numMin, "finalPeriod", "assistant.tutorialSession.form.error.horaMin");

		}
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TutorialSessionType.class, object.getNature());

		tuple = super.unbind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("tutorialId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		tuple.put("natures", choices);

		super.getResponse().setData(tuple);
	}

}

/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorial;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialShowService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

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
		Assistant assistant;
		Tutorial tutorial;

		masterId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = super.getRequest().getPrincipal().hasRole(assistant);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Collection<Course> course;
		SelectChoices choices;
		Tuple tuple;
		TutorialSession tutorialSession;
		double diferenciaHoras = 0.0;
		double total = 0.0;
		final Collection<TutorialSession> ts = this.repository.findManyTutorialSessionsByTutorialId(object.getId());
		final List<TutorialSession> ListaTutorialSessions = ts.stream().collect(Collectors.toList());
		final boolean draft = false;

		course = this.repository.findAllCourse(draft);
		choices = SelectChoices.from(course, "code", object.getCourse());

		if (ListaTutorialSessions == null)
			total = 0.0;

		for (int i = 0; i < ListaTutorialSessions.size(); i++) {
			tutorialSession = ListaTutorialSessions.get(i);
			final Date inicialPeriod = tutorialSession.getInicialPeriod();
			final Date finalPeriod = tutorialSession.getFinalPeriod();
			final long milisegundosInicio = inicialPeriod.getTime();
			final long milisegundosFin = finalPeriod.getTime();
			final long diferenciaMilisegundos = milisegundosFin - milisegundosInicio;
			if (diferenciaMilisegundos > 0) {
				diferenciaHoras = (double) diferenciaMilisegundos / (1000 * 60 * 60);
				total += diferenciaHoras;
			}

		}
		final int hours = (int) total;
		final int minutes = (int) ((total - hours) * 60);
		final double diffInHoursWithFormat = Double.parseDouble(hours + "." + minutes);
		object.setEstimatedTime(diffInHoursWithFormat);

		tuple = super.unbind(object, "code", "title", "abstracts", "goals", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("estimatedTime", diffInHoursWithFormat);
		super.getResponse().setData(tuple);
	}

}

/*
 * AuthenticatedAnnouncementListAllService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.tutorialSessions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSessions;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionsListService extends AbstractService<Assistant, TutorialSessions> {

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
		boolean result;
		int tutorialId;
		Tutorial tutorial;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		result = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(result);
	}

	@Override
	public void load() {

		final Collection<TutorialSessions> objects;
		final int tutorialId = super.getRequest().getData("id", int.class);
		objects = this.repository.findManyTutorialSessionsByTutorialId(tutorialId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSessions object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<TutorialSessions> objects) {
		assert objects != null;

		int tutorialId;
		Tutorial tutorial;
		final boolean showCreate;

		tutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialById(tutorialId);
		showCreate = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());

		super.getResponse().setGlobal("tutorialId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}

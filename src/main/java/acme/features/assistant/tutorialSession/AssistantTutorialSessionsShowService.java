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

package acme.features.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.entities.TutorialSessionType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionsShowService extends AbstractService<Assistant, TutorialSession> {

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
		final TutorialSession tutorialSession;
		final Tutorial tutorial;
		tutorialSessionId = super.getRequest().getData("id", int.class);
		tutorialSession = this.repository.findOneTutorialSessionsById(tutorialSessionId);
		tutorial = this.repository.findOneTutorialByTutorialSessionId(tutorialSessionId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		status = status && tutorialSession != null;
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
	public void unbind(final TutorialSession object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TutorialSessionType.class, object.getNature());

		tuple = super.unbind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("tutorialId", object.getTutorial().getId());
		tuple.put("natures", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());

		super.getResponse().setData(tuple);
	}

}

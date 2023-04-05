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

package acme.features.assistant.tutorialSessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Tutorial;
import acme.entities.TutorialSessions;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionsDeleteService extends AbstractService<Assistant, TutorialSessions> {

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
		TutorialSessions object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialSessionsById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSessions object) {
		assert object != null;

		super.bind(object, "title", "abstracts", "nature", "inicialPeriod", "finalPeriod", "link");
	}

	@Override
	public void validate(final TutorialSessions object) {
		assert object != null;
	}

	@Override
	public void perform(final TutorialSessions object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSessions object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstracts", "nature", "inicialPeriod", "finalPeriod", "link");
		tuple.put("tutorialId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());

		super.getResponse().setData(tuple);
	}

}

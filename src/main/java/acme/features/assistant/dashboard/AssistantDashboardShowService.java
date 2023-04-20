/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.StatData;
import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		//	final int assistantId = super.getRequest().getPrincipal().getActiveRoleId();

		final Double averageTimeTutorialPerAssistant = this.repository.averageTimeTutorialPerAssistant();
		//		final Double desviationTimeTutorialPerAssistant = this.repository.averageTimeTutorialPerAssistant();
		final Double maxTimeTutorialPerAssistant = this.repository.maxTimeTutorialPerAssistant();
		final Double minTimeTutorialPerAssistant = this.repository.minTimeTutorialPerAssistant();

		final StatData dashboard1 = new StatData();
		dashboard1.setAverage(averageTimeTutorialPerAssistant);
		//		dashboard1.setDesviation(desviationTimeTutorialPerAssistant);
		dashboard1.setMaximum(maxTimeTutorialPerAssistant);
		dashboard1.setMinimum(minTimeTutorialPerAssistant);

		final AssistantDashboard assistantDashboard = new AssistantDashboard();
		assistantDashboard.setTimeOfTutorialPerAssistant(dashboard1);
		super.getBuffer().setData(assistantDashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "timeOfTutorialPerAssistant.average", "timeOfTutorialPerAssistant.minimum");

		super.getResponse().setData(tuple);
	}

}

/*
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

import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		//super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Auditor.class));
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		final int id = super.getRequest().getPrincipal().getAccountId();

		AssistantDashboard dashboard;
		final Integer totalTutorials;
		final Double averageTutorialSessionTime;
		final Double deviationTutorialSessionTime;
		final Double minimumTutorialSessionTime;
		final Double maximumTutorialSessionTime;
		final Double averageTutorialTime;
		final Double deviationTutorialTime;
		final Double minimumTutorialTime;
		final Double maximumTutorialTime;

		totalTutorials = this.repository.findNumberOfTutorialsByAssistantId(id);
		averageTutorialSessionTime = this.repository.findAverageTutorialSessionTimeByAssistantId(id);
		deviationTutorialSessionTime = this.repository.findDeviationTutorialSessionTimeByAssistantId(id);
		minimumTutorialSessionTime = this.repository.findMinTutorialSessionTimeByAssistantId(id);
		maximumTutorialSessionTime = this.repository.findMaxTutorialSessionTimeByAssistantId(id);
		averageTutorialTime = this.repository.findAverageTutorialTimeByAssistantId(id);
		deviationTutorialTime = this.repository.findDeviationTutorialTimeByAssistantId(id);
		minimumTutorialTime = this.repository.findMinTutorialTimeByAssistantId(id);
		maximumTutorialTime = this.repository.findMaxTutorialTimeByAssistantId(id);

		dashboard = new AssistantDashboard();
		dashboard.setTotalTutorials(totalTutorials);
		dashboard.setAverageTutorialSessionTime(averageTutorialSessionTime);
		dashboard.setDeviationTutorialSessionTime(deviationTutorialSessionTime);
		dashboard.setMinimumTutorialSessionTime(minimumTutorialSessionTime);
		dashboard.setMaximumTutorialSessionTime(maximumTutorialSessionTime);
		dashboard.setAverageTutorialTime(averageTutorialTime);
		dashboard.setDeviationTutorialTime(deviationTutorialTime);
		dashboard.setMinimumTutorialTime(minimumTutorialTime);
		dashboard.setMaximumTutorialTime(maximumTutorialTime);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalTutorials", "averageTutorialSessionTime", "deviationTutorialSessionTime", "minimumTutorialSessionTime", "maximumTutorialSessionTime", "averageTutorialTime", "deviationTutorialTime", "minimumTutorialTime",
			"maximumTutorialTime");

		final int id = super.getRequest().getPrincipal().getAccountId();
		final boolean haveTutorials = this.repository.findNumberOfTutorialsByAssistantId(id) > 0;
		final boolean haveTutorialSessions = this.repository.findNumberOfTutorialSessionsByAssistantId(id) > 0;

		super.getResponse().setGlobal("haveTutorials", haveTutorials);
		super.getResponse().setGlobal("haveTutorialSessions", haveTutorialSessions);

		super.getResponse().setData(tuple);

	}

}

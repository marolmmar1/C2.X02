/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.StudentDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final StudentDashboard dashboard;
		Principal principal;
		int id;
		principal = super.getRequest().getPrincipal();
		id = principal.getActiveRoleId();

		final Integer totalNumberHandsonWorkbookActivities;
		final Integer totalNumberTheoryWorkbookActivities;

		final Double activityWorkbookTimeAverage;
		final Double activityWorkbookTimeDeviation;
		final Double activityWorkbookTimeMaximum;
		final Double activityWorkbookTimeMinimum;

		final Double courseEnrolledTimeAverage;
		final Double courseEnrolledTimeDeviation;
		final Double courseEnrolledTimeMaximum;
		final Double courseEnrolledTimeMinimum;

		totalNumberHandsonWorkbookActivities = this.repository.totalNumOfTheoryActivities();
		totalNumberTheoryWorkbookActivities = this.repository.totalNumOfHandsonActivities();

		activityWorkbookTimeAverage = this.repository.averageTimeOfActivityWorkbook(id);
		activityWorkbookTimeDeviation = this.repository.deviationTimeOfActivityWorkbook(id);
		activityWorkbookTimeMinimum = this.repository.minimumTimeOfActivityWorkbook(id);
		activityWorkbookTimeMaximum = this.repository.maximumTimeOfActivityWorkbook(id);

		courseEnrolledTimeAverage = this.repository.averageestimatedTimeAOfCoursesEnrolled(id);
		courseEnrolledTimeDeviation = this.repository.deviationestimatedTimeAOfCoursesEnrolled(id);
		courseEnrolledTimeMaximum = this.repository.maximumestimatedTimeAOfCoursesEnrolled(id);
		courseEnrolledTimeMinimum = this.repository.minimumestimatedTimeAOfCoursesEnrolled(id);

		dashboard = new StudentDashboard();
		dashboard.setTotalNumberTheoryWorkbookActivities(totalNumberTheoryWorkbookActivities);
		dashboard.setTotalNumberHandsonWorkbookActivities(totalNumberHandsonWorkbookActivities);
		dashboard.setActivityWorkbookTimeAverage(activityWorkbookTimeAverage);
		dashboard.setActivityWorkbookTimeDeviation(activityWorkbookTimeDeviation);
		dashboard.setActivityWorkbookTimeMaximum(activityWorkbookTimeMaximum);
		dashboard.setActivityWorkbookTimeMinimum(activityWorkbookTimeMinimum);
		dashboard.setCourseEnrolledTimeAverage(courseEnrolledTimeAverage);
		dashboard.setCourseEnrolledTimeDeviation(courseEnrolledTimeDeviation);
		dashboard.setCourseEnrolledTimeMinimum(courseEnrolledTimeMinimum);
		dashboard.setCourseEnrolledTimeMaximum(courseEnrolledTimeMaximum);
		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"totalNumberHandsonWorkbookActivities", "totalNumberTheoryWorkbookActivities", "activityWorkbookTimeAverage", "activityWorkbookTimeDeviation", "activityWorkbookTimeMaximum", "activityWorkbookTimeMinimum", "courseEnrolledTimeAverage",
			"courseEnrolledTimeDeviation", "courseEnrolledTimeMaximum", "courseEnrolledTimeMinimum");

		super.getResponse().setData(tuple);
	}

}

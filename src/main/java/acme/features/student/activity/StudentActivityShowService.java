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

package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Activity;
import acme.entities.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	//Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	//AbstractService<Authenticated, Consumer> ---------------------------


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
		Activity enrolment;
		final int id = super.getRequest().getData("id", int.class);

		enrolment = this.repository.findActivityById(id);

		super.getBuffer().setData(enrolment);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;

		final int id = super.getRequest().getPrincipal().getAccountId();
		final Collection<Enrolment> enrolments = this.repository.findAllEnrolmentsByStudentId(id);

		final SelectChoices choicesE = SelectChoices.from(enrolments, "code", object.getEnrolment());

		//		final SelectChoices choicesAN = SelectChoices.from(Nature.class, object.getActivityNature());

		tuple = super.unbind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "nature", "link", "enrolment.code");
		//		tuple.put("natureOptions", choicesAN);
		tuple.put("enrolments", choicesE);
		tuple.put("enrolment", choicesE.getSelected().getKey());

		super.getResponse().setData(tuple);
	}

}

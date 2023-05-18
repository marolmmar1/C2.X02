/*
 * AuthenticatedConsumerCreateService.java
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
import acme.entities.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


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

		Activity object;
		object = new Activity();

		object.setTitle("");
		object.setAbstracts("");
		object.setNature(Nature.BALANCE);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {

		assert object != null;

		final int enrolmentId;
		final Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolment", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "nature", "link");
		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod"))
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInicialPeriod()), "finalPeriod", "student.activity.form.error.menor");

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		SelectChoices choices;
		choices = SelectChoices.from(Nature.class, object.getNature());
		tuple = super.unbind(object, "title", "abstracts", "inicialPeriod", "finalPeriod", "link");
		tuple.put("nature", choices.getSelected().getKey());
		tuple.put("natures", choices);

		final int id = super.getRequest().getPrincipal().getAccountId();
		final Collection<Enrolment> enrolments = this.repository.findAllEnrolmentsByStudentId(id, false);

		final SelectChoices choicesE = SelectChoices.from(enrolments, "code", object.getEnrolment());

		tuple.put("enrolment", choicesE.getSelected().getKey());
		tuple.put("enrolments", choicesE);

		super.getResponse().setData(tuple);
	}

}

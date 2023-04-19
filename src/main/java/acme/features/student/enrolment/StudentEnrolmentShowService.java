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

package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Enrolment;
import acme.entities.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	//Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

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
		Enrolment enrolment;
		int id;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(enrolment);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;
		courses = this.repository.findCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "motivation", "goals", "course.title");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());

		super.getResponse().setData(tuple);
	}

}
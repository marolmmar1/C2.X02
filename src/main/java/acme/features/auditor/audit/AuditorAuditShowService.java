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

package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.EnumMode;
import acme.entities.Audit;
import acme.entities.Course;
import acme.entities.MarkType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

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
		Auditor Auditor;
		Audit Audit;

		masterId = super.getRequest().getData("id", int.class);
		Audit = this.repository.findOneAuditById(masterId);
		Auditor = Audit == null ? null : Audit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(Auditor);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Collection<Course> course;
		SelectChoices choices;
		Tuple tuple;

		final boolean draft = false;

		course = this.repository.findAllCourse(draft);
		choices = SelectChoices.from(course, "code", object.getCourse());

		MarkType modeMark;

		final List<MarkType> ts = this.repository.findManyAuditingRecordsMarkTypeByAuditId(object.getId());
		if (ts.size() != 0)
			modeMark = EnumMode.mode(ts);
		else
			modeMark = null;

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("modeMark", modeMark);
		super.getResponse().setData(tuple);
	}

}

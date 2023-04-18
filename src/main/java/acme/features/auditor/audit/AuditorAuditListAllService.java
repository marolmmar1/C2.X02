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

package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.EnumMode;
import acme.entities.Audit;
import acme.entities.MarkType;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListAllService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

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
		final Collection<Audit> objects;
		final int AuditorId = super.getRequest().getPrincipal().getActiveRoleId();

		objects = this.repository.findAllAuditByAuditorId(AuditorId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;
		MarkType modeMark;

		final List<MarkType> ts = this.repository.findManyAuditingRecordsMarkTypeByAuditId(object.getId());
		if (ts.size() != 0)
			modeMark = EnumMode.mode(ts);
		else
			modeMark = null;

		tuple = super.unbind(object, "code", "conclusion");
		tuple.put("modeMark", modeMark);
		super.getResponse().setData(tuple);
	}

}

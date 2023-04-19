/*
 * EmployerDutyUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditingRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.MarkType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

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
		int auditingRecordsId;
		Audit audit;

		auditingRecordsId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditByAuditingRecordId(auditingRecordsId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordsById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		super.bind(object, "subject", "assessment", "markType", "initialPeriod", "finalPeriod", "link");

	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initialPeriod")) {
			long diferenciaHoras = 0;
			final long num = 1;
			final Date moment = MomentHelper.getCurrentMoment();
			final Date initialPeriod = object.getInitialPeriod();
			final long milisegundosInicio = moment.getTime();
			final long milisegundosFin = initialPeriod.getTime();
			final long diferenciaMilisegundos = milisegundosFin - milisegundosInicio;

			if (diferenciaMilisegundos > 0)
				diferenciaHoras = TimeUnit.MILLISECONDS.toHours(diferenciaMilisegundos);

			super.state(diferenciaHoras < num, "initialPeriod", "auditor.auditingRecord.form.error.antes");
		}

		if (!super.getBuffer().getErrors().hasErrors("finalPeriod")) {

			long diferenciaHorasMin = 0;
			final Date initialPeriod = object.getInitialPeriod();
			final Date finalPeriod = object.getFinalPeriod();
			final long milisegundosInicio = initialPeriod.getTime();
			final long milisegundosFin = finalPeriod.getTime();
			final long diferenciaMilisegundosMin = milisegundosFin - milisegundosInicio;
			diferenciaHorasMin = TimeUnit.MILLISECONDS.toHours(diferenciaMilisegundosMin);

			final long numMin = 1;
			super.state(MomentHelper.isAfter(object.getFinalPeriod(), object.getInitialPeriod()), "finalPeriod", "auditor.auditingRecord.form.error.menor");
			super.state(diferenciaHorasMin >= numMin, "finalPeriod", "auditor.auditingRecord.form.error.horaMin");

		}

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(MarkType.class, object.getMarkType());

		tuple = super.unbind(object, "subject", "assessment", "markType", "initialPeriod", "finalPeriod", "link");
		tuple.put("markType", choices.getSelected().getKey());
		tuple.put("auditId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());
		tuple.put("markTypes", choices);

		super.getResponse().setData(tuple);
	}

}

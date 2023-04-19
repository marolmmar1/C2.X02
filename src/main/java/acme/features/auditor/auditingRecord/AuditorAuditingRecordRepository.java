/*
 * AuthenticatedAnnouncementRepository.java
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

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select t from Audit t where t.id= :id")
	Audit findOneAuditById(int id);

	@Query("select ts.audit from AuditingRecord ts where ts.id = :id")
	Audit findOneAuditByAuditingRecordId(int id);

	@Query("select t from AuditingRecord t where t.id= :id")
	AuditingRecord findOneAuditingRecordsById(int id);

	@Query("select ts from AuditingRecord ts where ts.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditId(int id);

}

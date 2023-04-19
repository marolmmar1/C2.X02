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

package acme.features.auditor.audit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.entities.AuditingRecord;
import acme.entities.Course;
import acme.entities.MarkType;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select t from Audit t where t.id= :id")
	Audit findOneAuditById(int id);

	@Query("select a from Auditor a where a.id= :id")
	Auditor findOneAuditorById(int id);

	@Query("select c from Course c where c.id= :id")
	Course findOneCourseById(int id);

	@Query("select t from Audit t where t.auditor.id = :id")
	Collection<Audit> findAllAuditByAuditorId(int id);

	@Query("select ts from AuditingRecord ts where ts.audit.id = :id")
	Collection<AuditingRecord> findManyAuditingRecordsByAuditId(int id);

	@Query("select ts.markType from AuditingRecord ts where ts.audit.id = :id")
	List<MarkType> findManyAuditingRecordsMarkTypeByAuditId(int id);

	@Query("select t from Audit t where t.code = :code")
	Audit findOneAuditByCode(String code);

	@Query("select c from Course c where c.draftMode =:bo")
	Collection<Course> findAllCourse(boolean bo);

}

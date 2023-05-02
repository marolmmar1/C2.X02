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

package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Audit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select t from Audit t where t.id = :id")
	Audit findOneAuditById(int id);

	@Query("select t from Audit t ")
	Collection<Audit> findAllAudit();

	@Query("select t from Audit t where t.course.id = :id")
	Collection<Audit> findManyAuditByCourseId(int id);

}
